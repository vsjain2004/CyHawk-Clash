package com.example.myapplication;

import static android.os.SystemClock.sleep;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import static com.example.myapplication.SignUp.theusername;
import java.util.ArrayList;



/**
 * this class have all the infomation for player in agme
 */
public class PlayerInGame {

    Boolean isDead = false;
    public int seconds;
    double radius=50;
    //list of bullets
    int playerID;
    public float currentHealth = 100;
    float movementDirection=0;
    // default is 99, but will be updated with either
    // 1(red team) or 2(blue team) based on the player's team
    boolean playerTeam=false;
    float movementSpeed=1;
    double width;
    double height;


    //x and y coordinates for the player
    double playerX=550+Math.random()*6950;
    double playerY=550+Math.random()*6950;
    // updates the player's coordinates based on:
    // the current location, direction & speed of the player.
    private void setLocation(int curX, int curY, float moveDir, float moveSpd){
        playerX=curX;
        playerY=curY;
        movementDirection=moveDir;
        movementSpeed=moveSpd;
    }

    private float getMovementDirection(){
        return movementDirection;
    }
    private float getMovementSpeed(){
        return movementSpeed;
    }

    ArrayList<Obstacle> obstacles;
    public PlayerInGame(ArrayList<Obstacle> list){
        obstacles=list;
    }
    private Paint black=new Paint();

    /**
     * draws the player(circle) to the center of the canvas
     * @param canvas : screen to be drawn to
     */
    protected void drawPlayer(Canvas canvas){
        Paint color = new Paint();
        color.setARGB(150,75,75,75);
        canvas.drawOval((float)canvas.getWidth()/2-100,(float)canvas.getHeight()/2+30,(float)
                canvas.getWidth()/2+20,(float)canvas.getHeight()/2+70,color);
        color.setARGB(255,150,150,0);
        canvas.drawCircle((float)canvas.getWidth()/2,(float)canvas.getHeight()/2,(float)radius,color);
        black.setARGB(255,255,255,255);
        black.setTextSize(50);


    }

    boolean teamset = false;
    double prevX;
    double prevY;
    /**
     * updates player location based on left joystick angle and intensity
     * @param stick Joystick used for movement
     */
    public void update(LeftStick stick,ArrayList<Boolean> teams, ArrayList<String> names){
        Log.d("dead", isDead+"");
        if(names.size()==2&& !hasUpdated) {
            if (teams.size() == names.size()) {
                for (int i = 0; i < names.size();i++) {
                    if(names.get(i).equals(theusername)){
                        playerTeam=teams.get(i);
                        hasUpdated=true;
                    }
                }
            }
        }

        if(teams.size()==names.size()) {
            if(!teamset) {
                for (int i = 0; i < teams.size(); i++) {
                    if (names.get(i).equals(theusername)) {
                        playerTeam = teams.get(i);
                    }
                }
                teamset=true;
            }
        }
        if(!isDead) {
            height = Math.sin(stick.angle) * stick.getIntensity() * 15;
            width = Math.cos(stick.angle) * stick.getIntensity() * 15;
            //stick is in upper right quadrant
            //top right quadrant
            if (stick.getCurrentX() > stick.getLeftBaseX() && stick.getCurrentY() < stick.getBaseY()) {
                playerX = playerX + width;
                playerY = playerY - height;
            }
            //top left quadrant
            else if (stick.getCurrentX() < stick.getLeftBaseX() && stick.getCurrentY() < stick.getBaseY()) {
                playerX = playerX + width;
                playerY = playerY - height;
            }
            //bottom left quadrant
            else if (stick.getCurrentX() < stick.getLeftBaseX() && stick.getCurrentY() > stick.getBaseY()) {
                playerX = playerX + width;
                playerY = playerY - height;
            }
            //bottom right quadrant
            else if (stick.getCurrentX() > stick.getLeftBaseX() && stick.getCurrentY() > stick.getBaseY()) {
                playerX = playerX + width;
                playerY = playerY - height;
            }

            double thisAngle = stick.getMovementDirection();
            if (playerY - 50 < 500 && thisAngle < Math.PI) {
                playerY = 550;
            } else if (playerY + 50 > 7000 && thisAngle > Math.PI) {
                playerY = 6950;
            }
            if (playerX - 50 < 500 && (thisAngle > Math.PI / 2 && thisAngle < Math.PI + Math.PI / 2)) {
                playerX = 550;
            } else if (playerX + 50 > 7000 && (thisAngle > Math.PI + Math.PI / 2 && thisAngle < 2 * Math.PI || thisAngle < Math.PI / 2 && thisAngle > 0)) {
                playerX = 6950;
            }
            //locks the location based on obstacles
            for (int i = 0; i < obstacles.size(); i++) {
                int obUp = obstacles.get(i).getTop();
                int obDown = obstacles.get(i).getDown();

                int obLeft = obstacles.get(i).getLeft();
                int obRight = obstacles.get(i).getRight();
                //if the player is between the left and right sides
                if (playerX >= obLeft && playerX <= obRight) {
                    //if the player is directly below the obstacle and is moving upwards
                    if (playerY - 50 < obDown && playerY - 50 > obUp && thisAngle < Math.PI) {
                        playerY = obDown + 50;
                    }
                    //if the player is directly above the obstacle and is moving downwards
                    else if (playerY + 50 > obUp && playerY + 50 < obDown && thisAngle > Math.PI) {
                        playerY = obUp - 50;
                    }

                }
                //if the player is between the top and bottom sides
                else if (playerY <= obDown && playerY >= obUp) {
                    //if the player is directly to the right of the obstacle and moving left
                    if (playerX - 20 <= obRight && playerX - 20 > obLeft && thisAngle > Math.PI / 2 && thisAngle < Math.PI * 3 / 2) {
                        playerX = obRight + 20;
                    }
                    //else if the player is directly to the left of the obstacle and moving right
                    else if (playerX + 20 >= obLeft && playerX + 20 < obRight && (thisAngle < Math.PI / 2 || thisAngle > Math.PI * 3 / 2)) {
                        playerX = obLeft - 20;
                    }
                }
            }


            if (Double.isNaN(playerX) || Double.isNaN(playerY)) {
                playerX = prevX;
                playerY = prevY;

            } else {
                prevX = playerX;
                prevY = playerY;
            }
        }
    }


    /**
     * returns current coordinates of player
     * @return double array[x,y] corresponding to [(width), (height)]
     */
    public double[] getLocation(){
        return new double[]{playerX, playerY};
    }

    /**
     * reutnrs current health of player
     * @return health
     */
    public float getHealth(){
        return currentHealth;
    }



    /**
     * sets player ID
     * @param id unique player ID from database
     */
    public void setPlayerID(int id){
        playerID=id;
    }
    /**
     * returns player ID
     * @return unique player ID
     */
    public int getPlayerID(){
        return playerID;
    }
    /**
     *
     * @return returns player X(width)
     */

    public double getPlayerX(){
        return playerX;
    }
    /**
     *
     * @return player Y(height)
     */
    public double getPlayerY(){
        return playerY;
    }

    /**
     *
     * @return radius of drawing player to screen
     */
    public double getPlayerRadius(){
        return radius;
    }
    public void setCurrentHealth(float health){

        currentHealth=health;
        if(currentHealth>0){
            isDead=false;
        }
        // otherwise, the player must have died.
        else{
            isDead=true;
        }
    }

    boolean hasUpdated=false;
    public void setPlayerX(double x){
        playerX=x;
    }
    public void setPlayerY(double y){
        playerY=y;
    }
}
