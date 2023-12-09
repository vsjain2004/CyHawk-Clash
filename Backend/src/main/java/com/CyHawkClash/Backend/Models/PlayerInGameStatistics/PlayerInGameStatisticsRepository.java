package com.CyHawkClash.Backend.Models.PlayerInGameStatistics;

import com.CyHawkClash.Backend.Models.PlayerInGame.PlayerInGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerInGameStatisticsRepository extends JpaRepository<PlayerInGameStatistics, Integer> {

    List<PlayerInGameStatistics> findAll();

    PlayerInGameStatistics findById(@Param("id")int id);

    PlayerInGameStatistics findByPlayerInGameAndDeleted(PlayerInGame playerInGame, boolean deleted);
}
