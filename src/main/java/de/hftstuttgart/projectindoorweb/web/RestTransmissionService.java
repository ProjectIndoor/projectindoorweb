package de.hftstuttgart.projectindoorweb.web;


import de.hftstuttgart.projectindoorweb.web.internal.requests.building.*;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.*;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.*;
import org.springframework.core.io.ClassPathResource;
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
