package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.web.internal.CalculatedPosition;
import de.hftstuttgart.projectindoorweb.web.internal.ProjectElement;
import de.hftstuttgart.projectindoorweb.web.internal.ProjectParameter;
import de.hftstuttgart.projectindoorweb.web.internal.TransmissionConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private RestTransmissionService restTransmissionService=RestTransmissionServiceComponent.getRestTransmissionServiceInstance();

    //TODO add selected algorithm
    @RequestMapping(path="/saveNewProject", method=POST)
    public boolean saveNewProject(@RequestBody Set<ProjectParameter> projectParameterSet, @RequestParam(value=TransmissionConstants.PROJECT_NAME_PARAM, defaultValue=TransmissionConstants.EMPTY_STRING_VALUE) String projectName) {
        return restTransmissionService.saveNewProject(projectParameterSet,projectName);
    }

    //TODO add selected algorithm
    @RequestMapping(path="/saveCurrentProject", method=POST)
    public boolean saveCurrentProject(@RequestBody Set<ProjectParameter> projectParameterSet, @RequestParam(value=TransmissionConstants.PROJECT_IDENTIFIER_PARAM, defaultValue=TransmissionConstants.EMPTY_STRING_VALUE) String projectIdentifier) {
        return restTransmissionService.saveCurrentProject(projectParameterSet,projectIdentifier);
    }

    @RequestMapping(path="/deleteSelectedProject", method=DELETE)
    public boolean deleteSelectedProject(@RequestParam(value=TransmissionConstants.PROJECT_IDENTIFIER_PARAM, defaultValue= TransmissionConstants.EMPTY_STRING_VALUE) String projectIdentifier) {
        return restTransmissionService.deleteSelectedProject(projectIdentifier);
    }

    @RequestMapping(path="/loadSelectedProject", method=GET)
    public ResponseEntity<ProjectElement> loadSelectedProject(@RequestParam(value=TransmissionConstants.PROJECT_IDENTIFIER_PARAM, defaultValue=TransmissionConstants.EMPTY_STRING_VALUE) String projectIdentifier) {
        ProjectElement result = restTransmissionService.loadSelectedProject(projectIdentifier);
        return new ResponseEntity<ProjectElement>(result, HttpStatus.OK);
    }
}
