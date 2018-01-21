var algoChooserModule = angular.module('IndoorApp.algoChooser', [
    'IndoorApp.calculationService',
    'IndoorApp.mapService',
    'IndoorApp.dataService']);

function AlgorithmController($scope, dataService, calculationService, mapService) {
    // decide when to hide/show
    $scope.algoShow = calculationService.isEvalSet;

    // load algorithms from server
    dataService.loadAllAlgorithms();

    // choosen algorithm (initialize from calculation service)
    $scope.choosenAlgorithm = calculationService.getAlgorithmAndParameters();

    // choosen radio maps
    $scope.radiomaps = calculationService.getRadiomaps();

    // available radiomaps for selected building
    $scope.availableRadiomaps = dataService.getCurrentRadiomaps;

    // check if loaded value is available
    $scope.checkLoadedParamValue = function (paramName) {
        //check if an value is available for this name
        if ($scope.choosenAlgorithm.projectParameters) {
            var param = $scope.choosenAlgorithm.projectParameters.find(function (pParams) {
                return pParams.name === paramName && pParams.value !== undefined;
            });
            return param.value;
        }
    };

    // handle strings received by API
    $scope.checkIntegerParamValue = function (paramValue) {
        return parseInt(paramValue);
    };
    $scope.checkFloatParamValue = function (paramValue) {
        return parseFloat(paramValue);
    };
    $scope.checkLoadedBooleanValue = function (projParam) {
        var lValue = $scope.checkLoadedParamValue(projParam.name) || projParam.defaultValue;
        return 'true' === lValue;
    };

    // available algorithms (inject loaded parameters if available)
    $scope.availableAlgorithms = function () {
        // load available algorithms
        var availAlgos = dataService.getAllAlgorithmTypes();
        // inject currently loaded/choosen algorithm
        availAlgos.forEach(function (algo) {
            if (algo.niceName === $scope.choosenAlgorithm.niceName) {
                algo.projectParameters = $scope.choosenAlgorithm.projectParameters;
            }
        });
        return availAlgos;
    };

    // action for calculation button
    $scope.calculatePos = function () {
        // clear map
        mapService.clearMap();

        // set choosen values for calculation
        calculationService.setRadiomaps($scope.radiomaps);
        calculationService.setAlgorithmAndParameters($scope.choosenAlgorithm);
        // run calculation and show results
        calculationService.generatePositions().then(function (data) {
            var posis = data;
            var errorList = [];
            var refCounter = 0;
            var errorSum = 0;

            var currentFloor = mapService.currentFloorLevel();
            for (var i = 0; i < posis.length; i++) {
                var calcP = posis[i].calculatedPosition;
                var refP = posis[i].referencePosition;
                var error = posis[i].distanceInMeters;

                // only add point if floors match
                if (Math.round(calcP.z) === currentFloor) {
                    // if no reference is available put points in a separate list
                    if (refP !== null) {
                        // only when a ref is available error is considered
                        errorList.push(error);
                        errorSum += error;
                        refCounter++;
                        // after sum error is rounded for a nicer displayed result
                        error = Math.round(error * 10) / 10;
                        mapService.addCalcPoint(calcP.x, calcP.y, error);
                        mapService.addRefPoint(refP.x, refP.y);
                        mapService.addErrorLine(calcP.x, calcP.y, refP.x, refP.y);
                    } else {
                        mapService.addNoRefCalcPoint(calcP.x, calcP.y)
                    }
                }
            }

            // sort the errors
            errorList.sort(function (a, b) {
                return a - b;
            });

            // get or calculate result values
            var medianIndex = Math.round(errorList.length / 2);
            var thirdQuartilIndex = Math.round((errorList.length / 4) * 3);

            var medianError = errorList[medianIndex];
            var thirdQuartilError = errorList[thirdQuartilIndex];
            var averageError = errorSum / refCounter;
            var maxError = errorList.pop();


            // set results in calculation service
            calculationService.setResult(errorSum, averageError, medianError, thirdQuartilError, maxError);
        });

        mapService.displayLines();
    };
}

algoChooserModule.controller('AlgorithmCtrl', AlgorithmController);