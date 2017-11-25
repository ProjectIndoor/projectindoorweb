package de.hftstuttgart.projectindoorweb.persistence.pojo;

import java.io.File;
import java.util.List;

public class RadiomapElement {

    private final File sourceFile;
    private final PosiReference posiReference;
    private final List<RssiReading> rssiReadings;

    public RadiomapElement(File sourceFile, PosiReference posiReference, List<RssiReading> rssiReadings) {
        this.sourceFile = sourceFile;
        this.posiReference = posiReference;
        this.rssiReadings = rssiReadings;
    }

    public File getSourceFile() {
        return sourceFile;
    }

    public PosiReference getPosiReference() {
        return posiReference;
    }

    public List<RssiReading> getRssiReadings() {
        return rssiReadings;
    }
}
