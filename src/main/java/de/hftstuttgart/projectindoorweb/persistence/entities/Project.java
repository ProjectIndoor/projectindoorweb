package de.hftstuttgart.projectindoorweb.persistence.entities;

import java.util.List;

public class Project extends ModelBase {

    private String projectName;

    private List<Parameter> parameters;
    private EvalFile evalFile;
    private Algorithm algorithm;


    public Project(String projectName, List<Parameter> parameters, EvalFile evalFile, Algorithm algorithm) {
        this.projectName = projectName;
        this.parameters = parameters;
        this.evalFile = evalFile;
        this.algorithm = algorithm;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public EvalFile getEvalFile() {
        return evalFile;
    }

    public void setEvalFile(EvalFile evalFile) {
        this.evalFile = evalFile;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }
}
