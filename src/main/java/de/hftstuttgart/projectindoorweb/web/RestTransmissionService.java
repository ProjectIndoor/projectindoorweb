package de.hftstuttgart.projectindoorweb.web;


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

public interface RestTransmissionService { //If too many methods get introduced, split this interface up into multiple services!

    /*Evaal file processing and generating position results*/
    boolean processEvaalFiles(String buildingId, boolean evaluationFiles, MultipartFile[] radioMapFiles, MultipartFile transformedPointsFile);

    boolean deleteEvaalFile(String evaalFileIdentifier);

    List<BatchPositionResult> generatePositionResults(GenerateBatchPositionResults generateBatchPositionResults);

    SinglePositionResult getPositionForWifiReading(GenerateSinglePositionResult generateSinglePositionResult);

    List<BatchPositionResult> getPositionResultsForProjectIdentifier(String positionIdentifier);

    List<GetEvaluationFilesForBuilding> getEvaluationFilesForBuilding(String buildingIdentifier);

    List<GetRadioMapFilesForBuilding> getRadioMapFilesForBuilding(String buildingIdentifier);

    List<GetAllEvaalEntries> getAllEvaalEntries();

    /*Projects*/
    long addNewProject(AddNewProject addNewProject);

    boolean updateProject(UpdateProject updateProject);

    boolean deleteProject(String projectIdentifier);

    LoadSelectedProject loadSelectedProject(String projectIdentifier);

    List<GetAllProjects> getAllProjects();

    /*Algorithms and parameters*/
    List<GetAllAlgorithmTypes> getAllAlgorithmTypes();

    List<GetAlgorithmParameters> getAllParameters();

    List<GetAlgorithmParameters> getParametersForAlgorithm(String algorithmType);


    /*Buildings*/
    long addNewBuilding(AddNewBuilding addNewBuilding);

    List<GetAllBuildings> getAllBuildings();

    GetSingleBuilding getSingleBuilding(String buildingIdentifier);

    boolean updateBuilding(UpdateBuilding updateBuilding);

    boolean addFloorToBuilding(String buildingIdentifier, String floorIdentifier, String floorName, MultipartFile floorMapFile);

    File getFloorMap(String floorIdentifier);

    boolean deleteBuilding(String buildingIdentifier);
}
