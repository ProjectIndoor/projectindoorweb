package de.hftstuttgart.projectindoorweb.persistence.entities;

public class PosiReference extends ModelBase {

    private int positionInSourceFile;
    private int avgNumber;
    private Position referencePosition;
    private double intervalStart;
    private double intervalEnd;
    private Floor floor;

    public PosiReference(int positionInSourceFile, int avgNumber, Position referencePosition,
                         double intervalStart, double intervalEnd, Floor floor) {

        this.positionInSourceFile = positionInSourceFile;
        this.avgNumber = avgNumber;
        this.referencePosition = referencePosition;
        this.intervalStart = intervalStart;
        this.intervalEnd = intervalEnd;
        this.floor = floor;
    }

    public int getPositionInSourceFile() {
        return positionInSourceFile;
    }

    public void setPositionInSourceFile(int positionInSourceFile) {
        this.positionInSourceFile = positionInSourceFile;
    }

    public int getAvgNumber() {
        return avgNumber;
    }

    public void setAvgNumber(int avgNumber) {
        this.avgNumber = avgNumber;
    }

    public Position getReferencePosition() {
        return referencePosition;
    }

    public void setReferencePosition(Position referencePosition) {
        this.referencePosition = referencePosition;
    }

    public double getIntervalStart() {
        return intervalStart;
    }

    public void setIntervalStart(double intervalStart) {
        this.intervalStart = intervalStart;
    }

    public double getIntervalEnd() {
        return intervalEnd;
    }

    public void setIntervalEnd(double intervalEnd) {
        this.intervalEnd = intervalEnd;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }
}
