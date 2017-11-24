package de.hftstuttgart.projectindoorweb.inputHandler;

import de.hftstuttgart.projectindoorweb.persistence.entities.Building;
import de.hftstuttgart.projectindoorweb.persistence.entities.LogFile;
import de.hftstuttgart.projectindoorweb.persistence.entities.Project;
import de.hftstuttgart.projectindoorweb.persistence.entities.RadioMap;

import java.io.File;
import java.util.List;

/**
 * Top level interface for classes which can handle input files
 * (e.g. parse and persist their data)
 */
public interface PreProcessingService {

    /**
     * Implementations should be able to extract data from an input file and
     * make the content usable by the application (e.g. persisting it into a
     * database)
     *
     * @param radioMapFiles The files the PreProcessingService implementation will handle.
     * @return An instance of class {@link LogFile} that contains the generated radio map along with some meta information.
     */
    List<LogFile> processIntoLogFiles(Project project, File ... radioMapFiles);//TODO split into two methods if viable. Ex. processRadioMapFiles, processEvalFiles?


}
