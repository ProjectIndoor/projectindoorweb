package de.hftstuttgart.projectindoorweb.web;


import de.hftstuttgart.projectindoorweb.web.internal.CalculatedPosition;
import de.hftstuttgart.projectindoorweb.web.internal.ProjectElement;
import de.hftstuttgart.projectindoorweb.web.internal.ProjectParameter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface RestTransmissionService { //If too many methods get introduced, split this interface up into multiple services!

    boolean generateRadioMap(String projectId, String buildingId, MultipartFile[] radioMapFiles);

    List<CalculatedPosition> generatePositionResults(String projectId, String buildingId, MultipartFile evaluationFile);

    CalculatedPosition getPositionForWifiReading(String wifiReading);

    List<CalculatedPosition> getPositionResultsForIdentifier(String positionIdentifier);

    long saveNewProject(Set<ProjectParameter> projectParameterSet, String projectName, String algorithmType);

    boolean saveCurrentProject(String projectName, Set<ProjectParameter> projectParameterSet, String projectIdentifier, String algorithmType);

    boolean deleteSelectedProject(String projectIdentifier);

    ProjectElement loadSelectedProject(String projectIdentifier);

    List<ProjectElement> getAllProjects();
}
