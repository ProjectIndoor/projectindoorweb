package de.hftstuttgart.projectindoorweb.web.internal.requests.building;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetFloor {

    private long floorId;

    private int floorLevel;

    private String floorName;
    private String floorMapUrl;

    @JsonCreator
    public GetFloor(@JsonProperty("floorId") long floorId,
                    @JsonProperty("floorLevel") int floorLevel,
                    @JsonProperty("floorName") String floorName,
                    @JsonProperty("floorMapUrl") String floorMapUrl) {
        this.floorId = floorId;
        this.floorLevel = floorLevel;
        this.floorName = floorName;
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

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getFloorMapUrl() {
        return floorMapUrl;
    }

    public void setFloorMapUrl(String floorMapUrl) {
        this.floorMapUrl = floorMapUrl;
    }
}
