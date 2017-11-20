package de.hftstuttgart.projectindoorweb.persistence.entities;

import java.util.List;

public class RadioMapElement extends ModelBase {

    private PosiReference posiReference;
    private List<RssiSignal> rssiSignals;

    public RadioMapElement(PosiReference posiReference, List<RssiSignal> rssiSignals) {
        this.posiReference = posiReference;
        this.rssiSignals = rssiSignals;
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
