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
    $scope.floorChoosen = calculationService.isBuildingSet;

    // setup usage of map service
    angular.extend($scope, {
        mapCenter: mapService.mapCenter,
        mapDefaults: mapService.mapDefaults,
        map: mapService.map,
        calcPoints: mapService.calcPoints,
        refPoints: mapService.refPoints,
        noRefCalcPoints: mapService.noRefCalcPoints,
        pathsLayer: mapService.pathsLayer
    });
}

mapModule.controller('MapCtrl', MapController);
