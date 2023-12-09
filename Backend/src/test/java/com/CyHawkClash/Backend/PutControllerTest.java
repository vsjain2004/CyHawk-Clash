package com.CyHawkClash.Backend;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import io.restassured.RestAssured;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
public class PutControllerTest {
    @LocalServerPort
    int port;

    private String authorization = "";

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";

        java.util.Map<String, String> loginBody= new HashMap<>();
        loginBody.put("username", "getTest");
        loginBody.put("password",  "getTestPassword");
        // Send request and receive response
        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(loginBody).
                when().
                post("/login");

        this.authorization = response.header("Authorization");
    }

    @Test
    public void putPigStatsTest(){
        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/pigstats");

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONArray responseArray = new JSONArray(responseString);
            assertNotNull(responseArray);
            assertEquals(3, responseArray.length());
            JSONObject responseObject = responseArray.getJSONObject(1);
            //Check total kills
            assertEquals("0", responseObject.get("num_kills"));
            //Check time
            assertEquals("100.0", responseObject.get("hp"));
            assertEquals("0.0", responseObject.get("x"));
            assertEquals("0.0", responseObject.get("y"));
            assertEquals("false", responseObject.get("team"));
            assertEquals("getTest", responseObject.get("username"));
            assertEquals("0", responseObject.get("num_deaths"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                body("{\"x_coordinate\":\"550\",\"y_coordinate\":\"550\"}").
                when().
                put("/pigstats");

        responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());

        response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/pigstats");

        responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONArray responseArray = new JSONArray(responseString);
            assertNotNull(responseArray);
            assertEquals(3, responseArray.length());
            JSONObject responseObject = responseArray.getJSONObject(1);
            //Check total kills
            assertEquals("0", responseObject.get("num_kills"));
            //Check time
            assertEquals("100.0", responseObject.get("hp"));
            assertEquals("550.0", responseObject.get("x"));
            assertEquals("550.0", responseObject.get("y"));
            assertEquals("false", responseObject.get("team"));
            assertEquals("getTest", responseObject.get("username"));
            assertEquals("0", responseObject.get("num_deaths"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                body("{\"x_coordinate\":\"0\",\"y_coordinate\":\"0\"}").
                when().
                put("/pigstats");
    }

    @Test
    public void putUserTest(){
        String testUsername = "getTest";
        String testFirstName = "get";
        String testLastName = "test";
        String testEmail = "getTest@123.456";
        String testPhone = "123-456-7890";


        // Send request and receive response
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/user");

        String responseString = response.getBody().asString();

        //Check if status code is correct
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        try{
            JSONObject responseObject = new JSONObject(responseString);
//            JSONObject responseObject = responseArray.getJSONObject(responseArray.length() -1);
            //Check Username
            assertEquals(testUsername, responseObject.get("username"));
            //Check Firstname
            assertEquals(testFirstName, responseObject.get("first_name"));
            //Check Lastname
            assertEquals(testLastName, responseObject.get("last_name"));
            //Check Email
            assertEquals(testEmail, responseObject.get("email"));
            //Check Phone
            assertEquals(testPhone, responseObject.get("phone"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                body("{\"new_username\":\"putTest\",\"new_password\":\"putTest\",\"new_email\":\"putTest\",\"new_phone\":\"putTest\"}").
                when().
                put("/users");

        responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());

        try{

            JSONObject responseObject = new JSONObject(responseString);
//            JSONObject responseObject = responseArray.getJSONObject(responseArray.length() -1);
            //Check Username
            assertEquals("putTest", responseObject.get("username"));
            //Check Firstname
            assertEquals(testFirstName, responseObject.get("first_name"));
            //Check Lastname
            assertEquals(testLastName, responseObject.get("last_name"));
            //Check Email
            assertEquals("putTest", responseObject.get("email"));
            //Check Phone
            assertEquals("putTest", responseObject.get("phone"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                body("{\"new_username\":\"getTest\",\"new_password\":\"getTestPassword\",\"new_email\":\"" + testEmail + "\",\"new_phone\":\"" + testPhone + "\"}").
                when().
                put("/users");
    }

    @Test
    public void putUWTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/user_weapon");

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONObject responseObject = new JSONObject(responseString);
//            assertEquals(5, responseArray.length());
//            JSONObject responseObject = responseArray.getJSONObject(0);
            assertEquals("Rifle", responseObject.get("name"));
            assertEquals("10", responseObject.get("fire_rate"));
            assertEquals("0", responseObject.get("num_max_bullets"));
            assertEquals("25", responseObject.get("bullet_size"));
            assertEquals("0", responseObject.get("reload_rate"));
            assertEquals("100", responseObject.get("weapon_range"));
            assertEquals("45", responseObject.get("bullet_speed"));
            assertEquals("25", responseObject.get("damage"));
            assertEquals("0.0", responseObject.get("accuracy"));
            assertEquals("0.1", responseObject.get("weapon_spread"));
            assertEquals("true", responseObject.get("equipped"));
            assertEquals("3", responseObject.get("weapon_id"));
            assertEquals("0", responseObject.get("num_kills"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                body("{\"weapon_id\":\"2\"}").
                when().
                put("/user_weapon");

        response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/user_weapon");

        responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONObject responseObject = new JSONObject(responseString);
//            assertEquals(5, responseArray.length());
//            JSONObject responseObject = responseArray.getJSONObject(0);
            assertEquals("Shotgun", responseObject.get("name"));
            assertEquals("50", responseObject.get("fire_rate"));
            assertEquals("0", responseObject.get("num_max_bullets"));
            assertEquals("25", responseObject.get("bullet_size"));
            assertEquals("0", responseObject.get("reload_rate"));
            assertEquals("50", responseObject.get("weapon_range"));
            assertEquals("45", responseObject.get("bullet_speed"));
            assertEquals("33", responseObject.get("damage"));
            assertEquals("0.0", responseObject.get("accuracy"));
            assertEquals("0.5", responseObject.get("weapon_spread"));
            assertEquals("true", responseObject.get("equipped"));
            assertEquals("2", responseObject.get("weapon_id"));
            assertEquals("0", responseObject.get("num_kills"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                body("{\"weapon_id\":\"3\"}").
                when().
                put("/user_weapon");
    }
}
