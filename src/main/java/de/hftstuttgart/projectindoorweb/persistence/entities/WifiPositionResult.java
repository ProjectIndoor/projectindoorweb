package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class WifiPositionResult extends PositionResult {

    private double weight;

    protected WifiPositionResult(){
    }

    public WifiPositionResult(double x, double y, double z, boolean transformedPosition, double weight) {
        super(x, y, z, transformedPosition);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

}
