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

        String internalName = "mergeRadioMaps";
        String prettyName = "Merge Radio Maps";
        String defaultValue = "true";
        String valueDataType = Boolean.class.getSimpleName();
        String description = "Decides whether or not to merge multiple given radio maps into a single radio map in the process " +
                "of calculating position results. It is generally recommended to merge multiple radio maps into one, hence the default " +
                "value of this parameter is 'true'.";
        String[] applicableForAlgorithms = {"WIFI"};
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "floorHeight";
        prettyName = "Floor Height";
        defaultValue = "1.0";
        valueDataType = Double.class.getSimpleName();
        description = "Specifies the height of a floor. The number is not given in meters, but in a more abstract unit where " +
                "'1.0' can be seen as the standard height of a floor (which is why it is the default value). The parameter is used " +
                "exclusively during transformation of a coordinate given as Latitude and Longitude to a local coordinate system where " +
                "the floor height multiplied by the current floor (such as '2') will be the Z value of the local coordinate.";
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "positionSimilarityThreshold";
        prettyName = "Position Similarity Distance Threshold";
        defaultValue = "0.7";
        valueDataType = Double.class.getSimpleName();
        description = "This parameter describes the maximum distance in meters for which two POSI references will be " +
                "considered similar in terms of their positions. The resulting definition of POSI reference similarity " +
                "is used during the process of merging together multiple radio maps into one radio map. The parameter " +
                "value defaults to '0.7'.";
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "smoothenWifiPositions";
        prettyName = "Smoothen WIFI positions";
        defaultValue = "true";
        valueDataType = Boolean.class.getSimpleName();
        description = "Parameter that enables (in case it is set to 'true') or disables (in case it is set to... make an " +
                "educated guess) the smoothening of two calculated WIFI positions. In case it is enabled, the backend will " +
                "take two calculated positions, multiply them by the factor specified by parameter 'Factor for WIFI Position " +
                "Smoothening', and add them to one another, which will yield one new position. In case smoothening is " +
                "disabled, the backend will take calculated positions as is and return them to the client.";
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "wifiPositionSmootheningFactor";
        prettyName = "Factor for WIFI Position Smoothening";
        defaultValue = "0.2";
        valueDataType = Double.class.getSimpleName();
        description = "The factor that controls the behaviour of WIFI position smoothening. The factor is applied in the " +
                "following way: The first calculated position is multiplied by the factor itself, whereas the second calculated " +
                "position is multiplied by '1 - factor'. The two positions are then merged by adding them to one another.";
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "weightedModeNumReferences";
        prettyName = "Number of Calculated Positions to Take";
        defaultValue = "3";
        valueDataType = Integer.class.getSimpleName();
        description = "For each block of WIFI lines, the backend will calculate a list of positions using a given radio map. " +
                "Each calculated position has a certain weight attached to it (which is calculated either by Euclidian or " +
                "Scalar correlation mode). The number of calculated positions specified by the given parameter is used to tell " +
                "the backend how many of these calculated positions it should take into account. The parameter is only used " +
                "in case 'useFixedWeights' is set to 'false'. In case it is set to 'true', the number of calculated positions " +
                "taken from the list is always 3.";
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "useFixedWeights";
        prettyName = "Use Fixed Weights";
        defaultValue = "true";
        valueDataType = Boolean.class.getSimpleName();
        description = "Enables or disables usage of fixed weights if set to 'true' or to 'false', respectively. Using " +
                "fixed weights means that only the top three of the positions calculated for one WIFI block -- the ones " +
                "with the highest weights -- will be retained and that the calculated weights of these three positions " +
                "will be overridden by the fixed weights given by parameters 'Weight for First/Second/Third Result in " +
                "Weighted Mode' after having taken them from the list.";
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "weightResult1";
        prettyName = "Weight for First Result in Fixed Weights Mode";
        defaultValue = "2";
        valueDataType = Double.class.getSimpleName();
        description = "The fixed weight to be associated with the position having the highest calculated weight appearing " +
                "at first index of the list of all calculated positions. This parameter is only used in case 'Use Fixed Weights' " +
                "is set to 'true'.";
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "weightResult2";
        prettyName = "Weight for Second Result in Fixed Weights Mode";
        defaultValue = "0.9";
        valueDataType = Double.class.getSimpleName();
        description = "The fixed weight to be associated with the position having the second-highest calculated weight appearing " +
                "at second index of the list of all calculated positions. This parameter is only used in case 'Use Fixed Weights' " +
                "is set to 'true'.";
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "weightResult3";
        prettyName = "Weight for Third Result in Fixed Weights Mode";
        defaultValue = "0.9";
        valueDataType = Double.class.getSimpleName();
        description = "The fixed weight to be associated with the position having the third-highest calculated weight appearing " +
                "at third index of the list of all calculated positions. This parameter is only used in case 'Use Fixed Weights' " +
                "is set to 'true'.";
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "correlationMode";
        prettyName = "Correlation Mode";
        defaultValue = "scalar";
        valueDataType = String.class.getSimpleName();
        description = "Sets the mode of correlation of RSSI signals from a WIFI block on one hand and from a radio map on the " +
                "other in order to calculate a position weight to either 'euclidian' or 'scalar'.";
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "posiReferenceSimilarityTimeDelta";
        prettyName = "Maximum Time Delta for POSI Reference Assignment";
        defaultValue = "2000";
        valueDataType = Long.class.getSimpleName();
        description = "Each calculated position will be assigned a reference position (taken from a POSI reference) if it is " +
                "possible; that is, if the evaluation file also contains POSI references along with its WIFI blocks. The given " +
                "parameter sets the maximum time delta (in Milliseconds) a calculated position and a POSI reference can " +
                "have. If no POSI reference is available for which the actual time delta is lower or equal than the configured " +
                "time delta, the calculated position will not be assigned a POSI reference, thus will not have a reference position " +
                "available. Because a POSI reference is needed for this process, it is only available for evaluation files " +
                "with POSI references.";
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "useShiftedPosiReferences";
        prettyName = "Use Shifted Timestamps for POSI Reference Assignment";
        defaultValue = "false";
        valueDataType = Boolean.class.getSimpleName();
        description = "The process of calculating time deltas between POSI references and calculated positions for assigning " +
                "one of the former to one of the latter can either be carried out using the shifted timestamps of the POSI references " +
                "or their original ones. The original timestamps are taken directly from the source EvAAL file, whereas the " +
                "shifted timestamps are the result of a shifting operation executed during the process of generating the radio map " +
                "out of a given EvAAL file.";
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "lowestConsideredRssiValue";
        prettyName = "Lowest Considered RSSI Value";
        defaultValue = "-95.0";
        valueDataType = Double.class.getSimpleName();
        description = "The lowest RSSI value to be considered during the weight calculation of a single RSSI signal. " +
                "This parameter only applies to Scalar correlation mode.";
        parametersMap.put(internalName, new GetAlgorithmParameters(internalName, prettyName,
                defaultValue, valueDataType, description, applicableForAlgorithms));

        internalName = "radioPropagationExponent";
        prettyName = "Radio Propagation Exponend";
        defaultValue = "4.0";
        valueDataType = Double.class.getSimpleName();
        description = "The radio propagation exponent used during calculation of the weight of a single RSSI signal. " +
                "This parameter only applies to Scalar correlation mode.";
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
