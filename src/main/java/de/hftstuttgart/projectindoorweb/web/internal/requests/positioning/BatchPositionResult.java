package de.hftstuttgart.projectindoorweb.web.internal.requests.positioning;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BatchPositionResult {

    private CalculatedPosition calculatedPosition;
    private ReferencePosition referencePosition;
    private double positionDistance;

    @JsonCreator
    public BatchPositionResult(@JsonProperty("calculatedPosition") CalculatedPosition calculatedPosition,
                               @JsonProperty("referencePosition") ReferencePosition referencePosition,
                               @JsonProperty("positionDistance") double positionDistance) {

        this.calculatedPosition = calculatedPosition;
        this.referencePosition = referencePosition;
        this.positionDistance = positionDistance;
    }

    public CalculatedPosition getCalculatedPosition() {
        return calculatedPosition;
    }

    public void setCalculatedPosition(CalculatedPosition calculatedPosition) {
        this.calculatedPosition = calculatedPosition;
    }

    public ReferencePosition getReferencePosition() {
        return referencePosition;
    }

    public void setReferencePosition(ReferencePosition referencePosition) {
        this.referencePosition = referencePosition;
    }

    public double getPositionDistance() {
        return positionDistance;
    }

    public void setPositionDistance(double positionDistance) {
        this.positionDistance = positionDistance;
    }
}
