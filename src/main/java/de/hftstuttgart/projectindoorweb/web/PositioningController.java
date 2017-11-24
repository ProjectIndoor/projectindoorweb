package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.web.internal.BuildingElement;
import de.hftstuttgart.projectindoorweb.web.internal.CalculatedPosition;
import de.hftstuttgart.projectindoorweb.web.internal.PositionAnchor;
import de.hftstuttgart.projectindoorweb.web.internal.TransmissionConstants;
import de.hftstuttgart.projectindoorweb.web.internal.util.EvaluationEntry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/position")
public class PositioningController {

    private RestTransmissionService restTransmissionService
            = RestTransmissionServiceComponent.getRestTransmissionServiceInstance();


    @RequestMapping(path = "/generateRadioMaps", method = GET)
    public boolean generateRadioMaps(@RequestParam(value = TransmissionConstants.PROJECT_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                             String projectIdentifier,
                                     @RequestParam(value = TransmissionConstants.BUILDING_IDENTIFIER_PARAM,
                                             defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                             String buildingIdentifier,
                                     @RequestBody MultipartFile[] radioMapFiles,
                                     @RequestParam(value = TransmissionConstants.WITH_PIXEL_POSITION_PARAM,
                                             defaultValue = TransmissionConstants.FALSE_STRING_VALUE)
                                             boolean withPixelPosition) {
        return restTransmissionService.generateRadioMap(projectIdentifier, buildingIdentifier, radioMapFiles);
    }

    @RequestMapping(path = "/generatePositionResults", method = GET)
    public ResponseEntity<List<CalculatedPosition>> generatePositionResults(
            @RequestParam(value = TransmissionConstants.PROJECT_IDENTIFIER_PARAM,
                    defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                    String projectIdentifier,
            @RequestParam(value = TransmissionConstants.BUILDING_IDENTIFIER_PARAM,
                    defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                    String buildingIdentifier,
            @RequestBody MultipartFile evaluationFile,
            @RequestParam(value = TransmissionConstants.WITH_PIXEL_POSITION_PARAM,
                    defaultValue = TransmissionConstants.FALSE_STRING_VALUE)
                    boolean withPixelPosition) {

        List<CalculatedPosition> result = restTransmissionService.generatePositionResults(projectIdentifier, buildingIdentifier, evaluationFile);
        return new ResponseEntity<List<CalculatedPosition>>(result, HttpStatus.OK);

    }

    @RequestMapping(path = "/getAllBuildings", method = GET)
    public ResponseEntity<List<BuildingElement>> getAllBuildings() {
        List<BuildingElement> result = restTransmissionService.getAllBuildings();
        return new ResponseEntity<List<BuildingElement>>(result, HttpStatus.OK);
    }

    @RequestMapping(path = "/calculatePositionWithWifiReading", method = GET)
    public ResponseEntity<CalculatedPosition> getPositionForWifiReading(@RequestParam(value = TransmissionConstants.WIFI_READING_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE) String wifiReading,
                                                                        @RequestParam(value = TransmissionConstants.WITH_PIXEL_POSITION_PARAM,
                                                                                defaultValue = TransmissionConstants.FALSE_STRING_VALUE)
                                                                                boolean withPixelPosition) {
        CalculatedPosition result = restTransmissionService.getPositionForWifiReading(wifiReading);
        return new ResponseEntity<CalculatedPosition>(result, HttpStatus.OK);
    }

    @RequestMapping(path = "/getPositionResultsForProjectIdentifier", method = GET)
    public ResponseEntity<List<CalculatedPosition>> getPositionResultsForProjectIdentifier(
            @RequestParam(value = TransmissionConstants.PROJECT_IDENTIFIER_PARAM,
                    defaultValue = TransmissionConstants.EMPTY_STRING_VALUE) String projectIdentifier) {
        List<CalculatedPosition> result = restTransmissionService.getPositionResultsForProjectIdentifier(projectIdentifier);
        return new ResponseEntity<List<CalculatedPosition>>(result, HttpStatus.OK);
    }

    @RequestMapping(path = "/getEvaluationEntriesForBuildingId", method = GET)
    public ResponseEntity<List<EvaluationEntry>> getEvaluationEntriesForBuildingId(@RequestParam(value = TransmissionConstants.BUILDING_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)String buildingIdentifier) {
        List<EvaluationEntry> result = restTransmissionService.getEvaluationEntriesForBuildingId(buildingIdentifier);
        return new ResponseEntity<List<EvaluationEntry>>(result, HttpStatus.OK);

    }

    @RequestMapping(path = "/addNewBuilding", method = GET)
    public long addNewBuilding(@RequestParam(value = TransmissionConstants.BUILDING_NAME_PARAM,
                                  defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)String buildingName,
                               @RequestParam(value = TransmissionConstants.NUMBER_OF_FLOORS_PARAM,
                                       defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)String numberOfFloors,
                               @RequestBody PositionAnchor southEastAnchor,
                               @RequestBody PositionAnchor southWestAnchor,
                               @RequestBody PositionAnchor northEastAnchor,
                               @RequestBody PositionAnchor northWestAnchor) {

        return restTransmissionService.addNewBuilding(buildingName,numberOfFloors,southEastAnchor,southWestAnchor,northEastAnchor,northWestAnchor);

    }

}
