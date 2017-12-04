package de.hftstuttgart.projectindoorweb.web.internal.requests.building;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BuildingPositionAnchor {

    private double latitude;
    private double longitude;


    @JsonCreator
    public BuildingPositionAnchor(@JsonProperty("latitude")double latitude,
                                  @JsonProperty("longitude")double longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }


}
