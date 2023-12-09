package com.CyHawkClash.Backend.Views;

import com.CyHawkClash.Backend.Models.Attachment.AttachmentRepository;
import com.CyHawkClash.Backend.Models.Game.Game;
import com.CyHawkClash.Backend.Models.Game.GameRepository;
import com.CyHawkClash.Backend.Models.Map.Map;
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
import com.CyHawkClash.Backend.Models.UserWeapon.UserWeaponRepository;
import com.CyHawkClash.Backend.Models.UserWeaponAttachment.UserWeaponAttachmentRepository;
import com.CyHawkClash.Backend.Models.Weapon.WeaponRepository;
import com.CyHawkClash.Backend.Models.WeaponAttachment.WeaponAttachmentRepository;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@Tag(name = "Delete Controller", description = "Holds all of the DELETE api controllers")
@RestController
public class DeleteController {

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ObjectiveRepository objectiveRepository;

    @Autowired
    private PlayerInGameRepository playerInGameRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModeratorRepository moderatorRepository;

    @Autowired
    private TeamRepository teamRepository;

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



    @Operation(
            summary = "Deletes a User",
            description = "Based on a User id a User object will be deleted from the table"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = User.class))}),
        @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
        @ApiResponse(responseCode = "403", content = {@Content(examples = {
                @ExampleObject(name = "Invalid JWT", value = "\"User not Registered\""),
                @ExampleObject(name = "Other error", value = "\"User is logged out or not Registered\"")
        })})
    })
    @DeleteMapping("/users")
    public String deleteUser(@RequestHeader("Authorization") String token) throws Exception{
        int id = Integer.parseInt(response.decode_jwt(token));

        User user = userRepository.findById(id);
        user.delete();
        userRepository.save(user);
        return success;
    }


    @Operation(
            summary = "Deletes a PlayerInGame",
            description = "Based on a PlayerInGame id a PlayerInGame object will be deleted from the table"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PlayerInGame.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "403", content = {@Content(examples = {
                    @ExampleObject(name = "Invalid JWT", value = "\"User not Registered\""),
                    @ExampleObject(name = "Other error", value = "\"User is logged out or not Registered\"")
            })})
    })
    @DeleteMapping("/pigs")
    public ResponseEntity<Object> deletePig(@RequestHeader("Authorization") String token) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/text");

        int id = Integer.parseInt(response.decode_jwt(token));
        PlayerInGame pig = playerInGameRepository.findByUserAndDeleted(userRepository.findById(id), false);
        PlayerInGameStatistics pigstats = playerInGameStatisticsRepository.findByPlayerInGameAndDeleted(pig, false);
        if(pig == null || pigstats == null){
            return new ResponseEntity<>(failure, headers, 400);
        }
        pig.delete();
        pigstats.delete();
        playerInGameRepository.save(pig);
        playerInGameStatisticsRepository.save(pigstats);

        return new ResponseEntity<>(success, headers, 200);
    }

    @DeleteMapping("/games")
    public ResponseEntity<Object> deleteGame(@RequestHeader("Authorization") String token) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/text");

        int id = Integer.parseInt(response.decode_jwt(token));
        Moderator mod = moderatorRepository.findByUserAndDeleted(userRepository.findById(id), false);
        Game game = gameRepository.findByModAndDeleted(mod, false);
        Team team1 = teamRepository.findByGameAndTeamAndDeleted(game, false, false);
        Team team2 = teamRepository.findByGameAndTeamAndDeleted(game, true, false);
//        Collection<PlayerInGame> pigsTeam1 = playerInGameRepository.findByTeam(team1);
//        Collection<PlayerInGame> pigsTeam2 = playerInGameRepository.findByTeam(team2);

        
        //Delete all Pigs, mod and Game
//        for(PlayerInGame pig : pigsTeam1){
//            pig.delete();
//            playerInGameRepository.save(pig);
//        }
//        for(PlayerInGame pig : pigsTeam2){
//            pig.delete();
//            playerInGameRepository.save(pig);
//        }
        team1.delete();
        team2.delete();
        mod.delete();
        game.delete();
        moderatorRepository.save(mod);
        gameRepository.save(game);
        teamRepository.save(team1);
        teamRepository.save(team2);

        return new ResponseEntity<>(success, headers, 200);

    }
    
}
