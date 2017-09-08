package com.ex.FantasySoccerLeague.Dao;

import com.ex.FantasySoccerLeague.tables.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Position_Dao extends JpaRepository<Position, Integer> {
}
