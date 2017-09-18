package com.ex.FantasySoccerLeague.Dao;

import com.ex.FantasySoccerLeague.tables.League;
import com.ex.FantasySoccerLeague.tables.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Player_Dao extends JpaRepository<Player, Integer> {


    public List<Player> findAllByLeague_Id(Integer id);
    public List<Player> findAllByLeague_IdAndTeam_IdIsNull(Integer id);
    public List<Player> findAllByTeam_Id(Integer i);
    public List<Player> findAllByTeam_IdIsNull();
    public List<Player> findAllByTeam_IdIsNotNull();
    public List<Player> findAllByLeague_IdAndTeam_IdIsNotNull(Integer id);
    public Player findById(Integer id);
    public Player findByName(String name);
}
