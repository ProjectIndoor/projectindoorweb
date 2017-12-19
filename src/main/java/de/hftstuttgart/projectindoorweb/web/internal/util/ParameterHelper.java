package de.hftstuttgart.projectindoorweb.web.internal.util;

import de.hftstuttgart.projectindoorweb.web.internal.requests.project.GetAlgorithmParameters;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ParameterHelper {

    private static ParameterHelper theInstance;

    private Map<String, GetAlgorithmParameters> parametersMap;

    private ParameterHelper() {
    }

    public static ParameterHelper getInstance() {
        if (theInstance == null) {
            theInstance = new ParameterHelper();
            theInstance.parametersMap = theInstance.getPopulatedParameterList();
        }
        return theInstance;
    }


    public Map<String, GetAlgorithmParameters> getPopulatedParameterList() {

        Map<String, GetAlgorithmParameters> parametersMap = new LinkedHashMap<>();

        String veryMeaningfulDescription =
                "An incredibly meaningful descriptipn will be added eventually.";


        String internalName = "mergeRadioMaps";
        String prettyName = "Merge Radio Maps";
        String defaultValue = "true";
        String valueDataType = Boolean.class.getSimpleName();
        String description = veryMeaningfulDescription;
        String[] applicableForAlgorithms = {"WIFI"};
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "floorHeight";
        prettyName = "Floor Height";
        defaultValue = "1.0";
        valueDataType = Double.class.getSimpleName();
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "positionSimilarityThreshold";
        prettyName = "Position Similarity Distance Threshold";
        defaultValue = "0.7";
        valueDataType = Double.class.getSimpleName();
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "smoothenWifiPositions";
        prettyName = "Smoothen WIFI positions";
        defaultValue = "true";
        valueDataType = Boolean.class.getSimpleName();
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "wifiPositionSmootheningFactor";
        prettyName = "Factor for WIFI position smoothening";
        defaultValue = "0.2";
        valueDataType = Double.class.getSimpleName();
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "weightedModeNumReferences";
        prettyName = "Number of References in Weighted Mode";
        defaultValue = "3";
        valueDataType = Integer.class.getSimpleName();
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "useFixedWeights";
        prettyName = "Use Fixed Weights";
        defaultValue = "true";
        valueDataType = Boolean.class.getSimpleName();
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "weightResult1";
        prettyName = "Weight for First Result in Weighted Mode";
        defaultValue = "2";
        valueDataType = Double.class.getSimpleName();
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "weightResult2";
        prettyName = "Weight for Second Result in Weighted Mode";
        defaultValue = "0.9";
        valueDataType = Double.class.getSimpleName();
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "weightResult3";
        prettyName = "Weight for Third Result in Weighted Mode";
        defaultValue = "0.9";
        valueDataType = Double.class.getSimpleName();
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "correlationMode";
        prettyName = "Correlation Mode";
        defaultValue = "euclidian";
        valueDataType = String.class.getSimpleName();
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "posiReferenceSimilarityTimeDelta";
        prettyName = "Maximum Time Delta for POSI Reference Assignment in Milliseconds";
        defaultValue = "3000";
        valueDataType = Long.class.getSimpleName();
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "useShiftedPosiReferences";
        prettyName = "Use Shifted Timestamps for POSI Reference Assignment";
        defaultValue = "false";
        valueDataType = Boolean.class.getSimpleName();
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        return parametersMap;

    }


    public List<GetAlgorithmParameters> getAllParameters() {
        return new ArrayList<>(this.parametersMap.values());
    }

    public List<GetAlgorithmParameters> getParametersForAlgorithmType(String algorithmType) {

        List<GetAlgorithmParameters> result = new ArrayList<>();

        for (String key :
                this.parametersMap.keySet()) {
            if (isParameterApplicableToAlgorithm(algorithmType, this.parametersMap.get(key))) {
                result.add(this.parametersMap.get(key));
            }
        }

        return result;

    }

    public GetAlgorithmParameters getParameterByInternalName(String internalName) {

        return this.parametersMap.get(internalName);

    }

    private boolean isParameterApplicableToAlgorithm(String algorithmName, GetAlgorithmParameters getAlgorithmParameters) {

        String[] algorithms = getAlgorithmParameters.getApplicableForAlgorithms();
        for (String algorithm :
                algorithms) {
            if (algorithm.equalsIgnoreCase(algorithmName)) {
                return true;
            }
        }

        return false;
    }


}
