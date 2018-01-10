var mapChooserModule = angular.module('IndoorApp.mapChooser', ['IndoorApp.calculationService', 'IndoorApp.mapService']);

//Controller to fetch the building using the data service
function BuildingController($scope, dataService, calculationService, mapService) {
    // properties (initialize from calculation service)
    $scope.selectedBuilding = calculationService.getCurrentBuilding();
    $scope.selectedFloor = null;

    // when building is set by loaded project also load files
    if (calculationService.isBuildingSet()) {
        dataService.loadEvalFilesForBuilding($scope.selectedBuilding.buildingId);
        dataService.loadRadiomapsForBuilding($scope.selectedBuilding.buildingId);
    }

    // building list
    $scope.buildings = dataService.getAllBuildings;

    //function to choose correct floor list
    $scope.floors = function () {
        if ($scope.selectedBuilding) {
            return $scope.selectedBuilding.buildingFloors
        }
    };

    //function to use either floor name or level
    $scope.resolveNameOrLevel = function (floor) {
        return floor.floorName || floor.floorLevel;
    };

    // enumeration function
    $scope.getNumber = function (num) {
        return new Array(num);
    };

    // load data from backend with service
    dataService.loadAllBuildings().then();

    $scope.setBuilding = function () {
        // update Map to new building
        mapService.setMap($scope.selectedFloor.floorMapUrl, $scope.selectedBuilding.imagePixelWidth, $scope.selectedBuilding.imagePixelHeight);
        // set building for calculation parameters
        calculationService.setCalculationBuilding($scope.selectedBuilding);
        // load building related evaluation files and radiomaps
        dataService.loadEvalFilesForBuilding($scope.selectedBuilding.buildingId);
        dataService.loadRadiomapsForBuilding($scope.selectedBuilding.buildingId);
    };
}

mapChooserModule.controller('BuildingCtrl', BuildingController);