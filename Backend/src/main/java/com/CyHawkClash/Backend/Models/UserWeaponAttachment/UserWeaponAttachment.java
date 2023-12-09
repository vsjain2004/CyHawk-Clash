package com.CyHawkClash.Backend.Models.UserWeaponAttachment;

import com.CyHawkClash.Backend.Models.Attachment.Attachment;
import com.CyHawkClash.Backend.Models.UserWeapon.UserWeapon;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="UserWeaponAttachment")
public class UserWeaponAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Will this be weapons id? or user_weapon id?
    //If this is user_weapon, I will update this when the user_weapon db is created.
    @ManyToOne
    @JoinColumn(name = "userweapon_id")
    private UserWeapon userWeapon;

    @ManyToOne
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

    @Column(name = "active")
    private boolean active;


    public UserWeaponAttachment(UserWeapon userWeapon, Attachment attachment){
        this.userWeapon = userWeapon;
        this.attachment = attachment;
        this.active = false;
    }

    public UserWeaponAttachment(){}

    public int getId() {
        return id;
    }

    public void setUserWeapon(UserWeapon userWeapon){
        this.userWeapon = userWeapon;
    }

    public UserWeapon getUserWeapon(){
        return this.userWeapon;
    }

    public void setAttachment(Attachment attachment){
        this.attachment=attachment;
    }

    public Attachment getAttachment(){
        return this.attachment;
    }

    public void setActive(Boolean active){
        this.active=active;
    }

    public Boolean isActive(){
        return active;
    }

    public Map<String, String> serialize(){
        Map<String,String> serialized = new HashMap<>();

        serialized.put("id", Integer.toString(this.id));
        serialized.put("name", this.attachment.getName());
        serialized.put("fire_rate", Integer.toString(this.attachment.getFire_rate()));
        serialized.put("num_max_bullets", Integer.toString(this.attachment.getNum_max_bullets()));
        serialized.put("bullet_size", Integer.toString(this.attachment.getBullet_size()));
        serialized.put("reload_rate", Integer.toString(this.attachment.getReload_rate()));
        serialized.put("weapon_range", Integer.toString(this.attachment.getWeapon_range()));
        serialized.put("bullet_speed", Integer.toString(this.attachment.getBullet_speed()));
        serialized.put("damage", Integer.toString(this.attachment.getDamage()));
        serialized.put("accuracy", Double.toString(this.attachment.getAccuracy()));

        return serialized;
    }
}
