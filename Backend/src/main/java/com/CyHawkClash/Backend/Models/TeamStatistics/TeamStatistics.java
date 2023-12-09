package com.CyHawkClash.Backend.Models.TeamStatistics;

import com.CyHawkClash.Backend.Models.PlayerInGame.PlayerInGame;
import com.CyHawkClash.Backend.Models.Team.Team;
import jakarta.persistence.*;

@Entity
public class TeamStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "obj_progress")
    private double obj_progress;

    @Column(name = "team_kills")
    private int team_kills;

    @Column(name = "team_deaths")
    private int team_deaths;

    @Column(name = "num_victories")
    private int num_victories;

    @Column(name = "num_defeats")
    private int num_defeats;

    @Column(name = "deleted")
    private boolean deleted;

    public TeamStatistics(Team team) {
        this.team = team;
        this.obj_progress = 0;
        this.team_kills = 0;
        this.team_deaths = 0;
        this.num_victories = 0;
        this.num_defeats = 0;
        this.deleted = false;
    }

    public TeamStatistics() {
    }

    public int getId(){
        return this.id;
    }
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public double getObjProgress() {
        return obj_progress;
    }

    public void setObjProgress(double obj_progress) {
        this.obj_progress = obj_progress;
    }

    public int getTeamKills() {
        return team_kills;
    }

    public void incTeamKills() {
        this.team_kills++;
    }

    public int getTeamDeaths() {
        return team_deaths;
    }

    public void incTeamDeaths() {
        this.team_deaths++;
    }

    public int getNumVictories() {
        return num_victories;
    }

    public void setNumVictories(int num_victories) {
        this.num_victories = num_victories;
    }

    public int getNumDefeats() {
        return num_defeats;
    }

    public void setNumDefeats(int num_defeats) {
        this.num_defeats = num_defeats;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete() {
        this.deleted = true;
    }
}
