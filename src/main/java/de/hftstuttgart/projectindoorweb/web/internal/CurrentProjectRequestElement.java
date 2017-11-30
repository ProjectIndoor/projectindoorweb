package de.hftstuttgart.projectindoorweb.web.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class CurrentProjectRequestElement {

    private Set<ProjectParameter> projectParameterSet;
    private String projectName;
    private String algorithmType;
    private String projectIdentifier;

    @JsonCreator
    public CurrentProjectRequestElement(@JsonProperty("projectParameterSet")Set<ProjectParameter> projectParameterSet,
                                        @JsonProperty("projectName")String projectName,
                                        @JsonProperty("algorithmType")String algorithmType,
                                        @JsonProperty("projectIdentifier")String projectIdentifier) {
        this.projectParameterSet = projectParameterSet;
        this.projectName = projectName;
        this.algorithmType = algorithmType;
        this.projectIdentifier = projectIdentifier;
    }

    public Set<ProjectParameter> getProjectParameterSet() {
        return projectParameterSet;
    }

    public void setProjectParameterSet(Set<ProjectParameter> projectParameterSet) {
        this.projectParameterSet = projectParameterSet;
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
