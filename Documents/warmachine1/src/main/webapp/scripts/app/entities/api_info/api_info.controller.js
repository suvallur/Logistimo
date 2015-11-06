'use strict';

angular.module('warmachine1App')
    .controller('Api_infoController', function ($scope, Api_info, Api_infoSearch, ParseLinks) {
        $scope.api_infos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Api_info.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.api_infos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Api_info.get({id: id}, function(result) {
                $scope.api_info = result;
                $('#deleteApi_infoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Api_info.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteApi_infoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Api_infoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.api_infos = result;
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
            $scope.api_info = {
                api_id: null,
                project: null,
                method: null,
                base_url: null,
                fragment: null,
                environment: null,
                req_body: null,
                id: null
            };
        };
    });
