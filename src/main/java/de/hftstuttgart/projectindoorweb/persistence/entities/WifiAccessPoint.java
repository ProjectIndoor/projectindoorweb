package de.hftstuttgart.projectindoorweb.persistence.entities;

public class WifiAccessPoint extends ModelBase {

    private String macAddress;
    private Position position;

    public WifiAccessPoint(String macAddress, Position position) {
        this.macAddress = macAddress;
        this.position = position;
    }

    public WifiAccessPoint(String macAddress) {
        this.macAddress = macAddress;
        this.position = null;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
