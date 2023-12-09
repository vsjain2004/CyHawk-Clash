package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Draws the map
 */
public class Map {

    //width of each object in pixels
    int pixels=500;
    Paint cyclone = new Paint();
    Paint hawkeye = new Paint();
    Paint obstacleTop = new Paint();
    Paint obstacleFront = new Paint();

    Paint obstacleLeft = new Paint();
    Paint obstacleRight = new Paint();
    boolean captureTheFlag = false;
    boolean deathMatch = false;
    boolean teamDeathMatch = false;
    // array of chars to spawn the map
    // CHARACTER KEY:
    // 'w' = wall (Not passable)
    // 's' = grass? (passable)
    // 'r' = walkway (passable)
    // 'o' = obstacle(not passable)
    // 'c' = safezone for cyclones(hawkeye bullets can't enter)
    // 'h' = safezone for hawkeyes(cyclone bullets can't enter)
    //
    //

    /**
     * instantiates the obstacles based on the map
     */
    public Map(){
        cyclone.setARGB(255,100,0,0);
        hawkeye.setARGB(255,100,100,0);
        obstacleTop.setARGB(255,200,200,200);
        obstacleLeft.setARGB(255,100,100,100);
        obstacleFront.setARGB(255,140,140,140);
        obstacleRight.setARGB(255,170,170,170);
        for (int row=0;row<map.length;row++){
            for(int col=0;col<map.length;col++){
                char block = map[row][col];
                if(block=='o'){
                    //create a new obstacle that contains the collision values for each side of the obstacle(left,up,right,down)
                    Obstacle o = new Obstacle(row*pixels,col*pixels,(row+1)*pixels,(col+1)*pixels);
                    blocks.add(o);
                }
            }
        }
    }
    char[][] map =
            //15 chars wide and tall
            {       {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w'},
                    {'w','s','s','s','s','s','s','c','s','s','s','s','s','s','w'},
                    {'w','s','s','s','o','s','s','r','s','s','s','o','s','s','w'},
                    {'w','s','s','o','s','s','s','r','s','s','s','s','o','s','w'},
                    {'w','s','o','s','s','s','s','r','s','s','s','s','s','o','w'},
                    {'w','s','s','s','s','s','s','r','s','s','s','s','s','s','w'},
                    {'w','s','s','s','s','s','o','r','o','s','s','s','s','s','w'},
                    {'w','r','r','r','r','r','r','r','r','r','r','r','r','r','w'},
                    {'w','s','s','s','s','s','o','r','o','s','s','s','s','o','w'},
                    {'w','s','s','s','s','s','s','r','s','s','s','s','o','o','w'},
                    {'w','s','s','o','s','s','s','r','s','s','o','s','s','s','w'},
                    {'w','s','o','o','o','s','s','r','s','s','o','o','s','s','w'},
                    {'w','s','s','o','s','s','s','r','s','s','s','s','s','s','w'},
                    {'w','s','s','s','s','s','s','h','s','s','s','s','s','s','w'},
                    {'w','w','w','w','w','w','w','w','w','w','w','w','w','w','w'}};

    /**
     *
     * @return 2d char array for map 1(only map currently)
     */
    protected char[][] getMap1(){
        return map;
    }

    ArrayList<Obstacle> blocks = new ArrayList<>();
    char charaLeft;
    char charaDown;
    char charaUp;
    char charaRight;
    /**
     * draws the map based on a 2d array of characters
     * @param theMap 2d array of chars
     * @param canvas screen to be drawn to
     * @param pX player x
     * @param pY player y
     */
    public void drawMap(char[][] theMap,Canvas canvas,double pX, double pY){
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        Paint color = new Paint();

        for(int row=0;row<theMap.length;row++){
            for(int col=0;col<theMap.length;col++){
                char chara = theMap[row][col];
                if(row!=0) {
                    charaLeft = theMap[row - 1][col];
                }
                if(row !=theMap.length-1){
                    charaRight = theMap[row+1][col];
                }
                if(col!=0){
                    charaUp = theMap[row][col-1];
                }
                if(col!= theMap.length-1){
                    charaDown = theMap[row][col+1];
                }
                if(chara=='w'){//wall
                    color.setARGB(255,61,58,53);//grey
                    canvas.drawRect((row)*     (pixels) -(int)pX+(float)width/2,
                            (col)*     (pixels) -(int)pY+(float)height/2,
                            (row+1)* (pixels) -(int)pX+(float)width/2,
                            (col+1)* (pixels) -(int)pY+(float)height/2,color);
                }
                //cyclone home base
                else if(chara=='c'){
                    canvas.drawRect((row)*     (pixels) -(int)pX+(float)width/2,
                            (col)*     (pixels) -(int)pY+(float)height/2,
                            (row+1)* (pixels) -(int)pX+(float)width/2,
                            (col+1)* (pixels) -(int)pY+(float)height/2,cyclone);
                }
                //hawkeye home base
                else if(chara=='h'){
                    canvas.drawRect((row)*     (pixels) -(int)pX+(float)width/2,
                            (col)*     (pixels) -(int)pY+(float)height/2,
                            (row+1)* (pixels) -(int)pX+(float)width/2,
                            (col+1)* (pixels) -(int)pY+(float)height/2,hawkeye);
                }
                else if(chara=='o'){//obstacle
                    int left =row*pixels-(int)pX+width/2;
                    int right = (row+1)*pixels-(int)pX+width/2;
                    int top = col * pixels-(int)pY+height/2;
                    int bot = (col+1)*pixels-(int)pY+height/2;

                    int leftUpper = left+pixels/10;
                    int rightUpper = right-pixels/10;
                    int topUpper = top-pixels/10;
                    int botUpper = bot-pixels/2;
                    //draw main line
                    obstacleTop.setStyle(Paint.Style.FILL);
                    obstacleLeft.setStyle(Paint.Style.FILL);
                    obstacleFront.setStyle(Paint.Style.FILL);
                    obstacleRight.setStyle(Paint.Style.FILL);
                    Path frontFace = new Path();
                    frontFace.moveTo(right,bot);
                    frontFace.lineTo(rightUpper,topUpper);
                    frontFace.lineTo(leftUpper,topUpper);
                    frontFace.lineTo(left,bot);
                    frontFace.lineTo(right,bot);
                    frontFace.close();
                    Path rightFace = new Path();
                    rightFace.moveTo(rightUpper,topUpper);
                    rightFace.lineTo(right,top);
                    rightFace.lineTo(right,bot);
                    rightFace.lineTo(rightUpper,botUpper);
                    rightFace.lineTo(rightUpper,topUpper);
                    rightFace.close();
                    Path topFace = new Path();
                    topFace.moveTo(leftUpper,topUpper);
                    topFace.lineTo(rightUpper,topUpper);
                    topFace.lineTo(rightUpper,botUpper);
                    topFace.lineTo(leftUpper,botUpper);
                    topFace.lineTo(leftUpper,topUpper);
                    topFace.close();
                    Path leftFace = new Path();
                    leftFace.moveTo(leftUpper,topUpper);
                    leftFace.lineTo(left,top);
                    leftFace.lineTo(left,bot);
                    leftFace.lineTo(leftUpper,botUpper);
                    leftFace.lineTo(leftUpper,topUpper);
                    leftFace.close();
                    canvas.drawPath(frontFace,obstacleFront);
                    canvas.drawPath(topFace,obstacleTop);
                    canvas.drawPath(leftFace,obstacleLeft);
                    canvas.drawPath(rightFace,obstacleRight);
                }
                else if(chara=='s'){//grass

                    color.setARGB(255,55,122,57);//green
                    //DRAW BASE GREEN SQUARE
                    canvas.drawRect((row)*     (pixels) -(int)pX+(float)width/2,
                            (col)*     (pixels) -(int)pY+(float)height/2,
                            (row+1)* (pixels) -(int)pX+(float)width/2,
                            (col+1)* (pixels) -(int)pY+(float)height/2,color);
                    //set color to dark green
                    color.setARGB(255,40,90,72);
                    //if there is road below
                    if(charaDown=='r'){

                        //darken the bottom
                        canvas.drawRect((row)*     (pixels) -(int)pX+(float)width/2,
                                (col)*     (pixels) -(int)pY+pixels-pixels/7+(float)height/2,
                                (row+1)* (pixels) -(int)pX+(float)width/2,
                                (col+1)* (pixels) -(int)pY+(float)height/2,color);
                    }
                    //if there is road to the right
                    if(charaRight=='r'){
                        //darken the right
                        canvas.drawRect((row)*     (pixels) -(int)pX+pixels-pixels/10+(float)width/2,
                                (col)*     (pixels) -(int)pY+(float)height/2,
                                (row+1)* (pixels) -(int)pX+(float)width/2,
                                (col+1)* (pixels) -(int)pY+(float)height/2,color);
                    }
                    if(charaLeft=='r'){
                        //darken the left
                        canvas.drawRect((row)*     (pixels) -(int)pX+(float)width/2,
                                (col)*     (pixels) -(int)pY+(float)height/2,
                                (row+1)* (pixels) -(int)pX-pixels+pixels/10+(float)width/2,
                                (col+1)* (pixels) -(int)pY+(float)height/2,color);
                    }
                    if(charaUp=='r'){
                        //darken the top
                        canvas.drawRect((row)*     (pixels) -(int)pX+(float)width/2,
                                (col)*     (pixels) -(int)pY+(float)height/2,
                                (row+1)* (pixels) -(int)pX+(float)width/2,
                                (col+1)* (pixels) -(int)pY-pixels+pixels/20+(float)height/2,color);
                    }
                }
                else if(chara=='r'){//road
                    color.setARGB(255,200,200,200);//white
                    canvas.drawRect((row)*     (pixels) -(int)pX+(float)width/2,
                            (col)*     (pixels) -(int)pY+(float)height/2,
                            (row+1)* (pixels) -(int)pX+(float)width/2,
                            (col+1)* (pixels) -(int)pY+(float)height/2,color);
                }
                color.setARGB(50,255,255,255); //black

            }
        }

    }

    /**
     * draws detail in front of the player, variables same as draw
     * @param theMap
     * @param canvas
     * @param pX
     * @param pY
     */
    public void drawFront(char[][] theMap,Canvas canvas,double pX, double pY){

    }

    public ArrayList<Obstacle> getObstacles(){
        return blocks;
    }
}