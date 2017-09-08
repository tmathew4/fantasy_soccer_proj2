package com.ex.FantasySoccerLeague.Dao;

import com.ex.FantasySoccerLeague.tables.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Team_Dao extends JpaRepository<Team, Integer> {
}
