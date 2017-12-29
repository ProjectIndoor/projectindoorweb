package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.Application;
import de.hftstuttgart.projectindoorweb.persistence.repositories.ProjectRepository;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectControllerTest {

    private final static String ALGORITHM_TYPE = "WIFI";
    private final static String DUMMY = "Dummy";
    private final static String OTHER_DUMMY = "other Dummy";
    private static final String EMPTY_STRING = "";

    private Set<SaveNewProjectParameters> emptyParameterSet = new HashSet<>();
    private long[] emptyRadioMapIdArray = new long[2];
    private long[] negativeRadioMapIdArray = {-1l};

    private MediaType jsonContentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));


    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private AddNewProject addNewProjectElement;

    private List<GetAllProjects> getAllProjectsElementList = new ArrayList<>();

    private UpdateProject updateProjectElement;

    private LoadSelectedProject loadSelectedProjectElement;

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
        initBuildingElements();
    }

    @Test
    public void AcreateProjectTest() throws Exception {
        mockMvc.perform(post("/project/saveNewProject")
                .content(this.json(this.addNewProjectElement))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void BreadAllProjectTest() throws Exception {

        mockMvc.perform(get("/project/getAllProjects")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string(json(getAllProjectsElementList)));
    }

    @Test
    public void CupdateProjectTest() throws Exception {
        mockMvc.perform(post("/project/saveCurrentProject")
                .content(this.json(this.updateProjectElement))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void DreadSelectedProjectTest() throws Exception {
        mockMvc.perform(get("/project/loadSelectedProject?projectIdentifier="
                + updateProjectElement.getProjectIdentifier())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string(this.json(this.loadSelectedProjectElement)));
    }

    @Test
    public void EdeleteProjectTest() throws Exception {
        mockMvc.perform(delete("/project/deleteSelectedProject?projectIdentifier="
                + updateProjectElement.getProjectIdentifier())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    private void initBuildingElements() {
        addNewProjectElement = new AddNewProject(emptyParameterSet, DUMMY, ALGORITHM_TYPE, 0l, 0l, emptyRadioMapIdArray);

        GetAllProjects getAllProjectsElement = new GetAllProjects(1, DUMMY, EMPTY_STRING);
        getAllProjectsElementList.add(getAllProjectsElement);

        updateProjectElement = new UpdateProject(1l, emptyParameterSet, OTHER_DUMMY, ALGORITHM_TYPE, 0l, 0l, emptyRadioMapIdArray);

        loadSelectedProjectElement = new LoadSelectedProject(1l, emptyParameterSet, OTHER_DUMMY, ALGORITHM_TYPE, -1l, -1l, negativeRadioMapIdArray);
    }

}
