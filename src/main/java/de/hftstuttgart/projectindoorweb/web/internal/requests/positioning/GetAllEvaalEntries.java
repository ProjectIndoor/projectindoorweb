package de.hftstuttgart.projectindoorweb.web.internal.requests.positioning;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAllEvaalEntries {

    private Long evaalFileId;
    private String fileName;
    private String type;
    private String buildingName;

    @JsonCreator
    public GetAllEvaalEntries(@JsonProperty("evaalFileId")Long evaalFileId,
                              @JsonProperty("fileName")String fileName,
                              @JsonProperty("type")String type,
                              @JsonProperty("buildingName")String buildingName) {
        this.evaalFileId = evaalFileId;
        this.fileName = fileName;
        this.type = type;
        this.buildingName = buildingName;
    }

    public Long getEvaalFileId() {
        return evaalFileId;
    }

    public void setEvaalFileId(Long evaalFileId) {
        this.evaalFileId = evaalFileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
}
