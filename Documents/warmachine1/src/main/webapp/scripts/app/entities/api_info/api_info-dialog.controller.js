'use strict';

angular.module('warmachine1App').controller('Api_infoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Api_info',
        function($scope, $stateParams, $modalInstance, entity, Api_info) {

        $scope.api_info = entity;
        $scope.load = function(id) {
            Api_info.get({id : id}, function(result) {
                $scope.api_info = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('warmachine1App:api_infoUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.api_info.id != null) {
                Api_info.update($scope.api_info, onSaveFinished);
            } else {
                Api_info.save($scope.api_info, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
