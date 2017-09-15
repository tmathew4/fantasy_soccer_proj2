package com.ex.FantasySoccerLeague.Dao;

import com.ex.FantasySoccerLeague.tables.Player;
import com.ex.FantasySoccerLeague.tables.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Trade_Dao extends JpaRepository<Trade, Integer> {
    List<Trade> findByPlayer2Id(Player player2Id);
}
