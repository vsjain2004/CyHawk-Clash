package com.example.myapplication;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.myapplication.Createlobby.theGame_id;
import static com.example.myapplication.runGame.*;
import static com.example.myapplication.Createlobby.Game_id;
import static com.example.myapplication.SignUp.theusername;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;


import java.util.ArrayList;
import java.util.Objects;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private final runGame rungame;
    Map map = new Map();

    LeftStick movement;
    RightStick shooting;

    InGameChat chat;
    PlayerInGame player = new PlayerInGame(map.getObstacles());
    HealthBar hp;
    Paint paint = new Paint();
    Paint cyclone = new Paint();
    Paint hawkeye = new Paint();
    int moveIndex = 0;
    int shootIndex = 1;
    int canvasWidth = 0;


    ArrayList<Boolean> playerTeams = new ArrayList<>();
    ArrayList<String> playerNames = new ArrayList<>();
    ArrayList<Float> playerX = new ArrayList<>();
    ArrayList<Float> playerY = new ArrayList<>();

    //creates a new sendMessage with the username and id
    SendMessage send = new SendMessage(theusername,theGame_id);
    ArrayList<Float> playerHP = new ArrayList<>();
    ArrayList<Float> bulletX = new ArrayList<>();
    //ArrayList of Bullet Y values
    ArrayList<Float> bulletY = new ArrayList<>();
    /**
     * game constructor - instantiates all objects for game logic to begin
     *
     * @param context
     */
    public Game(Context context) {

        super(context);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        Canvas canvas = holder.lockCanvas();
        rungame = new runGame(this, holder);
        hp = new HealthBar(player,rungame);
        movement = new LeftStick(canvas);
        Weapon weapon = new Weapon(player, map.getObstacles(),rungame);
        shooting = new RightStick(canvas, weapon);
        chat = new InGameChat();
        //red
        cyclone.setARGB(255,255,0,0);
        //yellow
        hawkeye.setARGB(255,255,255,0);
        setFocusable(true);
        paint.setARGB(255, 255, 255, 255);
    }
    //JOYSTICK METHOD

    int pointerCount;
    boolean once;

    /**
     * takes input and updates the location of the player based on the touch location
     *
     * @param motionEvent touch of user
     * @return true if no errors occur
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (rungame.isRunning()) {
            pointerCount = motionEvent.getPointerCount();

            float curX = motionEvent.getX();
            float curY = motionEvent.getY();
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    //if the chat is open
                    if (chat.getIsPressed()) {
                        //if the touch is outside of the chat window
                        if ((motionEvent.getX() < chat.getKeyIncrement() ||
                                motionEvent.getX() > rungame.canvas.getWidth() - chat.getKeyIncrement() ||
                                motionEvent.getY() < chat.getKeyIncrement() ||
                                motionEvent.getY() > rungame.canvas.getHeight() - chat.getKeyIncrement())) {
                            //close the chat box and start updating position again

                            //if touch is on button
                            if (motionEvent.getX() > chat.getButtonLeft() &&
                                    motionEvent.getX() < chat.getButtonRight() &&
                                    motionEvent.getY() > chat.getButtonTop() &&
                                    motionEvent.getY() < chat.getButtonBottom()) {
                                chat.toggle();
                                movement.setPosition(movement.getLeftBaseX(), movement.getBaseY());
                            }
                        }
                        //if the touch is inside the chat window and chat is open
                        //GIANT METHOD FOR TYPING IN GAME
                        else {
                            float[] keys = chat.getKeyLefts();
                            //touch is top row
                            if (curY >= chat.getKeyLineTop() - chat.keyWidth && curY <= chat.getKeyLineTop()) {
                                //Q key
                                if (curX >= keys[0] && curX <= (keys[0] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("q"));
                                }
                                //W key
                                else if (curX >= keys[1] && curX <= (keys[1] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("w"));
                                }
                                //E key
                                else if (curX >= keys[2] && curX <= (keys[2] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("e"));
                                }
                                //R key
                                else if (curX >= keys[3] && curX <= (keys[3] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("r"));
                                }
                                //T key
                                else if (curX >= keys[4] && curX <= (keys[4] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("t"));
                                }
                                //Y key
                                else if (curX >= keys[5] && curX <= (keys[5] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("y"));
                                }
                                //U key
                                else if (curX >= keys[6] && curX <= (keys[6] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("u"));
                                }
                                //I key
                                else if (curX >= keys[7] && curX <= (keys[7] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("i"));
                                }
                                //O key
                                else if (curX >= keys[8] && curX <= (keys[8] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("o"));
                                }
                                //P key
                                else if (curX >= keys[9] && curX <= (keys[9] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("p"));
                                }
                            }
                            // touch is middle row
                            else if (curY >= chat.getKeyLineMiddle() - chat.keyWidth && curY <= chat.getKeyLineMiddle()) {
                                //A key
                                if (curX >= keys[10] && curX <= (keys[10] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("a"));
                                }
                                //S key
                                else if (curX >= keys[11] && curX <= (keys[11] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("s"));
                                }
                                //D key
                                else if (curX >= keys[12] && curX <= (keys[12] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("d"));
                                }
                                //F key
                                else if (curX >= keys[13] && curX <= (keys[13] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("f"));
                                }
                                //G key
                                else if (curX >= keys[14] && curX <= (keys[14] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("g"));
                                }
                                //H key
                                else if (curX >= keys[15] && curX <= (keys[15] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("h"));
                                }
                                //J key
                                else if (curX >= keys[16] && curX <= (keys[16] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("j"));
                                }
                                //K key
                                else if (curX >= keys[17] && curX <= (keys[17] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("k"));
                                }
                                //L key
                                else if (curX >= keys[18] && curX <= (keys[18] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("l"));
                                }
                            }
                            // touch is bottom row
                            else if (curY >= chat.getKeyLineBottom() - chat.keyWidth && curY <= chat.getKeyLineBottom()) {

                                //Z key
                                if (curX >= keys[19] && curX <= (keys[19] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("z"));
                                }
                                //X key
                                else if (curX >= keys[20] && curX <= (keys[20] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("x"));
                                }
                                //C key
                                else if (curX >= keys[21] && curX <= (keys[21] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("c"));
                                }
                                //V key
                                else if (curX >= keys[22] && curX <= (keys[22] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("v"));
                                }
                                //B key
                                else if (curX >= keys[23] && curX <= (keys[23] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("b"));
                                }
                                //N key
                                else if (curX >= keys[24] && curX <= (keys[24] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("n"));
                                }
                                //M key
                                else if (curX >= keys[25] && curX <= (keys[25] + chat.keyWidth)) {
                                    chat.setMessage(chat.getMessage().concat("m"));
                                } else if (curX >= keys[26] && curX <= (keys[26] + chat.keyWidth)) {
                                    //subtracts the last character from the message
                                    String sub = chat.getMessage();
                                    if (!Objects.equals(sub, "")) {
                                        chat.setMessage(sub.substring(0, chat.getMessage().length() - 1));
                                    }
                                }
                                // touch is on the send button
                                else if (curX >= chat.getSendLeft() && curX <= chat.getSendLeft() + chat.keyWidth) {
                                    send.sendMessage(chat.getMessage());
                                    chat.setMessage("");
                                }
                            }
                        }
                    }
                    //if chat is not open
                    else {
                        //if touch is on button
                        if (motionEvent.getX() <= chat.getKeyIncrement() &&
                                motionEvent.getY() <= chat.getKeyIncrement()) {
                            chat.toggle();
                            movement.setPosition(movement.getLeftBaseX(), movement.getBaseY());
                        }
                        //if touch is not on button(normal movement)
                        else if ((motionEvent.getX() > chat.getKeyIncrement() &&
                                motionEvent.getY() > chat.getKeyIncrement()) ||
                                (motionEvent.getX() < chat.getKeyIncrement() && motionEvent.getY() > chat.getKeyIncrement()) ||
                                motionEvent.getY() < chat.getKeyIncrement() && motionEvent.getX() > chat.getKeyIncrement()) {
                            //if down touch is left side of screen
                            if (motionEvent.getX() < canvasWidth / 2.0) {
                                movement.setPosition(motionEvent.getX(), motionEvent.getY());
                                moveIndex = 0;
                                shootIndex = 1;
                            }
                            //if right side of screen
                            else if (motionEvent.getX() >= canvasWidth / 2.0) {
                                shooting.setPosition(motionEvent.getX(), motionEvent.getY());
                                shootIndex = 0;
                                moveIndex = 1;
                            }
                        }
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    //if the chat is not open
                    if (!chat.getIsPressed()) {
                        //if there is 1 finger on the screen
                        if (motionEvent.getPointerCount() == 1) {
                            if (moveIndex == 0) {
                                movement.setPosition(motionEvent.getX(), motionEvent.getY());
                            } else if (shootIndex == 0) {
                                shooting.setPosition(motionEvent.getX(), motionEvent.getY());
                            }
                        } else if (motionEvent.getPointerCount() == 2) {
                            movement.setPosition(motionEvent.getX(moveIndex), motionEvent.getY(moveIndex));
                            shooting.setPosition(motionEvent.getX(shootIndex), motionEvent.getY(shootIndex));
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP: //upon letting finger up
                    if (motionEvent.getPointerCount() > 1) {
                        if (shootIndex == 1) {
                            shooting.setPosition(shooting.getRightBaseX(), movement.getBaseY());
                            shootIndex = 5;
                        } else if (moveIndex == 1) {
                            movement.setPosition(movement.getLeftBaseX(), movement.getBaseY());
                            moveIndex = 5;
                        }
                    } else if (motionEvent.getPointerCount() == 1) {
                        movement.setPosition(movement.getLeftBaseX(), movement.getBaseY());
                        shooting.setPosition(shooting.getRightBaseX(), movement.getBaseY());
                        shootIndex = 5;
                        moveIndex = 5;

                    }
                    break;
            }
        }
        return true;

    }

    ;

    /**
     * @return returns current game that is running
     */
    public runGame getrungame() {
        return rungame;
    }

    public void setCanvasWidth(int w) {
        canvasWidth = w;
    }

    /**
     * starts the game and draws methods
     *
     * @param surfaceHolder device screen where game is drawn
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        rungame.startGame();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }



    /**
     * draws world based on locations of objects within it by calling each draw method
     *
     * @param canvas screen to be drawn to
     */
    public void draw(Canvas canvas) {
        super.draw(canvas);
        map.drawMap(map.getMap1(), canvas, player.getPlayerX(), player.getPlayerY());
        movement.draw(canvas);
        shooting.draw(canvas);
        hp.drawHealthBar(canvas, getWidth(), getHeight());
        player.drawPlayer(canvas);
        if(playerY!=null&&playerX!=null) {
            if (playerY.size() != 0 && playerX.size() != 0) {


                //draw players
                for(int k=0;k<playerTeams.size();k++){
                    //cyclone
                    if(!playerNames.get(k).equals(theusername)) {
                        if (playerHP.get(k) != 0) {
                            if (playerTeams.get(k)) {
                                canvas.drawCircle(playerX.get(k) - (float) player.getPlayerX() + getWidth() / 2,
                                        playerY.get(k) - (float) player.getPlayerY() + getHeight() / 2, 50, cyclone);
                            } else {
                                canvas.drawCircle(playerX.get(k) - (float) player.getPlayerX() + getWidth() / 2,
                                        playerY.get(k) - (float) player.getPlayerY() + getHeight() / 2, 50, hawkeye);
                            }
                        }
                    }
                }
                //draw player names
                for(int j=0;j<playerNames.size();j++){
                    if(!playerNames.get(j).equals(theusername)) {
                        canvas.drawText(playerNames.get(j), (float) ((playerX.get(j) - (float) player.getPlayerX() + getWidth() / 2) - 50),
                                (float) (playerY.get(j) - (float) player.getPlayerY() + getHeight() / 2) - 70, paint);
                    }
                }
            }
            else{
                canvas.drawText("Sizes are 0",500,1100,paint);
            }
        }

        //drawing bullet methods
        if(bulletY!=null&&bulletX!=null){
            if(bulletY.size()!=0&&bulletX.size()!=0){
                for(int j=0;j<bulletX.size();j++) {
                    canvas.drawCircle(bulletX.get(j) - (float) player.getPlayerX() + getWidth() / 2, bulletY.get(j) - (float) player.getPlayerY() + getHeight() / 2, 25, paint);
                }
            }
        }
        chat.draw(canvas);
        paint.setTextSize(50);
    }

    /**
     * updates movement joystick and player location based on current touch
     */
    public void update(Canvas canvas) {

        if(player.getHealth()>0) {
            movement.update();
            shooting.update(canvas, player);
            shooting.getStickWeapon().update(canvas, player);
            player.update(movement,playerTeams,playerNames);
        }
        //updates the chat to be drawn with the current chat from the game
        chat.setMessages(send.returnMessage());
        hp.update(playerNames,playerTeams,playerHP);
    }

    public PlayerInGame getPlayer() {
        return player;
    }

    boolean isSet=false;
    public void setBullets(ArrayList<Float> x, ArrayList<Float> y){

        bulletX=x;
        bulletY=y;
    }
    public void setAllPlayerXandYs(ArrayList<Float> x, ArrayList<Float> y) {
        playerX = x;
        playerY = y;
    }
    public void setTeamsAndNamesAndHP(ArrayList<Boolean> teams, ArrayList<String> names,ArrayList<Float> hp){
        if(!isSet) {
            playerTeams = teams;
            playerNames = names;
            isSet=true;
        }
        playerHP = hp;
    }
    public ArrayList<Float> getPlayerX(){
        return playerX;
    }
    public ArrayList<Float> getPlayerY(){
        return playerY;
    }



}