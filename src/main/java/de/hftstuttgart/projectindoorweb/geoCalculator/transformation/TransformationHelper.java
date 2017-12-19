package de.hftstuttgart.projectindoorweb.geoCalculator.transformation;

import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LLPoint;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LatLongCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LocalXYCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.XYPoint;
import de.hftstuttgart.projectindoorweb.persistence.entities.Building;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Iterator;

/**
 * Helper-Class for coordinate-Transformation.
 */
public final class TransformationHelper {

    /**
     * Private constructor to prevent creating an object.
     */
    private TransformationHelper() {

    }

    /**
     * Calculates the distance in meter between two points given in latitude / longitude.
     *
     * @param point1 first point
     * @param point2 second  point
     * @param sw     south-west corner point of the image
     * @param se     south-east corner pooint of the image
     * @return Distance between the two given points in meter
     */
    public static double getDistance(final LatLongCoord point1, final LatLongCoord point2,
                                     final LatLongCoord sw, final LatLongCoord se) {
        XYPoint p1 = Transformation.transformDataWGStoXY(new LLPoint("", point1, 0, 0), sw, se);
        XYPoint p2 = Transformation.transformDataWGStoXY(new LLPoint("", point2, 0, 0), sw, se);
        return Math.sqrt(Math.pow(p1.getCoords().x - p2.getCoords().x, 2) + Math.pow(p1.getCoords().y - p2.getCoords().y, 2));
    }

    /**
     * Calculates the scale-factor for the transformation between screen/pixel coordinates an a metrical coordinate system.
     *
     * @param picSizeInPixel Width or height of the picture/building in pixel
     * @param picSizeInMeter Width or height of the picture/building in meter
     * @return Scalefactor
     */
    public static double calculateScaleFactor(final double picSizeInPixel, final double picSizeInMeter) {
        return picSizeInMeter / picSizeInPixel;
    }

    /**
     * Extracts the picture size of a given Image.
     *
     * @param path Path to the image
     * @return Image-size in pixel
     * @throws IOException
     */
    public static Dimension getPictureSize(final String path) throws IOException {
        try (ImageInputStream in = ImageIO.createImageInputStream(path)) {
            final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                try {
                    reader.setInput(in);
                    return new Dimension(reader.getWidth(0), reader.getHeight(0));
                } finally {
                    reader.dispose();
                }
            }
            return null;
        }
    }

    /**
     * Calculates the south-east corner of the picture.
     *
     * @param center   Coordinates of the center of the image
     * @param angle    angle the image should be rotated, that the image is alligned correctly to the north
     * @param scale    scalefactor
     * @param imgSizeX width of the picture
     * @param imgSizeY height of the picture
     * @return coordinates of the south east corner
     */
    public static double[] calculateSouthEastCorner(final LatLongCoord center, final double angle,
                                                    final double scale, final int imgSizeX, final int imgSizeY) {
        LocalXYCoord centerXY = new LocalXYCoord(imgSizeX / 2, imgSizeY / 2);
        return pictToWGS(center, new LocalXYCoord(imgSizeX, imgSizeY), centerXY, angle, scale, imgSizeY);
    }

    /**
     * Calculates the south-west corner of the picture.
     *
     * @param center   Coordinates of the center of the image
     * @param angle    angle the image should be rotated, that the image is alligned correctly to the north
     * @param scale    scalefactor
     * @param imgSizeX width of the picture
     * @param imgSizeY height of the picture
     * @return coordinates of the south-west corner
     */
    public static double[] calculateSouthWestCorner(final LatLongCoord center, final double angle,
                                                    final double scale, final int imgSizeX, final int imgSizeY) {
        LocalXYCoord centerXY = new LocalXYCoord(imgSizeX / 2, imgSizeY / 2);
        return pictToWGS(center, new LocalXYCoord(0, imgSizeY), centerXY, angle, scale, imgSizeY);
    }

    /**
     * Calculates the north-east corner of the picture.
     *
     * @param center   Coordinates of the center of the image
     * @param angle    angle the image should be rotated, that the image is alligned correctly to the north
     * @param scale    scalefactor
     * @param imgSizeX width of the picture
     * @param imgSizeY height of the picture
     * @return coordinates of the north-east corner
     */
    public static double[] calculateNorthEastCorner(final LatLongCoord center, final double angle,
                                                    final double scale, final int imgSizeX, final int imgSizeY) {
        LocalXYCoord centerXY = new LocalXYCoord(imgSizeX / 2, imgSizeY / 2);
        return pictToWGS(center, new LocalXYCoord(imgSizeX, 0), centerXY, angle, scale, imgSizeY);
    }

    /**
     * Calculates the north-west corner of the picture.
     *
     * @param center   Coordinates of the center of the image
     * @param angle    angle the image should be rotated, that the image is aligned correctly to the north
     * @param scale    scalefactor
     * @param imgSizeX width of the picture
     * @param imgSizeY height of the picture
     * @return coordinates of the north-west corner
     */
    public static double[] calculateNorthWestCorner(final LatLongCoord center, final double angle,
                                                    final double scale, final int imgSizeX, final int imgSizeY) {
        LocalXYCoord centerXY = new LocalXYCoord(imgSizeX / 2, imgSizeY / 2);
        return pictToWGS(center, new LocalXYCoord(0, 0), centerXY, angle, scale, imgSizeY);
    }

    /**
     * Calculates the north west corner of the image from the corners of an building/structure.
     *
     * @param se            Coordinates of the south east corner of the building/structure
     * @param sw            Coordinates of the south west corner of the building/structure
     * @param ne            Coordinates of the north east corner of the building/structure
     * @param bp            basepoint in pixel, the XY-system has its origin in this point
     * @param buildingSizeX building/structure width in pixel
     * @param buildingSizeY building/strucutre height in pixel
     * @param imgSizeX      width of the picture in pixel
     * @param imgSizeY      height of the picture in pixel
     * @return coordinates of the north-west corner
     */
    public static double[] calculateNorthWestCorner(final LatLongCoord se, final LatLongCoord sw,
                                                    final LatLongCoord ne, final LocalXYCoord bp,
                                                    final double buildingSizeX, final double buildingSizeY,
                                                    final int imgSizeX, final int imgSizeY) {
        return TransformationHelper.pictToWGS(se, sw, ne, new LocalXYCoord(0, 0), buildingSizeX, buildingSizeY, imgSizeY, bp.x, bp.y);
    }

    /**
     * Calculates the north east corner of the image from the corners of an building/structure.
     *
     * @param se            Coordinates of the south east corner of the building/structure
     * @param sw            Coordinates of the south west corner of the building/structure
     * @param ne            Coordinates of the north east corner of the building/structure
     * @param bp            basepoint in pixel, the XY-system has its origin in this point
     * @param buildingSizeX building/structure width in pixel
     * @param buildingSizeY building/strucutre height in pixel
     * @param imgSizeX      width of the picture in pixel
     * @param imgSizeY      height of the picture in pixel
     * @return coordinates of the north-east corner
     */
    //Method to calculate the image Corner out of the building corners
    public static double[] calculateNorthEastCorner(final LatLongCoord se, final LatLongCoord sw,
                                                    final LatLongCoord ne, final LocalXYCoord bp,
                                                    final double buildingSizeX, final double buildingSizeY,
                                                    final int imgSizeX, final int imgSizeY) {
        return TransformationHelper.pictToWGS(se, sw, ne, new LocalXYCoord(imgSizeX, 0), buildingSizeX, buildingSizeY, imgSizeY, bp.x, bp.y);
    }

    /**
     * Calculates the south west corner of the image from the corners of an building/structure.
     *
     * @param se            Coordinates of the south east corner of the building/structure
     * @param sw            Coordinates of the south west corner of the building/structure
     * @param ne            Coordinates of the north east corner of the building/structure
     * @param bp            basepoint in pixel, the XY-system has its origin in this point
     * @param buildingSizeX building/structure width in pixel
     * @param buildingSizeY building/strucutre height in pixel
     * @param imgSizeX      width of the picture in pixel
     * @param imgSizeY      height of the picture in pixel
     * @return coordinates of the south-west corner
     */
    //Method to calculate the image Corner out of the building corners
    public static double[] calculateSouthWestCorner(final LatLongCoord se, final LatLongCoord sw,
                                                    final LatLongCoord ne, final LocalXYCoord bp,
                                                    final double buildingSizeX, final double buildingSizeY,
                                                    final int imgSizeX, final int imgSizeY) {
        return TransformationHelper.pictToWGS(se, sw, ne, new LocalXYCoord(0, imgSizeY), buildingSizeX, buildingSizeY, imgSizeY, bp.x, bp.y);
    }

    /**
     * Calculates the south east corner of the image from the corners of an building/structure.
     *
     * @param se            Coordinates of the south east corner of the building/structure
     * @param sw            Coordinates of the south west corner of the building/structure
     * @param ne            Coordinates of the north east corner of the building/structure
     * @param bp            basepoint in pixel, the XY-system has its origin in this point
     * @param buildingSizeX building/structure width in pixel
     * @param buildingSizeY building/strucutre height in pixel
     * @param imgSizeX      width of the picture in pixel
     * @param imgSizeY      height of the picture in pixel
     * @return coordinates of the south-east corner
     */
    //Method to calculate the image Corner out of the building corners
    public static double[] calculateSouthEastCorner(final LatLongCoord se, final LatLongCoord sw,
                                                    final LatLongCoord ne, final LocalXYCoord bp,
                                                    final double buildingSizeX, final double buildingSizeY,
                                                    final int imgSizeX, final int imgSizeY) {
        return TransformationHelper.pictToWGS(se, sw, ne, new LocalXYCoord(imgSizeX, imgSizeY), buildingSizeX, buildingSizeY, imgSizeY, bp.x, bp.y);
    }

    /**
     * Transforms one point from WGS84 to screen/pixel-coordinates.
     *
     * @param b         Building-object that contains the coordinates of the image corners
     * @param point     Coordinates in Latitude / Longitude that should be transformed
     * @param picWidth  width of the picture in pixel
     * @param picHeight height of the picture in pixel
     * @return pixel-coordinates
     */
    public static double[] wgsToPict(final Building b, final LatLongCoord point, final int picWidth, final int picHeight) {
        LatLongCoord se = new LatLongCoord(b.getSouthEast().getX(), b.getSouthEast().getY());
        LatLongCoord sw = new LatLongCoord(b.getSouthWest().getX(), b.getSouthWest().getY());
        LatLongCoord ne = new LatLongCoord(b.getNorthEast().getX(), b.getNorthEast().getY());
        XYPoint pXY = Transformation.transformDataWGStoXY(new LLPoint("", point, 0, 0), sw, se);
        double scaleX = calculateScaleFactor(picWidth, getDistance(se, sw, sw, se));
        double scaleY = calculateScaleFactor(picHeight, getDistance(se, ne, sw, se));

        XYPoint pPict = Transformation.transformDataXYtoPictXY(pXY, scaleX, scaleY, picHeight, 0, 0);
        double[] result = new double[2];
        result[0] = pPict.getCoords().x;
        result[1] = pPict.getCoords().y;
        return result;
    }

    /**
     * Transforms one point from WGS84 to metrical coordinate system.
     *
     * @param b     Building-object that contains the coordinates of the image corners
     * @param point Coordinates in Latitude / Longitude that should be transformed
     * @return XY-coordinates
     */
    public static double[] wgsToXY(final Building b, final LatLongCoord point) {
        LatLongCoord se = new LatLongCoord(b.getSouthEast().getX(), b.getSouthEast().getY());
        LatLongCoord sw = new LatLongCoord(b.getSouthWest().getX(), b.getSouthWest().getY());
        XYPoint pXY = Transformation.transformDataWGStoXY(new LLPoint("", point, 0, 0), sw, se);
        double[] result = new double[2];
        result[0] = pXY.getCoords().x;
        result[1] = pXY.getCoords().y;
        return result;
    }

    /**
     * Transforms one point from  metrical coordinate system to WGS84.
     *
     * @param b     Building-object that contains the coordinates of the image corners
     * @param point Coordinates in XY that should be transformed
     * @return WGS84-coordinates
     */
    public static double[] xyToWGS(final Building b, final LocalXYCoord point) {
        LatLongCoord se = new LatLongCoord(b.getSouthEast().getX(), b.getSouthEast().getY());
        LatLongCoord sw = new LatLongCoord(b.getSouthWest().getX(), b.getSouthWest().getY());
        LLPoint pLL = Transformation.transformDataXYtoWGS(new XYPoint("", point, 0, 0), sw, se);
        double[] result = new double[2];
        result[0] = pLL.getCoords().latitude;
        result[1] = pLL.getCoords().longitude;
        return result;
    }

    /**
     * Transforms a point from xy-coordinate system into pixel/screen coordinates.
     *
     * @param b         Building-object that contains the coordinates of the image corners
     * @param point     Coordinates in XY that should be transformed
     * @param picWidth  width of the picture in pixel
     * @param picHeight height of the picture in pixel
     * @return pixel/screen-coordinates
     */
    public static double[] xyToPict(final Building b, final LocalXYCoord point, final int picWidth, final int picHeight) {
        LatLongCoord se = new LatLongCoord(b.getSouthEast().getX(), b.getSouthEast().getY());
        LatLongCoord sw = new LatLongCoord(b.getSouthWest().getX(), b.getSouthWest().getY());
        LatLongCoord ne = new LatLongCoord(b.getNorthEast().getX(), b.getNorthEast().getY());

        double scaleX = calculateScaleFactor(picWidth, getDistance(se, sw, sw, se));
        double scaleY = calculateScaleFactor(picHeight, getDistance(se, ne, sw, se));

        XYPoint pPict = Transformation.transformDataXYtoPictXY(new XYPoint("", point, 0, 0), scaleX, scaleY, picHeight, 0, 0);
        double[] result = new double[2];
        result[0] = pPict.getCoords().x;
        result[1] = pPict.getCoords().y;
        return result;
    }

    /**
     * Transforms one point from pixel/screen coordinates into XY-coordinates.
     *
     * @param b         Building-object that contains the coordinates of the image corners
     * @param point     Coordinates in picel/screen-coordinates that should be transformed
     * @param picWidth  width of the picture in pixel
     * @param picHeight height of the picture in pixel
     * @return XY-coordinates
     */
    public static double[] pictToXY(final Building b, final LocalXYCoord point, final int picWidth, final int picHeight) {
        LatLongCoord se = new LatLongCoord(b.getSouthEast().getX(), b.getSouthEast().getY());
        LatLongCoord sw = new LatLongCoord(b.getSouthWest().getX(), b.getSouthWest().getY());
        LatLongCoord ne = new LatLongCoord(b.getNorthEast().getX(), b.getNorthEast().getY());

        double scaleX = calculateScaleFactor(picWidth, getDistance(sw, se, sw, se));
        double scaleY = calculateScaleFactor(picHeight, getDistance(sw, ne, sw, se));

        XYPoint pXY = Transformation.transformDataPictXYtoXY(new XYPoint("", point, 0, 0), scaleX, scaleY, picHeight, 0, 0);

        double[] result = new double[2];
        result[0] = pXY.getCoords().x;
        result[1] = pXY.getCoords().y;
        return result;
    }

    /**
     * Transforms one point from pixel/screen coordinates into WGS84-coordinates.
     *
     * @param b         Building-object that contains the coordinates of the image corners
     * @param point     Coordinates in pixel/screen-coordinates that should be transformed
     * @param picWidth  width of the picture in pixel
     * @param picHeight height of the picture in pixel
     * @return WGS84-coordinates
     */
    public static double[] pictToWGS(final Building b, final LocalXYCoord point, final int picWidth, final int picHeight) {
        LatLongCoord se = new LatLongCoord(b.getSouthEast().getX(), b.getSouthEast().getY());
        LatLongCoord sw = new LatLongCoord(b.getSouthWest().getX(), b.getSouthWest().getY());
        LatLongCoord ne = new LatLongCoord(b.getNorthEast().getX(), b.getNorthEast().getY());

        double scaleX = calculateScaleFactor(picWidth, getDistance(se, sw, sw, se));
        double scaleY = calculateScaleFactor(picHeight, getDistance(se, ne, sw, se));

        XYPoint pXY = Transformation.transformDataPictXYtoXY(new XYPoint("", point, 0, 0), scaleX, scaleY, picHeight, 0, 0);

        LLPoint pLL = Transformation.transformDataXYtoWGS(pXY, sw, se);

        double[] result = new double[2];
        result[0] = pLL.getCoords().latitude;
        result[1] = pLL.getCoords().longitude;
        return result;
    }

    /**
     * Transforms one point from pixel/screen coordinates into WGS84-coordinates.
     *
     * @param center    latitude / longitude of the center of the image
     * @param point     Coordinates in pixel/screen-coordinates that should be transformed
     * @param bp        basepoint in pixel, the XY-system has its origin in this point
     * @param angle     angle the image should be rotated, that the image is aligned correctly to the north
     * @param scale     scale-factor so transform between pixel-system and XY-system
     * @param picHeight height of the image
     * @return WGS84-coordinates
     */
    public static double[] pictToWGS(final LatLongCoord center, final LocalXYCoord point, final LocalXYCoord bp,
                                     final double angle, final double scale, final double picHeight) {

        XYPoint pXY = Transformation.transformDataPictXYtoXY(new XYPoint("", point, 0, 0), scale, scale, picHeight, bp.x, bp.y);

        LLPoint pLL = Transformation.transformDataXYtoWGS(pXY, center, angle);

        double[] result = new double[2];
        result[0] = pLL.getCoords().latitude;
        result[1] = pLL.getCoords().longitude;
        return result;
    }

    /**
     * Transforms one point from pixel/screen coordinates into WGS84-coordinates.
     *
     * @param se        south east corner of the image
     * @param sw        south west corner of the image
     * @param ne        north east corner of the image
     * @param point     Coordinates in pixel/screen-coordinates that should be transformed
     * @param picWidth  width of the building in pixel
     * @param picHeight height of the building in pixel
     * @param picSizeY  height of the picture in pixel
     * @param bpX       X-ccordinate of the basepoint in pixel, the XY-system has its origin in this point
     * @param bpY       Y-ccordinate of the basepoint in pixel, the XY-system has its origin in this point
     * @return WGS84-coordinates
     */
    private static double[] pictToWGS(final LatLongCoord se, final LatLongCoord sw, final LatLongCoord ne,
                                      final LocalXYCoord point, final double picWidth, final double picHeight,
                                      final double picSizeY, final double bpX, final double bpY) {

        double scaleX = calculateScaleFactor(picWidth, getDistance(se, sw, sw, se));
        double scaleY = calculateScaleFactor(picHeight, getDistance(se, ne, sw, se));

        XYPoint pXY = Transformation.transformDataPictXYtoXY(new XYPoint("", point, 0, 0), scaleX, scaleY, picSizeY, bpX, bpY);

        LLPoint pLL = Transformation.transformDataXYtoWGS(pXY, sw, se);

        double[] result = new double[2];
        result[0] = pLL.getCoords().latitude;
        result[1] = pLL.getCoords().longitude;
        return result;
    }
}
