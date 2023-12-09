package com.CyHawkClash.Backend.Models.Attachment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
    Attachment findById(@Param("id") int id);

}
