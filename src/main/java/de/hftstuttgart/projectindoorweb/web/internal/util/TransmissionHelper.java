package de.hftstuttgart.projectindoorweb.web.internal.util;

import de.hftstuttgart.projectindoorweb.persistence.entities.Building;
import de.hftstuttgart.projectindoorweb.persistence.entities.EvaalFile;
import de.hftstuttgart.projectindoorweb.persistence.entities.PositionResult;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.GetAllBuildings;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.GeneratePositionResult;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.GetEvaluationFilesForBuilding;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.GetRadioMapFilesForBuilding;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransmissionHelper {

    public static  File convertMultipartFileToLocalFile(MultipartFile multipartFile) throws IOException {

        File convertedFile = new File(multipartFile.getOriginalFilename());
        convertedFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
        return convertedFile;

    }

    public static List<GeneratePositionResult> convertToCalculatedPositions(List<? extends PositionResult> positionResults) {


        List<GeneratePositionResult> result = new ArrayList<>(positionResults.size());

        for (PositionResult positionResult :
                positionResults) {
            result.add(new GeneratePositionResult(positionResult.getX(), positionResult.getY(), positionResult.getZ(), positionResult.isWgs84(), "To be implemented"));
        }

        return result;

    }

    public static GeneratePositionResult convertToCalculatedPosition(PositionResult positionResult){

        return new GeneratePositionResult(positionResult.getX(), positionResult.getY(),
                positionResult.getZ(), positionResult.isWgs84(), "To be implemented");

    }

    public static List<GetAllBuildings> convertToBuildingSmallJsonWrapper(List<Building> buildings) {
        List<GetAllBuildings> result = new ArrayList<>(buildings.size());

        for (Building building :
                buildings) {
            result.add(new GetAllBuildings(building.getId(), building.getBuildingName(), building.getBuildingFloors().size()));
        }

        return result;
    }

    public static List<GetEvaluationFilesForBuilding> convertToEvaluationEntries(List<EvaalFile> evaalFiles){

        List<GetEvaluationFilesForBuilding> evaluationEntries = new ArrayList<>(evaalFiles.size());

        for (EvaalFile evaalFile:
             evaalFiles) {
            evaluationEntries.add(new GetEvaluationFilesForBuilding(evaalFile.getId(), evaalFile.getSourceFileName()));
        }

        return evaluationEntries;

    }

    public static List<GetRadioMapFilesForBuilding> convertToRadioMapEntry(List<EvaalFile> evaalFiles){

        List<GetRadioMapFilesForBuilding> radioMapEntries = new ArrayList<>(evaalFiles.size());

        for (EvaalFile evaalFile:
             evaalFiles) {

            radioMapEntries.add(new GetRadioMapFilesForBuilding(evaalFile.getId(), evaalFile.getSourceFileName()));

        }

        return radioMapEntries;

    }

    public static boolean areRequestedFilesPresent(EvaalFile[] evaalFiles){

        for(int i = 0; i < evaalFiles.length; i++){
            if(evaalFiles[i] == null){
                return false;
            }


        }

        return true;

    }
}
