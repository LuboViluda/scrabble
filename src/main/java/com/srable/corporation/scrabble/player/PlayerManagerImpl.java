package com.srable.corporation.scrabble.player;

import com.srable.corporation.scrabble.player.storage.PlayerDao;
import com.srable.corporation.scrabble.player.storage.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Implementation of {@link PlayerManager}.
 * Created by lubomir.viluda on 3/23/2019.
 */
@Component
public class PlayerManagerImpl implements PlayerManager {
    private static final int PLAYER_LEADERBOARD_LIMIT = 3;
    private static final int AT_LEAST_GAMES = 3;

    private PlayerRepository playerRepository;

    @Autowired
    public PlayerManagerImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<Player> getPlayers() {
        Iterable<PlayerDao> players = playerRepository.findAll();
        return StreamSupport.stream(players.spliterator(), false)
                .map(p -> convertFromDaoPlayer(p).build())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Player> getPlayer(String id) {
        Optional<PlayerDao> playerDao = playerRepository.findById(id);
        return playerDao.map(p -> convertFromDaoPlayer(p).build());
    }

    @Override
    public Map<String, Number> getPlayersStatistics(String id) {
        return playerRepository.getPlayerStatistics(id);
    }

    @Override
    public List<Player> getLeadersBoard() {
        List<Pair<String, Double>> leaderBoard = playerRepository.getLeaderBoard(PLAYER_LEADERBOARD_LIMIT, AT_LEAST_GAMES);
        ArrayList<Player> leaderBoardWithStat = new ArrayList<>();
        for (Pair<String, Double> pair : leaderBoard) {
            Player player = convertFromDaoWithStats(pair);
            leaderBoardWithStat.add(player);
        }
        return leaderBoardWithStat;
    }

    private Player.Builder convertFromDaoPlayer(PlayerDao playerDao) {
        return new Player.Builder()
                .withId(playerDao.getId())
                .withFirstName(playerDao.getFirstName())
                .withLastName(playerDao.getLastName());
    }

    private Player convertFromDaoWithStats(Pair<String, Double> pair) {
        PlayerDao playerDto = playerRepository.findById(pair.getFirst())
                .orElseThrow(() -> new PlayerManagerException("Inconsistency in data, missing player with id: " + pair.getFirst()));
        Player.Builder playerBuilder = convertFromDaoPlayer(playerDto);
        Map<String, Number> stats = new HashMap<>();
        stats.put("avgScore", pair.getSecond());
        playerBuilder.withPlayerStatistics(stats);
        return playerBuilder.build();
    }
}
