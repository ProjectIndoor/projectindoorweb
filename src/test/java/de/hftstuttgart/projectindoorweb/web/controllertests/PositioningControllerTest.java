package de.hftstuttgart.projectindoorweb.web.controllertests;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hftstuttgart.projectindoorweb.Application;
import de.hftstuttgart.projectindoorweb.persistence.repositories.BuildingRepository;
import de.hftstuttgart.projectindoorweb.web.configuration.TestWebConfiguration;
import de.hftstuttgart.projectindoorweb.web.helpers.TestHelper;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration(classes = TestWebConfiguration.class)
@WebAppConfiguration
public class PositioningControllerTest {

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
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/logfile_CAR_R01-2017_A5.txt")));
        radioMapFileR01S4Mini = new MockMultipartFile("radioMapFiles", "logfile_CAR_R01-2017_S4Mini.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/logfile_CAR_R01-2017_S4MINI.txt")));
        radioMapFileR01S4 = new MockMultipartFile("radioMapFiles", "logfile_CAR_R01-2017_S4.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/logfile_CAR_R01-2017_S4.txt")));
        radioMapFileR02A5 = new MockMultipartFile("radioMapFiles", "logfile_CAR_R02-2017_A5.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/logfile_CAR_R02-2017_A5.txt")));
        radioMapFileR02S4Mini = new MockMultipartFile("radioMapFiles", "logfile_CAR_R02-2017_S4Mini.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/logfile_CAR_R02-2017_S4MINI.txt")));
        radioMapFileR02S4 = new MockMultipartFile("radioMapFiles", "logfile_CAR_R02-2017_S4.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/logfile_CAR_R02-2017_S4.txt")));
        radioMapFileR03S4Mini = new MockMultipartFile("radioMapFiles", "logfile_CAR_R03-2017_S4Mini.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/logfile_CAR_R03-2017_S4MINI.txt")));
        radioMapFileR03S4 = new MockMultipartFile("radioMapFiles", "logfile_CAR_R03-2017_S4.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/logfile_CAR_R03-2017_S4.txt")));
        radioMapFileR04A5 = new MockMultipartFile("radioMapFiles", "logfile_CAR_R04-2017_A5.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/logfile_CAR_R04-2017_A5.txt")));
        radioMapFileR04S4Mini = new MockMultipartFile("radioMapFiles", "logfile_CAR_R04-2017_S4Mini.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/logfile_CAR_R04-2017_S4MINI.txt")));
        radioMapFileR04S4 = new MockMultipartFile("radioMapFiles", "logfile_CAR_R04-2017_S4.txt",
                "text/plain", Files.readAllBytes(Paths.get("./src/test/resources/logfile_CAR_R04-2017_S4.txt")));

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
    public void testProcessMultipleRadioMapFiles(){

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
            fail("An unexpected Exception of type " + e.getClass().getSimpleName() + " has occuured.");
        }


    }


}
