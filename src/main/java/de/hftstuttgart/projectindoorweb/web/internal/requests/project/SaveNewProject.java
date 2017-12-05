package de.hftstuttgart.projectindoorweb.web.internal.requests.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class SaveNewProject {

    private Set<SaveNewProjectParameters> saveNewProjectParametersSet;
    private String projectName;
    private String algorithmType;

    @JsonCreator
    public SaveNewProject(@JsonProperty("projectParameters") Set<SaveNewProjectParameters> saveNewProjectParametersSet,
                          @JsonProperty("projectName") String projectName,
                          @JsonProperty("algorithmType") String algorithmType) {
        this.saveNewProjectParametersSet = saveNewProjectParametersSet;
        this.projectName = projectName;
        this.algorithmType = algorithmType;
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
}
