package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int level;

    private String floorMapUrl;

    @OneToMany(targetEntity = PosiReference.class, mappedBy = "floor")
    private List<PosiReference> posiReferences;

    protected Floor(){}


    public Floor(int level, String floorMapUrl) {
        this.level = level;
        this.floorMapUrl = floorMapUrl;
    }

    public Floor(int level) {
        this.level = level;
        this.floorMapUrl = null;
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

    public String getFloorMapUrl() {
        return floorMapUrl;
    }

    public void setFloorMapUrl(String floorMapUrl) {
        this.floorMapUrl = floorMapUrl;
    }

    public List<PosiReference> getPosiReferences() {
        return posiReferences;
    }

    public void setPosiReferences(List<PosiReference> posiReferences) {
        this.posiReferences = posiReferences;
    }
}
