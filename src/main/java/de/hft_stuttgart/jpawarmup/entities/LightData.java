package de.hft_stuttgart.jpawarmup.entities;

import javax.persistence.Entity;

@Entity
public class LightData extends SensorData {

    private long sensorTimestamp;
    private float light;
    private String dataUnit;
    private int accuracy;

    protected LightData(){}

    public LightData(String logFileId, String rawName, long appTimestamp, long sensorTimestamp, float light, String dataUnit, int accuracy) {
        super(logFileId, rawName, appTimestamp);
        this.sensorTimestamp = sensorTimestamp;
        this.light = light;
        this.dataUnit = dataUnit;
        this.accuracy = accuracy;
    }


    public long getSensorTimestamp() {
        return sensorTimestamp;
    }

    public void setSensorTimestamp(long sensorTimestamp) {
        this.sensorTimestamp = sensorTimestamp;
    }

    public float getLight() {
        return light;
    }

    public void setLight(float light) {
        this.light = light;
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
    public String toString(){
        return String.format("LightData[rawName=%s, appTimestamp=%d, sensorTimestamp=%d, light=%f, dataUnit=%s, accuracy=%d]",
                super.getRawName(), super.getAppTimestamp(), sensorTimestamp, light, dataUnit, accuracy);
    }
}
