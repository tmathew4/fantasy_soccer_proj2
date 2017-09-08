package com.ex.FantasySoccerLeague.Services;

import com.ex.FantasySoccerLeague.Dao.Fantasy_UserDao;
import com.ex.FantasySoccerLeague.Dao.Team_Dao;
import com.ex.FantasySoccerLeague.tables.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ex.FantasySoccerLeague.tables.Fantasy_User;

@Service
public class ApplicationServices {

    @Autowired
    Fantasy_UserDao Dao;
    @Autowired
    Team_Dao DaoT;

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




}
