'use strict';

var sys = angular.module('sys');

sys.controller('UserListCtrl', [
    '$scope',
    '$window',
    '$filter',
    'ngTableParams',
    'Users',
    'orgsRes',
    'rolesRes',
    function($scope, $window, $filter, ngTableParams, Users, orgsRes, rolesRes) {
      // use in html like this ng-repeat="user in $data". see
      // https://github.com/esvit/ng-table/issues/464#issuecomment-64247038
      $scope.roles = rolesRes;
      $scope.organizations = orgsRes;
      
      $scope.tableParams = new ngTableParams({
        page : 1, // show first page
        count : 10, // count per page
        sorting : {
          id : 'asc' // initial sorting
        }
      }, {
        getData : function($defer, params) {
          var page = params.page() - 1, count = params.count(), sort = '';
          _.each(params.sorting(), function(n, key) {
            sort += key + ',' + n; 
          });
          var query = {
            page : page,
            size : count,
            sort : sort
          };
          if ($scope.search.query != '') {
            if($scope.selectedKey.id == 'username') {
                query.username = $scope.search.query;
            } else if($scope.selectedKey.id == 'email') {
                query.email = $scope.search.query;
            } else if($scope.selectedKey.id == 'mobile') {
                query.mobile = $scope.search.query;
            }
          }
          Users.getList(query).then(
              function(users) {
                params.total(users.page.totalElements);
                // use build-in angular filter
                // sort
                var orderedData = params.sorting() ? $filter('orderBy')(users,
                    params.orderBy()) : users;
                // filter
                orderedData = params.filter() ? $filter('filter')(orderedData,
                    params.filter()) : orderedData;
                $defer.resolve($scope.users = orderedData);
              });
        }
      });
      // 表格每行的多选框
      $scope.checkboxes = {
        'checked' : false,
        items : {}
      };
      // watch for check all checkbox
      $scope.$watch('checkboxes.checked', function(value) {
        angular.forEach($scope.users, function(item) {
          if (angular.isDefined(item.id)) {
            $scope.checkboxes.items[item.id] = value;
          }
        });
      });
      // watch for data checkboxes
      $scope.$watch('checkboxes.items', function(values) {
        if (!$scope.users) {
          return;
        }
        var checked = 0, unchecked = 0, total = $scope.users.length;
        angular.forEach($scope.users, function(item) {
          checked += ($scope.checkboxes.items[item.id]) || 0;
          unchecked += (!$scope.checkboxes.items[item.id]) || 0;
        });
        if ((unchecked == 0) || (checked == 0)) {
          $scope.checkboxes.checked = (checked == total);
        }
        // grayed checkbox
        angular.element(document.getElementById("select_all")).prop(
            "indeterminate", (checked != 0 && unchecked != 0));
      }, true);

      //删除选中
      $scope.deleteSelected = function() {
        var selected = _.map($scope.checkboxes.items, function(num, key) {
          if (num === true)
            return key;
        });
        if (selected.length > 0) { // should have selected some items
          Users.removeSelected(selected).then(function(){
            $scope.tableParams.reload();
          });
        }
      };
      $scope.deleteUser = function(userToDelete) {
        userToDelete.remove().then(function() {
          $window.alert('用户 ' + userToDelete.username + ' 已删除');
          // see https://github.com/esvit/ng-table/issues/322
          $scope.tableParams.reload();
        });
      };
      $scope.editUser = function(userToEdit) {
        userToEdit.$edit = true;
      }
      $scope.updateUser = function(userToUpdate) {
        userToUpdate.put().then(function() {
          $window.alert('用户 ' + userToUpdate.username + ' 已更新');
          $scope.tableParams.reload();
        }, function(){
          userToUpdate.$edit = false;
        });
      };
      $scope.search = function() {
        $scope.tableParams.page(1);
        $scope.tableParams.reload();
      };
      //选择按什么关键字搜索
      $scope.keys = [ {id: 'username', name: '用户名'}, {id: 'email', name: '邮箱'}, {id: 'mobile', name: '手机'}];
      $scope.selectedKey = $scope.keys[0];
      $scope.setKey = function(key) {
        $scope.selectedKey = key;
      };
    } ]);

sys.controller('UserDetailCtrl', [ '$scope', '$window',
    '$stateParams', '$state', 'usersRes', 'orgsRes', 'rolesRes',
    function($scope, $window, $stateParams, $state, usersRes, orgsRes, rolesRes) {
      $scope.user = usersRes;
      $scope.roles = rolesRes;
      $scope.organizations = orgsRes;

      $scope.deleteUser = function() {
        $scope.user.remove().then(function() {
          $window.alert('用户 ' + $scope.user.username + ' 已删除');
          $state.go('users');
        });
      };
      $scope.cancel = function() {
        $state.go('users');
      };
      $scope.updateUser = function() {
        $scope.user.put().then(function() {
          $window.alert('用户 ' + $scope.user.username + ' 已更新');
          $state.go('users');
        });
      };
    } ]);

sys.controller('UserCreationCtrl', [ '$scope', '$location',
    '$window', 'Users', 'orgsRes', 'rolesRes', function($scope, $location, $window, Users, orgsRes, rolesRes) {
      $scope.roles = rolesRes;
      $scope.organizations = orgsRes;
      //初始化
      $scope.user = {
          locked:false,
          roles:[$scope.roles[0]],
          organizations:[$scope.organizations[0]]
      };
      
      $scope.createUser = function() {
        Users.post($scope.user).then(function() {
          $window.alert('用户 ' + $scope.user.username + ' 已添加');
        });
      };
    } ]);