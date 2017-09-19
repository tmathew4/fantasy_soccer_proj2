package com.ex.FantasySoccerLeague.Dao;

import com.ex.FantasySoccerLeague.tables.Fantasy_User;
import com.ex.FantasySoccerLeague.tables.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface Team_Dao extends JpaRepository<Team, Integer> {
    List<Team> findByUser(Fantasy_User user);
    List<Team> findAllByLeagueId(Integer id);
    Team findAllByLeagueIdAndUser(Integer id, Fantasy_User user);
    Team save(Team team);
}
