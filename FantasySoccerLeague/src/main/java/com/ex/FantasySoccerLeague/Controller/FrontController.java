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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(path="/login", method = {RequestMethod.POST, RequestMethod.GET},
            consumes = "*/*" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLogin(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);

        JsonNode jsonNode1 = node.get("email");
        JsonNode jsonNode2 = node.get("password");

        Fantasy_User user = applicationServices.checkLogin(jsonNode1.textValue(), jsonNode2.textValue());
        return mapper.writeValueAsString(user);
    }

    @RequestMapping(path="/Home/{id}", method = {RequestMethod.GET, RequestMethod.POST},
            consumes = "*/*",produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMyTeam(@PathVariable("id") Integer x) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Team team = applicationServices.myTeam(x);
        String ret = null;
        ret = mapper.writeValueAsString(team);
//        System.out.println(ret);
        return ret;
    }

    @RequestMapping(path="/Team/{id}", method = {RequestMethod.GET, RequestMethod.POST},
            consumes = "*/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllTeam(@PathVariable("id") Integer y) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Team> allTeam = applicationServices.viewAllTeam(y);
        String ret;
        ret = mapper.writeValueAsString(allTeam);
        System.out.println(ret);
        return ret;
    }



}
