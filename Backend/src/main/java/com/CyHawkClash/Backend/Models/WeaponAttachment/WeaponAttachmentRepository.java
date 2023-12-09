package com.CyHawkClash.Backend.Models.WeaponAttachment;

import com.CyHawkClash.Backend.Models.Attachment.Attachment;
import com.CyHawkClash.Backend.Models.UserWeaponAttachment.UserWeaponAttachment;
import com.CyHawkClash.Backend.Models.Weapon.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface WeaponAttachmentRepository extends JpaRepository<WeaponAttachment, Integer> {

    WeaponAttachment findById(@Param("id") int id);

    Collection<WeaponAttachment> findByWeapon(Weapon weapon);

    Collection<WeaponAttachment> findByAttachment(Attachment attachment);
}
