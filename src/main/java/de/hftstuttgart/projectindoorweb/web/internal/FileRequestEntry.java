package de.hftstuttgart.projectindoorweb.web.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

public class FileRequestEntry {

    private String buildingIdentifier;
    private MultipartFile[] files;

    @JsonCreator
    public FileRequestEntry(@JsonProperty("buildingIdentifier") String buildingIdentifier,
                            @JsonProperty("files") MultipartFile[] files){

        this.buildingIdentifier=buildingIdentifier;
        this.files = files;
    }

    public String getBuildingIdentifier() {
        return buildingIdentifier;
    }

    public void setBuildingIdentifier(String buildingIdentifier) {
        this.buildingIdentifier = buildingIdentifier;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
