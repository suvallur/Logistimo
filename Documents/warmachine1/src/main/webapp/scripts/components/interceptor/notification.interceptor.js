 'use strict';

angular.module('warmachine1App')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-warmachine1App-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-warmachine1App-params')});
                }
                return response;
            }
        };
    });
