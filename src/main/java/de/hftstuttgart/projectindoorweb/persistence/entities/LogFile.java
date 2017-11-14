package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class LogFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String customName;
    private String sourceFileName;
    private Long creationDate;
    private int appVersion;
    private boolean radiomapFile;

    @ManyToOne
    @JoinColumn(name = "phoneId")
    private Phone recordedByPhone;

    @OneToMany(mappedBy = "containedInFile", cascade = CascadeType.ALL)
    private Set<SignalVector> signalVectors;

    @ManyToOne
    @JoinColumn(name = "buildingId")
    private Building recordedInBuilding;

    public LogFile(String customName, String sourceFileName, Long creationDate, int appVersion,
                   boolean radiomapFile, Phone recordedByPhone, Building recordedInBuilding) {
        this.customName = customName;
        this.sourceFileName = sourceFileName;
        this.creationDate = creationDate;
        this.appVersion = appVersion;
        this.radiomapFile = radiomapFile;
        this.recordedByPhone = recordedByPhone;
        this.recordedInBuilding = recordedInBuilding;
    }

    public Long getId() {
        return id;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }

    public int getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }

    public boolean isRadiomapFile() {
        return radiomapFile;
    }

    public void setRadiomapFile(boolean radiomapFile) {
        this.radiomapFile = radiomapFile;
    }

    public Phone getRecordedByPhone() {
        return recordedByPhone;
    }

    public void setRecordedByPhone(Phone recordedByPhone) {
        this.recordedByPhone = recordedByPhone;
    }

    public Set<SignalVector> getSignalVectors() {
        return signalVectors;
    }

    public void setSignalVectors(Set<SignalVector> signalVectors) {
        this.signalVectors = signalVectors;
    }

    public Building getRecordedInBuilding() {
        return recordedInBuilding;
    }

    public void setRecordedInBuilding(Building recordedInBuilding) {
        this.recordedInBuilding = recordedInBuilding;
    }
}
