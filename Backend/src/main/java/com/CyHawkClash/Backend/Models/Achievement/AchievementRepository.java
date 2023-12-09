package com.CyHawkClash.Backend.Models.Achievement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Integer> {

    List<Achievement> findAll();

    Achievement findById(@Param("id") int id);

}
