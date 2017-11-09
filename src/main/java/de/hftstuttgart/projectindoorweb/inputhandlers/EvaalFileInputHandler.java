package de.hftstuttgart.projectindoorweb.inputhandlers;

import de.hftstuttgart.projectindoorweb.config.ConfigContainer;
import de.hftstuttgart.projectindoorweb.parsers.EvaalFileParser;
import de.hftstuttgart.projectindoorweb.pojos.RadiomapElement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EvaalFileInputHandler implements InputHandler {

    private static EvaalFileInputHandler instance;

    private ExecutorService executorService;

    private EvaalFileInputHandler(){}

    public static EvaalFileInputHandler getInstance(){
        if(instance == null){
            instance = new EvaalFileInputHandler();
        }
        return instance;
    }


    @Override
    public boolean handleInput(boolean filesForRadiomap, File... inputFiles) {

        boolean handlingSuccessful = false;

        executorService = Executors.newFixedThreadPool(inputFiles.length);

        List<EvaalFileParser> fileParsers = new ArrayList<>(inputFiles.length);
        for (File inputFile: inputFiles) {
            if(!inputFile.getPath().contains(".swp")){
                fileParsers.add(new EvaalFileParser(true, inputFile));
            }
        }

        for (EvaalFileParser parser: fileParsers) {
            executorService.execute(parser);
        }


        executorService.shutdown();
        try {
            executorService.awaitTermination(ConfigContainer.PARSERS_TERMINATION_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            handlingSuccessful = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<RadiomapElement> radiomapElements = new ArrayList<>();
        for (EvaalFileParser parser: fileParsers) {
            if(parser.isParsingFinished()){
                radiomapElements.addAll(parser.getRadiomapElements());
            }
        }


        /*
        * TODO: Add possibility to merge radio map elements in case ConfigContainer.MERGE_RADIOMAP_ELEMENTS is set to 'true'
        * */

        return handlingSuccessful;


    }
}
