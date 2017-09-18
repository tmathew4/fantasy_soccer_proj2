package com.ex.FantasySoccerLeague.Services;

import com.ex.FantasySoccerLeague.Dao.*;
import com.ex.FantasySoccerLeague.tables.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
    @Autowired
    Player_Stats_Dao playerStatsDao;

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

    public Team viewMyTeamInLeague(Integer id, Fantasy_User user){
        return DaoT.findAllByLeagueIdAndUser(id, user);
    }

    public List<League> viewAllLeagues(){
        return DaoL.findAll();
    }

    public void registerUser(Fantasy_User user) {
        Dao.saveAndFlush(user);
    }

    public List<Player> findAllLeaguePlayers(int id){
        return playerDao.findAllByLeague_Id(id);
    }

    public List<Player> findAllPlayers(){
        return playerDao.findAll();
    }

    public List<Player> findAvailablePlayers(){
        return playerDao.findAllByTeam_IdIsNull();
    }

    public List<Player> findAvailableLeaguePlayers(int id){
        return playerDao.findAllByLeague_IdAndTeam_IdIsNull(id);
    }

    public List<Player> findUnavailablePlayers() {
        return playerDao.findAllByTeam_IdIsNotNull();
    }

    public List<Player> findUnavailableLeaguePlayers(int id) {
        return playerDao.findAllByLeague_IdAndTeam_IdIsNotNull(id);
    }

    public List<League> findAllLeagues(){
        return DaoL.findAll();
    }

    public Trade tradePlayers(Integer id1, Integer id2, Integer offer) {
        System.out.println("hello");

        Player p = playerDao.findById(id1);
        if(p.getTeam().getMoney() < offer)
            return null;

        Trade t = new Trade();
        t.setPlayer1Id(p);
        t.setPlayer2Id(playerDao.findById(id2));
        DaoTr.saveAndFlush(t);
        return t;
    }

    public String signPlayer(Integer id, Integer team_id) throws JsonProcessingException, IOException{
        Player p = playerDao.findById(id);

        Team t = DaoT.findOne(team_id);
        if(t.getMoney() < p.getCost())
            return "Failure";

        ArrayList<Player> team = (ArrayList) playerDao.findAllByTeam_Id(team_id);
        team.add(p);

        ObjectMapper mapper = new ObjectMapper();
        if(!validateTeam(mapper.writeValueAsString(team)))
            return "Failure";

        p.setTeam(t);
        playerDao.saveAndFlush(p);
        t.setMoney(t.getMoney() - p.getCost());
        DaoT.saveAndFlush(t);
        return "Success";
    }

    public boolean validateTrade(Player p1, Player p2) throws JsonProcessingException, IOException{
        ObjectMapper mapper = new ObjectMapper();

        ArrayList<Player> team = (ArrayList) playerDao.findAllByTeam_Id(p1.getTeam().getId());
        team.remove(p1);
        team.add(p2);
        if(!validateTeam(mapper.writeValueAsString(team)))
            return false;

        team = (ArrayList) playerDao.findAllByTeam_Id(p2.getTeam().getId());
        team.remove(p2);
        team.add(p1);
        if(!validateTeam(mapper.writeValueAsString(team)))
            return false;

        return true;
    }

    public String dropPlayer(Integer id){
        Player p = playerDao.findById(id);

//        Team t = p.getTeam();
        p.setTeam(null);
        playerDao.saveAndFlush(p);

//        t.setMoney(t.getMoney() + (p.getCost()/2));
//        DaoT.saveAndFlush(t);
        return "Success";
    }

    public Team registerTeam(Integer leagueId, String teamName, Fantasy_User user) {
        Team team = new Team();
        team.setId(-1);
        team.setName(teamName);
        team.setPoints(0);
        team.setUser(user);
        team.setLeague(mLeagueDao.findOne(leagueId));
        team.setMoney(100);
        return DaoT.saveAndFlush(team);
    }

    public Player_Stats getPlayerStats(Integer player_id){
        return playerStatsDao.findOneByPlayerId(playerDao.findOne(player_id));
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
                DEF = 2, GOAL = 1, ATK = 4, MID = 3;

        int defenders = 0, goalies = 0, attackers = 0, midfielders = 0;

        ObjectMapper mapper = new ObjectMapper();
//        Player[] players = mapper.readValue(json, Player[].class);
//        List<Player> players = mapper.readValue(json, new TypeReference<List<Player>>(){});
        List<Player> team = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Player.class));
        if(team.size() > MAX_TEAM_SIZE)
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

    public int generate(int min, int max) {
        return (int)Math.floor(Math.random() * max + min);
    }

    /**
     * Generates random weekly points for each player.
     * Should remove and create a data wipe when app is deployed to live environment.
     */
    public void generateWeeklyPoints() {
        List<Player_Points> weeklyPoints = mWeeklyPointsDao.findAll();
        for(Player_Points player : weeklyPoints) {
            System.out.println(player);
            player.setGoals(generate(0, 3));
            player.setAssists(generate(0, 7));
            player.setOwn_Goals(generate(0, 1));
            player.setSOG(generate(0, 14));
            player.setYellow_Cards(generate(0, 2));
            player.setRed_Cards(generate(0, 1));
            mWeeklyPointsDao.saveAndFlush(player);
        }
    }

    public void updatePoints() {
        List<Player_Points> weeklyPoints = mWeeklyPointsDao.findAll();
        for(Player_Points player : weeklyPoints) {
            int playerId = player.getPlayer().getId();

            System.out.println(player.getPlayer());

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

    public void resetPoints() {
        List<Player_Points> weeklyPoints = mWeeklyPointsDao.findAll();
        for(Player_Points player : weeklyPoints) {
            int playerId = player.getPlayer().getId();

            Player overallPoints = playerDao.findOne(playerId);
            overallPoints.setGoals(0);
            overallPoints.setAssists(0);
            overallPoints.setOwn_Goals(0);
            overallPoints.setSOG(0);

            //getYellowCard and getYellowCards <-- one has an s, the other doesn't
            overallPoints.setYellow_Card(0);
            overallPoints.setRed_Card(0);

            playerDao.saveAndFlush(overallPoints);
        }

        List<Team> teams = DaoT.findAll();
        for(Team t : teams) {
            t.setPoints(0);
            DaoT.saveAndFlush(t);
        }
    }

    public void resetTeams() {
        List<Player> players = playerDao.findAll();
        for(Player p : players) {
            p.setTeam(null);
            playerDao.saveAndFlush(p);
        }

        List<Team> teams = DaoT.findAll();
        for(Team t : teams) {
            t.setMoney(100);
            DaoT.saveAndFlush(t);
        }

        DaoTr.deleteAll();
        DaoTr.flush();
    }

    public void updateTeamPoints(Integer teamId) {
        List<Player> team = playerDao.findAllByTeam_Id(teamId);
        Integer teamTotal = 0;
        System.out.println(team);
        for(Player player : team) {
            Player_Points points = mWeeklyPointsDao.findByPlayer(player);
            teamTotal += points.getGoals() * (4 + player.getPosition().getId());
            teamTotal += 3 * points.getAssists();
            teamTotal += points.getSOG();
            teamTotal += 2 * Math.negateExact(points.getOwn_Goals());
            teamTotal += Math.negateExact(points.getYellow_Cards());
            teamTotal += 3 * Math.negateExact(points.getRed_Cards());
        }

        Team updatedTeam = DaoT.findOne(teamId);
        updatedTeam.setPoints(updatedTeam.getPoints() + teamTotal);
        updatedTeam.setMoney(updatedTeam.getMoney() + (int)(teamTotal/20));
        DaoT.saveAndFlush(updatedTeam);
    }


    public int calculatePlayerPoint(Player_Points p){
        int points = 0;
        points += p.getGoals()* (4+p.getPlayer().getPosition().getId());
        points += 3*p.getAssists();
        points += p.getSOG();
        points += (2*Math.negateExact(p.getOwn_Goals()));
        points += Math.negateExact(p.getYellow_Cards());
        points += (3*Math.negateExact(p.getRed_Cards()));
        return points;
    }

    public List<Player_Points> getTopPlayersFromAllLeagues(){
        List<League> leagues = mLeagueDao.findAll();
        List <List<Player_Points>> topPlayers = new ArrayList<>();
        List<Player_Points> topPlayersList = new ArrayList<>();
        for(League a : leagues){
            topPlayers.add(gettopplayers(a));
        }
        for(List<Player_Points> list: topPlayers){
            for(Player_Points player_points: list){
                topPlayersList.add(player_points);
            }
        }
        return topPlayersList;
    }

    public List<Player_Points> gettopplayers(League a){
        List<Player_Points> players = mWeeklyPointsDao.findByPlayerIn(playerDao.findAllByLeague_Id(a.getId()));
        System.out.println(players);
        List<Player_Points> topPlayer =  new ArrayList<>();
        int maxPoints1 = 0;
        int maxPoints2= 0;
        Player_Points maxPlayer1 = null;
        Player_Points maxPlayer2 = null;
        int points;
        for(Player_Points p : players){
                points = calculatePlayerPoint(p);
                if (maxPoints1 <= points) {
                    maxPoints2 = maxPoints1;
                    maxPoints1 = points;
                    maxPlayer2 = maxPlayer1;
                    maxPlayer1 = p;
                } else if (maxPoints2 <= points) {
                    maxPoints2 = points;
                    maxPlayer2 = p;
                }
        }
        topPlayer.add(maxPlayer1);
        topPlayer.add(maxPlayer2);
        return  topPlayer;
    }

    public List<Team> getTopTeamsFromAllLeagues(){
        List<League> leagues = mLeagueDao.findAll();
        List<List<Team>> topTeamsBigArray = new ArrayList<>();
        List<Team> topTeams = new ArrayList<>();
        for(League a : leagues){
            topTeamsBigArray.add(getTopTeams(a));
        }

        for(List<Team> team: topTeamsBigArray){
            for (Team myteam: team){
                topTeams.add(myteam);
            }
        }
        return topTeams;
    }

    public List<Team> getTopTeams(League a) {
        System.out.println("--------------------------------");
        List<Team> teams = DaoT.findAllByLeagueId(a.getId());
        System.out.println(teams);
        List<Team> topTeams = new ArrayList<>();
        int maxPoints1 = 0;
        int maxPoints2 = 0;
        Team team1 = null;
        Team team2 = null;
        int points = 0;
        for (Team team : teams) {
            points = team.getPoints();
            if (maxPoints1 <= points) {
                maxPoints2 = maxPoints1;
                maxPoints1 = points;
                team2 = team1;
                team1 = team;
            } else if (maxPoints2 <= points) {
                maxPoints2 = points;
                team2 = team;
            }
        }
        topTeams.add(team1);
        topTeams.add(team2);
        return topTeams;
    }

    public void updateAllTeamPoints() {
        for(Team t: DaoT.findAll()) {
            updateTeamPoints(t.getId());
        }
    }

    public void generatePlayerPoints() {
        for(Player p : playerDao.findAll()) {
            if(mWeeklyPointsDao.findByPlayer(p) == null) {
                Player_Points pp = new Player_Points();
                pp.setPlayer(p);
                mWeeklyPointsDao.saveAndFlush(pp);
            }
        }

    }

    int randomWithRange(int min, int max){
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public void returnMoneyToTeam(int player_id){
        Player p = playerDao.findById(player_id);
        Team team = DaoT.findOne(p.getTeam().getId());
        team.setMoney(team.getMoney()+(p.getCost()/2));
        DaoT.saveAndFlush(team);
    }

}
