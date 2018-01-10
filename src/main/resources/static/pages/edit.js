var editModule = angular.module('IndoorApp.edit', [
    'ngRoute',
    'IndoorApp.editBuilding',
    'IndoorApp.editEvaal',
    'IndoorApp.editProject'
]);

// add page route
editModule.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/edit', {
        title: 'Manage Data',
        templateUrl: 'pages/edit.html',
        controller: 'LogImportCtrl'
    }).when('/project', {
        title: 'Projects',
        templateUrl: 'pages/project.html',
        controller: 'ProjectCtrl'
    });
}]);