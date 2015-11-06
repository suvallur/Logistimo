'use strict';

angular.module('warmachine1App')
    .factory('Api_info', function ($resource, DateUtils) {
        return $resource('api/api_infos/:id', {}, {
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
