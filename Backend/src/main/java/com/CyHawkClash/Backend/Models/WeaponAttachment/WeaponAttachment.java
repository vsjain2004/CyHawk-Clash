package com.CyHawkClash.Backend.Models.WeaponAttachment;

import com.CyHawkClash.Backend.Models.Attachment.Attachment;
import com.CyHawkClash.Backend.Models.Weapon.Weapon;
import jakarta.persistence.*;

@Entity
public class WeaponAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "weapon_id")
    private Weapon weapon;

    @ManyToOne
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

    public WeaponAttachment(Weapon weapon, Attachment attachment) {
        this.weapon = weapon;
        this.attachment = attachment;
    }

    public WeaponAttachment() {

    }

    public int getId(){
        return this.id;
    }
    public Weapon getWeapon() {
        return weapon;
    }

    public Attachment getAttachment() {
        return attachment;
    }
}
