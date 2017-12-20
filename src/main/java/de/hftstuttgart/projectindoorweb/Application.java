package de.hftstuttgart.projectindoorweb;


import com.fasterxml.classmate.TypeResolver;
import de.hftstuttgart.projectindoorweb.application.internal.util.IndoorApiDescriptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
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
