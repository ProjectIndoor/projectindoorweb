package de.hftstuttgart.projectindoorweb.persistence;

public class PersistencyServiceComponent {
    private static PersistencyService theInstance;

    private PersistencyServiceComponent(){}

    public static void initComponent(){

        theInstance = new PersistencyServiceImpl();
    }

    public static void disposeComponent(){

        if(theInstance != null){
            theInstance = null;
        }

    }

    public static PersistencyService getPositionCalculator(){

        if(theInstance != null){
            return theInstance;

        }

        throw new IllegalStateException("Persistency service was not initialized yet. Please call 'initComponent()' first." );
    }
}
