package com.srable.corporation.scrabble;

import com.srable.corporation.scrabble.game.storage.GameDao;
import com.srable.corporation.scrabble.game.storage.GameRepository;
import com.srable.corporation.scrabble.player.storage.PlayerDao;
import com.srable.corporation.scrabble.player.storage.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Data initialization, adds hardcoded data into in-memory db.
 * Created by lubomir.viluda on 3/23/2019.
 */
@Component
public class DataInitialization implements ApplicationRunner {
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    @Autowired
    public DataInitialization(PlayerRepository playerRepository, GameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        PlayerDao player1 = new PlayerDao("1", "misko", "vil");
        PlayerDao player2 = new PlayerDao("2", "ferko", "liv");
        PlayerDao player3 = new PlayerDao("3", "peter", "liv");
        PlayerDao player4 = new PlayerDao("4", "janko", "liv");
        PlayerDao player5 = new PlayerDao("5", "misko", "liv");

        playerRepository.save(player1);
        playerRepository.save(player2);
        playerRepository.save(player3);
        playerRepository.save(player4);
        playerRepository.save(player5);

        gameRepository.save(new GameDao("g1001", player1, player2, 14, 2));
        gameRepository.save(new GameDao("g1002", player1, player3, 15, 5));
        gameRepository.save(new GameDao("g1003", player1, player4, 15, 20));
        gameRepository.save(new GameDao("g1004", player2, player3, 0, 8));
        gameRepository.save(new GameDao("g1005", player2, player4, 99, 50));
        gameRepository.save(new GameDao("g1006", player4, player1, 99, 50));
        gameRepository.save(new GameDao("g1007", player3, player1, 40, 50));
        gameRepository.save(new GameDao("g1008", player5, player1, 200, 50));
    }
}
