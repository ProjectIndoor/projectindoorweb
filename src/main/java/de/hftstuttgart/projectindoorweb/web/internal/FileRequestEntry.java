package de.hftstuttgart.projectindoorweb.web.internal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

public class FileRequestEntry {

    private String buildingIdentifier;
    private boolean withPixelPosition;
    private MultipartFile[] files;

    @JsonCreator
    public FileRequestEntry(@JsonProperty("buildingIdentifier") String buildingIdentifier,
                            @JsonProperty("withPixelPosition") boolean withPixelPosition,
                            @JsonProperty("files") MultipartFile[] files){

        this.buildingIdentifier=buildingIdentifier;
        this.withPixelPosition=withPixelPosition;
        this.files = files;
    }

    public String getBuildingIdentifier() {
        return buildingIdentifier;
    }

    public void setBuildingIdentifier(String buildingIdentifier) {
        this.buildingIdentifier = buildingIdentifier;
    }

    public boolean isWithPixelPosition() {
        return withPixelPosition;
    }

    public void setWithPixelPosition(boolean withPixelPosition) {
        this.withPixelPosition = withPixelPosition;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
