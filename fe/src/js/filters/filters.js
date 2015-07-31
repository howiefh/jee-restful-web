'use strict';

angular.module('filters', [])
.filter('checkmark', function() {
  return function(input) {
    return input ? '\u2713' : '\u2718';
  };
})
.filter('arrayField', function() {
  return function(inputArray, attr) {
    var res = "";
    for (var i = 0; i < inputArray.length; i++) {
      var tmp = inputArray[i];
      res += tmp[attr] + " ";
    }
    return res;
  }
})
/**
 * AngularJS default filter with the following expression:
 * "person in people | filter: {name: $select.search, age: $select.search}"
 * performs a AND between 'name: $select.search' and 'age: $select.search'.
 * We want to perform a OR.
 */
.filter('propsFilter', function() {
  return function(items, props) {
    var out = [];

    if (angular.isArray(items)) {
      items.forEach(function(item) {
        var itemMatches = false;

        var keys = Object.keys(props);
        for (var i = 0; i < keys.length; i++) {
          var prop = keys[i];
          var text = props[prop].toLowerCase();
          if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
            itemMatches = true;
            break;
          }
        }

        if (itemMatches) {
          out.push(item);
        }
      });
    } else {
      // Let the output be the input untouched
      out = items;
    }

    return out;
  };
});