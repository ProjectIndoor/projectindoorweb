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

    @OneToMany(targetEntity = EvaalFile.class, mappedBy = "recordedByPhone")
    private List<EvaalFile> recordedFiles;

    protected Phone(){}

    public Phone(String manufacturer, String model, int apiVersion, List<EvaalFile> recordedFiles) {
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

    public List<EvaalFile> getRecordedFiles() {
        return recordedFiles;
    }

    public void setRecordedFiles(List<EvaalFile> recordedFiles) {
        this.recordedFiles = recordedFiles;
    }
}
