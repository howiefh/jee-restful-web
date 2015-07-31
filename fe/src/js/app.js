'use strict';

var jeews = angular.module('jeews', [ 'ui.router', 'ui.bootstrap', 'restangular', 'ngTable', 'xtForm', 'ui.select', 'ngSanitize',
    'sys', 'filters', 'directives' ]);

jeews.config([
    '$stateProvider',
    '$urlRouterProvider',
    'RestangularProvider',
    'xtFormConfigProvider',
    function($stateProvider, $urlRouterProvider, RestangularProvider, xtFormConfigProvider) {
      // 全局配置
      xtFormConfigProvider.setErrorMessages({
        required: '该选项不能为空',
        maxlength: '该选项输入值长度不能大于{{ngMaxlength}}',
        minlength: '该选项输入值长度不能小于{{ngMinlength}}',
        email: '输入邮件的格式不正确',
        pattern: '该选项输入格式不正确',
        number: '必须输入数字',
        url: '输入URL格式不正确',
        max: '该选项输入值不能大于{{max}}',
        min: '该选项输入值不能小于{{min}}',
        date: '输入日期的格式不正确',
        datetimelocal: '输入日期的格式不正确',
        time: '输入时间的格式不正确',
        week: '输入周的格式不正确',
        month: '输入月的格式不正确',
        $$server: '(⊙o⊙)哦，遇到错误了'
      });
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

      $urlRouterProvider.otherwise('/');

      $stateProvider.state('home', {
        url : '/',
        template : '<h1>Hello JEE Web Site!</h1>'
      }).state('users', {
        url : '/users',
        templateUrl : 'users/list.html',
        controller : 'UserListCtrl',
        resolve : {
          rolesRes : function(Roles) {
            return Roles.getList();
          },
          orgsRes : function(Organizations) {
            return Organizations.getList();
          }
        }
      }).state('usersDetail', {
        url : '/users/update/:id',
        templateUrl : 'users/edit.html',
        controller : 'UserDetailCtrl',
        resolve : {
          usersRes : function(Users, $stateParams) {
            return Users.one($stateParams.id).get();
          },
          rolesRes : function(Roles) {
            return Roles.getList();
          },
          orgsRes : function(Organizations) {
            return Organizations.getList();
          }
        }
      }).state('usersCreate', {
        url : '/users/create',
        templateUrl : 'users/edit.html',
        controller : 'UserCreationCtrl',
        resolve : {
          rolesRes : function(Roles) {
            return Roles.getList();
          },
          orgsRes : function(Organizations) {
            return Organizations.getList();
          }
        }
      });

    } ]);
jeews.run([
    'Restangular',
    '$window',
    function(Restangular, $window) {
      //http://stackoverflow.com/questions/21445886/angularjs-change-url-in-module-config
      Restangular.setErrorInterceptor(function(response, deferred,
          responseHandler) {
        if (response.status == 401) {
          console.log("Login required... ");
          var location = response.header;
          $window.location.href = '/views/login.html';
        } else if (response.status == 404) {
          console.log("Resource not available...");
        } else {
          console.log("Response received with HTTP error code: " + response.status);
        }
        return false; // stop the promise chain
      });
    } ]);