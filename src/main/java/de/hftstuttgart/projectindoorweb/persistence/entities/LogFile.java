package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.*;

@Entity
public class LogFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sourceFileName;
    private String customFileName;
    private int appVersion;

    @ManyToOne(targetEntity = Building.class, cascade = CascadeType.ALL)
    private Building recordedInBuilding;

    @ManyToOne(targetEntity = Phone.class, cascade = CascadeType.ALL)
    private Phone recordedByPhone;

    @OneToOne(targetEntity = RadioMap.class, cascade = CascadeType.ALL)
    private RadioMap radioMap;

    @ManyToOne(targetEntity = Project.class, cascade = CascadeType.ALL)
    private Project generatedFromProject;

    protected LogFile(){}

    public LogFile(String sourceFileName, String customFileName, int appVersion, RadioMap radioMap, Project project) {
        this.sourceFileName = sourceFileName;
        this.customFileName = customFileName;
        this.appVersion = appVersion;
        this.radioMap = radioMap;
        this.generatedFromProject = project;
    }

    public LogFile(String sourceFileName, String customFileName, int appVersion, Building recordedInBuilding,
                   Phone recordedByPhone, RadioMap radioMap, Project project) {

        this.sourceFileName = sourceFileName;
        this.customFileName = customFileName;
        this.appVersion = appVersion;
        this.recordedInBuilding = recordedInBuilding;
        this.recordedByPhone = recordedByPhone;
        this.radioMap = radioMap;
        this.generatedFromProject = project;

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

    public Project getGeneratedFromProject() {
        return generatedFromProject;
    }

    public void setGeneratedFromProject(Project generatedFromProject) {
        this.generatedFromProject = generatedFromProject;
    }
}
