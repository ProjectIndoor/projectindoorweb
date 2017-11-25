package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String buildingName;

    @ManyToOne(targetEntity = Position.class, cascade = CascadeType.ALL)
    private Position northWest;

    @ManyToOne(targetEntity = Position.class, cascade = CascadeType.ALL)
    private Position northEast;

    @ManyToOne(targetEntity = Position.class, cascade = CascadeType.ALL)
    private Position southEast;

    @ManyToOne(targetEntity = Position.class, cascade = CascadeType.ALL)
    private Position southWest;

    @OneToMany(targetEntity = RadioMap.class)
    private List<RadioMap> belongingMaps;

    @OneToMany(targetEntity = Floor.class)
    private List<Floor> buildingFloors;

    public Building(){}

    public Building(String buildingName, Position northWest, Position northEast, Position southEast, Position southWest,
                    List<RadioMap> belongingMaps, List<Floor> buildingFloors) {
        this.buildingName = buildingName;
        this.northWest = northWest;
        this.northEast = northEast;
        this.southEast = southEast;
        this.southWest = southWest;
        this.belongingMaps = belongingMaps;
        this.buildingFloors = buildingFloors;
    }

    public Long getId() {
        return id;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Position getNorthWest() {
        return northWest;
    }

    public void setNorthWest(Position northWest) {
        this.northWest = northWest;
    }

    public Position getNorthEast() {
        return northEast;
    }

    public void setNorthEast(Position northEast) {
        this.northEast = northEast;
    }

    public Position getSouthEast() {
        return southEast;
    }

    public void setSouthEast(Position southEast) {
        this.southEast = southEast;
    }

    public Position getSouthWest() {
        return southWest;
    }

    public void setSouthWest(Position southWest) {
        this.southWest = southWest;
    }

    public List<RadioMap> getBelongingMaps() {
        return belongingMaps;
    }

    public void setBelongingMaps(List<RadioMap> belongingMaps) {
        this.belongingMaps = belongingMaps;
    }

    public List<Floor> getBuildingFloors() {
        return buildingFloors;
    }

    public void setBuildingFloors(List<Floor> buildingFloors) {
        this.buildingFloors = buildingFloors;
    }
}
