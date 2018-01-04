package de.hftstuttgart.projectindoorweb.web.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hftstuttgart.projectindoorweb.inputHandler.internal.util.ConfigContainer;
import de.hftstuttgart.projectindoorweb.positionCalculator.internal.CorrelationMode;
import de.hftstuttgart.projectindoorweb.web.internal.ResponseWrapper;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.*;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.GenerateBatchPositionResults;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.GenerateSinglePositionResult;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.SinglePositionResult;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.SaveNewProjectParameters;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestHelper {

    public static AddNewBuilding createGenericBuildingRequestObject() {

        String buildingName = "CAR";
        int numberOfFloors = 1;
        int imagePixelWidth = 1282;
        int imagePixelHeight = 818;
        BuildingPositionAnchor northWestAnchor = new BuildingPositionAnchor(40.31334657807515, -3.484103894895305);
        BuildingPositionAnchor northEastAnchor = new BuildingPositionAnchor(40.313438190511064, -3.4833257251177385);
        BuildingPositionAnchor southEastAnchor = new BuildingPositionAnchor(40.313059581924854, -3.4832490651046952);
        BuildingPositionAnchor southWestAnchor = new BuildingPositionAnchor(40.31296796948894, -3.484027234882262);
        BuildingPositionAnchor centerPoint = new BuildingPositionAnchor(40.31320308, -3.48367648);
        double rotationAngle = 0.15318405778903832;
        double metersPerPixel = 0.05207600;

        return new AddNewBuilding(buildingName, numberOfFloors, imagePixelWidth, imagePixelHeight,
                northWestAnchor, northEastAnchor, southEastAnchor, southWestAnchor, centerPoint, rotationAngle, metersPerPixel);

    }

    public static GetSingleBuilding createGenericBuildingSingleResponseObject() {

        long buildingId = 1;
        String buildingName = "CAR";
        int numberOfFloors = 1;
        int imagePixelWidth = 1282;
        int imagePixelHeight = 818;
        BuildingPositionAnchor northWestAnchor = new BuildingPositionAnchor(40.31334657807515, -3.484103894895305);
        BuildingPositionAnchor northEastAnchor = new BuildingPositionAnchor(40.313438190511064, -3.4833257251177385);
        BuildingPositionAnchor southEastAnchor = new BuildingPositionAnchor(40.313059581924854, -3.4832490651046952);
        BuildingPositionAnchor southWestAnchor = new BuildingPositionAnchor(40.31296796948894, -3.484027234882262);
        BuildingPositionAnchor centerPoint = new BuildingPositionAnchor(40.31320308, -3.48367648);
        double rotationAngle = 0.15318405778903832;
        double metersPerPixel = 0.05207600;

        List<GetSingleBuildingEvaalFile> evaalFiles = new ArrayList<>();
        List<GetFloor> floors = new ArrayList<>();
        floors.add(new GetFloor(1, 0, null, null));

        return new GetSingleBuilding(buildingId, buildingName, numberOfFloors, imagePixelWidth,
                imagePixelHeight, northWestAnchor, northEastAnchor, southEastAnchor, southWestAnchor,
                centerPoint, rotationAngle, metersPerPixel, evaalFiles, floors);

    }

    public static UpdateBuilding createGenericUpdateBuildingRequest() {

        long buildingId = 3;
        String buildingName = "Some Updated Building";
        int imagePixelWidth = 1282;
        int imagePixelHeight = 818;
        BuildingPositionAnchor northWestAnchor = new BuildingPositionAnchor(40.31334657807515, -3.484103894895305);
        BuildingPositionAnchor northEastAnchor = new BuildingPositionAnchor(40.313438190511064, -3.4833257251177385);
        BuildingPositionAnchor southEastAnchor = new BuildingPositionAnchor(40.313059581924854, -3.4832490651046952);
        BuildingPositionAnchor southWestAnchor = new BuildingPositionAnchor(40.31296796948894, -3.484027234882262);
        BuildingPositionAnchor centerPoint = new BuildingPositionAnchor(40.31320308, -3.48367648);
        double rotationAngle = 0.15318405778903832;
        double metersPerPixel = 0.05207600;

        List<GetSingleBuildingEvaalFile> evaalFiles = new ArrayList<>();
        List<GetFloor> floors = new ArrayList<>();
        floors.add(new GetFloor(1, 0, null, null));

        return new UpdateBuilding(buildingId, buildingName, imagePixelWidth, imagePixelHeight, northWestAnchor, northEastAnchor,
                southEastAnchor, southWestAnchor, centerPoint, rotationAngle, metersPerPixel);

    }

    public static long addNewBuildingAndRetrieveId(MockMvc mockMvc, MediaType contentType) throws Exception {

        ResultActions addBuildingActions = mockMvc.perform(post("/building/addNewBuilding")
                .content(TestHelper.jsonify(createGenericBuildingRequestObject()))
                .contentType(contentType));

        addBuildingActions.andExpect(status().isOk());
        String result = addBuildingActions.andReturn().getResponse().getContentAsString();
        ResponseWrapper responseWrapper = new ObjectMapper().readValue(result, ResponseWrapper.class);
        long buildingId = responseWrapper.getId();
        return buildingId;

    }

    public static GenerateBatchPositionResults createDefaultBatchPositionRequestObject(CorrelationMode correlationMode) {

        Set<SaveNewProjectParameters> saveNewProjectParameters = getDefaultProjectParameterSet(correlationMode);

        String algorithmType = "WIFI";
        boolean withPixelPosition = true;

        return new GenerateBatchPositionResults(-1L, -1L, -1L,
                null, algorithmType, saveNewProjectParameters, withPixelPosition);

    }

    public static GenerateSinglePositionResult createDefaultSinglePositionRequestObject(CorrelationMode correlationMode) {

        Set<SaveNewProjectParameters> saveNewProjectParameters = getDefaultProjectParameterSet(correlationMode);
        String algorithmType = "WIFI";
        boolean withPixelPosition = false;



        return new GenerateSinglePositionResult(-1L, -1L, -1L,
                null, algorithmType, saveNewProjectParameters, withPixelPosition, null);


    }

    public static Set<SaveNewProjectParameters> getDefaultProjectParameterSet(CorrelationMode correlationMode) {

        Set<SaveNewProjectParameters> result = new HashSet<>();

        result.add(new SaveNewProjectParameters("lowestConsideredRssiValue",
                String.valueOf(ConfigContainer.LOWEST_RSSI_IN_SCALAR_MODE)));
        result.add(new SaveNewProjectParameters("mergeRadioMaps",
                String.valueOf(ConfigContainer.MERGE_RADIOMAP_ELEMENTS)));
        result.add(new SaveNewProjectParameters("radioPropagationExponent",
                String.valueOf(ConfigContainer.RADIO_PROPAGATION_EXPONENT)));
        result.add(new SaveNewProjectParameters("weightedModeNumReferences",
                String.valueOf(ConfigContainer.NUM_REFERENCES_IN_WEIGHTED_MODE)));
        result.add(new SaveNewProjectParameters("correlationMode",
                String.valueOf(correlationMode).toLowerCase()));
        result.add(new SaveNewProjectParameters("weightResult3",
                String.valueOf(ConfigContainer.WEIGHT_RESULT_3)));
        result.add(new SaveNewProjectParameters("floorHeight",
                String.valueOf(ConfigContainer.FLOOR_HEIGHT)));
        result.add(new SaveNewProjectParameters("useFixedWeights",
                String.valueOf(ConfigContainer.USE_FIXED_WEIGHTS)));
        result.add(new SaveNewProjectParameters("posiReferenceSimilarityTimeDelta",
                String.valueOf(ConfigContainer.POSI_REFERENCE_MAX_TIME_DELTA_MILLIS)));
        result.add(new SaveNewProjectParameters("weightResult1",
                String.valueOf(ConfigContainer.WEIGHT_RESULT_1)));
        result.add(new SaveNewProjectParameters("smoothenWifiPositions",
                String.valueOf(ConfigContainer.SMOOTHEN_WIFI_POSITIONS)));
        result.add(new SaveNewProjectParameters("wifiPositionSmootheningFactor",
                String.valueOf(ConfigContainer.WIFI_POSITION_SMOOTHENER)));
        result.add(new SaveNewProjectParameters("useShiftedPosiReferences",
                String.valueOf(ConfigContainer.USE_SHIFTED_POSI_REFERENCES)));
        result.add(new SaveNewProjectParameters("weightResult2",
                String.valueOf(ConfigContainer.WEIGHT_RESULT_2)));
        result.add(new SaveNewProjectParameters("positionSimilarityThreshold",
                String.valueOf(ConfigContainer.SIMILAR_POSITION_THRESHOLD_METERS)));

        return result;

    }

    public static String jsonify(Object o) throws IOException {
        return new ObjectMapper().writeValueAsString(o);
    }


}
