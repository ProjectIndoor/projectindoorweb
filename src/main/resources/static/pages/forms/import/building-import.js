var buildImportModule = angular.module('IndoorApp.importBuilding', ['IndoorApp.uploadService', 'IndoorApp.dataService']);

// controller which handles the building import view
function BuildingImportController($scope, uploadService, dataService) {

    // add new empty floor
    $scope.addFloorFields = function () {
        var newFloor = {
            id: $scope.building.floors.length,
            mapUrl: null
        };
        $scope.building.floors.push(newFloor);
    };

    // remove last added floor until empty
    $scope.removeLastFloorField = function () {
        if ($scope.building.floors.length > 0) {
            $scope.building.floors.pop();
        }
    };

    // pre populated data
    $scope.buildingCAR = {
        buildingName: "CAR2",
        numberOfFloors: 1,
        imagePixelWidth: 1282,
        imagePixelHeight: 818,
        northWestAnchor: {
            latitude: 40.313342,
            longitude: -3.484113
        },
        northEastAnchor: {
            latitude: 40.313438,
            longitude: -3.483299
        },
        southEastAnchor: {
            latitude: 40.313041,
            longitude: -3.483226
        },
        southWestAnchor: {
            latitude: 40.312959,
            longitude: -3.484038
        },
        buildingCenterPoint: {
            latitude: 48.77966682484418,
            longitude: 9.1738866322615
        },
        rotationAngle: 0.15318405778903832,
        metersPerPixel: 0.05207600
    };

    $scope.building = {
        buildingName: "HFT Building 2",
        numberOfFloors: 5,
        imagePixelWidth: 3688,
        imagePixelHeight: 2304,
        northWestAnchor: {
            latitude: 48.77951340793322,
            longitude: 9.173423636538017
        },
        northEastAnchor: {
            latitude: 48.78002331402018,
            longitude: 9.173034525813376
        },
        southEastAnchor: {
            latitude: 48.78017673093113,
            longitude: 9.173497521536861
        },
        southWestAnchor: {
            latitude: 48.77966682484418,
            longitude: 9.1738866322615
        }
    };

    $scope.uploadBuildingData = function () {
        uploadService.uploadBuilding($scope.building).then(function (data) {
            // update building list when building added
            dataService.loadAllBuildings();
        });
        console.log($scope.building);
    }
}

buildImportModule.controller('BuildingImportCtrl', BuildingImportController);


