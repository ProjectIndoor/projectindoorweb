package de.hftstuttgart.projectindoorweb.inputHandler.internal.util;

import de.hftstuttgart.projectindoorweb.positionCalculator.internal.CorrelationMode;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LatLongCoord;

public class ConfigContainer {

    public static final double FLOOR_HEIGHT = 1.0;
    public static final double SIMILAR_POSITION_THRESHOLD_METERS = 0.7;
    public static final double WIFI_POSITION_SMOOTHENER = 0.2;
    public static final double WEIGHT_RESULT_1 = 2;
    public static final double WEIGHT_RESULT_2 = 0.9;
    public static final double WEIGHT_RESULT_3 = 0.9;
    public static final double POSI_REFERENCE_MAX_TIME_DELTA_MILLIS = 3000;
    public static final double LOWEST_RSSI_IN_SCALAR_MODE = -95.0;
    public static final double RADIO_PROPAGATION_EXPONENT = 4.0;

    public static final int POSI_MAX_INTERVAL_END = Integer.MAX_VALUE;
    public static final int NUM_REFERENCES_IN_WEIGHTED_MODE = 3;

    public static final boolean MERGE_RADIOMAP_ELEMENTS = true;
    public static final boolean USE_FIXED_WEIGHTS = true;
    public static final boolean SMOOTHEN_WIFI_POSITIONS = true;
    public static final boolean USE_SHIFTED_POSI_REFERENCES = false;

    public static final CorrelationMode CORRELATION_MODE = CorrelationMode.EUCLIDIAN;




}
