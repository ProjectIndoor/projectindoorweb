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

    @ManyToOne(targetEntity = Building.class)
    private Building building;

    @ManyToOne(targetEntity = EvaalFile.class)
    private EvaalFile evaluationFile;

    @ManyToMany(targetEntity = EvaalFile.class)
    @JoinTable(name = "project_files",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "evaal_file_id", referencedColumnName = "id"))
    private List<EvaalFile> evaalFiles;

    protected Project() {
    }

    public Project(String projectName, CalculationAlgorithm calculationAlgorithm, List<Parameter> projectParameters) {
        this.projectName = projectName;
        this.calculationAlgorithm = calculationAlgorithm;
        this.projectParameters = projectParameters;
    }

    public Project(String projectName, CalculationAlgorithm calculationAlgorithm,
                   List<Parameter> projectParameters, Building building, EvaalFile evaluationFile, List<EvaalFile> evaalFiles) {
        this.projectName = projectName;
        this.calculationAlgorithm = calculationAlgorithm;
        this.building = building;
        this.projectParameters = projectParameters;
        this.evaluationFile = evaluationFile;
        this.evaalFiles = evaalFiles;
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

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public List<Parameter> getProjectParameters() {
        return projectParameters;
    }

    public void setProjectParameters(List<Parameter> projectParameters) {
        this.projectParameters = projectParameters;
    }

    public EvaalFile getEvaluationFile() {
        return evaluationFile;
    }

    public void setEvaluationFile(EvaalFile evaluationFile) {
        this.evaluationFile = evaluationFile;
    }

    public List<EvaalFile> getEvaalFiles() {
        return evaalFiles;
    }

    public void setEvaalFiles(List<EvaalFile> evaalFiles) {
        this.evaalFiles = evaalFiles;
    }
}
