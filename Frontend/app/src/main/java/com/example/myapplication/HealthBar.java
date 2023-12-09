package com.example.myapplication;



import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import static com.example.myapplication.SignUp.theusername;
import java.util.ArrayList;

public class HealthBar {


    float maxHP=100;
    float curHealth=100;
    runGame rungame;
    PlayerInGame player;
    public HealthBar(PlayerInGame p, runGame R){
        player=p;
        rungame=R;
    }
    /**
     * draws health bar based on:
     * @param canvas phone screen
     * @param width width of canvas
     * @param height height of canvas
     */
    protected void drawHealthBar(Canvas canvas,double width, double height){


        Paint color = new Paint();

        color.setARGB(255,0,0,0);//black
        canvas.drawRect((float)width/4,0,(float)width-((float)width/4),(float)height/16,color);
        color.setARGB(255,150,0,0);//red
        canvas.drawRect((float)width/4, 0, (float)width/4 + (float)width/2*curHealth/maxHP, (float)height/16, color
        );
    }
    float prevHp;
    boolean hasCheckedTick=false;
    public void update(ArrayList<String> names,ArrayList<Boolean> teams,ArrayList<Float> hp){
        //names and hp are assumed to be the same size
        //for each player name, if the player name matches the user's player name, update their health
        Log.d("HP",""+hp.size());
        Log.d("Names",""+names.size());
        if(hp.size()==names.size()) {
            for (int i = 0; i < names.size(); i++) {
                Log.d("hithere",  hp.toString());
                if (names.get(i).equals(theusername)) {
                    curHealth = hp.get(i);
                    player.setCurrentHealth(hp.get(i));
                    //if the current HP is not 0 and the prev HP is 0, we must have respawned
                    if(curHealth>0&&prevHp==0){
                        if(teams.get(i)){
                            player.setPlayerX(750);
                            player.setPlayerY(3750);

                        }
                        else{
                            player.setPlayerX(6750);
                            player.setPlayerY(3750);
                        }
                    }
                }

                prevHp=curHealth;
            }
        }

    }

}