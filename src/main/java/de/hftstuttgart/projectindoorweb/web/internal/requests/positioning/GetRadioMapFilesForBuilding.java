package de.hftstuttgart.projectindoorweb.web.internal.requests.positioning;

public class GetRadioMapFilesForBuilding {

    private long id;

    private String radioMapSourceFileName;

    /*For JSON deserialization by Jackson*/
    protected GetRadioMapFilesForBuilding(){}

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
