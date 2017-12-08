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
    public List<WifiPositionResult> calculatePositions(Building building, Project project, EvaalFile evaluationFile,
                                                       EvaalFile[] radioMapFiles) {

        AssertParam.throwIfNull(building, "building");
        AssertParam.throwIfNull(project, "project");
        AssertParam.throwIfNull(evaluationFile, "evaluationFile");
        AssertParam.throwIfNull(radioMapFiles, "radioMapFiles");


        int totalNumberOfWifiBlocks = evaluationFile.getWifiBlocks().size();

        List<WifiPositionResult> wifiPositionResults = new ArrayList<>();
        List<RssiSignal> rssiSignals;

        WifiPositionResult previousResult;
        WifiPositionResult result;

        List<RadioMap> radioMaps = collectRadioMaps(radioMapFiles);


        if (ConfigContainer.MERGE_RADIOMAP_ELEMENTS) {
            RadioMap tmp = EvaalFileHelper.mergeRadioMapsBySimilarPositions(building, radioMaps);
            radioMaps.clear();
            radioMaps.add(tmp);
        }

        for (RadioMap radioMap :
                radioMaps) {
            rssiSignals = EvaalFileHelper.retrieveAveragedRssiSignalsForWifiBlock(evaluationFile.getWifiBlocks(), 0);
            previousResult = calculateSinglePosition(rssiSignals, radioMap);
            if(!ConfigContainer.SMOOTHEN_WIFI_POSITIONS){
                wifiPositionResults.add(previousResult);
            }

            for (int block = 1; block < totalNumberOfWifiBlocks; block++) {
                rssiSignals = EvaalFileHelper.retrieveAveragedRssiSignalsForWifiBlock(evaluationFile.getWifiBlocks(), block);
                result = calculateSinglePosition(rssiSignals, radioMap);

                if(ConfigContainer.SMOOTHEN_WIFI_POSITIONS){
                    result = WifiMathHelper.smoothenWifiPosition(result, previousResult, result.getWeight());
                    previousResult = result;
                }

                if(evaluationFile.getRadioMap() != null){
                    result.setPosiReference(EvaalFileHelper
                            .findBestPostReferenceForWifiResult(result.getRssiSignalsAppTimestamp(), evaluationFile.getRadioMap()));
                }

                wifiPositionResults.add(result);
            }
        }

        return wifiPositionResults;

    }

    @Override
    public WifiPositionResult calculateSinglePosition(Building building, Project project, String[] wifiReadings,
                                                      EvaalFile[] radioMapFiles) {

        AssertParam.throwIfNull(wifiReadings, "wifiReadings");
        AssertParam.throwIfNull(radioMapFiles, "radioMapFiles");
        AssertParam.throwIfNull(project, "project");
        AssertParam.throwIfNull(building, "building");

        List<RssiSignal> rssiSignals = new ArrayList<>(wifiReadings.length);

        for (String wifiReading :
                wifiReadings) {
            rssiSignals.add(parseRssiSignal(wifiReading));
        }

        List<RadioMap> radioMaps = collectRadioMaps(radioMapFiles);
        RadioMap mergedRadioMap = EvaalFileHelper.mergeRadioMapsBySimilarPositions(building, radioMaps);

        return calculateSinglePosition(rssiSignals, mergedRadioMap);

    }

    private WifiPositionResult calculateSinglePosition(List<RssiSignal> rssiSignals, RadioMap radioMap) {

        List<RadioMapElement> radioMapElements = radioMap.getRadioMapElements();
        List<WifiPositionResult> preResults = new ArrayList<>();

        double positionWeight = 0.0;
        double wifiBlockAppTimestamp = rssiSignals.get(0).getAppTimestamp();
        Position referencePosition;
        for (RadioMapElement radioMapElement :
                radioMapElements) {
            if (ConfigContainer.CORRELATION_MODE == CorrelationMode.EUCLIDIAN) {
                positionWeight = WifiMathHelper.calculateEuclidianRssiDistance(radioMapElement.getRssiSignals(), rssiSignals);
            } else {
                /*
                * This is where the Scalar weight position calculation would have
                * been included if it had been part of the prototype.
                *
                * */
            }
            referencePosition = radioMapElement.getPosiReference().getReferencePosition();
            preResults.add(new WifiPositionResult(referencePosition.getX(), referencePosition.getY(), referencePosition.getZ(),
                    true, positionWeight, wifiBlockAppTimestamp));
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

        result = new WifiPositionResult(resultPosition.getX(), resultPosition.getY(), resultPosition.getZ(),
                resultPosition.isWgs84(), weightSum, wifiBlockAppTimestamp);

        return result;


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
