var mapModule = angular.module('IndoorApp.map', [
    'ngRoute',
    'openlayers-directive',
    'IndoorApp.mapService',
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
function MapController($scope, mapService) {
    // example map service setup
    mapService.setMap("maps/hft_2_floor_3.png", 3688, 2304);

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
