/**
 * @file Angular App for Project Indoor
 */

/**
 * ----------------------------------------------
 * Angular specific entries, like controllers etc.
 * ----------------------------------------------
 */

// definition of the angular app
var app = angular.module('IndoorApp', ['ngMaterial', 'ngRoute', 'ngMessages', 'openlayers-directive']);

// ------------- Page routing
app.config(['$routeProvider',

    function ($routeProvider) {
        $routeProvider
            .when('/map', {
                title: 'Map View',
                templateUrl: 'pages/map.html',
                controller: 'MapCtrl'
            })
            .when('/edit', {
                title: 'Edit',
                templateUrl: 'pages/edit.html',
                controller: 'LogImportCtrl'
            })
            .when('/import', {
                title: 'Import',
                templateUrl: 'pages/import.html',
                controller: 'LogImportCtrl'
            })
            .otherwise({
                redirectTo: '/map'
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

// ------------- Application run
app.run(['$rootScope', '$route', function ($rootScope, $route) {
    $rootScope.$on('$routeChangeSuccess', function () {
        document.title = $route.current.title;
    });
}]);

// ------------- Services

// Upload service (send data to the server e.g. log files)
function UploadService($http, $mdToast) {
    //api endpoints
    var buildingUploadUrl = '/building/addNewBuilding';
    var logFileUploadUrl = '/position/processRadioMapFiles';
    var evalFileUploadUrl = '/position/processEvalFiles';

    // service functions
    return {
        uploadBuilding: function (newBuilding) {
            var postData = newBuilding;

            $http({
                method: 'POST',
                url: buildingUploadUrl,
                data: postData,
                headers: {
                    'Content-Type': 'application/json'
                }
            }).success(function (data, status, headers, config) {
                logMessage = "Building Data uploaded successfully!";
                showToast(logMessage);
            }).error(function (data, status, headers, config) {
                logMessage = "Error while uploading Building Data";
                showToast(logMessage);
            });
        },
        uploadRadioMap: function (radioMapSet) {
            // request parameters
            var requestParameters = {
                buildingIdentifier: radioMapSet.buildingIdentifier
            };

            // body content (log files)
            var formData = new FormData();
            formData.append('radioMapFiles', radioMapSet.radioMapFiles[0]);

            $http({
                method: 'POST',
                url: logFileUploadUrl,
                params: requestParameters,
                data: formData,
                transformRequest: function (data, headersGetterFunction) {
                    return data;
                },
                headers: {
                    'Content-Type': undefined
                }
            }).then(function successCallback(response) {
                // success
                logMessage = "Radio map uploaded successfully!";
                showToast(logMessage);
            }, function errorCallback(response) {
                // failure
                logMessage = "Error while uploading radio map data";
                showToast(logMessage);
            });
        },
        uploadEvaluationFile: function (evaluationSet) {
            // request parameters
            var requestParameters = {
                buildingIdentifier: evaluationSet.buildingIdentifier
            };

            // body content (eval files)
            var formData = new FormData();
            formData.append('evalFiles', evaluationSet.evalFiles[0]);

            $http({
                method: 'POST',
                url: evalFileUploadUrl,
                params: requestParameters,
                data: formData,
                transformRequest: function (data, headersGetterFunction) {
                    return data;
                },
                headers: {
                    'Content-Type': undefined
                }
            }).then(function successCallback(response) {
                // success
                logMessage = "Evaluation file uploaded successfully!";
                showToast(logMessage);
            }, function errorCallback(response) {
                // failure
                logMessage = "Error while uploading evaluation data";
                showToast(logMessage);
            });
        }
    };

    // private functions
    function showToast(logMessage) {
        var pinTo = "top right";

        $mdToast.show(
            $mdToast.simple()
                .textContent(logMessage)
                .position(pinTo )
                .hideDelay(3000)
        );
    }
}

app.factory("uploadService", UploadService);

// Data service (retrieve data from server e.g. get Buildings)
function DataService($http) {
    // API endpoints
    var getBuildingsUrl = '/building/getAllBuildings';
    var getEvalFilesUrl = '/position/getEvalFilesForBuildingId';
    var getAlgorithmTypesUrl = '/project/getAllAlgorithmTypes';

    // Cache
    var buildings = [];
    var evalFiles = [];

    // Service functions
    return {
        // api to get all buildings
        getAllBuildings: function () {
            // $http returns a promise, which has a then function, which also returns a promise
            var promise = $http.get(getBuildingsUrl).then(function (response) {
                // The then function here is an opportunity to modify the response
                console.log("Retrieved buildings:");
                console.log(response);
                // save response in cache
                angular.copy(response.data, buildings);
                // The return value gets picked up by the then in the controller.
                return response.data;
            });
            // Return the promise to the controller
            return promise;
        },
        loadEvalFilesForBuilding: function (buildingId) {
            var config = {
                params: {
                    buildingIdentifier: buildingId
                }
            };
            var promise = $http.get(getEvalFilesUrl, config).then(function (response) {
                console.log("Retrieved eval files:");
                console.log(response);

                // save response in cache
                angular.copy(response.data, evalFiles);

                return response.data;
            });
            return promise;
        },
        getEvalFilesForBuilding: function () {
            // return a copy of evalFiles
            return [].concat(evalFiles);
        },
        getAllAlgorithmTypes: function () {

        }
    }

}

app.factory("dataService", DataService);

// Calculation service (setup and call position calculations)
function CalculationService($http) {
    //api endpoints
    var generatePositionsUrl = '/position/generateBatchPositionResults';
    // properties
    var buildingId;
    var evalFileId;
    var radioMapFileIds = [1];
    var algorithmType = "WIFI";
    var projectParameters = [
        {name: "Param1", value: "Value1"},
        {name: "Param2", value: "Value2"}
    ];
    var asPixel = true;

    // workflow progress
    var workflowProgress = 0;

    function updateCalculationData() {
        // update broadcast
        $rootScope.$broadcast('updatedCalculationData');
    }

    return {
        // set and get progress
        flowProgress: function () {
            return workflowProgress;
        },
        increaseProgress: function () {
            workflowProgress++;
        },
        decreaseProgress: function () {
            workflowProgress--;
        },
        // set and get building
        getCurrentBuilding: function () {
            return buildingId;
        },
        setCalculationBuilding: function (bId) {
            buildingId = bId;
            console.log("Building Changed: " + buildingId);
        },
        // set and get eval file
        getEvalFile: function () {
            return evalFileId;
        },
        setEvalFile: function (evId) {
            evalFileId = evId;
            console.log("Eval File Changed: " + evalFileId);
        },
        generatePositions: function () {
            var data = {
                buildingIdentifier: buildingId,
                evaluationFile: evalFileId,
                radioMapFiles: radioMapFileIds,
                algorithmType: algorithmType,
                projectParameters: projectParameters,
                withPixelPosition: asPixel
            };
            var config = {
                headers: {
                    'Content-Type': 'application/json'
                }
            };
            var promise = $http.post(generatePositionsUrl, data, config).then(function (response) {
                console.log("Retrieved positions:");
                console.log(response);

                return response.data;
            });
            return promise;
        }
    }
}

app.factory("calculationService", CalculationService);

// Project service (create, persist and load projects)
function ProjectService($http) {
    //api endpoints
    var newProjUrl = '/project/saveNewProject';
    var generateRadiomapUrl = '/position/generateRadioMaps';
    var generatePositionsUrl = '/position/generatePositionResults';
    // project properties
    var projectId;
    var projectName = 'DemoRun';
    var algorithmType = 'WIFI';
    var projectParameter = {
        "name": "string",
        "value": "string"
    };
    var calcFile;

    var positionResults;


    // project access function
    return {
        // access methods
        currentProjectId: function () {
            return projectId;
        },
        calcPositions: function () {
            return positionResults;
        },
        // working methods
        createNewProject: function () {
            //service init - create Project
            var requestParameters = {
                projectName: projectName,
                algorithmType: algorithmType
            };
            $http({
                method: 'POST',
                url: newProjUrl,
                params: requestParameters,
                data: [projectParameter]
            }).then(function successCallback(response) {
                // success
                projectId = response.data;
            }, function errorCallback(response) {
                // failure
            });
        },
        generateRadiomap: function (files) {
            calcFile = files[0];
            var formData = new FormData();
            formData.append('radioMapFiles', calcFile);

            var requestParameters = {
                projectIdentifier: projectId,
                buildingIdentifier: 1,
                withPixelPosition: false
            };
            $http({
                method: 'POST',
                url: generateRadiomapUrl,
                params: requestParameters,
                data: formData,
                transformRequest: function (data, headersGetterFunction) {
                    return data;
                },
                headers: {
                    'Content-Type': undefined
                }
            }).then(function successCallback(response) {
                // success
            }, function errorCallback(response) {
                // failure
            });
        },
        retreivePositions: function () {
            var formData = new FormData();
            formData.append('evaluationFile', calcFile);

            var requestParameters = {
                projectIdentifier: projectId,
                buildingIdentifier: 1,
                withPixelPosition: false
            };
            $http({
                method: 'POST',
                url: generatePositionsUrl,
                params: requestParameters,
                data: formData,
                transformRequest: function (data, headersGetterFunction) {
                    return data;
                },
                headers: {
                    'Content-Type': undefined
                }
            }).then(function successCallback(response) {
                // success
                positionResults = response.data;
            }, function errorCallback(response) {
                // failure
            });
        }
    }
}

app.factory("projectService", ProjectService);

// Map service
function MapService() {
    // map service properties
    var referencePoints = [];
    var staticMap = {};
    var mDefaults = {
        interactions: {
            mouseWheelZoom: true
        }
    };
    var mCenter = {
        zoom: 2
    };

    // styles
    var ref_marker_style = {
        image: {
            icon: {
                anchor: [0.5, 0, 5],
                anchorXUnits: 'fraction',
                anchorYUnits: 'fraction',
                opacity: 1.0,
                src: '/icons/ref-marker.png'
            }
        }
    };

    // map service access functions
    return {
        // Reference points
        refPoints: function () {
            // return copy of list
            return [].concat(referencePoints);
        },
        addRefPoint: function (x, y) {
            // create a new reference point
            var newRef = {
                coord: [x, y],
                projection: 'pixel',
                style: ref_marker_style
            };
            referencePoints.push(newRef)
        },
        // Access map, defaults and center
        map: function () {
            return staticMap;
        },
        mapDefaults: function () {
            return mDefaults;
        },
        mapCenter: function () {
            return mCenter;
        },
        setMap: function (mapUrl, width, height) {
            // set map image
            staticMap.source = {
                type: "ImageStatic",
                url: mapUrl,
                imageSize: [width, height]
            };
            // set view size
            mDefaults.view = {
                projection: 'pixel',
                extent: [0, 0, width, height]
            };
            // set view center
            mCenter.coord = [Math.floor(width / 2), Math.floor(height / 2)];
        }
    };
}

app.factory('mapService', MapService);

// ------------- Controllers
// controller which handels page navigation
app.controller('NavToolbarCtrl', function ($scope, $timeout) {
    $scope.currentTitle = 'Map View';

    // change Toolbar title when route changes
    $scope.$on('$routeChangeSuccess', function (event, current) {
        $scope.currentTitle = current.title;
    });

});

// controller which handles map configuration
app.controller('MapSettingsCtrl', function ($scope, $timeout, $mdSidenav, mapService, projectService, calculationService) {
    $scope.toggleLeft = buildToggler('left');
    $scope.toggleRight = buildToggler('right');

    $scope.radiomaps = [
        {
            id: 1,
            radioMapSourceFileName: "radiomap.txt"
        }
    ];

    function buildToggler(componentId) {
        return function () {
            $mdSidenav(componentId).toggle();
        };
    }

    $scope.algoHide = function () {
        return calculationService.flowProgress() < 2;
    };

    $scope.calculatePos = function () {
        // run calculation and show results
        calculationService.generatePositions().then(function (data) {
            var posis = data;
            for (var i = 0; i < posis.length; i++) {
                var p = posis[i];
                // api returns picture coordinates move them by height to match image
                mapService.addRefPoint(p.x, p.y - 818);
            }
        });
    };
});

// controller which handles the map
function MapController($scope, $http, olData, mapService) {

    // example map service setup
    mapService.setMap("/maps/car.png", 1282, 818);

    // setup usage of map service
    angular.extend($scope, {
        mapCenter: mapService.mapCenter,
        mapDefaults: mapService.mapDefaults,
        map: mapService.map,
        refPoints: mapService.refPoints
    });
}

app.controller('MapCtrl', MapController);

// controller which handles the building import view
function BuildingImportController($scope, uploadService) {

    $scope.building = {
        buildingName: "",
        numberOfFloors: 0,
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
        }
    };

    $scope.uploadBuildingData = function () {
        uploadService.uploadBuilding($scope.building);
        console.log($scope.building)
    }
}

app.controller('BuildingImportCtrl', BuildingImportController);

//Controller to fetch the building using the data service
function BuildingController($scope, dataService, calculationService) {
    // properties
    $scope.buildingData = {
        buildings: [],
        selectedBuilding: {},
        selectedFloor: 0
    };

    // enumeration function
    $scope.getNumber = function (num) {
        return new Array(num);
    };

    // load data from backend with service
    dataService.getAllBuildings().then(function (data) {
        // set building
        $scope.buildingData.buildings = data;
        $scope.buildingData.selectedBuilding = data[0];
        $scope.buildingData.selectedFloor = 0;
        console.log(data)
    });

    $scope.setBuilding = function () {
        calculationService.setCalculationBuilding(1);
        calculationService.increaseProgress();

    };
}

app.controller('BuildingCtrl', BuildingController);

// Track chooser controller
function TrackController($scope, dataService, calculationService) {

    // hide if not needed yet
    $scope.trackHide = function () {
        return calculationService.flowProgress() < 1;
    };

    dataService.loadEvalFilesForBuilding(1).then(function (data) {
        $scope.evalFiles = data;
    });

    $scope.setEvaluationFile = function () {
        calculationService.setEvalFile(1);
        calculationService.increaseProgress();
    };
}

app.controller('TrackCtrl', TrackController);


/**
 * POST the uploaded log file
 * Custom directive to define ng-files attribute
 */
app.directive('ngFiles', ['$parse', function ($parse) {
    function filelink(scope, element, attrs) {
        var onChange = $parse(attrs.ngFiles);
        element.on('change', function (event) {
            onChange(scope, {$files: event.target.files});
        });
    }

    return {
        link: filelink
    }
}]);

// controller which handles the log import view
function LogImportController($scope, uploadService) {
    // show file chooser on button click
    $scope.upload = function () {
        angular.element(document.querySelector('#inputFile')).click();
    };

    // buildings to show for chooser
    $scope.buildings = [
        {
            id: 1,
            buildingName: "CAR2",
            floorCount: 1
        }
    ];

    // parameters needed to upload log file
    $scope.logFileParameters = {
        buildingIdentifier: 0,
        radioMapFiles: []
    };

    var formData = new FormData();

    $scope.getTheFiles = function ($files) {
        $scope.logFileParameters.radioMapFiles = $files;
        $scope.fileUploaded = "File: " + $files[0].name;
        // notify changed scope to display file name
        $scope.$apply();
    };

    //The success or error message
    $scope.uploadStatus = false;

    //Post the file and parameters
    $scope.uploadFiles = function () {
        console.log($scope.logFileParameters);
        uploadService.uploadRadioMap($scope.logFileParameters);
    }
}

app.controller('LogImportCtrl', LogImportController);

// controller which handles the eval import view
function EvaluationImportController($scope, uploadService) {
    // show file chooser on button click
    $scope.evalUpload = function () {
        angular.element(document.querySelector('#evalInputFile')).click();
    };

    // buildings to show for chooser
    $scope.buildings = [
        {
            id: 1,
            buildingName: "CAR2",
            floorCount: 1
        }
    ];

    // parameters needed to upload eval file
    $scope.evalFileParameters = {
        buildingIdentifier: 0,
        evalFiles: []
    };

    $scope.getEvalFiles = function ($files) {
        $scope.evalFileParameters.evalFiles = $files;
        $scope.fileUploaded = "File: " + $files[0].name;
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

app.controller('EvalImportCtrl', EvaluationImportController);

/**
 * ----------------------------------------------
 * Non angular specific entries, like map initializing
 * ----------------------------------------------
 */