package com.ex.FantasySoccerLeague.Dao;

import com.ex.FantasySoccerLeague.tables.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Game_Dao extends JpaRepository<Game, Integer> {
}
