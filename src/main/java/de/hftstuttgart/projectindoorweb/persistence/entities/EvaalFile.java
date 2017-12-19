package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Map;

@Entity
public class EvaalFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean evaluationFile;

    private String sourceFileName;
    private String customFileName;
    private int appVersion;

    @OneToMany(targetEntity = WifiBlock.class, cascade = CascadeType.ALL)
    private Map<Integer, WifiBlock> wifiBlocks;

    @ManyToOne(targetEntity = Building.class)
    private Building recordedInBuilding;

    @ManyToOne(targetEntity = Phone.class)
    private Phone recordedByPhone;

    @OneToOne(targetEntity = RadioMap.class, cascade = CascadeType.ALL)
    private RadioMap radioMap;

    protected EvaalFile() {
    }

    public EvaalFile(boolean evaluationFile, String sourceFileName, String customFileName, int appVersion, Map<Integer, WifiBlock> wifiBlocks,
                     Building recordedInBuilding, Phone recordedByPhone, RadioMap radioMap) {

        this.evaluationFile = evaluationFile;
        this.sourceFileName = sourceFileName;
        this.customFileName = customFileName;
        this.appVersion = appVersion;
        this.wifiBlocks = wifiBlocks;
        this.recordedInBuilding = recordedInBuilding;
        this.recordedByPhone = recordedByPhone;
        this.radioMap = radioMap;

    }

    public Long getId() {
        return id;
    }

    public boolean isEvaluationFile() {
        return evaluationFile;
    }

    public void setEvaluationFile(boolean evaluationFile) {
        this.evaluationFile = evaluationFile;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public String getCustomFileName() {
        return customFileName;
    }

    public void setCustomFileName(String customFileName) {
        this.customFileName = customFileName;
    }

    public int getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }

    public Map<Integer, WifiBlock> getWifiBlocks() {
        return wifiBlocks;
    }

    public void setWifiBlocks(Map<Integer, WifiBlock> wifiBlocks) {
        this.wifiBlocks = wifiBlocks;
    }

    public Building getRecordedInBuilding() {
        return recordedInBuilding;
    }

    public void setRecordedInBuilding(Building recordedInBuilding) {
        this.recordedInBuilding = recordedInBuilding;
    }

    public Phone getRecordedByPhone() {
        return recordedByPhone;
    }

    public void setRecordedByPhone(Phone recordedByPhone) {
        this.recordedByPhone = recordedByPhone;
    }

    public RadioMap getRadioMap() {
        return radioMap;
    }

    public void setRadioMap(RadioMap radioMap) {
        this.radioMap = radioMap;
    }

}
