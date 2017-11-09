package de.hftstuttgart.projectindoorweb.pojos;

import java.io.File;

public class PosiReference {

    private final int positionInSourceFile;
    private final int avgNumber;
    private final Position referencePosition;

    private double intervalStart;
    private double intervalEnd;


    public PosiReference(int positionInSourceFile, int avgNumber, Position referencePosition,
                         double intervalStart, double intervalEnd) {

        this.positionInSourceFile = positionInSourceFile;
        this.avgNumber = avgNumber;
        this.referencePosition = referencePosition;
        this.intervalStart = intervalStart;
        this.intervalEnd = intervalEnd;
    }

    public int getPositionInSourceFile() {
        return positionInSourceFile;
    }

    public int getAvgNumber() {
        return avgNumber;
    }

    public Position getReferencePosition() {
        return referencePosition;
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
}
