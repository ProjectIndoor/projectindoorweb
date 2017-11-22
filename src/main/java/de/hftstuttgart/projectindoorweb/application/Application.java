package de.hftstuttgart.projectindoorweb.application;


import de.hftstuttgart.projectindoorweb.persistence.PersistencyServiceComponent;
import de.hftstuttgart.projectindoorweb.persistence.RepositoryRegistry;
import de.hftstuttgart.projectindoorweb.persistence.entities.Project;
import de.hftstuttgart.projectindoorweb.persistence.repositories.ProjectRepository;
import de.hftstuttgart.projectindoorweb.positionCalculator.PositionCalculatorService;
import de.hftstuttgart.projectindoorweb.positionCalculator.PositionCalculatorComponent;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.ConfigContainer;
import de.hftstuttgart.projectindoorweb.inputHandler.InputHandler;
import de.hftstuttgart.projectindoorweb.inputHandler.InputHandlerComponent;
import de.hftstuttgart.projectindoorweb.persistence.entities.PositionResult;
import de.hftstuttgart.projectindoorweb.web.PositioningController;
import de.hftstuttgart.projectindoorweb.web.ProjectController;
import de.hftstuttgart.projectindoorweb.web.RestTransmissionServiceComponent;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@ComponentScan(basePackageClasses=PositioningController.class)
@ComponentScan(basePackageClasses=ProjectController.class)
@EntityScan("de.hftstuttgart.projectindoorweb.persistence.entities")
@EnableJpaRepositories("de.hftstuttgart.projectindoorweb.persistence.repositories")
public class Application {

    public static void main(String[] args) {

        startApplication();
        SpringApplication.run(Application.class, args);
    }

    private static void startApplication() {

        File[] inputFiles = new File(ConfigContainer.RADIO_MAP_TEST_FILE_PATH).listFiles();
        File evalFile = new File(ConfigContainer.EVALUATION_FILE_PATH);

        initComponents(inputFiles.length);

        InputHandler inputHandler = InputHandlerComponent.getInputHandler();
        inputHandler.handleInput(true, inputFiles);

        PositionCalculatorService positionCalculator = PositionCalculatorComponent.getPositionCalculator();
        List<? extends PositionResult> positionResults = positionCalculator.calculatePositions(evalFile, inputHandler.getGeneratedRadioMaps());

    }

    private static void initComponents(int threadPoolSize) {

        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

        InputHandlerComponent.initComponent(executorService);
        PositionCalculatorComponent.initComponent();
        PersistencyServiceComponent.initComponent();

        RestTransmissionServiceComponent.initComponent(InputHandlerComponent.getInputHandler());
    }

    @Bean
    public CommandLineRunner initApplication(ProjectRepository projectRepository){

        return (args) -> {
            RepositoryRegistry.registerRepository(Project.class.getName(),projectRepository);
        };
    }
}
