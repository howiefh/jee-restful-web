'use strict';

var sys = angular.module('sys');

sys.factory('Roles', ['Restangular', function(Restangular) {
  var path = 'roles';
  var service = Restangular.service(path);  
  return service;
}]);