package de.hftstuttgart.projectindoorweb.persistence;

import de.hftstuttgart.projectindoorweb.persistence.entities.Parameter;
import de.hftstuttgart.projectindoorweb.persistence.entities.Project;
import de.hftstuttgart.projectindoorweb.persistence.repositories.ProjectRepository;
import de.hftstuttgart.projectindoorweb.positionCalculator.CalculationAlgorithm;
import de.hftstuttgart.projectindoorweb.web.internal.ProjectParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PersistencyServiceImpl implements PersistencyService {



    @Override
    public long createNewProject(String projectName, String algorithmType, Set<ProjectParameter> projectParameters) {

        List<Parameter> parametersAsList = convertToEntityParameters(projectParameters);

        CalculationAlgorithm calculationAlgorithm = getAlgorithmFromText(algorithmType);
        Project newProject = new Project(projectName, calculationAlgorithm, parametersAsList);

        ProjectRepository projectRepository = (ProjectRepository) RepositoryRegistry.getRepositoryByEntityName(Project.class.getName());
        newProject = projectRepository.save(newProject);


        return newProject.getId();

    }

    @Override
    public boolean updateProject(long projectId, String newProjectName, String newAlgorithmType, Set<ProjectParameter> newProjectParameters) {

        ProjectRepository projectRepository = (ProjectRepository) RepositoryRegistry.getRepositoryByEntityName(Project.class.getName());

        Project project = projectRepository.findOne(projectId);

        if(project != null){
            project.setProjectName(newProjectName);
            project.setCalculationAlgorithm(getAlgorithmFromText(newAlgorithmType));
            project.setProjectParameters(convertToEntityParameters(newProjectParameters));
            projectRepository.save(project);
            return true;
        }

        return false;

    }

    @Override
    public boolean deleteProject(long projectId) {

        ProjectRepository projectRepository = (ProjectRepository) RepositoryRegistry.getRepositoryByEntityName(Project.class.getName());

        projectRepository.delete(projectId);
        Project project = projectRepository.findOne(projectId);

        return project == null;

    }

    @Override
    public Project getProjectById(long projectId) {

        ProjectRepository projectRepository = (ProjectRepository) RepositoryRegistry.getRepositoryByEntityName(Project.class.getName());

        return projectRepository.findOne(projectId);

    }

    @Override
    public List<Project> getAllProjects() {

        ProjectRepository projectRepository = (ProjectRepository) RepositoryRegistry.getRepositoryByEntityName(Project.class.getName());

        return (List<Project>) projectRepository.findAll();

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
