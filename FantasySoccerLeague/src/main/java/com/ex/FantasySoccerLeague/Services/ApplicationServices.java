package com.ex.FantasySoccerLeague.Services;

import com.ex.FantasySoccerLeague.Dao.Fantasy_UserDao;
import com.ex.FantasySoccerLeague.Dao.League_Dao;
import com.ex.FantasySoccerLeague.Dao.Team_Dao;
import com.ex.FantasySoccerLeague.tables.League;
import com.ex.FantasySoccerLeague.tables.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    League_Dao mLeagueDao;

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

    public void registerUser(Fantasy_User user) {
        Dao.saveAndFlush(user);
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
}
