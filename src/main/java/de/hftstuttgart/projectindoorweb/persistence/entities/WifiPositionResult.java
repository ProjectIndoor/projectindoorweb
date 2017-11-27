package de.hftstuttgart.projectindoorweb.persistence.entities;

import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LocXYZ;

import javax.persistence.Entity;

@Entity
public class WifiPositionResult extends PositionResult implements Comparable<WifiPositionResult> {

    private double weight;

    protected WifiPositionResult(){}

    public WifiPositionResult(double x, double y, double z, boolean wgs84, double weight) {
        super(x, y, z, wgs84);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(WifiPositionResult other) {
        if(this.weight > other.weight){
            return -1;
        }else if(this.weight < other.weight){
            return 1;
        }
        return 0;
    }
}
