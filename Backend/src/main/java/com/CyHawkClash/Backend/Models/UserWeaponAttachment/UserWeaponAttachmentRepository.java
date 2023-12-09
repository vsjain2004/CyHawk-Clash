package com.CyHawkClash.Backend.Models.UserWeaponAttachment;

import com.CyHawkClash.Backend.Models.Attachment.Attachment;
import com.CyHawkClash.Backend.Models.UserWeapon.UserWeapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserWeaponAttachmentRepository extends JpaRepository<UserWeaponAttachment, Integer> {

    List<UserWeaponAttachment> findAll();

    UserWeaponAttachment findById(@Param("id") int id);

    Collection<UserWeaponAttachment> findByUserWeapon(UserWeapon userWeapon);

    UserWeaponAttachment findByUserWeaponAndActive(UserWeapon userWeapon, boolean isActive);

    Collection<UserWeaponAttachment> findByAttachment(Attachment attachment);
}
