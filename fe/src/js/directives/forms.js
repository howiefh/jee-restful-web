'use strict';

angular.module('directives')
.directive( 'match', function() {
  return {
    require : 'ngModel',
    link : function(scope, element, attrs, ngModel) {
      ngModel.$parsers.push(function(value) {
        ngModel.$setValidity('match', value == scope .$eval(attrs.matchValidator));
        return value;
      });
    }
  }
});