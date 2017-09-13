package com.ex.FantasySoccerLeague.Controller;

import com.ex.FantasySoccerLeague.Dao.*;
import com.ex.FantasySoccerLeague.Services.ApplicationServices;
import com.ex.FantasySoccerLeague.tables.Player;
import com.ex.FantasySoccerLeague.tables.Player_Stats;
import com.ex.FantasySoccerLeague.tables.Team;
import com.ex.FantasySoccerLeague.tables.Trade;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Random;

@RestController
public class UserController {
    @Autowired
    DataRetriever dr;

    @Autowired
    Team_Dao teams;

    @Autowired
    Player_Dao players;

    @Autowired
    Player_Stats_Dao player_stats;

    private ApplicationServices applicationServices;

    @Autowired
    public void setApplicationServices(ApplicationServices applicationServices){
        this.applicationServices = applicationServices;
    }

    @RequestMapping(path="/my_team/{id}", method = RequestMethod.GET)
    public String getUserTeam(@PathVariable("id") Integer id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Team t = teams.findByUser(id);

        return mapper.writeValueAsString(t);
    }

    @RequestMapping(path="/get_data/{page}", method = RequestMethod.GET)
    public void getData(@PathVariable("page") Integer page) throws IOException {
        JsonNode node = dr.get(page);
        Random r = new Random();

        System.out.println(node.toString());

        for(int i = 0; i < node.size(); i++) {
            JsonNode curr = node.get(i);
            if(players.findByName(curr.get("firstName").textValue() + " " + curr.get("lastName").textValue()) == null) {
                Player p = new Player();
                p.setName(curr.get("firstName").textValue() + " " + curr.get("lastName").textValue());
                p.setNumber(r.nextInt(100));
                p.setPosition(dr.getPosition(curr.get("position").textValue()));
                p.setPercentage(curr.get("rating").intValue());
                p.setAssists(0);
                p.setGoals(0);
                p.setOwn_Goals(0);
                p.setRed_Card(0);
                p.setSOG(0);
                p.setYellow_Card(0);
                System.out.println(p.toString());
                players.saveAndFlush(p);

                Player_Stats s = new Player_Stats();
                s.setFirstName(curr.get("firstName").textValue());
                s.setLastName(curr.get("lastName").textValue());
                s.setBirthDate(curr.get("birthdate").textValue());
                s.setHeadShot(curr.get("headshotImgUrl").textValue());
                s.setNationName(curr.get("nation").get("name").textValue());
                s.setNationFlag(curr.get("nation").get("imageUrls").get("medium").textValue());
                s.setHeight(curr.get("height").intValue());
                s.setWeight(curr.get("weight").intValue());
                s.setPlayer_id(p);
                s.setPlayer_Stats_id(p.getId());
                System.out.println(s.toString());
                player_stats.saveAndFlush(s);
            }
        }
    }

    @RequestMapping(path="/trade_player/{id1}/{id2}", method = RequestMethod.GET)
    public int tradePlayer(@PathVariable("id1") Integer id1, @PathVariable("id2") Integer id2) throws IOException {
        applicationServices.tradePlayers(id1, id2);
        return 0;
    }

    @RequestMapping(path="/sign_player/{id}/{team_id}", method = RequestMethod.GET)
    public int signPlayer(@PathVariable("id") Integer id, @PathVariable("team_id") Integer team_id) throws IOException {
        applicationServices.signPlayer(id, team_id);
        return 1;
    }
}