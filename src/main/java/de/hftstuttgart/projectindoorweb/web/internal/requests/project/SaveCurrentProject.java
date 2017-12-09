package de.hftstuttgart.projectindoorweb.web.internal.requests.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class SaveCurrentProject {

    private Set<SaveNewProjectParameters> saveNewProjectParametersSet;
    private String projectName;
    private String algorithmType;
    private String projectIdentifier;
    private Long buildingIdentifier;
    private Long evalFileIdentifier;
    private long[] radioMapFileIdentifiers;

    @JsonCreator
    public SaveCurrentProject(@JsonProperty("saveNewProjectParametersSet")Set<SaveNewProjectParameters> saveNewProjectParametersSet,
                              @JsonProperty("projectName")String projectName,
                              @JsonProperty("algorithmType")String algorithmType,
                              @JsonProperty("projectIdentifier")String projectIdentifier,
                              @JsonProperty("buildingIdentifier") Long buildingIdentifier,
                              @JsonProperty("evalFileIdentifier") Long evalFileIdentifier,
                              @JsonProperty("radioMapFileIdentifiers") long[] radioMapFileIdentifiers) {
        this.saveNewProjectParametersSet = saveNewProjectParametersSet;
        this.projectName = projectName;
        this.algorithmType = algorithmType;
        this.projectIdentifier = projectIdentifier;
        this.buildingIdentifier = buildingIdentifier;
        this.evalFileIdentifier = evalFileIdentifier;
        this.radioMapFileIdentifiers = radioMapFileIdentifiers;
    }

    public Set<SaveNewProjectParameters> getSaveNewProjectParametersSet() {
        return saveNewProjectParametersSet;
    }

    public void setSaveNewProjectParametersSet(Set<SaveNewProjectParameters> saveNewProjectParametersSet) {
        this.saveNewProjectParametersSet = saveNewProjectParametersSet;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public Long getBuildingIdentifier() {
        return buildingIdentifier;
    }

    public void setBuildingIdentifier(Long buildingIdentifier) {
        this.buildingIdentifier = buildingIdentifier;
    }

    public Long getEvalFileIdentifier() {
        return evalFileIdentifier;
    }

    public void setEvalFileIdentifier(Long evalFileIdentifier) {
        this.evalFileIdentifier = evalFileIdentifier;
    }

    public long[] getRadioMapFileIdentifiers() {
        return radioMapFileIdentifiers;
    }

    public void setRadioMapFileIdentifiers(long[] radioMapFileIdentifiers) {
        this.radioMapFileIdentifiers = radioMapFileIdentifiers;
    }
}
