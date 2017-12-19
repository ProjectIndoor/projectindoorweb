package de.hftstuttgart.projectindoorweb.positionCalculator;

import de.hftstuttgart.projectindoorweb.persistence.entities.Building;
import de.hftstuttgart.projectindoorweb.persistence.entities.EvaalFile;
import de.hftstuttgart.projectindoorweb.persistence.entities.PositionResult;
import de.hftstuttgart.projectindoorweb.persistence.entities.Project;

import java.util.List;

public interface PositionCalculatorService {

    List<? extends PositionResult> calculatePositions(Building building, Project project, EvaalFile evaluationFile, EvaalFile[] radioMapFiles);

    <T extends PositionResult> T calculateSinglePosition(Building building, Project project, String[] wifiReadings, EvaalFile[] radioMapFiles);

}
