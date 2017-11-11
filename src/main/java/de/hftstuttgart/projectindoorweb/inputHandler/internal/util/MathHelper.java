package de.hftstuttgart.projectindoorweb.inputHandler.internal.util;

import de.hftstuttgart.projectindoorweb.geoCalculator.LocXYZ;
import de.hftstuttgart.projectindoorweb.persistence.pojo.Position;

public class MathHelper {


    public static double retrievePositionDistance(Position a, Position b){

        LocXYZ wrapperA = new LocXYZ(a.getLatitude(), a.getLongitude(), a.getHeight());
        LocXYZ wrapperB = new LocXYZ(b.getLatitude(), b.getLongitude(), b.getHeight());

        return wrapperA.getDist(wrapperB);

    }
}
