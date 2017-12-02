package de.hftstuttgart.projectindoorweb.web.internal.requests.building;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddNewBuilding {

    private String buildingName;
    private int numberOfFloors;
    private int imagePixelWidth;
    private int imagePixelHeight;
    private AddNewBuildingPositionAnchor northWest;
    private AddNewBuildingPositionAnchor northEast;
    private AddNewBuildingPositionAnchor southEast;
    private AddNewBuildingPositionAnchor southWest;

    @JsonCreator
    public AddNewBuilding(@JsonProperty("buildingName") String buildingName,
                          @JsonProperty("numberOfFloors") int numberOfFloors,
                          @JsonProperty("imagePixelWidth") int imagePixelWidth,
                          @JsonProperty("imagePixelHeight") int imagePixelHeight,
                          @JsonProperty("northWestAnchor") AddNewBuildingPositionAnchor northWest,
                          @JsonProperty("northEastAnchor") AddNewBuildingPositionAnchor northEast,
                          @JsonProperty("southEastAnchor") AddNewBuildingPositionAnchor southEast,
                          @JsonProperty("southWestAnchor") AddNewBuildingPositionAnchor southWest) {

        this.buildingName = buildingName;
        this.numberOfFloors = numberOfFloors;
        this.imagePixelWidth = imagePixelWidth;
        this.imagePixelHeight = imagePixelHeight;
        this.northWest = northWest;
        this.northEast = northEast;
        this.southEast = southEast;
        this.southWest = southWest;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
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

    public AddNewBuildingPositionAnchor getNorthWest() {
        return northWest;
    }

    public void setNorthWest(AddNewBuildingPositionAnchor northWest) {
        this.northWest = northWest;
    }

    public AddNewBuildingPositionAnchor getNorthEast() {
        return northEast;
    }

    public void setNorthEast(AddNewBuildingPositionAnchor northEast) {
        this.northEast = northEast;
    }

    public AddNewBuildingPositionAnchor getSouthEast() {
        return southEast;
    }

    public void setSouthEast(AddNewBuildingPositionAnchor southEast) {
        this.southEast = southEast;
    }

    public AddNewBuildingPositionAnchor getSouthWest() {
        return southWest;
    }

    public void setSouthWest(AddNewBuildingPositionAnchor southWest) {
        this.southWest = southWest;
    }
}
