/**
 * @file Angular App for Project Indoor
 */

/**
 * ----------------------------------------------
 * Angular specific entries, like controllers etc.
 * ----------------------------------------------
 */

// definition of the angular app
var app = angular.module('IndoorApp', ['ngMaterial', 'ngRoute']);

// ------------- Page routing
app.config(['$routeProvider',

    function ($routeProvider) {
        $routeProvider
            .when('/map', {
                templateUrl: 'pages/mapview.html',
                controller: 'MapCtrl'
            })
            .when('/edit', {
                templateUrl: 'pages/sensor-import.html',
                controller: 'LogImportCtrl'
            })
            .when('/import', {
                templateUrl: 'pages/sensor-import.html',
                controller: 'LogImportCtrl'
            })
            .otherwise({
                redirectTo: '/'
            });
    }]);


// ------------- Color definitions
app.config(function ($mdThemingProvider) {
    // palette for our purple color tone
    $mdThemingProvider.definePalette('inpurple', {
        '50': 'f4e3eb',
        '100': 'e3b9cd',
        '200': 'd08bab',
        '300': 'bd5c89',
        '400': 'af3970',
        '500': 'a11657',
        '600': '99134f',
        '700': '8f1046',
        '800': '850c3c',
        '900': '74062c',
        'A100': 'ffa3bd',
        'A200': 'ff7098',
        'A400': 'ff3d73',
        'A700': 'ff2461',
        'contrastDefaultColor': 'light',
        'contrastDarkColors': [
            '50',
            '100',
            '200',
            'A100',
            'A200'
        ],
        'contrastLightColors': [
            '300',
            '400',
            '500',
            '600',
            '700',
            '800',
            '900',
            'A400',
            'A700'
        ]
    });
    // palette for our blue color tone
    $mdThemingProvider.definePalette('inblue', {
        '50': 'e1e8eb',
        '100': 'b5c6ce',
        '200': '83a0ad',
        '300': '51798c',
        '400': '2c5d74',
        '500': '07405b',
        '600': '063a53',
        '700': '053249',
        '800': '042a40',
        '900': '021c2f',
        'A100': '68b3ff',
        'A200': '359aff',
        'A400': '0280ff',
        'A700': '0074e7',
        'contrastDefaultColor': 'light',
        'contrastDarkColors': [
            '50',
            '100',
            '200',
            'A100',
            'A200'
        ],
        'contrastLightColors': [
            '300',
            '400',
            '500',
            '600',
            '700',
            '800',
            '900',
            'A400',
            'A700'
        ]
    });
    $mdThemingProvider.theme('default')
        .primaryPalette('inpurple')
        .accentPalette('inblue');
    // add a palette variation for the toolbar
    var whiteMap = $mdThemingProvider.extendPalette('inpurple', {'500': '#ffffff', 'contrastDefaultColor': 'dark'});
    $mdThemingProvider.definePalette('inwhite', whiteMap);
});

// ------------- Controllers
// controller which handles the navigation
app.controller('NavCtrl', function ($scope, $timeout, $mdSidenav) {
    $scope.toggleLeft = buildToggler('left');
    $scope.toggleRight = buildToggler('right');

    function buildToggler(componentId) {
        return function () {
            $mdSidenav(componentId).toggle();
        };
    }

    $scope.buildings = ('HFT Building 1;HFT Building 2;HFT Building 3').split(';').map(function (building) {
        return {abbrev: building};
    });


    $scope.floors = ('Floor 1;Floor 2;Floor 3').split(';').map(function (floor) {
        return {abbrev: floor};
    });

});

// controller which handles the map
function MapController($scope) {

    $scope.initMap = function () {


        // map DOM element
        mapDiv = document.getElementById("map")

        var extent = [0, 0, 2560, 1536];
        var projection = new ol.proj.Projection({
            code: 'hft-image',
            units: 'pixels',
            extent: extent
        });

        var map = new ol.Map({
            target: 'map',
            layers: [
                //new ol.layer.Tile({
                //    source: new ol.source.OSM()
                //}),
                new ol.layer.Image({
                    source: new ol.source.ImageStatic({
                        url: '/maps/building_2_floor_3.png',
                        projection: projection,
                        imageExtent: extent
                    })
                })
            ],
            view: new ol.View({
                //center: ol.proj.fromLonLat([37.41, 8.82]),
                //zoom: 19
                projection: projection,
                center: ol.extent.getCenter(extent),
                zoom: 2,
                maxZoom: 8
            })
        });

    };

    this.$afterViewInit = function () {
        console.log('Hi')
        //map.invalidateSize();
    };
}

app.controller('MapCtrl', MapController);

// controller which handles the log import view
function LogImportController($scope) {
    $scope.upload = function () {
        angular.element(document.querySelector('#inputFile')).click();
    };
}

app.controller('LogImportCtrl', LogImportController);

/**
 * ----------------------------------------------
 * Non angular specific entries, like map initializing
 * ----------------------------------------------
 */
// resize map to use full height, also on window resize
$(window).on("resize", function () {
    $("#map").height($(window).height()).width($(window).width());
    map.invalidateSize();
}).trigger("resize");