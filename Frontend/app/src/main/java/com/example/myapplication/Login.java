package com.example.myapplication;

import static android.os.SystemClock.sleep;
import static com.example.myapplication.SignUp.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONObject;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * this class is used for users to log in or create account
 */
public class Login extends AppCompatActivity  {

    /**
     * store the user name
     */
    public  String username;
    /**
     * store the password
     */
    public static String password;
    /**
     * status of the post request
     */
    public static int Status =0;
    /**
     * Stores the authentication method
     */
    public static String Auth;
    private JsonObjectRequest request;

   private ArrayList<userInfo> oldusers = new ArrayList<>();
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
        setContentView(R.layout.login_screen);
        //Lock Screen in Land Mode
    this .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Button to Login
        Button LoginAcc = (Button)findViewById(R.id.login);
        //username and password id
        final EditText oldUserName = (EditText) findViewById(R.id.username);
        final EditText oldPassWord = (EditText) findViewById(R.id.password);

        /**
         * if login buttton is clicked
         */
        LoginAcc.setOnClickListener(new View.OnClickListener() {


            /**
             *
             * @param view The view that was clicked.
             */
            @Override
            public void onClick(View view) {
                username = oldUserName.getText().toString();
                password = oldPassWord.getText().toString();


                    PostForAuth("/login", username, password);
                    sleep(200);

                if(Status == 200){
                    Intent toLobby = new Intent(Login.this, home.class);
                    toLobby.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(toLobby);
                    Log.d("Staus", "the Staaaaaaa  " + Status);
                }else {
                    Toast mesage = Toast.makeText(getApplicationContext(),"wrong paswword, or username", Toast.LENGTH_LONG);
                    mesage.show();
                    Log.d("Erro", "the status is not 200  " + Status);
                }


            }

        });


        //Button for Creating an Account
        Button CreateAcc = findViewById(R.id.CreteAcc);
        /**
         * if create lobby button is clicked
         */
        CreateAcc.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view The view that was clicked.
             *             go to create lobby page
             */
            @Override
            public void onClick(View view) {
                Intent SignUpactivity = new Intent(Login.this, SignUp.class);
                SignUpactivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(SignUpactivity);

            }
        });

    }


    /**
     * gpost the loging input of the user to authenticate login
     * @param path
     * @param user
     * @param password
     */
    private void PostForAuth( String path, String user, String password) {
        // Convert input to JSONObject
        JSONObject postBody = null;
        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody = new JSONObject();

            postBody.put("username", user);
            postBody.put("password", password);

        } catch (Exception e){
            e.printStackTrace();
            return;
        }

        request = new JsonObjectRequest(
                Request.Method.POST,
                url+path, postBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    Log.d("erro","the erro"+error.getMessage());
                    }
                }
        ){
            @Override
            public java.util.Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                // headers.put("Authorization", Auth);
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
                    Auth = response.headers.get("Authorization").toString();

                    Log.d("The repon Login user", "isss"+ Auth);
                    Log.d("The repon login user", "isssssssss " + new String(response.data, StandardCharsets.UTF_8));
                    JSONObject users  = new JSONObject(new String(response.data, StandardCharsets.UTF_8));

                    theusername = users.get("username").toString();
                    thepassword =  users.get("password").toString();
                    theemail = users.get("email").toString();
                    thephone = users.get("phone").toString();
                    Team = "Cyclones";
                    id = users.get("id").toString();
                    userInfos = new userInfo(theusername, thepassword,theemail, thephone, "Cyclones");
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