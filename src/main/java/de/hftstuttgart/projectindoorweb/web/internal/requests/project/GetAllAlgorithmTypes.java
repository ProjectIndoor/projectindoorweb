package de.hftstuttgart.projectindoorweb.web.internal.requests.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAllAlgorithmTypes {

    private String className;
    private String niceName;


    @JsonCreator
    public GetAllAlgorithmTypes(@JsonProperty("className")String className, @JsonProperty("niceName")String niceName) {
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
