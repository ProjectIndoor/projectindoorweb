package de.hftstuttgart.projectindoorweb.positionCalculator.internal.utility;

import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.ConfigContainer;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.MathHelper;
import de.hftstuttgart.projectindoorweb.persistence.entities.*;

import java.util.List;

public class WifiMathHelper {



    public static double calculateEuclidianRssiDistance(List<RssiSignal> radioMapSignals, List<RssiSignal> evaluationSignals) {

        double squareTotalSum = 0.0;
        double radioMapSquareSum = 0.0;
        double evaluationSquareSum = 0.0;
        double radioMapAddend;
        double evalAddend;

        int maxIndex = Math.max(radioMapSignals.size(), evaluationSignals.size());

        RssiSignal radioMapSignal;
        RssiSignal evalSignal;

        for (int i = 0; i < maxIndex; i++) {
            try{
                radioMapSignal = radioMapSignals.get(i);
            }catch(IndexOutOfBoundsException ex){
                radioMapSignal = null;
            }
            radioMapAddend = radioMapSignal == null ? -95 : radioMapSignal.getRssiSignalStrength();
            try{
                evalSignal = evaluationSignals.get(i);
            }catch(IndexOutOfBoundsException ex){
                evalSignal = null;
            }
            evalAddend = evalSignal == null ? -95 : evalSignal.getRssiSignalStrength();
            squareTotalSum += Math.pow((radioMapAddend - evalAddend), 2);
            radioMapSquareSum += Math.pow(radioMapAddend, 2);
            evalAddend += Math.pow(evalAddend, 2);
        }

        double result;
        if (squareTotalSum > 0.0) {
            result = Double.valueOf(1) / Math.sqrt(squareTotalSum);
        } else {
            result = 0.0;
        }

        return result;
    }

    public static WifiPositionResult smoothenWifiPosition(WifiPositionResult currentResult, WifiPositionResult previousResult, double weight){

        double factorOld = ConfigContainer.WIFI_POSITION_SMOOTHENER;
        double factorNew = Double.valueOf(1) - factorOld;
        double averagedAppTimestamp = (previousResult.getRssiSignalsAppTimestamp() + currentResult.getRssiSignalsAppTimestamp()) / 2;

        Position newPreviousPosition = MathHelper.multiplyPosition(previousResult.getX(), previousResult.getY(),
                previousResult.getZ(), previousResult.isWgs84(), factorOld);
        Position newCurrentPosition = MathHelper.multiplyPosition(currentResult.getX(), currentResult.getY(),
                currentResult.getZ(), currentResult.isWgs84(), factorNew);

        Position newPosition = MathHelper.addTwoPositions(newPreviousPosition, newCurrentPosition);
        return new WifiPositionResult(newPosition.getX(), newPosition.getY(), newPosition.getZ(), newPosition.isWgs84(),
                weight, averagedAppTimestamp);


    }

    public static WifiPositionResult multiplyPositionWithWeight(WifiPositionResult wifiPositionResult, double weight) {

        double newX = wifiPositionResult.getX() * weight;
        double newY = wifiPositionResult.getY() * weight;
        double newZ = wifiPositionResult.getZ() * weight;

        return new WifiPositionResult(newX, newY, newZ, wifiPositionResult.isWgs84(), weight);

    }


}
