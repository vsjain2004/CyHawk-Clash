package com.CyHawkClash.Backend.Models.Attachment;

import jakarta.persistence.*;

@Entity
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "fire_rate")
    private int fire_rate;

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

    @Column(name = "damage")
    private int damage;

    @Column(name = "accuracy")
    private double accuracy;

    public Attachment(String name, int fire_rate, int num_max_bullets, int bullet_size, int reload_rate, int weapon_range, int bullet_speed, int damage, double accuracy) {
        this.name = name;
        this.fire_rate = fire_rate;
        this.num_max_bullets = num_max_bullets;
        this.bullet_size = bullet_size;
        this.reload_rate = reload_rate;
        this.weapon_range = weapon_range;
        this.bullet_speed = bullet_speed;
        this.damage = damage;
        this.accuracy = accuracy;
    }

    public Attachment() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getFire_rate() {
        return fire_rate;
    }

    public int getNum_max_bullets() {
        return num_max_bullets;
    }

    public int getBullet_size() {
        return bullet_size;
    }

    public int getReload_rate() {
        return reload_rate;
    }

    public int getWeapon_range() {
        return weapon_range;
    }

    public int getBullet_speed() {
        return bullet_speed;
    }

    public int getDamage() {
        return damage;
    }

    public double getAccuracy() {
        return accuracy;
    }
}
