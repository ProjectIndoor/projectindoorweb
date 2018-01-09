var resultViewModule = angular.module('IndoorApp.resultView', ['IndoorApp.calculationService']);

// Track chooser controller
function ResultController($scope, calculationService) {
    // properties
    $scope.result = calculationService.getResult;

    // hide if not needed yet
    $scope.resultShow = calculationService.hasResult;

}

resultViewModule.controller('ResultCtrl', ResultController);