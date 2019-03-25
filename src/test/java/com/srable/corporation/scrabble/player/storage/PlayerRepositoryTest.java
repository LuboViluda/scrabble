package com.srable.corporation.scrabble.player.storage;

import com.srable.corporation.scrabble.game.storage.GameDao;
import com.srable.corporation.scrabble.game.storage.GameRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.util.Pair;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by lubomir.viluda on 3/24/2019.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PlayerRepositoryTest {
    private final String WINS_PROPERTY = "wins";
    private final String LOSSES_PROPERTY = "losses";
    private final String AVERAGE_SCORE_PROPERTY = "avgScore";
    private final String PLAYER_ID_1 = "1";
    private final String PLAYER_ID_2 = "2";
    private final String PLAYER_ID_3 = "3";
    private final String PLAYER_ID_4 = "4";
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameRepository gameRepository;

    @Test
    public void getPlayerStatistics_nonExistingPlayer_returnsZeroStats() throws Exception {
        Map<String, Number> playerStatistics = playerRepository.getPlayerStatistics(PLAYER_ID_1);

        assertGameStats(playerStatistics, LOSSES_PROPERTY, 0);
        assertGameStats(playerStatistics, WINS_PROPERTY, 0);
        assertGameStats(playerStatistics, AVERAGE_SCORE_PROPERTY, 0.0);
    }

    @Test
    public void getPlayerStatistics_zeroGame_returnsZeroStats() throws Exception {
        preparePlayer(PLAYER_ID_1, "john", "wicks");

        Map<String, Number> playerStatistics = playerRepository.getPlayerStatistics(PLAYER_ID_2);

        assertGameStats(playerStatistics, LOSSES_PROPERTY, 0);
        assertGameStats(playerStatistics, WINS_PROPERTY, 0);
        assertGameStats(playerStatistics, AVERAGE_SCORE_PROPERTY, 0.0);
    }

    @Test
    public void getPlayerStatistics_moreGames_winsCorrect() throws Exception {
        prepareMoreGamesData();

        Map<String, Number> playerStatistics = playerRepository.getPlayerStatistics(PLAYER_ID_2);

        assertGameStats(playerStatistics, WINS_PROPERTY, 3);
    }

    @Test
    public void getPlayerStatistics_moreGames_lossesCorrect() throws Exception {
        prepareMoreGamesData();

        Map<String, Number> playerStatistics = playerRepository.getPlayerStatistics(PLAYER_ID_3);

        assertGameStats(playerStatistics, LOSSES_PROPERTY, 3);
    }

    @Test
    public void getPlayerStatistics_correctAverage() throws Exception {
        prepareMoreGamesData();

        Map<String, Number> playerStatistics = playerRepository.getPlayerStatistics(PLAYER_ID_2);

        assertGameStatsWithPrecision(playerStatistics, AVERAGE_SCORE_PROPERTY, 46.666);
    }

    @Test
    public void getLeaderBoard_correctOrder() throws Exception {
        prepareMoreGamesData();

        List<Pair<String, Double>> leaderBoard = playerRepository.getLeaderBoard(3, 3);
        List<String> ids = leaderBoard.stream().map(Pair::getFirst).collect(Collectors.toList());

        assertThat(ids).containsExactly(PLAYER_ID_2, PLAYER_ID_1, PLAYER_ID_3);
    }

    @Test
    public void getLeaderBoard_resultSizeLimited() throws Exception {
        prepareMoreGamesData();

        List<Pair<String, Double>> leaderBoard = playerRepository.getLeaderBoard(1, 3);

        assertThat(leaderBoard.size()).isEqualTo(1);
    }

    @Test
    public void getLeaderBoard_playerBelowGameMinimum_ignored() throws Exception {
        prepareMoreGamesData();
        Optional<PlayerDao> byId = playerRepository.findById(PLAYER_ID_1);
        PlayerDao player4 = preparePlayer(PLAYER_ID_4, "ava", "ro");
        createGame(player4, byId.get(), "g116", 999, 41);

        List<Pair<String, Double>> leaderBoard = playerRepository.getLeaderBoard(3, 3);
        List<String> ids = leaderBoard.stream().map(Pair::getFirst).collect(Collectors.toList());

        assertThat(ids).doesNotContain(PLAYER_ID_4);
    }

    private void prepareMoreGamesData() {
        PlayerDao player1 = preparePlayer(PLAYER_ID_1, "john", "wicks");
        PlayerDao player2 = preparePlayer(PLAYER_ID_2, "marek", "stes");
        PlayerDao player3 = preparePlayer(PLAYER_ID_3, "ervin", "got");

        createGame(player1, player2, "g111", 50, 55);
        createGame(player2, player1, "g112", 52, 50);
        createGame(player2, player3, "g113", 33, 30);
        createGame(player3, player1, "g114", 33, 42);
        createGame(player3, player1, "g115", 22, 41);
    }

    private void assertGameStatsWithPrecision(Map<String, Number> gameStats, String stastisticName, double expectedValue) {
        assertEquals(expectedValue, (double) gameStats.get(stastisticName), 0.001);
    }

    private void assertGameStats(Map<String, Number> gameStats, String stastisticName, Number expectedValue) {
        assertThat(gameStats.get(stastisticName)).isEqualTo(expectedValue);
    }

    private void createGame(PlayerDao player1, PlayerDao player2, String gameId, int score1, int score2) {
        GameDao game = new GameDao(gameId, player1, player2, score1, score2);
        gameRepository.save(game);
    }

    private PlayerDao preparePlayer(String id, String firstName, String lastName) {
        PlayerDao player1 = new PlayerDao(id, firstName, lastName);
        playerRepository.save(player1);
        return player1;
    }
}


