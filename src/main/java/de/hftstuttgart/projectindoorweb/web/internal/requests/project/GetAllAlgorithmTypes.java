package de.hftstuttgart.projectindoorweb.web.internal.requests.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetAllAlgorithmTypes {

    private String className;
    private String niceName;

    private List<GetAlgorithmParameters> getAlgorithmParameters;


    @JsonCreator
    public GetAllAlgorithmTypes(@JsonProperty("className") String className, @JsonProperty("niceName") String niceName,
                                @JsonProperty("getAlgorithmParameters") List<GetAlgorithmParameters>
                                        getAlgorithmParameters) {
        this.className = className;
        this.niceName = niceName;
        this.getAlgorithmParameters = getAlgorithmParameters;
    }

    public String getClassName() {
        return className;
    }

    public String getNiceName() {
        return niceName;
    }

    public List<GetAlgorithmParameters> getGetAlgorithmParameters() {
        return getAlgorithmParameters;
    }
}
