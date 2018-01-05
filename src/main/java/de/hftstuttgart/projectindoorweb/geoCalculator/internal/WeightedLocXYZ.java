package de.hftstuttgart.projectindoorweb.geoCalculator.internal;

/**
 * Class that represents a point in local mathematical 3D-coordinate-system with a given weight for further calculations.
 *
 * @author stefan
 */
public class WeightedLocXYZ extends LocXYZ implements Comparable<WeightedLocXYZ> {

    /**
     * Weight of this point used for some calculations.
     */
    private double vWeight;

    /**
     * Returns the weight of the point.
     *
     * @return eight of the point.
     */
    public double getWeight() {
        return vWeight;
    }

    /**
     * Sets the weight of the point.
     *
     * @param v weight of the point
     */
    public void setWeight(final double v) {
        this.vWeight = v;
    }

    /**
     * Standard-constructor with weight zero.
     */
    public WeightedLocXYZ() {
        super();
        this.setWeight(0.);
    }

    /**
     * Constructor.
     *
     * @param r      local xyz point
     * @param weight weight
     */
    public WeightedLocXYZ(final LocXYZ r, final double weight) {
        super(r);
        this.setWeight(weight);
    }

    /**
     * Copy-constructor.
     *
     * @param r point that should be copied.
     */
    public WeightedLocXYZ(final WeightedLocXYZ r) {
        super(r);
        this.setWeight(r.getWeight());
    }

    /**
     * Compares the weights of the two points.
     *
     * @param c point to compare to
     * @return 0 - weight is equal
     * 1 - weight of this point it higher
     * -1 - weight or this point is lower
     */
    @Override
    public int compareTo(final WeightedLocXYZ c) {
        if (getWeight() - c.getWeight() > 0) {
            return 1;
        }
        if (getWeight() - c.getWeight() == 0) {
            return 0;
        }
        return -1;
    }

    /**
     * Gives back the coordinates of this point as String.
     *
     * @return Formated coordinates as String
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" W: %.2f  ", getWeight());
    }

    /**
     * Clones this object.
     *
     * @return New Object with the same values
     */
    @Override
    public WeightedLocXYZ clone() {
        return new WeightedLocXYZ(this);
    }

    /**
     * Multiplies the coordinates with a factor and store it in a new object.
     * the weight will not be multiplied
     *
     * @param f multiplication factor
     * @return New object with the calculated coordinates
     */
    public WeightedLocXYZ mul(final double f) {
        return new WeightedLocXYZ(super.mul(f), this.getWeight());
    }

    /**
     * Adds the coordinates of another point to this point and store it in a new object.
     * The weight will be averaged.
     *
     * @param other coordinates and weight that should be added.
     * @return New object with the added coordinates and weight.
     */
    public WeightedLocXYZ add(final WeightedLocXYZ other) {
        return new WeightedLocXYZ(super.add(other), (this.getWeight() + other.getWeight()) / 2);
    }


}
