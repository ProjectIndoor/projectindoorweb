package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class RadioMapElement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(targetEntity = PosiReference.class, cascade = CascadeType.ALL)
    private PosiReference posiReference;

    @OneToMany(targetEntity = RssiSignal.class, cascade = CascadeType.ALL)
    private List<RssiSignal> rssiSignals;

    protected RadioMapElement() {
    }

    public RadioMapElement(PosiReference posiReference, List<RssiSignal> rssiSignals) {
        this.posiReference = posiReference;
        this.rssiSignals = rssiSignals;
    }

    public Long getId() {
        return id;
    }

    public PosiReference getPosiReference() {
        return posiReference;
    }

    public void setPosiReference(PosiReference posiReference) {
        this.posiReference = posiReference;
    }

    public List<RssiSignal> getRssiSignals() {
        return rssiSignals;
    }

    public void setRssiSignals(List<RssiSignal> rssiSignals) {
        this.rssiSignals = rssiSignals;
    }
}
