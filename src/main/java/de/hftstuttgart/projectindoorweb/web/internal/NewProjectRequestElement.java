package de.hftstuttgart.projectindoorweb.web.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class NewProjectRequestElement {

    private Set<ProjectParameter> projectParameterSet;
    private String projectName;
    private String algorithmType;

    @JsonCreator
    public NewProjectRequestElement(@JsonProperty("projectParameterSet") Set<ProjectParameter> projectParameterSet,
                                    @JsonProperty("projectName") String projectName,
                                    @JsonProperty("algorithmType") String algorithmType) {
        this.projectParameterSet = projectParameterSet;
        this.projectName = projectName;
        this.algorithmType = algorithmType;
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
}
