package de.hftstuttgart.projectindoorweb.web.internal.requests.building;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetSingleBuilding {

    private long buildingId;
    private String buildingName;
    private int numberOfFloors;
    private int imagePixelWidth;
    private int imagePixelHeight;
    private BuildingPositionAnchor northWest;
    private BuildingPositionAnchor northEast;
    private BuildingPositionAnchor southEast;
    private BuildingPositionAnchor southWest;
    private BuildingPositionAnchor buildingCenterPoint;
    private double rotationAngle;
    private double metersPerPixel;
    private List<GetSingleBuildingEvaalFile> evaalFiles;
    private List<GetFloor> buildingFloors;

    @JsonCreator
    public GetSingleBuilding(@JsonProperty("buildingId") long buildingId,
                             @JsonProperty("buildingName") String buildingName,
                             @JsonProperty("numberOfFloors") int numberOfFloors,
                             @JsonProperty("imagePixelWidth") int imagePixelWidth,
                             @JsonProperty("imagePixelHeight") int imagePixelHeight,
                             @JsonProperty("northWestAnchor") BuildingPositionAnchor northWestAnchor,
                             @JsonProperty("northEastAnchor") BuildingPositionAnchor northEastAnchor,
                             @JsonProperty("southEastAnchor") BuildingPositionAnchor southEastAnchor,
                             @JsonProperty("southWestAnchor") BuildingPositionAnchor southWestAnchor,
                             @JsonProperty("buildingCenterPoint") BuildingPositionAnchor buildingCenterPoint,
                             @JsonProperty("rotationAngle") double rotationAngle,
                             @JsonProperty("metersPerPixel") double metersPerPixel,
                             @JsonProperty("evaalFiles") List<GetSingleBuildingEvaalFile> evaalFiles,
                             @JsonProperty("buildingFloors") List<GetFloor> buildingFloors) {

        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.numberOfFloors = numberOfFloors;
        this.imagePixelWidth = imagePixelWidth;
        this.imagePixelHeight = imagePixelHeight;
        this.northWest = northWestAnchor;
        this.northEast = northEastAnchor;
        this.southEast = southEastAnchor;
        this.southWest = southWestAnchor;
        this.buildingCenterPoint = buildingCenterPoint;
        this.rotationAngle = rotationAngle;
        this.metersPerPixel = metersPerPixel;
        this.evaalFiles = evaalFiles;
        this.buildingFloors = buildingFloors;
    }

    public long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
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

    public BuildingPositionAnchor getNorthWest() {
        return northWest;
    }

    public void setNorthWest(BuildingPositionAnchor northWest) {
        this.northWest = northWest;
    }

    public BuildingPositionAnchor getNorthEast() {
        return northEast;
    }

    public void setNorthEast(BuildingPositionAnchor northEast) {
        this.northEast = northEast;
    }

    public BuildingPositionAnchor getSouthEast() {
        return southEast;
    }

    public void setSouthEast(BuildingPositionAnchor southEast) {
        this.southEast = southEast;
    }

    public BuildingPositionAnchor getSouthWest() {
        return southWest;
    }

    public void setSouthWest(BuildingPositionAnchor southWest) {
        this.southWest = southWest;
    }

    public BuildingPositionAnchor getBuildingCenterPoint() {
        return buildingCenterPoint;
    }

    public void setBuildingCenterPoint(BuildingPositionAnchor buildingCenterPoint) {
        this.buildingCenterPoint = buildingCenterPoint;
    }

    public double getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public double getMetersPerPixel() {
        return metersPerPixel;
    }

    public void setMetersPerPixel(double metersPerPixel) {
        this.metersPerPixel = metersPerPixel;
    }

    public List<GetSingleBuildingEvaalFile> getEvaalFiles() {
        return evaalFiles;
    }

    public void setEvaalFiles(List<GetSingleBuildingEvaalFile> evaalFiles) {
        this.evaalFiles = evaalFiles;
    }

    public List<GetFloor> getBuildingFloors() {
        return buildingFloors;
    }

    public void setBuildingFloors(List<GetFloor> buildingFloors) {
        this.buildingFloors = buildingFloors;
    }
}
