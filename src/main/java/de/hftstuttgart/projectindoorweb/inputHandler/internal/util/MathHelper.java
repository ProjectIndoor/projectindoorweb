package de.hftstuttgart.projectindoorweb.inputHandler.internal.util;

import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LocXYZ;
import de.hftstuttgart.projectindoorweb.persistence.entities.Position;
import de.hftstuttgart.projectindoorweb.persistence.entities.WifiPositionResult;

public class MathHelper {


    public static double retrievePositionDistance(Position a, Position b) {

        LocXYZ wrapperA = new LocXYZ(a.getX(), a.getY(), a.getZ());
        LocXYZ wrapperB = new LocXYZ(b.getX(), b.getY(), b.getZ());

        return wrapperA.getDist(wrapperB);

    }


    public static Position addTwoPositions(Position a, Position b) {

        LocXYZ wrapperA = new LocXYZ(a.getX(), a.getY(), a.getZ());
        LocXYZ wrapperB = new LocXYZ(b.getX(), b.getY(), b.getZ());

        LocXYZ resultWrapper = wrapperA.add(wrapperB);

        return new Position(resultWrapper.x, resultWrapper.y, resultWrapper.z, a.isWgs84() && b.isWgs84());


    }

    public static Position multiplyPosition(double x, double y, double z, boolean wgs84, double factor){

        return multiplyPosition(new Position(x, y, z, wgs84), factor);
    }

    public static Position multiplyPosition(Position p, double factor) {

        double newX = p.getX() * factor;
        double newY = p.getY() * factor;
        double newZ = p.getZ() * factor;

        return new Position(newX, newY, newZ, p.isWgs84());
    }


}
