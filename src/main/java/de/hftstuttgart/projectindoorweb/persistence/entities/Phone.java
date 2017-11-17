package de.hftstuttgart.projectindoorweb.persistence.entities;

import java.util.List;

public class Phone extends ModelBase{

    private String manufacturer;
    private String model;
    private int apiVersion;

    private List<LogFile> recordedFiles;

}
