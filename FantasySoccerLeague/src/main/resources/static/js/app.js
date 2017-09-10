var app = angular.module('main', ["ngRoute"]);
app.show = false;
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
			var user = {
				"email" : document.getElementById("email").value,
				"password" : document.getElementById("password").value
			};
            $http({
		        method : 'POST',
		        url : '/login',
		        contentType: 'application/json',
		        data : JSON.stringify(user)
		    }).then(function($response) {
		    	app.user = $response.data;
		    	console.log($response);
		    	console.log($response.data);
		    	console.log(app.user);
		    	if(app.user != null) {
		    	    $scope.route("/home");
		    	    //$scope.show_menu();
		    	    app.show = true;
		    	}
		    });
		}
        $scope.show_menu = function() {
		    console.log(app.user);
            var read = new XMLHttpRequest();
            read.open('GET', 'menu.html', false);
            read.send();
            document.getElementById("menu_container").innerHTML = read.responseText;
        }

	}]);
app.config(['$routeProvider', '$locationProvider', function($routeProvider) {
    $routeProvider
    .when("/", {
        templateUrl : "login1234.html"
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
    })
    .when("/players", {
        templateUrl : "players.html"
    })
    .when("/unsigned_players", {
        templateUrl : "unsigned_players.html"
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
	$http.get("/team_players/"+$scope.teamid, function($response){
		$scope.players = $response.data;
	});
});
app.controller("list_players", function($scope, $http) {
	$http.get("/get_players", function($response){
		$scope.players = $response.data;
	});
});
app.controller("list_unsigned_players", function($scope, $http) {
	$http.get("/get_unsigned_players", function($response){
		$scope.players = $response.data;
	});
});
app.controller("sign_player", function($scope, $http) {
	$http.get("/sign_player/" + document.getElementById("unsigned_player").value, function($response){
		$scope.players = $response.data;
	});
});
app.controller("trade_players", function($scope, $http) {
	$http.get("/trade_players/" + document.getElementById("my_player").value + "/" + document.getElementById("other_player").value, function($response){
		$scope.players = $response.data;
	});
});