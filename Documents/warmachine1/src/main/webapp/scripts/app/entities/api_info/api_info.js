'use strict';

angular.module('warmachine1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('api_info', {
                parent: 'entity',
                url: '/api_infos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Api_infos'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/api_info/api_infos.html',
                        controller: 'Api_infoController'
                    }
                },
                resolve: {
                }
            })
            .state('api_info.detail', {
                parent: 'entity',
                url: '/api_info/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Api_info'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/api_info/api_info-detail.html',
                        controller: 'Api_infoDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Api_info', function($stateParams, Api_info) {
                        return Api_info.get({id : $stateParams.id});
                    }]
                }
            })
            .state('api_info.new', {
                parent: 'api_info',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/api_info/api_info-dialog.html',
                        controller: 'Api_infoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    api_id: null,
                                    project: null,
                                    method: null,
                                    base_url: null,
                                    fragment: null,
                                    environment: null,
                                    req_body: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('api_info', null, { reload: true });
                    }, function() {
                        $state.go('api_info');
                    })
                }]
            })
            .state('api_info.edit', {
                parent: 'api_info',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/api_info/api_info-dialog.html',
                        controller: 'Api_infoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Api_info', function(Api_info) {
                                return Api_info.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('api_info', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
