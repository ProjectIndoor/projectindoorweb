package de.hftstuttgart.projectindoorweb.web.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BuildingJsonWrapperSmall {

    private Long id;

    private String buildingName;

    private int numberOfFloors;

    @JsonCreator
    public BuildingJsonWrapperSmall(@JsonProperty("id")Long id,
                                    @JsonProperty("buildingName")String buildingName,
                                    @JsonProperty("numberOfFloors")int numberOfFloors){
        this.id = id;
        this.buildingName = buildingName;
        this.numberOfFloors = numberOfFloors;
    }

    public Long getId() {
        return id;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public int getFloorCount() {
        return numberOfFloors;
    }
}
