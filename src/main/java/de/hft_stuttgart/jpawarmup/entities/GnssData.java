package de.hft_stuttgart.jpawarmup.entities;

import javax.persistence.Entity;

@Entity
public class GnssData extends SensorData {

    private long sensorTimestamp;
    private float latitude;
    private float longitude;
    private String dataUnitLatLong;
    private float altitude;
    private String dataUnitAltitude;
    private float bearing;
    private String dataUnitBearing;
    private float accuracy;
    private String dataUnitAccuracy;
    private float speed;
    private String dataUnitSpeed;
    private int satInView;
    private int satInUse;

    protected GnssData(){}


    public GnssData(String logFileId, String rawName, long appTimestamp, long sensorTimestamp, float latitude, float longitude, String dataUnitLatLong,
                    float altitude, String dataUnitAltitude, float bearing, String dataUnitBearing, float accuracy,
                    String dataUnitAccuracy, float speed, String dataUnitSpeed, int satInView, int satInUse) {

        super(logFileId, rawName, appTimestamp);
        this.sensorTimestamp = sensorTimestamp;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dataUnitLatLong = dataUnitLatLong;
        this.altitude = altitude;
        this.dataUnitAltitude = dataUnitAltitude;
        this.bearing = bearing;
        this.dataUnitBearing = dataUnitBearing;
        this.accuracy = accuracy;
        this.dataUnitAccuracy = dataUnitAccuracy;
        this.speed = speed;
        this.dataUnitSpeed = dataUnitSpeed;
        this.satInView = satInView;
        this.satInUse = satInUse;
    }

    public long getSensorTimestamp() {
        return sensorTimestamp;
    }

    public void setSensorTimestamp(long sensorTimestamp) {
        this.sensorTimestamp = sensorTimestamp;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getDataUnitLatLong() {
        return dataUnitLatLong;
    }

    public void setDataUnitLatLong(String dataUnitLatLong) {
        this.dataUnitLatLong = dataUnitLatLong;
    }

    public float getAltitude() {
        return altitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public String getDataUnitAltitude() {
        return dataUnitAltitude;
    }

    public void setDataUnitAltitude(String dataUnitAltitude) {
        this.dataUnitAltitude = dataUnitAltitude;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public String getDataUnitBearing() {
        return dataUnitBearing;
    }

    public void setDataUnitBearing(String dataUnitBearing) {
        this.dataUnitBearing = dataUnitBearing;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public String getDataUnitAccuracy() {
        return dataUnitAccuracy;
    }

    public void setDataUnitAccuracy(String dataUnitAccuracy) {
        this.dataUnitAccuracy = dataUnitAccuracy;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public String getDataUnitSpeed() {
        return dataUnitSpeed;
    }

    public void setDataUnitSpeed(String dataUnitSpeed) {
        this.dataUnitSpeed = dataUnitSpeed;
    }

    public int getSatInView() {
        return satInView;
    }

    public void setSatInView(int satInView) {
        this.satInView = satInView;
    }

    public int getSatInUse() {
        return satInUse;
    }

    public void setSatInUse(int satInUse) {
        this.satInUse = satInUse;
    }
}
