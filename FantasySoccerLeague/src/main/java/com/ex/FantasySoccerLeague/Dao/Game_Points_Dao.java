package com.ex.FantasySoccerLeague.Dao;

import com.ex.FantasySoccerLeague.tables.Player_Points;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Game_Points_Dao extends JpaRepository<Player_Points, Integer > {
}
