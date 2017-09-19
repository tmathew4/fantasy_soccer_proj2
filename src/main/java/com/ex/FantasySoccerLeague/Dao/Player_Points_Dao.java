package com.ex.FantasySoccerLeague.Dao;

import com.ex.FantasySoccerLeague.tables.League;
import com.ex.FantasySoccerLeague.tables.Player;
import com.ex.FantasySoccerLeague.tables.Player_Points;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Player_Points_Dao extends JpaRepository<Player_Points, Integer > {
    Player_Points findByPlayer(Player player);
    List <Player_Points> findByPlayerIn(List<Player> list);


}
