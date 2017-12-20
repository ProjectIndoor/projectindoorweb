package de.hftstuttgart.projectindoorweb.application;


import com.fasterxml.classmate.TypeResolver;
import de.hftstuttgart.projectindoorweb.application.internal.util.IndoorApiDescriptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = {
        "de.hftstuttgart.projectindoorweb.persistence",
        "de.hftstuttgart.projectindoorweb.positionCalculator",
        "de.hftstuttgart.projectindoorweb.inputHandler",
        "de.hftstuttgart.projectindoorweb.web"})
@EnableSwagger2
@EntityScan("de.hftstuttgart.projectindoorweb.persistence.entities")
@EnableJpaRepositories("de.hftstuttgart.projectindoorweb.persistence.repositories")
public class Application {

    @Autowired
    private TypeResolver typeResolver;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
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
