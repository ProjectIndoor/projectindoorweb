package de.hftstuttgart.projectindoorweb.web;


import de.hftstuttgart.projectindoorweb.web.internal.requests.building.AddNewBuilding;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.GetAllBuildings;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.*;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RestTransmissionService { //If too many methods get introduced, split this interface up into multiple services!

    boolean processEvaalFiles(String buildingId, boolean evaluationFiles, MultipartFile[] radioMapFiles);

    List<GeneratePositionResult> generatePositionResults(GenerateBatchPositionResults generateBatchPositionResults);

    GeneratePositionResult getPositionForWifiReading(GenerateSinglePositionResult generateSinglePositionResult);

    List<GeneratePositionResult> getPositionResultsForProjectIdentifier(String positionIdentifier);

    long saveNewProject(SaveNewProject saveNewProject);

    boolean saveCurrentProject(SaveCurrentProject saveCurrentProject);

    boolean deleteSelectedProject(String projectIdentifier);

    LoadSelectedProject loadSelectedProject(String projectIdentifier);

    List<LoadSelectedProject> getAllProjects();

    List<GetAllBuildings>getAllBuildings();

    List<GetAllAlgorithmTypes> getAllAlgorithmTypes();

    List<GetEvaluationFilesForBuilding> getEvaluationFilesForBuilding(String buildingIdentifier);

    List<GetRadioMapFilesForBuilding> getRadioMapFilesForBuilding(String buildingIdentifier);

    List<GetAlgorithmParameters> getAlgorithmParameterListForAlgorithmId(String algorithmIdentifier);

    boolean addNewBuilding(AddNewBuilding buildingJsonWrapper);
}
