package de.hftstuttgart.projectindoorweb.persistence.entities;

public class Algorithm {

    private String algorithmName;

    public Algorithm(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }
}
