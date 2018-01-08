package de.hftstuttgart.projectindoorweb.positionCalculator;

import de.hftstuttgart.projectindoorweb.persistence.entities.Building;
import de.hftstuttgart.projectindoorweb.persistence.entities.EvaalFile;
import de.hftstuttgart.projectindoorweb.persistence.entities.PositionResult;
import de.hftstuttgart.projectindoorweb.persistence.entities.Project;

import java.util.List;

public interface PositionCalculatorService {

    /***
     * Calculates a list of positions based on the given data.
     *
     * @param building The building for which the position calculation is to be carried out.
     * @param project The project whose settings and parameters are to be used for the position calculation.
     * @param evaluationFile The instance of class {@link EvaalFile} the position calculation will use as the evaluation file.
     * @param radioMapFiles An array of {@link EvaalFile} instances the position calculation will use as radiomap files.
     *
     * @return A list of calculated positions in the form of instances of any class that is a subclass of {@link PositionResult}.
     */
    List<? extends PositionResult> calculatePositions(Building building, Project project, EvaalFile evaluationFile, EvaalFile[] radioMapFiles);

    /**
     * Calculates a single position based on the given data.
     *
     * @param building      The building for which the calculation is to be carried out.
     * @param project       The project whose settings and parameters will be used for the position calculation.
     * @param wifiReadings  An array of WIFI readings representing a single WIFI block.
     * @param radioMapFiles An array containing the instances of class {@link EvaalFile} the position calculation will
     *                      use as radiomap files.
     * @param <T>           A subclass of {@link PositionResult}.
     * @return A calculated position in the form of an instance of any class that is a subclass of {@link PositionResult}.
     */
    <T extends PositionResult> T calculateSinglePosition(Building building, Project project, String[] wifiReadings, EvaalFile[] radioMapFiles);

}
