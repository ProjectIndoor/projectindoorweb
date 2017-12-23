package de.hftstuttgart.projectindoorweb.web.internal.util;

import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LatLongCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.transformation.TransformationHelper;
import de.hftstuttgart.projectindoorweb.persistence.entities.Building;
import de.hftstuttgart.projectindoorweb.persistence.entities.EvaalFile;
import de.hftstuttgart.projectindoorweb.persistence.entities.Floor;
import de.hftstuttgart.projectindoorweb.persistence.entities.Parameter;
import de.hftstuttgart.projectindoorweb.persistence.entities.Position;
import de.hftstuttgart.projectindoorweb.persistence.entities.PositionResult;
import de.hftstuttgart.projectindoorweb.persistence.entities.WifiPositionResult;
import de.hftstuttgart.projectindoorweb.positionCalculator.CalculationAlgorithm;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.BuildingPositionAnchor;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.GetAllBuildings;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.GetFloor;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.GetSingleBuilding;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.GetSingleBuildingEvaalFile;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.GetSingleBuildingFloor;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.BatchPositionResult;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.CalculatedPosition;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.GetAllEvaalEntries;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.GetEvaluationFilesForBuilding;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.GetRadioMapFilesForBuilding;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.ReferencePosition;
import de.hftstuttgart.projectindoorweb.web.internal.requests.positioning.SinglePositionResult;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.SaveNewProjectParameters;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TransmissionHelper {

    public static File convertMultipartFileToLocalFile(MultipartFile multipartFile) throws IOException {

        File convertedFile = new File(System.getProperty("java.io.tmpdir"), multipartFile.getOriginalFilename());
        convertedFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.flush();
        fileOutputStream.close();
        return convertedFile;

    }

    public static List<BatchPositionResult> convertToBatchPositionResults(List<? extends PositionResult> positionResults, Building building) {


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
                    referencePosition = new ReferencePosition(wifiPositionResult.getPosiReference().getPositionInSourceFile(),
                            position.getX(), position.getY(), position.getZ(), position.isWgs84());
                }
            }

            double distance = -1.0;
            if (calculatedPosition != null && referencePosition != null) {
                distance = retrieveDistanceBetweenWgsPositions(calculatedPosition, referencePosition, building);
            }

            result.add(new BatchPositionResult(calculatedPosition, referencePosition, distance));
            if (referencePosition != null) {
                referencePosition = null;
            }
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
            result.add(new GetAllBuildings(building.getId(), building.getBuildingName(), building.getImagePixelWidth(),
                    building.getImagePixelHeight(), convertToExternalFloor(building.getBuildingFloors())));
        }

        return result;
    }

    public static List<GetFloor> convertToExternalFloor(List<Floor> buildingFloors) {

        List<GetFloor> result = new ArrayList<>(buildingFloors.size());

        for (Floor floor :
                buildingFloors) {
            result.add(new GetFloor(floor.getId(), floor.getLevel(), floor.getFloorName(), floor.getFloorMapUrl()));
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
        List<GetFloor> getFloorList = convertToExternalFloor(building.getBuildingFloors());

        return new GetSingleBuilding(buildingId, buildingName, numberOfFloors, imagePixelWidth, imagePixeHeight, northWest, northEast,
                southEast, southWest, buildingCenterPoint, rotationAngle, metersPerPixel, getSingleBuildingEvaalFiles, getFloorList);

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

        if (position != null) {
            return new BuildingPositionAnchor(position.getX(), position.getY());
        }

        return new BuildingPositionAnchor(-1, -1);

    }

    public static Position convertPositionAnchorToPosition(BuildingPositionAnchor buildingPositionAnchor) {

        return new Position(buildingPositionAnchor.getLatitude(), buildingPositionAnchor.getLongitude(), 0.0, true);

    }

    public static List<BatchPositionResult> convertCalculatedResultsToPixelPositions(List<BatchPositionResult> batchPositionResults,
                                                                                     Building building) {

        Position latLongPosition;
        Position pixelPosition;
        List<BatchPositionResult> convertedBatchPositionResults = new ArrayList<>(batchPositionResults.size());
        CalculatedPosition calculatedPosition;
        ReferencePosition referencePosition;
        for (BatchPositionResult batchPositionResult :
                batchPositionResults) {
            calculatedPosition = batchPositionResult.getCalculatedPosition();
            latLongPosition = new Position(calculatedPosition.getX(), calculatedPosition.getY(), calculatedPosition.getZ(), calculatedPosition.isWgs84());
            pixelPosition = retrievePositionAsPixels(building, latLongPosition);
            calculatedPosition.setX(pixelPosition.getX());
            calculatedPosition.setY(pixelPosition.getY());
            calculatedPosition.setZ(pixelPosition.getZ());
            calculatedPosition.setWgs84(pixelPosition.isWgs84());
            batchPositionResult.setCalculatedPosition(calculatedPosition);
            referencePosition = batchPositionResult.getReferencePosition();
            if (referencePosition != null) {
                latLongPosition = new Position(referencePosition.getX(), referencePosition.getY(), referencePosition.getZ(), referencePosition.isWgs84());
                pixelPosition = retrievePositionAsPixels(building, latLongPosition);
                referencePosition.setX(pixelPosition.getX());
                referencePosition.setY(pixelPosition.getY());
                referencePosition.setZ(pixelPosition.getZ());
                referencePosition.setWgs84(pixelPosition.isWgs84());
                batchPositionResult.setReferencePosition(referencePosition);
            }

            convertedBatchPositionResults.add(batchPositionResult);
        }

        return convertedBatchPositionResults;


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

    public static double retrieveDistanceBetweenWgsPositions(CalculatedPosition calculatedPosition, ReferencePosition referencePosition, Building building) {

        Position southWest = building.getSouthWest();
        Position southEast = building.getSouthEast();

        if (southWest != null && southEast != null) {
            LatLongCoord position1Wrapper = new LatLongCoord(calculatedPosition.getX(), calculatedPosition.getY());
            LatLongCoord position2Wrapper = new LatLongCoord(referencePosition.getX(), referencePosition.getY());
            LatLongCoord southWestWrapper = new LatLongCoord(southWest.getX(), southWest.getY());
            LatLongCoord southEastWrapper = new LatLongCoord(southEast.getX(), southEast.getY());
            return TransformationHelper.getDistance(position1Wrapper, position2Wrapper, southWestWrapper, southEastWrapper);
        }

        return 0.0;

    }

    public static List<EvaalFile> convertArrayToMutableList(EvaalFile[] evaalFiles) {

        List<EvaalFile> result = new ArrayList<>(evaalFiles.length);

        for (int i = 0; i < evaalFiles.length; i++) {
            result.add(i, evaalFiles[i]);
        }

        return result;

    }

    public static Set<SaveNewProjectParameters> convertToExternalProjectParameters(List<Parameter> parameters) {

        Set<SaveNewProjectParameters> result = new HashSet<>(parameters.size());

        for (Parameter parameter :
                parameters) {
            result.add(new SaveNewProjectParameters(parameter.getParameterName(), parameter.getParameterValue()));
        }

        return result;

    }

    public static String convertToExternalAlgorithmType(CalculationAlgorithm algorithm) {

        switch (algorithm) {
            case WIFI:
                return "WIFI";
            /*Currently, only WIFI mode is supported.*/
            default:
                return "WIFI";
        }
    }

    public static long[] getEvaalFileIds(List<EvaalFile> evaalFiles) {

        long[] result = new long[evaalFiles.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = evaalFiles.get(i).getId();
        }

        return result;

    }

    public static String getFormattedNowTimestamp() {

        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd_HH:mm:ss"));


    }

    public static Floor getBuildingFloorById(long floorId, Building building) {

        List<Floor> buildingFloors = building.getBuildingFloors();

        for (Floor floor :
                buildingFloors) {
            if (floor.getId() - floorId == 0) {
                return floor;
            }
        }

        return null;

    }

    public static List<GetAllEvaalEntries> convertToGetAlLEvaalEntries(List<EvaalFile> evaalFiles) {

        List<GetAllEvaalEntries> result = new ArrayList<>(evaalFiles.size());


        Building building;
        String buildingName;
        String evaalFileType;
        for (EvaalFile evaalFile :
                evaalFiles) {
            building = evaalFile.getRecordedInBuilding();
            buildingName = building == null ? "" : building.getBuildingName();
            evaalFileType = evaalFile.isEvaluationFile() ? "Evaluation File" : "Radiomap File";
            result.add(new GetAllEvaalEntries(evaalFile.getId(), evaalFile.getSourceFileName(), evaalFileType, buildingName));
        }

        return result;

    }

    public static List<File> convertMulipartFileBatchToLocalFileBatch(MultipartFile[] multipartFiles){

        List<File> localFiles = new ArrayList<>(multipartFiles.length);

        try {
            for (int i = 0; i < multipartFiles.length; i++) {
                localFiles.add(TransmissionHelper.convertMultipartFileToLocalFile(multipartFiles[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return localFiles;

    }




}
