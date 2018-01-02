package de.hftstuttgart.projectindoorweb.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hftstuttgart.projectindoorweb.web.internal.HttpResultHandler;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.*;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RestController
@Api(value = "Position", description = "Operations for positions", tags = "Position")
@RequestMapping("/position")
public class PositioningController {

    @Autowired
    private RestTransmissionService restTransmissionService;


    @ApiOperation(value = "Processes Radio Map files", nickname = "position/processRadioMapFiles", notes = TransmissionConstants.GENERATE_RADIOMAPS_NOTE)
    @RequestMapping(path = "/processRadioMapFiles", method = POST, produces = "text/plain")
    public ResponseEntity processRadioMapFiles(@RequestParam(value = TransmissionConstants.BUILDING_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE, required = true) String buildingIdentifier,
                                        @RequestParam(value = TransmissionConstants.RADIOMAP_FILES_PARAM, required = true)
                                                MultipartFile[] radioMapFiles,
                                        @RequestParam(value = TransmissionConstants.TRANSFORMED_POINTS_FILE_PARAM, required = false)
                                                MultipartFile[] transformedPointsFiles) {
        String operationResult = restTransmissionService.processEvaalFiles(buildingIdentifier, false, radioMapFiles, transformedPointsFiles);
        return HttpResultHandler.getInstance().handleSimplePositioningResult(operationResult);

    }

    @ApiOperation(value = "Processes Eval files.", nickname = "position/processEvalFiles", notes = TransmissionConstants.GENERATE_RADIOMAPS_NOTE)
    @RequestMapping(path = "/processEvalFiles", method = POST, produces = "text/plain")
    public ResponseEntity processEvalFiles(@RequestParam(value = TransmissionConstants.BUILDING_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE) String buildingIdentifier,
                                    @RequestParam(value = TransmissionConstants.EVAL_FILE_PARAM)
                                            MultipartFile[] evalFiles) {
        String operationResult = restTransmissionService.processEvaalFiles(buildingIdentifier, true, evalFiles, null);
        return HttpResultHandler.getInstance().handleSimplePositioningResult(operationResult);
    }

    @ApiOperation(value = "Deletes a selected Evaal file", nickname = "project/deleteEvaalFile", notes = TransmissionConstants.DELETE_EVAAL_FILE_NOTE)
    @RequestMapping(path = "/deleteSelectedEvaalFile", method = DELETE, produces = "text/plain")
    public ResponseEntity deleteSelectedEvaalFile(@RequestParam(value = TransmissionConstants.EVAAL_FILE_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                                   String evaalFileIdentifier) {

        String operationResult = restTransmissionService.deleteEvaalFile(evaalFileIdentifier);
        return HttpResultHandler.getInstance().handleSimplePositioningResult(operationResult);
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

    @ApiOperation(value = "Get all current evaluation entries in the database", nickname = "position/getAllEvaalEntries",
            notes = TransmissionConstants.GET_ALL_EVAAL_FILES_NOTE)
    @RequestMapping(path = "/getAllEvaalEntries", method = GET)
    public ResponseEntity<List<GetAllEvaalEntries>> getAllEvaalEntries() {

        List<GetAllEvaalEntries> result = restTransmissionService.getAllEvaalEntries();
        return new ResponseEntity<List<GetAllEvaalEntries>>(result, HttpStatus.OK);

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
