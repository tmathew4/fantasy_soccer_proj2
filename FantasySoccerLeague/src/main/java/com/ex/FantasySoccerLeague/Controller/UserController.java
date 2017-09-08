package com.ex.FantasySoccerLeague.Controller;

import com.ex.FantasySoccerLeague.Dao.DataRetriever;
import com.ex.FantasySoccerLeague.Dao.Team_Dao;
import com.ex.FantasySoccerLeague.tables.Player;
import com.ex.FantasySoccerLeague.tables.Team;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    DataRetriever dr;

    @Autowired
    Team_Dao teams;

    @RequestMapping(path="/my_team/{id}", method = RequestMethod.GET)
    public String getUserTeam(Integer id) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Team t = teams.findByUser(id);

        return mapper.writeValueAsString(t);
    }

    @RequestMapping(path="/get_data/{page}", method = RequestMethod.GET)
    public void getData(Integer page) throws IOException {
        JsonNode node = dr.get(page);

        for(int i = 0; i < node.size(); i++) {
            JsonNode curr = node.get(i);
            Player p = new Player();
            p.setName(curr.get("firstName") + " " + curr.get("lastName"));
            //p.setNumber(Integer.parseInt(curr.get("id").textValue()));
            p.setPosition(dr.getPosition(curr.get("position").textValue()));
            p.setPercentage(Integer.parseInt(curr.get("rating").textValue()));
        }
    }
}
