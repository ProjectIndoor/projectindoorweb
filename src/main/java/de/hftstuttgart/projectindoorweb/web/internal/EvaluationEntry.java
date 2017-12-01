package de.hftstuttgart.projectindoorweb.web.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EvaluationEntry {

    private Long id;

    private String evaluationEntryName;

    @JsonCreator
    public EvaluationEntry(@JsonProperty("id")long id, @JsonProperty("evaluationEntryName")String evaluationEntryName) {
        this.id = id;
        this.evaluationEntryName = evaluationEntryName;
    }

    public Long getId() {
        return id;
    }

    public String getEvaluationEntryName() {
        return evaluationEntryName;
    }
}
