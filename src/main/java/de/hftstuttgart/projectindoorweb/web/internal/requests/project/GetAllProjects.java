package de.hftstuttgart.projectindoorweb.web.internal.requests.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAllProjects {

    long projectId;
    String projectName;
    String buildingName;

    @JsonCreator
    public GetAllProjects(@JsonProperty("projectId") long projectId,
                          @JsonProperty("projectName") String projectName,
                          @JsonProperty("buildingName") String buildingName) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.buildingName = buildingName;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
}
