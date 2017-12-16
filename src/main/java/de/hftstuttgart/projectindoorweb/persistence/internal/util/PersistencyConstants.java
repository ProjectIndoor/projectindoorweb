package de.hftstuttgart.projectindoorweb.persistence.internal.util;

import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;

public class PersistencyConstants {

    public static final String LOCAL_RESOURCE_DIR = "./src/main/resources/static";
    public static final String LOCAL_MAPS_DIR = "floor_maps";
    public static final OpenOption[] FILE_OPEN_OPTIONS = new StandardOpenOption[]{StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE};
    public static final String IMAGE_TARGET_FILE_ENDING = "jpg";
}
