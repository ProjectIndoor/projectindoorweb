package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.web.internal.CalculatedPosition;
import de.hftstuttgart.projectindoorweb.web.internal.TransmissionConstants;
import de.hftstuttgart.projectindoorweb.web.internal.util.EvaluationEntry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@Api(value = "Position", description = "Operations for positions", tags = "Position")
@RequestMapping("/position")
public class PositioningController {

    private RestTransmissionService restTransmissionService
            = RestTransmissionServiceComponent.getRestTransmissionServiceInstance();


    @ApiOperation(value = "Generate radio maps", nickname = "position/generateRadioMaps", notes = TransmissionConstants.GENERATE_RADIOMAPS_NOTE)
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

    @ApiOperation(value = "Generate position results", nickname = "position/generatePositionResults", notes = TransmissionConstants.GENERATE_POSITIONRESULTS_NOTE)
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
        if (result.isEmpty()) {
            return new ResponseEntity<List<CalculatedPosition>>(result, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<List<CalculatedPosition>>(result, HttpStatus.OK);
        }


    }

    @ApiOperation(value = "Calculate position with wifi reading line", nickname = "position/calculatePositionWithWifiReading", notes = TransmissionConstants.CALCULATE_POSITION_NOTE)
    @RequestMapping(path = "/calculatePositionWithWifiReading", method = GET)
    public ResponseEntity<CalculatedPosition> calculatePositionWithWifiReading(
            @RequestParam(value = TransmissionConstants.WIFI_READING_PARAM,
                    defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                    String wifiReading,
            @RequestParam(value = TransmissionConstants.WITH_PIXEL_POSITION_PARAM,
                    defaultValue = TransmissionConstants.FALSE_STRING_VALUE)
                    boolean withPixelPosition,
            @RequestParam(value = TransmissionConstants.PROJECT_IDENTIFIER_PARAM,
                    defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                    String projectIdentifier) {
        CalculatedPosition result = restTransmissionService.getPositionForWifiReading(projectIdentifier, wifiReading);
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
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE) String buildingIdentifier) {
        List<EvaluationEntry> result = restTransmissionService.getEvaluationEntriesForBuildingId(buildingIdentifier);
        return new ResponseEntity<List<EvaluationEntry>>(result, HttpStatus.OK);

    }



}
