package de.hftstuttgart.projectindoorweb.persistence.entities;

import java.util.List;

public class RadioMapElement extends ModelBase {

    private RadioMap belongingRadioMap;
    private PosiReference posiReference;
    private List<RssiSignal> rssiSignals;

}
