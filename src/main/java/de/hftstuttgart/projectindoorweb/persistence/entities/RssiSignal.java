package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.*;

@Entity
public class RssiSignal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private float signalStrength;

    @ManyToOne
    @JoinColumn(name = "accessPointId")
    private AccessPoint wifiAccessPoint;

    @ManyToOne
    @JoinColumn(name = "signalVectorId")
    private SignalVector signalVector;

    public RssiSignal(float signalStrength, AccessPoint wifiAccessPoint, SignalVector signalVector) {
        this.signalStrength = signalStrength;
        this.wifiAccessPoint = wifiAccessPoint;
        this.signalVector = signalVector;
    }

    public Long getId() {
        return id;
    }

    public float getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(float signalStrength) {
        this.signalStrength = signalStrength;
    }

    public AccessPoint getWifiAccessPoint() {
        return wifiAccessPoint;
    }

    public void setWifiAccessPoint(AccessPoint wifiAccessPoint) {
        this.wifiAccessPoint = wifiAccessPoint;
    }

    public SignalVector getSignalVector() {
        return signalVector;
    }

    public void setSignalVector(SignalVector signalVector) {
        this.signalVector = signalVector;
    }
}
