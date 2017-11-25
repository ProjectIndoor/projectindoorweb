package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.web.internal.BuildingElement;
import de.hftstuttgart.projectindoorweb.web.internal.CalculatedPosition;
import de.hftstuttgart.projectindoorweb.web.internal.PositionAnchor;
import de.hftstuttgart.projectindoorweb.web.internal.TransmissionConstants;
import de.hftstuttgart.projectindoorweb.web.internal.util.EvaluationEntry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "Position", description = "Operations for positions", tags = "Position")
@RequestMapping("/position")
public class PositioningController {

    private RestTransmissionService restTransmissionService
            = RestTransmissionServiceComponent.getRestTransmissionServiceInstance();


    @ApiOperation(value = "Generate radio maps", nickname = "position/generateRadioMaps",  notes = TransmissionConstants.GENERATE_RADIOMAPS_NOTE)
    @RequestMapping(path = "/generateRadioMaps", method = POST)
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

    @ApiOperation(value = "Generate position results", nickname = "position/generatePositionResults", notes= TransmissionConstants.GENERATE_POSITIONRESULTS_NOTE)
    @RequestMapping(path = "/generatePositionResults", method = POST)
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

    @ApiOperation(value = "Get all buildings", nickname = "position/getAllBuildings", notes= TransmissionConstants.GET_ALL_BUILDINGS_NOTE)
    @RequestMapping(path = "/getAllBuildings", method = GET)
    public ResponseEntity<List<BuildingElement>> getAllBuildings() {
        List<BuildingElement> result = restTransmissionService.getAllBuildings();
        return new ResponseEntity<List<BuildingElement>>(result, HttpStatus.OK);
    }


    @ApiOperation(value = "Calculate position with wifi reading line", nickname = "position/calculatePositionWithWifiReading", notes = TransmissionConstants.CALCULATE_POSITION_NOTE)
    @RequestMapping(path = "/calculatePositionWithWifiReading", method = GET)
    public ResponseEntity<CalculatedPosition> calculatePositionWithWifiReading(@RequestParam(value = TransmissionConstants.WIFI_READING_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE) String wifiReading,
                                                                        @RequestParam(value = TransmissionConstants.WITH_PIXEL_POSITION_PARAM,
                                                                                defaultValue = TransmissionConstants.FALSE_STRING_VALUE)
                                                                                boolean withPixelPosition) {
        CalculatedPosition result = restTransmissionService.getPositionForWifiReading(wifiReading);
        return new ResponseEntity<CalculatedPosition>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get position results for project identifier", nickname = "position/getPositionResultsForProjectIdentifier", notes = TransmissionConstants.GET_POSITIONRESULTS_NOTE)
    @RequestMapping(path = "/getPositionResultsForProjectIdentifier", method = GET)
    public ResponseEntity<List<CalculatedPosition>> getPositionResultsForProjectIdentifier(
            @RequestParam(value = TransmissionConstants.PROJECT_IDENTIFIER_PARAM,
                    defaultValue = TransmissionConstants.EMPTY_STRING_VALUE) String projectIdentifier) {
        List<CalculatedPosition> result = restTransmissionService.getPositionResultsForProjectIdentifier(projectIdentifier);
        return new ResponseEntity<List<CalculatedPosition>>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get evaluation entries for building identifier", nickname = "position/getEvaluationEntriesForBuildingId", notes = TransmissionConstants.GET_EVALUATIONENTRIES_NOTE)
    @RequestMapping(path = "/getEvaluationEntriesForBuildingId", method = GET)
    public ResponseEntity<List<EvaluationEntry>> getEvaluationEntriesForBuildingId(@RequestParam(value = TransmissionConstants.BUILDING_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)String buildingIdentifier) {
        List<EvaluationEntry> result = restTransmissionService.getEvaluationEntriesForBuildingId(buildingIdentifier);
        return new ResponseEntity<List<EvaluationEntry>>(result, HttpStatus.OK);

    }

    @ApiOperation(value = "Add a new building", nickname = "position/addNewBuilding", notes= TransmissionConstants.ADD_NEW_BUILDING_NOTE)
    @RequestMapping(path = "/addNewBuilding", method = POST)
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
