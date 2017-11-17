package de.hftstuttgart.projectindoorweb.persistence.entities;

import java.util.List;

public class Project extends ModelBase {

    private String projectName;

    private List<Parameter> parameters;
    private EvalFile evalFile;
    private Algorithm algorithm;


}
