package de.hftstuttgart.projectindoorweb.inputHandler;

import de.hftstuttgart.projectindoorweb.application.internal.AssertParam;
import de.hftstuttgart.projectindoorweb.geoCalculator.transformation.POSILogMerger;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.EvaalFileHelper;
import de.hftstuttgart.projectindoorweb.persistence.entities.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EvaalFilePreProcessingServiceImpl implements PreProcessingService {


    @Override
    public List<EvaalFile> processEvaalFiles(Building building, boolean evaluationFiles,
                                             List<File> radioMapFiles) {

        AssertParam.throwIfNull(radioMapFiles, "radioMapFiles");
        AssertParam.throwIfNull(building, "building");

        List<EvaalFile> processedEvaalFiles = new ArrayList<>(radioMapFiles.size());

        for (File inputFile : radioMapFiles) {
            if (!inputFile.isDirectory() && !inputFile.getPath().contains(".swp")) {
                List<String> allLines = null;
                try {
                    allLines = Files.lines(inputFile.toPath())
                            .filter(this::isDataValid).collect(Collectors.toList());
                    processedEvaalFiles.add(parseIntoEvaalFile(evaluationFiles, inputFile.getName(), allLines, building));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return processedEvaalFiles;

    }

    @Override
    public List<EvaalFile> processEmptyEvaalFiles(Building building, boolean evaluationFiles,
                                                  List<File> emptyRadioMapFiles, List<File> referencePointFiles) {

        List<EvaalFile> processedEvaalFiles = new ArrayList<>(emptyRadioMapFiles.size());
        if(emptyRadioMapFiles.size() != referencePointFiles.size()){
            return processedEvaalFiles;
        }

        File emptyRadioMapFile;
        File referencePointFile;

        List<String> allLines;
        List<String> emptyPosiReferences;
        List<String> completedPosiReferences;
        List<String> allLinesWithCompletesPosiReferences;
        for(int i = 0; i < emptyRadioMapFiles.size(); i++){
            emptyRadioMapFile = emptyRadioMapFiles.get(i);
            referencePointFile = referencePointFiles.get(i);

            try {
                allLines = Files.lines(emptyRadioMapFile.toPath())
                        .filter(this::isDataValid).collect(Collectors.toList());
                emptyPosiReferences = allLines.stream().filter(this::isDataValidForPosi).collect(Collectors.toList());
                completedPosiReferences = POSILogMerger.combineReferencePointsWithPOSILines(emptyPosiReferences, referencePointFile,
                        building, building.getImagePixelWidth(), building.getImagePixelHeight());
                allLinesWithCompletesPosiReferences = insertCompletedPosiReferencesIntoFileContent(allLines, completedPosiReferences);
                processedEvaalFiles.add(parseIntoEvaalFile(evaluationFiles, emptyRadioMapFile.getName(),
                        allLinesWithCompletesPosiReferences, building));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return processedEvaalFiles;

    }

    public EvaalFile parseIntoEvaalFile(boolean evaluationFiles, String fileName, List<String> allLines, Building building) {


        Map<Integer, WifiBlock> wifiBlocks = retrieveWifiBlockListFromFileLines(allLines);

        List<String> posiLines = allLines.stream()
                .filter(this::isDataValidForPosi).collect(Collectors.toList());

        List<String> rssiLines = allLines.stream()
                .filter(this::isDataValidForWifi).collect(Collectors.toList());

        List<RadioMapElement> radioMapElements = null;
        if (posiLines != null && !posiLines.isEmpty()) {

            List<PosiReference> unshiftedPosiReferences
                    = EvaalFileHelper.assemblePosiReferences(posiLines);
            List<PosiReference> posiReferences
                    = EvaalFileHelper.shiftPosiReferenceIntervals(unshiftedPosiReferences);

            List<RssiSignal> rssiSignals
                    = EvaalFileHelper.assembleRssiSignals(rssiLines);
            radioMapElements
                    = assembleRadiomapElements(posiReferences, rssiSignals);

        }

        return new EvaalFile(evaluationFiles, fileName, fileName, 0,
                wifiBlocks, building, null, new RadioMap(radioMapElements));

    }

    private List<String> insertCompletedPosiReferencesIntoFileContent(List<String> allLines, List<String> completedPosiReferences){

        int completedPosiReferencesIndex = 0;
        String currentLine;
        for(int i = 0; i < allLines.size(); i++){
            currentLine = allLines.get(i);
            if(this.isDataValidForPosi(currentLine)){
                allLines.set(i, completedPosiReferences.get(completedPosiReferencesIndex));
                completedPosiReferencesIndex++;
            }
        }

        return allLines;

    }


    private boolean isDataValid(String source) {

        return source != null
                && !source.startsWith("%")
                && !source.isEmpty();

    }


    private int retrieveNumberOfMacs(List<RssiSignal> rssiSignals) {

        List<String> macs = new ArrayList<>();
        String mac;
        for (RssiSignal signal :
                rssiSignals) {
            mac = signal.getWifiAccessPoint().getMacAddress();
            if (!macs.contains(mac)) {
                macs.add(mac);
            }
        }

        return macs.size();


    }

    private List<RadioMapElement> assembleRadiomapElements(List<PosiReference> posiReferences, List<RssiSignal> rssiSignals) {

        List<RadioMapElement> result = new ArrayList<>(posiReferences.size());


        double intervalStart;
        double intervalEnd;
        List<RssiSignal> relevantReadings;
        List<RssiSignal> averagedReadings;
        for (PosiReference posiReference : posiReferences) {
            relevantReadings = EvaalFileHelper.retrieveRssiReadingsForPosiReference(posiReference, rssiSignals);
            rssiSignals.removeAll(relevantReadings);
            averagedReadings = EvaalFileHelper.retrieveAveragedReadings(relevantReadings);
            result.add(new RadioMapElement(posiReference, averagedReadings));
        }

        return result;

    }

    private Map<Integer, WifiBlock> retrieveWifiBlockListFromFileLines(List<String> fileLines) {

        Map<Integer, WifiBlock> wifiBlocks = new HashMap<>();

        int currentBlock = 0;
        boolean insideWifiBlock = false;
        for (String currentLine :
                fileLines) {
            if (currentLine.startsWith("WIFI")) {
                if (!insideWifiBlock) {
                    insideWifiBlock = true;
                    wifiBlocks.put(currentBlock, new WifiBlock(new ArrayList<>()));
                }
                wifiBlocks.get(currentBlock).getWifiLines().add(currentLine);
            }
            if (insideWifiBlock && !currentLine.startsWith("WIFI")) {
                insideWifiBlock = false;
                currentBlock++;
            }
        }

        return wifiBlocks;

    }


    private boolean isDataValidForPosi(String source) {

        return source.split(";")[0].equals("POSI")
                && source.split(";").length == 7;

    }

    private boolean isDataValidForWifi(String source) {

        return source.split(";")[0].equals("WIFI")
                && source.split(";").length == 6;

    }

}
