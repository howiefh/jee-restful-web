'use strict';

var sys = angular.module('sys');

sys.controller('LoginCtrl', [
    '$scope',
    '$state',
    'Users',
    'Storage',
    function($scope, $state, Users, Storage) {
      $scope.loginUser = {};
      $scope.signupUser = {};

      $scope.login = function() {
        Users.login($scope.loginUser).then(function(data){
          Storage.setToken(data.access_token);
          Storage.setUser(data.user);
          Users.changeUser(data.user);
          $state.go('home');
        });
      };

      $scope.signup = function() {
        Users.signup($scope.signupUser).then(function(data){
          $state.go('login');
        });
      };
  } ]);

sys.controller('NavCtrl', [
    '$scope',
    '$state',
    'jwtHelper',
    'Users',
    'Storage',
    function($scope, $state, jwtHelper, Users, Storage) {
      $scope.currentUser = Users.user;
      
      $scope.logout = function(){
        Storage.clear();
        $state.go('login');
      };
      
      $scope.isLoggedIn = Storage.isLoggedIn;
  } ]);