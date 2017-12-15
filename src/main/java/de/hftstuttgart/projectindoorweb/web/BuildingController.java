package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.web.internal.requests.building.GetAllBuildings;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.AddNewBuilding;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.GetSingleBuilding;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.UpdateBuilding;
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

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
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
    public long addNewBuilding(@RequestBody AddNewBuilding buildingJsonWrapper) {

        return restTransmissionService.addNewBuilding(buildingJsonWrapper);

    }

    @ApiOperation(value = "Get all buildings", nickname = "building/getAllBuildings", notes = TransmissionConstants.GET_ALL_BUILDINGS_NOTE)
    @RequestMapping(path = "/getAllBuildings", method = GET)
    public ResponseEntity<List<GetAllBuildings>> getAllBuildings() {
        List<GetAllBuildings> result = restTransmissionService.getAllBuildings();
        return new ResponseEntity<List<GetAllBuildings>>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves detailed information about a single building", nickname = "building/getBuildingByBuildingId",
            notes = TransmissionConstants.GET_SINGLE_BUILDING_NOTE)
    @RequestMapping(path = "/getBuildingByBuildingId", method = GET)
    public ResponseEntity<GetSingleBuilding> getBuildingByBuildingId(
            @RequestParam(value = TransmissionConstants.BUILDING_IDENTIFIER_PARAM,
                    defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                    String buildingIdentifier) {
        GetSingleBuilding result = restTransmissionService.getSingleBuilding(buildingIdentifier);
        return new ResponseEntity<GetSingleBuilding>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Updates a given building by the given JSON body.", nickname = "building/updateBuilding",
            notes = TransmissionConstants.UPDATE_BUILDING_NOTE)
    @RequestMapping(path = "/updateBuilding", method = POST)
    public boolean updateBuilding(@RequestBody UpdateBuilding updateBuilding) {

        return restTransmissionService.updateBuilding(updateBuilding);

    }

    @ApiOperation(value = "Add a new floor to a given building", nickname = "building/addFloorToBuilding",
            notes = TransmissionConstants.ADD_FLOOR_TO_BUILDING_NOTE)
    @RequestMapping(path = "/addFloorToBuilding", method = POST)
    public boolean addFloorToBuilding(@RequestParam(value = TransmissionConstants.BUILDING_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                               String buildingIdentifier,
                                   @RequestParam(value = TransmissionConstants.FLOOR_IDENTIFIER_PARAM,
                                           defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                           String floorIdentifier,
                                   @RequestParam(value = TransmissionConstants.FLOOR_NAME_PARAM,
                                           defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                               String floorName,
                                   @RequestParam (value = TransmissionConstants.FLOOR_MAP_FILE_PARAM)
                                               MultipartFile floorMapFile) {
        return restTransmissionService.addFloorToBuilding(buildingIdentifier, floorIdentifier, floorName, floorMapFile);
    }

    @ApiOperation(value = "Delete a selected floor with a given floor identifier within a building",
            nickname = "project/deleteFloor", notes = TransmissionConstants.DELETE_FLOOR_NOTE)
    @RequestMapping(path = "/deleteSelectedFloor", method = DELETE)
    public boolean deleteSelectedFloor(@RequestParam(value = TransmissionConstants.FLOOR_IDENTIFIER_PARAM,
            defaultValue = TransmissionConstants.EMPTY_STRING_VALUE)
                                                 String floorIdentifier) {
        return restTransmissionService.deleteFloor(floorIdentifier);
    }

}
