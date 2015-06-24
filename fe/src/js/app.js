'use strict';

/* App Module */

var jeews = angular.module('jeews', [ 'ui.router', 'ui.bootstrap', 'restangular', 'ngTable',
    'controllers', 'filters', 'services', 'directives' ]);

jeews.config([
    '$stateProvider',
    '$urlRouterProvider',
    'RestangularProvider',
    function($stateProvider, $urlRouterProvider, RestangularProvider) {
      // 给所有后端 API 请求设置 baseUrl
      RestangularProvider.setBaseUrl('/jeews');
      RestangularProvider.setDefaultHeaders({
        "Content-Type" : 'application/json',
        "Accept" : 'application/hal+json'
      });
      RestangularProvider.setRestangularFields({
        selfLink : "_links.self.href"
      });
      RestangularProvider.setOnElemRestangularized(function(elem, isCollection,
          what, Restangular) {
        for ( var rel in elem._links) {
          if (rel !== 'curies') {
            var index = rel.indexOf(':');
            var name = rel.substring(index + 1);
            elem.addRestangularMethod(name, 'getList', elem._links[rel].href);
          }
        }
        if (isCollection) {
          // adding addRestangularMethod in setOnElemRestangularized is same as adding the following snippet in all services module :
          // see https://github.com/mgonto/restangular/issues/719
          // add custom method to remove all selected users
          // service.remove = function(elem) {
          //   Restangular.all('users').customOperation('remove', '', null, null, elem);
          // }
          // signature is (name, operation, path, params, headers, elementToPost)
          elem.addRestangularMethod('removeSelected', 'remove', '', null, null, elem);
        }
        return elem;
      });
      RestangularProvider.addResponseInterceptor(function(data, operation,
          what, url, response, deferred) {
        var returnData;
        if (_.has(data, "_embedded")) {
          returnData = _.values(data._embedded)[0];
          returnData.links = data._links;
          returnData.page = data.page;
          return returnData;
        }
        return data;
      });
      RestangularProvider.setErrorInterceptor(function(response, deferred,
          responseHandler) {
        console.log(response.data);
        return false;
      });

      $urlRouterProvider.otherwise('/');

      $stateProvider.state('home', {
        url : '/',
        template : '<h1>Hello JEE Web Site!</h1>'
      }).state('users', {
        url : '/users',
        templateUrl : 'list.html',
        controller : 'UserListCtrl'
      }).state('usersDetail', {
        url : '/users/update/:id',
        templateUrl : 'detail.html',
        controller : 'UserDetailCtrl',
        resolve : {
          usersRes : function(Users, $stateParams) {
            return Users.one($stateParams.id).get();
          }
        }
      }).state('usersCreate', {
        url : '/users/create',
        templateUrl : 'edit.html',
        controller : 'UserCreationCtrl',
        resolve : {
          usersRes : function(Users) {
            return {};
          }
        }
      });

    } ]);