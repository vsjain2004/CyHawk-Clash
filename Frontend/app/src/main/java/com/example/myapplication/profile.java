package com.example.myapplication;

import static com.example.myapplication.Login.Auth;
import static com.example.myapplication.SignUp.id;
import static com.example.myapplication.SignUp.url;
import static com.example.myapplication.SignUp.Team;
import static com.example.myapplication.SignUp.theemail;
import static com.example.myapplication.SignUp.thepassword;
import static com.example.myapplication.SignUp.thephone;
import static com.example.myapplication.SignUp.theusername;
import static com.example.myapplication.SignUp.userInfos;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * this class hold the the users information include Phone number eamil, username  and password.
 * It will also allow the user to change the information.
 */
public class profile extends AppCompatActivity {
    /**
     * view for cy profile pic
     */

    private   String Theresponse4;
    private  JsonObjectRequest putReuest;

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
        setContentView(R.layout.activity_profile);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        /**
         * button down id
         */
        Button back = findViewById(R.id.spinner2);

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
                Intent logOutToLogInScreen = new Intent(profile.this, home.class);
                logOutToLogInScreen.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(logOutToLogInScreen);
            }


        });
        /**
         * set the username box to the current username
         */
        final EditText useranem1 = findViewById(R.id.userButton);
        final String[] Username = new String[1];
        Username[0] = userInfos.getUsername();
        useranem1.setText(Username[0]);
        /**
         * if the user name box is clicked
         */
        useranem1.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v The view that was clicked.
             *  update the information  in the username box
             */
            @Override
            public void onClick(View v) {
                Username[0] = useranem1.getText().toString();
                useranem1.setText(Username[0]);
                 userInfos.SetUserName(Username[0]);
                 theusername = useranem1.getText().toString();

            }
        });
        /**
         * set the current password to the pasword box
         */
        final EditText pass = findViewById(R.id.passButton);
        final String Oldpass[]= new String[1];

        /**
         * if the password box is clicked
         */
        pass.setOnClickListener(new View.OnClickListener() {
            /**
             * update the information in the password box to userinfo
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                Oldpass[0] = pass.getText().toString();
                pass.setText(Oldpass[0]);
                userInfos.SetPassword(Oldpass[0]);
                thepassword =pass.getText().toString();


            }
        });

        /**
         * set the email to the current box
         */
        final EditText em = findViewById(R.id.emailButton);
        final String oldemail[]= new String[1];
        oldemail [0] = userInfos.getEmail();
        em.setText(oldemail[0]);
        /**
         * if the email box is clicked
         */
        em.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v The view that was clicked.
             *          update the information in the email bo to the userinfo
             */
            @Override
            public void onClick(View v) {
                oldemail[0] = em.getText().toString();
                em.setText(oldemail[0]);
                userInfos.Setemail(oldemail[0]);
                theemail =em.getText().toString();

            }
        });
        /**
         * set the current information to the phone box
         */
        final EditText phone = findViewById(R.id.phoneButton1);
        final String oldphone[]= new String[1];
        oldphone [0] = userInfos.getphoneNumber();
        phone.setText(oldphone[0]);
        /**
         * if the phone box is clicked
         */
        phone.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param v The view that was clicked.
             *          update the information in the phone box to userinfo
             */
            @Override
            public void onClick(View v) {
                oldphone[0] = phone.getText().toString();
                phone.setText(oldphone[0]);
                userInfos.SetPhone(oldphone[0]);
                thephone =phone.getText().toString();



            }
        });


        Button submit = findViewById(R.id.submit);

            submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putRequest("/users",theusername, thepassword, theemail,thephone);
                Log.d ("new info", "The id"+ id);
                Log.d("Oncreat User", "The username  " + theusername);
                Log.d("Oncreat User", "The password  " + thepassword);
                Log.d("Oncreat User", "The email  " + theemail);
                Log.d("Oncreat User", "The phone  " + thephone);
                }
          });

    }


    private  void putRequest ( String path, String username, String password, String Email, String Phone){

        // Convert input to JSONObject
        JSONObject postBody2 = null;
        try{
            // etRequest should contain a JSON object string as your POST body
            // similar to what you would have in POSTMAN-body field
            // and the fields should match with the object structure of @RequestBody on sb
            postBody2 = new JSONObject();

            postBody2.put("new_username", username);

            postBody2.put("new_password", password);

            postBody2.put("new_email", Email);

            postBody2.put("new_phone", Phone);



        } catch (Exception e){
            e.printStackTrace();
        }
        putReuest = new JsonObjectRequest(Request.Method.PUT,
                url+path, postBody2,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        //getting the user object
                        Log.d("Oncreat User", "The Arrayyyyy  " + response.toString());
                        Theresponse4 = response.toString();

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Theresponse4 = error.getMessage();

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
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(putReuest);
    }




}