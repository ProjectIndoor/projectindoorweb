package de.hftstuttgart.projectindoorweb.web.internal;

public class CalculatedPosition {

    private double x;
    private double y;
    private double z;

    private boolean wgs84;

    private String identifier;

    public CalculatedPosition(double x, double y, double z, boolean wgs84, String identifier) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.wgs84 = wgs84;
        this.identifier=identifier;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public boolean isWgs84() {
        return wgs84;
    }

    public String getIdentifier() {
        return identifier;
    }

}
