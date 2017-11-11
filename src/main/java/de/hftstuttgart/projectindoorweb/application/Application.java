package de.hftstuttgart.projectindoorweb.application;


import de.hftstuttgart.projectindoorweb.config.ConfigContainer;
import de.hftstuttgart.projectindoorweb.inputhandlers.EvaalFileInputHandler;
import de.hftstuttgart.projectindoorweb.web.RestTransmissionController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

@SpringBootApplication
@ComponentScan(basePackageClasses=RestTransmissionController.class)
public class Application {

    public static void main(String[] args) {

        startApplicatpion();
        SpringApplication.run(Application.class, args);
    }

    private static void startApplicatpion() {
        initComponents();

        EvaalFileInputHandler inputHandler = EvaalFileInputHandler.getInstance();

        File[] inputFiles = new File(ConfigContainer.RADIO_MAP_TEST_FILE_PATH).listFiles();

        inputHandler.handleInput(true, inputFiles);
    }

    private static void initComponents() {

    }
}
