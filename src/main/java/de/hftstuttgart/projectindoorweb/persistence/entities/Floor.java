package de.hftstuttgart.projectindoorweb.persistence.entities;

import java.util.List;

public class Floor extends ModelBase {

    private int level;
    private FloorMap floorMap;

    public Floor(int level, FloorMap floorMap) {
        this.level = level;
        this.floorMap = floorMap;
    }

    public Floor(int level) {
        this.level = level;
        this.floorMap = null;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public FloorMap getFloorMap() {
        return floorMap;
    }

    public void setFloorMap(FloorMap floorMap) {
        this.floorMap = floorMap;
    }
}
