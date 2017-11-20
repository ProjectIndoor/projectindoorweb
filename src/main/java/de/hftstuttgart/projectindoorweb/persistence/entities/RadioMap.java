package de.hftstuttgart.projectindoorweb.persistence.entities;

import java.util.List;

public class RadioMap extends ModelBase {

    private LogFile generatedFromFile;
    private List<RadioMapElement> radioMapElements;
    private int totalNumberOfMacs;

    public RadioMap(LogFile generatedFromFile, List<RadioMapElement> radioMapElements, int totalNumberOfMacs) {
        this.generatedFromFile = generatedFromFile;
        this.radioMapElements = radioMapElements;
        this.totalNumberOfMacs = totalNumberOfMacs;
    }

    public int getTotalNumberOfMacs() {
        return totalNumberOfMacs;
    }

    public void setTotalNumberOfMacs(int totalNumberOfMacs) {
        this.totalNumberOfMacs = totalNumberOfMacs;
    }

    public LogFile getGeneratedFromFile() {
        return generatedFromFile;
    }

    public void setGeneratedFromFile(LogFile generatedFromFile) {
        this.generatedFromFile = generatedFromFile;
    }

    public List<RadioMapElement> getRadioMapElements() {
        return radioMapElements;
    }

    public void setRadioMapElements(List<RadioMapElement> radioMapElements) {
        this.radioMapElements = radioMapElements;
    }
}
