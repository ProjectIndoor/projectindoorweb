var viewSettingsModule = angular.module('IndoorApp.viewSettings', ['IndoorApp.calculationService', 'IndoorApp.calculationService']);

// controller which handles map configuration panel
function MapSettingsController($scope, calculationService, mapService) {
    $scope.markerShow = {
        showRefVal: true,
        showPosVal: true,
        showNoRefPosVal: false,
        get showNoRefPos() {
            return this.showNoRefPosVal;
        },
        set showNoRefPos(show) {
            this.showNoRefPosVal = show;
            if (show) {
                mapService.showNoRefCalcPoints();
            } else {
                mapService.hideNoRefCalcPoints();
            }
            console.log("Show noRefPos:" + show);
        },
        get showRef() {
            return this.showRefVal;
        },
        set showRef(show) {
            this.showRefVal = show;
            if (show) {
                mapService.showRefPoints();
                mapService.showLines();
            } else {
                mapService.hideRefPoints();
                mapService.hideLines();
            }
            console.log("Show refPos:" + show);
        },
        get showPos() {
            return this.showPosVal;
        },
        set showPos(show) {
            this.showPosVal = show;
            if (show) {
                mapService.showCalcPoints();
            } else {
                mapService.hideCalcPoints();
            }
            console.log("Show calcPos:" + show);
        }
    };

    $scope.clearMap = mapService.clearMap;

    // project settings
    // properties
    $scope.projectData = calculationService.getCurrentProject();

    $scope.saveProject = function () {
        calculationService.saveCurrentProject($scope.projectData);
    };

    // decide when to hide/show project interface
    $scope.projectShow = calculationService.isAlgorithmReady;
}

viewSettingsModule.controller('MapSettingsCtrl', MapSettingsController);