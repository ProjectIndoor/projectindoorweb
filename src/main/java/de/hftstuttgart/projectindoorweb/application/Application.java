package de.hftstuttgart.projectindoorweb.application;


import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.ConfigContainer;
import de.hftstuttgart.projectindoorweb.inputHandler.InputHandler;
import de.hftstuttgart.projectindoorweb.inputHandler.InputHandlerComponent;
import de.hftstuttgart.projectindoorweb.web.PositioningController;
import de.hftstuttgart.projectindoorweb.web.ProjectController;
import de.hftstuttgart.projectindoorweb.web.RestTransmissionServiceComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@ComponentScan(basePackageClasses=PositioningController.class)
@ComponentScan(basePackageClasses=ProjectController.class)
public class Application {

    public static void main(String[] args) {

        startApplication();
        SpringApplication.run(Application.class, args);
    }

    private static void startApplication() {

        File[] inputFiles = new File(ConfigContainer.RADIO_MAP_FILE_PATH).listFiles();

        initComponents(inputFiles.length);

        InputHandler inputHandler = InputHandlerComponent.getInputHandler();
        inputHandler.handleInput(true, inputFiles);
    }

    private static void initComponents(int threadPoolSize) {

        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

        InputHandlerComponent.initComponent(executorService);

        RestTransmissionServiceComponent.initComponent(InputHandlerComponent.getInputHandler());
    }
}
