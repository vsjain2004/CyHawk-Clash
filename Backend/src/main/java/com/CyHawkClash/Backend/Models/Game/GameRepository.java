package com.CyHawkClash.Backend.Models.Game;

import com.CyHawkClash.Backend.Models.Moderator.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Game findById(@Param("id")int id);

    Game findByModAndDeleted(Moderator moderator, boolean deleted);

    Collection<Game> findByInLobbyAndOpenAndDeleted(boolean inLobby, boolean open, boolean deleted);

}
