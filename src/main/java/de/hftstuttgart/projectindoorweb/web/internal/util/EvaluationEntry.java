package de.hftstuttgart.projectindoorweb.web.internal.util;

public class EvaluationEntry {
    private String evaluationEntryName;

    private String evaluationEntryContent;

    public EvaluationEntry(String evaluationEntryName, String evaluationEntryContent) {
        this.evaluationEntryName = evaluationEntryName;
        this.evaluationEntryContent = evaluationEntryContent;
    }

    public String getEvaluationEntryName() {
        return evaluationEntryName;
    }

    public String getEvaluationEntryContent() {
        return evaluationEntryContent;
    }
}
