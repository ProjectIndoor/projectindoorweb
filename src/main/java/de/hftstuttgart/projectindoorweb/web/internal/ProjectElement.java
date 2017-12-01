package de.hftstuttgart.projectindoorweb.web.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class ProjectElement {

    private String name;
    private String identifier;
    private Set<ProjectParameter>projectParameters;

    @JsonCreator
    public ProjectElement(@JsonProperty("name")String name, @JsonProperty("identifier")String identifier, @JsonProperty("projectParameters")Set<ProjectParameter>projectParameters) {
        this.name = name;
        this.identifier = identifier;
        this.projectParameters = projectParameters;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Set<ProjectParameter> getProjectParameters() {
        return projectParameters;
    }
}
