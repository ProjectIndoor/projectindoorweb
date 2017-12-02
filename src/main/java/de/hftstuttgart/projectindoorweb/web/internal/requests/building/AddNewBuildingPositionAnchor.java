package de.hftstuttgart.projectindoorweb.web.internal.requests.building;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddNewBuildingPositionAnchor {
    private double longitude;
    private double latitude;

    @JsonCreator
    public AddNewBuildingPositionAnchor(@JsonProperty("longitude")double longitude, @JsonProperty("latitude")double latitude) {
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
