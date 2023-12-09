package com.example.myapplication;

import static android.os.SystemClock.sleep;
import static com.example.myapplication.Login.Auth;
import static com.example.myapplication.Login.Status;

import androidx.appcompat.app.AppCompatActivity;

import android.content.*;
import android.content.pm.*;
import android.os.Bundle;


import android.util.*;
import android.view.*;
import android.widget.*;



import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.*;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * the sign up page
 */
public class SignUp extends AppCompatActivity {
    /**
     * keeps all the users
     */
    public static  userInfo userInfos ;
    /**
     * the username
     */
    public static String theusername;
    /**
     * the password
     */
    public static String thepassword;
    /**
     * the first name
     */
    public static String theFirst;
    /**
     * the last name
     */
    public static String theLast;
    /**
     * the Email
     */
    public static String theemail;
    /**
     * the phone number
     */
    public static String thephone;
    /**
     * the context
     */
    public static  Context thecontext;

    /**
     * the Team either cy of hawkes
     */
    public static String Team;
    /**
     * the Server URL
     */
    public static String url = "http://coms-309-041.class.las.iastate.edu:8080";

    /**
     * the user id
     */
    public static String id;
    /**
     * item in the drop down
     */
    public static   String[] items = new String[]{"Team", "Cyclones", "Hawkes"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);



        //button for create then take user to the game main
        Button SignUpCreate = findViewById(R.id.Create);

        //Get new username
        final EditText newUsername = findViewById(R.id.userB);

        //Get New password
        final EditText newPassword = findViewById(R.id.passwordB);

        //Get Confirm Password
        final EditText confirmPassword = findViewById(R.id.confirmB);

        final EditText firtN = findViewById(R.id.firstB);
        final EditText lastN = findViewById(R.id.lastB);
        final EditText email = findViewById(R.id.emailB);
        final EditText phoneN = findViewById(R.id.phoneB);
        /**
         * if create is click to creat a new account
         */
        SignUpCreate.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view The view that was clicked.
             *             stores the user in the userInofs arraaylist and make a post request to the back end
             */
            @Override
            public void onClick(View view) {
                theusername = newUsername.getText().toString();
                thepassword = newPassword.getText().toString();
                theFirst = firtN.getText().toString();
                theLast = lastN.getText().toString();
                theemail = email.getText().toString();
                thephone = phoneN.getText().toString();
                sleep(500);


                postRequest("/users",theusername, thepassword, theFirst, theLast, theemail, thephone);

                if (confirmPassword.getText().toString().equals(newPassword.getText().toString())  && Status == 200) {


                    Log.d("Oncreat User", "The user  " + theusername);

                          userInfos = new userInfo(theusername, thepassword, theFirst, theLast, theemail, thephone,"Cyclones");
                            Intent CreateToGameMain = new Intent(SignUp.this, home.class);
                            startActivity(CreateToGameMain);
                } else {
                        // the password did not match the confirmed password
                    Toast passwordNotMatch = Toast.makeText(getApplicationContext(), "Pasword not match", Toast.LENGTH_SHORT);
                    passwordNotMatch.setGravity(Gravity.TOP, 0, 0);
                    passwordNotMatch.show();

                }
            }
        });


        //BAck to logIn page
        Button BackToLogin = findViewById(R.id.Back1);
        /**
         * if the back button is clicked
         */
        BackToLogin.setOnClickListener(new View.OnClickListener() {

            /**
             * goes back to the log in//////
             * @param view The view that was clicked.
             */
            @Override
            public void onClick(View view) {
                Intent BackToLoginScree = new Intent(SignUp.this, Login.class);
                BackToLoginScree.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(BackToLoginScree);
            }
        });


    }




    /**
     * @param user
     * @param password
     * @param theFirst
     * @param theLast
     * @param theemail
     * @param thephone
     *
     * post request for posting the user's information
     * inclouding username, password, firstname, lastname, email, and phone number
     */
    private void postRequest( String path, String user, String password, String theFirst, String theLast, String theemail, String thephone) {
        // Convert input to JSONObject
        JSONObject postBody = null;
        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody = new JSONObject();

            postBody.put("username", user);

            postBody.put("password", password);
            postBody.put("first_name", theFirst);

            postBody.put("last_name", theLast);
            postBody.put("email", theemail);

            postBody.put("phone", thephone);

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
                        Log.d("Oncreat User", "The user  " + response.toString());
                        try {
                            id =  response.get("id").toString();
                            Log.d("the id"," is " +id);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // postUser = ("Error: " + error.getMessage());
                    }
                }
        ){
            @Override
            public java.util.Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                // headers.put("Authorization", "");
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

                    Auth = response.headers.get("Authorization").toString();
                    Status = response.statusCode;
                    Log.d("The repon Added user", "isss1111111111111111112222222"+ Auth);
                    JSONObject usersSignUp  = new JSONObject(new String(response.data, StandardCharsets.UTF_8));
                    id = usersSignUp.get("id").toString();
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


