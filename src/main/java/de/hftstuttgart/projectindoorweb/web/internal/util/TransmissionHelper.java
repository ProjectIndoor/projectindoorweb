package de.hftstuttgart.projectindoorweb.web.internal.util;

import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LatLongCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.transformation.TransformationHelper;
import de.hftstuttgart.projectindoorweb.persistence.entities.*;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.*;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TransmissionHelper {

    public static File convertMultipartFileToLocalFile(MultipartFile multipartFile) throws IOException {

        File convertedFile = new File(multipartFile.getOriginalFilename());
        convertedFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
        return convertedFile;

    }

    public static List<BatchPositionResult> convertToBatchPositionResults(List<? extends PositionResult> positionResults) {


        List<BatchPositionResult> result = new ArrayList<>(positionResults.size());

        CalculatedPosition calculatedPosition;
        Position position;
        ReferencePosition referencePosition = null;
        WifiPositionResult wifiPositionResult;
        for (PositionResult positionResult :
                positionResults) {
            calculatedPosition = new CalculatedPosition(positionResult.getX(), positionResult.getY(), positionResult.getZ(), positionResult.isWgs84());
            if (positionResult instanceof WifiPositionResult) {
                wifiPositionResult = (WifiPositionResult) positionResult;
                if (wifiPositionResult.getPosiReference() != null) {
                    position = wifiPositionResult.getPosiReference().getReferencePosition();
                    referencePosition = new ReferencePosition(position.getX(), position.getY(), position.getZ(), position.isWgs84());
                }
            }

            result.add(new BatchPositionResult(calculatedPosition, referencePosition, 0));
        }

        return result;

    }

    public static SinglePositionResult convertToCalculatedPosition(PositionResult positionResult) {

        return new SinglePositionResult(positionResult.getX(), positionResult.getY(),
                positionResult.getZ(), positionResult.isWgs84());

    }

    public static List<GetAllBuildings> convertToBuildingSmallJsonWrapper(List<Building> buildings) {
        List<GetAllBuildings> result = new ArrayList<>(buildings.size());

        for (Building building :
                buildings) {
            result.add(new GetAllBuildings(building.getId(), building.getBuildingName(), building.getBuildingFloors().size()));
        }

        return result;
    }

    public static List<GetEvaluationFilesForBuilding> convertToEvaluationEntries(List<EvaalFile> evaalFiles) {

        List<GetEvaluationFilesForBuilding> evaluationEntries = new ArrayList<>(evaalFiles.size());

        for (EvaalFile evaalFile :
                evaalFiles) {
            evaluationEntries.add(new GetEvaluationFilesForBuilding(evaalFile.getId(), evaalFile.getSourceFileName()));
        }

        return evaluationEntries;

    }

    public static List<GetRadioMapFilesForBuilding> convertToRadioMapEntry(List<EvaalFile> evaalFiles) {

        List<GetRadioMapFilesForBuilding> radioMapEntries = new ArrayList<>(evaalFiles.size());

        for (EvaalFile evaalFile :
                evaalFiles) {

            radioMapEntries.add(new GetRadioMapFilesForBuilding(evaalFile.getId(), evaalFile.getSourceFileName()));

        }

        return radioMapEntries;

    }

    public static boolean areRequestedFilesPresent(EvaalFile[] evaalFiles) {

        for (int i = 0; i < evaalFiles.length; i++) {
            if (evaalFiles[i] == null) {
                return false;
            }


        }

        return true;

    }

    public static GetSingleBuilding convertToGetSingleBuildingResultObject(Building building) {

        long buildingId = building.getId();
        String buildingName = building.getBuildingName();
        int numberOfFloors = building.getBuildingFloors().size();
        int imagePixelWidth = building.getImagePixelWidth();
        int imagePixeHeight = building.getImagePixelHeight();
        BuildingPositionAnchor northWest = convertToBuildingPositionAnchor(building.getNorthWest());
        BuildingPositionAnchor northEast = convertToBuildingPositionAnchor(building.getNorthEast());
        BuildingPositionAnchor southEast = convertToBuildingPositionAnchor(building.getSouthEast());
        BuildingPositionAnchor southWest = convertToBuildingPositionAnchor(building.getSouthWest());
        BuildingPositionAnchor buildingCenterPoint = convertToBuildingPositionAnchor(building.getCenterPoint());
        double rotationAngle = building.getRotationAngle();
        double metersPerPixel = building.getMetersPerPixel();
        List<GetSingleBuildingEvaalFile> getSingleBuildingEvaalFiles = convertToGetSingleBuildingEvaalFiles(building.getEvaalFiles());
        List<GetSingleBuildingFloor> getSingleBuildingFloors = convertToGetSingleBuildingFloor(building.getBuildingFloors());

        return new GetSingleBuilding(buildingId, buildingName, numberOfFloors, imagePixelWidth, imagePixeHeight, northWest, northEast,
                southEast, southWest, buildingCenterPoint, rotationAngle, metersPerPixel, getSingleBuildingEvaalFiles, getSingleBuildingFloors);

    }

    public static List<GetSingleBuildingFloor> convertToGetSingleBuildingFloor(List<Floor> floors) {

        List<GetSingleBuildingFloor> result = new ArrayList<>(floors.size());

        for (Floor floor :
                floors) {
            result.add(new GetSingleBuildingFloor(floor.getId(), floor.getLevel(), floor.getFloorMapUrl()));
        }

        return result;

    }

    public static List<GetSingleBuildingEvaalFile> convertToGetSingleBuildingEvaalFiles(List<EvaalFile> evaalFiles) {

        List<GetSingleBuildingEvaalFile> result = new ArrayList<>(evaalFiles.size());

        for (EvaalFile evaalFile :
                evaalFiles) {
            result.add(new GetSingleBuildingEvaalFile(evaalFile.getId(), evaalFile.getSourceFileName(),
                    evaalFile.isEvaluationFile()));
        }

        return result;

    }


    public static BuildingPositionAnchor convertToBuildingPositionAnchor(Position position) {

        return new BuildingPositionAnchor(position.getX(), position.getY());

    }

    public static Position convertPositionAnchorToPosition(BuildingPositionAnchor buildingPositionAnchor) {

        return new Position(buildingPositionAnchor.getLatitude(), buildingPositionAnchor.getLongitude(), 0.0, true);

    }

    public static List<WifiPositionResult> convertCalculatedResultsToPixelPositions(List<WifiPositionResult> wifiPositionResults,
                                                                                    Building building) {

        Position latLongPosition;
        Position pixelPosition;
        List<WifiPositionResult> convertedWifiPositionsResults = new ArrayList<>(wifiPositionResults.size());
        for (WifiPositionResult wifiPositionResult :
                wifiPositionResults) {
            latLongPosition = new Position(wifiPositionResult.getX(), wifiPositionResult.getY(), wifiPositionResult.getZ(), wifiPositionResult.isWgs84());
            pixelPosition = retrievePositionAsPixels(building, latLongPosition);
            wifiPositionResult.setX(pixelPosition.getX());
            wifiPositionResult.setY(pixelPosition.getY());
            wifiPositionResult.setZ(pixelPosition.getZ());
            wifiPositionResult.setWgs84(pixelPosition.isWgs84());
            latLongPosition = wifiPositionResult.getPosiReference().getReferencePosition();
            pixelPosition = retrievePositionAsPixels(building, latLongPosition);
            wifiPositionResult.setPosiReference(new PosiReference(0, 0,
                    pixelPosition, 0, 0, null));
            convertedWifiPositionsResults.add(wifiPositionResult);
        }

        return convertedWifiPositionsResults;


    }

    public static WifiPositionResult convertCalculatedResultToPixelPosition(WifiPositionResult wifiPositionResult, Building building) {


        Position latLongPosition;
        Position pixelPosition;

        latLongPosition = new Position(wifiPositionResult.getX(), wifiPositionResult.getY(), wifiPositionResult.getZ(), wifiPositionResult.isWgs84());
        pixelPosition = retrievePositionAsPixels(building, latLongPosition);
        wifiPositionResult.setX(pixelPosition.getX());
        wifiPositionResult.setY(pixelPosition.getY());
        wifiPositionResult.setZ(pixelPosition.getZ());
        wifiPositionResult.setWgs84(pixelPosition.isWgs84());

        return wifiPositionResult;

    }

    public static Position retrievePositionAsPixels(Building building, Position latLongPosition) {

        LatLongCoord latLongCoord = new LatLongCoord(latLongPosition.getX(), latLongPosition.getY());
        double[] positionInPixels = TransformationHelper.wgsToPict(building, latLongCoord,
                building.getImagePixelWidth(), building.getImagePixelHeight());

        return new Position(positionInPixels[0], positionInPixels[1], latLongPosition.getZ(), false);

    }
}
