package de.hftstuttgart.projectindoorweb.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Parameter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String parameterName;
    private String paramenterValue;

    protected Parameter(){}

    public Parameter(String parameterName, String paramenterValue) {
        this.parameterName = parameterName;
        this.paramenterValue = paramenterValue;
    }

    public Long getId() {
        return id;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParamenterValue() {
        return paramenterValue;
    }

    public void setParamenterValue(String paramenterValue) {
        this.paramenterValue = paramenterValue;
    }
}
