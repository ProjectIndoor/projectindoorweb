package de.hftstuttgart.projectindoorweb.persistence.entities;

import java.util.List;

public class EvalFile {

    private String sourceFileName;
    private String customFileName;
    private List<String> filteredFileContent;

    public EvalFile(String sourceFileName, String customFileName, List<String> fileContent) {
        this.sourceFileName = sourceFileName;
        this.customFileName = customFileName;
        this.filteredFileContent = fileContent;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public String getCustomFileName() {
        return customFileName;
    }

    public void setCustomFileName(String customFileName) {
        this.customFileName = customFileName;
    }

    public List<String> getFilteredFileContent() {
        return filteredFileContent;
    }

    public void setFilteredFileContent(List<String> filteredFileContent) {
        this.filteredFileContent = filteredFileContent;
    }
}
