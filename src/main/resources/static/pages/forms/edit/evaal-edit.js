var evaalEditModule = angular.module('IndoorApp.editEvaal', ['ngMaterial', 'ngMessages', 'IndoorApp.dataService']);

//Controller to handle the evaal edit view
function EvaalEditController($scope, $mdPanel, dataService) {
    //load md panel
    var mdPanel = $mdPanel;

    // load list of evaals when calling controller
    dataService.loadAllEvaals();

    // assign loaded evaal files list to scope var
    $scope.evaalFiles = dataService.getAllEvaals;

    $scope.showEvaalInfo = function (evaalFile) {
        // setup panel position
        var position = mdPanel.newPanelPosition()
            .absolute()
            .center();

        // setup panel config
        var config = {
            attachTo: angular.element(document.body),
            templateUrl: 'pages/panels/evaal.panel.html',
            hasBackdrop: true,
            panelClass: 'project-dialog',
            position: position,
            controller: EvaalDialogController,
            controllerAs: 'ctrl',
            trapFocus: true,
            zIndex: 150,
            clickOutsideToClose: true,
            escapeToClose: true,
            focusOnOpen: true,
            locals: {
                "evaal": evaalFile
            }
        };

        // show evaal info panel
        mdPanel.open(config);
    }
}

evaalEditModule.controller('EvaalEditCtrl', EvaalEditController);


function EvaalDialogController(mdPanelRef, dataService) {
    var panelRef = mdPanelRef;

    this.deleteEvaal = function (evaalId) {
        // call delete and reload evaals on success
        dataService.deleteEvaalFile(evaalId).then(function (data) {
            dataService.loadAllEvaals();
        });
        this.closeDialog();
    };

    this.closeDialog = function () {
        panelRef && panelRef.close().then(function () {
            panelRef.destroy();
        });
    };
}

evaalEditModule.controller('EvaalDialogCtrl', EvaalDialogController);