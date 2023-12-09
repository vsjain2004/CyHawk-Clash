package com.CyHawkClash.Backend.Models.GameStatistics;

import com.CyHawkClash.Backend.Models.Game.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameStatisticsRepository extends JpaRepository<GameStatistics, Integer> {
    GameStatistics findById(@Param("id") int id);

    GameStatistics findByGame(Game game);
}
