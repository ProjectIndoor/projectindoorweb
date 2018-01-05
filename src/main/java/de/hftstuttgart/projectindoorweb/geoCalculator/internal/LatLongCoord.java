/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hftstuttgart.projectindoorweb.geoCalculator.internal;

import java.io.Serializable;

/**
 * Class that represents a Latitude/Longitude-Coordinate-System.
 *
 * @author stefan
 */
public class LatLongCoord implements Serializable {

    /**
     * VersionUID for serialisation.
     */
    static final long serialVersionUID = 1;
    /**
     * Latitude-value.
     */
    private double latitude = 0;
    /**
     * Longitude-value.
     */
    private double longitude = 0;

    /**
     * Returns the Latitude of the point.
     *
     * @return Latitude as double
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude of the point.
     *
     * @param lat Latitude as double
     */
    public void setLatitude(final double lat) {
        this.latitude = lat;
    }

    /**
     * Returns the longitude as double.
     *
     * @return Longitude as double
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude of the point.
     *
     * @param lon Longitude as double
     */
    public void setLongitude(final double lon) {
        this.longitude = lon;
    }

    /**
     * Standardconstructor to generate a new point.
     *
     * @param lat Latitude-Value of the point
     * @param lon Longitude-value of the point
     */
    public LatLongCoord(final double lat, final double lon) {
        this.setLatitude(lat);
        this.setLongitude(lon);
    }

    /**
     * Clones this object.
     *
     * @return New Object with the same values
     */
    @Override
    public LatLongCoord clone() {
        return new LatLongCoord(getLatitude(), getLongitude());
    }

    /**
     * Gives back the coordinates of this point as String.
     *
     * @return Formated coordinates as String
     */
    @Override
    public String toString() {
        return String.format("N: %2.7f E: %2.7f ", getLatitude(), getLongitude());
    }
}
