package com.ex.FantasySoccerLeague.Services;

import com.ex.FantasySoccerLeague.Dao.Fantasy_UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ex.FantasySoccerLeague.tables.Fantasy_User;

@Service
public class ApplicationServices {

    @Autowired
    Fantasy_UserDao Dao;

    public Fantasy_User checkLogin(String email, String password){
        Fantasy_User user = Dao.findByEmail(email);
        if(password.equals(user.getPassword())){
            return user;
        }
        return null;
    }


}
