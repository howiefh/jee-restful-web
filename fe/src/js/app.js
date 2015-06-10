'use strict';

/* App Module */

var jeewsTest = angular.module('jeewsTest', [
  'ngRoute',
  'testControllers',
  'testFilters',
  'testServices',
  'testDirectives'
]);

jeewsTest.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/users', {
        templateUrl: 'list.html',
        controller: 'UserListCtrl'
      }).
      when('/users/create', {
        templateUrl: 'edit.html',
        controller: 'UserCreationCtrl'
      }).
      when('/users/update/:userId', {
        templateUrl: 'detail.html',
        controller: 'UserDetailCtrl'
      }).
      otherwise({
        redirectTo: '/users'
      });
  }]);