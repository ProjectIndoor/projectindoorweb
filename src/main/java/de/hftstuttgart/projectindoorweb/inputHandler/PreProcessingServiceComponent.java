package de.hftstuttgart.projectindoorweb.inputHandler;

/**
 * Input handler component class to initialize and dispose of the Eva File input handler.
 * Do not use the <class>{@link EvaalFilePreProcessingServiceImpl}</class> class implementation directly. Only initialize
 * this class once through @initComponent on startup and access the object through the @getPreProcessingService method.
 */
public class PreProcessingServiceComponent {

    private static PreProcessingService instance;

    public static void initComponent() {
        instance = new EvaalFilePreProcessingServiceImpl();
    }

    public static void disposeComponent() {
        if (instance != null) {
            instance = null;
        } else {
            System.out.print("Preprocessing Service was not initialised when trying to dispose component.");
        }

    }

    public static PreProcessingService getPreProcessingService() {
        if (instance != null) {
            return instance;
        }

        throw new IllegalStateException("Preprocessing Service was not initialized yet, please call 'initComponent()' first.");
    }
}
