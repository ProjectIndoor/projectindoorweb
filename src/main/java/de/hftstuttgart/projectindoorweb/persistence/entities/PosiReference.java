package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.*;

@Entity
public class PosiReference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int positionInSourceFile;
    private int avgNumber;

    @ManyToOne(targetEntity = Position.class, cascade = CascadeType.ALL)
    private Position referencePosition;

    private double originalIntervalStart;
    private double originalIntervalEnd;

    private double shiftedIntervalStart;
    private double shiftedIntervalEnd;

    @ManyToOne(targetEntity = Floor.class, cascade = CascadeType.ALL)
    private Floor floor;

    protected PosiReference(){}

    public PosiReference(int positionInSourceFile, int avgNumber, Position referencePosition,
                         double originalIntervalStart, double originalIntervalEnd, double shiftedIntervalStart,
                         double shiftedIntervalEnd, Floor floor) {
        this.positionInSourceFile = positionInSourceFile;
        this.avgNumber = avgNumber;
        this.referencePosition = referencePosition;
        this.originalIntervalStart = originalIntervalStart;
        this.originalIntervalEnd = originalIntervalEnd;
        this.shiftedIntervalStart = shiftedIntervalStart;
        this.shiftedIntervalEnd = shiftedIntervalEnd;
        this.floor = floor;
    }

    public Long getId() {
        return id;
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

    public double getOriginalIntervalStart() {
        return originalIntervalStart;
    }

    public void setOriginalIntervalStart(double originalIntervalStart) {
        this.originalIntervalStart = originalIntervalStart;
    }

    public double getOriginalIntervalEnd() {
        return originalIntervalEnd;
    }

    public void setOriginalIntervalEnd(double originalIntervalEnd) {
        this.originalIntervalEnd = originalIntervalEnd;
    }

    public double getShiftedIntervalStart() {
        return shiftedIntervalStart;
    }

    public void setShiftedIntervalStart(double shiftedIntervalStart) {
        this.shiftedIntervalStart = shiftedIntervalStart;
    }

    public double getShiftedIntervalEnd() {
        return shiftedIntervalEnd;
    }

    public void setShiftedIntervalEnd(double shiftedIntervalEnd) {
        this.shiftedIntervalEnd = shiftedIntervalEnd;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }
}
