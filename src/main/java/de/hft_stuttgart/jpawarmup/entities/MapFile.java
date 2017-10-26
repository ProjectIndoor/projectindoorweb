package de.hft_stuttgart.jpawarmup.entities;

import javax.persistence.Entity;

@Entity
public class MapFile extends LocalFile{

    private byte[] imgData;

    protected MapFile(){}

    public MapFile(String id, String sourceFileName, long lastModified, byte[] imgData) {
        super(id, sourceFileName, lastModified);
        this.imgData = imgData;
    }

    public byte[] getImgData() {
        return imgData;
    }

    public void setImgData(byte[] imgData) {
        this.imgData = imgData;
    }
}
