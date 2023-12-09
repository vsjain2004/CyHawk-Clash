package com.example.myapplication;



import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import org.json.*;
import java.util.HashMap;
import java.util.Map;

import static com.example.myapplication.Login.*;
import static com.example.myapplication.SignUp.*;
import static com.example.myapplication.home.*;

import androidx.appcompat.app.AppCompatActivity;
import android.content.*;
import android.content.pm.*;
import android.os.Bundle;
import android.util.*;
import android.view.*;
import android.widget.*;

import org.json.JSONException;




/**
 * The class lets the user to create a lobby, and starts a game
 */
public class Createlobby extends AppCompatActivity {
    /**
     * the map
     */
    public  static  int  map ;
    /**
     * the obkective
     */
    public static int Obj ;
    /**
     * the number of players
     */
    public static int Nump;
    /**
     * the game id
     */
    public static String Game_id;
    /**
     * the view for the game id
     */
    public static TextView code;
    public static String theGame_id;



    private String url = "http://coms-309-041.class.las.iastate.edu:8080";

    private String mapname;

    private JsonObjectRequest arrayReuest;
    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createlobby);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);



        /**
         * Dropdown spinner id
         */
        Button back = findViewById(R.id.spinner4);
        /**
         * if  back clicked
         */
        back.setOnClickListener(new View.OnClickListener() {
            /**
             * go back to the lobby
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent logOutToLogInScreen = new Intent(Createlobby.this, home.class);
                startActivity(logOutToLogInScreen);
            }


        });


        Spinner objD = findViewById(R.id.spinner6);


        String objitems [] = new String[] {"objectvie", "Team Death Match", "Capter the flag"};





        /**
         * create the drop down functions
         */
        ArrayAdapter<String> adapterObj = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, objitems);
        objD.setAdapter(adapterObj);
        /**
         * if an item is selected
         */
        objD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             *
             * @param parent The AdapterView where the selection happened
             * @param view The view within the AdapterView that was clicked
             * @param position The position of the view in the adapter
             * @param id The row id of the item that is selected
             * if the item is selected goes to the page that te user selected
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("Team Death Match")) {
                    Obj = 1;



              } else if (parent.getItemAtPosition(position).equals("Capter the flag")) {

                    Obj = 2;

            }
            }

            /**
             *
             * @param parent The AdapterView that now contains no selected item.
             *               do nothing
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //do nothing
            }
        });




        Spinner mpdropdown = findViewById(R.id.spinner8);
        String  maps [] = new String[]{"Maps","Map1"};

        /**
         * create the drop down functions
         */
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, maps);
        mpdropdown.setAdapter(adapter3);
        /**
         * if an item is selected
         */
        mpdropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             *
             * @param parent The AdapterView where the selection happened
             * @param view The view within the AdapterView that was clicked
             * @param position The position of the view in the adapter
             * @param id The row id of the item that is selected
             * if the item is selected goes to the page that te user selected
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("Map1")) {
                   map =1;

                }

            }
                /**
                 *
                 * @param parent The AdapterView that now contains no selected item.
                 *               do nothing
                 */
                @Override
                public void onNothingSelected (AdapterView < ? > parent){
                    //do nothing
                }

        });





        Spinner numplayer = findViewById(R.id.spinner9);

        /**
         * start the drop down funtions
         */
        String numplayeritme [] ={"2", "4", "6", "8", "10"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, numplayeritme);
        numplayer.setAdapter(adapter4);


        numplayer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             *
             * @param parent The AdapterView where the selection happened
             * @param view The view within the AdapterView that was clicked
             * @param position The position of the view in the adapter
             * @param id The row id of the item that is selected
             *
             *           number of playeris allowed 2 to 10
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).toString().equals("2")) {
                    Nump = 2;


                } else if (parent.getItemAtPosition(position).toString().equals("4")) {
                    Nump = 4;


                } else if (parent.getItemAtPosition(position).toString().equals("6")) {
                    Nump = 6;


                }else if (parent.getItemAtPosition(position).toString().equals("8")) {
                    Nump = 8;


                }else if (parent.getItemAtPosition(position).toString().equals("10")) {
                    Nump = 10;


                }
            }

            /**
             *
             * @param parent The AdapterView that now contains no selected item.
             *               no item selected so do nothing
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**
         * get the code to display
         */
        Button getcode = findViewById(R.id.Getcode);
        code= findViewById(R.id.textView3);
        getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRequestforgame("/games", theusername, Obj, map, Nump);
                if (Status == 200) {
//                        sleep(200);
//                    code.setText(Game_id);

                }else{
                    Log.d("the game error ", "the game is not posted ");
                }

            }
        });
        /**
         * start the game
         */
        Button button1 =findViewById(R.id.start);
        button1.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                theView = 1;
                Intent move = new Intent(Createlobby.this, PreGameLobby.class);
                startActivity(move);
            }
        });


}

    private void postRequestforgame(String path, String Username, int map, int Obj, int max_player) {
         code = findViewById(R.id.textView3);
        // Convert input to JSONObject
        JSONObject postBody = null;
        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody = new JSONObject();

            postBody.put("username", 1);

            postBody.put("map_id", map);

            postBody.put("max_players", max_player);

            postBody.put("obj_id", Obj);



        } catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url+path, postBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Game_id =  response.get("game_id").toString();
                            code.setText(Game_id);

                            Log.d("the game i ", "the id "+response.toString());
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("the erro is in posr for game id", "no game id");
                    }
                }
        ){
            @Override
            public java.util.Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Authorization",Auth);
                //  headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected java.util.Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                //  params.put("param1", "value1");
                //   params.put("param2", "value2");
                return params;
            }


        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }



}