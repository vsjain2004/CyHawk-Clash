package com.CyHawkClash.Backend.Models.UserStatistics;

import com.CyHawkClash.Backend.Models.User.User;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Integer> {

    UserStatistics findById(@Param("id") int id);

    Collection<UserStatistics> findByUser(User user);
}
