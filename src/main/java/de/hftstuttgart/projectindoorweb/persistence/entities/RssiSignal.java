package de.hftstuttgart.projectindoorweb.persistence.entities;

public class RssiSignal extends ModelBase {

    private double appTimestamp;
    private double rssiSignalStrength;
    private boolean averagedSignalStrength;

    private WifiAccessPoint emittedByAccessPoint;


}
