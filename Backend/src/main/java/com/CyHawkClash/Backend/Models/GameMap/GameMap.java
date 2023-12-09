package com.CyHawkClash.Backend.Models.GameMap;

import com.CyHawkClash.Backend.Models.Game.Game;
import com.CyHawkClash.Backend.Models.Map.Map;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class GameMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "map_id")
    private Map map;

    @Column(name = "mapStr")
    private String mapStr;

    public GameMap(Game game, Map map, String mapStr) {
        this.game = game;
        this.map = map;
        this.mapStr = mapStr;
    }

    public GameMap() {}

    public int getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }

    public Map getMap() {
        return map;
    }

    public String getMapStr() {
        return mapStr;
    }

    public void setMapStr(String mapStr) {
        this.mapStr = mapStr;
    }

}
