package com.CyHawkClash.Backend.Models.UserWeapon;

import com.CyHawkClash.Backend.Models.User.User;
import com.CyHawkClash.Backend.Models.Weapon.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserWeaponRepository extends JpaRepository<UserWeapon, Integer>{
    List<UserWeapon> findAll();

    UserWeapon findById(@Param("id") int id);

    Collection<UserWeapon> findByUser(User user);

    UserWeapon findByUserAndEquipped(User user, boolean equipped);

    UserWeapon findByUserAndWeapon(User user, Weapon weapon);

    Collection<UserWeapon> findByWeapon(Weapon weapon);
}
