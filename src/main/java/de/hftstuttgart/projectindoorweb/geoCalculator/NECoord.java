/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hftstuttgart.projectindoorweb.geoCalculator;

/**
 * @author stefan
 */
public class NECoord {

    double north = 0;
    double east = 0;

    NECoord() {
    }

    NECoord subtract(NECoord that) {
        return new NECoord(this.north - that.north, this.east - that.east);
    }

    NECoord(double north, double east) {
        this.north = north;
        this.east = east;
    }

    public double getAbs() {
        return Math.sqrt(north * north + east * east);
    }

    @Override
    public NECoord clone() {
        return new NECoord(north, east);
    }

    @Override
    public String toString() {
        return String.format("N: %7.2f E: %7.2f \n", north, east);
    }
}
