'use strict';

var sys = angular.module('sys');

sys.factory('Storage',['localStorageService', function(localStorageService) {
  var storageKey = 'access_token';
  var userKey = "current_user";
  return {    
    setToken : function(token) {
      return localStorageService.set(storageKey, token);
    },
    getToken : function() {
      return localStorageService.get(storageKey);
    },
    setUser : function(user) {
      return localStorageService.set(userKey, user);
    },
    getUser : function() {
      return localStorageService.get(userKey);
    },
    clear : function() {
      return localStorageService.remove(storageKey, userKey);
    }
  };
}]);