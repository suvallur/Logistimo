'use strict';

describe('Api_params Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockApi_params, MockApi_info;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockApi_params = jasmine.createSpy('MockApi_params');
        MockApi_info = jasmine.createSpy('MockApi_info');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Api_params': MockApi_params,
            'Api_info': MockApi_info
        };
        createController = function() {
            $injector.get('$controller')("Api_paramsDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'warmachine1App:api_paramsUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
