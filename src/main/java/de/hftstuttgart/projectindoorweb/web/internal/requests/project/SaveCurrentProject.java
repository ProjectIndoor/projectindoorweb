package de.hftstuttgart.projectindoorweb.web.internal.requests.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class SaveCurrentProject {

    private Set<SaveNewProjectParameters> saveNewProjectParametersSet;
    private String projectName;
    private String algorithmType;
    private String projectIdentifier;

    @JsonCreator
    public SaveCurrentProject(@JsonProperty("saveNewProjectParametersSet")Set<SaveNewProjectParameters> saveNewProjectParametersSet,
                              @JsonProperty("projectName")String projectName,
                              @JsonProperty("algorithmType")String algorithmType,
                              @JsonProperty("projectIdentifier")String projectIdentifier) {
        this.saveNewProjectParametersSet = saveNewProjectParametersSet;
        this.projectName = projectName;
        this.algorithmType = algorithmType;
        this.projectIdentifier = projectIdentifier;
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
}
