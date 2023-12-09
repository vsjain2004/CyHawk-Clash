package com.CyHawkClash.Backend.Models.Objective;

import jakarta.persistence.*;

@Entity
@Table(name = "Objective")
public class Objective {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String obj;

    private String function;

    public Objective(String obj, String function){
        this.obj = obj;
        this.function = function;
    }
    public Objective(){}

    public int getId(){
        return this.id;
    }
    public String getObj(){
        return this.obj;
    }

    public String getFunction(){
        return this.function;
    }
}

