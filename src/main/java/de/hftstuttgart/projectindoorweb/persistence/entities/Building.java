package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String buildingName;

    private int imagePixelWidth;
    private int imagePixelHeight;

    private double rotationAngle;
    private double metersPerPixel;

    @ManyToOne(targetEntity = Position.class, cascade = CascadeType.ALL)
    private Position northWest;

    @ManyToOne(targetEntity = Position.class, cascade = CascadeType.ALL)
    private Position northEast;

    @ManyToOne(targetEntity = Position.class, cascade = CascadeType.ALL)
    private Position southEast;

    @ManyToOne(targetEntity = Position.class, cascade = CascadeType.ALL)
    private Position southWest;

    @ManyToOne(targetEntity = Position.class, cascade = CascadeType.ALL)
    private Position centerPoint;

    @OneToMany(targetEntity = EvaalFile.class, mappedBy = "recordedInBuilding")
    private List<EvaalFile> evaalFiles;

    @OneToMany(targetEntity = Floor.class, cascade = CascadeType.ALL)
    private List<Floor> buildingFloors;

    protected Building(){}

    public Building(String buildingName, int numberOfFloors, int imagePixelWidth, int imagePixelHeight, double rotationAngle,
                    double metersPerPixel, Position northWest, Position northEast, Position southEast, Position southWest,
                    Position centerPoint) {

        this.buildingName = buildingName;
        this.imagePixelWidth = imagePixelWidth;
        this.imagePixelHeight = imagePixelHeight;
        this.rotationAngle = rotationAngle;
        this.metersPerPixel = metersPerPixel;
        this.northWest = northWest;
        this.northEast = northEast;
        this.southEast = southEast;
        this.southWest = southWest;
        this.centerPoint = centerPoint;
        initInitialFloors(numberOfFloors);
    }

    public Building(String buildingName, int imagePixelWidth, int imagePixelHeight, double rotationAngle, double metersPerPixel,
                    Position northWest, Position northEast, Position southEast, Position southWest, Position centerPoint,
                    List<EvaalFile> evaalFiles, List<Floor> buildingFloors) {

        this.buildingName = buildingName;
        this.imagePixelWidth = imagePixelWidth;
        this.imagePixelHeight = imagePixelHeight;
        this.rotationAngle = rotationAngle;
        this.metersPerPixel = metersPerPixel;
        this.northWest = northWest;
        this.northEast = northEast;
        this.southEast = southEast;
        this.southWest = southWest;
        this.centerPoint = centerPoint;
        this.evaalFiles = evaalFiles;
        this.buildingFloors = buildingFloors;
    }

    public Long getId() {
        return id;
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

    public Position getNorthWest() {
        return northWest;
    }

    public void setNorthWest(Position northWest) {
        this.northWest = northWest;
    }

    public Position getNorthEast() {
        return northEast;
    }

    public void setNorthEast(Position northEast) {
        this.northEast = northEast;
    }

    public Position getSouthEast() {
        return southEast;
    }

    public void setSouthEast(Position southEast) {
        this.southEast = southEast;
    }

    public Position getSouthWest() {
        return southWest;
    }

    public void setSouthWest(Position southWest) {
        this.southWest = southWest;
    }

    public Position getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(Position centerPoint) {
        this.centerPoint = centerPoint;
    }

    public List<EvaalFile> getEvaalFiles() {
        return evaalFiles;
    }

    public void setEvaalFiles(List<EvaalFile> evaalFiles) {
        this.evaalFiles = evaalFiles;
    }

    public List<Floor> getBuildingFloors() {
        return buildingFloors;
    }

    public void setBuildingFloors(List<Floor> buildingFloors) {
        this.buildingFloors = buildingFloors;
    }

    private void initInitialFloors(int numberOfFloors){

        this.buildingFloors = new ArrayList<>(numberOfFloors);
        for(int i = 0; i < numberOfFloors; i++){
            this.buildingFloors.add(i, new Floor(i));
        }

    }
}
