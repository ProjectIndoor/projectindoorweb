package de.hftstuttgart.projectindoorweb.web.internal;

public class ProjectParameter {

    private  String name;
    private  String value;

    public ProjectParameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
