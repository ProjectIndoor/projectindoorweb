package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.web.internal.*;
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


    @ApiOperation(value = "Processes Radio Map files.", nickname = "position/processEvaalFiles", notes = TransmissionConstants.GENERATE_RADIOMAPS_NOTE)
    @RequestMapping(path = "/processRadioMapFiles", method = POST)
    public boolean processRadioMapFiles(@RequestParam(value = TransmissionConstants.BUILDING_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                                String buildingIdentifier,
                                        @RequestParam(value = TransmissionConstants.WITH_PIXEL_POSITION_PARAM,
                                                defaultValue = TransmissionConstants.FALSE_STRING_VALUE)
                                                boolean withPixelPosition,
                                        @RequestBody MultipartFile[] radioMapFiles) {
        return restTransmissionService.processEvaalFiles(buildingIdentifier, false, radioMapFiles);
    }

    @ApiOperation(value = "Processes Eval files.", nickname = "position/processEvalFiles", notes = TransmissionConstants.GENERATE_RADIOMAPS_NOTE)
    @RequestMapping(path = "/processEvalFiles", method = POST)
    public boolean processEvalFiles(@RequestParam(value = TransmissionConstants.BUILDING_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                            String buildingIdentifier,
                                    @RequestParam(value = TransmissionConstants.WITH_PIXEL_POSITION_PARAM,
                                            defaultValue = TransmissionConstants.FALSE_STRING_VALUE)
                                            boolean withPixelPosition,
                                    @RequestBody MultipartFile[] evalFiles) {
        return restTransmissionService.processEvaalFiles(buildingIdentifier, true, evalFiles);
    }


    @ApiOperation(value = "Generate position results", nickname = "position/generateBatchPositionResults", notes = TransmissionConstants.GENERATE_POSITIONRESULTS_NOTE)
    @RequestMapping(path = "/generateBatchPositionResults", method = POST)
    public ResponseEntity<List<CalculatedPosition>> generateBatchPositionResults(
            @RequestParam(value = TransmissionConstants.WITH_PIXEL_POSITION_PARAM,
                    defaultValue = TransmissionConstants.FALSE_STRING_VALUE)
                    boolean withPixelPosition,
            @RequestBody GeneratePositionJsonWrapper generatePositionJsonWrapper) {

        List<CalculatedPosition> result = restTransmissionService.generatePositionResults(withPixelPosition, generatePositionJsonWrapper);
        return new ResponseEntity<List<CalculatedPosition>>(result, HttpStatus.OK);

    }

    @ApiOperation(value = "Calculate position with wifi reading line", nickname = "position/generateSinglePositionResult", notes = TransmissionConstants.CALCULATE_POSITION_NOTE)
    @RequestMapping(path = "/generateSinglePositionResult", method = POST)
    public ResponseEntity<CalculatedPosition> generateSinglePositionResult(
            @RequestParam(value = TransmissionConstants.WIFI_READING_PARAM,
                    defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                    String wifiReading,
            @RequestParam(value = TransmissionConstants.WITH_PIXEL_POSITION_PARAM,
                    defaultValue = TransmissionConstants.FALSE_STRING_VALUE)
                    boolean withPixelPosition,
            @RequestBody GeneratePositionJsonWrapper generatePositionJsonWrapper) {

        CalculatedPosition result = restTransmissionService.getPositionForWifiReading(wifiReading, withPixelPosition, generatePositionJsonWrapper);
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

    @ApiOperation(value = "Get evaluation entries for building identifier", nickname = "position/getEvaluationFilesForBuilding",
            notes = TransmissionConstants.GET_EVALUATIONENTRIES_NOTE)
    @RequestMapping(path = "/getEvalFilesForBuilding", method = GET)
    public ResponseEntity<List<EvaluationEntry>> getEvaluationEntriesForBuildingId(
            @RequestParam(value = TransmissionConstants.BUILDING_IDENTIFIER_PARAM,
                    defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                    String buildingIdentifier) {

        List<EvaluationEntry> result = restTransmissionService.getEvaluationFilesForBuilding(buildingIdentifier);
        return new ResponseEntity<List<EvaluationEntry>>(result, HttpStatus.OK);

    }

    @ApiOperation(value = "Get Radio Maps for building identifier.", nickname = "position/getRadioMapsForBuilding",
            notes = TransmissionConstants.GET_EVALUATIONENTRIES_NOTE)
    @RequestMapping(path = "/getRadioMapsForBuilding", method = GET)
    public ResponseEntity<List<RadioMapEntry>> getRadioMapsForBuildingId(
            @RequestParam(value = TransmissionConstants.BUILDING_IDENTIFIER_PARAM,
                    defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                    String buildingIdentifier) {

        List<RadioMapEntry> result = restTransmissionService.getRadioMapFilesForBuilding(buildingIdentifier);
        return new ResponseEntity<List<RadioMapEntry>>(result, HttpStatus.OK);

    }


}
