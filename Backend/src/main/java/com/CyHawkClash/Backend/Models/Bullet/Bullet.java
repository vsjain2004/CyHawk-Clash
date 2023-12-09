package com.CyHawkClash.Backend.Models.Bullet;

import com.CyHawkClash.Backend.Models.PlayerInGame.PlayerInGame;
import jakarta.persistence.*;

@Entity
public class Bullet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "playeringame_id")
    private PlayerInGame playerInGame;

    @Column(name = "size")
    private int size;

    @Column(name = "bulletRange")
    private int bulletRange;

    @Column(name = "speed")
    private int speed;

    @Column(name = "damage")
    private float damage;

    @Column(name = "X")
    private float X;

    @Column(name = "Y")
    private float Y;

    @Column(name = "old_x")
    private float old_x;

    @Column(name = "old_y")
    private float old_y;

    @Column(name = "angle")
    private float angle;

    @Column(name = "deleted")
    private boolean deleted;

    public Bullet(PlayerInGame playerInGame, int size, int bulletRange, int speed, float damage, float x, float y, float angle) {
        this.playerInGame = playerInGame;
        this.size = size;
        this.bulletRange = bulletRange;
        this.speed = speed;
        this.damage = damage;
        this.X = x;
        this.Y = y;
        this.old_x = x;
        this.old_y = y;
        this.angle = angle;
        this.deleted = false;
    }

    public Bullet() {}

    public int getId() {
        return id;
    }

    public PlayerInGame getPlayerInGame() {
        return playerInGame;
    }

    public int getSize() {
        return size;
    }

    public int getBulletRange() {
        return bulletRange;
    }

    public int getSpeed() {
        return speed;
    }

    public float getDamage() {
        return damage;
    }

    public float getAngle() {
        return angle;
    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        this.old_x = this.X;
        this.X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(float y) {
        this.old_y = this.Y;
        this.Y = y;
    }

    public float getOld_x(){
        return this.old_x;
    }

    public float getOld_y(){
        return  this.old_y;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete() {
        this.deleted = true;
    }

}
