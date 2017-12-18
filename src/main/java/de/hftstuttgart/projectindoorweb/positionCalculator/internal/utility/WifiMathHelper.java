package de.hftstuttgart.projectindoorweb.positionCalculator.internal.utility;

import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.ConfigContainer;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.MathHelper;
import de.hftstuttgart.projectindoorweb.persistence.entities.*;

import java.util.List;

public class WifiMathHelper {


    public static double calculateEuclidianRssiDistance(List<RssiSignal> evaluationSignals, List<RssiSignal> radioMapSignals) {

        double squareTotalSum = 0.0;
        double radioMapSquareSum = 0.0;
        double evaluationSquareSum = 0.0;
        double radioMapAddend;
        double evalAddend;

        int maxIndex = Math.max(radioMapSignals.size(), evaluationSignals.size());

        RssiSignal radioMapSignal;
        RssiSignal evalSignal;

        for (int i = 0; i < maxIndex; i++) {
            try {
                radioMapSignal = radioMapSignals.get(i);
            } catch (IndexOutOfBoundsException ex) {
                radioMapSignal = null;
            }
            radioMapAddend = radioMapSignal == null ? -95 : radioMapSignal.getRssiSignalStrength();
            try {
                evalSignal = evaluationSignals.get(i);
            } catch (IndexOutOfBoundsException ex) {
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

    public static double calculateScalarRssiDistance(List<RssiSignal> evaluationSignals, List<RssiSignal> radioMapSignals) {

        double radioMapAddend;
        double evalAddend;
        double radioMapSignalSquareSum = 0.0;
        double evaluationSignalSquareSum = 0.0;
        double totalSum = 0.0;

        int maxIndex = Math.max(radioMapSignals.size(), evaluationSignals.size());

        double radioMapRssiSignal;
        double evaluationRssiSignal;
        for(int i = 0; i < maxIndex; i++){
            try{
                radioMapRssiSignal = radioMapSignals.get(i).getRssiSignalStrength();
                radioMapAddend = calculatePropagationWeight(radioMapRssiSignal);
            }catch(IndexOutOfBoundsException ex){
                radioMapAddend = 0.0;
            }
            try{
                evaluationRssiSignal = evaluationSignals.get(i).getRssiSignalStrength();
                evalAddend = calculatePropagationWeight(evaluationRssiSignal);
            }catch(IndexOutOfBoundsException ex){
                evalAddend = 0.0;
            }
            radioMapSignalSquareSum += Math.pow(radioMapAddend, 2);
            evaluationSignalSquareSum += Math.pow(evalAddend, 2);
            totalSum += radioMapAddend * evalAddend;
        }

        if(radioMapSignalSquareSum == 0.0 || evaluationSignalSquareSum == 0.0 || totalSum == 0.0){
            return 0.0;
        }

        double cosine = totalSum / (Math.sqrt(radioMapSignalSquareSum) * Math.sqrt(evaluationSignalSquareSum));
        cosine = Math.max(cosine, -1.0);
        cosine = Math.min(cosine, 1.0);

        double fi = 1 - Math.acos(cosine) / (Math.PI / 2);

        return fi;

    }

    public static double calculatePropagationWeight(double rssiSignal){

        double lowestConsideredRssiValue = ConfigContainer.LOWEST_RSSI_IN_SCALAR_MODE;
        double propagationExponent = ConfigContainer.RADIO_PROPAGATION_EXPONENT;

        if(rssiSignal <= lowestConsideredRssiValue){
            return 0.0;
        }

        return Math.pow(10, rssiSignal / (10.0 * propagationExponent));

    }

    public static WifiPositionResult smoothenWifiPosition(double smootheningFactor, WifiPositionResult currentResult, WifiPositionResult previousResult, double weight) {

        double factorOld = smootheningFactor;
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
