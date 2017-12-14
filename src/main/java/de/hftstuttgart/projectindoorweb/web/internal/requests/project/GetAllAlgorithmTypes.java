package de.hftstuttgart.projectindoorweb.web.internal.requests.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GetAllAlgorithmTypes {

    private String className;
    private String niceName;

    private List<GetAlgorithmParameters> applicableParameters;


    @JsonCreator
    public GetAllAlgorithmTypes(@JsonProperty("className") String className, @JsonProperty("niceName") String niceName,
                                @JsonProperty("applicableParameters") List<GetAlgorithmParameters>
                                        applicableParameters) {
        this.className = className;
        this.niceName = niceName;
        this.applicableParameters = applicableParameters;
    }

    public String getClassName() {
        return className;
    }

    public String getNiceName() {
        return niceName;
    }

    public List<GetAlgorithmParameters> getApplicableParameters() {
        return applicableParameters;
    }
}
