package de.hftstuttgart.projectindoorweb.web;


import de.hftstuttgart.projectindoorweb.persistence.entities.GenericResponse;
import de.hftstuttgart.projectindoorweb.web.internal.ResponseWrapper;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.AddNewBuilding;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.GetAllBuildings;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.GetSingleBuilding;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.UpdateBuilding;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.BatchPositionResult;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.GenerateBatchPositionResults;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.GenerateSinglePositionResult;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.GetAllEvaalEntries;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.GetEvaluationFilesForBuilding;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.GetRadioMapFilesForBuilding;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.SinglePositionResult;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.AddNewProject;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.GetAlgorithmParameters;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.GetAllAlgorithmTypes;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.GetAllProjects;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.LoadSelectedProject;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.UpdateProject;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface RestTransmissionService {

    /*Evaal file processing and generating position results*/
    String processEvaalFiles(String buildingId, boolean evaluationFiles,
                              MultipartFile[] radioMapFiles, MultipartFile[] transformedPointsFile);

    String deleteEvaalFile(String evaalFileIdentifier);

    List<BatchPositionResult> generatePositionResults(GenerateBatchPositionResults generateBatchPositionResults);

    SinglePositionResult getPositionForWifiReading(GenerateSinglePositionResult generateSinglePositionResult);

    List<BatchPositionResult> getPositionResultsForProjectIdentifier(String positionIdentifier);

    List<GetEvaluationFilesForBuilding> getEvaluationFilesForBuilding(String buildingIdentifier);

    List<GetRadioMapFilesForBuilding> getRadioMapFilesForBuilding(String buildingIdentifier);

    List<GetAllEvaalEntries> getAllEvaalEntries();

    /*Projects*/
    ResponseWrapper addNewProject(AddNewProject addNewProject);

    String updateProject(UpdateProject updateProject);

    String deleteProject(String projectIdentifier);

    LoadSelectedProject loadSelectedProject(String projectIdentifier);

    List<GetAllProjects> getAllProjects();

    /*Algorithms and parameters*/
    List<GetAllAlgorithmTypes> getAllAlgorithmTypes();

    List<GetAlgorithmParameters> getAllParameters();

    List<GetAlgorithmParameters> getParametersForAlgorithm(String algorithmType);


    /*Buildings*/
    ResponseWrapper addNewBuilding(AddNewBuilding addNewBuilding);

    List<GetAllBuildings> getAllBuildings();

    GetSingleBuilding getSingleBuilding(String buildingIdentifier);

    String updateBuilding(UpdateBuilding updateBuilding);

    String addFloorToBuilding(String buildingIdentifier, String floorIdentifier, String floorName, MultipartFile floorMapFile);

    File getFloorMap(String floorIdentifier);

    String deleteBuilding(String buildingIdentifier);
}
