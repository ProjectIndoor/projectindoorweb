package de.hftstuttgart.projectindoorweb.application.internal.util;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicates;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;

import java.time.LocalDate;
import java.util.Collections;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

public class IndoorApiDescriptionHelper {

    private static final String EXCLUDE_ERROR_PATH_REGEX="/error.*";
    private static final String ROOT_PATH="/";
    public static final String EMPTY_STRING = "";

    private static final String TAG_NAME="Indoor REST Service";
    private static final String TAG_DESCRIPTION="Indoor REST API which provides services for positioning and project services.";

    private static final String VALIDATOR_URL = "validatorUrl";
    private static final String DOC_EXPANSION = "none";
    private static final String APIS_SORTER = "alpha";
    private static final String DEFAULT_MODEL_RENDERING = "schema";
    public static final String TITLE = "Indoor positioning API";
    public static final String DESCRIPTION = "REST API for indoor positioning services. Provides services for position localizations and project services";
    public static final String VERSION = "1.0.0";

    public static final String TERMS_OF_SERVICE = "Terms of service";

    public static Docket createIndoorRestApiDocket(TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex(EXCLUDE_ERROR_PATH_REGEX)))
                .build()
                .pathMapping(ROOT_PATH)
                .directModelSubstitute(LocalDate.class,
                        String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false)
                .enableUrlTemplating(true)
                .tags(new Tag(TAG_NAME, TAG_DESCRIPTION)).apiInfo(apiInfo());
    }

    public static UiConfiguration createIndoorRestApiUiConfiguration() {
        return new UiConfiguration(
                VALIDATOR_URL,
                DOC_EXPANSION,
                APIS_SORTER,
                DEFAULT_MODEL_RENDERING,
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
                false,
                true,
                60000L);
    }

    private static ApiInfo apiInfo() {
        return new ApiInfo(
                TITLE,
                DESCRIPTION,
                VERSION,
                TERMS_OF_SERVICE,
                new Contact(EMPTY_STRING, EMPTY_STRING, EMPTY_STRING),
                EMPTY_STRING, EMPTY_STRING, Collections.emptyList());
    }
}
