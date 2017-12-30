package de.hftstuttgart.projectindoorweb.web.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hftstuttgart.projectindoorweb.web.internal.ResponseWrapper;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.*;
import org.hibernate.sql.Update;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestHelper {

    public static AddNewBuilding createGenericBuildingRequestObject(){

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

    public static GetSingleBuilding createGenericBuildingSingleResponseObject(){

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

    public static UpdateBuilding createGenericUpdateBuildingRequest(){

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

    public static String jsonify(Object o) throws IOException {
        return new ObjectMapper().writeValueAsString(o);
    }


}
