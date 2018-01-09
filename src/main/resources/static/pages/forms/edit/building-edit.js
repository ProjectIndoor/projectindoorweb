var buildEditModule = angular.module('IndoorApp.editBuilding', ['ngMaterial', 'ngMessages', 'IndoorApp.dataService']);

function BuildingEditController($scope, $mdPanel, dataService) {
    //load md panel
    var mdPanel = $mdPanel;

    // load list of buildings when calling controller
    dataService.loadAllBuildings();

    // assign loaded buildings list to scope var
    $scope.buildings = dataService.getAllBuildings;

    $scope.showBuildingInfo = function (building) {
        // setup panel position
        var position = mdPanel.newPanelPosition()
            .absolute()
            .center();

        // setup panel config
        var config = {
            attachTo: angular.element(document.body),
            templateUrl: 'pages/panels/building.panel.html',
            hasBackdrop: true,
            panelClass: 'project-dialog',
            position: position,
            controller: BuildingDialogController,
            controllerAs: 'ctrl',
            trapFocus: true,
            zIndex: 150,
            clickOutsideToClose: true,
            escapeToClose: true,
            focusOnOpen: true,
            locals: {
                "building": building
            }
        };

        // show building info panel
        mdPanel.open(config);
    }
}

buildEditModule.controller('BuildingEditCtrl', BuildingEditController);

/**
 * Controller to handle building panels
 * @param mdPanelRef
 * @param dataService
 * @constructor
 */
function BuildingDialogController(mdPanelRef, dataService) {
    var panelRef = mdPanelRef;

    this.deleteBuilding = function (buildingId) {
        // call delete and reload buildings on success
        dataService.deleteBuilding(buildingId).then(function (data) {
            dataService.loadAllBuildings();
        });
        this.closeDialog();
    };

    this.closeDialog = function () {
        panelRef && panelRef.close().then(function () {
            panelRef.destroy();
        });
    };
}

buildEditModule.controller('BuildingDialogCtrl', BuildingDialogController);

