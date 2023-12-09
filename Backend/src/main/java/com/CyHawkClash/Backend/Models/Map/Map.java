package com.CyHawkClash.Backend.Models.Map;

import com.CyHawkClash.Backend.Models.Objective.Objective;
import jakarta.persistence.*;

@Entity
@Table(name="Map")
public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="map_name")
    private String map_name;

    @Column(name="map")
    private String map;

    @Column(name="map_width")
    private int map_width;

    @ManyToOne
    @JoinColumn(name = "objective_id")
    private Objective obj;


    public Map(String map_name, String map, int map_width, Objective obj){
        this.map_name = map_name;
        this.map = map;
        this.map_width = map_width;
        this.obj = obj;
    }

    public Map(){

    }

    public Integer getId(){
        return this.id;
    }

    public String getMapName(){
        return this.map_name;
    }

    public String getMap(){
        return this.map;
    }

    public int getMapWidth(){
        return this.map_width;
    }

    public Objective getObj(){
        return this.obj;
    }

}
