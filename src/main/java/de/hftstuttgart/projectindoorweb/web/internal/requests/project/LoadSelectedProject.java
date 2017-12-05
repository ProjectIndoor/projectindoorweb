package de.hftstuttgart.projectindoorweb.web.internal.requests.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class LoadSelectedProject {

    private String name;
    private String identifier;
    private Set<SaveNewProjectParameters> saveNewProjectParamaters;

    @JsonCreator
    public LoadSelectedProject(@JsonProperty("name")String name, @JsonProperty("identifier")String identifier, @JsonProperty("saveNewProjectParamaters")Set<SaveNewProjectParameters> saveNewProjectParamaters) {
        this.name = name;
        this.identifier = identifier;
        this.saveNewProjectParamaters = saveNewProjectParamaters;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Set<SaveNewProjectParameters> getSaveNewProjectParamaters() {
        return saveNewProjectParamaters;
    }
}
