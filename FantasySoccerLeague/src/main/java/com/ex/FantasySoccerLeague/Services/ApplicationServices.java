package com.ex.FantasySoccerLeague.Services;

import com.ex.FantasySoccerLeague.Dao.Fantasy_UserDao;
import com.ex.FantasySoccerLeague.Dao.League_Dao;
import com.ex.FantasySoccerLeague.Dao.Player_Dao;
import com.ex.FantasySoccerLeague.Dao.Team_Dao;
import com.ex.FantasySoccerLeague.tables.League;
import com.ex.FantasySoccerLeague.tables.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ex.FantasySoccerLeague.tables.Fantasy_User;

import java.util.ArrayList;

@Service
public class ApplicationServices {

    @Autowired
    Fantasy_UserDao Dao;
    @Autowired
    Team_Dao DaoT;
    @Autowired
    League_Dao DaoL;
    @Autowired
    Player_Dao player_dao;

    public Fantasy_User checkLogin(String email, String password){
        Fantasy_User user = Dao.findByEmail(email);
        if(password.equals(user.getPassword())){
            return user;
        }
        return null;
    }

    public Team myTeam(Integer id){
        Team team = DaoT.findOne(id);
        return team;
    }

    public ArrayList<Team> viewAllTeam(Integer id){
       ArrayList<Team> names = new ArrayList<>();
            League league = DaoL.findOne(id);
            Integer nbr = league.getId();
            Team team = DaoT.findOne(id);
            League n = team.getLeague();

            if(nbr.equals(n)){
                names.add(team);
            }
        return names;
    }

    


}
