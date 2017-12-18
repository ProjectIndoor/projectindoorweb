package de.hftstuttgart.projectindoorweb.positionCalculator;

import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LatLongCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.transformation.TransformationHelper;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.EvaalFileHelper;
import de.hftstuttgart.projectindoorweb.positionCalculator.internal.CorrelationMode;
import de.hftstuttgart.projectindoorweb.positionCalculator.internal.utility.ProjectParameterResolver;
import de.hftstuttgart.projectindoorweb.positionCalculator.internal.utility.WifiMathHelper;
import de.hftstuttgart.projectindoorweb.application.internal.AssertParam;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.ConfigContainer;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.MathHelper;
import de.hftstuttgart.projectindoorweb.persistence.entities.*;
import sun.security.krb5.Config;

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

        Boolean mergeRadioMapElements = (Boolean) ProjectParameterResolver
                .retrieveParameterValue(project, "mergeRadioMaps", Boolean.class);
        if (mergeRadioMapElements == null) {
            mergeRadioMapElements = ConfigContainer.MERGE_RADIOMAP_ELEMENTS;
        }

        if (mergeRadioMapElements) {
            RadioMap tmp = EvaalFileHelper.mergeRadioMapsBySimilarPositions(building, project, radioMaps);
            radioMaps.clear();
            radioMaps.add(tmp);
        }

        Double wifiPositionSmootheningFactor = (Double) ProjectParameterResolver.retrieveParameterValue(project, "wifiPositionSmootheningFactor", Double.class);
        if (wifiPositionSmootheningFactor == null || wifiPositionSmootheningFactor < 0.0) {
            wifiPositionSmootheningFactor = ConfigContainer.WIFI_POSITION_SMOOTHENER;
        }
        for (RadioMap radioMap :
                radioMaps) {
            rssiSignals = EvaalFileHelper.retrieveAveragedRssiSignalsForWifiBlock(evaluationFile.getWifiBlocks(), 0);
            previousResult = calculateSinglePosition(project, rssiSignals, radioMap);
            Boolean smoothenWifiPositions = (Boolean) ProjectParameterResolver.retrieveParameterValue(project, "smoothenWifiPositions", Boolean.class);
            if (smoothenWifiPositions == null) {
                smoothenWifiPositions = ConfigContainer.SMOOTHEN_WIFI_POSITIONS;
            }
            if (!smoothenWifiPositions) {
                wifiPositionResults.add(previousResult);
            }

            for (int block = 1; block < totalNumberOfWifiBlocks; block++) {
                rssiSignals = EvaalFileHelper.retrieveAveragedRssiSignalsForWifiBlock(
                        evaluationFile.getWifiBlocks(), block);
                result = calculateSinglePosition(project, rssiSignals, radioMap);

                if (smoothenWifiPositions) {
                    result = WifiMathHelper.smoothenWifiPosition(wifiPositionSmootheningFactor,
                            result, previousResult, result.getWeight());
                    previousResult = result;
                }

                if (evaluationFile.getRadioMap() != null) {
                    Double maximumConfiguredTimeDelta = (Double) ProjectParameterResolver.retrieveParameterValue(project,
                            "posiReferenceSimilarityTimeDelta", Double.class);
                    if (maximumConfiguredTimeDelta == null || maximumConfiguredTimeDelta < 0.0) {
                        maximumConfiguredTimeDelta = ConfigContainer.POSI_REFERENCE_MAX_TIME_DELTA_MILLIS;
                    }
                    Boolean useShiftedPosiReferences = (Boolean) ProjectParameterResolver.retrieveParameterValue(project,
                            "useShiftedPosiReferences", Boolean.class);
                    if (useShiftedPosiReferences == null) {
                        useShiftedPosiReferences = ConfigContainer.USE_SHIFTED_POSI_REFERENCES;
                    }
                    result.setPosiReference(EvaalFileHelper
                            .findBestPostReferenceForWifiResult(result.getRssiSignalsAppTimestamp(),
                                    maximumConfiguredTimeDelta, useShiftedPosiReferences, evaluationFile.getRadioMap()));
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
        RadioMap mergedRadioMap = EvaalFileHelper.mergeRadioMapsBySimilarPositions(building, project, radioMaps);

        return calculateSinglePosition(project, rssiSignals, mergedRadioMap);

    }

    private WifiPositionResult calculateSinglePosition(Project project, List<RssiSignal> rssiSignals, RadioMap radioMap) {

        List<RadioMapElement> radioMapElements = radioMap.getRadioMapElements();
        List<WifiPositionResult> preResults = new ArrayList<>();

        double positionWeight = 0.0;
        double wifiBlockAppTimestamp = rssiSignals.get(0).getAppTimestamp();
        Position referencePosition;
        CorrelationMode correlationMode = (CorrelationMode) ProjectParameterResolver.retrieveParameterValue(project, "correlationMode", CorrelationMode.class);
        if (correlationMode == null) {
            correlationMode = ConfigContainer.CORRELATION_MODE;
        }
        for (RadioMapElement radioMapElement :
                radioMapElements) {
            positionWeight = WifiMathHelper.calculateEuclidianRssiDistance(radioMapElement.getRssiSignals(), rssiSignals);
            if (correlationMode == CorrelationMode.EUCLIDIAN) {

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

        Boolean useFixedWeights = (Boolean) ProjectParameterResolver.retrieveParameterValue(project, "useFixedWeights", Boolean.class);
        if (useFixedWeights == null) {
            useFixedWeights = ConfigContainer.USE_FIXED_WEIGHTS;
        }
        Integer numRefsFromUser = (Integer) ProjectParameterResolver.retrieveParameterValue(project, "weightedModeNumReferences", Integer.class);
        if (numRefsFromUser == null || numRefsFromUser < 0) {
            numRefsFromUser = ConfigContainer.NUM_REFERENCES_IN_WEIGHTED_MODE;
        }
        int numRefs = useFixedWeights ? 3 : numRefsFromUser;
        preResults = preResults.subList(0, numRefs);

        Double weightResult1 = (Double) ProjectParameterResolver.retrieveParameterValue(project, "weightResult1", Double.class);
        if (weightResult1 == null || weightResult1 < 0.0) {
            weightResult1 = ConfigContainer.WEIGHT_RESULT_1;
        }
        Double weightResult2 = (Double) ProjectParameterResolver.retrieveParameterValue(project, "weightResult2", Double.class);
        if (weightResult2 == null || weightResult2 < 0.0) {
            weightResult2 = ConfigContainer.WEIGHT_RESULT_2;
        }
        Double weightResult3 = (Double) ProjectParameterResolver.retrieveParameterValue(project, "weightResult3", Double.class);
        if (weightResult3 == null || weightResult3 < 0.0) {
            weightResult3 = ConfigContainer.WEIGHT_RESULT_3;
        }
        if (useFixedWeights) {
            preResults.get(0).setWeight(weightResult1);
            preResults.get(1).setWeight(weightResult2);
            preResults.get(2).setWeight(weightResult3);
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
