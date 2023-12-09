package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.myapplication.Login.*;
import static com.example.myapplication.SignUp.*;
import static com.example.myapplication.Createlobby.*;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

;import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * this classe has the list of open lobbys, and it allows the user to go to the create lobby page
 * if they do not want to join other people's lobby
 */
public class home extends AppCompatActivity {
    /**
     * get arrayrequest for geting objectives
     */
    public JsonArrayRequest arrayReuest  ;
    /**
     * the vide for the list of lobby code
     */
    public TextView lobbycode;

    /**
     * the list of lobby ids in a arraylist
     */
    public ArrayList<String> Lobby_IDs = new ArrayList<>();
    public static int theView = 0;

    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     *
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeviews);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


//get the list of open lobbys
getRequestArray("/games/open");




        /**
         * creat button id
         */
        Button CreateLobbyB = findViewById(R.id.createLobby);
         lobbycode = findViewById(R.id.code1);
        /**
         * litsens for when the create button is clicked
         */
        CreateLobbyB.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                /**
                 * start the create lobby page
                 */
                Intent CreatenewGame = new Intent(home.this, Createlobby.class);
                CreatenewGame.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(CreatenewGame);


            }
            });
        /**
         * drop down id
         */
        Spinner dropdown = findViewById(R.id.spinner1);
        /**
         * items in the drop down
         */
        String[] items = new String[]{"Setting","Profile", "Weapons", "Logout"};
        /**
         * create the drop down functions
         */
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        /**
         * if an item is selected
         */
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                if (parent.getItemAtPosition(position).toString().equals("Weapons")) {
                    //go to weapon page

                    Intent weaponactivity = new Intent(home.this, weapons_view.class);
                    weaponactivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(weaponactivity);

                } else if (parent.getItemAtPosition(position).toString().equals("Profile")) {
                    //go to Profiles page

                    Intent profileActivity = new Intent(home.this, profile.class);
                    profileActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(profileActivity);

                }else if (parent.getItemAtPosition(position).toString().equals("Logout")) {
                    //go to Profiles page

                    Intent logoutActivty = new Intent(home.this, Login.class);
                    Auth = null;
                    logoutActivty.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(logoutActivty);
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



        Button JoinGame =findViewById(R.id.JoinB);
        EditText codes = findViewById(R.id.idJoin);


        JoinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postJoinRequest("/pigs", codes.getText().toString() );
                Game_id = codes.getText().toString();


                if(Status == 200){
                    Intent Startgame = new Intent(home.this, PreGameLobby.class);

                    Startgame.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(Startgame);
                }else {

                    Log.d("game start erro","the game did not statrt");
                }
            }
        });


    }



    /**
     * getting the array of object to joing the game with the game id
     * @param path
     */
    private void getRequestArray (String path){

        arrayReuest = new JsonArrayRequest(Request.Method.GET,
                url+path, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("objectives", "the objs  "+ response.toString() + Status);

                        String games = "";
                        for (int i =0 ; i<response.length(); i++) {
                            try {
                                JSONObject lobby_id =response.getJSONObject(i);

                                games = games + String.format("%d\n",lobby_id.get("id"));


                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }

                        lobbycode.setText(games);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("erro","no request made");
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public java.util.Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                 headers.put("Authorization", Auth);
                // headers.put("Content-Type", "application/json");
                return headers;
            }

            @Override
            protected java.util.Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                //     params.put("param1", "value1");
                //   params.put("param2", "value2");

                return params;
            }




        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(arrayReuest);
    }

    private void postJoinRequest( String path, String game_Id) {
        // Convert input to JSONObject
        JSONObject postBody = null;
        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody = new JSONObject();

            postBody.put("game_id", game_Id);



        } catch (Exception e){
            e.printStackTrace();
            return;
        }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url+path, postBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       Log.d("the game", "aaaaaaaaaaaaaaaaaaaaa  "+response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    Log.d("Erro", "not posted");
                    }
                }
        ){
            @Override
            public java.util.Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", Auth);
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

            @Override
            protected Response parseNetworkResponse(NetworkResponse response) {
                try {

                    Auth = response.headers.get("Authorization");
                    Status = response.statusCode;
                    String json = new String(response.data, HttpHeaderParser.parseCacheHeaders(response).toString());


                    return Response.success(response,HttpHeaderParser.parseCacheHeaders(response));
                } catch (Exception e) {
                    return Response.error(new ParseError(e));
                }
            }

        };
        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }



}



