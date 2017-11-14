package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String manufacturer;
    private String model;
    private int apiVersion;

    @OneToMany(mappedBy = "recordedByPhone", cascade = CascadeType.ALL)
    private Set<LogFile> recordedLogFiles;

    public Phone(String manufacturer, String model, int apiVersion) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.apiVersion = apiVersion;
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

    public Set<LogFile> getRecordedLogFiles() {
        return recordedLogFiles;
    }

    public void setRecordedLogFiles(Set<LogFile> recordedLogFiles) {
        this.recordedLogFiles = recordedLogFiles;
    }
}
