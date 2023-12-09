package com.CyHawkClash.Backend.Models.PlayerInGame;

import com.CyHawkClash.Backend.Models.Team.Team;
import com.CyHawkClash.Backend.Models.User.User;
import com.CyHawkClash.Backend.Models.WeaponAttachment.WeaponAttachment;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PlayerInGameRepository extends JpaRepository<PlayerInGame, Integer> {

    List<PlayerInGame> findAll();

    PlayerInGame findById(@Param("id") int id);

    PlayerInGame findByUserAndDeleted(User user, boolean deleted);

    Collection<PlayerInGame> findByTeam(Team team);
}
