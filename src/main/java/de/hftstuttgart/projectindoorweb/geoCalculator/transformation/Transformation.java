package de.hftstuttgart.projectindoorweb.geoCalculator.transformation;

import de.hftstuttgart.projectindoorweb.geoCalculator.MyGeoMath;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LLPoint;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LatLongCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LocalXYCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.XYPoint;

/**
 * Class for transformations between different coordinate-systems (Screen/XY/WGS84).
 */
public final class Transformation {

    /**
     * Private constructor to prevent creating an object.
     */
    private Transformation() {

    }

    /**
     * Method to transform one point from XY-System to WGS84-System
     *
     * @param point point to transform in XY-System
     * @param sw    south-west corner point of the image
     * @param se    south-east corner pooint of the image
     * @return point with WGS84-Coordinates
     */
    public static LLPoint transformDataXYtoWGS(XYPoint point, LatLongCoord sw, LatLongCoord se) {
        return new LLPoint(point.getPointNumber(), MyGeoMath.xy2ll(new LocalXYCoord(point.getCoords().x, point.getCoords().y), sw, se), point.getBuilding(), point.getFloor());
    }

    /**
     * Method to transform one point from WGS84-System to XY-System
     *
     * @param point point to transform in WGS84-system
     * @param sw    south-west corner point of the image
     * @param se    south-east corner pooint of the image
     * @return point with XY-Coordinates
     */
    public static XYPoint transformDataWGStoXY(LLPoint point, LatLongCoord sw, LatLongCoord se) {
        return new XYPoint(point.getPointNumber(), MyGeoMath.ll2xy(new LatLongCoord(point.getCoords().latitude, point.getCoords().longitude), sw, se), point.getBuilding(), point.getFloor());
    }

    /**
     * Method to transform one point from XY-System to WGS84-System
     *
     * @param point point to transform in XY-System
     * @param bp    basepoint in pixel, the XY-system has its origin in this point
     * @param angle angle the image should be rotated, that the image is aligned correctly to the north
     * @return point with WGS84-Coordinates
     */
    public static LLPoint transformDataXYtoWGS(XYPoint point, LatLongCoord bp, double angle) {
        return new LLPoint(point.getPointNumber(), MyGeoMath.xy2ll(new LocalXYCoord(point.getCoords().x, point.getCoords().y), bp, angle), point.getBuilding(), point.getFloor());
    }

    /**
     * Method to transform one point from WGS84-System to XY-System
     *
     * @param point point to transform in WGS84-system
     * @param bp    basepoint in pixel, the XY-system has its origin in this point
     * @param angle angle the image should be rotated, that the image is aligned correctly to the north
     * @return point with XY-Coordinates
     */
    public static XYPoint transformDataWGStoXY(LLPoint point, LatLongCoord bp, double angle) {
        return new XYPoint(point.getPointNumber(), MyGeoMath.ll2xy(new LatLongCoord(point.getCoords().latitude, point.getCoords().longitude), bp, angle), point.getBuilding(), point.getFloor());
    }

    /**
     * Method to transform one point from screen/picture-System to XY-System.
     * If the basepoint is zero no shifting of the ccordinates is done, only a transformation from pixel-system into xy-system
     *
     * @param pictPoint    point to transform in screen/picture-system
     * @param scaleX       scale-factor for the X-axis
     * @param scaleY       scalte-Factor for the Y-axis
     * @param pictureSizeY width of the image
     * @param bpX          x-coordinate of the basepoint in pixel
     * @param bpY          Y-coordinate of the basepoint in pixel
     * @return point with XY-coordinates
     */
    public static XYPoint transformDataPictXYtoXY(XYPoint pictPoint, double scaleX, double scaleY, double pictureSizeY, double bpX, double bpY) {
        double y;
        double x;
        if (bpX == 0.) {
            x = (pictPoint.getCoords().x) * scaleX;
        } else {
            x = (pictPoint.getCoords().x - bpX) * scaleX;
        }
        if (bpY == 0.) {
            y = -scaleY * (pictPoint.getCoords().y - pictureSizeY);
        } else {
            y = -scaleY * ((pictPoint.getCoords().y - pictureSizeY) - (bpY - pictureSizeY));
        }
        return new XYPoint(pictPoint.getPointNumber(), new LocalXYCoord(x, y), pictPoint.getBuilding(), pictPoint.getFloor());
    }

    /**
     * Method to transform one point from XY-System to screen/picture-System.
     * If the basepoint is zero no shifting of the ccordinates is done,
     * only a transformation from XY-System to screen/picture-System is done.
     *
     * @param point        point to transform in screen/picture-system
     * @param scaleX       scale-factor for the X-axis
     * @param scaleY       scalte-Factor for the Y-axis
     * @param pictureSizeY width of the image
     * @param bpX          x-coordinate of the basepoint in meter
     * @param bpY          Y-coordinate of the basepoint in meter
     * @return point with XY-coordinates
     */
    public static XYPoint transformDataXYtoPictXY(XYPoint point, double scaleX, double scaleY, double pictureSizeY, double bpX, double bpY) {
        double y;
        double x;
        if (bpX == 0.) {
            x = (point.getCoords().x) / scaleX;
        } else {
            x = (point.getCoords().x + bpX) / scaleX;
        }
        if (bpY == 0.) {
            y = (-point.getCoords().y / scaleY) + pictureSizeY;
        } else {
            y = (((-point.getCoords().y / scaleY) + pictureSizeY) - ((-bpY / scaleY) + pictureSizeY));
        }
        return new XYPoint(point.getPointNumber(), new LocalXYCoord(Math.round(x), Math.round(y)), point.getBuilding(), point.getFloor());
    }
}
