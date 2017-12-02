package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.web.internal.requests.building.GetAllBuildings;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.AddNewBuilding;
import de.hftstuttgart.projectindoorweb.web.internal.util.TransmissionConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@Api(value = "Building", description = "Provides operations for interaction with Buildings.", tags = "Building")
@RequestMapping("/building")
public class BuildingController {

    private RestTransmissionService restTransmissionService
            = RestTransmissionServiceComponent.getRestTransmissionServiceInstance();

    @ApiOperation(value = "Add a new building", nickname = "building/addNewBuilding",
            notes = TransmissionConstants.ADD_NEW_BUILDING_NOTE)
    @RequestMapping(path = "/addNewBuilding", method = POST)
    public boolean addNewBuilding(@RequestBody AddNewBuilding buildingJsonWrapper) {

        return restTransmissionService.addNewBuilding(buildingJsonWrapper);

    }

    @ApiOperation(value = "Get all buildings", nickname = "building/getAllBuildings", notes = TransmissionConstants.GET_ALL_BUILDINGS_NOTE)
    @RequestMapping(path = "/getAllBuildings", method = GET)
    public ResponseEntity<List<GetAllBuildings>> getAllBuildings() {
        List<GetAllBuildings> result = restTransmissionService.getAllBuildings();
        return new ResponseEntity<List<GetAllBuildings>>(result, HttpStatus.OK);
    }
}
