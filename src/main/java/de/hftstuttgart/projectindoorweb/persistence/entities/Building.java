package de.hftstuttgart.projectindoorweb.persistence.entities;

import java.util.List;

public class Building extends ModelBase {

    private String buildingName;
    private Position southEastAnchor;
    private Position northWestAnchor;

    private List<LogFile> belongigLogFiles;

    private List<Floor> buildingFloors;

}
