package de.hftstuttgart.projectindoorweb.web.internal.requests.positioning;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class BatchPositionResult {

    private CalculatedPosition calculatedPosition;
    private ReferencePosition referencePosition;
    private double distanceInMeters;

    @JsonCreator
    public BatchPositionResult(@JsonProperty("calculatedPosition") CalculatedPosition calculatedPosition,
                               @JsonProperty("referencePosition") ReferencePosition referencePosition,
                               @JsonProperty("distanceInMeters") double distanceInMeters) {

        this.calculatedPosition = calculatedPosition;
        this.referencePosition = referencePosition;
        this.distanceInMeters = distanceInMeters;
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

    public double getDistanceInMeters() {
        return distanceInMeters;
    }

    public void setDistanceInMeters(double distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchPositionResult that = (BatchPositionResult) o;
        return Double.compare(that.distanceInMeters, distanceInMeters) == 0 &&
                Objects.equals(calculatedPosition, that.calculatedPosition) &&
                Objects.equals(referencePosition, that.referencePosition);
    }

    @Override
    public int hashCode() {

        return Objects.hash(calculatedPosition, referencePosition, distanceInMeters);
    }
}
