package com.ex.FantasySoccerLeague.Dao;

import com.ex.FantasySoccerLeague.tables.League;
import com.ex.FantasySoccerLeague.tables.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Player_Dao extends JpaRepository<Player, Integer> {

    public List<Player> findAllByTeam_IdEquals(Integer i);
    public List<Player> findAllByTeam_IdGreaterThan(Integer i);
    public List<Player> findAllByTeam_IdIsNull();
    public List<Player> findAllByTeam_IdIsNotNull();
}
