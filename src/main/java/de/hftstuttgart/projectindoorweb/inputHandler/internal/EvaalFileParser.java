package de.hftstuttgart.projectindoorweb.inputHandler.internal;

import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.EvaalFileHelper;
import de.hftstuttgart.projectindoorweb.persistence.pojo.PosiReference;
import de.hftstuttgart.projectindoorweb.persistence.pojo.RadiomapElement;
import de.hftstuttgart.projectindoorweb.persistence.pojo.RssiReading;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EvaalFileParser extends Parser {

    private List<RadiomapElement> radiomapElements;

    public EvaalFileParser(boolean fileForRadiomap, File sourceFile) {
        super(fileForRadiomap, sourceFile);
    }

    @Override
    protected boolean isDataValid(String source) {

        return source != null
                && !source.startsWith("%")
                && !source.isEmpty()
                && (isDataValidForPosi(source) || isDataValidForWifi(source));

    }

    @Override
    public boolean isParsingFinished() {
        return radiomapElements != null;
    }

    @Override
    public void run() {

        try {

            List<String> allLines =Files.lines(super.sourceFile.toPath()).filter(s -> {
                return isDataValid(s);
            }).collect(Collectors.toList());

            List<String> posiLines = allLines.stream().filter(s -> {
                return isDataValidForPosi(s);
            }).collect(Collectors.toList());

            List<String> rssiLines = allLines.stream().filter(s -> {
                return isDataValidForWifi(s);
            }).collect(Collectors.toList());

            List<PosiReference> unshiftedPosiReferences = EvaalFileHelper.assemblePosiReferences(posiLines);
            List<PosiReference> posiReferences = EvaalFileHelper.shiftPosiReferenceIntervals(unshiftedPosiReferences);

            List<RssiReading> rssiReadings = EvaalFileHelper.assembleRssiReadings(rssiLines);
            radiomapElements = assembleRadiomapElements(posiReferences, rssiReadings);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public List<RadiomapElement> getRadiomapElements() {
        return radiomapElements;
    }

    private List<RadiomapElement> assembleRadiomapElements(List<PosiReference> posiReferences, List<RssiReading> rssiReadings){

        List<RadiomapElement> result = new ArrayList<>(posiReferences.size());


        double intervalStart;
        double intervalEnd;
        List<RssiReading> relevantReadings;
        List<RssiReading> averagedReadings;
        for (PosiReference posiReference: posiReferences) {
            relevantReadings = EvaalFileHelper.retrieveRssiReadingsForPosiReference(posiReference, rssiReadings);
            rssiReadings.removeAll(relevantReadings);
            averagedReadings = EvaalFileHelper.retrieveAveragedReadings(relevantReadings);
            result.add(new RadiomapElement(super.sourceFile, posiReference, averagedReadings));
        }

        return result;

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
