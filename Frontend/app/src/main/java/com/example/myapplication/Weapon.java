package com.example.myapplication;

import static com.example.myapplication.PreGameLobby.WeaponChoose;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class creates the weapons
 */
public class Weapon {
    runGame game;
    String url;
    int magazineCapacity=30;
    int currentBullets=30;
    double startX;
    double startY;

    Paint p = new Paint();

    //larger = more time between shots, smaller = less time between shots
    int fireRate;
    float bulletSpeed;

    //multiplier for bullet spread; 0 is no spread
    double spread;
    ArrayList<Obstacle> obstacles;

   String WeaponType = WeaponChoose;

    public Weapon(PlayerInGame player, ArrayList<Obstacle> o, runGame g){
        url=g.getUrl();
        game=g;
        obstacles=o;
        startX=player.getPlayerX();
        startY=player.getPlayerY();
        p.setARGB(255,255,255,255);
        if(WeaponType!=null) {
            if (WeaponType.equals("Shotgun")) {
                bulletSpeed = 30;
                fireRate = 50;
                spread = 0.5;
            } else if (WeaponType.equals("Rifle")) {
                bulletSpeed = 40;
                fireRate = 10;
                spread = 0.1;
            } else if (WeaponType.equals("Sniper")) {
                bulletSpeed = 70;
                fireRate = 100;
                spread = 0;
            } else if (WeaponType.equals("SMG")) {
                bulletSpeed = 50;
                fireRate = 5;
                spread = 0.2;
            } else if (WeaponType.equals("Minigun")) {
                bulletSpeed = 40;
                fireRate = 2;
                spread = 0.1;
            }
        }
    }

    private ArrayList<Bullet>  bullets= new ArrayList<>();

    int shootTimes=0;


    /**
     * shoots the gun based on the right stick r's direction and the player location
     * @param canvas
     * @param r
     * @param player
     */
    public void shoot(Canvas canvas,RightStick r,PlayerInGame player){
        currentBullets--;
        if(currentBullets==0){
            //reload
            currentBullets=magazineCapacity;
        }
        else{
            //this gives us a value random between -1 and 1
            if(WeaponType.equals("Shotgun")){

                for(int i=0;i<5;i++){
                    double random = Math.random();
                    double sign = Math.random();
                    if(sign<0.5){
                        game.postBullet((float)player.getPlayerX(),(float)player.getPlayerY(),(float)(r.getFiringDirection()+random*spread));
//                        bullets.add(new Bullet(bulletSpeed,r.getFiringDirection()+random*spread,player));
                    }
                    else{
                        game.postBullet((float)player.getPlayerX(),(float)player.getPlayerY(),(float)(r.getFiringDirection()-random*spread));
//                        bullets.add(new Bullet(bulletSpeed,r.getFiringDirection()-random*spread,player));
                    }
                    shootTimes++;
                }
            }
            else if(WeaponType.equals("Sniper")){
                game.postBullet((float)player.getPlayerX(),(float)player.getPlayerY(),(float)(r.getFiringDirection()+1*spread));
//                    bullets.add(new Bullet(bulletSpeed,r.getFiringDirection()+1*spread,player));
            }
            else{
                double random = Math.random();
                double sign = Math.random();
                if(sign<0.5){
                    game.postBullet((float)player.getPlayerX(),(float)player.getPlayerY(),(float)(r.getFiringDirection()+random*spread));
//                    bullets.add(new Bullet(bulletSpeed,r.getFiringDirection()+random*spread,player));
                }
                else{
                    game.postBullet((float)player.getPlayerX(),(float)player.getPlayerY(),(float)(r.getFiringDirection()-random*spread));
//                    bullets.add(new Bullet(bulletSpeed,r.getFiringDirection()-random*spread,player));
                }
                shootTimes++;
            }
//            shootTimes++;
//            Bullet b = new Bullet(startX,startY,r,r.getFiringDirection()+random,player);
//            bullets.add(b);
            canvas.drawText(Integer.toString(shootTimes), (float) canvas.getWidth() / 2, (float) canvas.getHeight() / 2 + 200, p);
        }
    }


    /**
     * updates locations of bullets and redraws them.
     * @param canvas
     */
    protected void update(Canvas canvas,PlayerInGame player){
        for (int i=0;i<bullets.size();i++){
            bullets.get(i).update();
            bullets.get(i).draw(canvas, player);
            double x = bullets.get(i).getCurX();
            double y = bullets.get(i).getCurY();
            if(x<500||x>7000||y<500||y>7000){
                bullets.remove(i);
                i--;
            }
            //for each bullet, if the bullet is within any of the obstacles, delete it
            for(int j=0;j<obstacles.size();j++){
                Obstacle ob = obstacles.get(j);
                if(x> ob.getLeft()&&x<ob.getRight()&&y<ob.getDown()&&y>ob.getTop()){
                    bullets.remove(i);
                    i--;
                }
            }

        }
    }
}