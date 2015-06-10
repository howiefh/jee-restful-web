'use strict';

/* Services */

var testServices = angular.module('testServices', ['ngResource']);

testServices.factory('User', ['$resource',
  function($resource){
    return $resource('../../test/:userId', {}, {
      query: {method:'GET', params:{userId:''}, isArray:true},
      update: { method: 'PUT', params: {userId: '@id'} },
      create: { method: 'POST'},
      delete: { method: 'DELETE'}
    });
  }]);