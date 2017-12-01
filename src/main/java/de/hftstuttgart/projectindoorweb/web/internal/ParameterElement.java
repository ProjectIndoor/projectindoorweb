package de.hftstuttgart.projectindoorweb.web.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ParameterElement {

    private String name;

    private String type; //Why type? No value?

    @JsonCreator
    public ParameterElement(@JsonProperty("name")String name, @JsonProperty("type")String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
