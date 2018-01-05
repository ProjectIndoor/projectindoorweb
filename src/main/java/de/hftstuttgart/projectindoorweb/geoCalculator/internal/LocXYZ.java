package de.hftstuttgart.projectindoorweb.geoCalculator.internal;

/**
 * Class that represents a point in a local mathematical 3D-coordinate-system.
 *
 * @author stefan
 */
public class LocXYZ extends LocalXYCoord {

    /**
     * Height/floor-level of this point.
     */
    private double vZ = 0;

    /**
     * Returns the height/floor-level of the point.
     *
     * @return height/floor-level of the point.
     */
    public double getZ() {
        return vZ;
    }

    /**
     * Sets the height of the point.
     *
     * @param v height of the point
     */
    public void setZ(final double v) {
        this.vZ = v;
    }

    /**
     * Standard-constructor.
     */
    public LocXYZ() {
        super();
        setZ(0);
    }

    /**
     * Constructor.
     * Generates point with the height zero.
     *
     * @param xy xy-Coordinates
     */
    public LocXYZ(final LocalXYCoord xy) {
        super(xy.getX(), xy.getY());
        this.setZ(0);
    }

    /**
     * Constructor.
     *
     * @param localXYCoord xy-coordinates
     * @param height       height of the new point
     */
    public LocXYZ(final LocalXYCoord localXYCoord, final double height) {
        super(localXYCoord.getX(), localXYCoord.getY());
        this.setZ(height);
    }


    /**
     * Constructor.
     *
     * @param x x-value of the point
     * @param y y-value of the point
     * @param z z-value of the point
     */
    public LocXYZ(final double x, final double y, final double z) {
        super(x, y);
        this.setZ(z);
    }

    /**
     * Copy-Constructor.
     *
     * @param r point that should be copied.
     */
    public LocXYZ(final LocXYZ r) {
        this.setX(r.getX());
        this.setY(r.getY());
        this.setZ(r.getZ());
    }

    /**
     * Calculates the distance from this point to the origin of the coordinate-system in a 3D-coordinate-system.
     *
     * @return Distance to the origin of the coordinate-system
     */
    public double getAbs() {
        return Math.sqrt(getX() * getX() + getY() * getY() + getZ() * getZ());
    }

    /**
     * Calculates the distance from this point to the origin of the coordinate-system in a 2D-coordinate-system.
     *
     * @return Distance to the origin of the coordinate-system
     */
    public double getAbsXY() {
        return super.getAbs();
    }

    /**
     * Adds the coordinates of another point to this point and store it in a new object.
     *
     * @param r coordinates that should be added.
     * @return New object with the added coordinates.
     */
    public LocXYZ add(final LocXYZ r) {
        LocXYZ res = clone();
        res.setX(res.getX() + r.getX());
        res.setY(res.getY() + r.getY());
        res.setZ(res.getZ() + r.getZ());
        return res;
    }

    /**
     * Subtracts the coordinates of another point to this point and store it in a new object.
     *
     * @param r coordinates that should be subtracted.
     * @return New object with the subtracted coordinates.
     */
    public LocXYZ sub(final LocXYZ r) {
        LocXYZ res = clone();
        res.setX(res.getX() - r.getX());
        res.setY(res.getY() - r.getY());
        res.setZ(res.getZ() - r.getZ());
        return res;
    }

    /**
     * Multiplies the coordinates with a factor and store it in a new object.
     *
     * @param f multiplication factor
     * @return New object with the calculated coordinates
     */
    public LocXYZ mul(final double f) {
        LocXYZ res = clone();
        res.setX(res.getX() * f);
        res.setY(res.getY() * f);
        res.setZ(res.getZ() * f);
        return res;
    }

    /**
     * Calculates the distance between two points.
     *
     * @param p2 secons point for the calulation
     * @return Distance between the two point
     */
    public double getDist(final LocXYZ p2) {
        return this.sub(p2).getAbs();
    }

    /**
     * Clones this object.
     *
     * @return New Object with the same values
     */
    @Override
    public LocXYZ clone() {
        return new LocXYZ(getX(), getY(), getZ());
    }

    /**
     * Gives back the coordinates of this point as String.
     *
     * @return Formated coordinates as String
     */
    @Override
    public String toString() {
        return String.format("xyz : %6.2f / %6.2f / %6.2f ", getX(), getY(), getZ());
    }


}
