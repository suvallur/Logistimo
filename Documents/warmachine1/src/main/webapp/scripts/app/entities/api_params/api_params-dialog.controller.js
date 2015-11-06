'use strict';

angular.module('warmachine1App').controller('Api_paramsDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Api_params', 'Api_info',
        function($scope, $stateParams, $modalInstance, entity, Api_params, Api_info) {

        $scope.api_params = entity;
        $scope.api_infos = Api_info.query();
        $scope.load = function(id) {
            Api_params.get({id : id}, function(result) {
                $scope.api_params = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('warmachine1App:api_paramsUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.api_params.id != null) {
                Api_params.update($scope.api_params, onSaveFinished);
            } else {
                Api_params.save($scope.api_params, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
