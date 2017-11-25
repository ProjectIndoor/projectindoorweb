package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PositionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double x;
    private double y;
    private double z;
    private boolean transformedPosition;

    protected PositionResult(){}

    public PositionResult(double x, double y, double z, boolean transformedPosition) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.transformedPosition = transformedPosition;
    }


    public long getId() {
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

    public boolean isTransformedPosition() {
        return transformedPosition;
    }

    public void setTransformedPosition(boolean transformedPosition) {
        this.transformedPosition = transformedPosition;
    }
}
