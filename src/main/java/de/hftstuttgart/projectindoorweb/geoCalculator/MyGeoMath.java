/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hftstuttgart.projectindoorweb.geoCalculator;

import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LatLongCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LocalXYCoord;

/**
 * MyGeoMath contains static methods for coordinate conversions. Supported
 * conversions are Latitude/Longitude to local x/y coordinates and local x/y
 * coordinates to Latitude/Longitude For the conversion, a base point has to be
 * given in lat/long
 *
 * @author stefan
 */
public class MyGeoMath {

    /**
     * Radius of the ellipsoid of the earth.
     */
    private static final double R = 6371000.;
    /**
     * constant-value for 180 degree.
     */
    private static final double DEG180 = 180;
    /**
     * constant-value for 360 degree.
     */
    private static final int DEG360 = 360;

    /**
     * Private constructor to prevent creating an object.
     */
    private MyGeoMath() {

    }

    /**
     * Converts an angle given in degree to radian.
     *
     * @param deg angle in degree
     * @return angle in radian
     */
    public static double deg2rad(final double deg) {
        return deg / DEG180 * Math.PI;
    }

    /**
     * Converts an angle given in radian to degree.
     *
     * @param rad angle in radian
     * @return angle in degree
     */
    public static double rad2deg(final double rad) {
        return rad / Math.PI * DEG180;
    }

    /**
     * Converts a Latitutde/Logitude value from DMS(Degree, Minutes, Seconds) into a double value.
     * e.g. d=52,m=31,s=12.025
     * result=52.520007
     *
     * @param d degree
     * @param m minutes
     * @param s seconds
     * @return angle as double value
     */
    public static double dms(final int d, final int m, final double s) {
        return d + m / 60. + s / 3600.;
    }

    /**
     * Checks if the angle can be correct. An angle over 180degree is not possible.
     * If the angle is not correct, 360° is either added or subtracted.
     *
     * @param fi angle to check
     * @return correct angle
     */
    public static double alignDeg(double fi) {
        while (fi > DEG180) {
            fi -= DEG360;
        }
        while (fi < -DEG180) {
            fi += DEG360;
        }
        return fi;
    }

    /**
     * Calculates an east-value from a given latitude/longitude point.
     *
     * @param lati  latitude of the point
     * @param longi longitude of the point
     * @return east value
     */
    private static double getEast(final double lati, final double longi) {
        double radiuseff = R * Math.cos(deg2rad(lati));
        return radiuseff * deg2rad(longi);
    }

    /**
     * converts a latitude/longitude coordinate to a north/east coordinate.
     *
     * @param ll coordinate for which the transofrmation will be calculated
     * @param bp The latitude and longitude of the local xy-system (0/0)
     *           point
     * @return The return value holds the north/east-coordinate of the point ll, unit is meters
     */
    private static NECoord ll2ne(final LatLongCoord ll, final LatLongCoord bp) {
        //first, we go east on the base Latitude
        NECoord ne = new NECoord();
        ne.setEast(getEast(bp.getLatitude(), ll.getLongitude() - bp.getLongitude()));
        //then we go north
        ne.setNorth(R * (deg2rad(ll.getLatitude() - bp.getLatitude())));
        return ne;
    }

    /**
     * converts a latitude/longitude coordinate to a local xy coordinate.
     *
     * @param ne coordinates for which the transformation will be calculated
     * @param bp The latitude and longitude of the local xy-system (0/0)
     *           point
     * @return The return value holds the latitude/longitude coordinate of the point ne
     */
    private static LatLongCoord ne2ll(final NECoord ne, final LatLongCoord bp) {
        // we start on bp
        LatLongCoord ll = bp.clone();
        // we walk east on the base Latitude
        double radiuseff = R * Math.cos(deg2rad(bp.getLatitude()));
        ll.setLongitude(ll.getLongitude() + rad2deg(ne.getEast() / radiuseff));
        // we noe walk north
        ll.setLatitude(ll.getLatitude() + rad2deg(ne.getNorth() / R));
        return ll;
    }

    /**
     * ll2xy converts a latitude/longitude coordinate to a local xy coordinate.
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
    public static LocalXYCoord ll2xy(final LatLongCoord ll, final LatLongCoord basept, final LatLongCoord anglept) {

        NECoord angleptNe = ll2ne(anglept, basept);
        double angle = Math.atan2(angleptNe.getNorth(), angleptNe.getEast());
        // turn point into system
        angle = -angle;
        //System.out.println("angle" +rad2deg(angle));

        NECoord localNe = ll2ne(ll, basept);
        // System.out.println("ll: " +local_ne);
        //System.out.println("Dist LL - BP" + local_ne.getAbs());

        return new LocalXYCoord(localNe.getEast() * Math.cos(angle) - localNe.getNorth() * Math.sin(angle),
                localNe.getEast() * Math.sin(angle) + localNe.getNorth() * Math.cos(angle));
    }

    /**
     * ll2xy converts a latitude/longitude coordinate to a local xy coordinate.
     * it is not meant to be very accurate though ...
     * <p>
     * besides the actual coordinate ll to be converted, also a basept and an angle are needed.
     *
     * @param ll     coordinate for which the transformation will be calculated
     * @param basept The latitude and longitude of the local xy-system (0/0)
     *               point
     * @param angle  angle between the two coordinate-systems
     * @return The return value holds the x- and y coordinate of the point ll,
     * in local xy coordinates, unit is meters
     */
    public static LocalXYCoord ll2xy(final LatLongCoord ll, final LatLongCoord basept, double angle) {

        // NECoord anglept_ne =  ll2ne(anglept,basept);
        // double angle = Math.atan2(anglept_ne.north, anglept_ne.east);
        //turn point into system
        angle = -angle;
        //System.out.println("angle" +rad2deg(angle));

        NECoord localNe = ll2ne(ll, basept);
        // System.out.println("ll: " +local_ne);
        //System.out.println("Dist LL - BP" + local_ne.getAbs());

        return new LocalXYCoord(localNe.getEast() * Math.cos(angle) - localNe.getNorth() * Math.sin(angle),
                localNe.getEast() * Math.sin(angle) + localNe.getNorth() * Math.cos(angle));
    }

    /**
     * xy2ll converts a local xy coordinate to a latitude/longitude coordinate.
     * it is not meant to be very accurate though ...
     * <p>
     * besides the actual coordinate ll to be converted, also a basept and an angle are needed.
     *
     * @param xy      coordinates for which the transformation will be calculated
     * @param basept  The latitude and longitude of the local xy-system (0/0)
     *                point
     * @param anglept lat and long of a point on the x-Axis of the local xy
     *                system This point is used to detect the angle between the x-axis and the
     *                west-east direction
     * @return The return value holds the latitude/longitude coordinate of the point xy.
     */
    public static LatLongCoord xy2ll(final LocalXYCoord xy, final LatLongCoord basept, final LatLongCoord anglept) {
// rotate back
        NECoord angleptNe = ll2ne(anglept, basept);
        double angle = Math.atan2(angleptNe.getNorth(), angleptNe.getEast());
        // north = y, east = x ...
        NECoord myNe = new NECoord(xy.getX() * Math.sin(angle) + xy.getY() * Math.cos(angle),
                xy.getX() * Math.cos(angle) - xy.getY() * Math.sin(angle));
        return ne2ll(myNe, basept);
    }

    /**
     * xy2ll converts a local xy coordinate to a latitude/longitude coordinate.
     * it is not meant to be very accurate though ...
     * <p>
     * besides the actual coordinate ll to be converted, also a basept and an angle are needed.
     *
     * @param xy     coordinates for which the transformation will be calculated
     * @param basept The latitude and longitude of the local xy-system (0/0)
     *               point
     * @param angle  angle between the two vcoordinate-systems
     * @return The return value holds the latitude/longitude coordinate of the point xy.
     */
    public static LatLongCoord xy2ll(final LocalXYCoord xy, final LatLongCoord basept, final double angle) {
        // north = y, east = x ...
        NECoord myNe = new NECoord(xy.getX() * Math.sin(angle) + xy.getY() * Math.cos(angle),
                xy.getX() * Math.cos(angle) - xy.getY() * Math.sin(angle));
        return ne2ll(myNe, basept);
    }

    /**
     * Calculates the angle between Latitude/Longitude-System and the local xy system.
     *
     * @param basept  The latitude and longitude of the local xy-system (0/0)
     *                point
     * @param anglept lat and long of a point on the x-Axis of the local xy
     *                system This point is used to detect the angle between the x-axis and the
     *                west-east direction
     * @return angle between the two systems
     */
    public static double bearing(final LatLongCoord basept, final LatLongCoord anglept) {

        NECoord angleptNe = ll2ne(anglept, basept);
        double angle = Math.atan2(angleptNe.getNorth(), angleptNe.getEast());
        return angle;
    }

}
