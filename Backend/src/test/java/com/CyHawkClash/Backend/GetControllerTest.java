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
public class GetControllerTest {

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
    public void getUserTest(){
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
    }

    @Test
    public void getAttachmentTest(){
        double attachmentAccuracy = 0.9;
        int attachmentBulletSize = 1;
        int attachmentBulletSpeed = 1;
        int attachmentDamage = 1;
        int attachmentFireRate = 1;
        String attachmentName = "Suppressor";
        int attachmentNumBullets = 6;
        int attahcmentReloadRate = 5;
        int attachmentWeaponRange = 9;

        // Send request and receive response
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/attachments/1");

        String responseString = response.getBody().asString();

        //Check if status code is correct
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        try{
            JSONObject responseObject = new JSONObject(responseString);
//            JSONObject responseObject = responseArray.getJSONObject(responseArray.length() -1);
            //Check attachment accuracy
            assertEquals(attachmentAccuracy, responseObject.get("accuracy"));
            //Check attachment Bullet Size
            assertEquals(attachmentBulletSize, responseObject.get("bullet_size"));
            //Check attachment Bullet Speed
            assertEquals(attachmentBulletSpeed, responseObject.get("bullet_speed"));
            //Check attachment damage
            assertEquals(attachmentDamage, responseObject.get("damage"));
            //Check attachment fire rate
            assertEquals(attachmentFireRate, responseObject.get("fire_rate"));
            //Check attachment name
            assertEquals(attachmentName, responseObject.get("name"));
            //Check attachment num bullets
            assertEquals(attachmentNumBullets, responseObject.get("num_max_bullets"));
            //Check attachment reload rate
            assertEquals(attahcmentReloadRate, responseObject.get("reload_rate"));
            //Check attachment weapon range
            assertEquals(attachmentWeaponRange, responseObject.get("weapon_range"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getGameStatsTest(){


        int gameStatsRoundNumber = 1;
        int gameStatsTotalKills = 0;
        int gameStatsTime = 120;



        // Send request and receive response
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/game_stats");

        String responseString = response.getBody().asString();

        //Check if status code is correct
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        try{
            JSONObject responseObject = new JSONObject(responseString);
//            JSONObject responseObject = responseArray.getJSONObject(responseArray.length() -1);
            //Check Round number
            assertEquals(gameStatsRoundNumber, responseObject.get("roundNumber"));
            //Check total kills
            assertEquals(gameStatsTotalKills, responseObject.get("totalKills"));
            //Check time
            assertEquals(gameStatsTime, responseObject.get("time"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getAchievementsTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/achievements");

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONArray responseArray = new JSONArray(responseString);
            JSONObject responseObject = responseArray.getJSONObject(responseArray.length() -1);
            //Check Round number
            assertEquals("test", responseObject.get("function"));
            //Check total kills
            assertEquals("test", responseObject.get("requirement"));
            //Check time
            assertEquals("test", responseObject.get("reward"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getAchievementsIDTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/achievements/1");

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONObject responseObject = new JSONObject(responseString);
            //Check Round number
            assertEquals("test", responseObject.get("function"));
            //Check total kills
            assertEquals("test", responseObject.get("requirement"));
            //Check time
            assertEquals("test", responseObject.get("reward"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getGameIDTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/games/2");

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONObject responseObject = new JSONObject(responseString);
//            JSONObject responseObject = responseArray.getJSONObject(responseArray.length() -1);
            //Check Round number
            assertEquals(2, responseObject.get("num_people_team"));
            //Check total kills
            assertEquals(true, responseObject.get("open"));
            //Check time
            assertEquals(true, responseObject.get("inLobby"));
            assertEquals(false, responseObject.get("deleted"));
            assertNotNull(responseObject.get("moderator"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getGameMapTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/game_maps/2");

        String map = "wwwwwwwwwwwwwww,wssssssrssssssw,wsssossrsssossw,wssosssrssssosw,wsossssrsssssow,wssssssrssssssw,wsssssorosssssw,wrrrrrrrrrrrrrw,wsssssorossssow,wssssssrssssoow,wssosssrssosssw,wsooossrssoossw,wssosssrssssssw,wssssssrssssssw,wwwwwwwwwwwwwww";

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONObject responseObject = new JSONObject(responseString);
//            JSONObject responseObject = responseArray.getJSONObject(responseArray.length() -1);
            //Check Round number
            assertEquals(map, responseObject.get("mapStr"));
            assertNotNull(responseObject.get("game"));
            assertNotNull(responseObject.get("map"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getOpenGamesTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/games/open");

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONArray responseArray = new JSONArray(responseString);
            assertNotNull(responseArray);
            JSONObject responseObject = responseArray.getJSONObject(1);
            //Check Round number
            assertEquals(2, responseObject.get("num_people_team"));
            //Check total kills
            assertEquals(true, responseObject.get("open"));
            //Check time
            assertEquals(true, responseObject.get("inLobby"));
            assertEquals(false, responseObject.get("deleted"));
            assertNotNull(responseObject.get("moderator"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getPigStatsTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
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
    }

    @Test
    public void getPigUserTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/pig_users");

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONObject responseObject = new JSONObject(responseString);
//            assertNotNull(responseArray);
//            JSONObject responseObject = responseArray.getJSONObject(1);
            //Check Round number
            assertEquals(false, responseObject.get("deleted"));
            assertNotNull(responseObject.get("user"));
            assertNotNull(responseObject.get("team"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getMapIDTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/maps/1");

        String map = "wwwwwwwwwwwwwww,wssssssrssssssw,wsssossrsssossw,wssosssrssssosw,wsossssrsssssow,wssssssrssssssw,wsssssorosssssw,wrrrrrrrrrrrrrw,wsssssorossssow,wssssssrssssoow,wssosssrssosssw,wsooossrssoossw,wssosssrssssssw,wssssssrssssssw,wwwwwwwwwwwwwww";

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONObject responseObject = new JSONObject(responseString);
//            JSONObject responseObject = responseArray.getJSONObject(responseArray.length() -1);
            //Check Round number
            assertEquals(map, responseObject.get("map"));
            assertEquals("default", responseObject.get("mapName"));
            assertEquals(16, responseObject.get("mapWidth"));
            assertNotNull(responseObject.get("obj"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getObjsTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/objs");

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONArray responseArray = new JSONArray(responseString);
            assertEquals(2, responseArray.length());
            JSONObject responseObject = responseArray.getJSONObject(0);
            assertEquals("team_death", responseObject.get("function"));
            assertEquals("Team Deathmatch", responseObject.get("obj"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getMapObjTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/map_objs/1");

        String map = "wwwwwwwwwwwwwww,wssssssrssssssw,wsssossrsssossw,wssosssrssssosw,wsossssrsssssow,wssssssrssssssw,wsssssorosssssw,wrrrrrrrrrrrrrw,wsssssorossssow,wssssssrssssoow,wssosssrssosssw,wsooossrssoossw,wssosssrssssssw,wssssssrssssssw,wwwwwwwwwwwwwww";

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONArray responseArray = new JSONArray(responseString);
            JSONObject responseObject = responseArray.getJSONObject(responseArray.length() -1);
            assertEquals(map, responseObject.get("map"));
            assertEquals("default", responseObject.get("mapName"));
            assertEquals(16, responseObject.get("mapWidth"));
            assertNotNull(responseObject.get("obj"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getWeaponsTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/weapons");

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONArray responseArray = new JSONArray(responseString);
            assertEquals(6, responseArray.length());
            JSONObject responseObject = responseArray.getJSONObject(0);
            assertEquals("basic", responseObject.get("weapon_name"));
            assertEquals(1, responseObject.get("base_fire_rate"));
            assertEquals(6, responseObject.get("base_max_bullets"));
            assertEquals(1, responseObject.get("base_bullet_size"));
            assertEquals(5, responseObject.get("base_reload_rate"));
            assertEquals(10, responseObject.get("base_range"));
            assertEquals(1, responseObject.get("base_bullet_speed"));
            assertEquals(1, responseObject.get("base_damage"));
            assertEquals(1.0, responseObject.get("base_accuracy"));
            assertEquals(0.3, responseObject.get("base_spread"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getWeaponIDTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/weapons/1");

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONObject responseObject = new JSONObject(responseString);
//            assertEquals(6, responseArray.length());
//            JSONObject responseObject = responseArray.getJSONObject(0);
            assertEquals("basic", responseObject.get("weapon_name"));
            assertEquals(1, responseObject.get("base_fire_rate"));
            assertEquals(6, responseObject.get("base_max_bullets"));
            assertEquals(1, responseObject.get("base_bullet_size"));
            assertEquals(5, responseObject.get("base_reload_rate"));
            assertEquals(10, responseObject.get("base_range"));
            assertEquals(1, responseObject.get("base_bullet_speed"));
            assertEquals(1, responseObject.get("base_damage"));
            assertEquals(1.0, responseObject.get("base_accuracy"));
            assertEquals(0.3, responseObject.get("base_spread"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getUWTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/user_weapons");

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONArray responseArray = new JSONArray(responseString);
            assertEquals(5, responseArray.length());
            JSONObject responseObject = responseArray.getJSONObject(0);
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
            assertEquals("false", responseObject.get("equipped"));
            assertEquals("2", responseObject.get("weapon_id"));
            assertEquals("0", responseObject.get("num_kills"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getUWEqTest(){
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
    }

    @Test
    public void getWeapAttachTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/weapon_attachments");

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONArray responseArray = new JSONArray(responseString);
            assertEquals(2, responseArray.length());
            JSONObject responseObject = responseArray.getJSONObject(1);
            assertNotNull(responseObject.get("weapon"));
            assertNotNull(responseObject.get("attachment"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getWeapAttachIDTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/weapon_attachments/1");

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONObject responseObject = new JSONObject(responseString);
//            assertEquals(2, responseArray.length());
//            JSONObject responseObject = responseArray.getJSONObject(1);
            assertNotNull(responseObject.get("weapon"));
            assertNotNull(responseObject.get("attachment"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getUWATest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/user_weapon_attachments/12");

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONArray responseArray = new JSONArray(responseString);
            assertEquals(1, responseArray.length());
            JSONObject responseObject = responseArray.getJSONObject(0);
            assertEquals("Suppressor", responseObject.get("name"));
            assertEquals("1", responseObject.get("fire_rate"));
            assertEquals("6", responseObject.get("num_max_bullets"));
            assertEquals("1", responseObject.get("bullet_size"));
            assertEquals("5", responseObject.get("reload_rate"));
            assertEquals("9", responseObject.get("weapon_range"));
            assertEquals("1", responseObject.get("bullet_speed"));
            assertEquals("1", responseObject.get("damage"));
            assertEquals("0.9", responseObject.get("accuracy"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getBulletsTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/bullets");

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONArray responseArray = new JSONArray(responseString);
            assertEquals(0, responseArray.length());
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Test
    public void getTeamStatsTest(){
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                header("Authorization", this.authorization).
                when().
                get("/team_stats");

        String responseString = response.getBody().asString();

        assertEquals(200, response.getStatusCode());
        try{

            JSONArray responseArray = new JSONArray(responseString);
            assertEquals(2, responseArray.length());
            JSONObject responseObject = responseArray.getJSONObject(0);
            assertEquals(0, responseObject.get("numDefeats"));
            assertEquals(0, responseObject.get("numVictories"));
            assertEquals(0, responseObject.get("teamDeaths"));
            assertEquals(0, responseObject.get("teamKills"));
            assertEquals(0.0, responseObject.get("objProgress"));
            assertEquals(false, responseObject.get("deleted"));
            assertNotNull(responseObject.get("team"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
}
