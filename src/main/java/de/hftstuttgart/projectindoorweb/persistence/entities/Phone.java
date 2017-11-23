package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String manufacturer;
    private String model;
    private int apiVersion;

    @OneToMany(targetEntity = LogFile.class, mappedBy = "recordedByPhone")
    private List<LogFile> recordedFiles;

    protected Phone(){}

    public Phone(String manufacturer, String model, int apiVersion, List<LogFile> recordedFiles) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.apiVersion = apiVersion;
        this.recordedFiles = recordedFiles;
    }

    public Long getId() {
        return id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(int apiVersion) {
        this.apiVersion = apiVersion;
    }

    public List<LogFile> getRecordedFiles() {
        return recordedFiles;
    }

    public void setRecordedFiles(List<LogFile> recordedFiles) {
        this.recordedFiles = recordedFiles;
    }
}
