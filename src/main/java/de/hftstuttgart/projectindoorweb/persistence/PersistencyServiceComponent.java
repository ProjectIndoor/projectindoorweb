package de.hftstuttgart.projectindoorweb.persistence;

/**
 * Persistency component class to initialize and dispose of the Persistency service.
 * Do not use the <class>{@link PersistencyServiceImpl}</class> class implementation directly. Only initialize
 * this class once through @initComponent on startup and access the object through the @getRestTransmissionServiceInstance method.
 */
public class PersistencyServiceComponent {
    private static PersistencyService theInstance;

    private PersistencyServiceComponent() {
    }

    public static void initComponent() {
        RepositoryRegistry.initRepositoryMap();
        theInstance = new PersistencyServiceImpl();
    }

    public static void disposeComponent() {

        if (theInstance != null) {
            theInstance = null;
        }
        RepositoryRegistry.disposeRepositories();

    }

    public static PersistencyService getPersistencyService() {

        if (theInstance != null) {
            return theInstance;

        }

        throw new IllegalStateException("Persistency service was not initialized. Please call 'initComponent()' first.");
    }
}
