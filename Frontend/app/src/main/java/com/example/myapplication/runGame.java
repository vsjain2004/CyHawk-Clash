package com.example.myapplication;



import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.EditText;


import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import static com.example.myapplication.Login.*;

import static com.example.myapplication.PreGameLobby.sendmsg;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * the create a game and run run the joysticks and the player and the bullets.
 */
public class runGame  extends Thread implements SurfaceHolder.Callback {

    public static int second =50; // time of the game
    private SurfaceHolder holder;
    private boolean isRunning = false;
    private Game game;
    int canvasWidth;
    /**
     * null canvas to be overwritten
     */
    protected Canvas canvas;

    /**
     * constructor for running game
     * @param game Game to be ran
     * @param holder screen
     */
    public runGame(Game game, SurfaceHolder holder) {
        this.game = game;
        this.holder = holder;

    }

    /**
     * sets the boolean isRunning to true and starts the thread
     */
    public void startGame() {
        isRunning = true;
        start();
    }
    String url = "http://coms-309-041.class.las.iastate.edu:8080";
    String getResponse="";
    String putResponse="";
    JsonArrayRequest jsonRequest;



    ArrayList<String> playerNames = new ArrayList<>();
    ArrayList<Boolean> playerTeams = new ArrayList<>();
    boolean set = false;
    //Arraylist of Player X values
    ArrayList<Float> playerX;
    //ArrayList of Player Y values
    ArrayList<Float> playerY;
    //ArrayList of Bullet X values
    ArrayList<Float> bulletX;
    //ArrayList of Bullet Y values
    ArrayList<Float> bulletY;

    ArrayList<Float> playerHealth = new ArrayList<>();

    private void getBullets() {

        //reinitializes the arraylists

        // Request a string response from the provided URL.
        jsonRequest = new JsonArrayRequest(Request.Method.GET, url+"/bullets",null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        bulletX = new ArrayList<>();
                        bulletY = new ArrayList<>();
                        //adds all bullet x and y locations to arraylists
                        for(int i=0;i<response.length();i++){
                            try {
                                bulletX.add(Float.parseFloat( response.getJSONObject(i).get("x").toString()));
                                bulletY.add(Float.parseFloat( response.getJSONObject(i).get("y").toString()));
                                Log.d("grab", bulletX.size()+"");
                                Log.d("grab", bulletY.size()+"");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        game.setBullets(bulletX,bulletY);
                        getResponse=("Get Request is: "+ response);
                        url = "http://coms-309-041.class.las.iastate.edu:8080";
                    }
                },
                error -> {
                    getResponse=("That didn't work!" + error.toString());
                    url = "http://coms-309-041.class.las.iastate.edu:8080";
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", Auth);
//                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("param1", "value1");
                params.put("param2", "value2");
                return params;
            }

        };

        // Adding request to request queue
        VolleySingleton.getInstance(game.getContext()).addToRequestQueue(jsonRequest);
    }

    private void getPlayerLocationRequest() {


        //reinitializes the arraylists
        playerX = new ArrayList<>();
        playerY = new ArrayList<>();
        playerHealth=new ArrayList<>();
        // Request a string response from the provided URL.
        jsonRequest = new JsonArrayRequest(Request.Method.GET, url+"/pigstats",null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int length =0;
                        for(int i=0;i<response.length();i++) {
                           length++;
                        }
                        Log.d("len",""+length);
                        //length should be size of json array
                        if(length==2) {
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    if (!set) {
                                        playerTeams.add(Boolean.parseBoolean(response.getJSONObject(i).get("team").toString()));
                                        playerNames.add(response.getJSONObject(i).get("username").toString());
                                    }
                                    //adds all player hp to an arraylist
                                    playerHealth.add(Float.parseFloat(response.getJSONObject(i).get("hp").toString()));
                                    //adds all player x and y locations to arraylists
                                    playerX.add(Float.parseFloat(response.getJSONObject(i).get("x").toString()));
                                    playerY.add(Float.parseFloat(response.getJSONObject(i).get("y").toString()));

                                    if (playerX.size() == 2 && playerY.size() == 2) {
                                        game.setAllPlayerXandYs(playerX, playerY);
                                    }
                                    if (playerHealth.size() == 2 && playerNames.size() == 2 && playerTeams.size() == 2) {
                                        game.setTeamsAndNamesAndHP(playerTeams, playerNames, playerHealth);
                                    }
                                    Log.d("set", playerX.toString());
                                    Log.d("set", playerY.toString());
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        set=true;
                        // Display the first 500 characters of the response string.
                        // String response can be converted to JSONObject via
//                        try {
//                            JSONObject object = new JSONObject(response);
//                        } catch (JSONException e) {
//                            throw new RuntimeException(e);
//                        }
                        getResponse=("Get Request is: "+ response);
                        url = "http://coms-309-041.class.las.iastate.edu:8080";
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        getResponse=("That didn't work!" + error.toString());
                        url = "http://coms-309-041.class.las.iastate.edu:8080";
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", Auth);
//                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("param1", "value1");
//                params.put("param2", "value2");
                return params;
            }

        };

        // Adding request to request queue
        VolleySingleton.getInstance(game.getContext()).addToRequestQueue(jsonRequest);
    }

    public void postBullet(float x, float y,float angle) {
        // Convert input to JSONObject
        JSONObject postBody = null;
        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody = new JSONObject();
            postBody.put("x",Float.toString(x));
            postBody.put("y",Float.toString(y));
            postBody.put("angle",Float.toString(angle));

        } catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url+"/bullets",
                postBody,
                response -> {

                },
                error -> {

                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("Authorization", Auth);
                //                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };

        // Adding request to request queue

        VolleySingleton.getInstance(game.getContext()).addToRequestQueue(request);
    }

    private void putPIGRequest(float x, float y) {
        String id1= "hp";
        String id2= "x_coordinate";
        String id3= "y_coordinate";
        // Convert input to JSONObject
        JSONObject postBody = null;
        try{
            // etRequest should contain a JSON object string as your PUT body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody = new JSONObject();
            postBody.put(id2,x);
            postBody.put(id3,y);
        } catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url + "/pigstats",
                postBody,
                response -> {
                    putResponse=("Put request is: "+ response.toString());
                    url = "http://coms-309-041.class.las.iastate.edu:8080";
                },
                error -> {
                    putResponse=("Put error is " + error.getMessage());
                    url = "http://coms-309-041.class.las.iastate.edu:8080";
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", Auth);
                //                headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        // Adding request to request queue
        VolleySingleton.getInstance(game.getContext()).addToRequestQueue(request);
    }

//    private void PostRequest()
    int tick=0;
    int putGet = 0;
    boolean post=false;

    /**
     * start the next activity
     * @param context
     * @param nameOfClass
     */
    public void navigate(Context context, Class<?> nameOfClass)
    {
        sendmsg.onWebSocketClose(200, "", false);
        Intent i = new Intent(context, nameOfClass);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(i);
    }
    /**
     * method that starts the thread for the game to be run on.
     */
    public void run() {
        super.run();


        int HP = 500;
        Paint p = new Paint();
        Paint printTime = new Paint();
        printTime.setARGB(255,255,255,255);
        p.setARGB(255,0,0,0);
        p.setTextSize(35);
        printTime.setTextSize(35);
        while (isRunning) {
            canvas = holder.lockCanvas();
            tick++;
            while (canvas == null){
                canvas = holder.lockCanvas();

            }
            game.draw(canvas);
            if(tick % 60 == 0 ){
                second -= 1;

            } else if(second == 0){
                second = 50;

                navigate(game.getContext(), endgame.class);

            }
//            canvas.drawText(tick+"",250,250,p);
            canvas.drawText("Time: "+second+" seconds",195,55,printTime);


            game.update(canvas);
            if(tick%12==0) {
                putGet++;
                //put method for the player's hp, x, and y
                putPIGRequest((float)game.getPlayer().getPlayerX(), (float)game.getPlayer().getPlayerY());
                //get method for all the other player's locations
                getPlayerLocationRequest();
                getBullets();
            }
            if (canvas != null){
                if(canvasWidth==0){
                    canvasWidth=canvas.getWidth();
                    game.setCanvasWidth(canvasWidth);
                }
//                canvas.drawText(Integer.toString(tick), 500, 500, p);
                holder.unlockCanvasAndPost(canvas);
            }

        }

    }


    public ArrayList<String> getPlayerNames(){
        return playerNames;
    }
    public ArrayList<Boolean> getPlayerTeams(){
        return playerTeams;
    }
    /**
     * when the game is created, start game and set is Running to true
     * @param holder The SurfaceHolder whose surface is being created.
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        isRunning = true;
        start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
    public Game getGame(){
        return game;
    }
    public int getCanvasWidth(){
        return canvasWidth;
    }
    public String getUrl(){
        return url;
    }
    public boolean isRunning(){
        return isRunning;
    }

    public int getTick(){
        return tick;
    }

}
