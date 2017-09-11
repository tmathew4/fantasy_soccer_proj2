package com.ex.FantasySoccerLeague.Controller;

import com.ex.FantasySoccerLeague.Services.ApplicationServices;
import com.ex.FantasySoccerLeague.tables.Fantasy_User;
import com.ex.FantasySoccerLeague.tables.Player;
import com.ex.FantasySoccerLeague.tables.Team;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController    //spring bean that accepts requests
public class FrontController {

    private ApplicationServices applicationServices;

    @Autowired
    public void setApplicationServices(ApplicationServices applicationServices){
        this.applicationServices = applicationServices;
    }

    //RequestParam(value = "json", required = true)

    @RequestMapping(path="/login", method = {RequestMethod.POST, RequestMethod.GET},
            consumes = "*/*" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLogin( String json) throws IOException {
        System.out.println("This is the json object " + json);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        JsonNode jsonNode1 = node.get("email");
        JsonNode jsonNode2 = node.get("password");
        Fantasy_User user = applicationServices.checkLogin(jsonNode1.textValue(), jsonNode2.textValue());
        System.out.println(mapper.writeValueAsString(user));
        return mapper.writeValueAsString(user);
    }



    @RequestMapping(path="/Home/{id}", method = {RequestMethod.GET, RequestMethod.POST},
            consumes = "*/*",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Player> getMyTeam(@PathVariable("id") Integer x) throws JsonProcessingException {
        List<Player> players =  applicationServices.myTeam(x);
        return players;
    }

    @RequestMapping(path="/Team/{id}", method = {RequestMethod.GET, RequestMethod.POST},
            consumes = "*/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Team> getAllTeam(@PathVariable("id") Integer y) throws JsonProcessingException {
        List<Team> team =  applicationServices.viewAllTeam(y);
        return team;
    }



    @RequestMapping(path="/allPlayers", method = RequestMethod.GET,
            consumes = "*/*" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllPlayers() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Player> players= applicationServices.findAllPlayers();
        return mapper.writeValueAsString(players);
    }

    @RequestMapping(path="/availablePlayers", method = RequestMethod.GET,
            consumes = "*/*" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAvailablePlayers() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Player> players= applicationServices.findAvailablePlayers();
        return mapper.writeValueAsString(players);
    }

    @RequestMapping(path="/unavailablePlayers", method = RequestMethod.GET,
            consumes = "*/*" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public String getUnavailablePlayers() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Player> players= applicationServices.findUnavailablePlayers();
        return mapper.writeValueAsString(players);
    }


}
