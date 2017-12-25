package de.hftstuttgart.projectindoorweb.web.internal;

import de.hftstuttgart.projectindoorweb.web.internal.util.ResponseConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class HttpResultHandler {

    private static HttpResultHandler instance;

    private Map<String, HttpStatus> simpleBuildingOperationResultMap = new HashMap<>();
    private Map<String, HttpStatus> simplePositionOperationResultMap = new HashMap<>();
    private Map<String, HttpStatus> simpleProjectOperationResultMap = new HashMap<>();

    public static final String SHOULD_NOT_HAPPEN = "HTTP message type not found";

    private HttpResultHandler() {

        initSimpleBuildingOperationResultMap();
        initSimplePositioningOperationResultMap();
        initSimpleProjectOperationResultMap();

    }

    public static HttpResultHandler getInstance() {
        if (instance == null) {
            instance = new HttpResultHandler();
        }

        return instance;
    }

    public ResponseEntity handleSimpleBuildingResult(String operationResultMessage) {

        return getSimpleResponseEntity(simpleBuildingOperationResultMap, operationResultMessage);

    }

    public ResponseEntity handleSimpleProjectResult(String operationResultMessage) {

        return getSimpleResponseEntity(simpleProjectOperationResultMap, operationResultMessage);

    }

    public ResponseEntity handleSimplePositioningResult(String operationResultMessage) {

        return getSimpleResponseEntity(simplePositionOperationResultMap, operationResultMessage);

    }

    private void initSimpleBuildingOperationResultMap() {
        simpleBuildingOperationResultMap.put(ResponseConstants.BUILDING_GENERAL_FAILURE_ID_NOT_FOUND, HttpStatus.UNPROCESSABLE_ENTITY);
        simpleBuildingOperationResultMap.put(ResponseConstants.BUILDING_GENERAL_FAILURE_NO_DATA_RECEIVED, HttpStatus.BAD_REQUEST);
        simpleBuildingOperationResultMap.put(ResponseConstants.BUILDING_UPDATE_SUCCESS, HttpStatus.OK);
        simpleBuildingOperationResultMap.put(ResponseConstants.BUILDING_UPDATE_FAILURE_DB_WRITE, HttpStatus.INTERNAL_SERVER_ERROR);
        simpleBuildingOperationResultMap.put(ResponseConstants.FLOOR_UPDATE_FAILURE_INVALID_DATA_RECEIVED, HttpStatus.BAD_REQUEST);
        simpleBuildingOperationResultMap.put(ResponseConstants.FLOOR_UPDATE_FAILURE_ID_NOT_FOUND, HttpStatus.BAD_REQUEST);
        simpleBuildingOperationResultMap.put(ResponseConstants.FLOOR_UPDATE_SUCCESS, HttpStatus.OK);
        simpleBuildingOperationResultMap.put(ResponseConstants.FLOOR_UPDATE_FAILURE_LOCAL_WRITE, HttpStatus.INTERNAL_SERVER_ERROR);
        simpleBuildingOperationResultMap.put(ResponseConstants.FLOOR_UPDATE_FAILURE_DB_WRITE, HttpStatus.INTERNAL_SERVER_ERROR);
        simpleBuildingOperationResultMap.put(ResponseConstants.BUILDING_DELETE_SUCCESS, HttpStatus.OK);
        simpleBuildingOperationResultMap.put(ResponseConstants.BUILDING_DELETE_FAILURE_DB_WRITE, HttpStatus.INTERNAL_SERVER_ERROR);
        simpleBuildingOperationResultMap.put(ResponseConstants.BUILDING_DELETE_FAILURE_CONSTRAINT_VIOLATION, HttpStatus.NOT_ACCEPTABLE);
        simpleBuildingOperationResultMap.put(ResponseConstants.GENERAL_FAILURE_UNPARSABLE_NUMBER, HttpStatus.BAD_REQUEST);
    }

    private void initSimplePositioningOperationResultMap() {
        simplePositionOperationResultMap.put(ResponseConstants.GENERAL_FAILURE_UNPARSABLE_NUMBER, HttpStatus.BAD_REQUEST);
        simplePositionOperationResultMap.put(ResponseConstants.EVAAL_GENERAL_FAILURE_NO_DATA_RECEIVED, HttpStatus.BAD_REQUEST);
        simplePositionOperationResultMap.put(ResponseConstants.EVAAL_PROCESSING_GENERAL_FAILURE_RADIOMAPS_NULL, HttpStatus.BAD_REQUEST);
        simplePositionOperationResultMap.put(ResponseConstants.EVAAL_PROCESSING_SUCCESS, HttpStatus.OK);
        simplePositionOperationResultMap.put(ResponseConstants.EVAAL_PROCESSING_FAILURE_FILE_LIST_LENGTH_UNEQUAL, HttpStatus.BAD_REQUEST);
        simplePositionOperationResultMap.put(ResponseConstants.EVAAL_SAVE_FAILURE_DB_WRITE, HttpStatus.INTERNAL_SERVER_ERROR);
        simplePositionOperationResultMap.put(ResponseConstants.EVAAL_DELETE_SUCCESS, HttpStatus.OK);
        simplePositionOperationResultMap.put(ResponseConstants.EVAAL_DELETE_FAILURE_DB_WRITE, HttpStatus.INTERNAL_SERVER_ERROR);
        simplePositionOperationResultMap.put(ResponseConstants.EVAAL_DELETE_FAILURE_CONSTRAINT_VIOLATION, HttpStatus.NOT_ACCEPTABLE);
    }

    private void initSimpleProjectOperationResultMap() {
        simpleProjectOperationResultMap.put(ResponseConstants.GENERAL_FAILURE_UNPARSABLE_NUMBER, HttpStatus.BAD_REQUEST);
        simpleProjectOperationResultMap.put(ResponseConstants.PROJECT_GENERAL_FAILURE_NO_DATA_RECEIVED, HttpStatus.BAD_REQUEST);
        simpleProjectOperationResultMap.put(ResponseConstants.PROJECT_UPDATE_FAILURE_INVALID_DATA, HttpStatus.BAD_REQUEST);
        simpleProjectOperationResultMap.put(ResponseConstants.PROJECT_UPDATE_FAILURE_ALGORITHM_NULL, HttpStatus.BAD_REQUEST);
        simpleProjectOperationResultMap.put(ResponseConstants.PROJECT_UPDATE_FAILURE_ID_NOT_FOUND, HttpStatus.BAD_REQUEST);
        simpleProjectOperationResultMap.put(ResponseConstants.PROJECT_UPDATE_SUCCESS, HttpStatus.OK);
        simpleProjectOperationResultMap.put(ResponseConstants.PROJECT_UPDATE_FAILURE_DB_WRITE, HttpStatus.INTERNAL_SERVER_ERROR);
        simpleProjectOperationResultMap.put(ResponseConstants.PROJECT_DELETE_SUCCESS, HttpStatus.OK);
        simpleProjectOperationResultMap.put(ResponseConstants.PROJECT_DELETE_FAILURE_DB_WRITE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity getSimpleResponseEntity(Map<String, HttpStatus> operationResultMap, String operationResultMessage) {
        for (Map.Entry<String, HttpStatus> entry : operationResultMap.entrySet()) {
            String actualOperationResult = entry.getKey();
            HttpStatus actualHttpStatus = entry.getValue();

            if (actualOperationResult.equals(operationResultMessage)) {
                return createSimpleResponseEntity(actualHttpStatus, actualOperationResult);
            }
        }
        return createSimpleResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, SHOULD_NOT_HAPPEN);
    }

    private ResponseEntity createSimpleResponseEntity(HttpStatus status, Object body) {
        return ResponseEntity
                .status(status)
                .body(body);
    }
}
