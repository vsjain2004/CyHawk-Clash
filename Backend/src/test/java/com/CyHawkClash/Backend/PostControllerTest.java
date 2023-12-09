package com.CyHawkClash.Backend;


import com.CyHawkClash.Backend.Models.Bullet.Bullet;
import com.CyHawkClash.Backend.Models.Bullet.BulletRepository;
import com.CyHawkClash.Backend.Models.Game.Game;
import com.CyHawkClash.Backend.Models.Game.GameRepository;
import com.CyHawkClash.Backend.Models.GameMap.GameMap;
import com.CyHawkClash.Backend.Models.GameMap.GameMapRepository;
import com.CyHawkClash.Backend.Models.GameObjective.GameObjective;
import com.CyHawkClash.Backend.Models.GameObjective.GameObjectiveRepository;
import com.CyHawkClash.Backend.Models.GameStatistics.GameStatistics;
import com.CyHawkClash.Backend.Models.GameStatistics.GameStatisticsRepository;
import com.CyHawkClash.Backend.Models.Moderator.Moderator;
import com.CyHawkClash.Backend.Models.Moderator.ModeratorRepository;
import com.CyHawkClash.Backend.Models.PlayerInGame.PlayerInGame;
import com.CyHawkClash.Backend.Models.PlayerInGame.PlayerInGameRepository;
import com.CyHawkClash.Backend.Models.PlayerInGameStatistics.PlayerInGameStatistics;
import com.CyHawkClash.Backend.Models.PlayerInGameStatistics.PlayerInGameStatisticsRepository;
import com.CyHawkClash.Backend.Models.Team.Team;
import com.CyHawkClash.Backend.Models.Team.TeamRepository;
import com.CyHawkClash.Backend.Models.TeamStatistics.TeamStatistics;
import com.CyHawkClash.Backend.Models.TeamStatistics.TeamStatisticsRepository;
import com.CyHawkClash.Backend.Models.User.UserRepository;
import com.CyHawkClash.Backend.Views.response;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
public class PostControllerTest {

    @LocalServerPort
    int port;

    private String authorization = "";
    private String testUsername = "getTest";
    private String testFirstName = "get";
    private String testLastName = "test";
    private String testEmail = "getTest@123.456";
    private String testPhone = "123-456-7890";




    @Autowired
    private ModeratorRepository moderatorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameObjectiveRepository gameObjectiveRepository;

    @Autowired
    private GameMapRepository gameMapRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamStatisticsRepository teamStatisticsRepository;

    @Autowired
    private GameStatisticsRepository gameStatisticsRepository;

    @Autowired
    private PlayerInGameRepository playerInGameRepository;

    @Autowired
    private PlayerInGameStatisticsRepository playerInGameStatisticsRepository;

    @Autowired
    private BulletRepository bulletRepository;

    @Before
    public void setUp(){
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void testLogin(){
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

        String responseString = response.getBody().asString();

        //check status
        assertEquals(200, response.getStatusCode(), "Login test");


        try{
            JSONObject responseObject = new JSONObject(responseString);
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
    public void testIncorrectLogin(){
        java.util.Map<String, String> loginBody= new HashMap<>();
        loginBody.put("username", "getTest");
        //Wrong Password
        loginBody.put("password",  "gettestpassword");

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(loginBody).
                when().
                post("/login");


        //check status
        assertEquals(403, response.getStatusCode(), "Bad Login test");
    }


    @Test
    public void testSignUp(){
        java.util.Map<String, String> signUpBody= new HashMap<>();

        //Generate random string
        int leftLimit = 97; //'a'
        int rightLimit = 122; //'z'
        int stringLength = 9;
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(stringLength);
        for(int i=0;i<stringLength;i++){
            int randomLimitedInt = leftLimit + (int)(random.nextFloat() * (rightLimit-leftLimit+1));
            stringBuilder.append((char) randomLimitedInt);
        }

        String generatedUserInfo = stringBuilder.toString();

        signUpBody.put("first_name", generatedUserInfo);
        signUpBody.put("last_name", generatedUserInfo);
        signUpBody.put("email", generatedUserInfo);
        signUpBody.put("phone", generatedUserInfo);
        signUpBody.put("username", generatedUserInfo);
        signUpBody.put("password", generatedUserInfo);

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(signUpBody).
                when().
                post("/users");

        String responseString = response.getBody().asString();

        String auth = response.header("Authorization");

        //check status
        assertEquals(200, response.getStatusCode(), "Create user");

        try{
            JSONObject responseObject = new JSONObject(responseString);
            //Check Username
            assertEquals(generatedUserInfo, responseObject.get("username"));
            //Check Firstname
            assertEquals(generatedUserInfo, responseObject.get("first_name"));
            //Check Lastname
            assertEquals(generatedUserInfo, responseObject.get("last_name"));
            //Check Email
            assertEquals(generatedUserInfo, responseObject.get("email"));
            //Check Phone
            assertEquals(generatedUserInfo, responseObject.get("phone"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        //DELETE USER
        Response deleteResponse = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                header("Authorization",auth).
                body(signUpBody).
                when().
                delete("/users");

        assertEquals(200, deleteResponse.getStatusCode(), "Delete user");


    }

    @Test
    public void testGamePigAndBulletCreation() throws Exception{
        java.util.Map<String, String> gameBody= new HashMap<>();
        java.util.Map<String, String> loginBody= new HashMap<>();

        //Login first
        loginBody.put("username", "gameTest");
        loginBody.put("password",  "gameTestPassword");
        // login request
        Response loginResponse = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                body(loginBody).
                when().
                post("/login");

        this.authorization = loginResponse.header("Authorization");


        String maxPlayers = "4";
        String objId = "1";
        String mapId = "1";

        gameBody.put("max_players", maxPlayers);
        gameBody.put("obj_id", objId);
        gameBody.put("map_id", mapId);

        Response response = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                header("Authorization", authorization).
                body(gameBody).
                when().
                post("/games");

        //check status
        assertEquals(200, response.getStatusCode(), "Game creation");

        String responseString = response.getBody().asString();
        JSONObject responseObject = new JSONObject(responseString);

        int gameId= (int) responseObject.get("game_id");

        int id = Integer.parseInt(com.CyHawkClash.Backend.Views.response.decode_jwt(authorization));
        //Need to check Mod, game, gameObjective, gameMap, team x2, teamStats x2, gameStats
        Moderator mod = moderatorRepository.findByUserAndDeleted(userRepository.findById(id), false);
        if(mod == null){
            assertEquals(0, 1, "Moderator not created");
        }
        Game game = gameRepository.findByModAndDeleted(mod, false);
        if(game == null){
            assertEquals(0, 1, "Game not created");
        }
        GameObjective gameObjective = gameObjectiveRepository.findByGame(game);
        if(gameObjective == null){
            assertEquals(0, 1, "Game Objective not created");
        }
        GameMap gameMap = gameMapRepository.findByGame(game);
        if(gameMap == null){
            assertEquals(0, 1, "Game Map not created");
        }
        Team team1 = teamRepository.findByGameAndTeamAndDeleted(game, false, false);
        Team team2 = teamRepository.findByGameAndTeamAndDeleted(game, true, false);
        if(team1 == null || team2 == null){
            assertEquals(0, 1, "Teams not created");
        }
        TeamStatistics teamStats1 = teamStatisticsRepository.findByTeamAndDeleted(team1, false);
        TeamStatistics teamStats2 = teamStatisticsRepository.findByTeamAndDeleted(team2, false);
        if(teamStats1 == null || teamStats2 == null){
            assertEquals(0, 1, "Team stats not created");
        }
        GameStatistics gameStatistics = gameStatisticsRepository.findByGame(game);
        if(gameStatistics == null){
            assertEquals(0, 1, "Game stats not created");
        }

//        //Create PIG
//        java.util.Map<String, String> pigBody= new HashMap<>();
//        pigBody.put("game_id", String.valueOf(gameId));
//        Response pigResponse = RestAssured.given().
//                header("Content-Type", "application/json").
//                header("charset","utf-8").
//                header("Authorization", authorization).
//                body(pigBody).
//                when().
//                post("/pigs");
//
//        assertEquals(200, pigResponse.getStatusCode(), "Pig creation");

        PlayerInGame pig = playerInGameRepository.findByUserAndDeleted(userRepository.findById(id), false);
        if(pig ==null){
            assertEquals(0, 1, "PlayerInGame not created");

        }
        PlayerInGameStatistics pigStats = playerInGameStatisticsRepository.findByPlayerInGameAndDeleted(pig, false);
        if(pigStats == null){
            assertEquals(0, 1, "PlayerInGameStats not created");

        }

        //Create Bullet
        java.util.Map<String, String> bulletBody = new HashMap<>();
        bulletBody.put("x", "600");
        bulletBody.put("y", "600");
        bulletBody.put("angle", "3.141592");
        Response bulletResponse = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                header("Authorization", authorization).
                body(bulletBody).
                when().
                post("/bullets");

        assertEquals(200, bulletResponse.getStatusCode(), "Bullet creation");

        Collection<Bullet> bullet = bulletRepository.findByPlayerInGameAndDeleted(pig, false);

        if(bullet.iterator().next() == null){
            assertEquals(0, 1, "Bullet not created");
        }

        //CLEANUP
        //Delete everything related to game
        Response deleteResponse = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                header("Authorization", authorization).
                when().
                delete("/games");

        assertEquals(200, deleteResponse.getStatusCode(), "Delete games");

        //Delete everything related to PIG
        Response deletePigResponse = RestAssured.given().
                header("Content-Type", "application/json").
                header("charset","utf-8").
                header("Authorization", authorization).
                when().
                delete("/pigs");

        assertEquals(200, deletePigResponse.getStatusCode(), "Delete games");
    }


}
