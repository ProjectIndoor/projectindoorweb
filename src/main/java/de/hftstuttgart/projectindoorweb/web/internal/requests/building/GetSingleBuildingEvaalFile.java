package de.hftstuttgart.projectindoorweb.web.internal.requests.building;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetSingleBuildingEvaalFile {

    private long evaalFileId;
    private String evaalFileName;
    private boolean evaluationFile;

    @JsonCreator
    public GetSingleBuildingEvaalFile(@JsonProperty("evaalFileId") long evaalFileId,
                                      @JsonProperty("evaalFileName") String evaalFileName,
                                      @JsonProperty("evaluationFile") boolean evaluationFile) {
        this.evaalFileId = evaalFileId;
        this.evaalFileName = evaalFileName;
        this.evaluationFile = evaluationFile;
    }

    public long getEvaalFileId() {
        return evaalFileId;
    }

    public void setEvaalFileId(long evaalFileId) {
        this.evaalFileId = evaalFileId;
    }

    public String getEvaalFileName() {
        return evaalFileName;
    }

    public void setEvaalFileName(String evaalFileName) {
        this.evaalFileName = evaalFileName;
    }

    public boolean isEvaluationFile() {
        return evaluationFile;
    }

    public void setEvaluationFile(boolean evaluationFile) {
        this.evaluationFile = evaluationFile;
    }
}
