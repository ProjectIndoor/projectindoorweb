package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.Application;
import de.hftstuttgart.projectindoorweb.persistence.repositories.BuildingRepository;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class BuildingControllerTest {

    private static final double ROTATION_ANGLE_ACCEPTABLE_ERROR = 0.0000000000000001;
    private static final double LAT_LONG_ACCEPTABLE_ERROR = 0.00000000000001;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private AddNewBuilding addNewBuildingEmptyCornerAnchors;
    private GetSingleBuilding getSingleBuildingEmptyCornerAnchors;

    private AddNewBuilding addNewBuildingEmptyRefPoint;
    private GetSingleBuilding getSingleBuildingEmptyRefPoint;

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

        String buildingName = "CAR";
        int numberOfFloors = 1;
        int imagePixelWidth = 1282;
        int imagePixelHeight = 818;
        BuildingPositionAnchor southEastAnchor = null;
        BuildingPositionAnchor southWestAnchor = null;
        BuildingPositionAnchor northEastAnchor = null;
        BuildingPositionAnchor northWestAnchor = null;
        BuildingPositionAnchor centerPoint = new BuildingPositionAnchor(40.31320308, -3.48367648);
        double rotationAngle = 0.15318405778903832;
        double metersPerPixel = 0.05207600;

        addNewBuildingEmptyCornerAnchors = new AddNewBuilding(buildingName, numberOfFloors, imagePixelWidth, imagePixelHeight,
                northWestAnchor, northEastAnchor, southEastAnchor, southWestAnchor, centerPoint, rotationAngle, metersPerPixel);

        long buildingId = 1;
        northWestAnchor = new BuildingPositionAnchor(40.31334657807515, -3.484103894895305);
        northEastAnchor = new BuildingPositionAnchor(40.313438190511064, -3.4833257251177385);
        southEastAnchor = new BuildingPositionAnchor(40.313059581924854, -3.4832490651046952);
        southWestAnchor = new BuildingPositionAnchor(40.31296796948894, -3.484027234882262);
        List<GetSingleBuildingEvaalFile> evaalFiles = new ArrayList<>();
        List<GetFloor> floors = new ArrayList<>();
        floors.add(new GetFloor(1, 0, null, null));
        getSingleBuildingEmptyCornerAnchors = new GetSingleBuilding(buildingId, buildingName, numberOfFloors, imagePixelWidth,
                imagePixelHeight, northWestAnchor, northEastAnchor, southEastAnchor, southWestAnchor, centerPoint, rotationAngle,
                metersPerPixel, evaalFiles, floors);



    }

    @Test
    public void testAddBuildingWithNullCornerAnchors() throws IOException {

        try {
            mockMvc.perform(post("/building/addNewBuilding")
                    .content(this.json(this.addNewBuildingEmptyCornerAnchors))
                    .contentType(contentType))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/building/getBuildingByBuildingId?buildingIdentifier="
                    + getSingleBuildingEmptyCornerAnchors.getBuildingId()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(contentType))
                    .andExpect(jsonPath("$.buildingId",
                            is((int) getSingleBuildingEmptyCornerAnchors.getBuildingId())))
                    .andExpect(jsonPath("$.buildingName",
                            is(getSingleBuildingEmptyCornerAnchors.getBuildingName())))
                    .andExpect(jsonPath("$.numberOfFloors",
                            is(getSingleBuildingEmptyCornerAnchors.getNumberOfFloors())))
                    .andExpect(jsonPath("$.imagePixelWidth",
                            is(getSingleBuildingEmptyCornerAnchors.getImagePixelWidth())))
                    .andExpect(jsonPath("$.imagePixelHeight",
                            is(getSingleBuildingEmptyCornerAnchors.getImagePixelHeight())))
                    .andExpect(jsonPath("$.buildingCenterPoint.latitude",
                            is(getSingleBuildingEmptyCornerAnchors.getBuildingCenterPoint().getLatitude())))
                    .andExpect(jsonPath("$.rotationAngle",
                            closeTo(new BigDecimal(getSingleBuildingEmptyCornerAnchors.getRotationAngle(), MathContext.DECIMAL64),
                                    new BigDecimal(ROTATION_ANGLE_ACCEPTABLE_ERROR, MathContext.DECIMAL64))))
                    .andExpect(jsonPath("$.metersPerPixel",
                            is(getSingleBuildingEmptyCornerAnchors.getMetersPerPixel())))
                    .andExpect(jsonPath("$.evaalFiles",
                            is(getSingleBuildingEmptyCornerAnchors.getEvaalFiles())))
                    .andExpect(jsonPath("$.buildingFloors[0].floorId",
                            is((int) getSingleBuildingEmptyCornerAnchors.getBuildingFloors().get(0).getFloorId())))
                    .andExpect(jsonPath("$.buildingFloors[0].floorLevel",
                            is((int) getSingleBuildingEmptyCornerAnchors.getBuildingFloors().get(0).getFloorLevel())))
                    .andExpect(jsonPath("$.buildingFloors[0].floorName",
                            is(getSingleBuildingEmptyCornerAnchors.getBuildingFloors().get(0).getFloorName())))
                    .andExpect(jsonPath("$.buildingFloors[0].floorMapUrl",
                            is(getSingleBuildingEmptyCornerAnchors.getBuildingFloors().get(0).getFloorMapUrl())))
                    .andExpect(jsonPath("$.northWest.latitude",
                            closeTo(getSingleBuildingEmptyCornerAnchors.getNorthWest().getLatitude(), LAT_LONG_ACCEPTABLE_ERROR)))
                    .andExpect(jsonPath("$.northWest.longitude",
                            closeTo(getSingleBuildingEmptyCornerAnchors.getNorthWest().getLongitude(), LAT_LONG_ACCEPTABLE_ERROR)))
                    .andExpect(jsonPath("$.northEast.latitude",
                            closeTo(getSingleBuildingEmptyCornerAnchors.getNorthEast().getLatitude(), LAT_LONG_ACCEPTABLE_ERROR)))
                    .andExpect(jsonPath("$.northEast.longitude",
                            closeTo(new BigDecimal(getSingleBuildingEmptyCornerAnchors.getNorthEast().getLongitude(), MathContext.DECIMAL64),
                                    new BigDecimal(LAT_LONG_ACCEPTABLE_ERROR, MathContext.DECIMAL64))))
                    .andExpect(jsonPath("$.southEast.latitude",
                            closeTo(getSingleBuildingEmptyCornerAnchors.getSouthEast().getLatitude(), LAT_LONG_ACCEPTABLE_ERROR)))
                    .andExpect(jsonPath("$.southEast.longitude",
                            closeTo(new BigDecimal(getSingleBuildingEmptyCornerAnchors.getSouthEast().getLongitude(), MathContext.DECIMAL64),
                                    new BigDecimal(LAT_LONG_ACCEPTABLE_ERROR, MathContext.DECIMAL64))))
                    .andExpect(jsonPath("$.southWest.latitude",
                            closeTo(getSingleBuildingEmptyCornerAnchors.getSouthWest().getLatitude(), LAT_LONG_ACCEPTABLE_ERROR)))
                    .andExpect(jsonPath("$.southWest.longitude",
                            closeTo(getSingleBuildingEmptyCornerAnchors.getSouthWest().getLongitude(), LAT_LONG_ACCEPTABLE_ERROR)));

        } catch (Exception e) {
            e.printStackTrace();
            fail("An unexpected IOException occurred.");
        }
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
