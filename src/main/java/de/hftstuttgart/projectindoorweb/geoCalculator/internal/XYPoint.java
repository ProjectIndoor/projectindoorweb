package de.hftstuttgart.projectindoorweb.geoCalculator.internal;

public class XYPoint {

    private String pointNumber;
    private LocalXYCoord coord;
    private int floor;
    private int building;


    public XYPoint(String pointNumber, LocalXYCoord coord, int building, int floor) {
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

    public int getBuilding() {
        return building;
    }

    public int getFloor() {
        return floor;
    }
}
