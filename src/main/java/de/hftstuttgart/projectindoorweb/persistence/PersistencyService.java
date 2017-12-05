package de.hftstuttgart.projectindoorweb.persistence;

import de.hftstuttgart.projectindoorweb.persistence.entities.Building;
import de.hftstuttgart.projectindoorweb.persistence.entities.EvaalFile;
import de.hftstuttgart.projectindoorweb.persistence.entities.Position;
import de.hftstuttgart.projectindoorweb.persistence.entities.Project;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.BuildingPositionAnchor;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.SaveNewProjectParameters;

import java.util.List;
import java.util.Set;

public interface PersistencyService {

    boolean addNewBuilding(String buildingName, int numberOfFloors, int imagePixelWidth, int imagePixelHeight,
                           BuildingPositionAnchor southEastAnchor, BuildingPositionAnchor southWestAnchor,
                           BuildingPositionAnchor northEastAnchor, BuildingPositionAnchor northWestAnchor,
                           BuildingPositionAnchor buildingCenterPoint, double rotationAngle, double metersPerPixel);

    List<Building> getAllBuildings();

    Building getBuildingById(long buildingId);

    boolean updateBuilding(long buildingId, String buildingName, int imagePixelWidth, int imagePixelHeight,
                           Position northWest, Position northEast, Position southEast, Position southWest, Position buildingCenterPoint,
                           double rotationAngle, double metersPerPixel);

    boolean saveEvaalFiles(List<EvaalFile> evaalFiles);

    EvaalFile getEvaalFileForId(long evaalFileId);

    List<EvaalFile> getEvaluationFilesForBuilding(Building building);

    List<EvaalFile> getRadioMapFilesForBuiling(Building building);

    long createNewProject(String projectName, String algorithmType, Set<SaveNewProjectParameters> saveNewProjectParamaters);

    boolean updateProject(long projectId, String newProjectName, String newAlgorithmType, Set<SaveNewProjectParameters> newSaveNewProjectParamaters);

    boolean updateProject(Project project);

    boolean deleteProject(long projectId);

    List<Project> getAllProjects();

    Project getProjectById(long projectId);



}
