package de.hftstuttgart.projectindoorweb.maths;

public class WeightedLocXYZ extends LocXYZ implements Comparable<WeightedLocXYZ> {

    public double weight;

    public WeightedLocXYZ() {
        super();
        this.weight = 0.;
    }

    public WeightedLocXYZ(LocXYZ r, double weight) {
        super(r);
        this.weight = weight;
    }

    public WeightedLocXYZ(WeightedLocXYZ r) {
        super(r);
        this.weight = r.weight;
    }

    @Override
    public int compareTo(WeightedLocXYZ c) {
        if (weight - c.weight > 0) {
            return 1;
        }
        if (weight - c.weight == 0) {
            return 0;
        }
        return -1;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" W: %.2f  ", weight);
    }

    @Override
    public WeightedLocXYZ clone() {
        return new WeightedLocXYZ(this);
    }

    public WeightedLocXYZ mul(double f) {
        return new WeightedLocXYZ(super.mul(f), this.weight);
    }

    public WeightedLocXYZ add(WeightedLocXYZ other) {
        return new WeightedLocXYZ(super.add(other), (this.weight + other.weight) / 2);
    }

}
