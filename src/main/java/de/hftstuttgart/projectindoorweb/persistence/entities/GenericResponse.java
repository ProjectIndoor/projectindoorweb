package de.hftstuttgart.projectindoorweb.persistence.entities;

public class GenericResponse {

    private long resultId;
    private String resultMessage;

    public GenericResponse(long resultId, String resultMessage) {
        this.resultId = resultId;
        this.resultMessage = resultMessage;
    }

    public long getResultId() {
        return resultId;
    }

    public void setResultId(long resultId) {
        this.resultId = resultId;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

}
