package com.CyHawkClash.Backend.Models.TeamStatistics;

import com.CyHawkClash.Backend.Models.Team.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamStatisticsRepository extends JpaRepository<TeamStatistics, Integer> {

    TeamStatistics findById(@Param("id") int id);

    TeamStatistics findByTeamAndDeleted(Team team, boolean deleted);
}
