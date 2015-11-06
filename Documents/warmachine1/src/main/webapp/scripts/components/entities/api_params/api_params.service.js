'use strict';

angular.module('warmachine1App')
    .factory('Api_params', function ($resource, DateUtils) {
        return $resource('api/api_paramss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
