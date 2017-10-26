package de.hft_stuttgart.jpawarmup.entities;

import javax.persistence.Entity;

@Entity
public class MagnetometerData extends SensorData{

    private long sensorTimestamp;
    private float magnX;
    private float magnY;
    private float magnZ;
    private String dataUnit;
    private int accuracy;

    protected MagnetometerData(){}

    public MagnetometerData(long sensorTimestamp, float magnX, float magnY, float magnZ, String dataUnit, int accuracy) {
        this.sensorTimestamp = sensorTimestamp;
        this.magnX = magnX;
        this.magnY = magnY;
        this.magnZ = magnZ;
        this.dataUnit = dataUnit;
        this.accuracy = accuracy;
    }

    public MagnetometerData(String rawName, int appTimestamp, long sensorTimestamp, float magnX, float magnY, float magnZ, String dataUnit, int accuracy) {
        super(rawName, appTimestamp);
        this.sensorTimestamp = sensorTimestamp;
        this.magnX = magnX;
        this.magnY = magnY;
        this.magnZ = magnZ;
        this.dataUnit = dataUnit;
        this.accuracy = accuracy;
    }

    public long getSensorTimestamp() {
        return sensorTimestamp;
    }

    public void setSensorTimestamp(long sensorTimestamp) {
        this.sensorTimestamp = sensorTimestamp;
    }

    public float getMagnX() {
        return magnX;
    }

    public void setMagnX(float magnX) {
        this.magnX = magnX;
    }

    public float getMagnY() {
        return magnY;
    }

    public void setMagnY(float magnY) {
        this.magnY = magnY;
    }

    public float getMagnZ() {
        return magnZ;
    }

    public void setMagnZ(float magnZ) {
        this.magnZ = magnZ;
    }

    public String getDataUnit() {
        return dataUnit;
    }

    public void setDataUnit(String dataUnit) {
        this.dataUnit = dataUnit;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public String toString() {
        return String.format("MagnetometerData[rawName=%s, appTimestamp=%d, sensorTimestamp=%d, magnX=%f, magnY=%f, magnZ=%f, dataUnit=%s, accuracy=%d]",
                super.getRawName(), super.getAppTimestamp(), sensorTimestamp, magnX, magnY, magnZ, dataUnit, accuracy);
    }
}
