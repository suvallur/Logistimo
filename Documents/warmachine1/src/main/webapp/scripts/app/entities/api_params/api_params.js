'use strict';

angular.module('warmachine1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('api_params', {
                parent: 'entity',
                url: '/api_paramss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Api_paramss'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/api_params/api_paramss.html',
                        controller: 'Api_paramsController'
                    }
                },
                resolve: {
                }
            })
            .state('api_params.detail', {
                parent: 'entity',
                url: '/api_params/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Api_params'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/api_params/api_params-detail.html',
                        controller: 'Api_paramsDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Api_params', function($stateParams, Api_params) {
                        return Api_params.get({id : $stateParams.id});
                    }]
                }
            })
            .state('api_params.new', {
                parent: 'api_params',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/api_params/api_params-dialog.html',
                        controller: 'Api_paramsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    api_id: null,
                                    parameter: null,
                                    usage_type: null,
                                    default_value: null,
                                    type: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('api_params', null, { reload: true });
                    }, function() {
                        $state.go('api_params');
                    })
                }]
            })
            .state('api_params.edit', {
                parent: 'api_params',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/api_params/api_params-dialog.html',
                        controller: 'Api_paramsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Api_params', function(Api_params) {
                                return Api_params.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('api_params', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
