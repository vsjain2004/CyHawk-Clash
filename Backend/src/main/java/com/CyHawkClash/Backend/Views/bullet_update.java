package com.CyHawkClash.Backend.Views;

import com.CyHawkClash.Backend.Models.Bullet.Bullet;
import com.CyHawkClash.Backend.Models.Bullet.BulletRepository;
import com.CyHawkClash.Backend.Models.GameMap.GameMapRepository;
import com.CyHawkClash.Backend.Models.GameStatistics.GameStatistics;
import com.CyHawkClash.Backend.Models.PlayerInGame.PlayerInGame;
import com.CyHawkClash.Backend.Models.PlayerInGame.PlayerInGameRepository;
import com.CyHawkClash.Backend.Models.PlayerInGameStatistics.PlayerInGameStatistics;
import com.CyHawkClash.Backend.Models.PlayerInGameStatistics.PlayerInGameStatisticsRepository;
import com.CyHawkClash.Backend.Models.Team.TeamRepository;
import com.CyHawkClash.Backend.Models.TeamStatistics.TeamStatistics;
import com.CyHawkClash.Backend.Models.TeamStatistics.TeamStatisticsRepository;
import com.CyHawkClash.Backend.Websocket.ChatServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.awt.geom.Line2D;
import java.util.Collection;
import java.util.List;

import static java.lang.Thread.sleep;


@Service
public class bullet_update implements Runnable{

    private Bullet bullet;


    private static BulletRepository bulletRepository;

    private static PlayerInGameRepository playerInGameRepository;

    private static TeamRepository teamRepository;

    private static PlayerInGameStatisticsRepository playerInGameStatisticsRepository;

    private static GameMapRepository gameMapRepository;

    private static TeamStatisticsRepository teamStatisticsRepository;

    @Autowired
    public void setBulletRepository(BulletRepository repo){
        bulletRepository = repo;
    }

    @Autowired
    public void setPlayerInGameRepository(PlayerInGameRepository repo){
        playerInGameRepository = repo;
    }

    @Autowired
    public void setTeamRepository(TeamRepository repo) {
        teamRepository = repo;
    }

    @Autowired
    public void setPlayerInGameStatisticsRepository(PlayerInGameStatisticsRepository repo) {
        playerInGameStatisticsRepository = repo;
    }

    @Autowired
    public void setTeamStatisticsRepository(TeamStatisticsRepository repo) {
        teamStatisticsRepository = repo;
    }

    @Autowired
    public void setGameMapRepository(GameMapRepository repo) {
        bullet_update.gameMapRepository = repo;
    }

    private final Logger logger = LoggerFactory.getLogger(bullet_update.class);

    public bullet_update(){}
    private boolean collided(PlayerInGameStatistics playerInGameStatistics){
        double partbx = (bullet.getOld_x() - bullet.getX()) / 99.0;
        double partby = (bullet.getOld_y() - bullet.getY()) / 99.0;
        double partpx = (bullet.getOld_x() - bullet.getX()) / 99.0;
        double partpy = (bullet.getOld_x() - bullet.getX()) / 99.0;
        for(int i = 0; i < 100; i++){
            double d = Math.sqrt(Math.pow(bullet.getX() + i*partbx - playerInGameStatistics.getX_coordinate() - i*partpx, 2) + Math.pow(bullet.getY() + i*partby - playerInGameStatistics.getY_coordinate() - i*partpy, 2));
            if (d <= 50 + bullet.getSize()){
                return true;
            }
        }
        return false;
    }

    public void updateBullet(){
        float height = (float)(Math.sin(bullet.getAngle()) * bullet.getSpeed());
        float width = (float)(Math.cos(bullet.getAngle()) * bullet.getSpeed());

        bullet.setX(bullet.getX() + width);
        bullet.setY(bullet.getY() - height);

        if(bullet.getPlayerInGame().getTeam().isTeam()){
            if((bullet.getX() + bullet.getSize()) <= 1000 && (bullet.getX() - bullet.getSize()) >= 500 && (bullet.getY() - bullet.getSize()) >= 3500 && (bullet.getY() + bullet.getSize()) <= 4000){
                bullet.delete();
            }
        } else {
            if((bullet.getX() + bullet.getSize()) <= 7000 && (bullet.getX() - bullet.getSize()) >= 6500 && (bullet.getY() - bullet.getSize()) >= 3500 && (bullet.getY() + bullet.getSize()) <= 4000){
                bullet.delete();
            }
        }

        if(!bullet.isDeleted()) {
            Collection<PlayerInGame> playerInGameList = playerInGameRepository.findByTeam(teamRepository.findByGameAndTeamAndDeleted(bullet.getPlayerInGame().getTeam().getGame(), !(bullet.getPlayerInGame().getTeam().isTeam()), false));
            for (PlayerInGame playerInGame : playerInGameList) {
                PlayerInGameStatistics playerInGameStatistics = playerInGameStatisticsRepository.findByPlayerInGameAndDeleted(playerInGame, false);
                if (playerInGameStatistics.getHp() == 0) {
                    break;
                }
                if (collided(playerInGameStatistics)) {
                    PlayerInGameStatistics self = playerInGameStatisticsRepository.findByPlayerInGameAndDeleted(bullet.getPlayerInGame(), false);
                    TeamStatistics allyTeamStats = teamStatisticsRepository.findByTeamAndDeleted(self.getPlayerInGame().getTeam(), false);
                    TeamStatistics enemyTeamStats = teamStatisticsRepository.findByTeamAndDeleted(playerInGameStatistics.getPlayerInGame().getTeam(), false);
                    playerInGameStatistics.setHp(Math.max(0, playerInGameStatistics.getHp() - bullet.getDamage()));
                    if (playerInGameStatistics.getHp() == 0) {
                        playerInGameStatistics.incNum_deaths();
                        self.incNum_kills();
                        allyTeamStats.incTeamKills();
                        enemyTeamStats.incTeamDeaths();
                        ChatServer.broadcastViaGameId(self.getPlayerInGame().getUser().getUsername() + " killed " + playerInGame.getUser().getUsername(), bullet.getPlayerInGame().getTeam().getGame().getId());
                        Revive revive = new Revive();
                        revive.setPlayerInGameStatistics(playerInGameStatistics);
                        Thread t = new Thread(revive);
                        t.start();
                    }
                    playerInGameStatisticsRepository.save(playerInGameStatistics);
                    teamStatisticsRepository.save(allyTeamStats);
                    teamStatisticsRepository.save(enemyTeamStats);
                    playerInGameStatisticsRepository.save(self);
                    bullet.delete();
                    break;
                }
            }
        }

        if(!bullet.isDeleted()){
            if((bullet.getX() + bullet.getSize()) > 7000 || (bullet.getX() - bullet.getSize()) < 500 || (bullet.getY() - bullet.getSize()) < 500 || (bullet.getY() + bullet.getSize()) > 7000){
                bullet.delete();
            }
        }

        if(!bullet.isDeleted()){
            String[] map = gameMapRepository.findByGame(bullet.getPlayerInGame().getTeam().getGame()).getMapStr().split(",");
            for(int i = 0; i < map.length; i++){
                char[] line = map[i].toCharArray();
                for(int j = 0; j < line.length; j++){
                    if(line[j] == 'o'){
                        if((bullet.getX() + bullet.getSize() >= i*500 && bullet.getX() - bullet.getSize() <= (i+1)*500) && (bullet.getY() + bullet.getSize() >= j*500 && bullet.getY() - bullet.getSize() <= (j+1)*500)){
                            bullet.delete();
                            break;
                        }
                    }
                }
            }
        }

        bulletRepository.save(bullet);
    }

    @Override
    public void run() {
        int threadCounter = 0;
        while (bullet.getBulletRange() != threadCounter) {
            if(bullet.isDeleted()){
                return;
            }
            try {
                sleep(40);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logger.info("Thread running " + threadCounter);
            updateBullet();

            threadCounter++;
        }
        bullet.delete();
        bulletRepository.save(bullet);
    }

    public void setBullet (Bullet bullet){
        this.bullet = bullet;
    }
}

@Service
class Revive implements Runnable{

    private PlayerInGameStatistics playerInGameStatistics;

    private static PlayerInGameStatisticsRepository playerInGameStatisticsRepository;

    @Autowired
    public void setPlayerInGameStatisticsRepository(PlayerInGameStatisticsRepository repo) {
        playerInGameStatisticsRepository = repo;
    }

    @Override
    public void run() {
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        playerInGameStatistics.setHp(100);
        playerInGameStatistics.setY_coordinate(3750);
        if(playerInGameStatistics.getPlayerInGame().getTeam().isTeam()){
            playerInGameStatistics.setX_coordinate(6750);
        }
        else{
            playerInGameStatistics.setX_coordinate(750);
        }
        playerInGameStatisticsRepository.save(playerInGameStatistics);
    }

    public Revive() {}

    public void setPlayerInGameStatistics(PlayerInGameStatistics playerInGameStatistics) {
        this.playerInGameStatistics = playerInGameStatistics;
    }
}