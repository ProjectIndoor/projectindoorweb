package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.*;
import de.hftstuttgart.projectindoorweb.web.internal.util.TransmissionConstants;
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


    @ApiOperation(value = "Processes Radio Map files", nickname = "position/processRadioMapFiles", notes = TransmissionConstants.GENERATE_RADIOMAPS_NOTE)
    @RequestMapping(path = "/processRadioMapFiles", method = POST)
    public boolean processRadioMapFiles(@RequestParam(value = TransmissionConstants.BUILDING_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE, required = true) String buildingIdentifier,
                                        @RequestParam(value = TransmissionConstants.RADIOMAP_FILES_PARAM, required = true)
                                        MultipartFile[] radioMapFiles,
                                        @RequestParam(value = TransmissionConstants.TRANSFORMED_POINTS_FILE_PARAM, required = false)
                                        MultipartFile transformedPointsFile) {
        return restTransmissionService.processEvaalFiles(buildingIdentifier, false, radioMapFiles, transformedPointsFile);
    }

    @ApiOperation(value = "Processes Eval files.", nickname = "position/processEvalFiles", notes = TransmissionConstants.GENERATE_RADIOMAPS_NOTE)
    @RequestMapping(path = "/processEvalFiles", method = POST)
    public boolean processEvalFiles(@RequestParam(value = TransmissionConstants.BUILDING_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE) String buildingIdentifier,
                                    @RequestParam (value = TransmissionConstants.EVAL_FILE_PARAM)
                                    MultipartFile[] evalFiles) {
        return restTransmissionService.processEvaalFiles(buildingIdentifier, true, evalFiles, null);
    }

    @ApiOperation(value = "Generate position results", nickname = "position/generateBatchPositionResults", notes = TransmissionConstants.GENERATE_POSITIONRESULTS_NOTE)
    @RequestMapping(path = "/generateBatchPositionResults", method = POST)
    public ResponseEntity<List<BatchPositionResult>> generateBatchPositionResults(
            @RequestBody GenerateBatchPositionResults generateBatchPositionResults) {

        List<BatchPositionResult> result = restTransmissionService
                .generatePositionResults(generateBatchPositionResults);
        return new ResponseEntity<List<BatchPositionResult>>(result, HttpStatus.OK);

    }

    @ApiOperation(value = "Calculate position with wifi reading line", nickname = "position/generateSinglePositionResult", notes = TransmissionConstants.CALCULATE_POSITION_NOTE)
    @RequestMapping(path = "/generateSinglePositionResult", method = POST)
    public ResponseEntity<SinglePositionResult> generateSinglePositionResult(
            @RequestBody GenerateSinglePositionResult generateSinglePositionResult) {

        SinglePositionResult result = restTransmissionService.getPositionForWifiReading(generateSinglePositionResult);
        return new ResponseEntity<SinglePositionResult>(result, HttpStatus.OK);

    }

    @ApiOperation(value = "Get position results for project identifier", nickname = "position/getPositionResultsForProjectIdentifier", notes = TransmissionConstants.GET_POSITIONRESULTS_NOTE)
    @RequestMapping(path = "/getPositionResultsForProjectIdentifier", method = GET)
    public ResponseEntity<List<BatchPositionResult>> getPositionResultsForProjectIdentifier(
            @RequestParam(value = TransmissionConstants.PROJECT_IDENTIFIER_PARAM,
                    defaultValue = TransmissionConstants.EMPTY_STRING_VALUE) String projectIdentifier) {
        List<BatchPositionResult> result = restTransmissionService.getPositionResultsForProjectIdentifier(projectIdentifier);
        return new ResponseEntity<List<BatchPositionResult>>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Get evaluation entries for building identifier", nickname = "position/getEvaluationFilesForBuilding",
            notes = TransmissionConstants.GET_EVALUATIONENTRIES_NOTE)
    @RequestMapping(path = "/getEvalFilesForBuildingId", method = GET)
    public ResponseEntity<List<GetEvaluationFilesForBuilding>> getEvaluationEntriesForBuildingId(
            @RequestParam(value = TransmissionConstants.BUILDING_IDENTIFIER_PARAM,
                    defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                    String buildingIdentifier) {

        List<GetEvaluationFilesForBuilding> result = restTransmissionService.getEvaluationFilesForBuilding(buildingIdentifier);
        return new ResponseEntity<List<GetEvaluationFilesForBuilding>>(result, HttpStatus.OK);

    }

    @ApiOperation(value = "Get Radio Maps for building identifier.", nickname = "position/getRadioMapsForBuildingId",
            notes = TransmissionConstants.GET_EVALUATIONENTRIES_NOTE)
    @RequestMapping(path = "/getRadioMapsForBuildingId", method = GET)
    public ResponseEntity<List<GetRadioMapFilesForBuilding>> getRadioMapsForBuildingId(
            @RequestParam(value = TransmissionConstants.BUILDING_IDENTIFIER_PARAM,
                    defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                    String buildingIdentifier) {

        List<GetRadioMapFilesForBuilding> result = restTransmissionService.getRadioMapFilesForBuilding(buildingIdentifier);
        return new ResponseEntity<List<GetRadioMapFilesForBuilding>>(result, HttpStatus.OK);

    }

}
