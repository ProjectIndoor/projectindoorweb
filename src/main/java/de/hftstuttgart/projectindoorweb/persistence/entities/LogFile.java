package de.hftstuttgart.projectindoorweb.persistence.entities;

public class LogFile extends ModelBase {

    private String sourceFileName;
    private String customFileName;
    private int appVersion;

    private RadioMap generatedRadioMap;
    private Building recordedIn;
    private Phone recordedBy;


}
