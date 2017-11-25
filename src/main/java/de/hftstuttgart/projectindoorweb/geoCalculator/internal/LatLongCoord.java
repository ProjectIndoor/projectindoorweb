/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hftstuttgart.projectindoorweb.geoCalculator.internal;

import java.io.Serializable;

/**
 *
 * @author stefan
 */
public class LatLongCoord implements Serializable {

    static final long serialVersionUID = 1;
    public double latitude = 0;
    public double longitude = 0;

    public LatLongCoord(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public LatLongCoord clone() {
        return new LatLongCoord(latitude, longitude);
    }

    @Override
    public String toString() {
        return String.format("N: %2.7f E: %2.7f ", latitude, longitude);
    }
}
