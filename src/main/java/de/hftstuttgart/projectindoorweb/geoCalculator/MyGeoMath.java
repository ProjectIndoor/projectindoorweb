/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hftstuttgart.projectindoorweb.geoCalculator;

/**
 * MyGeoMath contains static methods for coordinate conversions. Supported
 * conversions are Latitude/Longitude to local x/y coordinates and local x/y
 * coordinates to Latitude/Longitude For the conversion, a base point has to be
 * given in lat/long
 *
 * @author stefan
 */
public class MyGeoMath {

    private static final double R = 6371000.;

    public static double deg2rad(double deg) {
        return deg / 180. * Math.PI;
    }

    public static double rad2deg(double rad) {
        return rad / Math.PI * 180.;
    }

    public static double dms(int d, int m, double s) {
        return d + m / 60. + s / 3600.;
    }

    public static double alignDeg(double fi) {
        while (fi > 180) {
            fi -= 360;
        }
        while (fi < -180) {
            fi += 360;
        }
        return fi;
    }

    private static double getEast(double lati, double longi) {
        double Reff = R * Math.cos(deg2rad(lati));
        return Reff * deg2rad(longi);
    }

    private static NECoord ll2ne(LatLongCoord ll, LatLongCoord bp) {
        // first, we go east on the base Latitude 
        NECoord ne = new NECoord();
        ne.east = getEast(bp.latitude, ll.longitude - bp.longitude);
        // then we go north
        ne.north = R * (deg2rad(ll.latitude - bp.latitude));
        return ne;
    }

    private static LatLongCoord ne2ll(NECoord ne, LatLongCoord bp) {
        // we start on bp
        LatLongCoord ll = bp.clone();
        // we walk east on the base Latitude
        double Reff = R * Math.cos(deg2rad(bp.latitude));
        ll.longitude += rad2deg(ne.east / Reff);
        // we noe walk north
        ll.latitude += rad2deg(ne.north / R);
        return ll;
    }

    /**
     * ll2xy converts a latitude/longitude coordinate to a local xy coordinate
     * it is not meant to be very accurate though ...
     * <p>
     * besides the actual coordinate ll to be converted, also a basept and an
     * anglept are needed
     *
     * @param ll      coordinate for which the transofrmation will be calculated
     * @param basept  The latitude and longitude of the local xy-system (0/0)
     *                point
     * @param anglept lat and long of a point on the x-Axis of the local xy
     *                system This point is used to detect the angle between the x-axis and the
     *                west-east direction
     * @return The return value holds the x- and y coordinate of the point ll,
     * in local xy coordinates, unit is meters
     */
    public static LocalXYCoord ll2xy(LatLongCoord ll, LatLongCoord basept, LatLongCoord anglept) {

        NECoord anglept_ne = ll2ne(anglept, basept);
        double angle = Math.atan2(anglept_ne.north, anglept_ne.east);
        angle = -angle; // turn point into system  
        //System.out.println("angle" +rad2deg(angle));

        NECoord local_ne = ll2ne(ll, basept);
        // System.out.println("ll: " +local_ne);
        //System.out.println("Dist LL - BP" + local_ne.getAbs());

        return new LocalXYCoord(local_ne.east * Math.cos(angle) - local_ne.north * Math.sin(angle),
                local_ne.east * Math.sin(angle) + local_ne.north * Math.cos(angle));
    }

    public static LocalXYCoord ll2xy(LatLongCoord ll, LatLongCoord basept, double angle) {

//	   NECoord anglept_ne =  ll2ne(anglept,basept);
//	   double angle = Math.atan2(anglept_ne.north, anglept_ne.east);
        angle = -angle; // turn point into system  
        //System.out.println("angle" +rad2deg(angle));

        NECoord local_ne = ll2ne(ll, basept);
        // System.out.println("ll: " +local_ne);
        //System.out.println("Dist LL - BP" + local_ne.getAbs());

        return new LocalXYCoord(local_ne.east * Math.cos(angle) - local_ne.north * Math.sin(angle),
                local_ne.east * Math.sin(angle) + local_ne.north * Math.cos(angle));
    }

    public static LatLongCoord xy2ll(LocalXYCoord xy, LatLongCoord basept, LatLongCoord anglept) {
// rotate back
        NECoord anglept_ne = ll2ne(anglept, basept);
        double angle = Math.atan2(anglept_ne.north, anglept_ne.east);
        // north = y, east = x ...
        NECoord my_ne = new NECoord(xy.x * Math.sin(angle) + xy.y * Math.cos(angle),
                xy.x * Math.cos(angle) - xy.y * Math.sin(angle));
        return ne2ll(my_ne, basept);
    }

    public static LatLongCoord xy2ll(LocalXYCoord xy, LatLongCoord basept, double angle) {
        // rotate back
//	   NECoord anglept_ne =  ll2ne(anglept,basept);
//	   double angle = Math.atan2(anglept_ne.north, anglept_ne.east);
        // north = y, east = x ...
        NECoord my_ne = new NECoord(xy.x * Math.sin(angle) + xy.y * Math.cos(angle),
                xy.x * Math.cos(angle) - xy.y * Math.sin(angle));
        return ne2ll(my_ne, basept);
    }

    public static double bearing(LatLongCoord basept, LatLongCoord anglept) {

        NECoord anglept_ne = ll2ne(anglept, basept);
        double angle = Math.atan2(anglept_ne.north, anglept_ne.east);
        return angle;
    }

}
