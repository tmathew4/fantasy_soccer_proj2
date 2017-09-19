package com.ex.FantasySoccerLeague.Dao;

import com.ex.FantasySoccerLeague.tables.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface League_Dao extends JpaRepository<League, Integer> {

}
