package de.hft_stuttgart.jpawarmup.entities;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String rawName;
    private int appTimestamp;

    protected SensorData(){}

    public SensorData(String rawName, int appTimestamp) {
        this.rawName = rawName;
        this.appTimestamp = appTimestamp;
    }

    public Long getId() {
        return id;
    }


    public String getRawName() {
        return rawName;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }

    public int getAppTimestamp() {
        return appTimestamp;
    }

    public void setAppTimestamp(int appTimestamp) {
        this.appTimestamp = appTimestamp;
    }

    @Override
    public String toString() {
        return String.format("SensorData[id=%d, rawName='%s', appTimestamp=%d]", id, rawName, appTimestamp);
    }
}
