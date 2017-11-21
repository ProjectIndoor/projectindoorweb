package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class Project extends ModelBase {

    private String projectName;

   // private List<Parameter> parameters;
    //private EvalFile evalFile;
  //  private Algorithm algorithm;


    public Project(String projectName) {
        this.projectName = projectName;
        //this.parameters = parameters;
       // this.evalFile = evalFile;
        //this.algorithm = algorithm;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
