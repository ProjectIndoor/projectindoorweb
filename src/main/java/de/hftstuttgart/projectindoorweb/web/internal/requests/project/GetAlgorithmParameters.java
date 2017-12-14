package de.hftstuttgart.projectindoorweb.web.internal.requests.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAlgorithmParameters {

    private String name;
    private String type;
    private Object defaultValue;

    @JsonCreator
    public GetAlgorithmParameters(@JsonProperty("name")String name, @JsonProperty("type")String type,
                                          @JsonProperty("defaultValue")Object defaultValue) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }
}
