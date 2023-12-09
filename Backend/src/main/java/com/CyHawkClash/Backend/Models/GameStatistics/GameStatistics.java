package com.CyHawkClash.Backend.Models.GameStatistics;

import com.CyHawkClash.Backend.Models.Game.Game;
import jakarta.persistence.*;

@Entity
public class GameStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "totalKills")
    private int totalKills;

    @Column(name = "roundNumber")
    private int roundNumber;

    @Column(name = "time")
    private int time;

    public GameStatistics(Game game) {
        this.game = game;
        this.totalKills = 0;
        this.roundNumber = 1;
        this.time = 120;
    }

    public GameStatistics() {}

    public int getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public void setTotalKills(int totalKills) {
        this.totalKills = totalKills;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public int getTime() {
        return time;
    }

    public void decTime() {
        this.time -= 1;
    }
}
