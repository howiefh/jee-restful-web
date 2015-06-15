'use strict';

/* App Module */

var jeewsTest = angular.module('jeewsTest', [
  'ui.router',
  'restangular',
  'testControllers',
  'testFilters',
  'testServices',
  'testDirectives'
]);

jeewsTest.config(['$stateProvider','$urlRouterProvider','RestangularProvider',
                  function($stateProvider, $urlRouterProvider, RestangularProvider) {
    //给所有后端 API 请求设置 baseUrl
    RestangularProvider.setBaseUrl('/jeews');
    RestangularProvider.setDefaultHeaders(
      { "Accept": 'application/hal+json'}
    );
    RestangularProvider.setRestangularFields({
      selfLink: "_links.self.href"
    });
    RestangularProvider.setOnElemRestangularized(function(elem, isCollection, what, Restangular) {
      for (var rel in elem._links) {
        if (rel !== 'curies') {
          var index = rel.indexOf(':');
          var name = rel.substring(index + 1);
          elem.addRestangularMethod(name, 'getList', elem._links[rel].href);
        }
      }
      return elem;
    });
    RestangularProvider.addResponseInterceptor(function(data, operation, what, url, response, deferred) {
      if (_.has(data, "_embedded")) {
        return _.values(data._embedded)[0];
      }
      return data;
    });
    RestangularProvider.setErrorInterceptor(function(response, deferred, responseHandler) {
      console.log(response.data);
      return false;
    });
    
    $urlRouterProvider.otherwise('/');

    $stateProvider
        .state('home', {
            url:'/',
            template:'<h1>Hello JEE Web Site!</h1>'
        })
        .state('users', {
            url:'/users',
            templateUrl:'list.html',
            controller : 'UserListCtrl',
            resolve : {
                usersRes : function(Users){
                   return Users.getList();
                }
            }
        })
        .state('usersDetail', {
            url:'/users/update/:id',
            templateUrl:'detail.html',
            controller: 'UserDetailCtrl',
            resolve : {
            	usersRes : function(Users,$stateParams){
                    return Users.one($stateParams.id).get();
                }
            }
        })
        .state('usersCreate', {
            url:'/users/create',
            templateUrl:'edit.html',
            controller: 'UserCreationCtrl',
            resolve : {
            	usersRes : function(Users){
                    return {};
                }
            }
        });

} ]);