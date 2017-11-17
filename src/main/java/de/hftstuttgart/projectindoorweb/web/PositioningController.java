package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.web.internal.CalculatedPosition;
import de.hftstuttgart.projectindoorweb.web.internal.TransmissionConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/position")
public class PositioningController {

    private RestTransmissionService restTransmissionService= RestTransmissionServiceComponent.getRestTransmissionServiceInstance();


    @RequestMapping(path="/generateRadioMap", method=POST)
    public boolean generateRadioMap(@RequestBody List<File> radioMapFiles) {
        return restTransmissionService.generateRadioMap(radioMapFiles);
    }

    @RequestMapping(path="/generatePositionResults", method=POST)
    public boolean generatePositionResults(@RequestBody List<File> evaluationFiles) {
        return restTransmissionService.generatePositionResults(evaluationFiles);
    }

    @RequestMapping(path="/calculatePositionWithWifiReading", method=GET)
    public ResponseEntity<CalculatedPosition> getPositionForWifiReading(@RequestParam(value=TransmissionConstants.WIFI_READING_PARAM, defaultValue= TransmissionConstants.EMPTY_STRING_VALUE) String wifiReading) {
        CalculatedPosition result = restTransmissionService.getPositionForWifiReading(wifiReading);
        return new ResponseEntity<CalculatedPosition>(result, HttpStatus.OK);
    }

    @RequestMapping(path="/getPositionResultsForIdentifier", method=GET)
    public ResponseEntity<List<CalculatedPosition>> getPositionResultsForIdentifier(@RequestParam(value=TransmissionConstants.POSITION_IDENTIFIER_PARAM, defaultValue=TransmissionConstants.EMPTY_STRING_VALUE) String positionIdentifier) {
        List<CalculatedPosition> result = restTransmissionService.getPositionResultsForIdentifier(positionIdentifier);
        return new ResponseEntity<List<CalculatedPosition>>(result, HttpStatus.OK);
    }
}
