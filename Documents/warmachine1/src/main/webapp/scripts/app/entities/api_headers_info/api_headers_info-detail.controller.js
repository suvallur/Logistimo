'use strict';

angular.module('warmachine1App')
    .controller('Api_headers_infoDetailController', function ($scope, $rootScope, $stateParams, entity, Api_headers_info, Api_info) {
        $scope.api_headers_info = entity;
        $scope.load = function (id) {
            Api_headers_info.get({id: id}, function(result) {
                $scope.api_headers_info = result;
            });
        };
        var unsubscribe = $rootScope.$on('warmachine1App:api_headers_infoUpdate', function(event, result) {
            $scope.api_headers_info = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
