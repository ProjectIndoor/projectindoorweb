package de.hftstuttgart.projectindoorweb.web.internal;

public class AlgorithmType {

    private String className;
    private String niceName;

    public AlgorithmType(String className, String niceName) {
        this.className = className;
        this.niceName = niceName;
    }

    public String getClassName() {
        return className;
    }

    public String getNiceName() {
        return niceName;
    }
}
