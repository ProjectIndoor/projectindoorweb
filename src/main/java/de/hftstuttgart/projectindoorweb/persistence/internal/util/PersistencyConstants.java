package de.hftstuttgart.projectindoorweb.persistence.internal.util;

import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;

public class PersistencyConstants {

    public static final String LOCAL_RESOURCE_DIR = String.format("%s/.projectindoor", System.getProperty("user.home"));
    public static final String LOCAL_MAPS_DIR = "floor_maps";
    public static final String IMAGE_TARGET_FILE_ENDING = "png";
}
