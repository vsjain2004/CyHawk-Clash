package com.CyHawkClash.Backend.Models.PlayerInGameStatistics;

import com.CyHawkClash.Backend.Models.PlayerInGame.PlayerInGame;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
public class PlayerInGameStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "pig_id")
    private PlayerInGame playerInGame;

    @Column(name = "hp")
    private float hp;

    @Column(name = "x_coordinate")
    private float x_coordinate;

    @Column(name = "y_coordinate")
    private float y_coordinate;

    @Column(name = "old_x")
    private float old_x;

    @Column(name = "old_y")
    private float old_y;

    @Column(name = "num_kills")
    private int num_kills;

    @Column(name = "num_deaths")
    private int num_deaths;

    @Column(name = "deleted")
    private boolean deleted;

    public PlayerInGameStatistics(PlayerInGame playerInGame, float hp, float x_coordinate, float y_coordinate) {
        this.playerInGame = playerInGame;
        this.hp = hp;
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
        this.old_x = x_coordinate;
        this.old_y = y_coordinate;
        this.num_kills = 0;
        this.num_deaths = 0;
        this.deleted = false;
    }

    public PlayerInGameStatistics() {
    }

    public int getId(){
        return this.id;
    }

    public PlayerInGame getPlayerInGame() {
        return playerInGame;
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(float x_coordinate) {
        //sets old x coordinate before x is updated
        this.old_x = this.x_coordinate;
        this.x_coordinate = x_coordinate;
    }

    public float getY_coordinate() {
        return y_coordinate;
    }

    public void setY_coordinate(float y_coordinate) {
        this.old_y = this.y_coordinate;
        this.y_coordinate = y_coordinate;
    }

    public float getOld_x() {
        return old_x;
    }

    public float getOld_y() {
        return old_y;
    }

    public int getNum_kills(){
        return num_kills;
    }

    public void incNum_kills(){
        this.num_kills++;
    }

    public int getNum_deaths() {
        return num_deaths;
    }

    public void incNum_deaths() {
        this.num_deaths++;
    }

    public void delete() {
        this.deleted = false;
    }

    public boolean isDeleted(){
        return this.deleted;
    }

    public Map<String, String> serialize(){
        Map<String, String> serialized = new HashMap<>();
        serialized.put("username", playerInGame.getUser().getUsername());
        serialized.put("hp", Double.toString(this.hp));
        serialized.put("x", Double.toString(this.x_coordinate));
        serialized.put("y", Double.toString(this.y_coordinate));
        serialized.put("team", Boolean.toString(this.playerInGame.getTeam().isTeam()));
        serialized.put("num_kills", Integer.toString(this.num_kills));
        serialized.put("num_deaths", Integer.toString(this.num_deaths));
        return serialized;
    }
}
