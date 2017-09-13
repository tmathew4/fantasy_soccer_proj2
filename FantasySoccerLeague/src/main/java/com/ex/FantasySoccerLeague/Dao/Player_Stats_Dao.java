package com.ex.FantasySoccerLeague.Dao;

import com.ex.FantasySoccerLeague.tables.Player;
import com.ex.FantasySoccerLeague.tables.Player_Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Player_Stats_Dao extends JpaRepository<Player_Stats, Integer> {
    Player_Stats findOneByPlayerId(Player id);
}


