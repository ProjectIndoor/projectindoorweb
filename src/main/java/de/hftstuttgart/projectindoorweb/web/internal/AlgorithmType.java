package de.hftstuttgart.projectindoorweb.web.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AlgorithmType {

    private String className;
    private String niceName;


    @JsonCreator
    public AlgorithmType(@JsonProperty("className")String className, @JsonProperty("niceName")String niceName) {
        this.className = className;
        this.niceName = niceName;
    }

    public String getClassName() {
        return className;
    }

    public String getNiceName() {
        return niceName;
    }
}
