package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.web.internal.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private RestTransmissionService restTransmissionService = RestTransmissionServiceComponent.getRestTransmissionServiceInstance();

    @RequestMapping(path = "/saveNewProject", method = GET)
    public long saveNewProject(@RequestBody Set<ProjectParameter> projectParameterSet,
                               @RequestParam(value = TransmissionConstants.PROJECT_NAME_PARAM,
                                       defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                       String projectName,
                               @RequestParam(value = TransmissionConstants.ALGORITHM_TYPE_PARAM,
                                       defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                       String algorithmType) {
        return restTransmissionService.saveNewProject(projectParameterSet, projectName, algorithmType);
    }

    @RequestMapping(path = "/saveCurrentProject", method = GET)
    public boolean saveCurrentProject(@RequestBody Set<ProjectParameter> projectParameterSet,
                                      @RequestParam(value = TransmissionConstants.PROJECT_IDENTIFIER_PARAM,
                                              defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                              String projectIdentifier,
                                      @RequestParam(value = TransmissionConstants.PROJECT_NAME_PARAM,
                                              defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                              String projectName,
                                      @RequestParam(value = TransmissionConstants.ALGORITHM_TYPE_PARAM,
                                              defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                              String algorithmType) {
        return restTransmissionService.saveCurrentProject(projectName, projectParameterSet, projectIdentifier, algorithmType);
    }

    @RequestMapping(path = "/deleteSelectedProject", method = GET)
    public boolean deleteSelectedProject(@RequestParam(value = TransmissionConstants.PROJECT_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                                 String projectIdentifier) {
        return restTransmissionService.deleteSelectedProject(projectIdentifier);
    }

    @RequestMapping(path = "/loadSelectedProject", method = GET)
    public ResponseEntity<ProjectElement> loadSelectedProject(@RequestParam(value = TransmissionConstants.PROJECT_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE) String projectIdentifier) {
        ProjectElement result = restTransmissionService.loadSelectedProject(projectIdentifier);
        return new ResponseEntity<ProjectElement>(result, HttpStatus.OK);
    }

    @RequestMapping(path = "/getAllProjects", method = GET)
    public ResponseEntity<List<ProjectElement>> getAllProjects() {

        List<ProjectElement> result = restTransmissionService.getAllProjects();

        return new ResponseEntity<List<ProjectElement>>(result, HttpStatus.OK);

    }

    @RequestMapping(path = "/getAllAlgorithmTypes", method = GET)
    public ResponseEntity<List<AlgorithmType>> getAllAlgorithmTypes() {

        List<AlgorithmType> result = restTransmissionService.getAllAlgorithmTypes();

        return new ResponseEntity<List<AlgorithmType>>(result, HttpStatus.OK);

    }

    @RequestMapping(path = "/getAlgorithmParameterListForAlgorithmId", method = GET)
    public ResponseEntity<List<ParameterElement>> getAlgorithmParameterListForAlgorithmId(@RequestParam(value = TransmissionConstants.ALGORITHM_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)String algorithmIdentifier) {

        List<ParameterElement> result = restTransmissionService.getAlgorithmParameterListForAlgorithmId(algorithmIdentifier);

        return new ResponseEntity<List<ParameterElement>>(result, HttpStatus.OK);

    }
}
