'use strict';

var sys = angular.module('sys');

sys.factory('Users', ['Restangular', function(Restangular) {
  var path = 'users';
  var service = Restangular.service(path);  
  return service;
}]);