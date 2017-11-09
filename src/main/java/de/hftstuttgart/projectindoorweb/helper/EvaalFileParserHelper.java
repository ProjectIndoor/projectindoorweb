package de.hftstuttgart.projectindoorweb.helper;

import de.hftstuttgart.projectindoorweb.config.ConfigContainer;
import de.hftstuttgart.projectindoorweb.maths.LatLongCoord;
import de.hftstuttgart.projectindoorweb.maths.LocXYZ;
import de.hftstuttgart.projectindoorweb.maths.MyGeoMath;
import de.hftstuttgart.projectindoorweb.pojos.PosiReference;
import de.hftstuttgart.projectindoorweb.pojos.Position;
import de.hftstuttgart.projectindoorweb.pojos.RadiomapElement;
import de.hftstuttgart.projectindoorweb.pojos.RssiReading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaalFileParserHelper {



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
