package com.CyHawkClash.Backend.Models.Team;

import com.CyHawkClash.Backend.Models.Game.Game;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

    Team findById(@Param("id") int id);

    Collection<Team> findByGameAndDeleted(Game game, boolean deleted);

    Team findByGameAndTeamAndDeleted(Game game, boolean team, boolean deleted);
}
