var importModule = angular.module('IndoorApp.import', [
    'ngRoute',
    'IndoorApp.importBuilding',
    'IndoorApp.importEval',
    'IndoorApp.importFloor',
    'IndoorApp.importRadiomap'
]);

// add page route
importModule.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/import', {
        title: 'Import Data',
        templateUrl: 'pages/import.html',
        controller: 'LogImportCtrl'
    });
}]);

/**
 * POST the uploaded log file
 * Custom directive to define ng-files attribute
 */
importModule.directive('ngFiles', ['$parse', function ($parse) {
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