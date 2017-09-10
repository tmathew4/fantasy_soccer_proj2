var app = angular.module('main', ["ngRoute"]);
app.controller("menu", ['$scope', '$location', '$http',
	function($scope, $location, $http) {
		$scope.route = function(path) {
		  $location.path(path);
		};
		$scope.my_team = function() {
			$http.get("/my_team/"+$scope.user.id, function($response) {
				return $response.data.id;
			})
		}
		$scope.login = function() {
			console.log("inside login function");
			var user = {
				"email" : document.getElementById("email").value,
				"password" : document.getElementById("password").value
			};
			console.log(user);
            $http({
		        method : 'POST',
		        url : '/login',
		        contentType: 'application/json',
		        data : JSON.stringify(user)
		    }).then(function($response) {
		    	app.user = $response.data;
		    	console.log($response);
		    	console.log($response.data);
		    	$location.url('/home');
		    });
		}


	}]);
app.config(['$routeProvider', '$locationProvider', function($routeProvider) {
    $routeProvider
    .when("/", {
        templateUrl : "login1234.html",
        controller: 'menu'
    })
    .when("/home", {
		templateUrl : "home.html",
		controller: 'teamData'
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
    })
    .when("/players", {
        templateUrl : "players.html",
        controller: 'list_players'
    })
    .when("/unsigned_players", {
        templateUrl : "unsigned_players.html",
        controller: 'list_unsigned_players'
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

app.controller("get_teams", function($scope, $http) {
	$http.get("/teams", function($response){
		$scope.l_teams = $response.data;
	});
});
app.controller("team_data", function($scope, $http) {
	$http.get("/team_players/"+$scope.teamid, function($response){
		$scope.players = $response.data;
	});
});
app.controller("list_players", function($scope, $http) {
	$http.get("http://localhost:8080/allPlayers").then(function(response){
		console.log(response.data);
		$scope.players = response.data;
	});
});
app.controller("list_unsigned_players", function($scope, $http) {
	$http.get("http://localhost:8080/availablePlayers").then(function(response){
		console.log(response.data);
		$scope.players = response.data;
	});
});

app.controller("list_signed_players", function($scope, $http) {
	$http.get("/unavailablePlayers").then( function(response){
		$scope.players = response.data;
	});
});