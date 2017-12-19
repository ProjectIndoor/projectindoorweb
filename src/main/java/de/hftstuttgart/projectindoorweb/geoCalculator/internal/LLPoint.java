package de.hftstuttgart.projectindoorweb.geoCalculator.internal;

public class LLPoint {

    private String pointNumber;
    private LatLongCoord coord;
    private int floor;
    private int building;


    public LLPoint(String pointNumber, LatLongCoord coord, int building, int floor) {
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

    public int getBuilding() {
        return building;
    }

    public int getFloor() {
        return floor;
    }
}

