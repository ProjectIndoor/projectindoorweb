package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double x;
    private double y;
    private double z;
    private boolean wgs84;

    public Position(){
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.wgs84 = true;
    }

    public Position(Position source){
        this.x = source.getX();
        this.y = source.getY();
        this.z = source.getZ();
        this.setWgs84(source.isWgs84());
    }

    public Position(double x, double y, double z, boolean wgs84) {
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
