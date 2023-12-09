package com.CyHawkClash.Backend.Models.UserStatistics;

import com.CyHawkClash.Backend.Models.User.User;
import jakarta.persistence.*;

@Entity
public class UserStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "num_kills")
    private int num_kills;

    @Column(name = "num_deaths")
    private int num_deaths;

    @Column(name = "num_games")
    private int num_games;

    @Column(name = "num_first_bloods")
    private int num_first_bloods;

    @Column(name = "max_kills")
    private int max_kills;

    @Column(name = "num_victories")
    private int num_victories;

    public UserStatistics(User user) {
        this.user = user;
        this.max_kills = 0;
        this.num_games = 0;
        this.num_kills = 0;
        this.num_deaths = 0;
        this.num_first_bloods = 0;
        this.num_victories = 0;
    }

    public UserStatistics() {}

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public int getNum_kills() {
        return num_kills;
    }

    public void setNum_kills(int num_kills) {
        this.num_kills = num_kills;
    }

    public int getNum_deaths() {
        return num_deaths;
    }

    public void setNum_deaths(int num_deaths) {
        this.num_deaths = num_deaths;
    }

    public int getNum_games() {
        return num_games;
    }

    public void setNum_games(int num_games) {
        this.num_games = num_games;
    }

    public int getNum_first_bloods() {
        return num_first_bloods;
    }

    public void setNum_first_bloods(int num_first_bloods) {
        this.num_first_bloods = num_first_bloods;
    }

    public int getMax_kills() {
        return max_kills;
    }

    public void setMax_kills(int max_kills) {
        this.max_kills = max_kills;
    }

    public int getNum_victories() {
        return num_victories;
    }

    public void setNum_victories(int num_victories) {
        this.num_victories = num_victories;
    }
}
