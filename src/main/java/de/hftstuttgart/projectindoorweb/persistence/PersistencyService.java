package de.hftstuttgart.projectindoorweb.persistence;

import de.hftstuttgart.projectindoorweb.persistence.entities.*;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.BuildingPositionAnchor;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.SaveNewProjectParameters;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface PersistencyService {

    /*Buildings and Floors*/
    GenericResponse addNewBuilding(String buildingName, int numberOfFloors, int imagePixelWidth, int imagePixelHeight,
                                   BuildingPositionAnchor southEastAnchor, BuildingPositionAnchor southWestAnchor,
                                   BuildingPositionAnchor northEastAnchor, BuildingPositionAnchor northWestAnchor,
                                   BuildingPositionAnchor buildingCenterPoint, double rotationAngle, double metersPerPixel);

    List<Building> getAllBuildings();

    Building getBuildingById(long buildingId);

    String updateBuilding(long buildingId, String buildingName, int imagePixelWidth, int imagePixelHeight,
                           Position northWest, Position northEast, Position southEast, Position southWest, Position buildingCenterPoint,
                           double rotationAngle, double metersPerPixel);

    String updateBuilding(Building building, Floor floor, File floorMapFile) throws IOException;

    File getFloorMapByFloorId(long floorId) throws IOException;

    String deleteBuilding(long buildingId);


    /*Evaal files*/
    String saveEvaalFiles(List<EvaalFile> evaalFiles);

    EvaalFile getEvaalFileForId(long evaalFileId);

    List<EvaalFile> getAllEvaalFiles();

    List<EvaalFile> getEvaluationFilesForBuilding(Building building);

    List<EvaalFile> getRadioMapFilesForBuiling(Building building);

    String deleteEvaalFile(long evaalFileId);


    /*Projects*/
    GenericResponse createNewProject(String projectName, String algorithmType, Set<SaveNewProjectParameters> saveNewProjectParameters,
                          long buildingIdentifier, long evalFileIdentifier, long[] radioMapFileIdentifiers);

    String updateProject(long projectId, String newProjectName, String newAlgorithmType,
                          Set<SaveNewProjectParameters> newSaveNewProjectParameters, long buildingIdentifier,
                          long evalFileIdentifier, long[] radioMapFileIdentifiers);

    String deleteProject(long projectId);

    List<Project> getAllProjects();

    Project getProjectById(long projectId);


}
