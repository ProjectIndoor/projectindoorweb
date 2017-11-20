package de.hftstuttgart.projectindoorweb.positionCalculator;

import de.hftstuttgart.projectindoorweb.persistence.entities.PositionResult;
import de.hftstuttgart.projectindoorweb.persistence.entities.RadioMap;

import java.io.File;
import java.util.List;

public interface PositionCalculatorService {

    //This must be a list of RadioMaps!
    List<? extends PositionResult> calculatePositions(File evalFile, List<RadioMap> radioMaps);

}
