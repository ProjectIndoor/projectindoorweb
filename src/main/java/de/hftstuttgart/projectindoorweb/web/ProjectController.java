package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.web.internal.HttpResultHandler;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.*;
import de.hftstuttgart.projectindoorweb.web.internal.util.TransmissionConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RestController
@Api(value = "/Project", description = "Operations for projects", tags = "Project")
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private RestTransmissionService restTransmissionService;

    @ApiOperation(value = "Save a new project", nickname = "project/addNewProject", notes = TransmissionConstants.SAVE_NEW_PROJECT_NOTE)
    @RequestMapping(path = "/saveNewProject", method = POST)
    public long saveNewProject(@RequestBody AddNewProject addNewProject) {
        return restTransmissionService.addNewProject(addNewProject);
    }

    @ApiOperation(value = "Save a current project", nickname = "project/updateProject", notes = TransmissionConstants.SAVE_CURRENT_PROJECT_NOTE)
    @RequestMapping(path = "/saveCurrentProject", method = POST)
    public ResponseEntity saveCurrentProject(@RequestBody UpdateProject updateProject) {
        String operationResult = restTransmissionService.updateProject(updateProject);
        return HttpResultHandler.getInstance().handleSimpleProjectResult(operationResult);
    }

    @ApiOperation(value = "Delete a selected project with a project identifier", nickname = "project/deleteProject", notes = TransmissionConstants.DELETE_PROJECT_NOTE)
    @RequestMapping(path = "/deleteSelectedProject", method = DELETE)
    public ResponseEntity deleteSelectedProject(@RequestParam(value = TransmissionConstants.PROJECT_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                                 String projectIdentifier) {
        String operationResult = restTransmissionService.deleteProject(projectIdentifier);
        return HttpResultHandler.getInstance().handleSimpleProjectResult(operationResult);
    }


    @ApiOperation(value = "Load a selected project with a project identifier", nickname = "project/loadSelectedProject", notes = TransmissionConstants.LOAD_PROJECT_NOTE)
    @RequestMapping(path = "/loadSelectedProject", method = GET)
    public ResponseEntity<LoadSelectedProject> loadSelectedProject(@RequestParam(value = TransmissionConstants.PROJECT_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE) String projectIdentifier) {
        LoadSelectedProject result = restTransmissionService.loadSelectedProject(projectIdentifier);
        return new ResponseEntity<LoadSelectedProject>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get all current projects", nickname = "project/getAllProjects", notes = TransmissionConstants.GET_ALL_PROJECT_NOTE)
    @RequestMapping(path = "/getAllProjects", method = GET)
    public ResponseEntity<List<GetAllProjects>> getAllProjects() {

        List<GetAllProjects> result = restTransmissionService.getAllProjects();

        return new ResponseEntity<List<GetAllProjects>>(result, HttpStatus.OK);

    }

    @ApiOperation(value = "Get all available positioning algorithms", nickname = "project/getAllAlgorithmTypes", notes = TransmissionConstants.GET_ALL_ALGORITHMS_NOTE)
    @RequestMapping(path = "/getAllAlgorithmTypes", method = GET)
    public ResponseEntity<List<GetAllAlgorithmTypes>> getAllAlgorithmTypes() {

        List<GetAllAlgorithmTypes> result = restTransmissionService.getAllAlgorithmTypes();

        return new ResponseEntity<List<GetAllAlgorithmTypes>>(result, HttpStatus.OK);

    }

    @ApiOperation(value = "Get all available parameters", nickname = "project/getAllParameters", notes = TransmissionConstants.GET_ALL_PARAMETERS_NOTE)
    @RequestMapping(path = "/getAllParameters", method = GET)
    public ResponseEntity<List<GetAlgorithmParameters>> getAllParameters() {

        List<GetAlgorithmParameters> result = restTransmissionService.getAllParameters();

        return new ResponseEntity<List<GetAlgorithmParameters>>(result, HttpStatus.OK);

    }

    @ApiOperation(value = "Get all parameters applicable to given algorithm", nickname = "project/getParametersForAlgorithm",
            notes = TransmissionConstants.GET_PARAMETERS_FOR_ALGORITHM_NOTE)
    @RequestMapping(path = "/getParametersForAlgorithm", method = GET)
    public ResponseEntity<List<GetAlgorithmParameters>> getParametersForAlgorithm(
            @RequestParam(value = TransmissionConstants.ALGORITHM_TYPE_PARAM,
                    defaultValue = TransmissionConstants.EMPTY_STRING_VALUE) String algorithmType) {

        List<GetAlgorithmParameters> result = restTransmissionService.getParametersForAlgorithm(algorithmType);

        return new ResponseEntity<List<GetAlgorithmParameters>>(result, HttpStatus.OK);

    }
}
