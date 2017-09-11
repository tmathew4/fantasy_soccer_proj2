package com.ex.FantasySoccerLeague.Controller;

import com.ex.FantasySoccerLeague.Dao.Team_Dao;
import com.ex.FantasySoccerLeague.Services.ApplicationServices;
import com.ex.FantasySoccerLeague.tables.Fantasy_User;
import com.ex.FantasySoccerLeague.tables.Team;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

@RestController    //spring bean that accepts requests
public class FrontController
{

    private ApplicationServices applicationServices;

    @Autowired
    public void setApplicationServices(ApplicationServices applicationServices)
    {
        this.applicationServices = applicationServices;
    }

    //RequestParam(value = "json", required = true)

    @RequestMapping(path = "/login", method = {RequestMethod.POST, RequestMethod.GET},
            consumes = "*/*", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLogin(String json) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);

        JsonNode jsonNode1 = node.get("email");
        JsonNode jsonNode2 = node.get("password");

        Fantasy_User user = applicationServices.checkLogin(jsonNode1.textValue(), jsonNode2.textValue());

        System.out.println(mapper.writeValueAsString(user));

        return mapper.writeValueAsString(user);
    }

    @RequestMapping(path = "/Home/{id}", method = {RequestMethod.GET, RequestMethod.POST},
            consumes = "*/*", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getMyTeam(@PathVariable("id") Integer x) throws JsonProcessingException
    {
        ObjectMapper mapper = new ObjectMapper();
        Team team = applicationServices.myTeam(x);
        String ret = null;
        ret = mapper.writeValueAsString(team);
//        System.out.println(ret);
        return ret;
    }

    @RequestMapping(path = "/register_user")
    public String registerUser(@RequestBody String json) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper(); //Maybe create a instance/class variable since we're using this so much.
        Fantasy_User user = mapper.readValue(json, Fantasy_User.class);
        user.setId(-1); //Will not update an existing user.
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
}
