package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.web.internal.SimpleResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class RestTransmissionController {


    private static final String DEFAULT_VALUE_PARAM="value";
    private static final String DEFAULT_VALUE="Some default value";


    @RequestMapping(path="/simpleResponse", method=GET)
    public SimpleResponse simpleResponse(@RequestParam(value=DEFAULT_VALUE_PARAM, defaultValue=DEFAULT_VALUE) String value) {

        RestTransmissionService restTransmissionService=RestTransmissionServiceComponent.getRestTransmissionServiceInstance();
        return restTransmissionService.createSimpleResponse(value);

    }
}
