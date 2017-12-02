package de.hftstuttgart.projectindoorweb.positionCalculator;

import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LatLongCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.transformation.TransformationHelper;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.EvaalFileHelper;
import de.hftstuttgart.projectindoorweb.positionCalculator.internal.CorrelationMode;
import de.hftstuttgart.projectindoorweb.positionCalculator.internal.utility.WifiMathHelper;
import de.hftstuttgart.projectindoorweb.application.internal.AssertParam;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.ConfigContainer;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.MathHelper;
import de.hftstuttgart.projectindoorweb.persistence.entities.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WifiPositionCalculatorServiceImpl implements PositionCalculatorService {


    @Override
    public List<WifiPositionResult> calculatePositions(EvaalFile evaluationFile, EvaalFile[] radioMapFiles,
                                                       Building building, boolean pixelPositionRequired) {

        AssertParam.throwIfNull(evaluationFile, "evaluationFile");
        AssertParam.throwIfNull(radioMapFiles, "radioMapFiles");
        AssertParam.throwIfNull(building, "building");

        int totalNumberOfWifiBlocks = evaluationFile.getWifiBlocks().size();

        List<WifiPositionResult> wifiPositionResults = new ArrayList<>();
        List<RssiSignal> rssiSignals;

        WifiPositionResult previousResult;
        WifiPositionResult result;

        List<RadioMap> radioMaps = collectRadioMaps(radioMapFiles);


        if (ConfigContainer.MERGE_RADIOMAP_ELEMENTS) {
            RadioMap tmp = EvaalFileHelper.mergeRadioMapsBySimilarPositions(radioMaps);
            radioMaps.clear();
            radioMaps.add(tmp);
        }

        for (RadioMap radioMap :
                radioMaps) {
            rssiSignals = EvaalFileHelper.retrieveAveragedRssiSignalsForWifiBlock(evaluationFile.getWifiBlocks(), 0);
            previousResult = calculateSinglePosition(rssiSignals, radioMap, building, pixelPositionRequired);

            for (int block = 1; block < totalNumberOfWifiBlocks; block++) {
                rssiSignals = EvaalFileHelper.retrieveAveragedRssiSignalsForWifiBlock(evaluationFile.getWifiBlocks(), block);
                result = calculateSinglePosition(rssiSignals, radioMap, building, pixelPositionRequired);
                result = WifiMathHelper.smoothenWifiPosition(result, previousResult, result.getWeight());
                wifiPositionResults.add(result);
                previousResult = result;
            }
        }

        return wifiPositionResults;

    }

    @Override
    public WifiPositionResult calculateSinglePosition(String[] wifiReadings, EvaalFile[] radioMapFiles,
                                                      Building building, boolean pixelPositionRequired) {

        AssertParam.throwIfNull(wifiReadings, "wifiReadings");
        AssertParam.throwIfNull(radioMapFiles, "radioMapFiles");

        List<RssiSignal> rssiSignals = new ArrayList<>(wifiReadings.length);

        for (String wifiReading :
                wifiReadings) {
            rssiSignals.add(parseRssiSignal(wifiReading));
        }

        List<RadioMap> radioMaps = collectRadioMaps(radioMapFiles);
        RadioMap mergedRadioMap = EvaalFileHelper.mergeRadioMapsBySimilarPositions(radioMaps);

        return calculateSinglePosition(rssiSignals, mergedRadioMap, building, pixelPositionRequired);

    }

    private WifiPositionResult calculateSinglePosition(List<RssiSignal> rssiSignals, RadioMap radioMap,
                                                       Building building, boolean pixelPositionRequired) {

        List<RadioMapElement> radioMapElements = radioMap.getRadioMapElements();
        List<WifiPositionResult> preResults = new ArrayList<>();

        double positionWeight = 0.0;
        Position referencePosition;
        for (RadioMapElement radioMapElement :
                radioMapElements) {
            if (ConfigContainer.CORRELATION_MODE == CorrelationMode.EUCLIDIAN) {
                positionWeight = WifiMathHelper.calculateEuclidianRssiDistance(radioMapElement.getRssiSignals(), rssiSignals);
            } else {
                //TODO: Implement scalar position weight calculation
            }
            referencePosition = radioMapElement.getPosiReference().getReferencePosition();
            preResults.add(new WifiPositionResult(referencePosition.getX(), referencePosition.getY(), referencePosition.getZ(), false, positionWeight));
        }

        Collections.sort(preResults);

        int numRefs = ConfigContainer.USE_FIXED_WEIGHTS ? 3 : ConfigContainer.NUM_REFERENCES_IN_WEIGHTED_MODE;
        preResults = preResults.subList(0, numRefs);

        if (ConfigContainer.USE_FIXED_WEIGHTS) {
            preResults.get(0).setWeight(2);
            preResults.get(1).setWeight(0.9);
            preResults.get(2).setWeight(0.9);
        }

        double weightSum = 0.0;

        WifiPositionResult result;
        Position resultPosition = new Position();
        WifiPositionResult tmp;
        for (WifiPositionResult preResult :
                preResults) {
            weightSum += preResult.getWeight();
            tmp = WifiMathHelper.multiplyPositionWithWeight(preResult, preResult.getWeight());
            resultPosition = MathHelper.addTwoPositions(resultPosition, new Position(tmp.getX(), tmp.getY(), tmp.getZ(), tmp.isWgs84()));
        }

        if (weightSum > 0) {
            resultPosition = MathHelper.multiplyPosition(resultPosition, Double.valueOf(1) / weightSum);
        }

        if (pixelPositionRequired) {
            resultPosition = retrievePositionAsPixels(building, resultPosition);
        }

        result = new WifiPositionResult(resultPosition.getX(), resultPosition.getY(), resultPosition.getZ(),
                resultPosition.isWgs84(), weightSum);

        return result;


    }

    private Position retrievePositionAsPixels(Building building, Position latLongPosition) {

        LatLongCoord latLongCoord = new LatLongCoord(latLongPosition.getX(), latLongPosition.getY());
        double[] positionInPixels = TransformationHelper.wgsToPict(building, latLongCoord,
                building.getImagePixelWidth(), building.getImagePixelHeight());

        return new Position(positionInPixels[0], positionInPixels[1], latLongPosition.getZ(), false);

    }

    private List<RadioMap> collectRadioMaps(EvaalFile[] radioMapFiles) {

        List<RadioMap> radioMaps = new ArrayList<>(radioMapFiles.length);

        for (EvaalFile radioMapFile :
                radioMapFiles) {
            radioMaps.add(radioMapFile.getRadioMap());
        }

        return radioMaps;

    }

    private RssiSignal parseRssiSignal(String wifiReading) {

        String[] elements = wifiReading.split(";");

        double appTimestamp = Double.valueOf(elements[1]);
        String mac = elements[4];
        double rssiSignalStrength = Double.valueOf(elements[5]);

        WifiAccessPoint accessPoint = new WifiAccessPoint(mac, null);

        return new RssiSignal(appTimestamp, rssiSignalStrength, false, accessPoint);

    }


}
