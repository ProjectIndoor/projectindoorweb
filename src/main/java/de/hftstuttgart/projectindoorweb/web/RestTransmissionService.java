package de.hftstuttgart.projectindoorweb.web;


import de.hftstuttgart.projectindoorweb.web.internal.*;
import de.hftstuttgart.projectindoorweb.web.internal.EvaluationEntry;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface RestTransmissionService { //If too many methods get introduced, split this interface up into multiple services!

    boolean processEvaalFiles(String buildingId, boolean evaluationFiles, MultipartFile[] radioMapFiles);

    List<CalculatedPosition> generatePositionResults(BatchPositionRequestElement batchPositionRequestElement);

    CalculatedPosition getPositionForWifiReading(SinglePositionRequestEntry singlePositionRequestEntry);

    List<CalculatedPosition> getPositionResultsForProjectIdentifier(String positionIdentifier);

    long saveNewProject(NewProjectRequestElement newProjectRequestElement);

    boolean saveCurrentProject(CurrentProjectRequestElement currentProjectRequestElement);

    boolean deleteSelectedProject(String projectIdentifier);

    ProjectElement loadSelectedProject(String projectIdentifier);

    List<ProjectElement> getAllProjects();

    List<BuildingJsonWrapperSmall>getAllBuildings();

    List<AlgorithmType> getAllAlgorithmTypes();

    List<EvaluationEntry> getEvaluationFilesForBuilding(String buildingIdentifier);

    List<RadioMapEntry> getRadioMapFilesForBuilding(String buildingIdentifier);

    List<ParameterElement> getAlgorithmParameterListForAlgorithmId(String algorithmIdentifier);

    boolean addNewBuilding(BuildingJsonWrapperLarge buildingJsonWrapper);
}
