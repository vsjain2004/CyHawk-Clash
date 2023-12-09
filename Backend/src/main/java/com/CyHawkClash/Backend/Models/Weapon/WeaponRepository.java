package com.CyHawkClash.Backend.Models.Weapon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface WeaponRepository extends JpaRepository<Weapon, Integer> {
    List<Weapon> findAll();

    Weapon findById(@Param("id") int id);

}