package de.hftstuttgart.projectindoorweb.web.internal.util;

import com.sun.imageio.spi.RAFImageOutputStreamSpi;
import de.hftstuttgart.projectindoorweb.persistence.entities.Building;
import de.hftstuttgart.projectindoorweb.persistence.entities.EvaalFile;
import de.hftstuttgart.projectindoorweb.persistence.entities.PositionResult;
import de.hftstuttgart.projectindoorweb.persistence.entities.RadioMap;
import de.hftstuttgart.projectindoorweb.web.internal.BuildingJsonWrapperSmall;
import de.hftstuttgart.projectindoorweb.web.internal.CalculatedPosition;
import de.hftstuttgart.projectindoorweb.web.internal.EvaluationEntry;
import de.hftstuttgart.projectindoorweb.web.internal.RadioMapEntry;
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

    public static List<CalculatedPosition> convertToCalculatedPositions(List<? extends PositionResult> positionResults) {


        List<CalculatedPosition> result = new ArrayList<>(positionResults.size());

        for (PositionResult positionResult :
                positionResults) {
            result.add(new CalculatedPosition(positionResult.getX(), positionResult.getY(), positionResult.getZ(), positionResult.isWgs84(), "To be implemented"));
        }

        return result;

    }

    public static CalculatedPosition convertToCalculatedPosition(PositionResult positionResult){

        return new CalculatedPosition(positionResult.getX(), positionResult.getY(),
                positionResult.getZ(), positionResult.isWgs84(), "To be implemented");

    }

    public static List<BuildingJsonWrapperSmall> convertToBuildingSmallJsonWrapper(List<Building> buildings) {
        List<BuildingJsonWrapperSmall> result = new ArrayList<>(buildings.size());

        for (Building building :
                buildings) {
            result.add(new BuildingJsonWrapperSmall(building.getId(), building.getBuildingName(), building.getBuildingFloors().size()));
        }

        return result;
    }

    public static List<EvaluationEntry> convertToEvaluationEntries(List<EvaalFile> evaalFiles){

        List<EvaluationEntry> evaluationEntries = new ArrayList<>(evaalFiles.size());

        for (EvaalFile evaalFile:
             evaalFiles) {
            evaluationEntries.add(new EvaluationEntry(evaalFile.getId(), evaalFile.getSourceFileName(), "To be clarified"));
        }

        return evaluationEntries;

    }

    public static List<RadioMapEntry> convertToRadioMapEntry(List<EvaalFile> evaalFiles){

        List<RadioMapEntry> radioMapEntries = new ArrayList<>(evaalFiles.size());

        for (EvaalFile evaalFile:
             evaalFiles) {

            radioMapEntries.add(new RadioMapEntry(evaalFile.getId(), evaalFile.getSourceFileName()));

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
