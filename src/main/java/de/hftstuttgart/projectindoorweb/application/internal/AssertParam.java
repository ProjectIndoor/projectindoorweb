package de.hftstuttgart.projectindoorweb.application.internal;

public class AssertParam {

    public static void throwIfNull(Object field, String message) {
        if (field == null) {
            throw new NullPointerException(message);
        }
    }

    public static boolean isNullOrEmpty(String value) {
        if (value == null || value.isEmpty() || value.matches("^\\s*$")) {
            return true;
        }
        return false;
    }

    public static void throwIfNullOrEmpty(String field, String message) {
        if (isNullOrEmpty(field)) {
            throw new NullPointerException(message);
        }
    }
}
