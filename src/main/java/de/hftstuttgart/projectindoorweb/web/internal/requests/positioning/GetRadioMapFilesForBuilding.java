package de.hftstuttgart.projectindoorweb.web.internal.requests.positioning;

public class GetRadioMapFilesForBuilding {

    private final long id;

    private final String radioMapSourceFileName;

    public GetRadioMapFilesForBuilding(long id, String radioMapSourceFileName) {
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
