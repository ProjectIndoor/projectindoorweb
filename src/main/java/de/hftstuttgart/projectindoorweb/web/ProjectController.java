package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.web.internal.requests.project.*;
import de.hftstuttgart.projectindoorweb.web.internal.util.TransmissionConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@Api(value = "/Project", description = "Operations for projects", tags = "Project")
@RequestMapping("/project")
public class ProjectController {

   private RestTransmissionService restTransmissionService = RestTransmissionServiceComponent.getRestTransmissionServiceInstance();

    @ApiOperation(value = "Save a new project", nickname = "project/saveNewProject", notes = TransmissionConstants.SAVE_NEW_PROJECT_NOTE)
    @RequestMapping(path = "/saveNewProject", method = POST)
    public long saveNewProject(@RequestBody SaveNewProject saveNewProject) {
        return restTransmissionService.saveNewProject(saveNewProject);
    }

    @ApiOperation(value = "Save a current project", nickname = "project/saveCurrentProject", notes = TransmissionConstants.SAVE_CURRENT_PROJECT_NOTE)
    @RequestMapping(path = "/saveCurrentProject", method = POST)
    public boolean saveCurrentProject(@RequestBody SaveCurrentProject saveCurrentProject) {
        return restTransmissionService.saveCurrentProject(saveCurrentProject);
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
    public ResponseEntity<LoadSelectedProject> loadSelectedProject(@RequestParam(value = TransmissionConstants.PROJECT_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE) String projectIdentifier) {
        LoadSelectedProject result = restTransmissionService.loadSelectedProject(projectIdentifier);
        return new ResponseEntity<LoadSelectedProject>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get all current projects", nickname = "project/getAllProjects", notes= TransmissionConstants.GET_ALL_PROJECT_NOTE)
    @RequestMapping(path = "/getAllProjects", method = GET)
    public ResponseEntity<List<LoadSelectedProject>> getAllProjects() {

        List<LoadSelectedProject> result = restTransmissionService.getAllProjects();

        return new ResponseEntity<List<LoadSelectedProject>>(result, HttpStatus.OK);

    }

    @ApiOperation(value = "Get all available positioning algorithms", nickname = "project/getAllAlgorithmTypes", notes = TransmissionConstants.GET_ALL_ALGORITHMS_NOTE)
    @RequestMapping(path = "/getAllAlgorithmTypes", method = GET)
    public ResponseEntity<List<GetAllAlgorithmTypes>> getAllAlgorithmTypes() {

        List<GetAllAlgorithmTypes> result = restTransmissionService.getAllAlgorithmTypes();

        return new ResponseEntity<List<GetAllAlgorithmTypes>>(result, HttpStatus.OK);

    }

    @ApiOperation(value = "Get a list of parameters which are used by a given algorithm", nickname = "project/getAlgorithmParameterListForAlgorithmId", notes = TransmissionConstants.GET_PARAMETERS_FOR_ALGORITHM_NOTE)
    @RequestMapping(path = "/getAlgorithmParameterListForAlgorithmId", method = GET)
    public ResponseEntity<List<GetAlgorithmParameters>> getAlgorithmParameterListForAlgorithmId(@RequestParam(value = TransmissionConstants.ALGORITHM_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)String algorithmIdentifier) {

        List<GetAlgorithmParameters> result = restTransmissionService.getAlgorithmParameterListForAlgorithmId(algorithmIdentifier);

        return new ResponseEntity<List<GetAlgorithmParameters>>(result, HttpStatus.OK);

    }
}
