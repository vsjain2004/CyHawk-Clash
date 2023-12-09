package com.CyHawkClash.Backend.Views;

import com.CyHawkClash.Backend.Models.Attachment.Attachment;
import com.CyHawkClash.Backend.Models.Attachment.AttachmentRepository;
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
import com.CyHawkClash.Backend.Models.Map.Map;
import com.CyHawkClash.Backend.Models.Map.MapRepository;
import com.CyHawkClash.Backend.Models.Moderator.Moderator;
import com.CyHawkClash.Backend.Models.Moderator.ModeratorRepository;
import com.CyHawkClash.Backend.Models.Objective.Objective;
import com.CyHawkClash.Backend.Models.Objective.ObjectiveRepository;
import com.CyHawkClash.Backend.Models.PlayerInGame.PlayerInGame;
import com.CyHawkClash.Backend.Models.PlayerInGame.PlayerInGameRepository;
import com.CyHawkClash.Backend.Models.PlayerInGameStatistics.PlayerInGameStatistics;
import com.CyHawkClash.Backend.Models.PlayerInGameStatistics.PlayerInGameStatisticsRepository;
import com.CyHawkClash.Backend.Models.Team.Team;
import com.CyHawkClash.Backend.Models.Team.TeamRepository;
import com.CyHawkClash.Backend.Models.TeamStatistics.TeamStatistics;
import com.CyHawkClash.Backend.Models.TeamStatistics.TeamStatisticsRepository;
import com.CyHawkClash.Backend.Models.User.User;
import com.CyHawkClash.Backend.Models.User.UserRepository;
import com.CyHawkClash.Backend.Models.UserWeapon.UserWeapon;
import com.CyHawkClash.Backend.Models.UserWeapon.UserWeaponRepository;
import com.CyHawkClash.Backend.Models.UserWeaponAttachment.UserWeaponAttachment;
import com.CyHawkClash.Backend.Models.UserWeaponAttachment.UserWeaponAttachmentRepository;
import com.CyHawkClash.Backend.Models.Weapon.Weapon;
import com.CyHawkClash.Backend.Models.Weapon.WeaponRepository;
import com.CyHawkClash.Backend.Models.WeaponAttachment.WeaponAttachmentRepository;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.CyHawkClash.Backend.Models.dbfunc;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Tag(name = "PostController", description = "This controller holds all of the POST API's")
@RestController
public class PostController {

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameObjectiveRepository gameObjectiveRepository;

    @Autowired
    private GameMapRepository gameMapRepository;

    @Autowired
    private GameStatisticsRepository gameStatisticsRepository;

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private ModeratorRepository moderatorRepository;

    @Autowired
    private ObjectiveRepository objectiveRepository;

    @Autowired
    private PlayerInGameRepository playerInGameRepository;

    @Autowired
    private PlayerInGameStatisticsRepository playerInGameStatisticsRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamStatisticsRepository teamStatisticsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserWeaponRepository userWeaponRepository;

    @Autowired
    private UserWeaponAttachmentRepository userWeaponAttachmentRepository;

    @Autowired
    private WeaponRepository weaponRepository;

    @Autowired
    private BulletRepository bulletRepository;

    @Operation(
            summary = "Creates a new User",
            description = "This POST creates a new User, verifies that the user has not already been created. This will create" +
                    "an authentication token for user. This will be used to verify all API calls from here on out"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(examples = {
                    @ExampleObject(value = "{\"message\":\"failure\"}")
            })})
    })

    @PostMapping("/users")
    public ResponseEntity<Object> newUser(@RequestBody java.util.Map<String, String> body) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        if((body.get("first_name") == null) || (body.get("last_name") == null) || (body.get("email") == null) || (body.get("username") == null) || (body.get("password") == null)){
            return new ResponseEntity<>(failure, headers, 400);
        } else if((body.get("first_name").isEmpty()) || (body.get("last_name").isEmpty()) || (body.get("email").isEmpty()) || (body.get("username").isEmpty()) || (body.get("password").isEmpty())){
            return new ResponseEntity<>(failure, headers, 400);
        } else if((userRepository.findByUsernameAndDeleted(body.get("username"), false) != null) || (userRepository.findByEmailAndDeleted(body.get("email"), false) != null)){
            return new ResponseEntity<>(failure, headers, 400);
        } else if ((body.get("phone") != null) && (userRepository.findByPhoneAndDeleted(body.get("phone"), false) != null)) {
            return new ResponseEntity<>(failure, headers, 400);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        User user = new User(body.get("first_name"), body.get("last_name"), body.get("email"), body.get("phone"), body.get("username"), encoder.encode(body.get("password")));
        userRepository.save(user);
        UserWeapon userWeapon1 = new UserWeapon(user, weaponRepository.findById(2));
        UserWeapon userWeapon2 = new UserWeapon(user, weaponRepository.findById(3));
        UserWeapon userWeapon3 = new UserWeapon(user, weaponRepository.findById(4));
        UserWeapon userWeapon4 = new UserWeapon(user, weaponRepository.findById(5));
        UserWeapon userWeapon5 = new UserWeapon(user, weaponRepository.findById(6));
        userWeapon2.setEquipped(true);
        userWeaponRepository.save(userWeapon1);
        userWeaponRepository.save(userWeapon2);
        userWeaponRepository.save(userWeapon3);
        userWeaponRepository.save(userWeapon4);
        userWeaponRepository.save(userWeapon5);
        return getObjectResponseEntity(headers, user);
    }

    @Operation(
            summary = "Logs a user in",
            description = "Handles a user trying to log in to their account. Verifies the users credentials and gives"
            + "them an authentication token"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "403", content = {@Content(examples = {
                    @ExampleObject(value = "{\"message\":\"failure\"}")
            })})
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody java.util.Map<String, String> body) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        if((body.get("username") == null) || (body.get("password") == null) || (body.get("username").isEmpty()) || (body.get("password").isEmpty())){
            return new ResponseEntity<>(failure, headers, 403);
        }

        User user = userRepository.findByUsernameAndDeleted(body.get("username"), false);
        if(user == null){
            return new ResponseEntity<>(failure, headers, 403);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        if(!encoder.matches(body.get("password"), user.getPassword())){
            return new ResponseEntity<>(failure, headers, 403);
        }

        return getObjectResponseEntity(headers, user);
    }

    private ResponseEntity<Object> getObjectResponseEntity(HttpHeaders headers, User user) throws Exception {
        java.util.Map<String, Object> jwtmap = new HashMap<>();
        jwtmap.put("\"id\"", user.getId());
        jwtmap.put("\"exp\"", String.format("\"%s\"", LocalDateTime.now().plusDays(1)));
        String token = response.encode_jwt(jwtmap);
        headers.add("Authorization", token);
        return new ResponseEntity<>(user, headers, 200);
    }


    @Operation(
            summary = "Creates a PlayerInGame entry",
            description = "Based on users Authentication token and team we will create a PlayerInGame object"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(examples = {
                    @ExampleObject(value = "{\"message\":\"success\"}")
            })}),
            @ApiResponse(responseCode = "400", content = {@Content(examples = {
                    @ExampleObject(value = "{\"message\":\"failure\"}")
            })}),
            @ApiResponse(responseCode = "403", content = {@Content(examples = {
                    @ExampleObject(name = "Invalid JWT", value = "\"User not Registered\""),
                    @ExampleObject(name = "Other error", value = "\"User is logged out or not Registered\"")
            })})
    })
    @PostMapping("/pigs")
    public ResponseEntity<Object> newPig(@RequestBody java.util.Map<String, String> body, @RequestHeader("Authorization") String token) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/text");

        int id = Integer.parseInt(response.decode_jwt(token));
        if(body == null){
            return new ResponseEntity<>(failure, headers, 400);
        }
        if(playerInGameRepository.findByUserAndDeleted(userRepository.findById(id), false) != null){
            return new ResponseEntity<>("{\"message\":\"User already has a PIG\"}", headers, 400);
        }
        PlayerInGame pig;
        User user = userRepository.findById(id);
        Game game = gameRepository.findById(Integer.parseInt(body.get("game_id")));
        //We still need to receive the team from the Frontend. True for Cyclones, False for Hawkeyes
        Team team0 = teamRepository.findByGameAndTeamAndDeleted(game, false, false);
        Team team1 = teamRepository.findByGameAndTeamAndDeleted(game, true, false);
        Collection<PlayerInGame> pig0 = playerInGameRepository.findByTeam(team0);
        Collection<PlayerInGame> pig1 = playerInGameRepository.findByTeam(team1);
        if(pig0.size() > pig1.size()){
            pig = new PlayerInGame(user,team1);
        } else if (pig1.size() > pig0.size()) {
            pig = new PlayerInGame(user,team0);
        } else {
            Random random = new Random(0);
            int team = random.nextInt() % 2;
            if(team == 0){
                pig = new PlayerInGame(user,team0);
            } else {
                pig = new PlayerInGame(user,team1);
            }
        }



        playerInGameRepository.save(pig);

        PlayerInGameStatistics pigStats = new PlayerInGameStatistics(pig, 100, 0, 0);

        playerInGameStatisticsRepository.save(pigStats);
        return new ResponseEntity<>(success, headers, 200);

    }

//    @Operation(
//            summary = "Create a new UserWeapon",
//            description = "Based on the authentication token, and weapon_id" +
//                    "a new UserWeapon will be created"
//    )
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", content = {@Content(examples = {
//                    @ExampleObject(value = "{\"message\":\"success\"}")
//            })}),
//            @ApiResponse(responseCode = "400", content = {@Content(examples = {
//                    @ExampleObject(value = "{\"message\":\"failure\"}")
//            })}),
//            @ApiResponse(responseCode = "403", content = {@Content(examples = {
//                    @ExampleObject(name = "Invalid JWT", value = "\"User not Registered\""),
//                    @ExampleObject(name = "Other error", value = "\"User is logged out or not Registered\"")
//            })})
//    })
//    @PostMapping("/user_weapons")
//    public ResponseEntity<Object> createNewUserWeapon(@RequestBody java.util.Map<String, String> body, @RequestHeader("Authorization") String token) throws Exception{
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/text");
//
//        int id = Integer.parseInt(response.decode_jwt(token));
//        if(body == null){
//            return new ResponseEntity<>(failure, headers, 400);
//        }
//
//        User user = userRepository.findById(id);
//
//        Weapon weapon = weaponRepository.findById(Integer.parseInt(body.get("weapon_id")));
//        UserWeapon userWeapon = new UserWeapon(user, weapon);
//        userWeaponRepository.save(userWeapon);
//
//        return new ResponseEntity<>(success, headers, 200);
//    }


    @Operation(
            summary = "Create objects that are needed when a Game is created",
            description = "Based on the authentication token, max_players, obj_id, and team_id" +
                    "a Moderator object will be created, a Game object will be created, a GameObjective object" +
                    "will be created, a GameMap object will be created, and finally two Team objects will be created."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(examples = {
                    @ExampleObject(value = "{" +
                            "\"game_id\" : 0" +
                            "}")
            })}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @PostMapping("/games")
    public ResponseEntity<Object> createNewGame(@RequestBody java.util.Map<String, String> body, @RequestHeader("Authorization") String token) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        int id = Integer.parseInt(response.decode_jwt(token));
        User user = userRepository.findById(id);
        if(user == null){
            return new ResponseEntity<>(failure, headers, 400);
        }

        //Checks if Keys are missing
        if(body.get("max_players") == null || body.get("obj_id") == null || body.get("map_id") == null){
            return new ResponseEntity<>(failure, headers, 400);
        }
        //Checks if keys are empty
        if(body.get("max_players").isEmpty() || body.get("obj_id").isEmpty() || body.get("map_id").isEmpty()){
            return new ResponseEntity<>(failure, headers, 400);

        }

        if(moderatorRepository.findByUserAndDeleted(user, false) != null || playerInGameRepository.findByUserAndDeleted(user, false) != null){
            return new ResponseEntity<>("{\"message\":\"User is already a moderator, or PIG\"}", headers, 400);
        }
        //Get user to make a Moderator
        Moderator mod = new Moderator(user);
        moderatorRepository.save(mod);

        //Get playersPerTeam from max_players to create a Game
        int playersPerTeam = (Integer.parseInt(body.get("max_players")))/2;
        Game game = new Game(mod, playersPerTeam, true);
        gameRepository.save(game);

        //Get obj_id to create GameObjective
        Objective obj = objectiveRepository.findById(Integer.parseInt(body.get("obj_id")));
        GameObjective gameObjective = new GameObjective(game, obj);
        gameObjectiveRepository.save(gameObjective);

        //Get map_id to create GameMap
        Map map = mapRepository.findById(Integer.parseInt(body.get("map_id")));
        String mapStr = map.getMap();
        GameMap gameMap = new GameMap(game, map, mapStr);
        gameMapRepository.save(gameMap);
        //Create Teams for the game
        Team redTeam = new Team(game, true);
        Team blackTeam = new Team(game, false);
        teamRepository.save(redTeam);
        teamRepository.save(blackTeam);

        PlayerInGame pig = new PlayerInGame(user, redTeam);
        playerInGameRepository.save(pig);

        PlayerInGameStatistics pigStats = new PlayerInGameStatistics(pig, 100, 0, 0);
        playerInGameStatisticsRepository.save(pigStats);

        TeamStatistics redTeamStats = new TeamStatistics(redTeam);
        TeamStatistics blackTeamStats = new TeamStatistics(blackTeam);
        teamStatisticsRepository.save(redTeamStats);
        teamStatisticsRepository.save(blackTeamStats);

        GameStatistics gameStatistics = new GameStatistics(game);
        gameStatisticsRepository.save(gameStatistics);

        java.util.Map<String, Integer> gameSuccess = new HashMap<>();

        gameSuccess.put("game_id", game.getId());

        return new ResponseEntity<>(gameSuccess, headers, 200);

    }

//    @Operation(
//            summary = "Creates a UserWeaponAttachment object",
//            description = "Based on the UserWeapon id and Attachment id this will create a UserWeaponAttachment object"
//    )
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", content = {@Content(examples = {
//                    @ExampleObject(value = "[" +
//                            "{" +
//                            "\"id\" : 0," +
//                            "\"name\" : \"string\"," +
//                            "\"fire_rate\" : 0," +
//                            "\"num_max_bullets\" : 0," +
//                            "\"bullet_size\" : 0," +
//                            "\"reload_rate\" : 0," +
//                            "\"weapon_range\" : 0," +
//                            "\"bullet_speed\" : 0," +
//                            "\"damage\" : 0," +
//                            "\"accuracy\" : 0" +
//                            "}" +
//                            "]")
//            })}),
//            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
//    })
//    @PostMapping("/user_weapon_attachments")
//    public ResponseEntity<Object> createUserWeaponAttachment(@RequestBody Collection<java.util.Map<String, String>> body){
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/json");
//
//        if(body.iterator().next().get("user_weapon_id") == null || body.iterator().next().get("attachment_id") == null){
//            return new ResponseEntity<>(failure, headers, 400);
//        }
//        if(body.iterator().next().get("user_weapon_id").isEmpty() || body.iterator().next().get("attachment_id").isEmpty()){
//            return new ResponseEntity<>(failure, headers, 400);
//        }
//
//        UserWeapon userWeapon = userWeaponRepository.findById(Integer.parseInt(body.iterator().next().get("user_weapon_id")));
//        Attachment attachment = attachmentRepository.findById(Integer.parseInt(body.iterator().next().get("attachment_id")));
//
//        UserWeaponAttachment userWeaponAttachment = new UserWeaponAttachment(userWeapon, attachment);
//        userWeaponAttachmentRepository.save(userWeaponAttachment);
//
//        Collection<java.util.Map<String,String>> userWeaponAttachments = new ArrayList<>();
//        Collection<UserWeaponAttachment> userWeaponAttachmentCollection = userWeaponAttachmentRepository.findByUserWeapon(userWeapon);
//        for(UserWeaponAttachment userWeapAtt : userWeaponAttachmentCollection){
//            userWeaponAttachments.add(userWeapAtt.serialize());
//        }
//
//
//
//        return new ResponseEntity<>(userWeaponAttachments, headers, 200);
//
//    }

    @PostMapping("/bullets")
    public ResponseEntity<Object> create_bullet(@RequestHeader("Authorization") String token, @RequestBody java.util.Map<String, String> body) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        if(body.get("x") == null || body.get("y") == null || body.get("angle") == null){
            return new ResponseEntity<>(failure, headers, 400);
        } else if (body.get("x").isEmpty() || body.get("y").isEmpty() || body.get("angle").isEmpty()) {
            return new ResponseEntity<>(failure, headers, 400);
        }

        int id = Integer.parseInt(response.decode_jwt(token));
        User user = userRepository.findById(id);

        if(user == null){
            return new ResponseEntity<>(failure, headers, 400);
        }

        PlayerInGame playerInGame = playerInGameRepository.findByUserAndDeleted(user, false);

        if(playerInGame == null){
            return new ResponseEntity<>(failure, headers, 400);
        }

        UserWeapon userWeapon = userWeaponRepository.findByUserAndEquipped(user, true);
        UserWeaponAttachment userWeaponAttachment = userWeaponAttachmentRepository.findByUserWeaponAndActive(userWeapon, true);
        Attachment attachment = null;
        if(userWeaponAttachment != null) {
            attachment = userWeaponAttachment.getAttachment();
        }
        Bullet bullet;
        if(attachment == null){
            bullet = new Bullet(playerInGame, userWeapon.getBullet_size(), userWeapon.getWeapon_range(), userWeapon.getBullet_speed(), userWeapon.getDamage(), Float.parseFloat(body.get("x")), Float.parseFloat(body.get("y")), Float.parseFloat(body.get("angle")));

        }
        else{
            bullet = new Bullet(playerInGame, userWeapon.getBullet_size() + attachment.getBullet_size(), userWeapon.getWeapon_range() + attachment.getWeapon_range(), userWeapon.getBullet_speed() + attachment.getBullet_speed(), userWeapon.getDamage() + attachment.getDamage(), Float.parseFloat(body.get("x")), Float.parseFloat(body.get("y")), Float.parseFloat(body.get("angle")));
        }
        bulletRepository.save(bullet);
        bullet_update b = new bullet_update();
        b.setBullet(bullet);
        Thread t = new Thread(b);
        t.start();
        return new ResponseEntity<>(success, headers, 200);
    }
}
