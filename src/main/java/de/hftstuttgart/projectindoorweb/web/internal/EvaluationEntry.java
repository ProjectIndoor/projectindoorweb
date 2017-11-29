package de.hftstuttgart.projectindoorweb.web.internal;

public class EvaluationEntry {

    private Long id;

    private String evaluationEntryName;

    private String evaluationEntryContent;

    public EvaluationEntry(long id, String evaluationEntryName, String evaluationEntryContent) {
        this.id = id;
        this.evaluationEntryName = evaluationEntryName;
        this.evaluationEntryContent = evaluationEntryContent;
    }

    public Long getId() {
        return id;
    }

    public String getEvaluationEntryName() {
        return evaluationEntryName;
    }

    public String getEvaluationEntryContent() {
        return evaluationEntryContent;
    }
}
