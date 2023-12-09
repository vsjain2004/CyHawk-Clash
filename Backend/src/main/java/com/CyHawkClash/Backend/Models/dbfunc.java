package com.CyHawkClash.Backend.Models;


import com.CyHawkClash.Backend.Models.Attachment.Attachment;
import com.CyHawkClash.Backend.Models.Bullet.Bullet;
import com.CyHawkClash.Backend.Models.Game.Game;
import com.CyHawkClash.Backend.Models.PlayerInGame.PlayerInGame;
import com.CyHawkClash.Backend.Models.PlayerInGame.PlayerInGameRepository;
import com.CyHawkClash.Backend.Models.PlayerInGameStatistics.PlayerInGameStatistics;
import com.CyHawkClash.Backend.Models.PlayerInGameStatistics.PlayerInGameStatisticsRepository;
import com.CyHawkClash.Backend.Models.Team.Team;
import com.CyHawkClash.Backend.Models.Team.TeamRepository;
import com.CyHawkClash.Backend.Models.User.User;
import com.CyHawkClash.Backend.Models.UserWeapon.UserWeapon;
import com.CyHawkClash.Backend.Models.UserWeapon.UserWeaponRepository;
import com.CyHawkClash.Backend.Models.UserWeaponAttachment.UserWeaponAttachmentRepository;
import com.CyHawkClash.Backend.Models.WeaponAttachment.WeaponAttachment;
import com.CyHawkClash.Backend.Models.WeaponAttachment.WeaponAttachmentRepository;
import com.CyHawkClash.Backend.Websocket.ChatServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class dbfunc {


    private static UserWeaponAttachmentRepository userWeaponAttachmentRepository;

    private static UserWeaponRepository userWeaponRepository;


    private static TeamRepository teamRepository;

    private static WeaponAttachmentRepository weaponAttachmentRepository;


    private static PlayerInGameRepository playerInGameRepository;


    private static PlayerInGameStatisticsRepository playerInGameStatisticsRepository;

    private final Logger logger = LoggerFactory.getLogger(dbfunc.class);

    @Autowired
    public void setTeamRepo(TeamRepository repo){
        teamRepository = repo;
    }

    @Autowired
    public void setPIGRepo(PlayerInGameRepository repo){
        playerInGameRepository = repo;
    }

    @Autowired
    public void setPIGSRepo(PlayerInGameStatisticsRepository repo){
        playerInGameStatisticsRepository = repo;
    }

    @Autowired
    public void setUserWeaponRepo(UserWeaponRepository repo){
        userWeaponRepository = repo;
    }

    @Autowired
    public void setUserWeaponAttachmentRepo(UserWeaponAttachmentRepository repo){
        userWeaponAttachmentRepository = repo;
    }

    @Autowired
    public void setWeaponAttachmentRepo(WeaponAttachmentRepository repo){
        weaponAttachmentRepository = repo;
    }

    public Attachment getAttachUser(User user){
        return userWeaponAttachmentRepository.findByUserWeaponAndActive(userWeaponRepository.findByUserAndEquipped(user, true), true).getAttachment();
    }

    public Collection<WeaponAttachment> getWeaponAttachUserWeapon(UserWeapon userWeapon){
        return weaponAttachmentRepository.findByWeapon(userWeapon.getWeapon());
    }

    public UserWeapon getUserWeaponPIG(PlayerInGame playerInGame){
        return userWeaponRepository.findByUserAndEquipped(playerInGame.getUser(), true);
    }

    public Game getGameUser(User user){
        return playerInGameRepository.findByUserAndDeleted(user, false).getTeam().getGame();
    }

    public User getUserBullet(Bullet bullet){
        return bullet.getPlayerInGame().getUser();
    }

    public PlayerInGameStatistics getPIGStatsUser(User user){
        return playerInGameStatisticsRepository.findByPlayerInGameAndDeleted(playerInGameRepository.findByUserAndDeleted(user, false), false);
    }

    public List<PlayerInGameStatistics> getAllPIGStatsGame(User user){
        List<PlayerInGameStatistics> pigStatsList = new ArrayList<>();
        Game game = getGameUser(user);
        logger.info(String.valueOf(game.getId()));
        Collection<Team> teams = teamRepository.findByGameAndDeleted(game, false);

        for(Team team : teams){
            logger.info(team.toString());
            Collection<PlayerInGame> pigs = playerInGameRepository.findByTeam(team);
            for(PlayerInGame pig : pigs){
                logger.info(playerInGameStatisticsRepository.findByPlayerInGameAndDeleted(pig, false).toString());
                if(playerInGameStatisticsRepository.findByPlayerInGameAndDeleted(pig, false) != null) {
                    pigStatsList.add(playerInGameStatisticsRepository.findByPlayerInGameAndDeleted(pig, false));
                }
            }
        }
        return pigStatsList;
    }

    public List<PlayerInGame> getAllPIGGame(User user) {
        List<PlayerInGame> playerInGames = new ArrayList<>();
        Game game = getGameUser(user);
        Collection<Team> teams = teamRepository.findByGameAndDeleted(game, false);
        for(Team team : teams){
            Collection<PlayerInGame> pigs = playerInGameRepository.findByTeam(team);
            playerInGames.addAll(pigs);
        }
        return playerInGames;
    }

    public dbfunc() {}
}
