package com.CyHawkClash.Backend.Models.Objective;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ObjectiveRepository extends JpaRepository<Objective, Integer> {


    List<Objective> findAll();

    Objective findById(@Param("id") int id);
}
