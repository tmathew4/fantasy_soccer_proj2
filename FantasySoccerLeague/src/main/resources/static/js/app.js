var app = angular.module('main', ["ngRoute"]);
app.controller("menu", ['$scope', '$location', function($scope, $location) {
		$scope.route = function ( path ) {
		  $location.path( path );
		};
	}]);
app.config(['$routeProvider', '$locationProvider', function($routeProvider) {
    $routeProvider
    .when("/", {
        templateUrl : "login.html"
    })
    .when("/home", {
        templateUrl : "home.html"
    })
    .when("/league", {
        templateUrl : "league.html"
    })
    .when("/teams", {
        templateUrl : "team.html"
    })
    .when("/schedule", {
        templateUrl : "schedule.html"
    })
    .when("/create", {
        templateUrl : "createuser.html"
    })
    .when("/sign", {
        templateUrl : "sign.html"
    })
    .when("/trade", {
        templateUrl : "trade.html"
    });
}]);
app.controller("my_players", function($scope, $location) {
		$http.get("/players", function($response){
			$scope.m_players = $response.data;
		});
	});
app.controller("not_my_players", function($scope, $location) {
		$http.get("/other_signed_players", function($response){
			$scope.o_players = $response.data;
		});
	});
app.controller("unsigned_players", function($scope, $location) {
		$http.get("/unsigned_players", function($response){
			$scope.o_players = $response.data;
		});
	});
app.controller("send_login", function($scope, $http) {
	$scope.login = function() {
		$scope.user = {
			"email" : $scope.email,
			"password" : $scope.password
		};
		$http({
	        method : 'POST',
	        url : '/login',
	        contentType: 'application/json',
	        data : JSON.stringify($scope.user),
	    });
	}
});