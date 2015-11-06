'use strict';

angular.module('warmachine1App')
    .controller('Api_headers_infoController', function ($scope, Api_headers_info, Api_headers_infoSearch, ParseLinks) {
        $scope.api_headers_infos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Api_headers_info.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.api_headers_infos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Api_headers_info.get({id: id}, function(result) {
                $scope.api_headers_info = result;
                $('#deleteApi_headers_infoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Api_headers_info.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteApi_headers_infoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            Api_headers_infoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.api_headers_infos = result;
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
            $scope.api_headers_info = {
                api_id: null,
                headers: null,
                default_value: null,
                id: null
            };
        };
    });
