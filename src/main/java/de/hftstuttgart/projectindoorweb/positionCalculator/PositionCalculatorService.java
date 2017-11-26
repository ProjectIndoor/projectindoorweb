package de.hftstuttgart.projectindoorweb.positionCalculator;

import de.hftstuttgart.projectindoorweb.persistence.entities.LogFile;
import de.hftstuttgart.projectindoorweb.persistence.entities.PositionResult;
import de.hftstuttgart.projectindoorweb.persistence.entities.Project;
import de.hftstuttgart.projectindoorweb.persistence.entities.RadioMap;

import java.io.File;
import java.util.List;

public interface PositionCalculatorService {

    List<? extends PositionResult> calculatePositions(File evalFile, Project project);

    <T extends PositionResult> T calculateSinglePosition(String wifiReading, Project project);

}
