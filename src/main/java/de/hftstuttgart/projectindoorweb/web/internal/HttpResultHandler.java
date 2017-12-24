package de.hftstuttgart.projectindoorweb.web.internal;

import de.hftstuttgart.projectindoorweb.web.internal.util.ResponseConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class HttpResultHandler {

    private static HttpResultHandler instance;

    private Map<String, HttpStatus> buildingOperationResultMap = new HashMap<>();
    private Map<String, HttpStatus> positionOperationResultMap = new HashMap<>();
    private Map<String, HttpStatus> projectOperationResultMap = new HashMap<>();

    public static final String SHOULD_NOT_HAPPEN = "HTTP message type not found";

    private HttpResultHandler() {

        initBuildingOperationResultMap();
        initPositioningOperationResultMap();
        initProjectOperationResultMap();

    }

    public static HttpResultHandler getInstance() {
        if (instance == null) {
            instance = new HttpResultHandler();
        }

        return instance;
    }

    public ResponseEntity handleSimpleBuildingResult(String operationResultMessage) {

        return getResponseEntity(buildingOperationResultMap, operationResultMessage);

    }

    public ResponseEntity handleSimpleProjectResult(String operationResultMessage) {

        return getResponseEntity(projectOperationResultMap, operationResultMessage);

    }

    public ResponseEntity handleSimplePositioningResult(String operationResultMessage) {

        return getResponseEntity(positionOperationResultMap, operationResultMessage);

    }

    private void initBuildingOperationResultMap() {
        buildingOperationResultMap.put(ResponseConstants.BUILDING_GENERAL_FAILURE_ID_NOT_FOUND, HttpStatus.UNPROCESSABLE_ENTITY);
        buildingOperationResultMap.put(ResponseConstants.BUILDING_GENERAL_FAILURE_NO_DATA_RECEIVED, HttpStatus.BAD_REQUEST);
        buildingOperationResultMap.put(ResponseConstants.BUILDING_UPDATE_SUCCESS, HttpStatus.OK);
        buildingOperationResultMap.put(ResponseConstants.BUILDING_UPDATE_FAILURE_DB_WRITE, HttpStatus.INTERNAL_SERVER_ERROR);
        buildingOperationResultMap.put(ResponseConstants.FLOOR_UPDATE_FAILURE_INVALID_DATA_RECEIVED, HttpStatus.BAD_REQUEST);
        buildingOperationResultMap.put(ResponseConstants.FLOOR_UPDATE_FAILURE_ID_NOT_FOUND, HttpStatus.BAD_REQUEST);
        buildingOperationResultMap.put(ResponseConstants.FLOOR_UPDATE_SUCCESS, HttpStatus.OK);
        buildingOperationResultMap.put(ResponseConstants.FLOOR_UPDATE_FAILURE_LOCAL_WRITE, HttpStatus.INTERNAL_SERVER_ERROR);
        buildingOperationResultMap.put(ResponseConstants.FLOOR_UPDATE_FAILURE_DB_WRITE, HttpStatus.INTERNAL_SERVER_ERROR);
        buildingOperationResultMap.put(ResponseConstants.BUILDING_DELETE_SUCCESS, HttpStatus.OK);
        buildingOperationResultMap.put(ResponseConstants.BUILDING_DELETE_FAILURE_DB_WRITE, HttpStatus.INTERNAL_SERVER_ERROR);
        buildingOperationResultMap.put(ResponseConstants.BUILDING_DELETE_FAILURE_CONSTRAINT_VIOLATION, HttpStatus.NOT_ACCEPTABLE);
        buildingOperationResultMap.put(ResponseConstants.GENERAL_FAILURE_UNPARSABLE_NUMBER, HttpStatus.BAD_REQUEST);
    }

    private void initPositioningOperationResultMap() {
        positionOperationResultMap.put(ResponseConstants.GENERAL_FAILURE_UNPARSABLE_NUMBER, HttpStatus.BAD_REQUEST);
        positionOperationResultMap.put(ResponseConstants.EVAAL_GENERAL_FAILURE_NO_DATA_RECEIVED, HttpStatus.BAD_REQUEST);
        positionOperationResultMap.put(ResponseConstants.EVAAL_PROCESSING_GENERAL_FAILURE_RADIOMAPS_NULL, HttpStatus.BAD_REQUEST);
        positionOperationResultMap.put(ResponseConstants.EVAAL_PROCESSING_SUCCESS, HttpStatus.OK);
        positionOperationResultMap.put(ResponseConstants.EVAAL_PROCESSING_FAILURE_FILE_LIST_LENGTH_UNEQUAL, HttpStatus.BAD_REQUEST);
        positionOperationResultMap.put(ResponseConstants.EVAAL_SAVE_FAILURE_DB_WRITE, HttpStatus.INTERNAL_SERVER_ERROR);
        positionOperationResultMap.put(ResponseConstants.EVAAL_DELETE_SUCCESS, HttpStatus.OK);
        positionOperationResultMap.put(ResponseConstants.EVAAL_DELETE_FAILURE_DB_WRITE, HttpStatus.INTERNAL_SERVER_ERROR);
        positionOperationResultMap.put(ResponseConstants.EVAAL_DELETE_FAILURE_CONSTRAINT_VIOLATION, HttpStatus.NOT_ACCEPTABLE);
    }

    private void initProjectOperationResultMap() {
        projectOperationResultMap.put(ResponseConstants.GENERAL_FAILURE_UNPARSABLE_NUMBER, HttpStatus.BAD_REQUEST);
        projectOperationResultMap.put(ResponseConstants.PROJECT_GENERAL_FAILURE_NO_DATA_RECEIVED, HttpStatus.BAD_REQUEST);
        projectOperationResultMap.put(ResponseConstants.PROJECT_UPDATE_FAILURE_INVALID_DATA, HttpStatus.BAD_REQUEST);
        projectOperationResultMap.put(ResponseConstants.PROJECT_UPDATE_FAILURE_ALGORITHM_NULL, HttpStatus.BAD_REQUEST);
        projectOperationResultMap.put(ResponseConstants.PROJECT_UPDATE_FAILURE_ID_NOT_FOUND, HttpStatus.BAD_REQUEST);
        projectOperationResultMap.put(ResponseConstants.PROJECT_UPDATE_SUCCESS, HttpStatus.OK);
        projectOperationResultMap.put(ResponseConstants.PROJECT_UPDATE_FAILURE_DB_WRITE, HttpStatus.INTERNAL_SERVER_ERROR);
        projectOperationResultMap.put(ResponseConstants.PROJECT_DELETE_SUCCESS, HttpStatus.OK);
        projectOperationResultMap.put(ResponseConstants.PROJECT_DELETE_FAILURE_DB_WRITE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity getResponseEntity(Map<String, HttpStatus> operationResultMap, String operationResultMessage) {
        for (Map.Entry<String, HttpStatus> entry : operationResultMap.entrySet()) {
            String actualOperationResult = entry.getKey();
            HttpStatus actualHttpStatus = entry.getValue();

            if (actualOperationResult.equals(operationResultMessage)) {
                return createResponseEntity(actualHttpStatus, actualOperationResult);
            }
        }
        return createResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, SHOULD_NOT_HAPPEN);
    }

    private ResponseEntity createResponseEntity(HttpStatus status, Object body) {
        return ResponseEntity
                .status(status)
                .body(body);
    }
}
