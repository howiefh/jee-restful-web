'use strict';

var sys = angular.module('sys');

sys.factory('Organizations', ['Restangular', function(Restangular) {
  var path = 'organizations';
  var service = Restangular.service(path);  
  return service;
}]);