package de.hftstuttgart.projectindoorweb.web;

import de.hftstuttgart.projectindoorweb.application.internal.AssertParam;
import de.hftstuttgart.projectindoorweb.inputHandler.InputHandler;
import de.hftstuttgart.projectindoorweb.web.internal.CalculatedPosition;
import de.hftstuttgart.projectindoorweb.web.internal.ProjectElement;
import de.hftstuttgart.projectindoorweb.web.internal.ProjectParameter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestTransmissionServiceImpl implements RestTransmissionService {

    private InputHandler inputHandler;

    public RestTransmissionServiceImpl(InputHandler inputHandler){
        this.inputHandler=inputHandler;
    }

    @Override
    public boolean generateRadioMap(List<File> radioMapFiles) {

        if(radioMapFiles==null || radioMapFiles.size()==0){
            return false;
        }

        File[] radioMapFileArray = radioMapFiles.toArray(new File[radioMapFiles.size()]);
        return inputHandler.handleInput(true, radioMapFileArray);//TODO call correct method as soon as available
    }

    @Override
    public boolean generatePositionResults(List<File> evaluationFiles) {

        if(evaluationFiles==null || evaluationFiles.size()==0){
            return false;
        }

        File[] evaluationFileArray = evaluationFiles.toArray(new File[evaluationFiles.size()]);
        return inputHandler.handleInput(false, evaluationFileArray);//TODO call correct method as soon as available
    }

    @Override
    public CalculatedPosition getPositionForWifiReading(String wifiReading) {

        if(AssertParam.isNullOrEmpty(wifiReading)){
            return createEmptyCalculatedPosition();
        }

        return new CalculatedPosition(0,0,0,false,""); //TODO implement when ready
    }

    @Override
    public List<CalculatedPosition> getPositionResultsForIdentifier(String positionIdentifier) {

        List<CalculatedPosition> result = new ArrayList<>();

        if(AssertParam.isNullOrEmpty(positionIdentifier)){
            return result;
        }

        return result;//TODO implement when ready
    }

    @Override
    public boolean saveNewProject(Set<ProjectParameter> projectParameterSet, String projectName, String algorithmType) {

        if(projectParameterSet==null || projectParameterSet.size()==0||AssertParam.isNullOrEmpty(projectName)||AssertParam.isNullOrEmpty(algorithmType)){
            return false;
        }

        return false;//TODO implement when ready
    }

    @Override
    public boolean saveCurrentProject(Set<ProjectParameter> projectParameterSet, String projectIdentifier, String algorithmType) {

        if(projectParameterSet==null || projectParameterSet.size()==0||AssertParam.isNullOrEmpty(projectIdentifier)||AssertParam.isNullOrEmpty(algorithmType)){
            return false;
        }

        return false;//TODO implement when ready
    }

    @Override
    public boolean deleteSelectedProject(String projectIdentifier) {

        if(AssertParam.isNullOrEmpty(projectIdentifier)){
            return false;
        }

        return false;//TODO implement when ready
    }

    @Override
    public ProjectElement loadSelectedProject(String projectIdentifier) {
        if(AssertParam.isNullOrEmpty(projectIdentifier)){
            return createEmptyProjectElement();
        }

        return new ProjectElement("","",new HashSet<>());//TODO implement when ready
    }

    private CalculatedPosition createEmptyCalculatedPosition() {
        return new CalculatedPosition(0,0,0,false,"");
    }

    private ProjectElement createEmptyProjectElement() {
        return new ProjectElement("","",new HashSet<>());
    }
}
