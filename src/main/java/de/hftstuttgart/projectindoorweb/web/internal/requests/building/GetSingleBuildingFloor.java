package de.hftstuttgart.projectindoorweb.web.internal.requests.building;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetSingleBuildingFloor {

    private long floorId;
    private int floorLevel;
    private String floorMapUrl;

    @JsonCreator
    public GetSingleBuildingFloor(@JsonProperty("floorId") long floorId,
                                  @JsonProperty("floorLevel") int floorLevel,
                                  @JsonProperty("floorMapUrl") String floorMapUrl) {
        this.floorId = floorId;
        this.floorLevel = floorLevel;
        this.floorMapUrl = floorMapUrl;
    }

    public long getFloorId() {
        return floorId;
    }

    public void setFloorId(long floorId) {
        this.floorId = floorId;
    }

    public int getFloorLevel() {
        return floorLevel;
    }

    public void setFloorLevel(int floorLevel) {
        this.floorLevel = floorLevel;
    }

    public String getFloorMapUrl() {
        return floorMapUrl;
    }

    public void setFloorMapUrl(String floorMapUrl) {
        this.floorMapUrl = floorMapUrl;
    }
}
