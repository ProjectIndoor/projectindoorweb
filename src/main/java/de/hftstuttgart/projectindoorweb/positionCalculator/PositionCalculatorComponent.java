package de.hftstuttgart.projectindoorweb.positionCalculator;

public class PositionCalculatorComponent {

    private static PositionCalculatorService theInstance;

    private PositionCalculatorComponent(){}


    public static void initComponent(){

        theInstance = new WifiPositionCalculatorServiceImpl();

    }

    public static void disposeComponent(){

        if(theInstance != null){
            theInstance = null;
        }

    }

    public static PositionCalculatorService getPositionCalculator(){

        if(theInstance != null){
            return theInstance;

        }

        throw new IllegalStateException("PositionCalculatorService was not initialized yet. Please call 'initComponent()' first." );
    }

}
