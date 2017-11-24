package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.web.internal.CalculatedPosition;
import de.hftstuttgart.projectindoorweb.web.internal.TransmissionConstants;
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
                                     @RequestBody MultipartFile[] radioMapFiles) {

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
            @RequestBody MultipartFile evaluationFile) {

        List<CalculatedPosition> result = restTransmissionService.generatePositionResults(projectIdentifier, buildingIdentifier, evaluationFile);
        return new ResponseEntity<List<CalculatedPosition>>(result, HttpStatus.OK);

    }

    //TODO add method getAllBuildings returns object adjusted to entity
    @RequestMapping(path = "/calculatePositionWithWifiReading", method = GET)
    public ResponseEntity<CalculatedPosition> getPositionForWifiReading(@RequestParam(value = TransmissionConstants.WIFI_READING_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE) String wifiReading) {
        CalculatedPosition result = restTransmissionService.getPositionForWifiReading(wifiReading);
        return new ResponseEntity<CalculatedPosition>(result, HttpStatus.OK);
    }

    @RequestMapping(path = "/getPositionResultsForIdentifier", method = GET)
//TODO add new parameters projectId and buildingId:
    public ResponseEntity<List<CalculatedPosition>> getPositionResultsForIdentifier(@RequestParam(value = TransmissionConstants.POSITION_IDENTIFIER_PARAM, defaultValue = TransmissionConstants.EMPTY_STRING_VALUE) String positionIdentifier) {
        List<CalculatedPosition> result = restTransmissionService.getPositionResultsForIdentifier(positionIdentifier);
        return new ResponseEntity<List<CalculatedPosition>>(result, HttpStatus.OK);
    }

}
