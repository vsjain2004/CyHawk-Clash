package com.CyHawkClash.Backend.Models.UserWeapon;

import com.CyHawkClash.Backend.Models.User.User;
import com.CyHawkClash.Backend.Models.Weapon.Weapon;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
public class UserWeapon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "weapon_id")
    private Weapon weapon;

    @Column(name = "fire_rate")
    private int fire_rate;

    @Column(name ="num_bullets")
    private int num_bullets;

    @Column(name = "num_max_bullets")
    private int num_max_bullets;

    @Column(name = "bullet_size")
    private int bullet_size;

    @Column(name = "reload_rate")
    private int reload_rate;

    @Column(name = "weapon_range")
    private int weapon_range;

    @Column(name = "bullet_speed")
    private int bullet_speed;

    @Column(name = "weapon_spread")
    private double weapon_spread;

    @Column(name = "damage")
    private int damage;

    @Column(name = "accuracy")
    private double accuracy;

    @Column(name = "equipped")
    private boolean equipped;

    @Column(name = "num_kills")
    private int num_kills;

    public UserWeapon(User user, Weapon weapon) {
        this.user = user;
        this.weapon = weapon;
        this.fire_rate = weapon.getBase_fire_rate();
        this.num_bullets = weapon.getBase_max_bullets();
        this.num_max_bullets = weapon.getBase_max_bullets();
        this.bullet_size = weapon.getBase_bullet_size();
        this.reload_rate = weapon.getBase_reload_rate();
        this.weapon_range = weapon.getBase_range();
        this.bullet_speed = weapon.getBase_bullet_speed();
        this.damage = weapon.getBase_damage();
        this.accuracy = weapon.getBase_accuracy();
        this.weapon_spread = weapon.getBase_spread();
        this.equipped = false;
        this.num_kills = 0;
    }

    public UserWeapon() {}

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getFire_rate() {
        return fire_rate;
    }

    public void setFire_rate(int fire_rate) {
        this.fire_rate = fire_rate;
    }

    public int getNum_bullets() {
        return num_bullets;
    }

    public void setNum_bullets(int num_bullets) {
        this.num_bullets = num_bullets;
    }

    public int getNum_max_bullets() {
        return num_max_bullets;
    }

    public void setNum_max_bullets(int num_max_bullets) {
        this.num_max_bullets = num_max_bullets;
    }

    public int getBullet_size() {
        return bullet_size;
    }

    public void setBullet_size(int bullet_size) {
        this.bullet_size = bullet_size;
    }

    public int getReload_rate() {
        return reload_rate;
    }

    public void setReload_rate(int reload_rate) {
        this.reload_rate = reload_rate;
    }

    public int getWeapon_range() {
        return weapon_range;
    }

    public void setWeapon_range(int weapon_range) {
        this.weapon_range = weapon_range;
    }

    public int getBullet_speed() {
        return bullet_speed;
    }

    public void setBullet_speed(int bullet_speed) {
        this.bullet_speed = bullet_speed;
    }

    public double getWeapon_spread() {
        return weapon_spread;
    }

    public void setWeapon_spread(double weapon_spread) {
        this.weapon_spread = weapon_spread;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public int getNum_kills() {
        return num_kills;
    }

    public void setNum_kills(int num_kills) {
        this.num_kills = num_kills;
    }

    public Map<String, String> serialize(){
        Map<String, String> serialized = new HashMap<>();

        serialized.put("weapon_id", Integer.toString(this.weapon.getId()));
        serialized.put("name", weapon.getWeapon_name());
        serialized.put("fire_rate", Integer.toString(this.fire_rate));
        serialized.put("num_max_bullets", Integer.toString(this.num_max_bullets));
        serialized.put("bullet_size", Integer.toString(this.bullet_size));
        serialized.put("reload_rate", Integer.toString(this.reload_rate));
        serialized.put("weapon_range", Integer.toString(this.weapon_range));
        serialized.put("bullet_speed", Integer.toString(this.bullet_speed));
        serialized.put("weapon_spread", Double.toString(this.weapon_spread));
        serialized.put("damage", Integer.toString(this.damage));
        serialized.put("accuracy", Double.toString(this.accuracy));
        serialized.put("equipped", Boolean.toString(this.equipped));
        serialized.put("num_kills", Integer.toString(this.num_kills));

        return serialized;
    }
}
