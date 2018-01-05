/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hftstuttgart.projectindoorweb.geoCalculator.internal;

import de.hftstuttgart.projectindoorweb.geoCalculator.MyGeoMath;

/**
 * Class that represents a point in a local mathematical 2D-coordinate-system.
 *
 * @author stefan
 */
public class LocalXYCoord {

    /**
     * X-coordinate.
     */
    private double vX = 0;
    /**
     * y-coordinate.
     */
    private double vY = 0;


    /**
     * Returns the x-Value of the point.
     *
     * @return x-value as double
     */
    public double getX() {
        return vX;
    }

    /**
     * Sets the x-value of the point.
     *
     * @param v x-value
     */
    public void setX(final double v) {
        this.vX = v;
    }


    /**
     * Returns the y-value of the point.
     *
     * @return y-value as double
     */
    public double getY() {
        return vY;
    }

    /**
     * Sets the y-Value of the point.
     *
     * @param v y-value
     */
    public void setY(final double v) {
        this.vY = v;
    }

    /**
     * Standard-constructor.
     */
    public LocalXYCoord() {
    }

    /**
     * Standard-constructor.
     *
     * @param x x-value of the point
     * @param y y-value of the point
     */
    public LocalXYCoord(final double x, final double y) {
        this.setX(x);
        this.setY(y);
    }

    /**
     * Calculates the distance between two points.
     *
     * @param p2 secons point for the calulation
     * @return Distance between the two point
     */
    public double getDist(final LocalXYCoord p2) {
        return this.sub(p2).getAbs();
    }

    /**
     * Calculates the distance from this point to the origin of the coordinate-system.
     *
     * @return Distance to the origin of the coordinate-system
     */
    public double getAbs() {
        return Math.sqrt(getX() * getX() + getY() * getY());
    }

    /**
     * Calculates the distance from this point to the origin of the coordinate-system.
     *
     * @return Distance to the origin of the coordinate-system
     */
    public double getAbsXY() {
        return getAbs();
    }

    /**
     * Adds the coordinates of another point to this point and store it in a new object.
     *
     * @param xy coordinates that should be added.
     * @return New object with the added coordinates.
     */
    public LocalXYCoord add(final LocalXYCoord xy) {
        LocalXYCoord res = clone();
        res.setX(res.getX() + xy.getX());
        res.setY(res.getY() + xy.getY());
        return res;
    }

    /**
     * Subtracts the coordinates of another point to this point and store it in a new object.
     *
     * @param xy coordinates that should be subtracted.
     * @return New object with the subtracted coordinates.
     */
    public LocalXYCoord sub(final LocalXYCoord xy) {
        LocalXYCoord res = clone();
        res.setX(res.getX() - xy.getX());
        res.setY(res.getY() - xy.getY());
        return res;
    }

    /**
     * Multiplies the coordinates with a factor and store it in a new object.
     *
     * @param f multiplication factor
     * @return New object with the calculated coordinates
     */
    public LocalXYCoord mul(final double f) {
        LocalXYCoord res = clone();
        res.setX(res.getX() * f);
        res.setY(res.getY() * f);
        return res;
    }

    /**
     * Clones this object.
     *
     * @return New Object with the same values
     */
    @Override
    public LocalXYCoord clone() {
        return new LocalXYCoord(getX(), getY());
    }

    /**
     * Gives back the coordinates of this point as String.
     *
     * @return formated coordinates as String
     */
    @Override
    public String toString() {
        return String.format("xy : %6.2f / %6.2f", getX(), getY());
    }

    /**
     * Calculates the angle between two points and give it back in degree.
     *
     * @param b ssecond point for the calculation
     * @return Angle between the two points in degree
     */
    public double getAngleDeg(final LocalXYCoord b) {
        double own = Math.atan2(this.getY(), this.getX());
        double other = Math.atan2(b.getY(), b.getX());
        return MyGeoMath.alignDeg(MyGeoMath.rad2deg(other - own));
    }

    /**
     * Calculates the angle between two points and give it back in radian.
     *
     * @param b second point for the calculation
     * @return Angle between the two points in radian
     */
    public double getCos(final LocalXYCoord b) {
        double cos = (getX() * b.getX() + getY() * b.getY()) / (getAbs() * b.getAbs());
        return cos;
    }

    /**
     * Checks if the coordinates of another point are equal.
     *
     * @param obj other point to compare
     * @return true, if the given object has the same coordinates.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!LocalXYCoord.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final LocalXYCoord other = (LocalXYCoord) obj;

        return (getX() == other.getX() && getY() == other.getY());
    }


}
