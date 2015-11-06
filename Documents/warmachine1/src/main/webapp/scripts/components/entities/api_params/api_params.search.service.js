'use strict';

angular.module('warmachine1App')
    .factory('Api_paramsSearch', function ($resource) {
        return $resource('api/_search/api_paramss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
