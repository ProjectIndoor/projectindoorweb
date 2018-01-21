var mapModule = angular.module('IndoorApp.map', [
    'ngRoute',
    'openlayers-directive',
    'IndoorApp.mapService',
    'IndoorApp.calculationService',
    'IndoorApp.resultView',
    'IndoorApp.mapChooser',
    'IndoorApp.trackChooser',
    'IndoorApp.algoChooser',
    'IndoorApp.viewSettings'
]);

// add page route
mapModule.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/map', {
        title: 'Map View',
        templateUrl: 'pages/map.html',
        controller: 'MapCtrl'
    });
}]);

// controller which handles the map
function MapController($scope, mapService, calculationService) {
    // is map choosen already?
    $scope.floorChoosen = function () {
        return calculationService.isBuildingSet() && mapService.currentFloorLevel() != -1;
    };

    // setup usage of map service
    angular.extend($scope, {
        mapCenter: mapService.mapCenter,
        mapDefaults: mapService.mapDefaults,
        mapView: mapService.mapView,
        map: mapService.map,
        calcPointLayer: mapService.calcPointLayer,
        refPointLayer: mapService.refPointLayer,
        noRefCalcPointLayer: mapService.noRefCalcPointLayer,
        pointLabels: mapService.pointLabels,
        pathsLayer: mapService.pathsLayer
    });

    // react to calc point clicks
    $scope.$on('openlayers.layers.calclayer.click', function (event, feature) {
        $scope.$apply(function (scope) {
            if (feature) {
                pointInfo = mapService.getCalcPointInfo(feature.getId());
                coords = feature.getGeometry().getCoordinates();
                mapService.clearLabels();
                mapService.addCalcLabel(pointInfo.x, pointInfo.y, pointInfo.error);
            } else {
                mapService.clearLabels();
            }
        });
    });

    // react to no ref calc point clicks
    $scope.$on('openlayers.layers.norefcalclayer.click', function (event, feature) {
        $scope.$apply(function (scope) {
            if (feature) {
                pointInfo = mapService.getNoRefCalcPointInfo(feature.getId());
                coords = feature.getGeometry().getCoordinates();
                mapService.clearLabels();
                mapService.addNoRefCalcLabel(pointInfo.x, pointInfo.y);
            } else {
                mapService.clearLabels();
            }
        });
    });

    // react to ref point clicks
    $scope.$on('openlayers.layers.reflayer.click', function (event, feature) {
        $scope.$apply(function (scope) {
            if (feature) {
                pointInfo = mapService.getRefPointInfo(feature.getId());
                coords = feature.getGeometry().getCoordinates();
                mapService.clearLabels();
                mapService.addRefLabel(pointInfo.x, pointInfo.y);
            } else {
                mapService.clearLabels();
            }
        });
    });
}

mapModule.controller('MapCtrl', MapController);
