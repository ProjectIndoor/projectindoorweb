package de.hft_stuttgart.jpawarmup.entities;

import javax.persistence.Entity;

@Entity
public class AccelerometerData extends SensorData {

    private long sensorTimestamp;
    private float accX;
    private float accY;
    private float accZ;
    private String dataUnit;
    private int accuracy;

    protected AccelerometerData(){}

    public AccelerometerData(String logFileId, String rawName, long appTimestamp, long sensorTimestamp, float accX, float accY, float accZ, String dataUnit, int accuracy) {
        super(logFileId, rawName, appTimestamp);
        this.sensorTimestamp = sensorTimestamp;
        this.accX = accX;
        this.accY = accY;
        this.accZ = accZ;
        this.dataUnit = dataUnit;
        this.accuracy = accuracy;
    }

    public long getSensorTimestamp() {
        return sensorTimestamp;
    }

    public void setSensorTimestamp(long sensorTimestamp) {
        this.sensorTimestamp = sensorTimestamp;
    }

    public float getAccX() {
        return accX;
    }

    public void setAccX(float accX) {
        this.accX = accX;
    }

    public float getAccY() {
        return accY;
    }

    public void setAccY(float accY) {
        this.accY = accY;
    }

    public float getAccZ() {
        return accZ;
    }

    public void setAccZ(float accZ) {
        this.accZ = accZ;
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
        return String.format("AccelerometerData[rawName=%s, appTimestamp=%d, sensorTimestamp=%d, accX=%f, accY=%f, accZ=%f, dataUnit=%s, accuracy=%d]",
                super.getRawName(), super.getAppTimestamp(), sensorTimestamp, accX, accY, accZ, dataUnit, accuracy);
    }
}
