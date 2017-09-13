package com.ex.FantasySoccerLeague.Services;

import com.ex.FantasySoccerLeague.Dao.*;
import com.ex.FantasySoccerLeague.tables.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
