package com.CyHawkClash.Backend.Models.PlayerInGame;

import com.CyHawkClash.Backend.Models.Team.Team;
import com.CyHawkClash.Backend.Models.User.User;
import jakarta.persistence.*;

@Entity
public class PlayerInGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "deleted")
    private boolean deleted;


    public PlayerInGame(User user, Team team) {
        this.user = user;
        this.team = team;
        this.deleted = false;
    }

    public PlayerInGame() {
    }


    public int getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public Team getTeam() {
        return team;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete(){
        this.deleted = true;
    }
}

