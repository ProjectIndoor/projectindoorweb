package de.hftstuttgart.projectindoorweb.persistence.pojo;

public class RssiReading {

    private final double appTimestamp;
    private final String networkName;
    private final String macAddress;
    private final double rssiSignalStrength;
    private boolean averagedSignalStrength;

    public RssiReading(double appTimestamp, String networkName, String macAddress, double rssiReading, boolean averagedRssiReading) {
        this.appTimestamp = appTimestamp;
        this.networkName = networkName;
        this.macAddress = macAddress;
        this.rssiSignalStrength = rssiReading;
        this.averagedSignalStrength = averagedRssiReading;
    }

    public double getAppTimestamp() {
        return appTimestamp;
    }

    public String getNetworkName() {
        return networkName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public double getRssiSignalStrength() {
        return rssiSignalStrength;
    }

    public boolean isAveragedSignalStrength() {
        return averagedSignalStrength;
    }

    public void setAveragedSignalStrength(boolean averagedSignalStrength) {
        this.averagedSignalStrength = averagedSignalStrength;
    }
}
