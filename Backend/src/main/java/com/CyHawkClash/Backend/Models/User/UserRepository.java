package com.CyHawkClash.Backend.Models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(@Param("id") int id);

    User findByUsernameAndDeleted(String username, boolean deleted);

    User findByEmailAndDeleted(String email, boolean deleted);

    User findByPhoneAndDeleted(String phone, boolean deleted);

}
