package com.ex.FantasySoccerLeague.Dao;

import com.ex.FantasySoccerLeague.tables.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Trade_Dao extends JpaRepository<Trade, Integer> {
}
