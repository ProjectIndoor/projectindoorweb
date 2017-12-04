package de.hftstuttgart.projectindoorweb.geoCalculator.transformation;

import de.hftstuttgart.projectindoorweb.geoCalculator.MyGeoMath;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LLPoint;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LatLongCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LocalXYCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.XYPoint;

class Transformation {

    static LLPoint transformDataXYtoWGS(XYPoint point, LatLongCoord SE, LatLongCoord SW) {
        return new LLPoint(point.getPointNumber(), MyGeoMath.xy2ll(new LocalXYCoord(point.getCoords().x,point.getCoords().y),SE,SW),point.getBuilding(),point.getFloor());
    }

    static XYPoint transformDataWGStoXY(LLPoint point, LatLongCoord SE, LatLongCoord SW) {
        return new XYPoint(point.getPointNumber(), MyGeoMath.ll2xy(new LatLongCoord(point.getCoords().latitude,point.getCoords().longitude),SE,SW),point.getBuilding(),point.getFloor());
    }

    public static LLPoint transformDataXYtoWGS(XYPoint point, LatLongCoord BP, double angle) {
        return new LLPoint(point.getPointNumber(), MyGeoMath.xy2ll(new LocalXYCoord(point.getCoords().x,point.getCoords().y),BP,angle),point.getBuilding(),point.getFloor());
    }

    public static XYPoint transformDataWGStoXY(LLPoint point, LatLongCoord BP, double angle) {
        return new XYPoint(point.getPointNumber(), MyGeoMath.ll2xy(new LatLongCoord(point.getCoords().latitude,point.getCoords().longitude),BP,angle),point.getBuilding(),point.getFloor());
    }

    static XYPoint transformDataPictXYtoXY(XYPoint PictPoint, double scaleX, double scaleY, double pictureSizeY, double bpX, double bpY) {
        double y = -scaleY * (PictPoint.getCoords().y - bpY - pictureSizeY);
        double x = (PictPoint.getCoords().x - bpX) * scaleX;
        return new XYPoint(PictPoint.getPointNumber(), new LocalXYCoord(x,y),PictPoint.getBuilding(),PictPoint.getFloor());
    }
    static XYPoint transformDataXYtoPictXY(XYPoint Point, double scaleX, double scaleY, double pictureSizeY, double bpX, double bpY) {
        double y = Math.round((-Point.getCoords().y + bpY )/scaleY) + pictureSizeY;
        double x = Math.round((Point.getCoords().x + bpX) / scaleX);
        return new XYPoint(Point.getPointNumber(), new LocalXYCoord(x,y),Point.getBuilding(),Point.getFloor());
    }
}
