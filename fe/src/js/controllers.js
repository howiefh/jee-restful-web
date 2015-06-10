'use strict';

/* Controllers */

var testControllers = angular.module('testControllers', []);

testControllers.controller('UserListCtrl', [ '$scope', 'User',
		function($scope, User) {
			$scope.users = User.query();
			$scope.orderProp = 'id';
	        $scope.deleteUser = function (userId) {
	            User.delete({ userId: userId });
	            $scope.users = User.query();
	        };
		} ]);

testControllers.controller('UserDetailCtrl', [ '$scope', '$routeParams',
		'User', '$location', function($scope, $routeParams, User, $location) {
			$scope.user = User.get({
				userId : $routeParams.userId
			}, function(user) {
			});
			// callback for ng-click 'cancel':
			$scope.cancel = function() {
				$location.path('/users');
			};
			// callback for ng-click 'updateUser':
			$scope.updateUser = function() {
				User.update($scope.user);
				$location.path('/users');
			};
		} ]);

testControllers.controller('UserCreationCtrl', [ '$scope', 'User', '$location',
		function($scope, User, $location) {

			// callback for ng-click 'createNewUser':
			$scope.createNewUser = function() {
				User.create($scope.user);
				$location.path('/users');
			};
		} ]);