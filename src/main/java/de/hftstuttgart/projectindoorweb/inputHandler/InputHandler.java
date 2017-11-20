package de.hftstuttgart.projectindoorweb.inputHandler;

import de.hftstuttgart.projectindoorweb.persistence.entities.RadioMap;

import java.io.File;
import java.util.List;

/**
 * Top level interface for classes which can handle input files
 * (e.g. parse and persist their data)
 */
public interface InputHandler {

    /**
     * Implementations should be able to extract data from an input file and
     * make the content usable by the application (e.g. persisting it into a
     * database)
     *
     * @param  filesForRadiomap True if the files shall be used to generate a radio map, false otherwise.
     * @param inputFiles The files the InputHandler implementation will handle.
     * @return True if handling the input files was successful.
     */
    boolean handleInput(boolean filesForRadiomap, File ... inputFiles);//TODO split into two methods if viable


    List<RadioMap> getGeneratedRadioMaps();

}
