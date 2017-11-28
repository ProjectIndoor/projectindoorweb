package de.hftstuttgart.projectindoorweb.web.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BuildingJsonWrapperLarge {

    private String buildingName;
    private int numberOfFloors;
    private int imagePixelWidth;
    private int imagePixelHeight;
    private PositionAnchor northWest;
    private PositionAnchor northEast;
    private PositionAnchor southEast;
    private PositionAnchor southWest;

    @JsonCreator
    public BuildingJsonWrapperLarge(@JsonProperty("buildingName") String buildingName,
                                    @JsonProperty("numberOfFloors") int numberOfFloors,
                                    @JsonProperty("imagePixelWidth") int imagePixelWidth,
                                    @JsonProperty("imagePixelHeight") int imagePixelHeight,
                                    @JsonProperty("northWestAnchor") PositionAnchor northWest,
                                    @JsonProperty("northEastAnchor") PositionAnchor northEast,
                                    @JsonProperty("southEastAnchor") PositionAnchor southEast,
                                    @JsonProperty("southWestAnchor") PositionAnchor southWest) {

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

    public PositionAnchor getNorthWest() {
        return northWest;
    }

    public void setNorthWest(PositionAnchor northWest) {
        this.northWest = northWest;
    }

    public PositionAnchor getNorthEast() {
        return northEast;
    }

    public void setNorthEast(PositionAnchor northEast) {
        this.northEast = northEast;
    }

    public PositionAnchor getSouthEast() {
        return southEast;
    }

    public void setSouthEast(PositionAnchor southEast) {
        this.southEast = southEast;
    }

    public PositionAnchor getSouthWest() {
        return southWest;
    }

    public void setSouthWest(PositionAnchor southWest) {
        this.southWest = southWest;
    }
}
