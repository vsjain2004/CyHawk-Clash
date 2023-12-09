package com.example.myapplication;

import java.util.ArrayList;

public class PlayerInfo {
    int id;
    int hp;
    float x;
    float y;

    /**
     * creates a new object with the player info from the get method
     * @param id player id
     * @param hp current player hp
     * @param x current player x location
     * @param y current player y location
     */
    public PlayerInfo(int id, int hp, float x, float y){
        this.id=id;
        this.hp=hp;
        this.x=x;
        this.y=y;
    }
}
