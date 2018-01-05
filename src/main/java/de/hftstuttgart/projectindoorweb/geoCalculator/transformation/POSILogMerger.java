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
     * constant-value for integer 3.
     */
    private static final int INT3 = 3;
    /**
     * constant-value for integer 4.
     */
    private static final int INT4 = 4;
    /**
     * constant-value for integer 4.
     */
    private static final int INT5 = 5;
    /**
     * constant-value for integer 6.
     */
    private static final int INT6 = 6;
    /**
     * constant-value for integer 7.
     */
    private static final int INT7 = 7;

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
                int building = Integer.valueOf(elements[INT3]);
                int floor = Integer.valueOf(elements[INT4]);

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
                && line.split(";").length == INT5;
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
    public static List<String> combineReferencePointsWithPOSILines(final List<String> posiLines, final File referenceFile,
                                                                   final Building b, final int width, final int height) {

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
            if (line[0].equals("POSI") && line.length == INT7) {
                LLPoint p = transformedPoints.get(i);
                line[INT3] = Double.toString(p.getCoords().getLatitude());
                line[INT4] = Double.toString(p.getCoords().getLongitude());
                line[INT5] = Integer.toString(p.getFloor());
                line[INT6] = Integer.toString(p.getBuilding());

                result.add(line[0] + ";" + line[1] + ";" + line[2] + ";" + line[INT3] + ";" + line[INT4] + ";" + line[INT5] + ";" + line[INT6] + ";");
            } else {
                result.add(posiLines.get(i));
            }
        }

        return result;
    }
}
