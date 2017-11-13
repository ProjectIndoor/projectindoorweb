/**
 * @file Angular App for Project Indoor
 */

/**
 * ----------------------------------------------
 * Angular specific entries, like controllers etc.
 * ----------------------------------------------
 */

// definition of the angular app
var app = angular.module('IndoorApp', ['ngMaterial']);

// app configuration, defining color palettes
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
});

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
function MapController() {
    this.$afterViewInit = function () {
        console.log('Hi')
        map.invalidateSize();
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
// map DOM element
mapDiv = document.getElementById("map")

// create a map inside the map DOM element
var map = L.map('map', {
    maxZoom: 20,
    minZoom: 18,
    crs: L.CRS.Simple
}).setView([0, 0], 20);

var southWest = map.unproject([0, 1536], map.getMaxZoom());
var northEast = map.unproject([2560, 0], map.getMaxZoom());
map.setMaxBounds(new L.LatLngBounds(southWest, northEast));

L.tileLayer('https://doblix.de/tiles/hft/building_2/floor_3/{z}/map_{x}_{y}.png', {
    attribution: 'Map data &copy; HfT Stuttgart',
    maxZoom: 20,
}).addTo(map);

// pixel coordinates in large image
var m = {
    x: 320,
    y: 320
}

// icon definition for access points
var icAP = L.icon({
    iconUrl: '/icons/access-point.png',
    iconSize: [36, 36],
    iconAnchor: [36, 36],
    popupAnchor: [-18, -18],
});

// a demo marker
var marker = L.marker(map.unproject([m.x, m.y], map.getMaxZoom()), {icon: icAP}).addTo(map);
marker.bindPopup("<b>Access Point</b><br>AP 1");

// resize map to use full height, also on window resize
$(window).on("resize", function () {
    $("#map").height($(window).height()).width($(window).width());
    map.invalidateSize();
}).trigger("resize");

