package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int level;


    @OneToOne(targetEntity = FloorMap.class, cascade = CascadeType.ALL)
    private FloorMap floorMap;

    @OneToMany(targetEntity = PosiReference.class, mappedBy = "floor")
    private List<PosiReference> posiReferences;

    protected Floor(){}


    public Floor(int level, FloorMap floorMap) {
        this.level = level;
        this.floorMap = floorMap;
    }

    public Floor(int level) {
        this.level = level;
        this.floorMap = null;
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

    public FloorMap getFloorMap() {
        return floorMap;
    }

    public void setFloorMap(FloorMap floorMap) {
        this.floorMap = floorMap;
    }

    public List<PosiReference> getPosiReferences() {
        return posiReferences;
    }

    public void setPosiReferences(List<PosiReference> posiReferences) {
        this.posiReferences = posiReferences;
    }
}
