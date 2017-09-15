var app = angular.module('main', ["ngRoute"]);
app.show = false;
app.controller("menu", ['$scope', '$location', '$http', '$rootScope', function($scope, $location, $http, $rootScope) {
        $rootScope.route = function(path) {
            $location.path(path);
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
		    	$rootScope.user = $response.data;
		    	console.log($response);
		    	console.log($response.data);
		    	console.log($rootScope.user);
		    	if($rootScope.user != null) {
		    	    $location.path("/home");
		    	}
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
		controller: 'team_data'
    })
    .when("/leagues", {
        templateUrl : "leagues.html",
        controller : "get_leagues"
    })
    .when("/p_league", {
        templateUrl : "p_league.html",
        controller : "get_p_leagues"
    })
    .when("/league", {
        templateUrl : "league.html",
        controller : "get_teams"
    })
    .when("/m_teams", {
        templateUrl : "m_teams.html",
        controller : "m_teams"
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
    .when("/trades", {
        templateUrl : "trades.html",
        controller : "get_trades"
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
app.controller("get_leagues", ['$scope', '$location', '$http', '$rootScope', function($scope, $location, $http, $rootScope) {
	$http.get("/league_list").then(function($response){
	    console.log($response.data);
		$scope.leagues = $response.data;
	});
	$scope.get_league = function(id) {
	    $rootScope.league_id = id;
	    $location.path("/league");
	}
}]);
app.controller("get_p_leagues", ['$scope', '$location', '$http', '$rootScope', function($scope, $location, $http, $rootScope) {
	$http.get("/league_list").then(function($response){
	    console.log($response.data);
		$scope.p_leagues = $response.data;
	});
	$scope.get_players = function(id) {
	    $rootScope.league_id = id;
	    $location.path("/players");
	}
}]);
app.controller("get_teams", ['$scope', '$location', '$http', '$rootScope', function($scope, $location, $http, $rootScope) {
    console.log($rootScope.league_id);
	$http.get("/league/" + $rootScope.league_id).then(function($response){
	    console.log($response.data);
		$scope.l_teams = $response.data;
	});
	$scope.get_team = function(id, name, league, points) {
        $rootScope.team_id = id;
        $rootScope.team_name = name;
        $rootScope.league_id = league;
        $rootScope.team_points = points;
	    $rootScope.mine = false;
	    $location.path("/teams");
	}
}]);
app.controller("team_data", ['$scope','$http', '$rootScope', function($scope, $http, $rootScope) {
    console.log($rootScope.team_points);
	$http.get("/team/" + $rootScope.team_id).then(function($response){
	    console.log($response.data);
		$scope.t_players = $response.data;
	});
	$scope.get_points = function(player) {
	    var points = player.goals * (4 + player.position.id);
	    points += player.sog;
	    points += 3 * player.assists;
	    points -= player.yellow_Card;
	    points -= 2 * player.own_Goals;
	    points -= 3 * player.red_Card;
	    return points;
	}
}]);
app.controller("m_teams", ['$scope', '$location', '$http', '$rootScope', function($scope, $location, $http, $rootScope) {
    $http.get("/my_teams").then(function($response) {
        console.log($response.data);
        $scope.m_teams = $response.data;
    });
    $scope.load_team = function(id, name, league, points) {
        $rootScope.team_id = id;
        $rootScope.team_name = name;
        $rootScope.league_id = league;
        $rootScope.team_points = points;
	    $rootScope.mine = true;
	    $location.path("/teams");
    };
}]);
app.controller("list_players", ['$scope', '$http', '$rootScope', function($scope, $http, $rootScope) {
	$http.get("/all_players/" + $rootScope.league_id).then(function(response){
		console.log(response.data);
		$scope.list_players = response.data;
	});
}]);
app.controller("list_unsigned_players", ['$scope', '$http', '$rootScope', function($scope, $http, $rootScope) {
	$http.get("/available_players/" + $rootScope.league_id).then(function(response){
		console.log(response.data);
		$scope.unsigned_players = response.data;
	});
}]);
app.controller("player_stats", ['$scope', '$routeParams','$http', "$location", "$rootScope", function($scope, $routeParams, $http, $location, $rootScope) {
	$http.get("player_stats/"+$routeParams.id).then(function(response){
		console.log(response.data);
		$scope.u_player_stats = response.data;
	});
	$http.get("/league/" + $rootScope.league_id).then(function($response){
	    console.log($response.data);
		$scope.s_team = $response.data;
	});
	$scope.sign = function(player_id) {
	    var team_id = document.getElementById("my_team");
	    $http.get("/sign_player/" + player_id + "/" + team_id);
	    $location.path("/teams")
	}
}]);
app.controller("sign_player", ['$scope','$http', '$location', '$rootScope', function($scope, $http, $location, $rootScope) {
	$http.get("/available_players/" + $rootScope.league_id).then(function($response){
	    console.log($response.data);
		$scope.u_players = $response.data;
	});
	$scope.sign = function() {
	    var player1 = document.getElementById("unsigned_player").value;

	    $http.get("/sign_player/" + player1 + "/" + $rootScope.team_id);
	    $location.path("/teams")
	}

}]);
app.controller("drop_player",['$scope', '$http', '$rootScope', function($scope, $http, $rootScope){
	$http.get("/team/"+ $rootScope.team_id).then(function($response){
		console.log($response.data); 
		$scope.my_players = $response.data; 
	});
	$scope.drop = function() {
		var player1 = document.getElementById("assigned_player").value; 
		$http.get("/delete_player/" + player1 ); 
    }
}]);

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
app.controller("trade_players", ['$scope','$http', '$location', '$rootScope', function($scope, $http, $location, $rootScope) {
	$http.get("/team/" + $rootScope.team_id).then( function($response){
	    console.log($response.data);
		$scope.m_players = $response.data;
	});
	$http.get("/unavailable_players/" + $rootScope.league_id).then( function($response){
	    console.log($response.data);
	    $scope.a_players = $response.data;
	    var new_players = $scope.m_players;
	    $scope.o_players = $scope.a_players.filter(function (item) {
	                            return new_players.filter( function( item2 ){
                                                        return item.id == item2.id;
                                                      }).length == 0;
                               });
	});
	$scope.trade = function() {
	    var player1 = document.getElementById("my_player").value;
	    var player2 = document.getElementById("other_player").value;

	    $http.get("/trade_player/" + player1 + "/" + player2);
	    $location.path("/teams")
	}
}]);
app.controller("create_user", ['$scope', '$location', '$http', '$rootScope', function($scope, $location, $http, $rootScope) {

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
		    	$rootScope.user = $response.data;
		    	if($rootScope.user != null) {
		    	    $location.path("/home");
		    	}
		    });
		}
}]);
app.controller("get_trades", ['$scope','$http', '$location', '$rootScope', function($scope, $http, $location, $rootScope) {
	$http.get("/get_trades").then(function($response){
	    console.log($response.data);
		$scope.trades = $response.data;
	});
	$scope.finalize_trade = function(id) {
	    $http.get("/finalize_trade/" + id).then(function($response) {
	        console.log($response);
	    });
	    $location.path("/teams");
	}
	$scope.delete_trade = function(id) {
	    $http.get("/delete_trade/" + id).then(function($response) {
	        console.log($response);
	    });
	    $location.path("/teams");
	}
}]);