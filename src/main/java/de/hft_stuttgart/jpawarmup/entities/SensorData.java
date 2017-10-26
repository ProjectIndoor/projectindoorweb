package de.hft_stuttgart.jpawarmup.entities;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String logFileId;
    private String rawName;
    private long appTimestamp;

    protected SensorData(){}

    public SensorData(String logFileId, String rawName, long appTimestamp) {
        this.logFileId = logFileId;
        this.rawName = rawName;
        this.appTimestamp = appTimestamp;
    }

    public Long getId() {
        return id;
    }

    public String getLogFileId() { return logFileId; }

    public String getRawName() {
        return rawName;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }

    public long getAppTimestamp() {
        return appTimestamp;
    }

    public void setAppTimestamp(long appTimestamp) {
        this.appTimestamp = appTimestamp;
    }




    @Override
    public String toString() {
        return String.format("SensorData[id=%d, rawName='%s', appTimestamp=%d]", id, rawName, appTimestamp);
    }
}
