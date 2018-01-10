package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int level;

    private String floorName;
    private String floorMapUrl;
    private String floorMapPath;

    protected Floor() {
    }

    public Floor(int level, String floorName, String floorMapUrl, String floorMapPath) {
        this.level = level;
        this.floorName = floorName;
        this.floorMapUrl = floorMapUrl;
        this.floorMapPath = floorMapPath;
    }

    public Floor(int level) {
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public String getFloorMapPath() {
        return floorMapPath;
    }

    public void setFloorMapPath(String floorMapPath) {
        this.floorMapPath = floorMapPath;
    }

}
