var dataServiceModule = angular.module('IndoorApp.dataService', []);

function DataService($http, toastService) {
    // API endpoints
    var getBuildingsUrl = 'building/getAllBuildings';
    var getEvalFilesUrl = 'position/getEvalFilesForBuildingId';
    var getRadiomapsUrl = 'position/getRadioMapsForBuildingId';
    var getAlgorithmTypesUrl = 'project/getAllAlgorithmTypes';
    var getAllEvaalFilesUrl = 'position/getAllEvaalEntries';
    var deleteBuildingUrl = 'building/deleteSelectedBuilding';
    var deleteEvaalUrl = 'position/deleteSelectedEvaalFile';
    var deleteProjectUrl = 'project/deleteSelectedProject';


    // Cache
    var buildings = [];
    var evalFiles = [];
    var radiomaps = [];
    var algorithms = [];
    var evaalFiles = [];

    // Service functions
    return {
        // API calls
        loadAllBuildings: function () {
            // api to get all buildings
            // $http returns a promise, which has a then function, which also returns a promise
            var promise = $http.get(getBuildingsUrl).then(function (response) {
                // The then function here is an opportunity to modify the response
                /*console.log("Retrieved buildings:");
                console.log(response);*/
                // save response in cache
                angular.copy(response.data, buildings);

                // The return value gets picked up by the then in the controller.
                return response.data;
            });
            // Return the promise to the controller
            return promise;
        },
        loadEvalFilesForBuilding: function (buildingId) {
            var config = {
                params: {
                    buildingIdentifier: buildingId
                }
            };
            var promise = $http.get(getEvalFilesUrl, config).then(function (response) {
                console.log("Retrieved eval files:");
                console.log(response);

                // save response in cache
                angular.copy(response.data, evalFiles);

                return response.data;
            });
            return promise;
        },
        loadRadiomapsForBuilding: function (buildingId) {
            var config = {
                params: {
                    buildingIdentifier: buildingId
                }
            };
            var promise = $http.get(getRadiomapsUrl, config).then(function (response) {
                console.log("Retrieved radiomaps:");
                console.log(response);

                // save response in cache
                angular.copy(response.data, radiomaps);

                return response.data;
            });
            return promise;
        },
        loadAllAlgorithms: function () {
            var promise = $http.get(getAlgorithmTypesUrl).then(function (response) {
                console.log("Retrieved algorithm types");
                console.log(response);

                // save response in cache
                angular.copy(response.data, algorithms);

                return response.data;
            });
            return promise;
        },
        loadAllEvaals: function () {
            var promise = $http.get(getAllEvaalFilesUrl).then(function (response) {
                // save response in cache
                angular.copy(response.data, evaalFiles);

                return response.data;
            });
            return promise;
        },
        // delete functions
        deleteBuilding: function (buildingId) {
            var config = {
                params: {
                    buildingIdentifier: buildingId
                }
            };
            var promise = $http.delete(deleteBuildingUrl, config)
                .then(function (response) {
                    logMessage = "Building deleted successfully!";
                    toastService.showToast(logMessage, "success-toast");
                    // return response data with promise
                    return response.data;
                }, function errorCallback(response) {
                    // failure
                    logMessage = response.data.message;
                    toastService.showToast(logMessage, "error-toast");
                });
            return promise;
        },
        deleteEvaalFile: function (evaalFileId) {
            var config = {
                params: {
                    evaalFileIdentifier: evaalFileId
                }
            };
            var promise = $http.delete(deleteEvaalUrl, config)
                .then(function (response) {
                    logMessage = "Evaal entry deleted successfully!";
                    toastService.showToast(logMessage, "success-toast");
                    // return response data with promise
                    return response.data;
                }, function errorCallback(response) {
                    // failure
                    logMessage = response.data.message;
                    toastService.showToast(logMessage, "error-toast");
                });
            return promise;
        },
        deleteProject: function (projectId) {
            var config = {
                params: {
                    projectIdentifier: projectId
                }
            };
            var promise = $http.delete(deleteProjectUrl, config)
                .then(function (response) {
                    logMessage = "Project deleted successfully!";
                    toastService.showToast(logMessage, "success-toast");
                    // return response data with promise
                    return response.data;
                }, function errorCallback(response) {
                    // failure
                    logMessage = response.data.message;
                    toastService.showToast(logMessage, "error-toast");
                });
            return promise;
        },
        // access functions
        getCurrentEvalFiles: function () {
            // return a copy of evalFiles
            return [].concat(evalFiles);
        },
        getCurrentRadiomaps: function () {
            // return a copy of radiomaps
            return [].concat(radiomaps);
        },
        getAllBuildings: function () {
            // return a copy of buildings
            return [].concat(buildings);
        },
        getAllAlgorithmTypes: function () {
            // return a copy of algorithms
            return [].concat(algorithms);
        },
        getAllEvaals: function () {
            return [].concat(evaalFiles);
        }
    };
}

dataServiceModule.factory("dataService", DataService);