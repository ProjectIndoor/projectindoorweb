package de.hftstuttgart.projectindoorweb.algorithm;

import de.hftstuttgart.projectindoorweb.algorithm.internal.CorrelationMode;
import de.hftstuttgart.projectindoorweb.algorithm.internal.EvalFileParser;
import de.hftstuttgart.projectindoorweb.algorithm.internal.WifiMathHelper;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.ConfigContainer;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.MathHelper;
import de.hftstuttgart.projectindoorweb.persistence.entities.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WifiAlgorithmHandler implements AlgorithmHandler {


    @Override
    public List<WifiPositionResult> calculatePositions(File evalFile, List<RadioMap> radioMaps) {


        EvalFileParser parser = new EvalFileParser(evalFile);

        int totalNumberOfWifiBlocks = parser.getTotalNumberOfWifiBlocks();

        List<WifiPositionResult> wifiPositionResults = new ArrayList<>();
        List<RssiSignal> rssiSignals;

        WifiPositionResult previousResult;
        WifiPositionResult result;

        for (RadioMap radioMap:
             radioMaps) {
            rssiSignals = parser.retrieveAveragedRssiSignalsForWifiBlock(0);
            previousResult = calculateSinglePosition(rssiSignals, radioMap);

            for (int block = 1; block < totalNumberOfWifiBlocks; block++) {
                rssiSignals = parser.retrieveAveragedRssiSignalsForWifiBlock(block);
                result = calculateSinglePosition(rssiSignals, radioMap);
                result = WifiMathHelper.smoothenWifiPosition(result, previousResult, result.getWeight());
                wifiPositionResults.add(result);
                previousResult = result;
            }
        }

        return wifiPositionResults;

    }

    private WifiPositionResult calculateSinglePosition(List<RssiSignal> rssiSignals, RadioMap radioMap) {


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

        result = new WifiPositionResult(resultPosition.getX(), resultPosition.getY(), resultPosition.getZ(),
                resultPosition.isWgs84(), weightSum);

        return result;


    }


}
