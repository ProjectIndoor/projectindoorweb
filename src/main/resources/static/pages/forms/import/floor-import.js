var floorImportModule = angular.module('IndoorApp.importFloor', ['IndoorApp.uploadService', 'IndoorApp.dataService']);

// controller which handles the floor import view
function FloorImportController($scope, dataService, uploadService) {
    // show file chooser on button click
    $scope.floorUpload = function () {
        angular.element(document.querySelector('#floorInputFile')).click();
    };

    dataService.getAllBuildings();

    // buildings to show for chooser
    $scope.buildings = dataService.getAllBuildings;

    // parameters needed to upload eval file
    $scope.floorParameters = {
        buildingIdentifier: 0,
        floorFiles: []
    };

    // show floors of a selected building
    $scope.floors = function () {
        if ($scope.floorParameters.building) {
            return $scope.floorParameters.building.buildingFloors;
        }
    };

    $scope.getFloorFiles = function ($files) {
        $scope.floorParameters.floorFiles = $files;
        $scope.fileUploaded = "File: " + $files[0].name;
        // notify changed scope to display file name
        $scope.$apply();
    };

    //The success or error message
    $scope.uploadStatus = false;

    //Post the file and parameters
    $scope.uploadFloor = function () {
        console.log($scope.floorParameters);
        uploadService.uploadFloorMap($scope.floorParameters);
    }
}

floorImportModule.controller('FloorImportCtrl', FloorImportController);
