package com.CyHawkClash.Backend.Models.Bullet;

import com.CyHawkClash.Backend.Models.PlayerInGame.PlayerInGame;
import com.CyHawkClash.Backend.Models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BulletRepository extends JpaRepository<Bullet, Integer>{

    Bullet findById(@Param("id") int id);

    Collection<Bullet> findByPlayerInGameAndDeleted(PlayerInGame playerInGame, boolean deleted);
}
