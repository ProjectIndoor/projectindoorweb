package de.hftstuttgart.projectindoorweb.inputHandler;

import de.hftstuttgart.projectindoorweb.application.internal.AssertParam;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.EvaalFileParser;
import de.hftstuttgart.projectindoorweb.persistence.entities.Building;
import de.hftstuttgart.projectindoorweb.persistence.entities.EvaalFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EvaalFilePreProcessingServiceImpl implements PreProcessingService {


    @Override
    public List<EvaalFile> processIntoLogFiles(Building building, boolean evaluationFiles, File... radioMapFiles) {

        AssertParam.throwIfNull(radioMapFiles, "radioMapFiles");
        AssertParam.throwIfNull(building, "building");

        List<EvaalFileParser> fileParsers = new ArrayList<>(radioMapFiles.length);
        List<EvaalFile> processedEvaalFiles = new ArrayList<>();

        for (File inputFile : radioMapFiles) {
            if (!inputFile.isDirectory() && !inputFile.getPath().contains(".swp")) {
                fileParsers.add(new EvaalFileParser(evaluationFiles, inputFile, building));
            }
        }

        for (EvaalFileParser parser : fileParsers) {
            processedEvaalFiles.add(parser.parseIntoEvaalFile());
        }


        return processedEvaalFiles;

    }

}
