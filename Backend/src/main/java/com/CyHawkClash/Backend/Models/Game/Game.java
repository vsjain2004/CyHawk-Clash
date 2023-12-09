package com.CyHawkClash.Backend.Models.Game;

import com.CyHawkClash.Backend.Models.Moderator.Moderator;
import jakarta.persistence.*;

@Entity
@Table(name = "Game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @OneToOne
    @JoinColumn(name = "mod_id")
    private Moderator mod;

    @Column(name = "num_people_team")
    private int num_people_team;

    @Column(name = "open")
    private boolean open;

    @Column(name = "inLobby")
    private boolean inLobby;

    @Column(name = "deleted")
    private boolean deleted;

    public Game(){}

    public Game(Moderator mod, int num_people_team, boolean open){
        this.mod = mod;
        this.num_people_team = num_people_team;
        this.open = open;
        this.inLobby = true;
        this.deleted = false;
    }

    public int getId() {
        return id;
    }

    public boolean isOpen() {
        return open;
    }

    public Moderator getModerator(){
        return this.mod;
    }

    public void setNum_people_team(int num_people_team){
        this.num_people_team = num_people_team;
    }

    public int getNum_people_team(){
        return this.num_people_team;
    }

    public boolean isInLobby() {
        return inLobby;
    }

    public void setInLobby(boolean inLobby) {
        this.inLobby = inLobby;
    }

    public void delete(){
        this.deleted = true;
    }

    public boolean isDeleted(){
        return deleted;
    }
}
