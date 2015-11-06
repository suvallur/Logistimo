'use strict';

angular.module('warmachine1App')
    .factory('Api_headers_infoSearch', function ($resource) {
        return $resource('api/_search/api_headers_infos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
