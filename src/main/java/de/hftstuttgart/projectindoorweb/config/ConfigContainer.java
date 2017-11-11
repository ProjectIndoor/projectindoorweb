package de.hftstuttgart.projectindoorweb.config;

import de.hftstuttgart.projectindoorweb.maths.LatLongCoord;

public class ConfigContainer {

    public static final String RADIO_MAP_FILE_PATH = "./radiomapfiles";
    public static final String RADIO_MAP_TEST_FILE_PATH = "./radiomapfiles";
    public static final String EVALUATION_FILE_PATH = "./evaluationfiles";

    public static final LatLongCoord BASE_POSITION = new LatLongCoord(40.31320308, -3.48367648);

    public static final double ANGLE_RAD = 0.15318405778903832;
    public static final double FLOOR_HEIGHT = 1.0;
    public static final double SIMILAR_POSITION_THRESHOLD_METERS = 0.7;

    public static final int POSI_MAX_INTERVAL_END = Integer.MAX_VALUE;

    public static final long PARSERS_TERMINATION_TIMEOUT_MILLIS = 15000;

    public static final boolean MERGE_RADIOMAP_ELEMENTS = true;

}
