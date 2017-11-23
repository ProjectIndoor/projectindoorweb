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

    private double intervalStart;
    private double intervalEnd;

    @ManyToOne(targetEntity = Floor.class, cascade = CascadeType.ALL)
    private Floor floor;

    protected PosiReference(){}

    public PosiReference(int positionInSourceFile, int avgNumber, Position referencePosition,
                         double intervalStart, double intervalEnd, Floor floor) {

        this.positionInSourceFile = positionInSourceFile;
        this.avgNumber = avgNumber;
        this.referencePosition = referencePosition;
        this.intervalStart = intervalStart;
        this.intervalEnd = intervalEnd;
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
