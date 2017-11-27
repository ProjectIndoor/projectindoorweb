package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class PositionResult {

    @Id
    @GeneratedValue
    private Long id;

    private double x;
    private double y;
    private double z;
    private boolean wgs84;

    protected PositionResult(){}

    public PositionResult(double x, double y, double z, boolean wgs84) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.wgs84 = wgs84;
    }


    public Long getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public boolean isWgs84() {
        return wgs84;
    }

    public void setWgs84(boolean wgs84) {
        this.wgs84 = wgs84;
    }

}
