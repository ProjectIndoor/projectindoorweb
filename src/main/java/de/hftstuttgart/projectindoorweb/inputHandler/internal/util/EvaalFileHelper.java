package de.hftstuttgart.projectindoorweb.inputHandler.internal.util;

import de.hftstuttgart.projectindoorweb.geoCalculator.LatLongCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.LocXYZ;
import de.hftstuttgart.projectindoorweb.geoCalculator.MyGeoMath;
import de.hftstuttgart.projectindoorweb.persistence.pojo.PosiReference;
import de.hftstuttgart.projectindoorweb.persistence.pojo.Position;
import de.hftstuttgart.projectindoorweb.persistence.pojo.RadiomapElement;
import de.hftstuttgart.projectindoorweb.persistence.pojo.RssiReading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaalFileHelper {



    public static List<RadiomapElement> mergeSimilarPositions(List<RadiomapElement> unmergedElements){

        List<RadiomapElement> result = new ArrayList<>();

        boolean unique;
        Position a;
        Position b;
        for (RadiomapElement outerElement: unmergedElements) {
            unique = true;
            for(int i = 0; i < result.size(); i++){
                RadiomapElement innerElement = result.get(i);
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

        return result;

    }

    public static RadiomapElement mergeRadioMapElements(RadiomapElement a, RadiomapElement b){


        /*
        * "SomeAverage" is not a joke (well, sort of): It is not apparent in the prototype what the "average" saved with
        * every Posi reference is supposed to mean, so their sum is, quite literally, just "some average".
        * */
        int someAverage = a.getPosiReference().getAvgNumber() + b.getPosiReference().getAvgNumber();
        Map<String, Double[]> averagedMacSignalStrength = new HashMap<>();

        RadiomapElement result;
        String macAddress;
        for(RssiReading reading : a.getRssiReadings()){
            macAddress = reading.getMacAddress();
            if(!averagedMacSignalStrength.containsKey(macAddress)){
                averagedMacSignalStrength.put(macAddress, null);
            }
        }

        for(RssiReading reading : b.getRssiReadings()){
            macAddress = reading.getMacAddress();
            if(!averagedMacSignalStrength.containsKey(macAddress)){
                averagedMacSignalStrength.put(macAddress, null);
            }
        }

        double readingA;
        double readingB;
        for(String someOtherMacAddress : averagedMacSignalStrength.keySet()){
            readingA = retrieveSignalStrengthByMacAddress(a.getRssiReadings(), someOtherMacAddress);
            readingB = retrieveSignalStrengthByMacAddress(b.getRssiReadings(), someOtherMacAddress);
            averagedMacSignalStrength.put(someOtherMacAddress, new Double[]{readingA, readingB});
        }

        List<RssiReading> mergedRadioMapElements = mergeRssiReadings(averagedMacSignalStrength, a, b);
        PosiReference mergedPosiReference = mergePosiReferences(a.getPosiReference(), b.getPosiReference());

        return new RadiomapElement(null, mergedPosiReference, mergedRadioMapElements);

    }


    private static PosiReference mergePosiReferences(PosiReference a, PosiReference b){

        Position positionA = a.getReferencePosition();
        Position positionB = b.getReferencePosition();
        LocXYZ wrapperA = new LocXYZ(positionA.getLatitude(), positionA.getLongitude(), positionA.getHeight());
        LocXYZ wrapperB = new LocXYZ(positionB.getLatitude(), positionB.getLongitude(), positionB.getHeight());

        wrapperA = wrapperA.mul(a.getAvgNumber());
        wrapperB = wrapperB.mul(b.getAvgNumber());

        LocXYZ wrapperMerged = wrapperA.add(wrapperB);
        wrapperMerged = wrapperMerged.mul(1.0d / (a.getAvgNumber() + b.getAvgNumber()));

        return new PosiReference(-1, a.getAvgNumber() + b.getAvgNumber(),
                new Position(wrapperMerged.x, wrapperMerged.y, wrapperMerged.z), -1, -1);

    }

    private static List<RssiReading> mergeRssiReadings(Map<String, Double[]> averagedMacSignalStrenghts, RadiomapElement a, RadiomapElement b){

        List<RssiReading> result = new ArrayList<>(averagedMacSignalStrenghts.size());

        double readingA;
        double readingB;
        double merged;
        int avgA = a.getPosiReference().getAvgNumber();
        int avgB = b.getPosiReference().getAvgNumber();
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
            result.add(new RssiReading(0.0, null, macAddress,
                    merged, true));
        }

        return result;

    }



    private static double retrieveSignalStrengthByMacAddress(List<RssiReading> rssiReadings, String macAddress){

        double result = 0.0;
        for (RssiReading reading: rssiReadings) {
            if(macAddress.equals(reading.getMacAddress())){
                result = reading.getRssiSignalStrength();
                break;
            }
        }

        return result;

    }



    public static List<RssiReading> retrieveAveragedReadings(List<RssiReading> relevantReadings){


        Map<String, List<RssiReading>> rawReadingsForMacs = new HashMap<>();
        Map<String, RssiReading> averagedReadingForMac = new HashMap<>();

        for(RssiReading reading : relevantReadings){
            String macAddress = reading.getMacAddress();
            if(rawReadingsForMacs.containsKey(macAddress)){
                rawReadingsForMacs.get(macAddress).add(reading);
            }else{
                List<RssiReading> values = new ArrayList<>();
                values.add(reading);
                rawReadingsForMacs.put(macAddress, values);
            }
        }

        int numOccurences;
        double sumSignalStrengths;
        List<RssiReading> readingsForMac;
        for(String macAddress : rawReadingsForMacs.keySet()){
            readingsForMac = rawReadingsForMacs.get(macAddress);
            numOccurences = readingsForMac.size();
            sumSignalStrengths = 0;
            for(RssiReading reading : readingsForMac){
                sumSignalStrengths += reading.getRssiSignalStrength();
            }
            averagedReadingForMac.put(macAddress, new RssiReading(0.0, null, macAddress,
                    (sumSignalStrengths / numOccurences), true));
        }

        return new ArrayList<>(averagedReadingForMac.values());


    }

    public static List<RssiReading> retrieveRssiReadingsForPosiReference(PosiReference posiReference, List<RssiReading> rssiReadings){

        List<RssiReading> result = new ArrayList<>();

        double intervalStart = posiReference.getIntervalStart();
        double intervalEnd = posiReference.getIntervalEnd();

        for (RssiReading rssiReading: rssiReadings) {
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

        for (int i = 0; i < posiLines.size(); i++) {
            currentLineElements = posiLines.get(i).split(";");
            intervalEnd = Double.parseDouble(currentLineElements[1]);
            positionInSourceFile = Integer.parseInt(currentLineElements[2]);
            latitude = Double.parseDouble(currentLineElements[3]);
            longitude = Double.parseDouble(currentLineElements[4]);
            floorId = Integer.parseInt(currentLineElements[5]);
            buildingId = Integer.parseInt(currentLineElements[6]);
            referencePosition = retrieveTransformedReferencePosition(latitude, longitude, floorId);
            result.add(new PosiReference(positionInSourceFile, avgNumber, referencePosition, intervalStart, intervalEnd));
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

    public static List<RssiReading> assembleRssiReadings(List<String> rssiLines) {

        List<RssiReading> result = new ArrayList<>(rssiLines.size());

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
            result.add(new RssiReading(appTimestamp, networkName, macAddress, rssiSignalStrength, false));
        }


        return result;

    }

    public static Position retrieveTransformedReferencePosition(double latitude, double longitude, int floor) {

        LatLongCoord untransformedPosition = new LatLongCoord(latitude, longitude);
        LocXYZ transformedPosition = new LocXYZ(MyGeoMath.ll2xy(untransformedPosition, ConfigContainer.BASE_POSITION,
                ConfigContainer.ANGLE_RAD), floor * ConfigContainer.FLOOR_HEIGHT);
        return new Position(transformedPosition.x, transformedPosition.y, transformedPosition.z);

    }




}
