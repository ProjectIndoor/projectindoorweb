package de.hftstuttgart.projectindoorweb.inputHandler;

import de.hftstuttgart.projectindoorweb.application.internal.AssertParam;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.ConfigContainer;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.LogFileHelper;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.LogFileParser;
import de.hftstuttgart.projectindoorweb.persistence.entities.RadioMap;
import de.hftstuttgart.projectindoorweb.persistence.entities.RadioMapElement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LogFilePreProcessingServiceImpl implements PreProcessingService {



    @Override
    public List<RadioMap> generateRadioMap(File... radioMapFiles) {

        AssertParam.throwIfNull(radioMapFiles,"radioMapFiles");

        List<LogFileParser> fileParsers = new ArrayList<>(radioMapFiles.length);
        for (File inputFile: radioMapFiles) {
            if(!inputFile.isDirectory() && !inputFile.getPath().contains(".swp")){
                fileParsers.add(new LogFileParser(true, inputFile));
            }
        }

        ExecutorService executorService = Executors.newFixedThreadPool(radioMapFiles.length);
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
        List<RadioMap> preProcessingRadioMaps = new ArrayList<>();
        for (LogFileParser parser: fileParsers) {
            if(parser.isParsingFinished()){
                radiomapElements = parser.getRadiomapElements();
                preProcessingRadioMaps.add(new RadioMap(radiomapElements));
            }
        }

        //TODO Move merging to position calculation
        if(ConfigContainer.MERGE_RADIOMAP_ELEMENTS){
            RadioMap tmp = LogFileHelper.mergeRadioMapsBySimilarPositions(preProcessingRadioMaps);
            preProcessingRadioMaps.clear();
            preProcessingRadioMaps.add(tmp);
        }


        return preProcessingRadioMaps;

    }

}
