package de.hftstuttgart.projectindoorweb.application;


import com.fasterxml.classmate.TypeResolver;
import de.hftstuttgart.projectindoorweb.application.internal.util.IndoorApiDescriptionHelper;
import de.hftstuttgart.projectindoorweb.inputHandler.PreProcessingService;
import de.hftstuttgart.projectindoorweb.persistence.PersistencyService;
import de.hftstuttgart.projectindoorweb.persistence.PersistencyServiceComponent;
import de.hftstuttgart.projectindoorweb.persistence.RepositoryRegistry;
import de.hftstuttgart.projectindoorweb.persistence.entities.Building;
import de.hftstuttgart.projectindoorweb.persistence.entities.LogFile;
import de.hftstuttgart.projectindoorweb.persistence.entities.PositionResult;
import de.hftstuttgart.projectindoorweb.persistence.entities.Project;
import de.hftstuttgart.projectindoorweb.persistence.repositories.BuildingRepository;
import de.hftstuttgart.projectindoorweb.persistence.repositories.LogFileRepository;
import de.hftstuttgart.projectindoorweb.persistence.repositories.ProjectRepository;
import de.hftstuttgart.projectindoorweb.positionCalculator.PositionCalculatorComponent;
import de.hftstuttgart.projectindoorweb.inputHandler.PreProcessingServiceComponent;
import de.hftstuttgart.projectindoorweb.positionCalculator.PositionCalculatorService;
import de.hftstuttgart.projectindoorweb.web.BuildingController;
import de.hftstuttgart.projectindoorweb.web.PositioningController;
import de.hftstuttgart.projectindoorweb.web.ProjectController;
import de.hftstuttgart.projectindoorweb.web.RestTransmissionServiceComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackageClasses = PositioningController.class)
@ComponentScan(basePackageClasses = ProjectController.class)
@ComponentScan(basePackageClasses = BuildingController.class)
@EntityScan("de.hftstuttgart.projectindoorweb.persistence.entities")
@EnableJpaRepositories("de.hftstuttgart.projectindoorweb.persistence.repositories")
public class Application {

    @Autowired
    private TypeResolver typeResolver;

    public static void main(String[] args) {

        initComponents();
        SpringApplication.run(Application.class, args);
    }

    private static void initComponents() {

        PreProcessingServiceComponent.initComponent();
        PositionCalculatorComponent.initComponent();
        PersistencyServiceComponent.initComponent();

        PersistencyService persistencyService = PersistencyServiceComponent.getPersistencyService();
        PreProcessingService preProcessingService = PreProcessingServiceComponent.getPreProcessingService();
        PositionCalculatorService positionCalculatorService = PositionCalculatorComponent.getPositionCalculator();

        RestTransmissionServiceComponent.initComponent(persistencyService, preProcessingService, positionCalculatorService);

    }

    @Bean
    public CommandLineRunner initApplication(ProjectRepository projectRepository,
                                             LogFileRepository logFileRepository,
                                             BuildingRepository buildingRepository) {

        return (args) -> {
            RepositoryRegistry.registerRepository(Project.class.getName(), projectRepository);
            RepositoryRegistry.registerRepository(LogFile.class.getName(), logFileRepository);
            RepositoryRegistry.registerRepository(Building.class.getName(), buildingRepository);
        };
    }

    @Bean
    public Docket initIndoorRestApi() {
        return IndoorApiDescriptionHelper.createIndoorRestApiDocket(typeResolver);
    }

    @Bean
    UiConfiguration initUiConfiguration() {
        return IndoorApiDescriptionHelper.createIndoorRestApiUiConfiguration();
    }
}
