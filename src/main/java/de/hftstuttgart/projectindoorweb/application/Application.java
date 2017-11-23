package de.hftstuttgart.projectindoorweb.application;


import de.hftstuttgart.projectindoorweb.persistence.PersistencyServiceComponent;
import de.hftstuttgart.projectindoorweb.persistence.RepositoryRegistry;
import de.hftstuttgart.projectindoorweb.persistence.entities.Project;
import de.hftstuttgart.projectindoorweb.persistence.repositories.ProjectRepository;
import de.hftstuttgart.projectindoorweb.positionCalculator.PositionCalculatorComponent;
import de.hftstuttgart.projectindoorweb.inputHandler.PreProcessingServiceComponent;
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

@SpringBootApplication
@ComponentScan(basePackageClasses=PositioningController.class)
@ComponentScan(basePackageClasses=ProjectController.class)
@EntityScan("de.hftstuttgart.projectindoorweb.persistence.entities")
@EnableJpaRepositories("de.hftstuttgart.projectindoorweb.persistence.repositories")
public class Application {

    public static void main(String[] args) {

        initComponents();
        SpringApplication.run(Application.class, args);
    }

    private static void initComponents() {

        PreProcessingServiceComponent.initComponent();
        PositionCalculatorComponent.initComponent();
        PersistencyServiceComponent.initComponent();
        RestTransmissionServiceComponent.initComponent();

    }

    @Bean
    public CommandLineRunner initApplication(ProjectRepository projectRepository){

        return (args) -> {
            RepositoryRegistry.registerRepository(Project.class.getName(),projectRepository);
        };
    }
}
