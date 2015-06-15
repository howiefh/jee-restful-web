'use strict';

/* Controllers */

var testControllers = angular.module('testControllers', []);

testControllers.controller('UserListCtrl', [ '$scope','$window', 'Users', 'usersRes',
		function($scope, $window, Users, usersRes) {
    		$scope.users = usersRes;
    	    $scope.attribute = "id";
    	    $scope.reverse = false;

    	    $scope.orderListBy = function(attr){
    	        $scope.attribute = attr;
    	        $scope.reverse = !$scope.reverse;
    	    };
	        $scope.deleteUser = function(userToDelete){
	        	userToDelete.remove()
	                .then(function(){
	                    $window.alert('用户 '+ userToDelete.username+' 已删除');

	                    var userWithId = _.find($scope.users, function(user) {
	                        return user.id === userToDelete.id;
	                    });

	                    var index = $scope.users.indexOf(userWithId);

	                    if(index > -1) $scope.users.splice(index, 1);
	                });
	        };
		} ]);

testControllers.controller('UserDetailCtrl', [ '$scope', '$window','$stateParams','$state',
		'usersRes',  function($scope, $window, $stateParams, $state, usersRes) {
            $scope.user = usersRes;
        
            $scope.deleteUser = function(){
                $scope.user.remove()
                    .then(function(){
	                    $window.alert('用户 '+$scope.user.username+' 已删除');
                        $state.go('users');
                    });
            };
			$scope.cancel = function() {
                $state.go('users');
			};
			$scope.updateUser = function(){
	            $scope.user.put()
	                .then(function(){
	                    $window.alert('用户 '+$scope.user.username+' 已更新');
	                    $state.go('users');
	                });
		    };
		} ]);

testControllers.controller('UserCreationCtrl', [ '$scope', '$location', '$window', 'Users',
		function($scope, $location, $window, Users) {

			$scope.createUser = function() {
				Users.post($scope.user)
                .then(function(){
                    $window.alert('用户 '+$scope.user.username+' 已添加');
                });
			};
		} ]);