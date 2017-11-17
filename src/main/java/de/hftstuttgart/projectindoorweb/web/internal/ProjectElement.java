package de.hftstuttgart.projectindoorweb.web.internal;

import java.util.Set;

public class ProjectElement {

    private String name;
    private String identifier;
    private Set<ProjectParameter>projectParameters;

    public ProjectElement(String name, String identifier, Set<ProjectParameter>projectParameters) {
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
