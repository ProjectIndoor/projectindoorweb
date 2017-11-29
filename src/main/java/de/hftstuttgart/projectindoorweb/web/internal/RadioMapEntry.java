package de.hftstuttgart.projectindoorweb.web.internal;

public class RadioMapEntry {

    private final long id;

    private final String radioMapSourceFileName;

    public RadioMapEntry(long id, String radioMapSourceFileName) {
        this.id = id;
        this.radioMapSourceFileName = radioMapSourceFileName;
    }

    public long getId() {
        return id;
    }

    public String getRadioMapSourceFileName() {
        return radioMapSourceFileName;
    }

}
