'use strict';

angular.module('warmachine1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('api_headers_info', {
                parent: 'entity',
                url: '/api_headers_infos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Api_headers_infos'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/api_headers_info/api_headers_infos.html',
                        controller: 'Api_headers_infoController'
                    }
                },
                resolve: {
                }
            })
            .state('api_headers_info.detail', {
                parent: 'entity',
                url: '/api_headers_info/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Api_headers_info'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/api_headers_info/api_headers_info-detail.html',
                        controller: 'Api_headers_infoDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Api_headers_info', function($stateParams, Api_headers_info) {
                        return Api_headers_info.get({id : $stateParams.id});
                    }]
                }
            })
            .state('api_headers_info.new', {
                parent: 'api_headers_info',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/api_headers_info/api_headers_info-dialog.html',
                        controller: 'Api_headers_infoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    api_id: null,
                                    headers: null,
                                    default_value: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('api_headers_info', null, { reload: true });
                    }, function() {
                        $state.go('api_headers_info');
                    })
                }]
            })
            .state('api_headers_info.edit', {
                parent: 'api_headers_info',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/api_headers_info/api_headers_info-dialog.html',
                        controller: 'Api_headers_infoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Api_headers_info', function(Api_headers_info) {
                                return Api_headers_info.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('api_headers_info', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
