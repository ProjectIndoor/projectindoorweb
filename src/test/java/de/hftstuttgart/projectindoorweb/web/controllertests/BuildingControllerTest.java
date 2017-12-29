package de.hftstuttgart.projectindoorweb.web.controllertests;

import de.hftstuttgart.projectindoorweb.Application;
import de.hftstuttgart.projectindoorweb.persistence.repositories.BuildingRepository;
import de.hftstuttgart.projectindoorweb.web.helpers.TestHelper;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BuildingControllerTest {

    private static final double ROTATION_ANGLE_ACCEPTABLE_ERROR = 0.0000000000000001;
    private static final double LAT_LONG_ACCEPTABLE_ERROR = 0.00000000000001;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private AddNewBuilding addNewBuilding;
    private GetSingleBuilding getSingleBuilding;

    private AddNewBuilding addNewBuildingEmptyCornerAnchors;

    private AddNewBuilding addNewBuildingEmptyRefPoint;
    private GetSingleBuilding getSingleBuildingEmptyRefPoint;

    private UpdateBuilding updateBuilding;
    private GetSingleBuilding getSingleBuildingUpdated;

    private MockMultipartFile floorMapFileBmp;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setMappingJackson2HttpMessageConverter(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("JSON message converter must not be null.", this.mappingJackson2HttpMessageConverter);

    }

    @Before
    public void setUp() throws Exception {

        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.buildingRepository.deleteAll();

        addNewBuilding = TestHelper.createGenericBuildingRequestObject();

        addNewBuildingEmptyCornerAnchors = TestHelper.createGenericBuildingRequestObject();
        addNewBuildingEmptyCornerAnchors.setNorthWest(null);
        addNewBuildingEmptyCornerAnchors.setNorthEast(null);
        addNewBuildingEmptyCornerAnchors.setSouthEast(null);
        addNewBuildingEmptyCornerAnchors.setSouthWest(null);

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

        floorMapFileBmp = new MockMultipartFile("CAR.bmp",
                new FileInputStream(new File("./src/test/resources/CAR.bmp")));

    }

    @Test
    public void a_testAddBuildingWithNullCornerAnchors() throws IOException {

        try {
            mockMvc.perform(post("/building/addNewBuilding")
                    .content(this.json(this.addNewBuildingEmptyCornerAnchors))
                    .contentType(contentType))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/building/getBuildingByBuildingId?buildingIdentifier="
                    + getSingleBuilding.getBuildingId()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath("$.buildingId",
                            is((int) getSingleBuilding.getBuildingId())))
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
                    .andExpect(jsonPath("$.buildingFloors[0].floorId",
                            is((int) getSingleBuilding.getBuildingFloors().get(0).getFloorId())))
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
    public void b_testAddBuildingWithNullReferencePoint() {

        try {
            mockMvc.perform(post("/building/addNewBuilding")
                    .content(this.json(this.addNewBuildingEmptyRefPoint))
                    .contentType(contentType))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/building/getBuildingByBuildingId?buildingIdentifier="
                    + getSingleBuildingEmptyRefPoint.getBuildingId()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath("$.buildingId",
                            is((int) getSingleBuildingEmptyRefPoint.getBuildingId())))
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
                    .andExpect(jsonPath("$.buildingFloors[0].floorId",
                            is((int) getSingleBuildingEmptyRefPoint.getBuildingFloors().get(0).getFloorId())))
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
    public void c_testUpdateBuilding() {

        try {
            mockMvc.perform(post("/building/addNewBuilding")
                    .content(this.json(this.addNewBuilding))
                    .contentType(contentType))
                    .andExpect(status().isOk());

            mockMvc.perform(post("/building/updateBuilding")
                    .content(this.json(this.updateBuilding))
                    .contentType(contentType))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/building/getBuildingByBuildingId?buildingIdentifier="
                    + getSingleBuildingUpdated.getBuildingId()))
                    .andExpect(jsonPath("$.buildingName",
                            is(getSingleBuildingUpdated.getBuildingName())));


        } catch (Exception e) {
            e.printStackTrace();
            fail("An unexpected Exception of type " + e.getClass().getSimpleName() + " has occurred.");
        }


    }

    @Test
    public void d_testAddFloorMap(){



    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }


}
