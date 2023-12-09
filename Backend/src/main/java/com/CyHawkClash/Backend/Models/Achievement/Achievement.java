package com.CyHawkClash.Backend.Models.Achievement;

import jakarta.persistence.*;

@Entity
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "requirement")
    private String requirement;

    @Column(name = "reward")
    private String reward;

    @Column(name = "function")
    private String function;

    public Achievement(String requirement, String reward, String function) {
        this.requirement = requirement;
        this.reward = reward;
        this.function = function;
    }

    public Achievement(){}

    public int getId(){
        return this.id;
    }
    public String getRequirement() {
        return requirement;
    }

    public String getReward() {
        return reward;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}
