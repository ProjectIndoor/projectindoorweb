package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private float latitude;
    private float longitude;
    private int floor;

    @ManyToOne
    @JoinColumn(name = "buildingId")
    private Building building;

    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
    private Set<AccessPoint> accessPoints;

    @OneToMany(mappedBy = "referencePosition")
    private Set<SignalVector> signalVectors;

    public Position(float latitude, float longitude, int floor) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.floor = floor;
    }

    public Long getId() {
        return id;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public int getFloor() {
        return floor;
    }

    public Set<AccessPoint> getAccessPoints() {
        return accessPoints;
    }

    public Set<SignalVector> getSignalVectors() {
        return signalVectors;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setAccessPoints(Set<AccessPoint> accessPoints) {
        this.accessPoints = accessPoints;
    }

    public void setSignalVectors(Set<SignalVector> signalVectors) {
        this.signalVectors = signalVectors;
    }
}
