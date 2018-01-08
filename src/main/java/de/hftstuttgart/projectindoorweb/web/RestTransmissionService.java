package de.hftstuttgart.projectindoorweb.web;


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


/**
 * REST transmission service. Uses all underlying services of the Back End. Works as gateway between REST ContrResources
 * and Back End functionalities. All Components need to access <code>this</code> service in order to access Back End
 * functionalities. Do not use other services directly!
 */
public interface RestTransmissionService {

    //Evaal file processing and generating position results

    /**
     * Processes given Evaal files. Given files can be Evaluation files or radio map files.
     *
     * @param buildingId            The building id which the given files are associated with. Must not be <code>null</code>.
     * @param evaluationFiles       Flag if given files are evaluation files. True if files are evaluation files, otherwise false.
     * @param radioMapFiles         Radio map file array. Must not be <code>null</code>.
     * @param transformedPointsFile Transformed points file array. Can be null.
     * @return String message containing information if processing was successful or not.
     */
    String processEvaalFiles(String buildingId, boolean evaluationFiles,
                             MultipartFile[] radioMapFiles, MultipartFile[] transformedPointsFile);


    /**
     * Removes a evaal file with the given evaal file identifier.
     *
     * @param evaalFileIdentifier The identifier of the evaal file. Must not be <code>null</code>.
     * @return String message containing information if deletion was successful or not.
     */
    String deleteEvaalFile(String evaalFileIdentifier);

    /**
     * Generates a list of batch position results from a given batch.
     *
     * @param generateBatchPositionResults Batch containing information needed for position calculation. Must not be
     *                                     <code><null</code>.
     * @return A list of position results or an empty list if given input is invalid.
     */
    List<BatchPositionResult> generatePositionResults(GenerateBatchPositionResults generateBatchPositionResults);

    /**
     * Generates one position result with a given position information line.
     *
     * @param generateSinglePositionResult Position information line. Must not be <code>null</code>.
     * @return One position result or an empty position result if given data is invalid.
     */
    SinglePositionResult getPositionForWifiReading(GenerateSinglePositionResult generateSinglePositionResult);

    /**
     * Retrieves a position result of a project based on the given project identifier.
     *
     * @param projectIdentifier The project identifier of the wanted project's position. Must not be <code>null</code>.
     * @return A list of position results or an empty list if given data is invalid.
     */
    List<BatchPositionResult> getPositionResultsForProjectIdentifier(String projectIdentifier);

    /**
     * Retrieves all evaluation files for a given Building.
     *
     * @param buildingIdentifier The building identifier of the wanted evaluation files. Must not be <code>null</code>
     * @return A list of evaluation files or an
     */
    List<GetEvaluationFilesForBuilding> getEvaluationFilesForBuilding(String buildingIdentifier);

    /**
     * Retrieves all radio map files of a given building.
     *
     * @param buildingIdentifier The identifier of the building which is associated with the wanted radio map files.
     * @return A list of radio map files or an empty list if given data was invalid.
     */
    List<GetRadioMapFilesForBuilding> getRadioMapFilesForBuilding(String buildingIdentifier);

    /**
     * Retrieves all currently available evaal file entries.
     *
     * @return A list of evaal file entries or an empty list if no files have been found.
     */
    List<GetAllEvaalEntries> getAllEvaalEntries();


    //Projects

    /**
     * Adds a new project to the database.
     *
     * @param addNewProject project which should be added to the database. Must not be <code>null</code>.
     * @return A response containing a message if addition was successful and an associated project id.
     */
    ResponseWrapper addNewProject(AddNewProject addNewProject);

    /**
     * Overwrites an already available project with a given project.
     *
     * @param updateProject The project which updates a already available one. Must not be <code>null</code>.
     * @return A String message informing about update status.
     */
    String updateProject(UpdateProject updateProject);

    /**
     * Deletes a project with a given identifier.
     *
     * @param projectIdentifier The identfier of the project which should be deleted. Must not be <code>null</code>.
     * @return A String message informing about deletion status.
     */
    String deleteProject(String projectIdentifier);

    /**
     * Loads and retrieves a project based on the given project identifier.
     *
     * @param projectIdentifier The project identifier of the project which should be loaded and retrieved.
     *                          Must not be <code>null</code>
     * @return The wanted project or an empty result element if given data was invalid.
     */
    LoadSelectedProject loadSelectedProject(String projectIdentifier);

    /**
     * Retrieves all projects currently availabe in the database:.
     *
     * @return A list of projects currently available or an empty list if no projects have been found.
     */
    List<GetAllProjects> getAllProjects();

    //Algorithms and parameters

    /**
     * Retrieves all currently available algorithm types.
     *
     * @return A list of algorithm types or an empty list if none have been found.
     */
    List<GetAllAlgorithmTypes> getAllAlgorithmTypes();

    /**
     * Retrieves all parameters currently available in the Back End.
     *
     * @return A list of currently available parameters or an empty list if no parameters have been found
     */
    List<GetAlgorithmParameters> getAllParameters();

    /**
     * Retrieves all known parameters which are associated with the given algorithm type.
     *
     * @param algorithmType The algorithm type from which the known parameters should be retrieved from. Must not
     *                      be <code>null</code>.
     * @return A list of know parameters or an empty list if given data is invalid.
     */
    List<GetAlgorithmParameters> getParametersForAlgorithm(String algorithmType);


    //Buildings

    /**
     * Adds a new building to the database.
     *
     * @param addNewBuilding The building which should be added. Must not be <code>null</code>.
     * @return A response containing a String message if addition was successful and an associated building id.
     */
    ResponseWrapper addNewBuilding(AddNewBuilding addNewBuilding);

    /**
     * Retrieves all available buildings in the database.
     *
     * @return A list of buildings or an empty list if no buildings have been found.
     */
    List<GetAllBuildings> getAllBuildings();

    /**
     * Retrieves a building based on the given building identifier.
     *
     * @param buildingIdentifier The identifier of the wanted building. Must not be <code>null</code>.
     * @return A building which is associated with the given identifier or an empty result object if
     * given data was invalid.
     */
    GetSingleBuilding getSingleBuilding(String buildingIdentifier);

    /**
     * Overwrites an already available building based on the given building element.
     *
     * @param updateBuilding The building which should overwrite an already existing building.
     *                       Must not be <code>null</code>.
     * @return A String message describing the update status of the building.
     */
    String updateBuilding(UpdateBuilding updateBuilding);

    /**
     * Adds a new floor to a given building.
     *
     * @param buildingIdentifier The identifier of the building which is associated to the given floor.
     *                           Must not be <code>null</code>.
     * @param floorIdentifier    The floor identifier which is associated with the given building. Mst
     *                           not be <code>null</code>.
     * @param floorName          The floor name. Must not be <code>null</code>.
     * @param floorMapFile       The floor map file. Must not be <code>null</code>.
     * @return
     */
    String addFloorToBuilding(String buildingIdentifier, String floorIdentifier, String floorName, MultipartFile floorMapFile);

    /**
     * Retrieves a file which represents the floor map of the given floor identifier.
     *
     * @param floorIdentifier The floor identifier. Must not be <code>null</code>.
     * @return A file which represents the floor map. Or <code>null</code> if no file has been found!
     */
    File getFloorMap(String floorIdentifier);

    /**
     * Deletes a building based on the given building identifier.
     *
     * @param buildingIdentifier The identifier of the given building
     * @return A String message describing the deletion status of the given building.
     */
    String deleteBuilding(String buildingIdentifier);
}
