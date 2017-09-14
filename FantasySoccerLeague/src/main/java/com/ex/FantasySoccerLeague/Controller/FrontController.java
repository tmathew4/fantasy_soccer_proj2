package com.ex.FantasySoccerLeague.Controller;

import com.ex.FantasySoccerLeague.Services.ApplicationServices;
import com.ex.FantasySoccerLeague.tables.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController    //spring bean that accepts requests
public class FrontController {

    private ApplicationServices applicationServices;

    @Autowired
    public void setApplicationServices(ApplicationServices applicationServices) {
        this.applicationServices = applicationServices;
    }

    //RequestParam(value = "json", required = true)

    @RequestMapping(path="/login", method = {RequestMethod.POST, RequestMethod.GET},
            consumes = "*/*" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLogin(@RequestBody String json, HttpServletRequest req) throws IOException {
        System.out.println("This is the json object " + json);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        JsonNode jsonNode1 = node.get("email");
        JsonNode jsonNode2 = node.get("password");
        String mypassword = applicationServices.hashPassword(jsonNode2.textValue());
        Fantasy_User user = applicationServices.checkLogin(jsonNode1.textValue(), mypassword);
        req.getSession().setAttribute("user", user);
        System.out.println(mapper.writeValueAsString(user));
        return mapper.writeValueAsString(user);
    }

    @RequestMapping(path="/team/{id}", method = {RequestMethod.GET, RequestMethod.POST},
            consumes = "*/*",produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMyTeam(@PathVariable("id") Integer x) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Player> players =  applicationServices.myTeam(x);
        System.out.println(mapper.writeValueAsString(players));
        return mapper.writeValueAsString(players);
    }

    @RequestMapping(path="/league/{id}", method = {RequestMethod.GET, RequestMethod.POST},
            consumes = "*/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllTeam(@PathVariable("id") Integer y) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Team> team =  applicationServices.viewAllTeam(y);
        return mapper.writeValueAsString(team);
    }

    @RequestMapping(path="/all_players", method = RequestMethod.GET,
            consumes = "*/*" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllPlayers() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Player> players= applicationServices.findAllPlayers();
        return mapper.writeValueAsString(players);
    }

    @RequestMapping(path="/available_players", method = RequestMethod.GET,
            consumes = "*/*" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAvailablePlayers() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Player> players= applicationServices.findAvailablePlayers();
        return mapper.writeValueAsString(players);
    }

    @RequestMapping(path="/unavailable_players", method = RequestMethod.GET,
            consumes = "*/*" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUnavailablePlayers() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Player> players= applicationServices.findUnavailablePlayers();
        return mapper.writeValueAsString(players);
    }

    @RequestMapping(path="/league_list", method = {RequestMethod.GET, RequestMethod.POST},
            consumes = "*/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllLeagues() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<League> team =  applicationServices.viewAllLeagues();
        return mapper.writeValueAsString(team);
    }

    @RequestMapping(path = "/register_user")
    public String registerUser(@RequestBody String json) throws IOException
    {
        System.out.println(json);
        ObjectMapper mapper = new ObjectMapper(); //Maybe create a instance/class variable since we're using this so much.
        JsonNode node = mapper.readTree(json);
        System.out.println(json);
        Fantasy_User user = mapper.readValue(json, Fantasy_User.class);
        user.setPassword(applicationServices.hashPassword(user.getPassword()));
        applicationServices.registerUser(user);
        return json; //So dumb.

        //Alternative
//        try {
//            applicationServices.registerUser(user);
//        } catch(Exception e) {
//            return "error";
//        }
//        return "success";
    }

    @RequestMapping(path = "/register_team/{league_id}/{team_name}")
    public void registerTeam(@PathVariable("league_id") Integer leagueId, @PathVariable("team_name") String teamName, HttpServletRequest req) throws IOException
    {
        Fantasy_User user = (Fantasy_User) req.getSession().getAttribute("user");
        applicationServices.registerTeam(leagueId, teamName, user);
    }


    /**
     * Flushes the weekly points gathered for each player and updates their
     * overall score.
     */
    @RequestMapping(path = "/update_points")
    public void updatePoints() {
        applicationServices.generateWeeklyPoints();
        applicationServices.updatePoints();
    }

    @RequestMapping(path = "/update_team_points/{team_id}")
    public void getTeamPoints(@PathVariable("team_id") Integer teamId ) {
        applicationServices.updateTeamPoints(teamId);
    }


    @RequestMapping(path="/player_stats/{id}", method = RequestMethod.GET,
            consumes = "*/*" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPlayerStats(@PathVariable("id") Integer player_id) throws IOException {
        Player_Stats stats = applicationServices.getPlayerStats(player_id);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(stats));
        return mapper.writeValueAsString(stats);
    }

    @RequestMapping(path="/delete_player/{player_id}{team_id}", method = RequestMethod.GET,
            consumes = "*/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public void removePlayer(@PathVariable("player_id") Integer player_id, @PathVariable("team_id") Integer team_id) throws IOException{
        applicationServices.dropPlayer(player_id, team_id);
    }
}

