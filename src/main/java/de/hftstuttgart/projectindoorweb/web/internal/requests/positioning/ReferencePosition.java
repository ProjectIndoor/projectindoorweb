package de.hftstuttgart.projectindoorweb.web.internal.requests.positioning;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReferencePosition {

    private double x;
    private double y;
    private double z;

    private boolean wgs84;

    @JsonCreator
    public ReferencePosition(@JsonProperty("x") double x,
                              @JsonProperty("y") double y,
                              @JsonProperty("z") double z,
                              @JsonProperty("wgs84") boolean wgs84) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.wgs84 = wgs84;
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
