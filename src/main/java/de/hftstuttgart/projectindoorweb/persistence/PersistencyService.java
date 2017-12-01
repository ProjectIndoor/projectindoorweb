package de.hftstuttgart.projectindoorweb.persistence;

import de.hftstuttgart.projectindoorweb.persistence.entities.Building;
import de.hftstuttgart.projectindoorweb.persistence.entities.EvaalFile;
import de.hftstuttgart.projectindoorweb.persistence.entities.Project;
import de.hftstuttgart.projectindoorweb.web.internal.PositionAnchor;
import de.hftstuttgart.projectindoorweb.web.internal.ProjectParameter;

import java.util.List;
import java.util.Set;

public interface PersistencyService {

    boolean addNewBuilding(String buildingName, int numberOfFloors, int imagePixelWidth, int imagePixelHeight,
                           PositionAnchor southEastAnchor, PositionAnchor southWestAnchor,
                           PositionAnchor northEastAnchor, PositionAnchor northWestAnchor);

    List<Building> getAllBuildings();

    Building getBuildingById(long buildingId);

    boolean saveEvaalFiles(List<EvaalFile> evaalFiles);

    EvaalFile getEvaalFileForId(long evaalFileId);

    List<EvaalFile> getEvaluationFilesForBuilding(Building building);

    List<EvaalFile> getRadioMapFilesForBuiling(Building building);

    long createNewProject(String projectName, String algorithmType, Set<ProjectParameter> projectParameters);

    boolean updateProject(long projectId, String newProjectName, String newAlgorithmType, Set<ProjectParameter> newProjectParameters);

    boolean updateProject(Project project);

    boolean deleteProject(long projectId);

    List<Project> getAllProjects();

    Project getProjectById(long projectId);



}
