var trackChooserModule = angular.module('IndoorApp.trackChooser', ['IndoorApp.calculationService', 'IndoorApp.dataService']);

// Track chooser controller
function TrackController($scope, dataService, calculationService) {
    // properties
    $scope.trackData = calculationService.getEvalFile();

    // hide if not needed yet
    $scope.trackShow = calculationService.isBuildingSet;

    $scope.evalFiles = dataService.getCurrentEvalFiles;

    $scope.setEvaluationFile = function () {
        calculationService.setEvalFile($scope.trackData);
    };
}

trackChooserModule.controller('TrackCtrl', TrackController);