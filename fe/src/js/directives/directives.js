'use strict';

angular.module('directives')
.directive('ngEnter', function() {//http://eric.sau.pe/angularjs-detect-enter-key-ngenter/
  return function(scope, element, attrs) {
    element.bind("keydown keypress", function(event) {
      if (event.which === 13) {
        scope.$apply(function() {
          scope.$eval(attrs.ngEnter);
        });

        event.preventDefault();
      }
    });
  };
});