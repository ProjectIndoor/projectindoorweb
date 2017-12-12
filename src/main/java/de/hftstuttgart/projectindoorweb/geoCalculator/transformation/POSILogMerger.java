package de.hftstuttgart.projectindoorweb.geoCalculator.transformation;

import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LLPoint;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LatLongCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LocalXYCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.XYPoint;
import de.hftstuttgart.projectindoorweb.persistence.entities.Building;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class POSILogMerger {

    private static List readValuesFromFile(String filePath) {
        List parsedDataList = new ArrayList<>();
        try {
            Files.readAllLines(Paths.get(filePath)).stream().filter(s -> {

                return isValidLine(s);

            }).map(s -> {

                String[] elements = s.split(";");
                String pointNumber = elements[0];
                double coord1 = Double.valueOf(elements[1]);
                double coord2 = Double.valueOf(elements[2]);
                double building = Double.valueOf(elements[3]);
                double floor = Double.valueOf(elements[4]);

                return new XYPoint(pointNumber, new LocalXYCoord(coord1,coord2), building, floor);

            }).collect(Collectors.toCollection(() -> parsedDataList));
            return parsedDataList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isValidLine(String line) {
        return !line.contains("%")
                && !line.isEmpty()
                && line.split(";").length == 5;
    }

    public static List<String> combineReferencePointsWithPOSILines(List<String> POSILines, String referenceFile, Building b, int width, int height)
    {

        List<XYPoint> Points = readValuesFromFile(referenceFile);
        List<LLPoint> transformedPoints =  new ArrayList<LLPoint>();
        for (int j = 0; j < Points.size(); j++) {
            double[] wgs = TransformationHelper.pictToWGS(b,Points.get(j).getCoords(),width,height);
            transformedPoints.add(new LLPoint(Points.get(j).getPointNumber(),new LatLongCoord(wgs[0],wgs[1]),Points.get(j).getBuilding(),Points.get(j).getFloor()));
        }

        if (POSILines.size() != transformedPoints.size()) {
            return null;
        }

        List<String> result = new ArrayList<String>();
        for (int i = 0; i< POSILines.size(); i++) {
            String[] line = POSILines.get(i).split(";");
            if (line[0].equals("POSI")&& line.length == 7) {
                LLPoint p = (LLPoint) transformedPoints.get(i);
                line[3] = Double.toString(p.getCoords().latitude);
                line[4] = Double.toString(p.getCoords().longitude);
                line[5] = Double.toString(p.getFloor());
                line[6] = Double.toString(p.getBuilding());

                result.add(line[0]+";"+line[1]+";"+line[2]+";"+line[3]+";"+line[4]+";"+line[5]+";"+line[6]+";");
            }else {
                result.add(POSILines.get(i));
            }
        }
        return  result;
    }
}
