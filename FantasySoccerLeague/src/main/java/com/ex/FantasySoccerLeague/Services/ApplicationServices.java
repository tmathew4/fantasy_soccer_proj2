package com.ex.FantasySoccerLeague.Services;

import com.ex.FantasySoccerLeague.Dao.*;
import com.ex.FantasySoccerLeague.tables.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
@Service
public class ApplicationServices {

    @Autowired
    Fantasy_UserDao Dao;
    @Autowired
    Team_Dao DaoT;
    @Autowired
    League_Dao DaoL;
    @Autowired
    Player_Dao playerDao;
    @Autowired
    Trade_Dao DaoTr;
    @Autowired
    League_Dao mLeagueDao;
    @Autowired
    Player_Points_Dao mWeeklyPointsDao;


    public Fantasy_User checkLogin(String email, String password){
        System.out.println(email + " " + password);
        Fantasy_User user = Dao.findByEmail(email);
        System.out.println(user.toString());
        if(password.equals(user.getPassword())){
            return user;
        }
        return null;
    }

    public List<Player> myTeam(Integer id){
        return playerDao.findAllByTeam_Id(id);
    }

    public List<Team> viewAllTeam(Integer id){
        return DaoT.findAllByLeagueId(id);
    }

    public void registerUser(Fantasy_User user) {
        Dao.saveAndFlush(user);
    }

     public List<Player> findAllPlayers(){
        return playerDao.findAll();
    }

    public List<Player> findAvailablePlayers(){
        return playerDao.findAllByTeam_IdIsNull();
    }

    public List<Player> findUnavailablePlayers() {
        return playerDao.findAllByTeam_IdIsNotNull();
    }

    public List<League> findAllLeagues(){
        return DaoL.findAll();
    }

    public Trade tradePlayers(Integer id1, Integer id2) {
        System.out.println("hello");
        Trade t = new Trade();
        t.setPlayer1Id(playerDao.findById(id1));
        t.setPlayer2Id(playerDao.findById(id2));
        DaoTr.saveAndFlush(t);
        return t;
    }

    public String signPlayer(Integer id, Integer team_id) {
        Player p = playerDao.findById(id);
        p.setTeam(DaoT.findOne(team_id));
        playerDao.saveAndFlush(p);
        return "Success";
    }

    public Team registerTeam(Integer leagueId, String teamName, Fantasy_User user) {
        Team team = new Team();
        team.setId(-1);
        team.setName(teamName);
        team.setPoints(0);
        team.setUser(user);
        team.setLeague(mLeagueDao.findOne(leagueId));
        return DaoT.saveAndFlush(team);
    }

    public static String hashPassword(String input) {
        String md5 = null;
        if(null == input) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }

    public boolean validateTeam(String json) throws IOException {
        final int MAX_TEAM_SIZE = 14, FOUR = 4, TWO = 2,
                DEF = 1, GOAL = 2, ATK = 3, MID = 4;

        int defenders = 0, goalies = 0, attackers = 0, midfielders = 0;

        ObjectMapper mapper = new ObjectMapper();
//        Player[] players = mapper.readValue(json, Player[].class);
//        List<Player> players = mapper.readValue(json, new TypeReference<List<Player>>(){});
        List<Player> team = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Player.class));
        if(team.size() != MAX_TEAM_SIZE)
            return false;
        else {
            for(Player player : team) {
                int position = player.getPosition().getId();
                switch(position) {
                    case DEF:
                        if(defenders < FOUR)
                            defenders++;
                        else
                            return false;
                        break;
                    case GOAL:
                        if(goalies < TWO)
                            goalies++;
                        else
                            return false;
                        break;
                    case ATK:
                        if(attackers < FOUR)
                            attackers++;
                        else
                            return false;
                        break;
                    case MID:
                        if(midfielders < FOUR)
                            midfielders++;
                        else
                            return false;
                        break;
                    default:
                        return false;
                }
            }
        }
        return true;
    }

    private int generate(int min, int max) {
        return (int)Math.floor(Math.random() * max + min);
    }

    /**
     * Generates random weekly points for each player.
     * Should remove and create a data wipe when app is deployed to live environment.
     */
    public void generateWeeklyPoints() {
        List<Player_Points> weeklyPoints = mWeeklyPointsDao.findAll();
        for(Player_Points player : weeklyPoints) {
            player.setGoals(generate(0, 7));
            player.setAssists(generate(0, 14));
            player.setOwn_Goals(generate(0, 3));
            player.setSOG(generate(0, 7));
            player.setYellow_Cards(generate(0, 3));
            player.setRed_Cards(generate(0, 2));
            mWeeklyPointsDao.saveAndFlush(player);
        }
    }

    public void updatePoints() {
        List<Player_Points> weeklyPoints = mWeeklyPointsDao.findAll();
        for(Player_Points player : weeklyPoints) {
            int playerId = player.getPlayer().getId();

            Player overallPoints = playerDao.findOne(playerId);
            overallPoints.setGoals(overallPoints.getGoals() + player.getGoals());
            overallPoints.setAssists(overallPoints.getAssists() + player.getAssists());
            overallPoints.setOwn_Goals(overallPoints.getOwn_Goals() + player.getOwn_Goals());
            overallPoints.setSOG(overallPoints.getSOG() + player.getSOG());

            //getYellowCard and getYellowCards <-- one has an s, the other doesn't
            overallPoints.setYellow_Card(overallPoints.getYellow_Card() + player.getYellow_Cards());
            overallPoints.setRed_Card(overallPoints.getRed_Card() + player.getRed_Cards());

            playerDao.saveAndFlush(overallPoints);
        }
    }

    public void updateTeamPoints(Integer teamId) {
        List<Player> team = playerDao.findAllByTeam_Id(teamId);
        Integer teamTotal = 0;
        for(Player player : team) {
            Player_Points points = mWeeklyPointsDao.findByPlayer(player);
            teamTotal += points.getGoals();
            teamTotal += points.getAssists();
            teamTotal += points.getSOG();
            teamTotal += Math.negateExact(points.getOwn_Goals());
            teamTotal += Math.negateExact(points.getYellow_Cards());
            teamTotal += Math.negateExact(points.getRed_Cards());
        }

        Team updatedTeam = DaoT.findOne(teamId);
        updatedTeam.setPoints(teamTotal);
        DaoT.saveAndFlush(updatedTeam);
    }
}
