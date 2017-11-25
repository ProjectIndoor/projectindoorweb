package de.hftstuttgart.projectindoorweb.web.internal;

public class ParameterElement {

    private String name;

    private String type; //Why type? No value?

    public ParameterElement(String name, String type) {
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
