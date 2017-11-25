package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class AccessPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String macAddress;


    @ManyToOne
    @JoinColumn(name = "positionId")
    private Position position;

    @OneToMany(mappedBy = "wifiAccessPoint", cascade = CascadeType.ALL)
    private Set<RssiSignal> rssiSignals;

    public AccessPoint(String macAddress, Position position) {
        this.macAddress = macAddress;
        this.position = position;
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

    public Set<RssiSignal> getRssiSignals() {
        return rssiSignals;
    }

    public void setRssiSignals(Set<RssiSignal> rssiSignals) {
        this.rssiSignals = rssiSignals;
    }
}
