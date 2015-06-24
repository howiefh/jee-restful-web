'use strict';

/* Services */

var services = angular.module('services', []);

services.factory('Users', ['Restangular', function(Restangular) {
  var path = 'users';
  var service = Restangular.service(path);  
  return service;
}]);