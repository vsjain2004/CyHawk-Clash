package com.CyHawkClash.Backend.Models.UserAchievement;

import com.CyHawkClash.Backend.Models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievement, Integer> {

    UserAchievement findById(@Param("id")int id);

    Collection<UserAchievement> findByUser(User user);

}
