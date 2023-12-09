package com.example.myapplication;

import static android.os.SystemClock.sleep;
import static com.example.myapplication.Createlobby.Game_id;
import static com.example.myapplication.Login.*;
import static com.example.myapplication.SignUp.*;

import static com.example.myapplication.home.*;

import static com.example.myapplication.weapons_view.*;
import static com.example.myapplication.WebSocketManager.player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.content.pm.*;
import android.os.Bundle;


import android.util.Log;
import android.view.*;
import android.widget.*;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PreGameLobby extends AppCompatActivity {
        public  static  Button Next;
        public static String WeaponChoose;
        /**
         * view for the chat box
         */
        public static  TextView msg2;
        public static  TextView thePlayerd;
        private int time=0;
        /**
         * send the messge
         */
        public static SendMessage sendmsg;
        /**
         * the send button
         */
        public  static Button send1;
        private JsonArrayRequest weaponess;

        private TextView weaponView;

        private  JsonObjectRequest putReuestweapon;

        private String weapon = "";
        protected void onCreate(Bundle savedInstanceState) {

                super.onCreate(savedInstanceState);
                setContentView(R.layout.pregame);
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        sleep(200);

                /**
                 * create connection of the user
                 */
                sendmsg = new SendMessage(theusername ,Game_id);
                send1 = findViewById(R.id.send);
                final EditText msg1 = findViewById(R.id.edit);
                msg2 = findViewById(R.id.msg2);

                send1.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                                sendmsg.sendMessage(msg1.getText().toString());


                        }
                });

                WeaponChoose = "Shotgun";
                putRequest("/user_weapon", 2);

                 Next = findViewById(R.id.next);
                 thePlayerd = findViewById(R.id.numP);
                thePlayerd.setText(player-1 +"");
//                if(theView == 0){
//
//                      Next.setEnabled(false);
//                        Next.setVisibility(Next.INVISIBLE);
//                        Log.d("objectives", "the objs99999999999999999999999999999999999999999  "+ Next.isEnabled());
//
//                        Next.isEnabled();
//                 }else {
                        Next.setVisibility(Next.VISIBLE);

                        Next.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                        putStartgameRequest("/start_game");

                                        Intent gamestart = new Intent(PreGameLobby.this, MainActivity.class);
                                        startActivity(gamestart);

                                }
                        });

                //}



                getRequestArray("/user_weapons");
                weaponView = findViewById(R.id.weaponsUse);

                /**
                 * drop down id
                 */
                Spinner dropdown = findViewById(R.id.add);
                /**
                 * items in the drop down
                 */
                String[] itemsWeapon = new String[]{"Choose Weapons","Shotgun", "Rifle","Sniper","SMG", "Minigun"};
                /**
                 * create the drop down functions
                 */
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsWeapon);
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

                                if (parent.getItemAtPosition(position).toString().equals("Shotgun")) {
                                        WeaponChoose = "Shotgun";
                                        putRequest("/user_weapon", 2);


                                }
                                else if (parent.getItemAtPosition(position).toString().equals("Rifle")) {
                                        WeaponChoose = "Rifle";
                                        putRequest("/user_weapon", 3);


                                }  else if (parent.getItemAtPosition(position).toString().equals("Sniper")) {
                                        WeaponChoose = "Sniper";
                                        putRequest("/user_weapon", 4);


                                }  else if (parent.getItemAtPosition(position).toString().equals("SMG")) {
                                        WeaponChoose ="SMG";
                                        putRequest("/user_weapon", 5);


                                } else  if (parent.getItemAtPosition(position).toString().equals("Minigun")) {
                                        WeaponChoose ="Minigun";
                                        putRequest("/user_weapon", 6);


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



        }


        private void getRequestArray (String path){
                weaponView = findViewById(R.id.weaponsUse);

                weaponess = new JsonArrayRequest(Request.Method.GET,
                        url+path, null,
                        new Response.Listener<JSONArray>() {

                                @Override
                                public void onResponse(JSONArray response) {
                                        Log.d("objectives", "the objs  "+ response.length() );

                                        try {
                                                for(int i =0 ; i< response.length(); i++) {
                                                        JSONObject wepo = response.getJSONObject(i);
                                                        wep_id = wepo.get("weapon_id").toString();
                                                         weapon += String.format("Name: %s\n", wepo.get("name"));

                                                }
                                                weaponView.setText(weapon);

                                        } catch (JSONException e) {
                                                throw new RuntimeException(e);
                                        }

                                }

                        }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                                Log.d("erro is in getting the weapons name for use","no request made");
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
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(weaponess);
        }



        private  void putRequest ( String path, int id){

                // Convert input to JSONObject
                JSONObject postBody2 = null;
                try{
                        // etRequest should contain a JSON object string as your POST body
                        // similar to what you would have in POSTMAN-body field
                        // and the fields should match with the object structure of @RequestBody on sb
                        postBody2 = new JSONObject();

                        postBody2.put("weapon_id", id);





                } catch (Exception e){
                        e.printStackTrace();
                }
                putReuestweapon = new JsonObjectRequest(Request.Method.PUT,
                        url+path, postBody2,
                        new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {

                                        //getting the user object
                                        Log.d("Oncreat User", "The Arrayyyyy  " + response.toString());


                                }

                        }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                                Log.d("the errro user choose weapon button", "isssssssssssssss11111");

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
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(putReuestweapon);
        }

        private  void putStartgameRequest ( String path){

                // Convert input to JSONObject

                putReuestweapon = new JsonObjectRequest(Request.Method.PUT,
                        url+path, null,
                        new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {

                                        //getting the user object
                                        Log.d("Oncreat User", "The Arrayyyyy  " + response.toString());


                                }

                        }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                                Log.d("the errro start the game if you are a mod", "isssssssssssssss11111");

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
                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(putReuestweapon);
        }



}