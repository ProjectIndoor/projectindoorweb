package de.hftstuttgart.projectindoorweb.persistence.entities;

import de.hftstuttgart.projectindoorweb.positionCalculator.CalculationAlgorithm;

import javax.persistence.*;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String projectName;
    private CalculationAlgorithm calculationAlgorithm;

    @OneToMany(targetEntity = Parameter.class, cascade = CascadeType.ALL)
    private List<Parameter> projectParameters;

    @OneToMany(targetEntity = EvaalFile.class, cascade = CascadeType.ALL)
    private List<EvaalFile> evaalFiles;

    @OneToOne
    private Building building;

    @OneToMany(targetEntity = RadioMap.class, cascade = CascadeType.ALL)
    private List<RadioMap>radioMaps;

    protected Project(){}

    public Project(String projectName, CalculationAlgorithm calculationAlgorithm, List<Parameter> projectParameters, List<EvaalFile> evaalFiles, Building building, List<RadioMap>radioMaps) {
        this.projectName = projectName;
        this.calculationAlgorithm = calculationAlgorithm;
        this.projectParameters = projectParameters;
        this.evaalFiles = evaalFiles;
        this.building = building;
        this.radioMaps = radioMaps;
    }

    public Project(String projectName, CalculationAlgorithm calculationAlgorithm, List<Parameter> projectParameters) {
        this.projectName = projectName;
        this.calculationAlgorithm = calculationAlgorithm;
        this.projectParameters = projectParameters;
    }

    public Long getId() {
        return id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public CalculationAlgorithm getCalculationAlgorithm() {
        return calculationAlgorithm;
    }

    public void setCalculationAlgorithm(CalculationAlgorithm calculationAlgorithm) {
        this.calculationAlgorithm = calculationAlgorithm;
    }

    public List<Parameter> getProjectParameters() {
        return projectParameters;
    }

    public void setProjectParameters(List<Parameter> projectParameters) {
        this.projectParameters = projectParameters;
    }

    public List<EvaalFile> getEvaalFiles() {
        return evaalFiles;
    }

    public void setEvaalFiles(List<EvaalFile> evaalFiles) {
        this.evaalFiles = evaalFiles;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public List<RadioMap> getRadioMaps() {
        return radioMaps;
    }

    public void setRadioMaps(List<RadioMap> radioMaps) {
        this.radioMaps = radioMaps;
    }
}
