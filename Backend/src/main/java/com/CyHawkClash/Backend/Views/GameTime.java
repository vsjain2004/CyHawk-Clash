package com.CyHawkClash.Backend.Views;

import com.CyHawkClash.Backend.Models.GameStatistics.GameStatistics;
import com.CyHawkClash.Backend.Models.GameStatistics.GameStatisticsRepository;
import com.CyHawkClash.Backend.Models.PlayerInGameStatistics.PlayerInGameStatisticsRepository;

import static java.lang.Thread.sleep;

public class GameTime implements Runnable{
    private GameStatistics gameStatistics;
    private static GameStatisticsRepository gameStatisticsRepository;

    public void setGameStatisticsRepository(GameStatisticsRepository repo) {
        gameStatisticsRepository = repo;
    }

    public void setGameStatistics(GameStatistics gameStatistics) {
        this.gameStatistics = gameStatistics;
    }

    public GameTime() {}

    @Override
    public void run() {
        while(gameStatistics.getTime() != 0){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            gameStatistics.decTime();
            gameStatisticsRepository.save(gameStatistics);
        }
    }
}
