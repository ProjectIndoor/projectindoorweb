package de.hftstuttgart.projectindoorweb.inputHandler.internal;

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

public class EvaalFileParser extends Parser {

    private List<RadioMapElement> radiomapElements;
    private Map<Integer, WifiBlock> wifiBlocks;
    private Building building;

    public EvaalFileParser(boolean evaluationFile, File sourceFile, Building building) {
        super(evaluationFile, sourceFile);
        this.building = building;

    }

    @Override
    protected boolean isDataValid(String source) {

        return source != null
                && !source.startsWith("%")
                && !source.isEmpty();

    }

    @Override
    public boolean isParsingFinished() {
        return radiomapElements != null;
    }


    public EvaalFile parseIntoEvaalFile() {

        try {

            List<String> allLines = Files.lines(super.sourceFile.toPath())
                    .filter(this::isDataValid).collect(Collectors.toList());

            this.wifiBlocks = retrieveWifiBlockListFromFileLines(allLines);

            List<String> posiLines = allLines.stream()
                    .filter(this::isDataValidForPosi).collect(Collectors.toList());

            List<String> rssiLines = allLines.stream()
                    .filter(this::isDataValidForWifi).collect(Collectors.toList());



            if(posiLines != null && !posiLines.isEmpty()){


                List<PosiReference> unshiftedPosiReferences
                        = EvaalFileHelper.assemblePosiReferences(posiLines);
                List<PosiReference> posiReferences
                        = EvaalFileHelper.shiftPosiReferenceIntervals(unshiftedPosiReferences);

                List<RssiSignal> rssiSignals
                        = EvaalFileHelper.assembleRssiSignals(rssiLines);
                radiomapElements
                        = assembleRadiomapElements(posiReferences, rssiSignals);



            }

            String fileName = super.sourceFile.getName();
            return new EvaalFile(super.evaluationFile, fileName, fileName, 0,
                    wifiBlocks, building, null, new RadioMap(radiomapElements));


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }

    public List<RadioMapElement> getRadiomapElements() {
        return radiomapElements;
    }

    public Map<Integer, WifiBlock> getWifiBlocks() {
        return wifiBlocks;
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
