package de.hftstuttgart.projectindoorweb.web.controllertests;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hftstuttgart.projectindoorweb.Application;
import de.hftstuttgart.projectindoorweb.persistence.repositories.BuildingRepository;
import de.hftstuttgart.projectindoorweb.web.configuration.TestWebConfiguration;
import de.hftstuttgart.projectindoorweb.web.helpers.TestHelper;
import de.hftstuttgart.projectindoorweb.web.internal.ResponseWrapper;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.AddNewBuilding;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.BuildingPositionAnchor;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.GetSingleBuilding;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.UpdateBuilding;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = TestWebConfiguration.class)
@WebAppConfiguration
public class BuildingControllerTest {

    private static final double ROTATION_ANGLE_ACCEPTABLE_ERROR = 0.0000000000000001;
    private static final double LAT_LONG_ACCEPTABLE_ERROR = 0.00000000000001;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private AddNewBuilding addNewBuilding;
    private GetSingleBuilding getSingleBuilding;


    private AddNewBuilding addNewBuildingEmptyRefPoint;
    private GetSingleBuilding getSingleBuildingEmptyRefPoint;

    private UpdateBuilding updateBuilding;
    private GetSingleBuilding getSingleBuildingUpdated;

    private MockMultipartFile floorMapFileBmp;

    private ObjectMapper objectMapper;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {

        objectMapper = new ObjectMapper();

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        this.buildingRepository.deleteAll();

        addNewBuilding = TestHelper.createGenericBuildingRequestObject();

        addNewBuildingEmptyRefPoint = TestHelper.createGenericBuildingRequestObject();
        addNewBuildingEmptyRefPoint.setBuildingCenterPoint(null);
        addNewBuildingEmptyRefPoint.setRotationAngle(0.0);
        addNewBuildingEmptyRefPoint.setMetersPerPixel(0.0);


        getSingleBuilding = TestHelper.createGenericBuildingSingleResponseObject();

        BuildingPositionAnchor emptyCenterPoint = new BuildingPositionAnchor(-1, -1);
        getSingleBuildingEmptyRefPoint = TestHelper.createGenericBuildingSingleResponseObject();
        getSingleBuildingEmptyRefPoint.setBuildingId(2);
        getSingleBuildingEmptyRefPoint.setBuildingCenterPoint(emptyCenterPoint);
        getSingleBuildingEmptyRefPoint.setRotationAngle(0.0);
        getSingleBuildingEmptyRefPoint.setMetersPerPixel(0.0);
        getSingleBuildingEmptyRefPoint.getBuildingFloors().get(0).setFloorId(2);

        updateBuilding = TestHelper.createGenericUpdateBuildingRequest();
        getSingleBuildingUpdated = TestHelper.createGenericBuildingSingleResponseObject();
        getSingleBuildingUpdated.setBuildingId(updateBuilding.getBuildingId());
        getSingleBuildingUpdated.setBuildingName(updateBuilding.getBuildingName());

        floorMapFileBmp = new MockMultipartFile("floorMapFile", "CAR.bmp", "image/bmp",
                Files.readAllBytes(Paths.get("./src/test/resources/CAR.bmp")));

    }

    @Test
    public void testAddBuildingWithNullCornerAnchors() throws IOException {

        try {
            long buildingId = TestHelper.addNewBuildingAndRetrieveId(this.mockMvc, this.contentType);
            assertTrue("Failed to add new building.", buildingId > 0);

            mockMvc.perform(get("/building/getBuildingByBuildingId?buildingIdentifier="
                    + buildingId))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath("$.buildingId",
                            is((int) buildingId)))
                    .andExpect(jsonPath("$.buildingName",
                            is(getSingleBuilding.getBuildingName())))
                    .andExpect(jsonPath("$.numberOfFloors",
                            is(getSingleBuilding.getNumberOfFloors())))
                    .andExpect(jsonPath("$.imagePixelWidth",
                            is(getSingleBuilding.getImagePixelWidth())))
                    .andExpect(jsonPath("$.imagePixelHeight",
                            is(getSingleBuilding.getImagePixelHeight())))
                    .andExpect(jsonPath("$.buildingCenterPoint.latitude",
                            is(getSingleBuilding.getBuildingCenterPoint().getLatitude())))
                    .andExpect(jsonPath("$.rotationAngle",
                            closeTo(new BigDecimal(getSingleBuilding.getRotationAngle(), MathContext.DECIMAL64),
                                    new BigDecimal(ROTATION_ANGLE_ACCEPTABLE_ERROR, MathContext.DECIMAL64))))
                    .andExpect(jsonPath("$.metersPerPixel",
                            is(getSingleBuilding.getMetersPerPixel())))
                    .andExpect(jsonPath("$.evaalFiles",
                            is(getSingleBuilding.getEvaalFiles())))
                    .andExpect(jsonPath("$.buildingFloors[0].floorLevel",
                            is(getSingleBuilding.getBuildingFloors().get(0).getFloorLevel())))
                    .andExpect(jsonPath("$.buildingFloors[0].floorName",
                            is(getSingleBuilding.getBuildingFloors().get(0).getFloorName())))
                    .andExpect(jsonPath("$.buildingFloors[0].floorMapUrl",
                            is(getSingleBuilding.getBuildingFloors().get(0).getFloorMapUrl())))
                    .andExpect(jsonPath("$.northWest.latitude",
                            closeTo(getSingleBuilding.getNorthWest().getLatitude(), LAT_LONG_ACCEPTABLE_ERROR)))
                    .andExpect(jsonPath("$.northWest.longitude",
                            closeTo(getSingleBuilding.getNorthWest().getLongitude(), LAT_LONG_ACCEPTABLE_ERROR)))
                    .andExpect(jsonPath("$.northEast.latitude",
                            closeTo(getSingleBuilding.getNorthEast().getLatitude(), LAT_LONG_ACCEPTABLE_ERROR)))
                    .andExpect(jsonPath("$.northEast.longitude",
                            closeTo(new BigDecimal(getSingleBuilding.getNorthEast().getLongitude(), MathContext.DECIMAL64),
                                    new BigDecimal(LAT_LONG_ACCEPTABLE_ERROR, MathContext.DECIMAL64))))
                    .andExpect(jsonPath("$.southEast.latitude",
                            closeTo(getSingleBuilding.getSouthEast().getLatitude(), LAT_LONG_ACCEPTABLE_ERROR)))
                    .andExpect(jsonPath("$.southEast.longitude",
                            closeTo(new BigDecimal(getSingleBuilding.getSouthEast().getLongitude(), MathContext.DECIMAL64),
                                    new BigDecimal(LAT_LONG_ACCEPTABLE_ERROR, MathContext.DECIMAL64))))
                    .andExpect(jsonPath("$.southWest.latitude",
                            closeTo(getSingleBuilding.getSouthWest().getLatitude(), LAT_LONG_ACCEPTABLE_ERROR)))
                    .andExpect(jsonPath("$.southWest.longitude",
                            closeTo(getSingleBuilding.getSouthWest().getLongitude(), LAT_LONG_ACCEPTABLE_ERROR)));

        } catch (Exception e) {
            e.printStackTrace();
            fail("An unexpected IOException occurred.");
        }
    }

    @Test
    public void testAddBuildingWithNullReferencePoint() {

        try {

            ResultActions addBuildingActions = mockMvc.perform(post("/building/addNewBuilding")
                    .content(TestHelper.jsonify(this.addNewBuildingEmptyRefPoint))
                    .contentType(contentType));

            addBuildingActions.andExpect(status().isOk());
            String result = addBuildingActions.andReturn().getResponse().getContentAsString();
            ResponseWrapper responseWrapper = this.objectMapper.readValue(result, ResponseWrapper.class);
            long buildingId = responseWrapper.getId();
            assertTrue("Failed to add new building.", buildingId > 0);

            mockMvc.perform(get("/building/getBuildingByBuildingId?buildingIdentifier="
                    + buildingId))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath("$.buildingId",
                            is((int) buildingId)))
                    .andExpect(jsonPath("$.buildingName",
                            is(getSingleBuildingEmptyRefPoint.getBuildingName())))
                    .andExpect(jsonPath("$.numberOfFloors",
                            is(getSingleBuildingEmptyRefPoint.getNumberOfFloors())))
                    .andExpect(jsonPath("$.imagePixelWidth",
                            is(getSingleBuildingEmptyRefPoint.getImagePixelWidth())))
                    .andExpect(jsonPath("$.imagePixelHeight",
                            is(getSingleBuildingEmptyRefPoint.getImagePixelHeight())))
                    .andExpect(jsonPath("$.buildingCenterPoint.latitude",
                            is(getSingleBuildingEmptyRefPoint.getBuildingCenterPoint().getLatitude())))
                    .andExpect(jsonPath("$.rotationAngle",
                            is(getSingleBuildingEmptyRefPoint.getRotationAngle())))
                    .andExpect(jsonPath("$.metersPerPixel",
                            is(getSingleBuildingEmptyRefPoint.getMetersPerPixel())))
                    .andExpect(jsonPath("$.evaalFiles",
                            is(getSingleBuildingEmptyRefPoint.getEvaalFiles())))
                    .andExpect(jsonPath("$.buildingFloors[0].floorLevel",
                            is(getSingleBuildingEmptyRefPoint.getBuildingFloors().get(0).getFloorLevel())))
                    .andExpect(jsonPath("$.buildingFloors[0].floorName",
                            is(getSingleBuildingEmptyRefPoint.getBuildingFloors().get(0).getFloorName())))
                    .andExpect(jsonPath("$.buildingFloors[0].floorMapUrl",
                            is(getSingleBuildingEmptyRefPoint.getBuildingFloors().get(0).getFloorMapUrl())))
                    .andExpect(jsonPath("$.northWest.latitude",
                            closeTo(getSingleBuildingEmptyRefPoint.getNorthWest().getLatitude(), LAT_LONG_ACCEPTABLE_ERROR)))
                    .andExpect(jsonPath("$.northWest.longitude",
                            closeTo(getSingleBuildingEmptyRefPoint.getNorthWest().getLongitude(), LAT_LONG_ACCEPTABLE_ERROR)))
                    .andExpect(jsonPath("$.northEast.latitude",
                            closeTo(getSingleBuildingEmptyRefPoint.getNorthEast().getLatitude(), LAT_LONG_ACCEPTABLE_ERROR)))
                    .andExpect(jsonPath("$.northEast.longitude",
                            closeTo(new BigDecimal(getSingleBuildingEmptyRefPoint.getNorthEast().getLongitude(), MathContext.DECIMAL64),
                                    new BigDecimal(LAT_LONG_ACCEPTABLE_ERROR, MathContext.DECIMAL64))))
                    .andExpect(jsonPath("$.southEast.latitude",
                            closeTo(getSingleBuildingEmptyRefPoint.getSouthEast().getLatitude(), LAT_LONG_ACCEPTABLE_ERROR)))
                    .andExpect(jsonPath("$.southEast.longitude",
                            closeTo(new BigDecimal(getSingleBuildingEmptyRefPoint.getSouthEast().getLongitude(), MathContext.DECIMAL64),
                                    new BigDecimal(LAT_LONG_ACCEPTABLE_ERROR, MathContext.DECIMAL64))))
                    .andExpect(jsonPath("$.southWest.latitude",
                            closeTo(getSingleBuildingEmptyRefPoint.getSouthWest().getLatitude(), LAT_LONG_ACCEPTABLE_ERROR)))
                    .andExpect(jsonPath("$.southWest.longitude",
                            closeTo(getSingleBuildingEmptyRefPoint.getSouthWest().getLongitude(), LAT_LONG_ACCEPTABLE_ERROR)));

        } catch (Exception e) {
            e.printStackTrace();
            fail("An unexpected Exception of type " + e.getClass().getSimpleName() + " has occurred.");
        }

    }

    @Test
    public void testUpdateBuilding() {

        try {

            long buildingId = TestHelper.addNewBuildingAndRetrieveId(this.mockMvc, this.contentType);
            assertTrue("Failed to add new building.", buildingId > 0);

            this.updateBuilding.setBuildingId(buildingId);
            mockMvc.perform(post("/building/updateBuilding")
                    .content(TestHelper.jsonify(this.updateBuilding))
                    .contentType(contentType))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/building/getBuildingByBuildingId?buildingIdentifier="
                    + buildingId))
                    .andExpect(jsonPath("$.buildingName",
                            is(getSingleBuildingUpdated.getBuildingName())));


        } catch (Exception e) {
            e.printStackTrace();
            fail("An unexpected Exception of type " + e.getClass().getSimpleName() + " has occurred.");
        }


    }

    @Test
    public void testAddFloorMap() {

        try {
            long buildingId = TestHelper.addNewBuildingAndRetrieveId(this.mockMvc, this.contentType);
            assertTrue("Failed to add new building.", buildingId > 0);

            ResultActions getBuildingActions = mockMvc.perform(get("/building/getBuildingByBuildingId?" +
                    "buildingIdentifier=" + buildingId));
            getBuildingActions.andExpect(status().isOk());
            String result = getBuildingActions.andReturn().getResponse().getContentAsString();
            GetSingleBuilding getSingleBuilding = this.objectMapper.readValue(result, GetSingleBuilding.class);

            String floorName = "SomeFloor";
            mockMvc.perform(MockMvcRequestBuilders
                    .fileUpload("/building/addFloorToBuilding")
                    .file(floorMapFileBmp)
                    .param("buildingIdentifier", String.valueOf(getSingleBuilding.getBuildingId()))
                    .param("floorIdentifier", String.valueOf(getSingleBuilding.getBuildingFloors().get(0).getFloorId()))
                    .param("floorName", floorName))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/building/getBuildingByBuildingId?" +
                    "buildingIdentifier=" + buildingId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.buildingFloors[0].floorMapUrl", is("building/getFloorMap?floorIdentifier=" + buildingId)));

        } catch (Exception e) {
            e.printStackTrace();
            fail("An unexpected Exception of type " + e.getClass().getSimpleName() + " has occurred.");
        }

    }

    @Test
    public void testDeleteBuilding() {

        try {

            long[] buildingIds = new long[10];
            for (int i = 0; i < buildingIds.length; i++) {
                buildingIds[i] = TestHelper.addNewBuildingAndRetrieveId(this.mockMvc, this.contentType);
            }

            for (int i = 0; i < buildingIds.length; i++) {
                mockMvc.perform(delete("/building/deleteSelectedBuilding?buildingIdentifier="
                        + buildingIds[i]))
                        .andExpect(status().isOk());
            }

            for (int i = 0; i < buildingIds.length; i++) {
                mockMvc.perform(get("/building/getSingleBuildingByBuildingId?buildingIdentifier="
                        + buildingIds[i]))
                        .andExpect(status().is4xxClientError());
            }


        } catch (Exception e) {
            e.printStackTrace();
            fail("An unexpected Exception of type " + e.getClass().getSimpleName() + " has occurred.");
        }


    }

}
