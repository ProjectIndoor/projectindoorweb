package de.hftstuttgart.projectindoorweb.algorithm;

import de.hftstuttgart.projectindoorweb.persistence.entities.PositionResult;
import de.hftstuttgart.projectindoorweb.persistence.entities.Project;
import de.hftstuttgart.projectindoorweb.persistence.entities.RadioMap;

import java.io.File;
import java.util.List;

public interface AlgorithmHandler {

    //This must be a list of RadioMaps!
    public List<? extends PositionResult> calculatePositions(File evalFile, List<RadioMap> radioMaps);

}
