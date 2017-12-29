package de.hftstuttgart.projectindoorweb.web.internal.util;

public class ResponseConstants {

    /*General*/
    public static final String GENERAL_FAILURE_UNPARSABLE_NUMBER = "Unable to perform operation: Given input could not be " +
            "parsed as a number.";

    /*Buildings and floors*/
    public static final String BUILDING_GENERAL_FAILURE_ID_NOT_FOUND = "Unable to perform operation on building: Building with given ID does not exist.";
    public static final String BUILDING_GENERAL_FAILURE_NO_DATA_RECEIVED = "Unable to perform operation on building: Received no data.";

    public static final String BUILDING_CREATION_SUCCESS = "Building created successfully.";
    public static final String BUILDING_CREATION_FAILURE = "Unable to create building: Database returned no entity.";
    public static final String BUILDING_UPDATE_SUCCESS = "Building updated successfully.";
    public static final String BUILDING_UPDATE_FAILURE_DB_WRITE = "Unable to update building: Could not write entity to database.";

    public static final String FLOOR_UPDATE_FAILURE_INVALID_DATA_RECEIVED = "Unable to update building floor: Received invalid or incomplete data.";
    public static final String FLOOR_UPDATE_FAILURE_ID_NOT_FOUND = "Unable to update building floor: The given floor ID does not exist within the specified building.";
    public static final String FLOOR_UPDATE_SUCCESS = "Building floor updated successfully.";
    public static final String FLOOR_UPDATE_FAILURE_LOCAL_WRITE = "Unable to update building floor: Could not save image data to local file system.";
    public static final String FLOOR_UPDATE_FAILURE_DB_WRITE = "Unable to update building floor: Could not write entity to database.";

    public static final String BUILDING_DELETE_SUCCESS = "Building deleted successfully.";
    public static final String BUILDING_DELETE_FAILURE_DB_WRITE = "Unable to delete building: Could not write changes to database.";
    public static final String BUILDING_DELETE_FAILURE_CONSTRAINT_VIOLATION = "Unable to delete building: The given building is referenced by one or multiple projects.";

    /*Projects*/
    public static final String PROJECT_GENERAL_FAILURE_NO_DATA_RECEIVED = "Unable to perform operation on project: No data received.";

    public static final String PROJECT_CREATION_SUCCESS = "Project created successfully.";
    public static final String PROJECT_CREATION_FAILURE = "Unable to create project: Database returned no entity.";
    public static final String PROJECT_CREATION_FAILURE_ALGORITHM_NULL = "Unable to create project: Unspecified calculation algorithm.";

    public static final String PROJECT_UPDATE_FAILURE_INVALID_DATA = "Unable to update project: Invalid or incomplete data received.";
    public static final String PROJECT_UPDATE_FAILURE_ALGORITHM_NULL = "Unable to update project: Unspecified calculation algorithm.";
    public static final String PROJECT_UPDATE_FAILURE_ID_NOT_FOUND = "Unable to update project: The project with the given ID does not exist.";
    public static final String PROJECT_UPDATE_SUCCESS = "Project updated successfully.";
    public static final String PROJECT_UPDATE_FAILURE_DB_WRITE = "Unable to update project: Could not write entity to database.";

    public static final String PROJECT_DELETE_SUCCESS = "Project deleted successfully.";
    public static final String PROJECT_DELETE_FAILURE_DB_WRITE = "Unable to delete building: Could not write changes to database.";

    /*EvAAL files*/
    public static final String EVAAL_GENERAL_FAILURE_NO_DATA_RECEIVED = "Unable to perform operation on EvAAL file: Received no data.";

    public static final String EVAAL_PROCESSING_GENERAL_FAILURE_RADIOMAPS_NULL = "Unable to process EvAAL files: List of radio map files was unspecified.";
    public static final String EVAAL_PROCESSING_SUCCESS = "EvAAL files processed successfully.";
    public static final String EVAAL_PROCESSING_FAILURE_FILE_LIST_LENGTH_UNEQUAL = "Unable to process EvAAL files: The number of given radio map files does not equal the number of files containing transformed points.";

    public static final String EVAAL_SAVE_FAILURE_DB_WRITE = "Unable to process EvAAL files: Could not write result entities to database.";

    public static final String EVAAL_DELETE_SUCCESS = "EvAAL file entries deleted successfully.";
    public static final String EVAAL_DELETE_FAILURE_DB_WRITE = "Unable to delete EvAAL file entries: Could not write changes to database.";
    public static final String EVAAL_DELETE_FAILURE_CONSTRAINT_VIOLATION = "Unable to delete EvAAL file entries: The given EvAAL file entries are referenced by one or multiple projects.";


}
