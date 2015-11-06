'use strict';

describe('Api_headers_info Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockApi_headers_info, MockApi_info;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockApi_headers_info = jasmine.createSpy('MockApi_headers_info');
        MockApi_info = jasmine.createSpy('MockApi_info');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Api_headers_info': MockApi_headers_info,
            'Api_info': MockApi_info
        };
        createController = function() {
            $injector.get('$controller')("Api_headers_infoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'warmachine1App:api_headers_infoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
