package de.hftstuttgart.projectindoorweb.web.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class BatchPositionRequestElement {

    private Long buildingIdentifier;
    private Long evalFileIdentifier;
    private Long[] radioMapFileIdentifiers;
    private String algorithmType;
    private Set<ProjectParameter> projectParameters;
    private boolean withPixelPosition;


    @JsonCreator
    public BatchPositionRequestElement(@JsonProperty("buildingIdentifier") Long buildingIdentifier,
                                       @JsonProperty("evaluationFile") Long evalFileIdentifier,
                                       @JsonProperty("radioMapFiles") Long[] radioMapFileIdentifiers,
                                       @JsonProperty("algorithmType") String algorithmType,
                                       @JsonProperty("projectParameters") Set<ProjectParameter> projectParameters,
                                       @JsonProperty("withPixelPosition") boolean withPixelPosition) {

        this.buildingIdentifier = buildingIdentifier;
        this.evalFileIdentifier = evalFileIdentifier;
        this.radioMapFileIdentifiers = radioMapFileIdentifiers;
        this.algorithmType = algorithmType;
        this.projectParameters = projectParameters;
        this.withPixelPosition = withPixelPosition;
    }

    public Long getBuildingIdentifier() {
        return buildingIdentifier;
    }

    public void setBuildingIdentifier(Long buildingIdentifier) {
        this.buildingIdentifier = buildingIdentifier;
    }

    public Long getEvalFileIdentifier() {
        return evalFileIdentifier;
    }

    public void setEvalFileIdentifier(Long evalFileIdentifier) {
        this.evalFileIdentifier = evalFileIdentifier;
    }

    public Long[] getRadioMapFileIdentifiers() {
        return radioMapFileIdentifiers;
    }

    public void setRadioMapFileIdentifiers(Long[] radioMapFileIdentifiers) {
        this.radioMapFileIdentifiers = radioMapFileIdentifiers;
    }

    public String getAlgorithmType() {
        return algorithmType;
    }

    public void setAlgorithmType(String algorithmType) {
        this.algorithmType = algorithmType;
    }

    public Set<ProjectParameter> getProjectParameters() {
        return projectParameters;
    }

    public void setProjectParameters(Set<ProjectParameter> projectParameters) {
        this.projectParameters = projectParameters;
    }

    public boolean isWithPixelPosition() {
        return withPixelPosition;
    }

    public void setWithPixelPosition(boolean withPixelPosition) {
        this.withPixelPosition = withPixelPosition;
    }
}
