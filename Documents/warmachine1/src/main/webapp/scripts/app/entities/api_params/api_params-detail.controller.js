'use strict';

angular.module('warmachine1App')
    .controller('Api_paramsDetailController', function ($scope, $rootScope, $stateParams, entity, Api_params, Api_info) {
        $scope.api_params = entity;
        $scope.load = function (id) {
            Api_params.get({id: id}, function(result) {
                $scope.api_params = result;
            });
        };
        var unsubscribe = $rootScope.$on('warmachine1App:api_paramsUpdate', function(event, result) {
            $scope.api_params = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
