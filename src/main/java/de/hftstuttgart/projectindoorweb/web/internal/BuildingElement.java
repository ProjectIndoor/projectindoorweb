package de.hftstuttgart.projectindoorweb.web.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BuildingElement {
    private Long id;

    private String buildingName;

    private long floorCount;

    @JsonCreator
    public BuildingElement(@JsonProperty("id")Long id, @JsonProperty("buildingName")String buildingName, @JsonProperty("floorCount")long floorCount){
        this.id = id;
        this.buildingName = buildingName;
        this.floorCount = floorCount;
    }

    public Long getId() {
        return id;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public long getFloorCount() {
        return floorCount;
    }
}
