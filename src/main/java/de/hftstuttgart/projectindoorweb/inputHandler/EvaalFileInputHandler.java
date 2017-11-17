package de.hftstuttgart.projectindoorweb.inputHandler;

import de.hftstuttgart.projectindoorweb.application.internal.AssertParam;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.ConfigContainer;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.EvaalFileHelper;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.EvaalFileParser;
import de.hftstuttgart.projectindoorweb.persistence.pojo.RadiomapElement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class EvaalFileInputHandler implements InputHandler {

    private ExecutorService executorService;

    public EvaalFileInputHandler(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public boolean handleInput(boolean filesForRadiomap, File... inputFiles) {

        AssertParam.throwIfNull(inputFiles,"inputFiles");

        boolean handlingSuccessful = false;

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
        System.out.println("Hi.");


        if(ConfigContainer.MERGE_RADIOMAP_ELEMENTS){
            radiomapElements = EvaalFileHelper.mergeSimilarPositions(radiomapElements);
        }

        return handlingSuccessful;


    }
}
