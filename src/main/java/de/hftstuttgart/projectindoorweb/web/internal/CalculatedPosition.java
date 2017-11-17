package de.hftstuttgart.projectindoorweb.web.internal;

public class CalculatedPosition {

    private double x;
    private double y;
    private double z;

    private boolean transformedPosition;

    private String identifier;

    public CalculatedPosition(double x, double y, double z, boolean transformedPosition, String identifier) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.transformedPosition = transformedPosition;
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

    public boolean isTransformedPosition() {
        return transformedPosition;
    }

    public String getIdentifier() {
        return identifier;
    }
}
