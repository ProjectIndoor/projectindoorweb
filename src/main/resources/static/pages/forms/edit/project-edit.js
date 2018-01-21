var projectEditModule = angular.module('IndoorApp.editProject', ['ngMaterial', 'ngMessages', 'IndoorApp.projectService']);

//Controller to handle the project edit view
function ProjectController($scope, $mdPanel, projectService) {
    //load md panel
    var mdPanel = $mdPanel;

    // load list of projects when calling controller
    projectService.loadAllProjects();

    // assign loaded project list to scope var
    $scope.projects = projectService.getAllProjects;

    $scope.showProjectInfo = function (projectId) {
        // setup panel position
        var position = mdPanel.newPanelPosition()
            .absolute()
            .center();

        // setup panel config
        var config = {
            attachTo: angular.element(document.body),
            templateUrl: 'pages/panels/project.panel.html',
            hasBackdrop: true,
            panelClass: 'project-dialog',
            position: position,
            controller: ProjectDialogController,
            controllerAs: 'ctrl',
            trapFocus: true,
            zIndex: 150,
            clickOutsideToClose: true,
            escapeToClose: true,
            focusOnOpen: true
        };

        // retrieve project information and display it in a dialog
        projectService.loadProjectInfo(projectId).then(function (data) {
            config.locals = {
                "project": data
            };
            mdPanel.open(config);
        });
    }
}

projectEditModule.controller('ProjectCtrl', ProjectController);


function ProjectDialogController(mdPanelRef, calculationService, dataService, projectService, mapService, toastService) {
    var panelRef = mdPanelRef;

    // ensure we have all buildings
    // load list of buildings when calling controller
    dataService.loadAllBuildings();

    // function to load project into calculation Service
    this.loadProject = function () {
        // reset floor
        mapService.resetFloor();
        calculationService.clearResult();

        // get project
        lProject = this.project;
        // list of available buildings
        buildingList = dataService.getAllBuildings();

        projectBuilding = buildingList.find(function (b) {
            return b.buildingId == lProject.buildingIdentifier;
        });

        lProject.building = projectBuilding;

        calculationService.loadDataFromProject(lProject);
        logMessage = "Project was loaded";
        toastService.showToast(logMessage, "success-toast");
        panelRef && panelRef.close().then(function () {
            panelRef.destroy();
        });
    };

    this.deleteProject = function (projectId) {
        // call delete and reload projects on success
        dataService.deleteProject(projectId).then(function (data) {
            projectService.loadAllProjects();
        });
        this.closeDialog();
    };

    this.closeDialog = function () {
        panelRef && panelRef.close().then(function () {
            panelRef.destroy();
        });
    };
}

projectEditModule.controller('ProjectDialogCtrl', ProjectDialogController);