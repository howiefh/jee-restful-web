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
})
.directive('udRequired', function() {
  return {
    require : 'ngModel',
    link : function(scope, element, attrs, ngModel) {
      ngModel.$validators.required = function(modelValue, viewValue) {
        return modelValue !== undefined && modelValue !== null && modelValue && modelValue.length > 0;
      };
    }
  };
});