package de.hftstuttgart.projectindoorweb.geoCalculator.transformation;

import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LLPoint;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LatLongCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LocalXYCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.XYPoint;
import de.hftstuttgart.projectindoorweb.persistence.entities.Building;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.io.IOException;
import java.util.Iterator;

public class TransformationHelper {

    public static double getDistance(LatLongCoord point1, LatLongCoord point2, LatLongCoord SW, LatLongCoord SE) {
        XYPoint p1 = Transformation.transformDataWGStoXY(new LLPoint("",point1,0,0),SW,SE);
        XYPoint p2 = Transformation.transformDataWGStoXY(new LLPoint("",point2,0,0),SW,SE);
        return Math.sqrt(Math.pow(p1.getCoords().x - p2.getCoords().x,2)+Math.pow(p1.getCoords().y - p2.getCoords().y,2));
    }

    public static double calculateScaleFactor(double picSizeInPixel, double picSizeInMeter) {
        return  picSizeInMeter/picSizeInPixel;
    }
    public static Dimension getPictureSize(String path)  throws IOException {
        try(ImageInputStream in = ImageIO.createImageInputStream(path)){
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

    public static double[] calculateSouthWestCorner(LatLongCoord center, double angle, double scale, int imgSizeX, int imgSizeY) {
        LocalXYCoord centerXY = new LocalXYCoord(imgSizeX/2,imgSizeY/2);
        return pictToWGS(center,new LocalXYCoord(imgSizeX,imgSizeY),centerXY,angle,scale,imgSizeY);
    }

    public static double[] calculateSouthEastCorner(LatLongCoord center, double angle, double scale, int imgSizeX, int imgSizeY) {
        LocalXYCoord centerXY = new LocalXYCoord(imgSizeX/2,imgSizeY/2);
        return pictToWGS(center,new LocalXYCoord(0,imgSizeY),centerXY,angle,scale,imgSizeY);
    }
    public static double[] calculateNorthWestCorner(LatLongCoord center, double angle, double scale, int imgSizeX, int imgSizeY) {
        LocalXYCoord centerXY = new LocalXYCoord(imgSizeX/2,imgSizeY/2);
        return pictToWGS(center,new LocalXYCoord(imgSizeX,0),centerXY,angle,scale,imgSizeY);
    }
    public static double[] calculateNorthEastCorner(LatLongCoord center, double angle, double scale, int imgSizeX, int imgSizeY) {
        LocalXYCoord centerXY = new LocalXYCoord(imgSizeX/2,imgSizeY/2);
        return pictToWGS(center,new LocalXYCoord(0,0),centerXY,angle,scale,imgSizeY);
    }
    //Method to calculate the image Corner out of the building corners
    public static double[] calculateNorthEastCorner(LatLongCoord SE, LatLongCoord SW, LatLongCoord NE, LocalXYCoord BP, double buildingSizeX, double buildingSizeY, int imgSizeX, int imgSizeY) {
        return TransformationHelper.pictToWGS(SE,SW,NE,new LocalXYCoord(0,0),buildingSizeX ,buildingSizeY,imgSizeY, BP.x,BP.y);
    }
    //Method to calculate the image Corner out of the building corners
    public static double[] calculateNorthWestCorner(LatLongCoord SE, LatLongCoord SW, LatLongCoord NE, LocalXYCoord BP, double buildingSizeX, double buildingSizeY, int imgSizeX, int imgSizeY) {
        return TransformationHelper.pictToWGS(SE,SW,NE,new LocalXYCoord(imgSizeX,0),buildingSizeX,buildingSizeY,imgSizeY, BP.x,BP.y);
    }
    //Method to calculate the image Corner out of the building corners
    public static double[] calculateSouthEastCorner(LatLongCoord SE, LatLongCoord SW, LatLongCoord NE, LocalXYCoord BP, double buildingSizeX, double buildingSizeY, int imgSizeX, int imgSizeY) {
        return TransformationHelper.pictToWGS(SE,SW,NE,new LocalXYCoord(0,imgSizeY), buildingSizeX,buildingSizeY,imgSizeY, BP.x,BP.y);
    }
    //Method to calculate the image Corner out of the building corners
    public static double[] calculateSouthWestCorner(LatLongCoord SE, LatLongCoord SW, LatLongCoord NE, LocalXYCoord BP, double buildingSizeX, double buildingSizeY, int imgSizeX, int imgSizeY) {
        return TransformationHelper.pictToWGS(SE,SW,NE,new LocalXYCoord(imgSizeX,imgSizeY),buildingSizeX,buildingSizeY,imgSizeY, BP.x,BP.y);
    }

    public static double[] wgsToPict(Building b, LatLongCoord point, int picWidth, int picHeight) {
        LatLongCoord SE = new LatLongCoord(b.getSouthEast().getX(),b.getSouthEast().getY());
        LatLongCoord SW = new LatLongCoord(b.getSouthWest().getX(),b.getSouthWest().getY());
        LatLongCoord NE = new LatLongCoord(b.getNorthEast().getX(),b.getNorthEast().getY());
        XYPoint pXY = Transformation.transformDataWGStoXY(new LLPoint("",point,0,0),SW,SE);
        double scaleX = calculateScaleFactor(picWidth,getDistance(SE,SW,SW,SE));
        double scaleY = calculateScaleFactor(picHeight,getDistance(SE,NE,SW,SE));

        XYPoint pPict = Transformation.transformDataXYtoPictXY(pXY,scaleX,scaleY,picHeight,0,0);
        double[] result = new double[2];
        result[0] = pPict.getCoords().x;
        result[1] = pPict.getCoords().y;
        return result;
    }

    public static double[] wgsToXY(Building b, LatLongCoord point) {
        LatLongCoord SE = new LatLongCoord(b.getSouthEast().getX(),b.getSouthEast().getY());
        LatLongCoord SW = new LatLongCoord(b.getSouthWest().getX(),b.getSouthWest().getY());
        XYPoint pXY = Transformation.transformDataWGStoXY(new LLPoint("",point,0,0),SW,SE);
        double[] result = new double[2];
        result[0] = pXY.getCoords().x;
        result[1] = pXY.getCoords().y;
        return result;
    }
    public static double[] xyToWGS(Building b, LocalXYCoord point) {
        LatLongCoord SE = new LatLongCoord(b.getSouthEast().getX(),b.getSouthEast().getY());
        LatLongCoord SW = new LatLongCoord(b.getSouthWest().getX(),b.getSouthWest().getY());
        LLPoint pLL = Transformation.transformDataXYtoWGS(new XYPoint("",point,0,0),SW,SE);
        double[] result = new double[2];
        result[0] = pLL.getCoords().latitude;
        result[1] = pLL.getCoords().longitude;
        return result;
    }
    public static double[] xyToPict(Building b, LocalXYCoord point, int picWidth, int picHeight) {
        LatLongCoord SE = new LatLongCoord(b.getSouthEast().getX(),b.getSouthEast().getY());
        LatLongCoord SW = new LatLongCoord(b.getSouthWest().getX(),b.getSouthWest().getY());
        LatLongCoord NE = new LatLongCoord(b.getNorthEast().getX(),b.getNorthEast().getY());

        double scaleX = calculateScaleFactor(picWidth,getDistance(SE,SW,SW,SE));
        double scaleY = calculateScaleFactor(picHeight,getDistance(SE,NE,SW,SE));

        XYPoint pPict = Transformation.transformDataXYtoPictXY(new XYPoint("",point,0,0),scaleX,scaleY,picHeight,0,0);
        double[] result = new double[2];
        result[0] = pPict.getCoords().x;
        result[1] = pPict.getCoords().y;
        return result;
    }
    public static double[] pictToXY(Building b, LocalXYCoord point, int picWidth, int picHeight) {
        LatLongCoord SE = new LatLongCoord(b.getSouthEast().getX(),b.getSouthEast().getY());
        LatLongCoord SW = new LatLongCoord(b.getSouthWest().getX(),b.getSouthWest().getY());
        LatLongCoord NE = new LatLongCoord(b.getNorthEast().getX(),b.getNorthEast().getY());

        double scaleX = calculateScaleFactor(picWidth,getDistance(SE,SW,SW,SE));
        double scaleY = calculateScaleFactor(picHeight,getDistance(SE,NE,SW,SE));

        XYPoint pXY = Transformation.transformDataPictXYtoXY(new XYPoint("",point,0,0),scaleX,scaleY,picHeight,0,0);

        double[] result = new double[2];
        result[0] = pXY.getCoords().x;
        result[1] = pXY.getCoords().y;
        return result;
    }
    public static double[] pictToWGS(Building b, LocalXYCoord point, int picWidth, int picHeight) {
        LatLongCoord SE = new LatLongCoord(b.getSouthEast().getX(),b.getSouthEast().getY());
        LatLongCoord SW = new LatLongCoord(b.getSouthWest().getX(),b.getSouthWest().getY());
        LatLongCoord NE = new LatLongCoord(b.getNorthEast().getX(),b.getNorthEast().getY());

        double scaleX = calculateScaleFactor(picWidth,getDistance(SE,SW,SW,SE));
        double scaleY = calculateScaleFactor(picHeight,getDistance(SE,NE,SW,SE));

        XYPoint pXY = Transformation.transformDataPictXYtoXY(new XYPoint("",point,0,0),scaleX,scaleY,picHeight,0,0);

        LLPoint pLL = Transformation.transformDataXYtoWGS(pXY,SW,SE);

        double[] result = new double[2];
        result[0] = pLL.getCoords().latitude;
        result[1] = pLL.getCoords().longitude;
        return result;
    }
    public static double[] pictToWGS(LatLongCoord center, LocalXYCoord point, LocalXYCoord BP,double angle, double scale, double picHeight) {

        XYPoint pXY = Transformation.transformDataPictXYtoXY(new XYPoint("",point,0,0),scale,scale,picHeight,BP.x,BP.y);

        LLPoint pLL = Transformation.transformDataXYtoWGS(pXY,center,angle);

        double[] result = new double[2];
        result[0] = pLL.getCoords().latitude;
        result[1] = pLL.getCoords().longitude;
        return result;
    }

    private static double[] pictToWGS(LatLongCoord SE, LatLongCoord SW, LatLongCoord NE, LocalXYCoord point, double picWidth, double picHeight, double picSizeY, double bpX, double bpY) {

        double scaleX = calculateScaleFactor(picWidth,getDistance(SE,SW,SW,SE));
        double scaleY = calculateScaleFactor(picHeight,getDistance(SE,NE,SW,SE));

        XYPoint pXY = Transformation.transformDataPictXYtoXY(new XYPoint("",point,0,0),scaleX,scaleY,picSizeY,bpX,bpY);

        LLPoint pLL = Transformation.transformDataXYtoWGS(pXY,SW,SE);

        double[] result = new double[2];
        result[0] = pLL.getCoords().latitude;
        result[1] = pLL.getCoords().longitude;
        return result;
    }
}
