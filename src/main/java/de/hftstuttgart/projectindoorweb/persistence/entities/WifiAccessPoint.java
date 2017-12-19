package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class WifiAccessPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String macAddress;

    @ManyToOne(targetEntity = Position.class, cascade = CascadeType.ALL)
    private Position position;

    protected WifiAccessPoint() {
    }

    public WifiAccessPoint(String macAddress, Position position) {
        this.macAddress = macAddress;
        this.position = position;
    }

    public WifiAccessPoint(String macAddress) {
        this.macAddress = macAddress;
        this.position = null;
    }

    public Long getId() {
        return id;
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
