package de.hftstuttgart.projectindoorweb.web.controllertests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hftstuttgart.projectindoorweb.Application;
import de.hftstuttgart.projectindoorweb.persistence.repositories.BuildingRepository;
import de.hftstuttgart.projectindoorweb.web.configuration.TestWebConfiguration;
import de.hftstuttgart.projectindoorweb.web.helpers.TestHelper;
import de.hftstuttgart.projectindoorweb.web.internal.ResponseWrapper;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.BatchPositionResult;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.GenerateBatchPositionResults;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.GetEvaluationFilesForBuilding;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.GetRadioMapFilesForBuilding;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.AddNewProject;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.SaveNewProjectParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = TestWebConfiguration.class)
@WebAppConfiguration
public class EverythingControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private MockMultipartFile radioMapFileR01A5;
    private MockMultipartFile radioMapFileR01S4Mini;
    private MockMultipartFile radioMapFileR01S4;
    private MockMultipartFile radioMapFileR02A5;
    private MockMultipartFile radioMapFileR02S4Mini;
    private MockMultipartFile radioMapFileR02S4;
    private MockMultipartFile radioMapFileR03S4Mini;
    private MockMultipartFile radioMapFileR03S4;
    private MockMultipartFile radioMapFileR04A5;
    private MockMultipartFile radioMapFileR04S4Mini;
    private MockMultipartFile radioMapFileR04S4;

    private MockMultipartFile emptyRadioMapFileHfT;
    private MockMultipartFile transformedPointsFile;

    private MockMultipartFile evaluationFile;

    private long[] emptyRadioMapIdArray = new long[2];

    private final static String ALGORITHM_TYPE = "WIFI";
    private final static String DUMMY = "Dummy";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BuildingRepository buildingRepository;

    @Before
    public void setUp() throws Exception {

        this.objectMapper = new ObjectMapper();

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        this.buildingRepository.deleteAll();

        radioMapFileR01A5 = new MockMultipartFile("radioMapFiles", "logfile_CAR_R01-2017_A5.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/radiomapfiles/logfile_CAR_R01-2017_A5.txt")));
        radioMapFileR01S4Mini = new MockMultipartFile("radioMapFiles", "logfile_CAR_R01-2017_S4Mini.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/radiomapfiles/logfile_CAR_R01-2017_S4MINI.txt")));
        radioMapFileR01S4 = new MockMultipartFile("radioMapFiles", "logfile_CAR_R01-2017_S4.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/radiomapfiles/logfile_CAR_R01-2017_S4.txt")));
        radioMapFileR02A5 = new MockMultipartFile("radioMapFiles", "logfile_CAR_R02-2017_A5.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/radiomapfiles/logfile_CAR_R02-2017_A5.txt")));
        radioMapFileR02S4Mini = new MockMultipartFile("radioMapFiles", "logfile_CAR_R02-2017_S4Mini.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/radiomapfiles/logfile_CAR_R02-2017_S4MINI.txt")));
        radioMapFileR02S4 = new MockMultipartFile("radioMapFiles", "logfile_CAR_R02-2017_S4.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/radiomapfiles/logfile_CAR_R02-2017_S4.txt")));
        radioMapFileR03S4Mini = new MockMultipartFile("radioMapFiles", "logfile_CAR_R03-2017_S4Mini.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/radiomapfiles/logfile_CAR_R03-2017_S4MINI.txt")));
        radioMapFileR03S4 = new MockMultipartFile("radioMapFiles", "logfile_CAR_R03-2017_S4.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/radiomapfiles/logfile_CAR_R03-2017_S4.txt")));
        radioMapFileR04A5 = new MockMultipartFile("radioMapFiles", "logfile_CAR_R04-2017_A5.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/radiomapfiles/logfile_CAR_R04-2017_A5.txt")));
        radioMapFileR04S4Mini = new MockMultipartFile("radioMapFiles", "logfile_CAR_R04-2017_S4Mini.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/radiomapfiles/logfile_CAR_R04-2017_S4MINI.txt")));
        radioMapFileR04S4 = new MockMultipartFile("radioMapFiles", "logfile_CAR_R04-2017_S4.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/radiomapfiles/logfile_CAR_R04-2017_S4.txt")));

        emptyRadioMapFileHfT = new MockMultipartFile("radioMapFiles", "logfile_2017_11_25_10_23_07_Survey1.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/radiomapfiles/logfile_2017_11_25_10_23_07_Survey1.txt")));
        transformedPointsFile = new MockMultipartFile("transformedPointsFiles", "Survey1.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/transformedpointsfiles/Survey1.txt")));

        evaluationFile = new MockMultipartFile("evalFiles", "logfile_CAR_R01-2017_A5.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/evaluationfiles/logfile_CAR_R01-2017_A5.txt")));

    }


    @Test
    public void testProcessSingleRadioMapFile() {

        try {
            long buildingId = TestHelper.addNewBuildingAndRetrieveId(this.mockMvc, this.contentType);
            assertTrue("Failed to add new building.", buildingId > 0);

            mockMvc.perform(MockMvcRequestBuilders.fileUpload("/position/processRadioMapFiles")
                    .file(radioMapFileR01A5)
                    .param("buildingIdentifier", String.valueOf(buildingId)))
                    .andExpect(status().isOk());


        } catch (Exception e) {
            e.printStackTrace();
            fail("An unexpected Exception of type " + e.getClass().getSimpleName() + " has occuured.");
        }


    }

    @Test
    public void testProcessMultipleRadioMapFiles() {

        try {
            long buildingId = TestHelper.addNewBuildingAndRetrieveId(this.mockMvc, this.contentType);
            assertTrue("Failed to add new building.", buildingId > 0);

            mockMvc.perform(MockMvcRequestBuilders.fileUpload("/position/processRadioMapFiles")
                    .file(radioMapFileR01A5)
                    .file(radioMapFileR01S4Mini)
                    .file(radioMapFileR01S4)
                    .file(radioMapFileR02A5)
                    .file(radioMapFileR02S4Mini)
                    .file(radioMapFileR02S4)
                    .file(radioMapFileR03S4Mini)
                    .file(radioMapFileR03S4)
                    .file(radioMapFileR04A5)
                    .file(radioMapFileR04S4Mini)
                    .file(radioMapFileR04S4)
                    .param("buildingIdentifier", String.valueOf(buildingId)))
                    .andExpect(status().isOk());


        } catch (Exception e) {
            e.printStackTrace();
            fail("An unexpected Exception of type " + e.getClass().getSimpleName() + " has occurred.");
        }

    }


    @Test
    public void testProcessEmptyRadioMapFile() {

        try {
            long buildingId = TestHelper.addNewBuildingAndRetrieveId(this.mockMvc, this.contentType);
            assertTrue("Failed to add new building.", buildingId > 0);

            mockMvc.perform(MockMvcRequestBuilders.fileUpload("/position/processRadioMapFiles")
                    .file(emptyRadioMapFileHfT)
                    .file(transformedPointsFile)
                    .param("buildingIdentifier", String.valueOf(buildingId)))
                    .andExpect(status().isOk());


        } catch (Exception e) {
            e.printStackTrace();
            fail("An unexpected Exception of type " + e.getClass().getSimpleName() + " has occurred.");
        }


    }

    @Test
    public void testProcessEvaluationFile() {

        try {
            long buildingId = TestHelper.addNewBuildingAndRetrieveId(this.mockMvc, this.contentType);
            assertTrue("Failed to add new building.", buildingId > 0);

            mockMvc.perform(MockMvcRequestBuilders.fileUpload("/position/processEvalFiles")
                    .file(evaluationFile)
                    .param("buildingIdentifier", String.valueOf(buildingId)))
                    .andExpect(status().isOk());

        } catch (Exception e) {
            e.printStackTrace();
            fail("An unexpected Exception of type " + e.getClass().getSimpleName() + " has occurred.");
        }

    }

    @Test
    public void testGenerateBatchPositionResultsWoProject() {


        try {

            long buildingId = prepareBatchPositionResultCalculations();

            ResultActions getRadioMapResultActions = mockMvc.perform(get("/position/getRadioMapsForBuildingId?" +
                    "buildingIdentifier=" + buildingId));
            getRadioMapResultActions.andExpect(status().isOk());

            String getRadioMapResult = getRadioMapResultActions.andReturn().getResponse().getContentAsString();
            List<GetRadioMapFilesForBuilding> getRadioMapFilesForBuilding = (List<GetRadioMapFilesForBuilding>)
                    this.objectMapper.readValue(getRadioMapResult, new TypeReference<List<GetRadioMapFilesForBuilding>>() {
                    });
            assertTrue("The returned list of type " + GetRadioMapFilesForBuilding.class.getSimpleName() + " had an unexpected size.",
                    getRadioMapFilesForBuilding.size() == 1);

            ResultActions getEvalFileResultActions = mockMvc.perform(get("/position/getEvalFilesForBuildingId?" +
                    "buildingIdentifier=" + buildingId));
            getEvalFileResultActions.andExpect(status().isOk());
            String getEvalFileResult = getEvalFileResultActions.andReturn().getResponse().getContentAsString();
            List<GetEvaluationFilesForBuilding> getEvaluationFilesForBuilding = (List<GetEvaluationFilesForBuilding>)
                    this.objectMapper.readValue(getEvalFileResult, new TypeReference<List<GetEvaluationFilesForBuilding>>() {
                    });
            assertTrue("The returned list of type " + GetEvaluationFilesForBuilding.class.getSimpleName() + " had an unexpected size.",
                    getEvaluationFilesForBuilding.size() == 1);

            long radioMapFileId = getRadioMapFilesForBuilding.get(0).getId();
            long evalFileId = getEvaluationFilesForBuilding.get(0).getId();

            GenerateBatchPositionResults defaultGenerateBatchPositionResults = TestHelper.createDefaultBatchPositionRequestObject();
            defaultGenerateBatchPositionResults.setBuildingIdentifier(buildingId);
            defaultGenerateBatchPositionResults.setEvalFileIdentifier(evalFileId);
            defaultGenerateBatchPositionResults.setRadioMapFileIdentifiers(new long[]{radioMapFileId});

            ResultActions generateBatchPositionsResultActions = mockMvc.perform(post("/position/generateBatchPositionResults")
                    .content(TestHelper.jsonify(defaultGenerateBatchPositionResults))
                    .contentType(this.contentType));
            generateBatchPositionsResultActions.andExpect(status().isOk());
            String batchPositionResult = generateBatchPositionsResultActions.andReturn().getResponse().getContentAsString();
            List<BatchPositionResult> batchPositionResults = (List<BatchPositionResult>) this.objectMapper.readValue(batchPositionResult,
                    new TypeReference<List<BatchPositionResult>>() {
                    });
            assertTrue("The backend returned an unexpected number of results.", batchPositionResults.size() == 396);

        } catch (Exception e) {
            e.printStackTrace();
            fail("An unexpected Exception of type " + e.getClass().getSimpleName() + " has occurred.");
        }


    }


    @Test
    public void testGenerateBatchPositionResultsWithProject() throws Exception {

        long buildingId = prepareBatchPositionResultCalculations();

        AddNewProject addNewProjectElement = new AddNewProject(TestHelper.getSimpleProjectParameterSet(), DUMMY, ALGORITHM_TYPE, buildingId, 0l, emptyRadioMapIdArray);

        ResultActions saveNewProjectAction = mockMvc.perform(post("/project/saveNewProject")
                .content(TestHelper.jsonify(addNewProjectElement))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        String saveNewProjectResult = saveNewProjectAction.andReturn().getResponse().getContentAsString();
        ResponseWrapper responseWrapper = this.objectMapper.readValue(saveNewProjectResult, new TypeReference<ResponseWrapper>() {
        });
        long retrievedProjectId = responseWrapper.getId();

        ResultActions getRadioMapResultActions = mockMvc.perform(get("/position/getRadioMapsForBuildingId?" +
                "buildingIdentifier=" + buildingId));
        getRadioMapResultActions.andExpect(status().isOk());

        String getRadioMapResult = getRadioMapResultActions.andReturn().getResponse().getContentAsString();
        List<GetRadioMapFilesForBuilding> getRadioMapFilesForBuilding = (List<GetRadioMapFilesForBuilding>)
                this.objectMapper.readValue(getRadioMapResult, new TypeReference<List<GetRadioMapFilesForBuilding>>() {
                });
        assertTrue("The returned list of type " + GetRadioMapFilesForBuilding.class.getSimpleName() + " had an unexpected size.",
                getRadioMapFilesForBuilding.size() == 1);

        ResultActions getEvalFileResultActions = mockMvc.perform(get("/position/getEvalFilesForBuildingId?" +
                "buildingIdentifier=" + buildingId));
        getEvalFileResultActions.andExpect(status().isOk());
        String getEvalFileResult = getEvalFileResultActions.andReturn().getResponse().getContentAsString();
        List<GetEvaluationFilesForBuilding> getEvaluationFilesForBuilding = (List<GetEvaluationFilesForBuilding>)
                this.objectMapper.readValue(getEvalFileResult, new TypeReference<List<GetEvaluationFilesForBuilding>>() {
                });
        assertTrue("The returned list of type " + GetEvaluationFilesForBuilding.class.getSimpleName() + " had an unexpected size.",
                getEvaluationFilesForBuilding.size() == 1);

        long radioMapFileId = getRadioMapFilesForBuilding.get(0).getId();
        long evalFileId = getEvaluationFilesForBuilding.get(0).getId();

        GenerateBatchPositionResults positionRequestObject = TestHelper.createPositionRequestObjectWithProjectId(retrievedProjectId);
        positionRequestObject.setBuildingIdentifier(buildingId);
        positionRequestObject.setEvalFileIdentifier(evalFileId);
        positionRequestObject.setRadioMapFileIdentifiers(new long[]{radioMapFileId});

        ResultActions generateBatchPositionsResultActions = mockMvc.perform(post("/position/generateBatchPositionResults")
                .content(TestHelper.jsonify(positionRequestObject))
                .contentType(this.contentType));
        generateBatchPositionsResultActions.andExpect(status().isOk());
        String batchPositionResult = generateBatchPositionsResultActions.andReturn().getResponse().getContentAsString();
        List<BatchPositionResult> batchPositionResults = (List<BatchPositionResult>) this.objectMapper.readValue(batchPositionResult,
                new TypeReference<List<BatchPositionResult>>() {
                });
        assertTrue("The backend returned an unexpected number of results.", batchPositionResults.size() == 396);

    }


    @Test
    public void testGenerateBatchPositionResultsWoProjectWoParameterSet() throws Exception {

        long buildingId = prepareBatchPositionResultCalculations();
        assertTrue("Failed to add new building.", buildingId > 0);

        ResultActions getRadioMapResultActions = mockMvc.perform(get("/position/getRadioMapsForBuildingId?" +
                "buildingIdentifier=" + buildingId));
        getRadioMapResultActions.andExpect(status().isOk());

        String getRadioMapResult = getRadioMapResultActions.andReturn().getResponse().getContentAsString();
        List<GetRadioMapFilesForBuilding> getRadioMapFilesForBuilding = (List<GetRadioMapFilesForBuilding>)
                this.objectMapper.readValue(getRadioMapResult, new TypeReference<List<GetRadioMapFilesForBuilding>>() {
                });
        assertTrue("The returned list of type " + GetRadioMapFilesForBuilding.class.getSimpleName() + " had an unexpected size.",
                getRadioMapFilesForBuilding.size() == 1);

        ResultActions getEvalFileResultActions = mockMvc.perform(get("/position/getEvalFilesForBuildingId?" +
                "buildingIdentifier=" + buildingId));
        getEvalFileResultActions.andExpect(status().isOk());
        String getEvalFileResult = getEvalFileResultActions.andReturn().getResponse().getContentAsString();
        List<GetEvaluationFilesForBuilding> getEvaluationFilesForBuilding = (List<GetEvaluationFilesForBuilding>)
                this.objectMapper.readValue(getEvalFileResult, new TypeReference<List<GetEvaluationFilesForBuilding>>() {
                });
        assertTrue("The returned list of type " + GetEvaluationFilesForBuilding.class.getSimpleName() + " had an unexpected size.",
                getEvaluationFilesForBuilding.size() == 1);

        long radioMapFileId = getRadioMapFilesForBuilding.get(0).getId();
        long evalFileId = getEvaluationFilesForBuilding.get(0).getId();

        GenerateBatchPositionResults positionRequestObject = TestHelper.createPositionRequestObjectWithProjectId(1l);
        positionRequestObject.setBuildingIdentifier(buildingId);
        positionRequestObject.setEvalFileIdentifier(evalFileId);
        positionRequestObject.setRadioMapFileIdentifiers(new long[]{radioMapFileId});

        ResultActions generateBatchPositionsResultActions = mockMvc.perform(post("/position/generateBatchPositionResults")
                .content(TestHelper.jsonify(positionRequestObject))
                .contentType(this.contentType));
        generateBatchPositionsResultActions.andExpect(status().isOk());
        String batchPositionResult = generateBatchPositionsResultActions.andReturn().getResponse().getContentAsString();
        List<BatchPositionResult> batchPositionResults = (List<BatchPositionResult>) this.objectMapper.readValue(batchPositionResult,
                new TypeReference<List<BatchPositionResult>>() {
                });
        assertTrue("The backend returned an unexpected number of results.", batchPositionResults.size() == 396);


    }

    private long prepareBatchPositionResultCalculations() throws Exception {
        long buildingId = TestHelper.addNewBuildingAndRetrieveId(this.mockMvc, this.contentType);
        assertTrue("Failed to add new building.", buildingId > 0);

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/position/processRadioMapFiles")
                .file(radioMapFileR01A5)
                .param("buildingIdentifier", String.valueOf(buildingId)))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/position/processEvalFiles")
                .file(evaluationFile)
                .param("buildingIdentifier", String.valueOf(buildingId)))
                .andExpect(status().isOk());

        return buildingId;
    }

    //To be continued ;)


}
