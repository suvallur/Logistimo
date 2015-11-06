'use strict';

angular.module('warmachine1App').controller('Api_headers_infoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Api_headers_info', 'Api_info',
        function($scope, $stateParams, $modalInstance, entity, Api_headers_info, Api_info) {

        $scope.api_headers_info = entity;
        $scope.api_infos = Api_info.query();
        $scope.load = function(id) {
            Api_headers_info.get({id : id}, function(result) {
                $scope.api_headers_info = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('warmachine1App:api_headers_infoUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.api_headers_info.id != null) {
                Api_headers_info.update($scope.api_headers_info, onSaveFinished);
            } else {
                Api_headers_info.save($scope.api_headers_info, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
