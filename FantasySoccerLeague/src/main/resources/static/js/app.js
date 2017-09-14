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
        templateUrl : "login1234.html",
        controller: 'menu'
    })
    .when("/home", {
		templateUrl : "home.html",
		controller: 'team_data'
    })
    .when("/league", {
		templateUrl : "league.html",
		controller: 'get_leagues'
    })
    .when("/teams", {
        templateUrl : "team.html",
        controller: 'team_data'
	})
	.when("/create_team", {
		templateUrl : "createteam.html",
		controller : "create_team"
	})
    .when("/schedule", {
        templateUrl : "schedule.html"
    })
    .when("/create", {
        templateUrl : "createuser.html",
        controller : "create_user"
    })
    .when("/sign", {
        templateUrl : "sign.html",
        controller : "sign_player"
	})
	.when("/drop",{
		templateUrl : "drop.html",
		controller : "drop_player"
	})
    .when("/trade", {
        templateUrl : "trade.html",
        controller : "trade_players"
    })
    .when("/players", {
        templateUrl : "players.html",
        controller: 'list_players'
    })
    .when("/unsigned_players", {
        templateUrl : "unsigned_players.html",
        controller: 'list_unsigned_players'
	})
	.when("/player_stats/:id", {
        templateUrl : "playerStats.html",
        controller: 'player_stats'
    });
}]);

app.controller("get_leagues", function($scope, $http) {
	$http.get("/leagues").then(function($response){
		$scope.l_teams = $response.data;
	});
});

// app.controller("get_teams", function($scope, $http) {
// 	$http.get("/league/1").then(function($response){
// 		$scope.l_teams = $response.data;
// 	});
// });
app.controller("team_data", function($scope, $http) {
	$http.get("/team/1").then(function($response){
	    console.log($response.data);
		$scope.t_players = $response.data;
	});
});
app.controller("list_players", function($scope, $http) {
	$http.get("all_players").then(function(response){
		console.log(response.data);
		$scope.list_players = response.data;
	});
});
app.controller("list_unsigned_players", function($scope, $http) {
	$http.get("available_players").then(function(response){
		console.log(response.data);
		$scope.unsigned_players = response.data;
	});
});

app.controller("player_stats", ['$scope', '$routeParams','$http',function($scope, $routeParams, $http) {
	$http.get("player_stats/"+$routeParams.id).then(function(response){
		console.log(response.data);
		$scope.player_stats = response.data;
	});
}]);
//app.controller("list_signed_players", function($scope, $http) {
//	$http.get("/unavailable_players").then( function(response){
//		$scope.players = response.data;
//	});
//});
app.controller("sign_player", function($scope, $http) {
	$http.get("/available_players").then(function($response){
	    console.log($response.data);
		$scope.u_players = $response.data;
	});
	$scope.sign = function() {
	    var player1 = document.getElementById("unsigned_player").value;

	    $http.get("/sign_player/" + player1 + "/1");
	}
});
app.controller("drop_player", function($scope, $http){
	$http.get("/team").then(function($response){
		console.log($response.data); 
		$scope.my_players = $response.data; 
	});
	$scope.drop = function() {
		var player1 = document.getElementById("team_data").value; 
		$http.get("/drop_player/" + player1); 
	}
});
app.controller("create_team", ['$scope', '$http','$location',
  function($scope, $http, $location) {
  $scope.route = function(path) {
  		  $location.path(path);
  		};
		  $http.get("/league_list").then(function($response){
		   console.log($response.data);
		   $scope.league_names = $response.data;
		   });
   	$scope.create_team = function() {
		let myLeagueId =  document.getElementById("leagueId").value;
		let myTeamName = document.getElementById("teamName").value;
		console.log(myLeagueId);
		console.log(myTeamName); 
		$http({
			method :'GET',
			url : "/register_team/" + myLeagueId + "/" + myTeamName,
			contentType : 'application/json'
		}).then(function() {
				$scope.route("/home");
		});
	}
}]);
app.controller("trade_players", function($scope, $http) {
	$http.get("/team/1").then( function($response){
	    console.log($response.data);
		$scope.m_players = $response.data;
	});
	$http.get("/unavailable_players").then( function($response){
	    console.log($response.data);
	    $scope.a_players = $response.data;
	    var new_players = $scope.m_players;
	    $scope.o_players = $scope.a_players.filter(function (item) {
	                            return new_players.filter( function( item2 ){
                                                        return item.id == item2.id;
                                                      }).length == 0;
//                                   return new_players.indexOf(item) === -1;
                               });
	});
	$scope.trade = function() {
	    var player1 = document.getElementById("my_player").value;
	    var player2 = document.getElementById("other_player").value;

	    $http.get("/trade_player/" + player1 + "/" + player2);
	}
});
app.controller("team_name", function($scope, $http) {
    $scope.name = "HI";
});
app.controller("create_user", function($scope, $http) {

		$scope.create_user = function() {
			console.log("inside create user function");
			var user = {
				"email" : document.getElementById("email").value,
				"password" : document.getElementById("password").value,
				"fname" : document.getElementById("fname").value,
				"lname" : document.getElementById("lname").value
			};
			console.log(user);
            $http({
		        method : 'POST',
		        url : '/register_user',
		        contentType: 'application/json',
		        data : JSON.stringify(user)
		    }).then(function($response) {
		    	app.user = $response.data;
		    	console.log($response);
		    	console.log($response.data);
		    	console.log(app.user);
		    	if(app.user != null) {
		    	    $scope.route("/home");
		    	    app.show = true;
		    	}
		    });
		}
});