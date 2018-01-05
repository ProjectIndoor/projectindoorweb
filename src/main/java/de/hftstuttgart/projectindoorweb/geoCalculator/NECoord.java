/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hftstuttgart.projectindoorweb.geoCalculator;

/**
 * Class that represents a North-East-Coordinate-System.
 *
 * @author stefan
 */
public class NECoord {

    /**
     * North-value for the coordinates.
     */
    private double vNorth = 0;
    /**
     * East-value for the coordinates.
     */
    private double vEast = 0;

    /**
     * Returns the north-value of the point.
     *
     * @return North-value
     */
    public double getNorth() {
        return vNorth;
    }

    /**
     * Sets the North-value of the point.
     *
     * @param v north-value
     */
    public void setNorth(final double v) {
        this.vNorth = v;
    }

    /**
     * Returns the east-value of the point.
     *
     * @return East-value
     */
    public double getEast() {
        return vEast;
    }

    /**
     * Sets the east-Value of the point.
     *
     * @param v east-value
     */
    public void setEast(final double v) {
        this.vEast = v;
    }

    /**
     * Standard-constructor.
     */
    NECoord() {
    }

    /**
     * Method to calculate the coordinate differences of two points.
     *
     * @param that Point that should be subtracted from the Object.
     * @return Coordinate differences as NE-coordinates
     */
    NECoord subtract(final NECoord that) {
        return new NECoord(this.getNorth() - that.getNorth(), this.getEast() - that.getEast());
    }

    /**
     * Standard-constructor.
     *
     * @param north North-Coordinate of the point
     * @param east East-coordinate of the point
     */
    NECoord(final double north, final double east) {
        this.setNorth(north);
        this.setEast(east);
    }

    /**
     * Calculates the distance from this point to the origin of the coordinate-system.
     *
     * @return Distance to the origin of the coordinate-system
     */
    public double getAbs() {
        return Math.sqrt(getNorth() * getNorth() + getEast() * getEast());
    }

    /**
     * Clones this object.
     *
     * @return New Object with the same values
     */
    @Override
    public NECoord clone() {
        return new NECoord(getNorth(), getEast());
    }

    /**
     * Gives back the coordinates of this point as String.
     *
     * @return Formated coordinates as String
     */
    @Override
    public String toString() {
        return String.format("N: %7.2f E: %7.2f \n", getNorth(), getEast());
    }


}
