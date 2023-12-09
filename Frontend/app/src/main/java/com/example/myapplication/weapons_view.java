package com.example.myapplication;

import static com.example.myapplication.Login.*;

import static com.example.myapplication.SignUp.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * this class hold the information for the weapon that the user have
 */
public class weapons_view extends AppCompatActivity {
    /**
     * request for list of weapons
     */
    public JsonArrayRequest weapones;
    /**
     * request for list of atttachments
     */
    public JsonArrayRequest attch;
    /**
     * the view for the avliable weapons
     */
    public TextView wep;
    /**
     * the id of the weapon
     */
    public static String wep_id;

    private  TextView addAtts;

    /**git
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapons);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        /**
         * button back id
         */
        Button back = findViewById(R.id.spinner4);

        /**
         * if  back clicked
         */
        back.setOnClickListener(new View.OnClickListener() {
            /**
             * go back to the lobby
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Intent logOutToLogInScreen = new Intent(weapons_view.this, home.class);
                logOutToLogInScreen.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(logOutToLogInScreen);
            }

        });

        wep =findViewById(R.id.weapon);

        getRequestArray("/user_weapons");
        Button addAtt = findViewById(R.id.add);
         addAtts =findViewById(R.id.attachV);
        addAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // postRequestGetAtt("/user_weapon_attachments");
                getAttachRequestArray("/user_weapon_attachments/"+wep_id);

            }
        });
    }

        private void getRequestArray (String path){

            weapones = new JsonArrayRequest(Request.Method.GET,
                    url+path, null,
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("objectives", "the objs  "+ response.length() );

                            try {
                                JSONObject wepo = response.getJSONObject(0);
                                wep_id = wepo.get("weapon_id").toString();
                                String weapon = String.format("Name: %s\n",wepo.get("name"));
                                weapon = weapon +  String.format("Fire Rate: %s\n",wepo.get("fire_rate"));
                                weapon = weapon +  String.format("Max Bullets: %s\n", wepo.get("num_max_bullets"));
                                weapon = weapon +  String.format("Reload Rate %s\n", wepo.get("reload_rate"));
                                weapon = weapon +  String.format("Range: %s\n",wepo.get("weapon_range"));
                                weapon = weapon +  String.format("Speed: %s\n",wepo.get("bullet_speed"));
                                weapon = weapon +  String.format("Bullet Size: %s\n",wepo.get("bullet_size"));
                                weapon = weapon +  String.format("Damage: %s\n",wepo.get("damage"));
                                weapon = weapon +  String.format("Accuracy: %s\n",wepo.get("accuracy"));
                                weapon = weapon +  String.format("Equipped: %s\n",wepo.get("equipped"));
                                weapon = weapon +  String.format("Kills: %s\n",wepo.get("num_kills"));
                                wep.setText(weapon);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

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
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(weapones);
        }


    private void getAttachRequestArray (String path){

        attch = new JsonArrayRequest(Request.Method.GET,
                url+path, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("objectives", "the objs  "+ response.toString() );
                        try {
                            JSONObject att = response.getJSONObject(0);
                            String strs = String.format("Damage: %s\n",att.get("damage"));
                            strs +=  String.format("Reload Rate: %s\n",att.get("reload_rate"));
                            strs +=  String.format("Fire Rate: %s\n",att.get("fire_rate"));
                            strs +=  String.format("name: %s    \n",att.get("name"));
                            strs +=  String.format("Weapon Range: %s\n",att.get("weapon_range"));
                            strs +=  String.format("Accuracy: %s\n",att.get("accuracy"));
                            addAtts.setText(strs);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(attch);
    }


    private void postRequestGetAtt(String path) {
        // Convert input to JSONObject
        JSONObject postBody = null;
        JSONArray body = null;
        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody = new JSONObject();

            postBody.put("user_weapon_id", wep_id);

            postBody.put("attachment_id", 1);

            Collection<JSONObject> c = new ArrayList<>();
            c.add(postBody);
            body = new JSONArray(c);


        } catch (Exception e){
            e.printStackTrace();
        }

        JsonArrayRequest requestAdd = new JsonArrayRequest(
                Request.Method.POST,
                url+path, body,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

        Log.d("The attachments", "they are"+response.toString());

                        try {
                            JSONObject  wepo = response.getJSONObject(0);
                            String weaponAdd = String.format("Name: %s\n",wepo.get("name"));
                            weaponAdd =weaponAdd +  String.format("Fire Rate: %s\n",wepo.get("fire_rate"));
                            weaponAdd = weaponAdd +  String.format("Max Bullets: %s\n", wepo.get("num_max_bullets"));
                            weaponAdd = weaponAdd +  String.format("Bullet Size: %s\n",wepo.get("bullet_size"));
                            weaponAdd =weaponAdd +  String.format("Reload Rate %s\n", wepo.get("reload_rate"));
                            weaponAdd = weaponAdd +  String.format("Range: %s\n",wepo.get("weapon_range"));
                            weaponAdd = weaponAdd +  String.format("Speed: %s\n",wepo.get("bullet_speed"));
                            weaponAdd = weaponAdd +  String.format("Damage: %s\n",wepo.get("damage"));
                            weaponAdd = weaponAdd +  String.format("Accuracy: %s\n",wepo.get("accuracy"));
                            weaponAdd = weaponAdd +  String.format("Equipped: %s\n",wepo.get("equipped"));
                            weaponAdd =weaponAdd +  String.format("Kills: %s\n",wepo.get("num_kills"));
                            addAtts.setText(weaponAdd);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }





                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errors ;
                        errors = ("Error: " + error.getMessage());
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
        };

        // Adding request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(requestAdd);
    }




}