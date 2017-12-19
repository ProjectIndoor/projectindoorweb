package de.hftstuttgart.projectindoorweb.geoCalculator.transformation;

import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LLPoint;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LatLongCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LocalXYCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.XYPoint;
import de.hftstuttgart.projectindoorweb.persistence.entities.Building;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that merges an Evaal-Logilfe with empty POSI-entries with a given list of reference points.
 */
public final class POSILogMerger {

    /**
     * Private constructor to prevent creating an object.
     */
    private POSILogMerger() {
    }

    /**
     * Reads the file that contains the reference-points in pixel-coordinates.
     *
     * @param file File that contains the reference-points
     * @return Point-list with all reference-points
     */
    private static List readValuesFromFile(final File file) {
        List parsedDataList = new ArrayList<>();
        try {
            Files.readAllLines(file.toPath()).stream().filter(s -> {
                return isValidLine(s);
            }).map(s -> {

                String[] elements = s.split(";");
                String pointNumber = elements[0];
                double coord1 = Double.valueOf(elements[1]);
                double coord2 = Double.valueOf(elements[2]);
                int building = Integer.valueOf(elements[3]);
                int floor = Integer.valueOf(elements[4]);

                return new XYPoint(pointNumber, new LocalXYCoord(coord1, coord2), building, floor);

            }).collect(Collectors.toCollection(() -> parsedDataList));
            return parsedDataList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Checks if the line is valid.
     *
     * @param line Line to check
     * @return returns true if the line is correct
     */
    private static boolean isValidLine(final String line) {
        return !line.contains("%")
                && !line.isEmpty()
                && line.split(";").length == 5;
    }

    /**
     * Merges the POSI-Lines with the given reference-point information.
     *
     * @param posiLines     all POSI-Lines of one EVaal-File
     * @param referenceFile File with the pixel-coordinates of the reference points
     * @param b             Building-object, that contains the coordinates of the corners of the image/picture
     * @param width         width of the picture
     * @param height        height of the picture
     * @return List with all POSI-Lines with filled in coordinates, floor and building informations
     */
    public static List<String> combineReferencePointsWithPOSILines(final List<String> posiLines, final File referenceFile, final Building b, final int width, final int height) {

        List<XYPoint> points = readValuesFromFile(referenceFile);
        List<LLPoint> transformedPoints = new ArrayList<LLPoint>();
        for (int j = 0; j < points.size(); j++) {
            double[] wgs = TransformationHelper.pictToWGS(b, points.get(j).getCoords(), width, height);
            transformedPoints.add(new LLPoint(points.get(j).getPointNumber(), new LatLongCoord(wgs[0], wgs[1]), points.get(j).getBuilding(), points.get(j).getFloor()));
        }

        if (posiLines.size() != transformedPoints.size()) {
            return null;
        }

        List<String> result = new ArrayList<String>();
        for (int i = 0; i < posiLines.size(); i++) {
            String[] line = posiLines.get(i).split(";");
            if (line[0].equals("POSI") && line.length == 7) {
                LLPoint p = transformedPoints.get(i);
                line[3] = Double.toString(p.getCoords().latitude);
                line[4] = Double.toString(p.getCoords().longitude);
                line[5] = Double.toString(p.getFloor());
                line[6] = Double.toString(p.getBuilding());

                result.add(line[0] + ";" + line[1] + ";" + line[2] + ";" + line[3] + ";" + line[4] + ";" + line[5] + ";" + line[6] + ";");
            } else {
                result.add(posiLines.get(i));
            }
        }
        return result;
    }
}
