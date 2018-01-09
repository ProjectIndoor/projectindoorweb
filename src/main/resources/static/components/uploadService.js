var uploadServiceModule = angular.module('IndoorApp.uploadService', ['IndoorApp.toastService']);

// Upload service (send data to the server e.g. log files)
function UploadService($http, toastService) {
    //api endpoints
    var buildingUploadUrl = 'building/addNewBuilding';
    var logFileUploadUrl = 'position/processRadioMapFiles';
    var evalFileUploadUrl = 'position/processEvalFiles';
    var floorUploadUrl = 'building/addFloorToBuilding';


    // service functions
    return {
        uploadBuilding: function (newBuilding) {
            var postData = newBuilding;

            var promise = $http({
                method: 'POST',
                url: buildingUploadUrl,
                data: postData,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then(function (response) {
                logMessage = response.data.message;
                toastService.showToast(logMessage, "success-toast");
                return response.data;
            }, function errorCallback(response) {
                logMessage = response.data.message;
                toastService.showToast(logMessage, "error-toast");
            });
            return promise;
        },
        uploadRadioMap: function (radioMapSet) {
            if (!radioMapSet.radioMapFiles) {
                if (radioMapSet.buildingIdentifier !== 0) {
                    logMessage = "Please choose a file to upload";
                    toastService.showToast(logMessage, "error-toast");
                }
            } else if (radioMapSet.tpFiles.length && radioMapSet.tpFiles.length !== radioMapSet.radioMapFiles.length) {
                logMessage = "Number of tp files and radiomap files should match";
                toastService.showToast(logMessage, "error-toast");
            } else {
                // body content (log files and buildingId)
                var formData = new FormData();
                formData.append('buildingIdentifier', radioMapSet.buildingIdentifier);
                for (var i = 0; i < radioMapSet.radioMapFiles.length; i++) {
                    formData.append('radioMapFiles', radioMapSet.radioMapFiles[i]);
                }
                for (var j = 0; j < radioMapSet.tpFiles.length; j++) {
                    formData.append('transformedPointsFiles', radioMapSet.tpFiles[j]);
                }


                $http({
                    method: 'POST',
                    url: logFileUploadUrl,
                    data: formData,
                    transformRequest: function (data, headersGetterFunction) {
                        return data;
                    },
                    headers: {
                        'Content-Type': undefined
                    }
                }).then(function successCallback(response) {
                    // success
                    logMessage = response.data.message;
                    toastService.showToast(logMessage, "success-toast");
                }, function errorCallback(response) {
                    // failure
                    logMessage = "Error while uploading radio map data";
                    toastService.showToast(logMessage, "error-toast");
                });
            }
        },
        uploadEvaluationFile: function (evaluationSet) {
            if (evaluationSet.evalFiles[0] === null) {
                if (evaluationSet.buildingIdentifier !== 0) {
                    logMessage = "Please choose a file to upload";
                    toastService.showToast(logMessage, "error-toast");
                }
            } else if (evaluationSet.tpFiles.length && evaluationSet.tpFiles.length !== evaluationSet.evalFiles.length) {
                logMessage = "Number of tp files and eval files should match";
                toastService.showToast(logMessage, "error-toast");
            } else {
                // body content (eval files and buildingId)
                var formData = new FormData();
                formData.append('buildingIdentifier', evaluationSet.buildingIdentifier);
                formData.append('evalFiles', evaluationSet.evalFiles[0]);
                for (var j = 0; j < evaluationSet.tpFiles.length; j++) {
                    formData.append('transformedPointsFiles', evaluationSet.tpFiles[j]);
                }

                $http({
                    method: 'POST',
                    url: evalFileUploadUrl,
                    data: formData,
                    transformRequest: function (data, headersGetterFunction) {
                        return data;
                    },
                    headers: {
                        'Content-Type': undefined
                    }
                }).then(function successCallback(response) {
                    // success
                    logMessage = response.data.message;
                    toastService.showToast(logMessage, "success-toast");
                }, function errorCallback(response) {
                    // failure
                    logMessage = "Error while uploading evaluation data";
                    toastService.showToast(logMessage, "error-toast");
                });
            }
        },
        uploadFloorMap: function (floorSet) {
            if (floorSet.floorFiles[0] === null) {
                logMessage = "Please choose an image file to upload";
                toastService.showToast(logMessage, "error-toast");
            } else {
                // body content (floor file, floorId and buildingId)
                var formData = new FormData();
                formData.append('buildingIdentifier', floorSet.building.buildingId);
                formData.append('floorIdentifier', floorSet.floorIdentifier);
                formData.append('floorName', floorSet.floorName);
                formData.append('floorMapFile', floorSet.floorFiles[0]);

                $http({
                    method: 'POST',
                    url: floorUploadUrl,
                    data: formData,
                    transformRequest: function (data, headersGetterFunction) {
                        return data;
                    },
                    headers: {
                        'Content-Type': undefined
                    }
                }).then(function successCallback(response) {
                    // success
                    logMessage = response.data.message;
                    toastService.showToast(logMessage, "success-toast");
                }, function errorCallback(response) {
                    // failure
                    logMessage = "Error while uploading floor map";
                    toastService.showToast(logMessage, "error-toast");
                });
            }
        }
    };
}

uploadServiceModule.factory("uploadService", UploadService);
