package de.hftstuttgart.projectindoorweb.geoCalculator.internal;

/**
 * Wrapper class for a XY point, that includes the building-number, the floor-number and a point-number.
 */
public class XYPoint {

    /**
     * Pointnumber.
     */
    private String pointNumber;
    /**
     * Coordinates of this point.
     */
    private LocalXYCoord coord;
    /**
     * Floor number for this point.
     */
    private int floor;
    /**
     * Building-number for this point.
     */
    private int building;

    /**
     * Standard-constructor.
     *
     * @param ptNumber    point-number of this point
     * @param xyCoord     coordinates of this point in as Latitude/Longitude
     * @param buildingNbr building in which the point is
     * @param floorNbr    floor oin which the point is
     */
    public XYPoint(final String ptNumber, final LocalXYCoord xyCoord, final int buildingNbr, final int floorNbr) {
        this.pointNumber = ptNumber;
        this.coord = xyCoord;
        this.building = buildingNbr;
        this.floor = floorNbr;
    }

    /**
     * Gives back the coordinates of the point.
     *
     * @return Coordinates as LocalXYCoord-Object
     */
    public LocalXYCoord getCoords() {
        return coord;
    }

    /**
     * Gives back the pointnumber of the point.
     *
     * @return point-number
     */
    public String getPointNumber() {
        return pointNumber;
    }

    /**
     * Gives back the building of the point.
     *
     * @return building-number
     */
    public int getBuilding() {
        return building;
    }

    /**
     * Gives back the floor-number of the point.
     *
     * @return floor number
     */
    public int getFloor() {
        return floor;
    }
}
