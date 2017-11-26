package de.hftstuttgart.projectindoorweb.web.internal.util;

import de.hftstuttgart.projectindoorweb.persistence.entities.Building;
import de.hftstuttgart.projectindoorweb.persistence.entities.PositionResult;
import de.hftstuttgart.projectindoorweb.web.internal.BuildingElement;
import de.hftstuttgart.projectindoorweb.web.internal.CalculatedPosition;
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

    public static List<BuildingElement> convertToBuildingElements(List<Building> buildings) {
        List<BuildingElement> result = new ArrayList<>(buildings.size());

        for (Building building :
                buildings) {
            result.add(new BuildingElement(building.getId(), building.getBuildingName(), building.getBuildingFloors().size()));
        }

        return result;
    }
}
