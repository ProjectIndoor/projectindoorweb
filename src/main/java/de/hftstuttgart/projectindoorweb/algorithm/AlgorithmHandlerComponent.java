package de.hftstuttgart.projectindoorweb.algorithm;

public class AlgorithmHandlerComponent {

    private static AlgorithmHandler theInstance;

    private AlgorithmHandlerComponent(){}


    public static void initComponent(){

        theInstance = new WifiAlgorithmHandler();

    }

    public static void disposeComponent(){

        if(theInstance != null){
            theInstance = null;
        }

    }

    public static AlgorithmHandler getAlgorithmHandler(){

        if(theInstance != null){
            return theInstance;

        }

        throw new IllegalStateException("AlgorithmHandler was not initialized yet. Please call 'initComponent()' first." );
    }

}
