package de.hftstuttgart.projectindoorweb.web.internal.requests.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAllProjects {

    long projectId;
    String projectName;
    long buildingId;
    String buildingName;

    @JsonCreator
    public GetAllProjects(@JsonProperty("projectId") long projectId,
                          @JsonProperty("projectName") String projectName,
                          @JsonProperty("buildingId") long buildingId,
                          @JsonProperty("buildingName") String buildingName) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.buildingId = buildingId;
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

    public long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(long buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
}
