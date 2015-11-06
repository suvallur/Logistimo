'use strict';

angular.module('warmachine1App')
    .controller('Api_paramsController', function ($scope, Api_params, Api_paramsSearch, ParseLinks) {
        $scope.api_paramss = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Api_params.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.api_paramss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Api_params.get({id: id}, function(result) {
                $scope.api_params = result;
                $('#deleteApi_paramsConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Api_params.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteApi_paramsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Api_paramsSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.api_paramss = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.api_params = {
                api_id: null,
                parameter: null,
                usage_type: null,
                default_value: null,
                type: null,
                id: null
            };
        };
    });
