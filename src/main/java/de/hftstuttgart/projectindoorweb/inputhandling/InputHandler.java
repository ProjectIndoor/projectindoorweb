package de.hftstuttgart.projectindoorweb.inputhandling;

import java.io.File;

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
     * @param inputFile file which should be handled
     * @return true if file was handled successfully
     */
    boolean parseFile(File inputFile);

}
