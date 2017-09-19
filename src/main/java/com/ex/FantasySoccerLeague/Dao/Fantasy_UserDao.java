package com.ex.FantasySoccerLeague.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ex.FantasySoccerLeague.tables.Fantasy_User;

@Repository
public interface Fantasy_UserDao extends JpaRepository<Fantasy_User, Integer>{
    Fantasy_User findByEmail(String Email);

    //Fantasy_User save(Fantasy_User user);
}
