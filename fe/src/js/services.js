'use strict';

/* Services */

var testServices = angular.module('testServices', []);

testServices.factory('Users', ['Restangular', function(Restangular) {
  var path = 'testusers';
  var service = Restangular.service(path);  
  return service;
}]);