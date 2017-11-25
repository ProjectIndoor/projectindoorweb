package de.hftstuttgart.projectindoorweb.web.internal;

public class PositionAnchor {
    private double longitude;
    private double latitude;

    public PositionAnchor(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
