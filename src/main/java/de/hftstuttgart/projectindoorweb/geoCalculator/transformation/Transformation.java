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

    //if basepoint is zero no shifting is done, only a transformation from pixel system into math-system
    public static XYPoint transformDataPictXYtoXY(XYPoint PictPoint,double scaleX, double scaleY, double pictureSizeY, double bpX, double bpY) {
        double y;
        double x;
        if (bpX == 0.) {
            x = (PictPoint.getCoords().x) * scaleX;
        }else {
            x = (PictPoint.getCoords().x - bpX) * scaleX;
        }
        if (bpY == 0.) {
            y = -scaleY * (PictPoint.getCoords().y - pictureSizeY);
        }else {
            y = -scaleY * ((PictPoint.getCoords().y - pictureSizeY) - ( bpY - pictureSizeY));
        }
        return new XYPoint(PictPoint.getPointNumber(), new LocalXYCoord(x,y),PictPoint.getBuilding(),PictPoint.getFloor());
    }

    //BasePoint should be given in mathematical system. If no basepoint should be use, give 0 as value for bpX and bpY
    public static XYPoint transformDataXYtoPictXY(XYPoint Point,double scaleX, double scaleY, double pictureSizeY, double bpX, double bpY) {
        double y;
        double x;
        if (bpX == 0.) {
            x = (Point.getCoords().x) / scaleX;
        }else {
            x = (Point.getCoords().x + bpX) / scaleX;
        }
        if (bpY == 0.) {
            y = (-Point.getCoords().y  / scaleY) + pictureSizeY;
        }else {
            y = (((-Point.getCoords().y / scaleY) + pictureSizeY) - (( -bpY / scaleY) + pictureSizeY));
        }
        return new XYPoint(Point.getPointNumber(), new LocalXYCoord(Math.round(x),Math.round(y)),Point.getBuilding(),Point.getFloor());
    }

}
