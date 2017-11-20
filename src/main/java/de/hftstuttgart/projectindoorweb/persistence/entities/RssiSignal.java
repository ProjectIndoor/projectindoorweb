package de.hftstuttgart.projectindoorweb.persistence.entities;

public class RssiSignal extends ModelBase {

    private double appTimestamp;
    private double rssiSignalStrength;
    private boolean averagedSignalStrength;

    private WifiAccessPoint wifiAccessPoint;

    public RssiSignal(double appTimestamp, double rssiSignalStrength,
                      boolean averagedSignalStrength, WifiAccessPoint emittedByAccessPoint) {

        this.appTimestamp = appTimestamp;
        this.rssiSignalStrength = rssiSignalStrength;
        this.averagedSignalStrength = averagedSignalStrength;
        this.wifiAccessPoint = emittedByAccessPoint;
    }

    public double getAppTimestamp() {
        return appTimestamp;
    }

    public void setAppTimestamp(double appTimestamp) {
        this.appTimestamp = appTimestamp;
    }

    public double getRssiSignalStrength() {
        return rssiSignalStrength;
    }

    public void setRssiSignalStrength(double rssiSignalStrength) {
        this.rssiSignalStrength = rssiSignalStrength;
    }

    public boolean isAveragedSignalStrength() {
        return averagedSignalStrength;
    }

    public void setAveragedSignalStrength(boolean averagedSignalStrength) {
        this.averagedSignalStrength = averagedSignalStrength;
    }

    public WifiAccessPoint getWifiAccessPoint() {
        return wifiAccessPoint;
    }

    public void setWifiAccessPoint(WifiAccessPoint wifiAccessPoint) {
        this.wifiAccessPoint = wifiAccessPoint;
    }
}
