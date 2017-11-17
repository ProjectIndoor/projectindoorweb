package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.inputHandler.InputHandler;
import de.hftstuttgart.projectindoorweb.web.internal.CalculatedPosition;
import de.hftstuttgart.projectindoorweb.web.internal.ProjectElement;
import de.hftstuttgart.projectindoorweb.web.internal.ProjectParameter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestTransmissionServiceImpl implements RestTransmissionService{

    private InputHandler inputHandler;

    public RestTransmissionServiceImpl(InputHandler inputHandler){
        this.inputHandler=inputHandler;
    }

    @Override
    public boolean generateRadioMap(List<File> radioMapFiles) {
        File[] radioMapFileArray = radioMapFiles.toArray(new File[radioMapFiles.size()]);
        return inputHandler.handleInput(true, radioMapFileArray);//TODO call correct method as soon as available
    }

    @Override
    public boolean generatePositionResults(List<File> evaluationFiles) {
        File[] evaluationFileArray = evaluationFiles.toArray(new File[evaluationFiles.size()]);
        return inputHandler.handleInput(false, evaluationFileArray);//TODO call correct method as soon as available
    }

    @Override
    public CalculatedPosition getPositionForWifiReading(String wifiReading) {
        return new CalculatedPosition(0,0,0,false,""); //TODO implement when ready
    }

    @Override
    public List<CalculatedPosition> getPositionResultsForIdentifier(String positionIdentifier) {
        List<CalculatedPosition> result = new ArrayList<>();//TODO implement when ready
        return result;
    }

    @Override
    public boolean saveNewProject(Set<ProjectParameter> projectParameterSet, String projectName) {
        return false;//TODO implement when ready
    }

    @Override
    public boolean saveCurrentProject(Set<ProjectParameter> projectParameterSet, String projectIdentifier) {
        return false;//TODO implement when ready
    }

    @Override
    public boolean deleteSelectedProject(String projectIdentifier) {
        return false;//TODO implement when ready
    }

    @Override
    public ProjectElement loadSelectedProject(String projectIdentifier) {
        return new ProjectElement("","",new HashSet<>());//TODO implement when ready
    }
}
