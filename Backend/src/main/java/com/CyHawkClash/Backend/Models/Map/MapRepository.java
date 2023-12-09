package com.CyHawkClash.Backend.Models.Map;

import com.CyHawkClash.Backend.Models.Map.Map;
import com.CyHawkClash.Backend.Models.Objective.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface MapRepository extends JpaRepository<Map, Integer> {

    Map findById(@Param("id") int id);

    Collection<Map> findByObj(Objective obj);
}
