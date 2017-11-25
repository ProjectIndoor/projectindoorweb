package de.hftstuttgart.projectindoorweb.web.internal;

public class BuildingElement {
    private Long id;

    private String buildingName;

    private long floorCount;

    public BuildingElement(Long id, String buildingName, long floorCount){
        this.id = id;
        this.buildingName = buildingName;
        this.floorCount = floorCount;
    }

    public Long getId() {
        return id;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public long getFloorCount() {
        return floorCount;
    }
}
