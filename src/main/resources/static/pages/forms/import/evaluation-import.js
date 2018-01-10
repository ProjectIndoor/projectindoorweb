var evalImportModule = angular.module('IndoorApp.importEval', ['IndoorApp.uploadService', 'IndoorApp.dataService']);

// controller which handles the eval import view
function EvaluationImportController($scope, dataService, uploadService) {
    // show file chooser on button click
    $scope.evalUpload = function () {
        angular.element(document.querySelector('#evalInputFile')).click();
    };
    $scope.uploadTransformedClick = function () {
        angular.element(document.querySelector('#transformedPointsFileEval')).click();
    };

    dataService.getAllBuildings();

    // buildings to show for chooser
    $scope.buildings = dataService.getAllBuildings;

    // parameters needed to upload eval file
    $scope.evalFileParameters = {
        buildingIdentifier: 0,
        evalFiles: [],
        tpFiles: []
    };

    $scope.getEvalFiles = function ($files) {
        $scope.evalFileParameters.evalFiles = $files;
        $scope.fileUploaded = "File: " + $files[0].name;
        // notify changed scope to display file name
        $scope.$apply();
    };

    $scope.getTpFiles = function ($files) {
        $scope.evalFileParameters.tpFiles = $files;
        // set filename on ui
        $scope.tpFileUploaded = $files[0].name;
        if ($files.length > 1) {
            $scope.tpFileUploaded += " and " + ($files.length - 1) + " more file(s)";
        }
        // notify changed scope to display file name
        $scope.$apply();
    };

    //The success or error message
    $scope.uploadStatus = false;

    //Post the file and parameters
    $scope.uploadEvaluation = function () {
        console.log($scope.evalFileParameters);
        uploadService.uploadEvaluationFile($scope.evalFileParameters);
    }
}

evalImportModule.controller('EvalImportCtrl', EvaluationImportController);