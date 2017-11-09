package de.hftstuttgart.projectindoorweb.parsers;

import de.hftstuttgart.projectindoorweb.config.ConfigContainer;
import de.hftstuttgart.projectindoorweb.helper.EvaalFileParserHelper;
import de.hftstuttgart.projectindoorweb.maths.LatLongCoord;
import de.hftstuttgart.projectindoorweb.maths.LocXYZ;
import de.hftstuttgart.projectindoorweb.maths.MyGeoMath;
import de.hftstuttgart.projectindoorweb.pojos.PosiReference;
import de.hftstuttgart.projectindoorweb.pojos.Position;
import de.hftstuttgart.projectindoorweb.pojos.RadiomapElement;
import de.hftstuttgart.projectindoorweb.pojos.RssiReading;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

            List<String> posiLines = Files.lines(super.sourceFile.toPath()).filter(s -> {
                return isDataValid(s) && isDataValidForPosi(s);
            }).collect(Collectors.toList());

            List<PosiReference> unshiftedPosiReferences = EvaalFileParserHelper.assemblePosiReferences(posiLines);
            List<PosiReference> posiReferences = EvaalFileParserHelper.shiftPosiReferenceIntervals(unshiftedPosiReferences);

            List<String> rssiLines = Files.lines(super.sourceFile.toPath()).filter(s -> {
                return isDataValid(s) && isDataValidForWifi(s);
            }).collect(Collectors.toList());

            List<RssiReading> rssiReadings = EvaalFileParserHelper.assembleRssiReadings(rssiLines);
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
            relevantReadings = EvaalFileParserHelper.retrieveRssiReadingsForPosiReference(posiReference, rssiReadings);
            rssiReadings.removeAll(relevantReadings);
            averagedReadings = EvaalFileParserHelper.retrieveAveragedReadings(relevantReadings);
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
