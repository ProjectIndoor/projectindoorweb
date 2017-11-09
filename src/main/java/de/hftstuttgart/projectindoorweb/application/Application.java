package de.hftstuttgart.projectindoorweb.application;


import de.hftstuttgart.projectindoorweb.config.ConfigContainer;
import de.hftstuttgart.projectindoorweb.inputhandlers.EvaalFileInputHandler;
import de.hftstuttgart.projectindoorweb.inputhandlers.InputHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Stream;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {


//        SpringApplication.run(Application.class, args);


        EvaalFileInputHandler inputHandler = EvaalFileInputHandler.getInstance();

        File[] inputFiles = new File(ConfigContainer.RADIO_MAP_FILE_PATH).listFiles();

        inputHandler.handleInput(true, inputFiles);


    }
}
