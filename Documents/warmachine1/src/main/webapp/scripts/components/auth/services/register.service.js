'use strict';

angular.module('warmachine1App')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


