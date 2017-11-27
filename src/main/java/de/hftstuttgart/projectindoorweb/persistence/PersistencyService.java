package de.hftstuttgart.projectindoorweb.persistence;

import de.hftstuttgart.projectindoorweb.persistence.entities.Building;
import de.hftstuttgart.projectindoorweb.persistence.entities.LogFile;
import de.hftstuttgart.projectindoorweb.persistence.entities.Project;
import de.hftstuttgart.projectindoorweb.web.internal.PositionAnchor;
import de.hftstuttgart.projectindoorweb.web.internal.ProjectParameter;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PersistencyService {
    long createNewProject(String projectName, String algorithmType, Set<ProjectParameter> projectParameters);

    boolean updateProject(long projectId, String newProjectName, String newAlgorithmType, Set<ProjectParameter> newProjectParameters);

    boolean updateProject(Project project);

    boolean deleteProject(long projectId);

    long addNewBuilding(String buildingName, long actualNumberOfFloors, PositionAnchor southEastAnchor, PositionAnchor southWestAnchor, PositionAnchor northEastAnchor, PositionAnchor northWestAnchor);

    Project getProjectById(long projectId);

    List<Project> getAllProjects();

    List<Building> getAllBuildings();

    boolean saveLogFiles(List<LogFile> logFiles);

}
