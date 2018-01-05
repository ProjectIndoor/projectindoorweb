package de.hftstuttgart.projectindoorweb.geoCalculator.internal;

/**
 * Wrapper class for a Latitude/Longitude point, that includes the building-number, the floor-number and a point-number.
 */
public class LLPoint {

    /**
     * Pointnumber.
     */
    private String pointNumber;
    /**
     * Coordinates of this point.
     */
    private LatLongCoord coord;
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
     * @param ptNumber     point-number of this point
     * @param latLongCoord coordinates of this point in as Latitude/Longitude
     * @param buildingNbr  building in which the point is
     * @param flooNbrr     floor oin which the point is
     */
    public LLPoint(final String ptNumber, final LatLongCoord latLongCoord, final int buildingNbr, final int flooNbrr) {
        this.pointNumber = ptNumber;
        this.coord = latLongCoord;
        this.building = buildingNbr;
        this.floor = flooNbrr;
    }

    /**
     * Gives back the coordinates of the point.
     *
     * @return Coordinates as LogtLongCoord-Object
     */
    public LatLongCoord getCoords() {
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

