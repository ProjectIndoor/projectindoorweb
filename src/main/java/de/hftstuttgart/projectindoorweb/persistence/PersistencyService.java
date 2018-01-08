package de.hftstuttgart.projectindoorweb.persistence;

import de.hftstuttgart.projectindoorweb.persistence.entities.*;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.BuildingPositionAnchor;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.SaveNewProjectParameters;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface PersistencyService {

    /**
     * Adds a new building to the database.
     *
     * @param buildingName        The name of the building to be added.
     * @param numberOfFloors      The number of floors the building has.
     * @param imagePixelWidth     The width of the floor map pictures in pixels.
     * @param imagePixelHeight    The height of the floor map pictures in pixels.
     * @param southEastAnchor     The south-eastern reference point of the building as Latitude and Longitude.
     * @param southWestAnchor     The south-western reference point of the building as Latitude and Longitude.
     * @param northEastAnchor     The north-eastern reference point of the building as Latitude and Longitude.
     * @param northWestAnchor     The north-western reference point of the building as Latitude and Longitude.
     * @param buildingCenterPoint The building's central reference point as Latitude and Longitude.
     * @param rotationAngle       The rotation angle of the building in relation to the geometric north in degrees.
     * @param metersPerPixel      Meters per pixel in the building's floor maps.
     * @return An instance of {@link GenericResponse} containing the ID of the newly created building (or -1
     * in case the creation was not successful) as well as a result message.
     */
    GenericResponse addNewBuilding(String buildingName, int numberOfFloors, int imagePixelWidth, int imagePixelHeight,
                                   BuildingPositionAnchor southEastAnchor, BuildingPositionAnchor southWestAnchor,
                                   BuildingPositionAnchor northEastAnchor, BuildingPositionAnchor northWestAnchor,
                                   BuildingPositionAnchor buildingCenterPoint, double rotationAngle, double metersPerPixel);

    /**
     * Returns a list of all buildings currently present in the database.
     *
     * @return The list of all buildings.
     */
    List<Building> getAllBuildings();

    /**
     * Returns a {@link Building} representing the building instance whose ID matches the provided ID.
     *
     * @param buildingId The ID of the {@link Building} instance that is to be returned.
     * @return The {@link Building} instance matching the given ID or null in case the database does
     * not contain a building with that ID.
     */
    Building getBuildingById(long buildingId);

    /**
     * Updates the characteristics of a building given its ID.
     *
     * @param buildingId          The ID of the building that will be updated.
     * @param buildingName        The new name of the building.
     * @param imagePixelWidth     The new width of the building's floor map pictures.
     * @param imagePixelHeight    The new height of the building's floor map pictures.
     * @param northWest           The new position of the north-western building reference point.
     * @param northEast           The new position of the north-eastern building reference point.
     * @param southEast           The new position of the south-eastern building reference point.
     * @param southWest           The new position of the south-western building reference point.
     * @param buildingCenterPoint The new position of the building's central reference point.
     * @param rotationAngle       The new rotation angle of the building in relation to the geometric north given in degrees.
     * @param metersPerPixel      The new number of meters per pixel in the building's floor maps.
     * @return A short message indicating operation success or failure.
     */
    String updateBuilding(long buildingId, String buildingName, int imagePixelWidth, int imagePixelHeight,
                          Position northWest, Position northEast,
                          Position southEast, Position southWest,
                          Position buildingCenterPoint, double rotationAngle, double metersPerPixel);

    /**
     * Updates given floor within the given building with the supplied floor map picture file.
     *
     * @param building     The building containing the floor that the floor map picture file will be added to.
     * @param floor        The floor that the map will be added to.
     * @param floorMapFile The floor map picture file to be added.
     * @return A short message indicating operation success or failure.
     * @throws IOException In case something somewhere went terribly wrong.
     */
    String updateBuilding(Building building, Floor floor, File floorMapFile) throws IOException;

    /**
     * Returns the floor map picture file associated with the floor identified by the provided ID. Because the floor IDs
     * are unique across all buildings, it is sufficient to provide only the floor ID -- the building ID is not needed.
     *
     * @param floorId The ID of the floor whose map picture file is to be returned.
     * @return The floor map picture file.
     * @throws IOException In case something somewhere went terribly wrong.
     */
    File getFloorMapByFloorId(long floorId) throws IOException;

    /**
     * Deletes the building specified by the given ID. Buildings can only be deleted if they are not referenced by
     * a project.
     *
     * @param buildingId The ID of the building that is to be deleted.
     * @return A short message indicating operation success or failure.
     */
    String deleteBuilding(long buildingId);

    /**
     * Persists the given list of instances of class {@link EvaalFile}.
     *
     * @param evaalFiles The list of instances of class {@link EvaalFile} that are to be persisted.
     * @return A short message indicating operation success or failure.
     */
    String saveEvaalFiles(List<EvaalFile> evaalFiles);

    /**
     * Returns the instance of class {@link EvaalFile} whose ID matches the specified ID.
     *
     * @param evaalFileId The ID of the instance of class {@link EvaalFile} that is to be returned.
     * @return The instance of class {@link EvaalFile} or null in case there is no instance with the given
     * ID present in the database.
     */
    EvaalFile getEvaalFileForId(long evaalFileId);

    /**
     * Gives back a list of all instances of class {@link EvaalFile} currently present in the database.
     *
     * @return A list of all instances of class {@link EvaalFile} currently present in the database.
     */
    List<EvaalFile> getAllEvaalFiles();

    /**
     * Returns a list of all instances of class {@link EvaalFile} that currently exist in the database for a specific
     * building and that are marked as evaluation files.
     *
     * @param building The building whose instances of class {@link EvaalFile} marked as evaluation files are to be retrieved.
     * @return A list of all instances of class {@link EvaalFile} that are present for the given building and that are
     * marked as evaluation files. The list will be empty if a) the building does not exist or b) there are no instances
     * of class {@link EvaalFile} marked as evaluation files present in the database for the given building.
     */
    List<EvaalFile> getEvaluationFilesForBuilding(Building building);

    /**
     * Returns a list of all instances of class {@link EvaalFile} that currently exist in the database for a specific
     * building and that are marked as radiomap files.
     *
     * @param building The building whose instances of class {@link EvaalFile} marked as radiomap files are to be retrieved.
     * @return A list of all instances of class {@link EvaalFile} that are present for the given building and that are
     * marked as radiomap files. The list will be empty if a) the building does not exist or b) there are no instances
     * of class {@link EvaalFile} marked as radiomap files present in the database for the given building.
     */
    List<EvaalFile> getRadioMapFilesForBuiling(Building building);

    /**
     * Deletes the instance of class {@link EvaalFile} whose ID matches the given ID. An {@link EvaalFile} can only be
     * deleted if it is not referenced by a instance of class {@link Project}.
     *
     * @param evaalFileId The ID of the instance of class {@link EvaalFile} that is to be deleted.
     * @return A short message indicating operation success or failure.
     */
    String deleteEvaalFile(long evaalFileId);

    /**
     * Creates a new instance of class {@link Project} with the given characteristics and persists it to the database.
     *
     * @param projectName              The name of the project.
     * @param algorithmType            The type of algorithm the position calculation will use. Currently -- as of January 2018 --, the
     *                                 only supported algorithm is the 'WIFI' algorithm.
     * @param saveNewProjectParameters The set of parameters the position calculation will use. In case certain
     *                                 parameters -- or the entire set, for that matter -- are omitted, the backend
     *                                 will fall back to default parameters.
     * @param buildingIdentifier       The ID of the building for which the project is created.
     * @param evalFileIdentifier       The ID of the instance of class {@link EvaalFile} that the project will use as
     *                                 the evaluation file ('learningMode=false' in Mr. Knauths prototype).
     * @param radioMapFileIdentifiers  An array of IDs for instances of class {@link EvaalFile} that the project will use
     *                                 as radiomap files ('learningMode=true' in Mr. Knauths prototype).
     * @return An instance of class {@link GenericResponse} containing the ID of the newly created instance of class
     * {@link Project} as well as a short message describing the operation's result. The ID will be -1 in case the operation
     * was not successful.
     */
    GenericResponse createNewProject(String projectName, String algorithmType, Set<SaveNewProjectParameters> saveNewProjectParameters,
                                     long buildingIdentifier, long evalFileIdentifier, long[] radioMapFileIdentifiers);

    /**
     * Updates the characteristics of the instance of class {@link Project} whose ID corresponds to the provided ID.
     *
     * @param projectId                   The ID of the instance of class {@link Project} that is to be updated.
     * @param newProjectName              The new name of the project.
     * @param newAlgorithmType            The new algorithm type of the project. As of now -- January 2018 --, the only supported
     *                                    type of algorithm is 'WIFI'.
     * @param newSaveNewProjectParameters The new set of parameters for the project.
     * @param buildingIdentifier          The new building ID for the project.
     * @param evalFileIdentifier          The new ID for the instance of class {@link EvaalFile} used by this project as
     *                                    an evaluation file ('learningMode=true' in Mr. Knauths prototype).
     * @param radioMapFileIdentifiers     The new array of IDs identifying all instances of class {@link EvaalFile} that this
     *                                    project will use as radiomap files ('learningMode=true' in Mr. Knauths prototype).
     * @return A short message indicating operation success or failure.
     */
    String updateProject(long projectId, String newProjectName, String newAlgorithmType,
                         Set<SaveNewProjectParameters> newSaveNewProjectParameters, long buildingIdentifier,
                         long evalFileIdentifier, long[] radioMapFileIdentifiers);

    /**
     * Deletes the instance of class {@link Project} whose ID matches the provided ID.
     *
     * @param projectId The ID of the instance of class {@link Project} that is to be deleted.
     * @return A short message indicating operation success or failure.
     */
    String deleteProject(long projectId);

    /**
     * Returns a list of all instances of class {@link Project} currently present in the database.
     *
     * @return A list of all instances of class {@link Project} currently present in the database.
     */
    List<Project> getAllProjects();

    /**
     * Returns the instance of class {@link Project} whose ID matches the given ID.
     *
     * @param projectId The ID of the project that is to be returned.
     * @return The instance of class {@link Project} that matches the provided ID or null in case no such instance was
     * present in the database.
     */
    Project getProjectById(long projectId);


}
