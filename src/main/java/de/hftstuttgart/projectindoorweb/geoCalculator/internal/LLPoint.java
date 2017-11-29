package de.hftstuttgart.projectindoorweb.geoCalculator.internal;

public class LLPoint {

    private String pointNumber;
    private LatLongCoord coord;
    private double floor;
    private double building;


    public LLPoint(String pointNumber, LatLongCoord coord, double building, double floor) {
        this.pointNumber = pointNumber;
        this.coord = coord;
        this.building = building;
        this.floor = floor;
    }

    public LatLongCoord getCoords() {
        return coord;
    }

    public String getPointNumber() {
        return pointNumber;
    }

    public double getBuilding() {
        return building;
    }

    public double getFloor() {
        return floor;
    }
}

