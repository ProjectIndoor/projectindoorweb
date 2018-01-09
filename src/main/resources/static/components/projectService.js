var projectServiceModule = angular.module('IndoorApp.projectService', []);

// Project service (create, persist and load projects)
function ProjectService($http) {
    //api endpoints
    var allProjUrl = 'project/getAllProjects';
    var projInfoUrl = 'project/loadSelectedProject';

    // project properties
    var projectId;
    var projectName = 'DemoRun';

    // Cache
    var projects = [];

    return {
        // allProjects
        loadAllProjects: function () {
            var promise = $http.get(allProjUrl).then(function (response) {
                // cache response copy
                angular.copy(response.data, projects);

                // return data to allow access from caller
                return response.data;
            });
            return promise;
        },
        getAllProjects: function () {
            return [].concat(projects);
        },
        // single project info
        loadProjectInfo: function (projectId) {
            var config = {
                params: {
                    projectIdentifier: projectId
                }
            };
            var promise = $http.get(projInfoUrl, config).then(function (response) {
                // return data to allow access from caller
                return response.data;
            });
            return promise;
        }
    }
}

projectServiceModule.factory("projectService", ProjectService);