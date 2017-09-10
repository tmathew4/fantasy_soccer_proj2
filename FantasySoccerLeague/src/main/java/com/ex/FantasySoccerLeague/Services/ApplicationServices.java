package com.ex.FantasySoccerLeague.Services;

import com.ex.FantasySoccerLeague.Dao.Fantasy_UserDao;
import com.ex.FantasySoccerLeague.Dao.Player_Dao;
import com.ex.FantasySoccerLeague.Dao.Team_Dao;
import com.ex.FantasySoccerLeague.Dao.Trade_Dao;
import com.ex.FantasySoccerLeague.tables.Player;
import com.ex.FantasySoccerLeague.tables.Team;
import com.ex.FantasySoccerLeague.tables.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ex.FantasySoccerLeague.tables.Fantasy_User;

import java.util.List;

@Service
public class ApplicationServices {

    @Autowired
    Fantasy_UserDao Dao;
    @Autowired
    Team_Dao DaoT;
    @Autowired
    Player_Dao playerDao;
    @Autowired
    Trade_Dao DaoTr;

    public Fantasy_User checkLogin(String email, String password){
        System.out.println(email + " " + password);
        Fantasy_User user = Dao.findByEmail(email);
        System.out.println(user.toString());
        if(password.equals(user.getPassword())){
            return user;
        }
        return null;
    }

    public Team myTeam(Integer id){
        Team team = DaoT.findOne(id);
        return team;
    }

    public Team viewAllTeam(){
        Team team = (Team) DaoT.findAll();
        return team;
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

    public Trade tradePlayers(Integer id1, Integer id2) {
        System.out.println("hello");
        Trade t = new Trade();
        t.setPlayer1Id(playerDao.findById(id1));
        t.setPlayer2Id(playerDao.findById(id2));
        DaoTr.save(t);
        return t;
    }

    public String signPlayer(Integer id, Integer team_id) {
        Player p = playerDao.findById(id);
        p.setTeam(DaoT.findOne(team_id));
        playerDao.save(p);
        return "Success";
    }

}
