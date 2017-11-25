package de.hftstuttgart.projectindoorweb.web.internal;

public class TransmissionConstants { //TODO move to config container file and move somewhere more viewable

    public static final String EMPTY_STRING_VALUE = "";
    public static final String FALSE_STRING_VALUE = "false";

    public static final String WIFI_READING_PARAM = "wifiReading";

    public static final String POSITION_IDENTIFIER_PARAM = "positionIdentifier";
    public static final String PROJECT_IDENTIFIER_PARAM = "projectIdentifier";

    public static final String PROJECT_NAME_PARAM = "projectName";
    public static final String ALGORITHM_TYPE_PARAM = "algorithmType";
    public static final String ALGORITHM_IDENTIFIER_PARAM = "algorithmIdentifier";

    public static final String BUILDING_IDENTIFIER_PARAM = "buildingIdentifier";
    public static final String BUILDING_NAME_PARAM = "buildingName";
    public static final String NUMBER_OF_FLOORS_PARAM = "numberOfFloors";

    public static final String RADIOMAP_FILES_PARAM = "radioMapFiles";
    public static final String WITH_PIXEL_POSITION_PARAM = "withPixelPosition";

    public static final String SAVE_NEW_PROJECT_NOTE = "Saves a new project in the database and returns its unique identifier. Returns -1 if save was not successful or given data was invalid.";
    public static final String SAVE_CURRENT_PROJECT_NOTE = "Overrides a given project with the given project based on the given project identifier. Returns true if successful, otherwise false.";
    public static final String DELETE_PROJECT_NOTE = "Removes a given project from the database, based on the given project identifier. Returns true if successful, otherwise false.";
    public static final String LOAD_PROJECT_NOTE = "Loads and returns a wanted project, based on the given identifier. Returns an empty project if an error occurred or the given data was invalid.";
    public static final String GET_ALL_PROJECT_NOTE = "Returns all current projects in the database in a list or an empty list if no projects are available.";
    public static final String GET_ALL_ALGORITHMS_NOTE = "Returns all current algorithm types in a list that are currently available or an empty list if none are available.";
    public static final String GET_PARAMETERS_FOR_ALGORITHM_NOTE = "Returns a list of parameters which are currently associated with the given algorithm identifier. Returns an empty list if nothing has been found or if the given data is invalid.";

    public static final String GENERATE_RADIOMAPS_NOTE = "Generates a radio map. Returns true if generation was successful, otherwise false.";
    public static final String GENERATE_POSITIONRESULTS_NOTE = "Generates and returns position results. Returns a list of generated position results or an empty list if generation was not successful or given parameters were invalid.";
    public static final String GET_ALL_BUILDINGS_NOTE = "Returns a list of buildings or an empty list if no buildings are available.";
    public static final String CALCULATE_POSITION_NOTE = "Calculates and returns a position for a wifi reading line. Returns an empty position if given data was invalid.";
    public static final String GET_POSITIONRESULTS_NOTE = "Returns a list of position results which are associated with a given project identifier.";
    public static final String GET_EVALUATIONENTRIES_NOTE = "Returns a list of evaluation entries which are associated with a given building identifier.";
    public static final String ADD_NEW_BUILDING_NOTE = "Adds a new building to the database and returns its unique identifier or -1 if given data was invalid.";

}
