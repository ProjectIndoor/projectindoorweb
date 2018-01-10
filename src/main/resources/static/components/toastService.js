var toastServiceModule = angular.module('IndoorApp.toastService', ['ngMaterial']);

// Toast service
function ToastService($mdToast) {
    // toast service access function
    return {
        showToast: function (logMessage, customTheme) {
            //Position of the toast
            var pinTo = "bottom center";

            $mdToast.show(
                $mdToast.simple()
                    .textContent(logMessage)
                    .position(pinTo)
                    .hideDelay(3000)
                    .theme(customTheme)
            );
        }
    };
}

toastServiceModule.factory('toastService', ToastService);
