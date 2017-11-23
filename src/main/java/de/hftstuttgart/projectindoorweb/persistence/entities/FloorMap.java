package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FloorMap {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private byte[] mapContent;

    protected FloorMap(){}

    public FloorMap(byte[] mapContent) {
        this.mapContent = mapContent;
    }

    public Long getId() {
        return id;
    }

    public byte[] getMapContent() {
        return mapContent;
    }

    public void setMapContent(byte[] mapContent) {
        this.mapContent = mapContent;
    }
}
