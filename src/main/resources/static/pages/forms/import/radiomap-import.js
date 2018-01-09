var radiomapImportModule = angular.module('IndoorApp.importRadiomap', ['IndoorApp.uploadService', 'IndoorApp.dataService']);

// controller which handles the log import view
function LogImportController($scope, uploadService, dataService) {
    // show file chooser on button click
    $scope.uploadLogClick = function () {
        angular.element(document.querySelector('#inputFile')).click();
    };
    $scope.uploadTransformedClick = function () {
        angular.element(document.querySelector('#transformedPointsFile')).click();
    };

    dataService.loadAllBuildings();

    // buildings to show for chooser
    $scope.buildings = dataService.getAllBuildings;

    // parameters needed to upload log file
    $scope.logFileParameters = {
        buildingIdentifier: 0,
        radioMapFiles: [],
        tpFiles: []
    };

    $scope.getTheFiles = function ($files) {
        $scope.logFileParameters.radioMapFiles = $files;
        // set filename on ui
        $scope.fileUploaded = $files[0].name;
        if ($files.length > 1) {
            $scope.fileUploaded += " and " + ($files.length - 1) + " more file(s)";
        }
        // notify changed scope to display file name
        $scope.$apply();
    };

    $scope.getTpFiles = function ($files) {
        $scope.logFileParameters.tpFiles = $files;
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
    $scope.uploadFiles = function () {
        uploadService.uploadRadioMap($scope.logFileParameters);
    }
}

radiomapImportModule.controller('LogImportCtrl', LogImportController);
