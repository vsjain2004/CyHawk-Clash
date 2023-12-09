package com.CyHawkClash.Backend.Models.Team;

import com.CyHawkClash.Backend.Models.Game.Game;
import jakarta.persistence.*;

@Entity
@Table(name = "Team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;


    //TRUE == Red Team
    //FALSE == Black team
    @Column(name = "team")
    private boolean team;

    @Column(name = "deleted")
    private boolean deleted;

    public Team(Game game, boolean team) {
        this.game = game;
        this.team = team;
        this.deleted = false;
    }

    public Team() {}

    public int getId(){
        return this.id;
    }
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isTeam() {
        return team;
    }


    public void delete(){
        this.deleted = true;
    }

    public boolean isDeleted(){
        return deleted;
    }
}
