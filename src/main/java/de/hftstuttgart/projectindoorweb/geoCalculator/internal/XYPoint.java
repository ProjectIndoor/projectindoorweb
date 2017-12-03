package de.hftstuttgart.projectindoorweb.geoCalculator.internal;

public class XYPoint {

    private String pointNumber;
    private LocalXYCoord coord;
    private double floor;
    private double building;


    public XYPoint(String pointNumber, LocalXYCoord coord, double building, double floor) {
        this.pointNumber = pointNumber;
        this.coord = coord;
        this.building = building;
        this.floor = floor;
    }

    public LocalXYCoord getCoords() {
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
