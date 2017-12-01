package de.hftstuttgart.projectindoorweb.web.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CalculatedPosition {

    private double x;
    private double y;
    private double z;

    private boolean wgs84;

    private String identifier;

    @JsonCreator
    public CalculatedPosition(@JsonProperty("x")double x, @JsonProperty("y")double y, @JsonProperty("z")double z, @JsonProperty("wgs84")boolean wgs84, @JsonProperty("identifier")String identifier) {
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
