package de.hftstuttgart.projectindoorweb.inputHandler.internal.util;

import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LatLongCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LocXYZ;
import de.hftstuttgart.projectindoorweb.geoCalculator.MyGeoMath;
import de.hftstuttgart.projectindoorweb.persistence.entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogFileHelper {

    //TODO This method is now accessed from a completely different package -- is there a more elegant way?
    public static RadioMap mergeRadioMapsBySimilarPositions(List<RadioMap> radioMaps){

        List<RadioMapElement> result = new ArrayList<>();
        List<RadioMapElement> unmergedElements = new ArrayList<>();

        for (RadioMap radioMap:
                radioMaps) {
            unmergedElements.addAll(radioMap.getRadioMapElements());
        }
        boolean unique;
        Position a;
        Position b;
        for (RadioMapElement outerElement: unmergedElements) {
            unique = true;
            for(int i = 0; i < result.size(); i++){
                RadioMapElement innerElement = result.get(i);
                a = innerElement.getPosiReference().getReferencePosition();
                b = outerElement.getPosiReference().getReferencePosition();
                if(MathHelper.retrievePositionDistance(a, b) <= ConfigContainer.SIMILAR_POSITION_THRESHOLD_METERS){
                    unique = false;
                    result.set(i, mergeRadioMapElements(innerElement, outerElement));
                    break;
                }
            }
            if(unique){
                result.add(outerElement);
            }
        }

        return new RadioMap(result);

    }

    public static RadioMapElement mergeRadioMapElements(RadioMapElement a, RadioMapElement b){

        int someAverage = a.getPosiReference().getAvgNumber() + b.getPosiReference().getAvgNumber();
        Map<String, Double[]> averagedMacSignalStrength = new HashMap<>();

        RadioMapElement result;
        String macAddress;
        for(RssiSignal signal : a.getRssiSignals()){
            macAddress = signal.getWifiAccessPoint().getMacAddress();
            if(!averagedMacSignalStrength.containsKey(macAddress)){
                averagedMacSignalStrength.put(macAddress, null);
            }
        }

        for(RssiSignal signal : b.getRssiSignals()){
            macAddress = signal.getWifiAccessPoint().getMacAddress();
            if(!averagedMacSignalStrength.containsKey(macAddress)){
                averagedMacSignalStrength.put(macAddress, null);
            }
        }

        double readingA;
        double readingB;
        for(String someOtherMacAddress : averagedMacSignalStrength.keySet()){
            readingA = retrieveSignalStrengthByMacAddress(a.getRssiSignals(), someOtherMacAddress);
            readingB = retrieveSignalStrengthByMacAddress(b.getRssiSignals(), someOtherMacAddress);
            averagedMacSignalStrength.put(someOtherMacAddress, new Double[]{readingA, readingB});
        }

        List<RssiSignal> mergedRadioMapElements = mergeRssiSignals(averagedMacSignalStrength, a, b);
        PosiReference mergedPosiReference = mergePosiReferences(a.getPosiReference(), b.getPosiReference());

        return new RadioMapElement(mergedPosiReference, mergedRadioMapElements);

    }


    private static PosiReference mergePosiReferences(PosiReference a, PosiReference b){

        Position positionA = a.getReferencePosition();
        Position positionB = b.getReferencePosition();
        LocXYZ wrapperA = new LocXYZ(positionA.getX(), positionA.getY(), positionA.getZ());
        LocXYZ wrapperB = new LocXYZ(positionB.getX(), positionB.getY(), positionB.getZ());

        wrapperA = wrapperA.mul(a.getAvgNumber());
        wrapperB = wrapperB.mul(b.getAvgNumber());

        LocXYZ wrapperMerged = wrapperA.add(wrapperB);
        wrapperMerged = wrapperMerged.mul(1.0d / (a.getAvgNumber() + b.getAvgNumber()));

        return new PosiReference(-1, a.getAvgNumber() + b.getAvgNumber(),
                new Position(wrapperMerged.x, wrapperMerged.y, wrapperMerged.z, false),
                -1, -1, null);

    }

    private static List<RssiSignal> mergeRssiSignals(Map<String, Double[]> averagedMacSignalStrenghts,
                                                      RadioMapElement a, RadioMapElement b){

        List<RssiSignal> result = new ArrayList<>(averagedMacSignalStrenghts.size());

        double readingA;
        double readingB;
        double merged;
        int avgA = a.getPosiReference().getAvgNumber();
        int avgB = b.getPosiReference().getAvgNumber();
        WifiAccessPoint accessPoint;
        for(String macAddress : averagedMacSignalStrenghts.keySet()){
            readingA = averagedMacSignalStrenghts.get(macAddress)[0];
            readingB = averagedMacSignalStrenghts.get(macAddress)[1];
            if(readingA != 0.0 && readingB != 0.0){
                merged = (readingA * avgA + readingB * avgB) / (avgA + avgB);
            }else if(readingA == 0.0){
                merged = readingB;
            }else{
                merged = readingA;
            }
            accessPoint = new WifiAccessPoint(macAddress);
            result.add(new RssiSignal(0.0, merged, true, accessPoint));
        }

        return result;

    }



    private static double retrieveSignalStrengthByMacAddress(List<RssiSignal> rssiSignals, String macAddress){

        double result = 0.0;
        for (RssiSignal signal: rssiSignals) {
            if(macAddress.equals(signal.getWifiAccessPoint().getMacAddress())){
                result = signal.getRssiSignalStrength();
                break;
            }
        }

        return result;

    }



    public static List<RssiSignal> retrieveAveragedReadings(List<RssiSignal> relevantReadings){


        Map<String, List<RssiSignal>> rawReadingsForMacs = new HashMap<>();
        Map<String, RssiSignal> averagedReadingForMac = new HashMap<>();

        for(RssiSignal signal : relevantReadings){
            String macAddress = signal.getWifiAccessPoint().getMacAddress();
            if(rawReadingsForMacs.containsKey(macAddress)){
                rawReadingsForMacs.get(macAddress).add(signal);
            }else{
                List<RssiSignal> values = new ArrayList<>();
                values.add(signal);
                rawReadingsForMacs.put(macAddress, values);
            }
        }

        int numOccurences;
        double sumSignalStrengths;
        List<RssiSignal> readingsForMac;
        for(String macAddress : rawReadingsForMacs.keySet()){
            readingsForMac = rawReadingsForMacs.get(macAddress);
            numOccurences = readingsForMac.size();
            sumSignalStrengths = 0;
            for(RssiSignal signal : readingsForMac){
                sumSignalStrengths += signal.getRssiSignalStrength();
            }
            averagedReadingForMac.put(macAddress, new RssiSignal(0.0, (sumSignalStrengths / numOccurences), true, new WifiAccessPoint(macAddress)));
        }

        return new ArrayList<>(averagedReadingForMac.values());


    }

    public static List<RssiSignal> retrieveRssiReadingsForPosiReference(PosiReference posiReference, List<RssiSignal> rssiReadings){

        List<RssiSignal> result = new ArrayList<>();

        double intervalStart = posiReference.getIntervalStart();
        double intervalEnd = posiReference.getIntervalEnd();

        for (RssiSignal rssiReading: rssiReadings) {
            if(rssiReading.getAppTimestamp() >= intervalStart && rssiReading.getAppTimestamp() < intervalEnd){
                result.add(rssiReading);
            }else if(rssiReading.getAppTimestamp() >= intervalEnd){
                break;
            }

        }

        return result;

    }


    public static List<PosiReference> assemblePosiReferences(List<String> posiLines) {

        List<PosiReference> result = new ArrayList<>(posiLines.size());
        String[] currentLineElements;
        double intervalEnd;
        double intervalStart = 0;
        int positionInSourceFile;
        int avgNumber = 1;
        double latitude;
        double longitude;
        int floorId;
        int buildingId;
        Position referencePosition;
        Floor floor;

        for (int i = 0; i < posiLines.size(); i++) {
            currentLineElements = posiLines.get(i).split(";");
            intervalEnd = Double.parseDouble(currentLineElements[1]);
            positionInSourceFile = Integer.parseInt(currentLineElements[2]);
            latitude = Double.parseDouble(currentLineElements[3]);
            longitude = Double.parseDouble(currentLineElements[4]);
            floorId = Integer.parseInt(currentLineElements[5]);
            floor = new Floor(floorId);
            buildingId = Integer.parseInt(currentLineElements[6]);
            referencePosition = retrieveTransformedReferencePosition(latitude, longitude, floorId);
            result.add(new PosiReference(positionInSourceFile, avgNumber, referencePosition, intervalStart, intervalEnd, floor));
            intervalStart = intervalEnd;
        }

        return result;

    }

    public static List<PosiReference> shiftPosiReferenceIntervals(List<PosiReference> unshiftedPosiReferences) {

        PosiReference current;
        PosiReference next;

        current = unshiftedPosiReferences.get(0);
        next = unshiftedPosiReferences.get(1);

        double formerIntervalEnd = (current.getIntervalEnd() + next.getIntervalEnd()) / 2;
        current.setIntervalEnd(formerIntervalEnd);

        for (int i = 1; i < unshiftedPosiReferences.size() - 1; i++) {
            current = unshiftedPosiReferences.get(i);
            current.setIntervalStart(formerIntervalEnd);
            next = unshiftedPosiReferences.get(i + 1);
            formerIntervalEnd = (current.getIntervalEnd() + next.getIntervalEnd()) / 2;
            current.setIntervalEnd(formerIntervalEnd);
        }

        current = unshiftedPosiReferences.get(unshiftedPosiReferences.size() - 1);
        current.setIntervalStart(formerIntervalEnd);
        current.setIntervalEnd(ConfigContainer.POSI_MAX_INTERVAL_END);

        List<PosiReference> shiftedPosiReferences = new ArrayList<>(unshiftedPosiReferences);
        return shiftedPosiReferences;

    }

    public static List<RssiSignal> assembleRssiSignals(List<String> rssiLines) {

        List<RssiSignal> result = new ArrayList<>(rssiLines.size());

        String[] lineElements;
        double appTimestamp;
        String networkName;
        String macAddress;
        double rssiSignalStrength;
        for (String line: rssiLines) {
            lineElements = line.split(";");
            appTimestamp = Double.parseDouble(lineElements[1]);
            networkName = lineElements[3];
            macAddress = lineElements[4];
            rssiSignalStrength = Double.parseDouble(lineElements[5]);
            result.add(new RssiSignal(appTimestamp, rssiSignalStrength, false, new WifiAccessPoint(macAddress)));

        }


        return result;

    }

    public static Position retrieveTransformedReferencePosition(double latitude, double longitude, int floor) {

        LatLongCoord untransformedPosition = new LatLongCoord(latitude, longitude);
        LocXYZ transformedPosition = new LocXYZ(MyGeoMath.ll2xy(untransformedPosition, ConfigContainer.BASE_POSITION,
                ConfigContainer.ANGLE_RAD), floor * ConfigContainer.FLOOR_HEIGHT);
        return new Position(transformedPosition.x, transformedPosition.y, transformedPosition.z, false);

    }




}
