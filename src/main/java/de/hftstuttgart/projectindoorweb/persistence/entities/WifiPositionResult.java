package de.hftstuttgart.projectindoorweb.persistence.entities;

import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LocXYZ;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class WifiPositionResult extends PositionResult implements Comparable<WifiPositionResult> {

    private double weight;
    private double rssiSignalsAppTimestamp;

    @ManyToOne(targetEntity = PosiReference.class, cascade = CascadeType.ALL)
    private PosiReference posiReference;

    protected WifiPositionResult(){}

    public WifiPositionResult(double x, double y, double z, boolean wgs84, double weight) {
        super(x, y, z, wgs84);
        this.weight = weight;
        this.rssiSignalsAppTimestamp = 0.0;
    }

    public WifiPositionResult(double x, double y, double z, boolean wgs84, double weight, double rssiSignalsAppTimestamp) {
        super(x, y, z, wgs84);
        this.weight = weight;
        this.rssiSignalsAppTimestamp = rssiSignalsAppTimestamp;
    }

    public double getRssiSignalsAppTimestamp() {
        return rssiSignalsAppTimestamp;
    }

    public void setRssiSignalsAppTimestamp(double rssiSignalsAppTimestamp) {
        this.rssiSignalsAppTimestamp = rssiSignalsAppTimestamp;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public PosiReference getPosiReference() {
        return posiReference;
    }

    public void setPosiReference(PosiReference posiReference) {
        this.posiReference = posiReference;
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
