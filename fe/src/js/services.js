'use strict';

/* Services */

var testServices = angular.module('testServices', []);

testServices.factory('Users', ['Restangular', function(Restangular) {
  return Restangular.service('testusers');
}]);