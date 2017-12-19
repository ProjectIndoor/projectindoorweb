package de.hftstuttgart.projectindoorweb.persistence;

import de.hftstuttgart.projectindoorweb.application.internal.AssertParam;
import de.hftstuttgart.projectindoorweb.geoCalculator.internal.LatLongCoord;
import de.hftstuttgart.projectindoorweb.geoCalculator.transformation.TransformationHelper;
import de.hftstuttgart.projectindoorweb.persistence.entities.Building;
import de.hftstuttgart.projectindoorweb.persistence.entities.EvaalFile;
import de.hftstuttgart.projectindoorweb.persistence.entities.Floor;
import de.hftstuttgart.projectindoorweb.persistence.entities.Parameter;
import de.hftstuttgart.projectindoorweb.persistence.entities.Position;
import de.hftstuttgart.projectindoorweb.persistence.entities.Project;
import de.hftstuttgart.projectindoorweb.persistence.entities.RadioMap;
import de.hftstuttgart.projectindoorweb.persistence.internal.util.PersistencyConstants;
import de.hftstuttgart.projectindoorweb.persistence.repositories.BuildingRepository;
import de.hftstuttgart.projectindoorweb.persistence.repositories.EvaalFileRepository;
import de.hftstuttgart.projectindoorweb.persistence.repositories.FloorRepository;
import de.hftstuttgart.projectindoorweb.persistence.repositories.ProjectRepository;
import de.hftstuttgart.projectindoorweb.persistence.repositories.RadioMapRepository;
import de.hftstuttgart.projectindoorweb.positionCalculator.CalculationAlgorithm;
import de.hftstuttgart.projectindoorweb.web.internal.requests.building.BuildingPositionAnchor;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.GetAlgorithmParameters;
import de.hftstuttgart.projectindoorweb.web.internal.requests.project.SaveNewProjectParameters;
import de.hftstuttgart.projectindoorweb.web.internal.util.ParameterHelper;
import de.hftstuttgart.projectindoorweb.web.internal.util.TransmissionConstants;
import de.hftstuttgart.projectindoorweb.web.internal.util.TransmissionHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class PersistencyServiceImpl implements PersistencyService {

    @Override
    public long createNewProject(String projectName, String algorithmType, Set<SaveNewProjectParameters> saveNewProjectParameters,
                                 long buildingIdentifier, long evalFileIdentifier, long[] radioMapFileIdentifiers) {

        AssertParam.throwIfNullOrEmpty(projectName, "projectName");
        AssertParam.throwIfNullOrEmpty(algorithmType, "algorithmType");
        //AssertParam.throwIfNull(saveNewProjectParameters, "saveNewProjectParameters");

        BuildingRepository buildingRepository = (BuildingRepository) RepositoryRegistry.getRepositoryByEntityName(Building.class.getName());
        EvaalFileRepository evaalFileRepository = (EvaalFileRepository) RepositoryRegistry.getRepositoryByEntityName(EvaalFile.class.getName());

        CalculationAlgorithm calculationAlgorithm = getAlgorithmFromText(algorithmType);
        if (calculationAlgorithm == null) {
            return -1;
        }

        List<Parameter> parametersAsList = convertToEntityParameters(saveNewProjectParameters);

        Building building = buildingRepository.findOne(buildingIdentifier);
        EvaalFile evaluationFile = evaalFileRepository.findOne(evalFileIdentifier);

        EvaalFile[] evaalFiles = new EvaalFile[radioMapFileIdentifiers.length];
        for (int i = 0; i < evaalFiles.length; i++) {
            evaalFiles[i] = getEvaalFileForId(radioMapFileIdentifiers[i]);
        }


        Project projectToBeSaved;
        if (building == null && evaluationFile == null && !TransmissionHelper.areRequestedFilesPresent(evaalFiles)) {
            projectToBeSaved = new Project(projectName, calculationAlgorithm, parametersAsList);
        } else {
            List<EvaalFile> evaalFileList = Arrays.asList(evaalFiles);
            projectToBeSaved = new Project(projectName, calculationAlgorithm, parametersAsList, building, evaluationFile, evaalFileList);
        }

        ProjectRepository projectRepository = (ProjectRepository) RepositoryRegistry.getRepositoryByEntityName(Project.class.getName());
        projectToBeSaved = projectRepository.save(projectToBeSaved);

        if (projectToBeSaved != null) {
            return projectToBeSaved.getId();
        }

        return -1;


    }

    @Override
    public boolean updateProject(long projectId, String newProjectName, String newAlgorithmType, Set<SaveNewProjectParameters> newSaveNewProjectParameters,
                                 long buildingIdentifier, long evalFileIdentifier, long[] radioMapFileIdentifiers) {

        AssertParam.throwIfNull(projectId, "projectId");
        AssertParam.throwIfNullOrEmpty(newProjectName, "newProjectName");
        AssertParam.throwIfNullOrEmpty(newAlgorithmType, "newAlgorithmType");
        AssertParam.throwIfNull(newSaveNewProjectParameters, "newSaveNewProjectParameters");
        AssertParam.throwIfNull(buildingIdentifier, "buildingIdentifier");
        AssertParam.throwIfNull(evalFileIdentifier, "evalFileIdentifier");
        AssertParam.throwIfNull(radioMapFileIdentifiers, "radioMapFileIdentifiers");

        ProjectRepository projectRepository = (ProjectRepository) RepositoryRegistry.getRepositoryByEntityName(Project.class.getName());
        BuildingRepository buildingRepository = (BuildingRepository) RepositoryRegistry.getRepositoryByEntityName(Building.class.getName());
        EvaalFileRepository evaalFileRepository = (EvaalFileRepository) RepositoryRegistry.getRepositoryByEntityName(EvaalFile.class.getName());

        Project projectToBeUpdated = projectRepository.findOne(projectId);

        if (projectToBeUpdated != null) {
            CalculationAlgorithm calculationAlgorithm = getAlgorithmFromText(newAlgorithmType);
            if (calculationAlgorithm == null) {
                return false;
            }

            List<Parameter> parametersAsList = convertToEntityParameters(newSaveNewProjectParameters);

            Building building = buildingRepository.findOne(buildingIdentifier);
            List<Parameter> projectParameters = convertToEntityParameters(newSaveNewProjectParameters);
            EvaalFile evaluationFile = evaalFileRepository.findOne(evalFileIdentifier);

            EvaalFile[] evaalFiles = new EvaalFile[radioMapFileIdentifiers.length];
            for (int i = 0; i < evaalFiles.length; i++) {
                evaalFiles[i] = getEvaalFileForId(radioMapFileIdentifiers[i]);
            }

            if (newProjectName != null
                    && calculationAlgorithm != null
                    && projectParameters != null) {

                List<EvaalFile> evaalFileList = TransmissionHelper.convertArrayToMutableList(evaalFiles);
                projectToBeUpdated.setProjectName(newProjectName);
                projectToBeUpdated.setCalculationAlgorithm(calculationAlgorithm);
                projectToBeUpdated.setBuilding(building);
                projectToBeUpdated.setProjectParameters(projectParameters);
                projectToBeUpdated.setEvaluationFile(evaluationFile);
                projectToBeUpdated.setEvaalFiles(evaalFileList);
                return projectRepository.save(projectToBeUpdated) != null;
            }
        }


        return false;

    }


    @Override
    public boolean deleteProject(long projectId) {

        AssertParam.throwIfNull(projectId, "projectId");

        ProjectRepository projectRepository = (ProjectRepository) RepositoryRegistry.getRepositoryByEntityName(Project.class.getName());

        projectRepository.delete(projectId);
        Project project = projectRepository.findOne(projectId);

        return project == null;

    }

    @Override
    public long addNewBuilding(String buildingName, int numberOfFloors, int imagePixelWidth, int imagePixelHeight,
                               BuildingPositionAnchor southEastAnchor, BuildingPositionAnchor southWestAnchor,
                               BuildingPositionAnchor northEastAnchor, BuildingPositionAnchor northWestAnchor,
                               BuildingPositionAnchor buildingCenterPoint, double rotationAngle, double metersPerPixel) {

        AssertParam.throwIfNullOrEmpty(buildingName, "buildingName");
        AssertParam.throwIfNull(numberOfFloors, "numberOfFloors");
        AssertParam.throwIfNull(imagePixelWidth, "imagePixelWidth");
        AssertParam.throwIfNull(imagePixelHeight, "imagePixelHeigth");

        if ((southEastAnchor == null || southWestAnchor == null || northEastAnchor == null || northWestAnchor == null)
                && (buildingCenterPoint == null)) {

            AssertParam.throwIfNull(northWestAnchor, "northWestAnchor");
            AssertParam.throwIfNull(northEastAnchor, "northEastAnchor");
            AssertParam.throwIfNull(southEastAnchor, "southEastAnchor");
            AssertParam.throwIfNull(southWestAnchor, "southWestAnchor");
            AssertParam.throwIfNull(buildingCenterPoint, "buildingCenterPoint");

        }


        if (northWestAnchor == null) {
            northWestAnchor = retrievePositionAnchor(buildingCenterPoint, rotationAngle, metersPerPixel,
                    imagePixelWidth, imagePixelHeight, "northWest");
        }

        if (northEastAnchor == null) {
            northEastAnchor = retrievePositionAnchor(buildingCenterPoint, rotationAngle, metersPerPixel,
                    imagePixelWidth, imagePixelHeight, "northEast");
        }

        if (southEastAnchor == null) {
            southEastAnchor = retrievePositionAnchor(buildingCenterPoint, rotationAngle, metersPerPixel,
                    imagePixelWidth, imagePixelHeight, "southEast");
        }

        if (southWestAnchor == null) {
            southWestAnchor = retrievePositionAnchor(buildingCenterPoint, rotationAngle, metersPerPixel,
                    imagePixelWidth, imagePixelHeight, "southWest");
        }

        Position northWestPosition = TransmissionHelper.convertPositionAnchorToPosition(northWestAnchor);
        Position northEastPosition = TransmissionHelper.convertPositionAnchorToPosition(northEastAnchor);
        Position southEastPosition = TransmissionHelper.convertPositionAnchorToPosition(southEastAnchor);
        Position southWestPosition = TransmissionHelper.convertPositionAnchorToPosition(southWestAnchor);

        Position buildingCenterPosition = null;
        if (buildingCenterPoint != null) {
            buildingCenterPosition = new Position(buildingCenterPoint.getLatitude(), buildingCenterPoint.getLongitude(), 0.0, true);
        }

        Building buildingToBeSaved = new Building(buildingName, numberOfFloors, imagePixelWidth, imagePixelHeight,
                rotationAngle, metersPerPixel, northWestPosition, northEastPosition, southEastPosition,
                southWestPosition, buildingCenterPosition);

        BuildingRepository buildingRepository = (BuildingRepository) RepositoryRegistry.getRepositoryByEntityName(Building.class.getName());
        buildingToBeSaved = buildingRepository.save(buildingToBeSaved);

        if (buildingToBeSaved != null) {
            return buildingToBeSaved.getId();
        }

        return -1;


    }


    @Override
    public Project getProjectById(long projectId) {

        AssertParam.throwIfNull(projectId, "projectId");

        ProjectRepository projectRepository = (ProjectRepository) RepositoryRegistry.getRepositoryByEntityName(Project.class.getName());

        return projectRepository.findOne(projectId);

    }

    @Override
    public List<Project> getAllProjects() {

        ProjectRepository projectRepository = (ProjectRepository) RepositoryRegistry.getRepositoryByEntityName(Project.class.getName());

        return (List<Project>) projectRepository.findAll();

    }

    @Override
    public List<Building> getAllBuildings() {

        BuildingRepository buildingRepository = (BuildingRepository) RepositoryRegistry.getRepositoryByEntityName(Building.class.getName());

        return (List<Building>) buildingRepository.findAll();


    }

    @Override
    public Building getBuildingById(long buildingId) {

        AssertParam.throwIfNull(buildingId, "buildingId");

        BuildingRepository buildingRepository = (BuildingRepository) RepositoryRegistry.getRepositoryByEntityName(Building.class.getName());

        return buildingRepository.findOne(buildingId);

    }

    @Override
    public boolean updateBuilding(long buildingId, String buildingName, int imagePixelWidth, int imagePixelHeight,
                                  Position northWest, Position northEast, Position southEast, Position southWest, Position buildingCenterPoint,
                                  double rotationAngle, double metersPerPixel) {

        AssertParam.throwIfNull(buildingId, "buildingId");
        AssertParam.throwIfNullOrEmpty(buildingName, "buildingName");
        AssertParam.throwIfNull(imagePixelWidth, "imagePixelWidth");
        AssertParam.throwIfNull(imagePixelHeight, "imagePixelHeight");
        AssertParam.throwIfNull(northWest, "northWest");
        AssertParam.throwIfNull(northEast, "northEast");
        AssertParam.throwIfNull(southEast, "southEast");
        AssertParam.throwIfNull(southWest, "southWest");
        AssertParam.throwIfNull(buildingCenterPoint, "buildingCenterPoint");
        AssertParam.throwIfNull(rotationAngle, "rotationAngle");
        AssertParam.throwIfNull(metersPerPixel, "metersPerPixel");

        Building buildingToBeUpdated = this.getBuildingById(buildingId);

        if (buildingToBeUpdated != null) {
            buildingToBeUpdated.setBuildingName(buildingName);
            buildingToBeUpdated.setImagePixelWidth(imagePixelWidth);
            buildingToBeUpdated.setImagePixelHeight(imagePixelHeight);
            buildingToBeUpdated.setNorthWest(northWest);
            buildingToBeUpdated.setNorthEast(northEast);
            buildingToBeUpdated.setSouthEast(southEast);
            buildingToBeUpdated.setSouthWest(southWest);
            buildingToBeUpdated.setCenterPoint(buildingCenterPoint);
            buildingToBeUpdated.setRotationAngle(rotationAngle);
            buildingToBeUpdated.setMetersPerPixel(metersPerPixel);

            BuildingRepository buildingRepository = (BuildingRepository) RepositoryRegistry.getRepositoryByEntityName(Building.class.getName());
            return buildingRepository.save(buildingToBeUpdated) != null;
        }

        return false;


    }

    @Override
    public boolean updateBuilding(Building building, Floor floor, File floorMapFile) throws IOException {

        AssertParam.throwIfNull(building, "building");
        AssertParam.throwIfNull(floor, "floor");
        AssertParam.throwIfNull(floorMapFile, "floorMapFile");

        String buildingName = building.getBuildingName();
        if (buildingName == null || buildingName.isEmpty()) {
            buildingName = "DefaultBuildingName";
        }


        boolean fileWriteSuccess = doInitialImageFolderSetup(buildingName);

        String localTargetFileName = replaceFileEnding(floorMapFile, PersistencyConstants.IMAGE_TARGET_FILE_ENDING);
        String separator = File.separator;
        Path localFilePath = Paths.get(String.format("%s%s%s%s%s%s%s", PersistencyConstants.LOCAL_RESOURCE_DIR, separator,
                PersistencyConstants.LOCAL_MAPS_DIR, separator, buildingName, separator, localTargetFileName));
        File localFile = new File(localFilePath.toUri());

        if (!Files.exists(localFilePath)) {
            OutputStream outputStream = new FileOutputStream(localFilePath.toFile());
            BufferedImage bufferedImage = ImageIO.read(floorMapFile);
            fileWriteSuccess = ImageIO.write(bufferedImage, PersistencyConstants.IMAGE_TARGET_FILE_ENDING, outputStream);
        }


        if (fileWriteSuccess) {
            String cleanFloorMapPath = String.format("%s%s%s%s%s", PersistencyConstants.LOCAL_MAPS_DIR, separator,
                    building.getBuildingName(), separator, localFile.getName());
            floor.setFloorMapPath(cleanFloorMapPath);
            String cleanFloorMapUrl = String.format("building%s?%s=%d", TransmissionConstants.GET_FLOOR_MAP_REST_URL,
                    TransmissionConstants.FLOOR_IDENTIFIER_PARAM, floor.getId());
            floor.setFloorMapUrl(cleanFloorMapUrl);
        }

        BuildingRepository buildingRepository = (BuildingRepository) RepositoryRegistry.getRepositoryByEntityName(Building.class.getName());
        return fileWriteSuccess && buildingRepository.save(building) != null;

    }

    @Override
    public File getFloorMapByFloorId(long floorId) throws IOException {

        FloorRepository floorRepository = (FloorRepository) RepositoryRegistry.getRepositoryByEntityName(Floor.class.getName());
        Floor floorFromDatabase = floorRepository.findOne(floorId);

        if (floorFromDatabase != null) {
            String separator = File.separator;
            Path localFilePath = Paths.get(String.format("%s%s%s", PersistencyConstants.LOCAL_RESOURCE_DIR, separator,
                    floorFromDatabase.getFloorMapPath()));
            if (Files.exists(localFilePath)) {
                File result = localFilePath.toFile();
                return result;
            }
        }

        return null;

    }

    @Override
    public boolean deleteBuilding(long buildingId) {

        AssertParam.throwIfNull(buildingId, "buildingId");

        BuildingRepository buildingRepository = (BuildingRepository) RepositoryRegistry.getRepositoryByEntityName(Building.class.getName());
        buildingRepository.delete(buildingId);
        Building deletedBuilding = buildingRepository.findOne(buildingId);
        return deletedBuilding == null;

    }


    @Override
    public boolean saveEvaalFiles(List<EvaalFile> evaalFiles) {

        AssertParam.throwIfNull(evaalFiles, "evaalFiles");

        EvaalFileRepository evaalFileRepository = (EvaalFileRepository) RepositoryRegistry.getRepositoryByEntityName(EvaalFile.class.getName());

        Iterable<EvaalFile> saved = evaalFileRepository.save(evaalFiles);

        return saved != null;

    }

    @Override
    public EvaalFile getEvaalFileForId(long evaalFileId) {

        AssertParam.throwIfNull(evaalFileId, "evaalFileId");

        EvaalFileRepository evaalFileRepository = (EvaalFileRepository) RepositoryRegistry.getRepositoryByEntityName(EvaalFile.class.getName());

        return evaalFileRepository.findOne(evaalFileId);
    }

    @Override
    public List<EvaalFile> getAllEvaalFiles() {

        EvaalFileRepository evaalFileRepository = (EvaalFileRepository) RepositoryRegistry.getRepositoryByEntityName(EvaalFile.class.getName());

        return (List<EvaalFile>) evaalFileRepository.findAll();

    }


    @Override
    public List<EvaalFile> getEvaluationFilesForBuilding(Building building) {

        AssertParam.throwIfNull(building, "building");

        EvaalFileRepository evaalFileRepository = (EvaalFileRepository) RepositoryRegistry.getRepositoryByEntityName(EvaalFile.class.getName());

        return evaalFileRepository.findByRecordedInBuildingAndAndEvaluationFileTrue(building);

    }

    @Override
    public List<EvaalFile> getRadioMapFilesForBuiling(Building building) {

        AssertParam.throwIfNull(building, "building");

        EvaalFileRepository evaalFileRepository = (EvaalFileRepository) RepositoryRegistry.getRepositoryByEntityName(EvaalFile.class.getName());

        return evaalFileRepository.findByRecordedInBuildingAndEvaluationFileFalse(building);

    }

    @Override
    public boolean deleteEvaalFile(long evaalFileId) {

        AssertParam.throwIfNull(evaalFileId, "evaalFileId");

        EvaalFileRepository evaalFileRepository = (EvaalFileRepository) RepositoryRegistry.getRepositoryByEntityName(EvaalFile.class.getName());
        evaalFileRepository.delete(evaalFileId);
        EvaalFile deletedEvaalFile = evaalFileRepository.findOne(evaalFileId);
        return deletedEvaalFile == null;
    }


    private List<Parameter> convertToEntityParameters(Set<SaveNewProjectParameters> saveNewProjectParameters) {

        List<Parameter> parametersAsList = new ArrayList<>();
        ParameterHelper helper = ParameterHelper.getInstance();

        if (saveNewProjectParameters != null) {
            for (SaveNewProjectParameters parameter :
                    saveNewProjectParameters) {
                GetAlgorithmParameters getAlgorithmParameters = helper.getParameterByInternalName(parameter.getName());
                parametersAsList.add(new Parameter(parameter.getName(), parameter.getValue()));
            }
        }

        return parametersAsList;


    }

    private BuildingPositionAnchor retrievePositionAnchor(BuildingPositionAnchor buildingCenterPoint,
                                                          double rotationAngle, double metersPerPixel,
                                                          int imagePixelWidth, int imagePixelHeight,
                                                          String corner) {

        String capitalizedCorner = corner.toUpperCase();

        double[] calculatedLatLong;
        switch (capitalizedCorner) {
            case "NORTHWEST":
                calculatedLatLong = TransformationHelper.calculateNorthWestCorner(new LatLongCoord(buildingCenterPoint.getLatitude(),
                        buildingCenterPoint.getLongitude()), rotationAngle, metersPerPixel, imagePixelWidth, imagePixelHeight);
                break;
            case "NORTHEAST":
                calculatedLatLong = TransformationHelper.calculateNorthEastCorner(new LatLongCoord(buildingCenterPoint.getLatitude(),
                        buildingCenterPoint.getLongitude()), rotationAngle, metersPerPixel, imagePixelWidth, imagePixelHeight);
                break;
            case "SOUTHEAST":
                calculatedLatLong = TransformationHelper.calculateSouthEastCorner(new LatLongCoord(buildingCenterPoint.getLatitude(),
                        buildingCenterPoint.getLongitude()), rotationAngle, metersPerPixel, imagePixelWidth, imagePixelHeight);
                break;
            case "SOUTHWEST":
                calculatedLatLong = TransformationHelper.calculateSouthWestCorner(new LatLongCoord(buildingCenterPoint.getLatitude(),
                        buildingCenterPoint.getLongitude()), rotationAngle, metersPerPixel, imagePixelWidth, imagePixelHeight);
                break;
            default:
                calculatedLatLong = null;
        }

        return new BuildingPositionAnchor(calculatedLatLong[0], calculatedLatLong[1]);

    }


    private CalculationAlgorithm getAlgorithmFromText(String text) {

        CalculationAlgorithm calculationAlgorithm = null;
        if (text.equals("WIFI")) {
            calculationAlgorithm = CalculationAlgorithm.WIFI;
        }

        return calculationAlgorithm;

    }


    private List<RadioMap> getAllRadioMaps(RadioMapRepository radioMapRepository, long[] radioMapFileIdentifiers) {
        List<RadioMap> radioMaps = new ArrayList<>();

        for (long radioMapFileIdentifier : radioMapFileIdentifiers) {
            RadioMap radioMap = radioMapRepository.findOne(radioMapFileIdentifier);
            if (radioMap != null) {
                radioMaps.add(radioMap);
            }
        }

        return radioMaps;
    }

    private String replaceFileEnding(File file, String newFileEnding) {

        String fileName = file.getName();
        fileName = fileName.substring(0, fileName.lastIndexOf("."));

        return String.format("%s.%s", fileName, newFileEnding);

    }

    private boolean doInitialImageFolderSetup(String buildingName) {

        try {

            if (!Files.exists(Paths.get(PersistencyConstants.LOCAL_RESOURCE_DIR))) {
                Files.createDirectory(Paths.get(PersistencyConstants.LOCAL_RESOURCE_DIR));
            }

            String separator = File.separator;
            Path localFloorMapsPath = Paths.get(String.format("%s%s%s", PersistencyConstants.LOCAL_RESOURCE_DIR, separator,
                    PersistencyConstants.LOCAL_MAPS_DIR));
            if (!Files.exists(localFloorMapsPath)) {

                Files.createDirectory(localFloorMapsPath);
            }

            Path localFloorMapsBuildingPath = Paths.get(String.format("%s%s%s%s%s", PersistencyConstants.LOCAL_RESOURCE_DIR, separator,
                    PersistencyConstants.LOCAL_MAPS_DIR, separator, buildingName));

            if (!Files.exists(localFloorMapsBuildingPath)) {
                Files.createDirectory(localFloorMapsBuildingPath);
            }

            return true;

        } catch (IOException ex) {
            return false;
        }


    }


}
