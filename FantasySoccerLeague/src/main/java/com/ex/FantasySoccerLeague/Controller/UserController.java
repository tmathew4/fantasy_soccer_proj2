package com.ex.FantasySoccerLeague.Controller;

import com.ex.FantasySoccerLeague.Dao.*;
import com.ex.FantasySoccerLeague.Services.ApplicationServices;
import com.ex.FantasySoccerLeague.tables.*;
import com.ex.FantasySoccerLeague.tables.Player_Stats;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    Trade_Dao trades;

    @Autowired
    League_Dao leagues;

    private ApplicationServices applicationServices;

    @Autowired
    public void setApplicationServices(ApplicationServices applicationServices){
        this.applicationServices = applicationServices;
    }

    @RequestMapping(path="/get_user", method = RequestMethod.GET)
    public String getUser(HttpServletRequest req) throws IOException {
        Fantasy_User user = (Fantasy_User) req.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        if(user != null)
            return mapper.writeValueAsString(user);
        return null;
    }

    @RequestMapping(path="/my_teams", method = RequestMethod.GET)
    public String getUserTeams(HttpServletRequest req) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Fantasy_User user = (Fantasy_User) req.getSession().getAttribute("user");
        String ret = mapper.writeValueAsString(teams.findByUser(user));
        return ret;
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
                s.setPlayerId(p);
                s.setPlayer_Stats_id(p.getId());
                System.out.println(s.toString());
                player_stats.saveAndFlush(s);
            }
        }
    }

    @RequestMapping(path="/trade_player/{id1}/{id2}", method = RequestMethod.GET)
    public int tradePlayer(@PathVariable("id1") Integer id1, @PathVariable("id2") Integer id2, @PathVariable("offer") Integer offer) throws IOException {
        applicationServices.tradePlayers(id1, id2, offer);
        return 0;
    }

    @RequestMapping(path="/sign_player/{id}/{team_id}", method = RequestMethod.GET)
    public int signPlayer(@PathVariable("id") Integer id, @PathVariable("team_id") Integer team_id) throws IOException {
        applicationServices.signPlayer(id, team_id);
        return 1;
    }

    @RequestMapping(path="/delete_trade/{id}", method = RequestMethod.GET)
    public void deleteTrade(@PathVariable("id") Integer id) throws IOException {
        Trade trade = trades.findOne(id);
        trades.delete(trade);
        trades.flush();
    }

    @RequestMapping(path="/temp", method = RequestMethod.GET)
    public void temp() throws IOException {
        int i = 0;
        for(Player p: players.findAll()) {
            p.setLeague(leagues.findOne(i+1));
            i = (i + 1) % 3;
            players.saveAndFlush(p);
        }
    }

    @RequestMapping(path="/finalize_trade/{id}", method = RequestMethod.GET)
    public void finalizeTrade(@PathVariable("id") Integer id) throws IOException {
        Trade trade = trades.findOne(id);

        Player p1 = trade.getPlayer1Id();
        Player p2 = trade.getPlayer2Id();

        Team t = p1.getTeam();
        if(applicationServices.validateTrade(p1, p2) || t.getMoney() <= trade.getOffer())
            return;

        p1.setTeam(p2.getTeam());
        p2.setTeam(t);

        players.saveAndFlush(p1);
        players.saveAndFlush(p2);

        t.setMoney(t.getMoney() + trade.getOffer());
        teams.saveAndFlush(t);

        trades.delete(trade);
        trades.flush();
    }

    @RequestMapping(path="/get_trades", method = RequestMethod.GET)
    public String getTrades(HttpServletRequest req) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Fantasy_User user = (Fantasy_User) req.getSession().getAttribute("user");

        List<Trade> u_trades = new ArrayList<Trade>();

        List<Team> u_teams = teams.findByUser(user);

        for(Team t: u_teams) {
            List<Player> u_players = players.findAllByTeam_Id(t.getId());

            for(Player p: u_players) {
                List<Trade> m_trades = trades.findByPlayer2Id(p);

                for(Trade trade : m_trades)
                    u_trades.add(trade);
            }

        }

        String ret = mapper.writeValueAsString(u_trades);
        System.out.println(ret);
        return ret;
    }

//    @RequestMapping(path="/top_players", method = RequestMethod.GET)
//    public String getPoints() throws IOException {
//        List<Player> ps = players.findAll();
//        ArrayList<Player> top = new ArrayList<Player>();
//        int first = 0;
//        int second = 0;
//        for(Player p : ps) {
//            int points = calculatePlayerPoints(p);
//            if(top.isEmpty() || top.size() == 1)
//                top.add(p);
//            else if(points > first) {
//                top.remove(1);
//                top.add(0, p);
//                second = first;
//                first = calculatePlayerPoints(p);
//            } else if(points > second) {
//                top.remove(1);
//                top.add(1, p);
//                second = calculatePlayerPoints(p);
//            }
//        }
//        ObjectMapper mapper = new ObjectMapper();
//        String ret = mapper.writeValueAsString(top);
//        return ret;
//    }
}