var calculationServiceModule = angular.module('IndoorApp.calculationService', ['IndoorApp.toastService']);

// Calculation service (setup and call position calculations)
function CalculationService($http, toastService) {
    //api endpoints
    var generatePositionsUrl = 'position/generateBatchPositionResults';
    var createProjectUrl = 'project/saveNewProject';

    // properties
    var currentBuilding = {};
    var evalFile = {};
    var radioMapFileIds;
    var algorithmType;
    var projectParameters;
    var asPixel = true;

    // result cache
    var result;

    // loaded projectInfo
    var loadedProject = {
        projectName: "",
        loadedProjectId: null
    };

    // workflow progress
    var workflowProgress = 0;

    return {
        // set and get progress
        isEvalSet: function () {
            return !angular.equals(evalFile, {});
        },
        isBuildingSet: function () {
            return !angular.equals(currentBuilding, {});
        },
        isAlgorithmReady: function () {
            return currentBuilding && evalFile && radioMapFileIds && algorithmType && projectParameters;
        },
        hasResult: function () {
            return result;
        },
        // set and get building
        getCurrentBuilding: function () {
            return currentBuilding;
        },
        setCalculationBuilding: function (building) {
            currentBuilding = building;
            console.log("Building Changed: " + currentBuilding);
        },
        // set and get eval file
        getEvalFile: function () {
            return evalFile;
        },
        setEvalFile: function (evalF) {
            evalFile = evalF;
            console.log("Eval File Changed: " + evalFile);
        },
        // set and get radiomaps
        getRadiomaps: function () {
            if (radioMapFileIds) {
                return [].concat(radioMapFileIds);
            }
        },
        setRadiomaps: function (radiomapIds) {
            radioMapFileIds = radiomapIds;
            console.log("Radiomaps Changed: " + radioMapFileIds);
        },
        // set and get algorithm
        getAlgorithmAndParameters: function () {
            var currentAlgorithm = {
                niceName: algorithmType,
                projectParameters: projectParameters
            };
            return currentAlgorithm;
        },
        setAlgorithmAndParameters: function (choosenAlgorithm) {
            algorithmType = choosenAlgorithm.niceName;
            console.log("Algorithm set: " + algorithmType);
            projectParameters = choosenAlgorithm.applicableParameters;
        },
        // set and get loaded project
        getCurrentProject: function () {
            return loadedProject;
        },
        setCalculationProject: function (project) {
            loadedProject.projectName = project.projectName;
            loadedProject.projectId = project.projectId;
            console.log("Building Changed: " + currentBuilding);
        },
        // set and get results
        getResult: function () {
            return result;
        },
        setResult: function (sum, average, median, thirdQuartil, max) {
            // Round saved result
            var rSum = Math.round(sum * 100) / 100;
            var rAverage = Math.round(average * 100) / 100;
            var rMedian = Math.round(median * 100) / 100;
            var rThirdQuartil = Math.round(thirdQuartil * 100) / 100;
            var rMax = Math.round(max * 100) / 100;

            result = {
                errorSum: rSum,
                averageError: rAverage,
                medianError: rMedian,
                thirdQuartilError: rThirdQuartil,
                maxError: rMax
            }
        },
        // API calls
        generatePositions: function () {
            var data = {
                buildingIdentifier: currentBuilding.buildingId,
                evaluationFile: evalFile.id,
                radioMapFiles: radioMapFileIds,
                algorithmType: algorithmType,
                projectParameters: projectParameters,
                withPixelPosition: asPixel
            };
            var config = {
                headers: {
                    'Content-Type': 'application/json'
                }
            };
            var promise = $http.post(generatePositionsUrl, data, config).then(function (response) {
                console.log("Retrieved positions:");
                console.log(response);

                return response.data;
            });
            return promise;
        },
        saveCurrentProject: function (project) {
            var data = {
                projectName: project.projectName,
                buildingIdentifier: currentBuilding.buildingId,
                evalFileIdentifier: evalFile.id,
                radioMapFileIdentifiers: radioMapFileIds,
                algorithmType: algorithmType,
                projectParameters: projectParameters
            };
            var config = {
                headers: {
                    'Content-Type': 'application/json'
                }
            };
            var promise = $http.post(createProjectUrl, data, config).then(function (response) {
                logMessage = response.data.message;
                toastService.showToast(logMessage, "success-toast");
                return response.data;
            }, function errorCallback(response) {
                logMessage = response.data.message;
                toastService.showToast(logMessage, "error-toast");
            });
            return promise;
        },
        loadDataFromProject: function (project) {
            console.log("Load project");
            console.log(project);
            algorithmType = project.algorithmType;
            currentBuilding.buildingId = project.buildingIdentifier;
            evalFile.id = project.evalFileIdentifier;
            radioMapFileIds = project.radioMapFileIdentifiers;
            projectParameters = project.saveNewProjectParametersSet;
            this.setCalculationProject(project);
        }
    }
}

calculationServiceModule.factory("calculationService", CalculationService);