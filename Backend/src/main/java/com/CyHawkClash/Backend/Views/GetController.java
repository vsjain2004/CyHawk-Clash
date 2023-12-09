package com.CyHawkClash.Backend.Views;

import com.CyHawkClash.Backend.Models.Achievement.Achievement;
import com.CyHawkClash.Backend.Models.Achievement.AchievementRepository;
import com.CyHawkClash.Backend.Models.Attachment.Attachment;
import com.CyHawkClash.Backend.Models.Attachment.AttachmentRepository;
import com.CyHawkClash.Backend.Models.Bullet.Bullet;
import com.CyHawkClash.Backend.Models.Bullet.BulletRepository;
import com.CyHawkClash.Backend.Models.Game.Game;
import com.CyHawkClash.Backend.Models.Game.GameRepository;
import com.CyHawkClash.Backend.Models.GameMap.GameMap;
import com.CyHawkClash.Backend.Models.GameMap.GameMapRepository;
import com.CyHawkClash.Backend.Models.GameStatistics.GameStatistics;
import com.CyHawkClash.Backend.Models.GameStatistics.GameStatisticsRepository;
import com.CyHawkClash.Backend.Models.Map.Map;
import com.CyHawkClash.Backend.Models.Map.MapRepository;
import com.CyHawkClash.Backend.Models.Objective.Objective;
import com.CyHawkClash.Backend.Models.Objective.ObjectiveRepository;
import com.CyHawkClash.Backend.Models.PlayerInGame.PlayerInGame;
import com.CyHawkClash.Backend.Models.PlayerInGame.PlayerInGameRepository;
import com.CyHawkClash.Backend.Models.PlayerInGameStatistics.PlayerInGameStatistics;
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
import com.CyHawkClash.Backend.Models.WeaponAttachment.WeaponAttachment;
import com.CyHawkClash.Backend.Models.WeaponAttachment.WeaponAttachmentRepository;
import com.CyHawkClash.Backend.Models.dbfunc;
import com.CyHawkClash.Backend.Websocket.ChatServer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@RestController
public class GetController {
    
    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameMapRepository gameMapRepository;

    @Autowired
    private GameStatisticsRepository gameStatisticsRepository;

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
    private WeaponRepository weaponRepository;

    @Autowired
    private WeaponAttachmentRepository weaponAttachmentRepository;

    @Autowired
    private UserWeaponAttachmentRepository userWeaponAttachmentRepository;


    @Autowired
    private BulletRepository bulletRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamStatisticsRepository teamStatisticsRepository;

    private final Logger logger = LoggerFactory.getLogger(GetController.class);


    @Operation(
            summary = "Get a user's details",
            description = "Based on users Authentication token"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "403", content = {@Content(examples = {
                    @ExampleObject(name = "Invalid JWT", value = "\"User not Registered\""),
                    @ExampleObject(name = "Other error", value = "\"User is logged out or not Registered\"")
            })})
    })
    @GetMapping("/user")
    public User getUser(@RequestHeader("Authorization")String token) throws Exception{
        int id = Integer.parseInt(response.decode_jwt(token));
        return userRepository.findById(id);
    }

    @Operation(
            summary = "Get a list of all possible achievements",
            description = "Get a list of all possible achievements"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Achievement.class)))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/achievements")
    public List<Achievement> getAchievements(){
        return achievementRepository.findAll();
    }

    @Operation(
            summary = "Get an achievement based on its ID",
            description = "Get an achievement based on its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Achievement.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/achievements/{id}")
    public Achievement getAchievement(@PathVariable int id){
        return achievementRepository.findById(id);
    }

    @Operation(
            summary = "Get an attachment based on its ID",
            description = "Get an attachment based on its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Attachment.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/attachments/{id}")
    public Attachment getAttachment(@PathVariable int id){
        return attachmentRepository.findById(id);
    }

    @Operation(
            summary = "Get an game based on its ID",
            description = "Get an game based on its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Game.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/games/{id}")
    public Game getGame(@PathVariable int id){
        return gameRepository.findById(id);
    }

    @Operation(
            summary = "Get the Game Statistics based on user Authentication token",
            description = "Get the Game Statistics based on user Authentication token"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = GameStatistics.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/game_stats")
    public GameStatistics getGameStats(@RequestHeader("Authorization") String token) throws Exception{
        int id = Integer.parseInt(response.decode_jwt(token));
        User user = userRepository.findById(id);
        PlayerInGame pig = playerInGameRepository.findByUserAndDeleted(user, false);
        Game game = pig.getTeam().getGame();
        return gameStatisticsRepository.findByGame(game);
    }

    @Operation(
            summary = "Get the Game Map based on the Game ID",
            description = "Get the Game Map based on the Game ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = GameMap.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/game_maps/{id}")
    public GameMap getGameMap(@PathVariable int id){
        return gameMapRepository.findByGame(gameRepository.findById(id));
    }

    @Operation(
            summary = "Get a list of all open games",
            description = "Get a list of all open games"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Game.class)))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/games/open")
    public Collection<Game> getOpenGames(){
        return gameRepository.findByInLobbyAndOpenAndDeleted(true, true, false);
    }

    @Operation(
            summary = "Get all PlayerInGameStatistics for the game the user is in",
            description = "Uses Authorization header"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(examples = {
                    @ExampleObject(value = "[" +
                            "{" +
                            "\"pig_id\" : 0," +
                            "\"hp\" : 0," +
                            "\"x\" : 0," +
                            "\"y\" : 0," +
                            "\"team\" : true" +
                            "}" +
                            "]")
            })}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "403", content = {@Content(examples = {
                    @ExampleObject(name = "Invalid JWT", value = "\"User not Registered\""),
                    @ExampleObject(name = "Other error", value = "\"User is logged out or not Registered\"")
            })})
    })
    @GetMapping("/pigstats")
    public List<java.util.Map<String, String>> getPigStatsGame(@RequestHeader("Authorization") String token) throws Exception{
        List<java.util.Map<String, String>> toReturn = new ArrayList<>();
        int id = Integer.parseInt(response.decode_jwt(token));
        User user = userRepository.findById(id);
        dbfunc db = new dbfunc();
        List<PlayerInGameStatistics> pigStatsList = db.getAllPIGStatsGame(user);
        for(PlayerInGameStatistics pigStat : pigStatsList){
            toReturn.add(pigStat.serialize());
        }
        
        return toReturn;
    }

    @Operation(
            summary = "Get the PlayerInGame associated with the user",
            description = "Uses user's Authorization token"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PlayerInGame.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "403", content = {@Content(examples = {
                    @ExampleObject(name = "Invalid JWT", value = "\"User not Registered\""),
                    @ExampleObject(name = "Other error", value = "\"User is logged out or not Registered\"")
            })})
    })
    @GetMapping("/pig_users")
    public PlayerInGame getPIG_user(@RequestHeader("Authorization") String token) throws Exception{
        int id = Integer.parseInt(response.decode_jwt(token));
        return playerInGameRepository.findByUserAndDeleted(userRepository.findById(id), false);
    }

    @Operation(
            summary = "Get a Map based on its ID",
            description = "Get a Map based on its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/maps/{id}")
    public Map getMap(@PathVariable int id){
        return mapRepository.findById(id);
    }

    @Operation(
            summary = "Get a list of all game 'Objective' options",
            description = "Get a list of all game 'Objective' options"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Objective.class)))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/objs")
    public List<Objective> getObjs(){
        return objectiveRepository.findAll();
    }

    @Operation(
            summary = "Get a list of all maps given an objective",
            description = "Get a list of all maps given an objective"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Map.class)))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/map_objs/{id}")
    public Collection<Map> getMap_Obj(@PathVariable int id){
        return mapRepository.findByObj(objectiveRepository.findById(id));
    }

    @Operation(
            summary = "Get a list of all weapons",
            description = "Get a list of all weapons"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Weapon.class)))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/weapons")
    public List<Weapon> getWeapons(){
        return weaponRepository.findAll();
    }

    @Operation(
            summary = "Get a weapon based on its ID",
            description = "Get a weapon based on its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Weapon.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/weapons/{id}")
    public Weapon getWeapon(@PathVariable int id){
        return weaponRepository.findById(id);
    }

    @Operation(
            summary = "Get a list of all weapons owned by the user",
            description = "Uses Authorization token in header"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = UserWeapon.class)))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "403", content = {@Content(examples = {
                    @ExampleObject(name = "Invalid JWT", value = "\"User not Registered\""),
                    @ExampleObject(name = "Other error", value = "\"User is logged out or not Registered\"")
            })})
    })
    @GetMapping("/user_weapons")
    public Collection<java.util.Map<String, String>> getUserWeapons(@RequestHeader("Authorization") String token) throws Exception{
        Collection<java.util.Map<String,String>> toReturn = new ArrayList<>();
        int id = Integer.parseInt(response.decode_jwt(token));
//        logger.info("UserID: "+id);
        User user = userRepository.findById(id);
        Collection<UserWeapon> userWeapons = userWeaponRepository.findByUser(user);
        for(UserWeapon userWeapon : userWeapons){
            toReturn.add(userWeapon.serialize());
        }
//        logger.info(toReturn.toString());
        return toReturn;
    }

    @GetMapping("/user_weapon")
    public java.util.Map<String, String> getUserWeapon(@RequestHeader("Authorization") String token) throws Exception{
        int id = Integer.parseInt(response.decode_jwt(token));
        User user = userRepository.findById(id);
        UserWeapon userWeapon = userWeaponRepository.findByUserAndEquipped(user, true);
        java.util.Map<String, String> toReturn = userWeapon.serialize();
        return toReturn;
    }

    @Operation(
            summary = "Get a list of all possible Weapon Attachments",
            description = "Get a list of all possible Weapon Attachments"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = WeaponAttachment.class)))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/weapon_attachments")
    public List<WeaponAttachment> getWeaponAttachments(){
        return weaponAttachmentRepository.findAll();
    }

    @Operation(
            summary = "Get a Weapon Attachment based on its ID",
            description = "Get a Weapon Attachment based on its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = WeaponAttachment.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/weapon_attachments/{id}")
    public WeaponAttachment getWeaponAttachment(@PathVariable int id){
        return weaponAttachmentRepository.findById(id);
    }

    @Operation(
            summary = "Get a User's Weapon Attachment based on the User's Weapon's ID",
            description = "Get a User's Weapon Attachment based on the User's Weapon's ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(examples = {
                        @ExampleObject(value = "[" +
                                "{" +
                                "\"id\" : 0," +
                                "\"name\" : \"string\"," +
                                "\"fire_rate\" : 0," +
                                "\"num_max_bullets\" : 0," +
                                "\"bullet_size\" : 0," +
                                "\"reload_rate\" : 0," +
                                "\"weapon_range\" : 0," +
                                "\"bullet_speed\" : 0," +
                                "\"damage\" : 0," +
                                "\"accuracy\" : 0" +
                                "}" +
                                "]")
            })}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/user_weapon_attachments/{user_weapon_id}")
    public Collection<java.util.Map<String, String>> getUserWeaponAttachments(@PathVariable int user_weapon_id){
        Collection<java.util.Map<String,String>> userWeaponAttachments = new ArrayList<>();
        Collection<UserWeaponAttachment> userWeaponCollection = userWeaponAttachmentRepository.findByUserWeapon(userWeaponRepository.findById(user_weapon_id));
        for(UserWeaponAttachment userWeapon : userWeaponCollection){
            userWeaponAttachments.add(userWeapon.serialize());
        }
        return userWeaponAttachments;
    }

    @GetMapping("/bullets")
    public Collection<Bullet> getBullets(@RequestHeader("Authorization") String token) throws Exception {
        int id = Integer.parseInt(response.decode_jwt(token));
        User user = userRepository.findById(id);
        dbfunc db = new dbfunc();
        List<PlayerInGame> playerInGames = db.getAllPIGGame(user);
        Collection<Bullet> bullets = new ArrayList<>();
        for(PlayerInGame playerInGame : playerInGames){
            Collection<Bullet> bulletCollection = bulletRepository.findByPlayerInGameAndDeleted(playerInGame, false);
            bullets.addAll(bulletCollection);
        }
        return bullets;
    }

    @GetMapping("/team_stats")
    public Collection<TeamStatistics> getTeamStats(@RequestHeader("Authorization") String token) throws Exception{
        Collection<TeamStatistics> toReturn = new ArrayList<>();

        int id = Integer.parseInt(response.decode_jwt(token));
        User user = userRepository.findById(id);
        PlayerInGame pig = playerInGameRepository.findByUserAndDeleted(user, false);
        Game game = pig.getTeam().getGame();
        Collection<Team> teams = teamRepository.findByGameAndDeleted(game, false);
        for(Team team : teams){
            toReturn.add(teamStatisticsRepository.findByTeamAndDeleted(team, false));
        }

        return toReturn;
    }

}
