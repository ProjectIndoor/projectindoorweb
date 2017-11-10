package de.hftstuttgart.projectindoorweb.helper;

import de.hftstuttgart.projectindoorweb.maths.LocXYZ;
import de.hftstuttgart.projectindoorweb.pojos.Position;

public class MathHelper {


    public static double retrievePositionDistance(Position a, Position b){

        LocXYZ wrapperA = new LocXYZ(a.getLatitude(), a.getLongitude(), a.getHeight());
        LocXYZ wrapperB = new LocXYZ(b.getLatitude(), b.getLongitude(), b.getHeight());

        return wrapperA.getDist(wrapperB);

    }
}
