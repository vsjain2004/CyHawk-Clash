package com.CyHawkClash.Backend.Models.Weapon;

import com.CyHawkClash.Backend.Models.Attachment.Attachment;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Weapon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "weapon_name")
    private String weapon_name;

    @Column(name = "base_fire_rate")
    private int base_fire_rate;

    @Column(name = "base_max_bullets")
    private int base_max_bullets;

    @Column(name = "base_bullet_size")
    private int base_bullet_size;

    @Column(name = "base_reload_rate")
    private int base_reload_rate;

    @Column(name = "base_range")
    private int base_range;

    @Column(name = "base_bullet_speed")
    private int base_bullet_speed;

    @Column(name = "base_damage")
    private int base_damage;

    @Column(name = "base_accuracy")
    private double base_accuracy;

    @Column(name = "base_spread")
    private double base_spread;

    public Weapon(String weapon_name, int base_fire_rate, int base_max_bullets, int base_bullet_size, int base_reload_rate, int base_range, int base_bullet_speed, int base_damage, double base_accuracy, double base_spread) {
        this.weapon_name = weapon_name;
        this.base_fire_rate = base_fire_rate;
        this.base_max_bullets = base_max_bullets;
        this.base_bullet_size = base_bullet_size;
        this.base_reload_rate = base_reload_rate;
        this.base_range = base_range;
        this.base_bullet_speed = base_bullet_speed;
        this.base_damage = base_damage;
        this.base_accuracy = base_accuracy;
        this.base_spread = base_spread;
    }

    public Weapon() {}

    public int getId() {
        return id;
    }

    public String getWeapon_name() {
        return weapon_name;
    }

    public int getBase_fire_rate() {
        return base_fire_rate;
    }

    public int getBase_max_bullets() {
        return base_max_bullets;
    }

    public int getBase_bullet_size() {
        return base_bullet_size;
    }

    public int getBase_reload_rate() {
        return base_reload_rate;
    }

    public int getBase_range() {
        return base_range;
    }

    public int getBase_bullet_speed() {
        return base_bullet_speed;
    }

    public int getBase_damage() {
        return base_damage;
    }

    public double getBase_accuracy() {
        return base_accuracy;
    }

    public double getBase_spread(){
        return base_spread;
    }

}
