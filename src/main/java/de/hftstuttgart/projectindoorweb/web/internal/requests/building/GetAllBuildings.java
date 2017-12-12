package de.hftstuttgart.projectindoorweb.web.internal.requests.building;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetAllBuildings {

    private Long buildingId;

    private String buildingName;

    private int imagePixelWidth;
    private int imagePixelHeight;

    private List<GetFloor> buildingFloors;

    @JsonCreator
    public GetAllBuildings(@JsonProperty("buildingId") Long buildingId,
                           @JsonProperty("buildingName") String buildingName,
                           @JsonProperty("imagePixelWidth") int imagePixelWidth,
                           @JsonProperty("imagePixelHeight") int imagePixelHeight,
                           @JsonProperty("buildingFloors") List<GetFloor> buildingFloors) {
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.imagePixelWidth = imagePixelWidth;
        this.imagePixelHeight = imagePixelHeight;
        this.buildingFloors = buildingFloors;
    }

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getImagePixelWidth() {
        return imagePixelWidth;
    }

    public void setImagePixelWidth(int imagePixelWidth) {
        this.imagePixelWidth = imagePixelWidth;
    }

    public int getImagePixelHeight() {
        return imagePixelHeight;
    }

    public void setImagePixelHeight(int imagePixelHeight) {
        this.imagePixelHeight = imagePixelHeight;
    }

    public List<GetFloor> getBuildingFloors() {
        return buildingFloors;
    }

    public void setBuildingFloors(List<GetFloor> buildingFloors) {
        this.buildingFloors = buildingFloors;
    }
}
