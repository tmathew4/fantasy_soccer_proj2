var app = angular.module('main', ["ngRoute"]);
app.controller("menu", ['$scope', '$location', 
	function($scope, $location) {
		$scope.route = function(path) {
		  $location.path(path);
		};
		$scope.my_team = function() {
			$http.get("/my_team/"+$scope.user.id, function($response) {
				return $response.data.id;
			})
		}
		$scope.login = function() {
			$scope.user = {
				"email" : document.getElementById("email").innerHTML,
				"password" : document.getElementById("password").innerHTML
			};
		    $scope.route('/home');
			$http({
		        method : 'POST',
		        url : '/login',
		        contentType: 'application/json',
		        data : JSON.stringify($scope.user),
		    }, function($response) {
		    	app.user = $response.data;
		    });
		}
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
app.controller("get_teams", function($scope, $http) {
	$http.get("/teams", function($response){
		$scope.l_teams = $response.data;
	});
});
app.controller("team_data", function($scope, $http) {
	$http.get("/players/"+$scope.teamid, function($response){
		$scope.players = $response.data;
	});
});