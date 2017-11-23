package de.hftstuttgart.projectindoorweb.inputHandler;

import de.hftstuttgart.projectindoorweb.persistence.entities.RadioMap;

import java.io.File;
import java.util.List;

/**
 * Top level interface for classes which can handle input files
 * (e.g. parse and persist their data)
 */
public interface PreProcessingService { //TODO change name to PreProcessingService and package name to prepocessing?

    /**
     * Implementations should be able to extract data from an input file and
     * make the content usable by the application (e.g. persisting it into a
     * database)
     *
     * @param radioMapFiles The files the PreProcessingService implementation will handle.
     * @return The generated radio maps as objects of class {@link RadioMap}.
     */
    List<RadioMap> generateRadioMap(File ... radioMapFiles);//TODO split into two methods if viable. Ex. processRadioMapFiles, processEvalFiles?


}
