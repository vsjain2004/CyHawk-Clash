package com.CyHawkClash.Backend.Views;

import com.CyHawkClash.Backend.Models.Attachment.AttachmentRepository;
import com.CyHawkClash.Backend.Models.Bullet.Bullet;
import com.CyHawkClash.Backend.Models.Bullet.BulletRepository;
import com.CyHawkClash.Backend.Models.Game.Game;
import com.CyHawkClash.Backend.Models.Game.GameRepository;
import com.CyHawkClash.Backend.Models.GameStatistics.GameStatisticsRepository;
import com.CyHawkClash.Backend.Models.Map.MapRepository;
import com.CyHawkClash.Backend.Models.Moderator.Moderator;
import com.CyHawkClash.Backend.Models.Moderator.ModeratorRepository;
import com.CyHawkClash.Backend.Models.Objective.ObjectiveRepository;
import com.CyHawkClash.Backend.Models.PlayerInGame.PlayerInGame;
import com.CyHawkClash.Backend.Models.PlayerInGame.PlayerInGameRepository;
import com.CyHawkClash.Backend.Models.PlayerInGameStatistics.PlayerInGameStatistics;
import com.CyHawkClash.Backend.Models.PlayerInGameStatistics.PlayerInGameStatisticsRepository;
import com.CyHawkClash.Backend.Models.Team.Team;
import com.CyHawkClash.Backend.Models.Team.TeamRepository;
import com.CyHawkClash.Backend.Models.User.User;
import com.CyHawkClash.Backend.Models.User.UserRepository;
import com.CyHawkClash.Backend.Models.UserWeapon.UserWeapon;
import com.CyHawkClash.Backend.Models.UserWeapon.UserWeaponRepository;
import com.CyHawkClash.Backend.Models.UserWeaponAttachment.UserWeaponAttachmentRepository;
import com.CyHawkClash.Backend.Models.Weapon.Weapon;
import com.CyHawkClash.Backend.Models.Weapon.WeaponRepository;
import com.CyHawkClash.Backend.Models.WeaponAttachment.WeaponAttachmentRepository;
import com.CyHawkClash.Backend.Models.dbfunc;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@Tag(name = "Put Contoller", description = "This class contains all of the PUT API's")

@RestController
public class PutController {

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private ObjectiveRepository objectiveRepository;

    @Autowired
    private PlayerInGameRepository playerInGameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserWeaponRepository userWeaponRepository;

    @Autowired
    private UserWeaponAttachmentRepository userWeaponAttachmentRepository;

    @Autowired
    private WeaponRepository weaponRepository;

    @Autowired
    private WeaponAttachmentRepository weaponAttachmentRepository;

    @Autowired
    private PlayerInGameStatisticsRepository playerInGameStatisticsRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private BulletRepository bulletRepository;

    @Autowired
    private ModeratorRepository moderatorRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameStatisticsRepository gameStatisticsRepository;


    @Operation(
            summary = "Update PlayerInGameStatistics",
            description = "Based on the authentication token, hp, x, and y a PlayerInGameStatistics object will be updated"
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
    @PutMapping ("/pigstats")
    public ResponseEntity<Object> pigStatsUpdate(@RequestBody Map<String, Float> request, @RequestHeader("Authorization") String token) throws Exception{

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        dbfunc db = new dbfunc();


        int id = Integer.parseInt(response.decode_jwt(token));
        PlayerInGameStatistics pigStats = db.getPIGStatsUser(userRepository.findById(id));

        if(pigStats == null){
            return new ResponseEntity<>(failure, headers, 400);
        }
        if(request.get("x_coordinate") == null || request.get("y_coordinate") == null){
            return new ResponseEntity<>(failure, headers, 400);
        }
        pigStats.setX_coordinate(request.get("x_coordinate"));
        pigStats.setY_coordinate(request.get("y_coordinate"));

//        Bullet bullet = new Bullet(pigStats.getPlayerInGame(), 1, 20, 2, 1, 200, 200, (float)Math.PI/4);
//        bulletRepository.save(bullet);
//        bullet_update b = new bullet_update();
//        b.setBullet(bullet);
//        Thread t = new Thread(b);
//        t.start();

        playerInGameStatisticsRepository.save(pigStats);
        return new ResponseEntity<>(success, headers, 200);

    }


    @Operation(
            summary = "Update a Users account information",
            description = "Based on the authentication token, new_username, new_email, new_phone, or new_password " +
                    "a User object will be updated"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(examples = {
                    @ExampleObject(value = "{\"message\":\"failure\"}")
            })}),
            @ApiResponse(responseCode = "403", content = {@Content(examples = {
                    @ExampleObject(name = "Invalid JWT", value = "\"User not Registered\""),
                    @ExampleObject(name = "Other error", value = "\"User is logged out or not Registered\"")
            })})
    })
    @PutMapping("/users")
    public ResponseEntity<Object> userUpdate(@RequestBody java.util.Map<String, String> request, @RequestHeader("Authorization") String token) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        int id = Integer.parseInt(response.decode_jwt(token));
        User user = userRepository.findById(id);
        if (user == null) {
            return new ResponseEntity<>(failure, headers, 400);
        }
        //Changes Username
        if (request.containsKey("new_username")) {
            if ((userRepository.findByUsernameAndDeleted(request.get("new_username"), false) == null)) {
                user.setUsername(request.get("new_username"));
            }
        }
        //Changes email
        if (request.containsKey("new_email")) {
            if((userRepository.findByEmailAndDeleted(request.get("new_email"), false) == null)) {
                user.setEmail(request.get("new_email"));
            }
        }
        //Changes phone
        if (request.containsKey("new_phone")) {
            if((userRepository.findByPhoneAndDeleted(request.get("new_phone"), false) == null)) {
                user.setPhone(request.get("new_phone"));
            }
        }
        //Changes Password --- Encodes the password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
        if (request.containsKey("new_password")) {
            if(!encoder.matches(request.get("new_password"), user.getPassword())) {
                user.setPassword(encoder.encode(request.get("new_password")));
            }
        }

        userRepository.save(user);
        return new ResponseEntity<>(user, headers, 200);
    }


    @PutMapping("/user_weapon")
    public ResponseEntity<Object> changeEquippedWeapon(@RequestBody java.util.Map<String, Integer> request, @RequestHeader("Authorization") String token) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        int id = Integer.parseInt(response.decode_jwt(token));
        User user = userRepository.findById(id);
        if (user == null) {
            return new ResponseEntity<>(failure, headers, 400);
        }
        if(request == null){
            return new ResponseEntity<>(failure, headers, 400);
        }
        UserWeapon oldWeapon = userWeaponRepository.findByUserAndEquipped(user, true);
        oldWeapon.setEquipped(false);
        userWeaponRepository.save(oldWeapon);

        Weapon weapon = weaponRepository.findById(request.get("weapon_id").intValue());

        UserWeapon newWeapon = userWeaponRepository.findByUserAndWeapon(user, weapon);

        newWeapon.setEquipped(true);
        userWeaponRepository.save(newWeapon);

        return new ResponseEntity<>(success, headers, 200);

    }

    @PutMapping("/start_game")
    public ResponseEntity<Object> start_game(@RequestHeader("Authorization") String token) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        int id = Integer.parseInt(response.decode_jwt(token));
        User user = userRepository.findById(id);
        if (user == null) {
            return new ResponseEntity<>(failure, headers, 400);
        }

        Moderator moderator = moderatorRepository.findByUserAndDeleted(user, false);
        if (moderator == null){
            return new ResponseEntity<>(failure, headers, 403);
        }

        Game game = gameRepository.findByModAndDeleted(moderator, false);
        game.setInLobby(false);
        GameTime gameTime = new GameTime();
        gameTime.setGameStatistics(gameStatisticsRepository.findByGame(game));
        Thread t = new Thread(gameTime);
        t.start();

        return new ResponseEntity<>(success, headers, 200);
    }

}
