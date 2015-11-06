'use strict';

angular.module('warmachine1App')
    .controller('Api_infoDetailController', function ($scope, $rootScope, $stateParams, entity, Api_info) {
        $scope.api_info = entity;
        $scope.load = function (id) {
            Api_info.get({id: id}, function(result) {
                $scope.api_info = result;
            });
        };
        var unsubscribe = $rootScope.$on('warmachine1App:api_infoUpdate', function(event, result) {
            $scope.api_info = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
