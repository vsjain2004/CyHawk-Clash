package com.CyHawkClash.Backend.Models.GameMap;

import com.CyHawkClash.Backend.Models.Game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameMapRepository extends JpaRepository<GameMap, Integer> {

   GameMap findById(@Param("id")int id);

   GameMap findByGame(Game game);

}
