package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.web.internal.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "/Project", description = "Operations for projects", tags = "Project")
@RequestMapping("/project")
public class ProjectController {

   private RestTransmissionService restTransmissionService = RestTransmissionServiceComponent.getRestTransmissionServiceInstance();

    @ApiOperation(value = "Save a new project", nickname = "project/saveNewProject", notes = TransmissionConstants.SAVE_NEW_PROJECT_NOTE)
    @RequestMapping(path = "/saveNewProject", method = POST)
    public long saveNewProject(@RequestBody Set<ProjectParameter> projectParameterSet,
                               @RequestParam(value = TransmissionConstants.PROJECT_NAME_PARAM,
                                       defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                       String projectName,
                               @RequestParam(value = TransmissionConstants.ALGORITHM_TYPE_PARAM,
                                       defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                       String algorithmType) {
        return restTransmissionService.saveNewProject(projectParameterSet, projectName, algorithmType);
    }

    @ApiOperation(value = "Save a current project", nickname = "project/saveCurrentProject", notes = TransmissionConstants.SAVE_CURRENT_PROJECT_NOTE)
    @RequestMapping(path = "/saveCurrentProject", method = POST)
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

    @ApiOperation(value = "Delete a selected project with a project identifier", nickname = "project/deleteSelectedProject", notes = TransmissionConstants.DELETE_PROJECT_NOTE)
    @RequestMapping(path = "/deleteSelectedProject", method = DELETE)
    public boolean deleteSelectedProject(@RequestParam(value = TransmissionConstants.PROJECT_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                                 String projectIdentifier) {
        return restTransmissionService.deleteSelectedProject(projectIdentifier);
    }


    @ApiOperation(value = "Load a selected project with a project identifier", nickname = "project/loadSelectedProject", notes = TransmissionConstants.LOAD_PROJECT_NOTE)
    @RequestMapping(path = "/loadSelectedProject", method = GET)
    public ResponseEntity<ProjectElement> loadSelectedProject(@RequestParam(value = TransmissionConstants.PROJECT_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE) String projectIdentifier) {
        ProjectElement result = restTransmissionService.loadSelectedProject(projectIdentifier);
        return new ResponseEntity<ProjectElement>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get all current projects", nickname = "project/getAllProjects", notes= TransmissionConstants.GET_ALL_PROJECT_NOTE)
    @RequestMapping(path = "/getAllProjects", method = GET)
    public ResponseEntity<List<ProjectElement>> getAllProjects() {

        List<ProjectElement> result = restTransmissionService.getAllProjects();

        return new ResponseEntity<List<ProjectElement>>(result, HttpStatus.OK);

    }

    @ApiOperation(value = "Get all available positioning algorithms", nickname = "project/getAllAlgorithmTypes", notes = TransmissionConstants.GET_ALL_ALGORITHMS_NOTE)
    @RequestMapping(path = "/getAllAlgorithmTypes", method = GET)
    public ResponseEntity<List<AlgorithmType>> getAllAlgorithmTypes() {

        List<AlgorithmType> result = restTransmissionService.getAllAlgorithmTypes();

        return new ResponseEntity<List<AlgorithmType>>(result, HttpStatus.OK);

    }

    @ApiOperation(value = "Get a list of parameters which are used by a given algorithm", nickname = "project/getAlgorithmParameterListForAlgorithmId", notes = TransmissionConstants.GET_PARAMETERS_FOR_ALGORITHM_NOTE)
    @RequestMapping(path = "/getAlgorithmParameterListForAlgorithmId", method = GET)
    public ResponseEntity<List<ParameterElement>> getAlgorithmParameterListForAlgorithmId(@RequestParam(value = TransmissionConstants.ALGORITHM_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)String algorithmIdentifier) {

        List<ParameterElement> result = restTransmissionService.getAlgorithmParameterListForAlgorithmId(algorithmIdentifier);

        return new ResponseEntity<List<ParameterElement>>(result, HttpStatus.OK);

    }
}
