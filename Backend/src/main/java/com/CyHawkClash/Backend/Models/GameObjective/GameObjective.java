package com.CyHawkClash.Backend.Models.GameObjective;

import com.CyHawkClash.Backend.Models.Game.Game;
import com.CyHawkClash.Backend.Models.Objective.Objective;
import jakarta.persistence.*;

@Entity
public class GameObjective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "objective_id")
    private Objective obj;


    public GameObjective(Game game, Objective obj) {
        this.game = game;
        this.obj = obj;
    }

    public GameObjective() {}

    public int getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public Objective getObj() {
        return obj;
    }
}
