package de.hftstuttgart.projectindoorweb.persistence;

import de.hftstuttgart.projectindoorweb.application.internal.AssertParam;
import de.hftstuttgart.projectindoorweb.persistence.entities.Building;
import de.hftstuttgart.projectindoorweb.persistence.entities.LogFile;
import de.hftstuttgart.projectindoorweb.persistence.entities.Parameter;
import de.hftstuttgart.projectindoorweb.persistence.entities.Project;
import de.hftstuttgart.projectindoorweb.persistence.repositories.LogFileRepository;
import de.hftstuttgart.projectindoorweb.persistence.repositories.ProjectRepository;
import de.hftstuttgart.projectindoorweb.positionCalculator.CalculationAlgorithm;
import de.hftstuttgart.projectindoorweb.web.internal.PositionAnchor;
import de.hftstuttgart.projectindoorweb.web.internal.ProjectParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PersistencyServiceImpl implements PersistencyService {

    @Override
    public long createNewProject(String projectName, String algorithmType, Set<ProjectParameter> projectParameters) {

        AssertParam.throwIfNullOrEmpty(projectName,"projectName");
        AssertParam.throwIfNullOrEmpty(algorithmType,"algorithmType");
        AssertParam.throwIfNull(projectParameters,"projectParameters");

        CalculationAlgorithm calculationAlgorithm = getAlgorithmFromText(algorithmType);

        if(calculationAlgorithm==null){
            return -1;
        }

        List<Parameter> parametersAsList = convertToEntityParameters(projectParameters);

        Project newProject = new Project(projectName, calculationAlgorithm, parametersAsList);

        ProjectRepository projectRepository = (ProjectRepository) RepositoryRegistry.getRepositoryByEntityName(Project.class.getName());
        newProject = projectRepository.save(newProject);

        return newProject.getId();

    }

    @Override
    public boolean updateProject(long projectId, String newProjectName, String newAlgorithmType, Set<ProjectParameter> newProjectParameters) {

        AssertParam.throwIfNull(projectId,"projectId");
        AssertParam.throwIfNullOrEmpty(newProjectName,"newProjectName");
        AssertParam.throwIfNullOrEmpty(newAlgorithmType,"newAlgorithmType");
        AssertParam.throwIfNull(newProjectParameters,"newProjectParameters");

        ProjectRepository projectRepository = (ProjectRepository) RepositoryRegistry.getRepositoryByEntityName(Project.class.getName());

        Project project = projectRepository.findOne(projectId);
        CalculationAlgorithm requestedAlgorithm = getAlgorithmFromText(newAlgorithmType);

        if(project != null && requestedAlgorithm != null){
            project.setProjectName(newProjectName);
            project.setCalculationAlgorithm(requestedAlgorithm);
            project.setProjectParameters(convertToEntityParameters(newProjectParameters));
            projectRepository.save(project);
            return true;
        }

        return false;

    }

    @Override
    public boolean updateProject(Project project) {

        ProjectRepository projectRepository = (ProjectRepository) RepositoryRegistry.getRepositoryByEntityName(Project.class.getName());

        Project fromDatabase = projectRepository.findOne(project.getId());

        if(project != null){
            fromDatabase.setProjectName(project.getProjectName());
            fromDatabase.setProjectParameters(project.getProjectParameters());
            fromDatabase.setCalculationAlgorithm(project.getCalculationAlgorithm());
            fromDatabase.setLogFiles(project.getLogFiles());
            projectRepository.save(fromDatabase);
            return true;
        }

        return false;

    }



    @Override
    public boolean deleteProject(long projectId) {

        AssertParam.throwIfNull(projectId,"projectId");

        ProjectRepository projectRepository = (ProjectRepository) RepositoryRegistry.getRepositoryByEntityName(Project.class.getName());

        projectRepository.delete(projectId);
        Project project = projectRepository.findOne(projectId);

        return project == null;

    }

    @Override
    public long addNewBuilding(String buildingName, long actualNumberOfFloors, PositionAnchor southEastAnchor, PositionAnchor southWestAnchor, PositionAnchor northEastAnchor, PositionAnchor northWestAnchor) {

        AssertParam.throwIfNullOrEmpty(buildingName,"buildingName");
        AssertParam.throwIfNull(actualNumberOfFloors,"actualNumberOfFloors");
        AssertParam.throwIfNull(southEastAnchor,"southEastAnchor");
        AssertParam.throwIfNull(southWestAnchor,"southWestAnchor");
        AssertParam.throwIfNull(northEastAnchor,"northEastAnchor");
        AssertParam.throwIfNull(northWestAnchor,"northWestAnchor");

        return 0;//TODO implement when ready
    }

    @Override
    public Project getProjectById(long projectId) {

        AssertParam.throwIfNull(projectId,"projectId");

        ProjectRepository projectRepository = (ProjectRepository) RepositoryRegistry.getRepositoryByEntityName(Project.class.getName());

        return projectRepository.findOne(projectId);

    }

    @Override
    public List<Project> getAllProjects() {

        ProjectRepository projectRepository = (ProjectRepository) RepositoryRegistry.getRepositoryByEntityName(Project.class.getName());

        return (List<Project>) projectRepository.findAll();

    }

    @Override
    public List<Building> getAllBuildings() {
        //TODO implement when ready
        return new ArrayList<>();
    }

    @Override
    public boolean saveLogFiles(List<LogFile> logFiles) {

        AssertParam.throwIfNull(logFiles,"logFiles");

        LogFileRepository logFileRepository = (LogFileRepository) RepositoryRegistry.getRepositoryByEntityName(LogFile.class.getName());

        Iterable<LogFile> saved = logFileRepository.save(logFiles);

        return saved != null;

    }


    private List<Parameter> convertToEntityParameters(Set<ProjectParameter> projectParameters){

        List<Parameter> parametersAsList = new ArrayList<>();

        for (ProjectParameter parameter:
                projectParameters) {
            parametersAsList.add(new Parameter(parameter.getName(), parameter.getValue()));
        }

        return parametersAsList;


    }

    private CalculationAlgorithm getAlgorithmFromText(String text){

        CalculationAlgorithm calculationAlgorithm = null;
        if(text.equals("WIFI")){
            calculationAlgorithm = CalculationAlgorithm.WIFI;
        }

        return calculationAlgorithm;

    }


}
