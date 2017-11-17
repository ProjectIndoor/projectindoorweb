package de.hftstuttgart.projectindoorweb.web;


import de.hftstuttgart.projectindoorweb.web.internal.CalculatedPosition;
import de.hftstuttgart.projectindoorweb.web.internal.ProjectElement;
import de.hftstuttgart.projectindoorweb.web.internal.ProjectParameter;

import java.io.File;
import java.util.List;
import java.util.Set;

public interface RestTransmissionService { //If too many methods get introduced, split this interface up into multiple services!

    boolean generateRadioMap(List<File> radioMapFiles);

    boolean generatePositionResults(List<File> evaluationFiles);

    CalculatedPosition getPositionForWifiReading(String wifiReading);

    List<CalculatedPosition> getPositionResultsForIdentifier(String positionIdentifier);

    boolean saveNewProject( Set<ProjectParameter>  projectParameterSet, String projectName);//TODO add algorithm type

    boolean saveCurrentProject( Set<ProjectParameter> projectParameterSet, String projectIdentifier);//TODO add algorithm type

    boolean deleteSelectedProject( String projectIdentifier);

    ProjectElement loadSelectedProject(String projectIdentifier);
}
