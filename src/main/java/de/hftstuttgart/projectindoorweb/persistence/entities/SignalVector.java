package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class SignalVector {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "logFileId")
    private LogFile containedInFile;

    @ManyToOne
    @JoinColumn(name = "positionId")
    private Position referencePosition;

    @OneToMany(mappedBy = "signalVector", cascade = CascadeType.ALL)
    private Set<RssiSignal> rssiSignals;

    public SignalVector(LogFile containedInFile, Position referencePosition) {
        this.containedInFile = containedInFile;
        this.referencePosition = referencePosition;
    }

    public Long getId() {
        return id;
    }

    public LogFile getContainedInFile() {
        return containedInFile;
    }

    public void setContainedInFile(LogFile containedInFile) {
        this.containedInFile = containedInFile;
    }

    public Position getReferencePosition() {
        return referencePosition;
    }

    public void setReferencePosition(Position referencePosition) {
        this.referencePosition = referencePosition;
    }

    public Set<RssiSignal> getRssiSignals() {
        return rssiSignals;
    }

    public void setRssiSignals(Set<RssiSignal> rssiSignals) {
        this.rssiSignals = rssiSignals;
    }
}
