package de.hftstuttgart.projectindoorweb.inputHandler.internal;

import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.LogFileHelper;
import de.hftstuttgart.projectindoorweb.persistence.entities.PosiReference;
import de.hftstuttgart.projectindoorweb.persistence.entities.RadioMapElement;
import de.hftstuttgart.projectindoorweb.persistence.entities.RssiSignal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LogFileParser extends Parser {

    private List<RadioMapElement> radiomapElements;

    public LogFileParser(boolean fileForRadiomap, File sourceFile) {
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

            List<PosiReference> unshiftedPosiReferences = LogFileHelper.assemblePosiReferences(posiLines);
            List<PosiReference> posiReferences = LogFileHelper.shiftPosiReferenceIntervals(unshiftedPosiReferences);

            List<RssiSignal> rssiSignals = LogFileHelper.assembleRssiSignals(rssiLines);
            //this.numberOfMacs = retrieveNumberOfMacs(rssiSignals);
            radiomapElements = assembleRadiomapElements(posiReferences, rssiSignals);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public List<RadioMapElement> getRadiomapElements() {
        return radiomapElements;
    }

    private int retrieveNumberOfMacs(List<RssiSignal> rssiSignals){

        List<String> macs = new ArrayList<>();
        String mac;
        for (RssiSignal signal:
             rssiSignals) {
            mac = signal.getWifiAccessPoint().getMacAddress();
            if(!macs.contains(mac)){
                macs.add(mac);
            }
        }

        return macs.size();



    }

    private List<RadioMapElement> assembleRadiomapElements(List<PosiReference> posiReferences, List<RssiSignal> rssiSignals){

        List<RadioMapElement> result = new ArrayList<>(posiReferences.size());


        double intervalStart;
        double intervalEnd;
        List<RssiSignal> relevantReadings;
        List<RssiSignal> averagedReadings;
        for (PosiReference posiReference: posiReferences) {
            relevantReadings = LogFileHelper.retrieveRssiReadingsForPosiReference(posiReference, rssiSignals);
            rssiSignals.removeAll(relevantReadings);
            averagedReadings = LogFileHelper.retrieveAveragedReadings(relevantReadings);
            result.add(new RadioMapElement(posiReference, averagedReadings));
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
