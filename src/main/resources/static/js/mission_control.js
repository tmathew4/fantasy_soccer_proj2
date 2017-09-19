var app = angular.module('main', ["ngRoute"]);
app.controller("nuke", ['$scope','$http', '$rootScope', function($scope, $http, $rootScope) {
    $scope.reset_everything = function() {
        console.log("/reset_everything")
	    $http.get("/reset_everything");
	}
    $scope.reset_teams = function() {
        console.log("/reset_teams")
	    $http.get("/reset_teams");
	}
    $scope.reset_points = function() {
        console.log("/reset_points")
	    $http.get("/reset_points");
	}
    $scope.update = function() {
        console.log("/update_everything")
	    $http.get("/update_everything");
	}
}]);