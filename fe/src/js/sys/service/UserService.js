'use strict';

var sys = angular.module('sys');

sys.factory('Users', ['Restangular', 'Storage', function(Restangular, Storage) {
  var path = 'users';
  var service = Restangular.service(path);  
  
  var currentUser = Storage.getUser() || { username: ''};

  service.changeUser = function(user) {
      angular.extend(currentUser, user);
  };
  service.user = currentUser;

  service.login = function(elem) {
    return Restangular.one('login').customPOST(elem, '', null, null);
  };
  service.signup = function(elem) {
    return Restangular.one('signup').customPOST(elem, '', null, null);
  };
  return service;
}]);