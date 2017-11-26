package de.hftstuttgart.projectindoorweb.inputHandler;

import de.hftstuttgart.projectindoorweb.application.internal.AssertParam;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.ConfigContainer;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.LogFileHelper;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.LogFileParser;
import de.hftstuttgart.projectindoorweb.persistence.entities.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LogFilePreProcessingServiceImpl implements PreProcessingService {



    @Override
    public List<LogFile> processIntoLogFiles(Project project, File... radioMapFiles) {

        AssertParam.throwIfNull(radioMapFiles,"radioMapFiles");

        List<LogFileParser> fileParsers = new ArrayList<>(radioMapFiles.length);
        for (File inputFile: radioMapFiles) {
            if(!inputFile.isDirectory() && !inputFile.getPath().contains(".swp")){
                fileParsers.add(new LogFileParser(true, inputFile));
            }
        }

        ExecutorService executorService = Executors.newFixedThreadPool(radioMapFiles.length); //Can not be globalised as thread pool has to be redefined on every call

        for (LogFileParser parser: fileParsers) {
            executorService.execute(parser);
        }


        executorService.shutdown();
        try {
            executorService.awaitTermination(ConfigContainer.PARSERS_TERMINATION_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int totalNumberOfMacs = 0;
        List<RadioMapElement> radiomapElements = new ArrayList<>();
        List<LogFile> processedLogFiles = new ArrayList<>();
        LogFile logFile;
        String fileName;
        RadioMap generatedRadioMap;
        for (LogFileParser parser: fileParsers) {
            if(parser.isParsingFinished()){
                fileName = parser.getSourceFile().getName();
                radiomapElements = parser.getRadiomapElements();
                generatedRadioMap = new RadioMap(radiomapElements);
                processedLogFiles.add(new LogFile(fileName, fileName, 0, generatedRadioMap));

            }
        }


        return processedLogFiles;

    }

}
