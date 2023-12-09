package com.example.myapplication;
/**
 * the graphics class
 */
import android.graphics.*;

public class Bullet {


    private double angle;

    private double bulletRadius=20;
    private double curX;
    private double curY;

    private float speed;


    private int team;

    double width;
    double height;

    double startX=0;
    double startY=0;
    Paint p = new Paint();

    Paint darken = new Paint();
    /**
     * creates a new bullet object with params
     * @param speed speed the bullet travels at(multiplier for direction)
     * @param angle direction the bullet travels at(in radians)
     * @param player information of the player who fired it
     */
    public Bullet(float speed,double angle,PlayerInGame player){
        this.angle=angle;
        this.speed=speed;
        //location on bullet creation
        startX=player.getPlayerX();
        startY=player.getPlayerY();
        curX=startX;
        curY=startY;
        height = Math.sin(angle)*speed;
        width= Math.cos(angle)*speed;
        p.setARGB(255,255,255,255);
        darken.setARGB(150,75,75,75);
    }

    /**
     * updates location of bullets based on width and height from joystick
     */
    protected void update(){

        curX+=width;
        curY-=height;

    }

    /**
     * draw the bullet to the screen
     * @param canvas
     * @param player
     */
    protected void draw(Canvas canvas,PlayerInGame player){
        //to accurately draw the bullet, we take the player's current location(X and Y), subtract the bullet's current location(X and Y),
        //and add those values to the player's location
        double playerCurrentX = player.getPlayerX();
        double playerCurrentY = player.getPlayerY();
        double cx = curX-playerCurrentX;
        double cy = curY-playerCurrentY;
        float drawX = (float)cx+(float)canvas.getWidth()/2;
        float drawY = (float)cy+(float)canvas.getHeight()/2;
        canvas.drawCircle(drawX,drawY,20,p);
        int widthOffset = canvas.getWidth()/10;
        int heightOffset = canvas.getHeight()/10;
//        canvas.drawOval(drawX-widthOffset-40,drawY+heightOffset-20,
//                drawX-widthOffset+40,drawY+heightOffset+20,darken);

    }

    /**
     *
     * @return angle of joystick(radians)
     */
    public double getAngle() {
        return angle;
    }

    /**
     *
     * @return speed of bullet
     */
    public float getSpeed() {
        return speed;
    }

    /**
     *
     * @return current X location(width)
     */
    public double getCurX() {
        return curX;
    }

    /**
     *
     * @param curX x to be updated
     */

    public void setCurX(double curX) {
        this.curX = curX;
    }

    /**
     * radius of bullet for collision and bullet creation
     * @return
     */
    public double getBulletRadius(){
        return bulletRadius;
    }

    /**
     * current Y of bullet
     * @return
     */
    public double getCurY() {
        return curY;
    }

    /**
     *
     * @param curY y to be updated
     */
    public void setCurY(double curY) {
        this.curY = curY;
    }
}