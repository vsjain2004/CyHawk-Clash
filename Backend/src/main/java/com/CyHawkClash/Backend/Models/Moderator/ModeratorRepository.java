package com.CyHawkClash.Backend.Models.Moderator;

import com.CyHawkClash.Backend.Models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Integer> {

    Moderator findById(@Param("id") int id);

    Moderator findByUserAndDeleted(User user, boolean deleted);
}
