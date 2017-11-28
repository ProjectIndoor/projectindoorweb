package de.hftstuttgart.projectindoorweb.web;


import de.hftstuttgart.projectindoorweb.web.internal.*;
import de.hftstuttgart.projectindoorweb.web.internal.util.EvaluationEntry;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface RestTransmissionService { //If too many methods get introduced, split this interface up into multiple services!

    boolean generateRadioMap(String projectId, String buildingId, MultipartFile[] radioMapFiles);

    List<CalculatedPosition> generatePositionResults(String projectId, String buildingId, MultipartFile evaluationFile);

    CalculatedPosition getPositionForWifiReading(String projectId, String wifiReading);

    List<CalculatedPosition> getPositionResultsForProjectIdentifier(String positionIdentifier);

    long saveNewProject(Set<ProjectParameter> projectParameterSet, String projectName, String algorithmType);

    boolean saveCurrentProject(String projectName, Set<ProjectParameter> projectParameterSet, String projectIdentifier, String algorithmType);

    boolean deleteSelectedProject(String projectIdentifier);

    ProjectElement loadSelectedProject(String projectIdentifier);

    List<ProjectElement> getAllProjects();

    List<BuildingJsonWrapperSmall>getAllBuildings();

    List<AlgorithmType> getAllAlgorithmTypes();

    List<EvaluationEntry> getEvaluationEntriesForBuildingId(String buildingIdentifier);

    List<ParameterElement> getAlgorithmParameterListForAlgorithmId(String algorithmIdentifier);

    boolean addNewBuilding(BuildingJsonWrapperLarge buildingJsonWrapper);
}
