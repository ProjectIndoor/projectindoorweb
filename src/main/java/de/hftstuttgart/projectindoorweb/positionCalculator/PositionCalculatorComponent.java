package de.hftstuttgart.projectindoorweb.positionCalculator;


/**
 * Position calculator component class to initialize and dispose of the Calculator service.
 * Do not use the <class>{@link WifiPositionCalculatorServiceImpl}</class> class implementation directly. Only initialize
 * this class once through @initComponent on startup and access the object through the @getRestTransmissionServiceInstance method.
 */
public class PositionCalculatorComponent {

    private static PositionCalculatorService theInstance;

    private PositionCalculatorComponent() {
    }


    public static void initComponent() {

        theInstance = new WifiPositionCalculatorServiceImpl();

    }

    public static void disposeComponent() {

        if (theInstance != null) {
            theInstance = null;
        }

    }

    public static PositionCalculatorService getPositionCalculator() {

        if (theInstance != null) {
            return theInstance;

        }

        throw new IllegalStateException("PositionCalculatorService was not initialized yet. Please call 'initComponent()' first.");
    }

}
