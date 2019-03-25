package com.srable.corporation.scrabble.player.storage;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lubomir.viluda on 3/24/2019.
 */
@Repository
public class PlayerRepositoryImpl implements PlayerRepositoryCustom {
    private static final String GET_WINS_NUMBER_QUERY = "SELECT COUNT(*) FROM GAME " +
            "WHERE (PLAYER_ID1=:id AND (SCORE1 > SCORE2)) OR (PLAYER_ID2=:id AND (SCORE2 > SCORE1));";
    private static final String GET_LOSSES_NUMBER_QUERY = "SELECT COUNT(*) FROM GAME " +
            "WHERE (PLAYER_ID1=:id AND (SCORE1 < SCORE2)) OR (PLAYER_ID2=:id AND (SCORE2 < SCORE1));";
    private static final String GET_AVERAGE_SCORE_QUERY = "SELECT  AVG(CASE WHEN PLAYER_ID1=:id THEN SCORE1 " +
            "ELSE SCORE2 END) from GAME WHERE (PLAYER_ID1=:id OR PLAYER_ID2=:id)";
    private static final String GET_LEADERBOARD_QUERY = "SELECT PLAYER_ID1, AVG(SCORE1) FROM (SELECT  PLAYER_ID1, " +
            "SCORE1 FROM game UNION ALL (SELECT PLAYER_ID2, SCORE2 FROM GAME))" +
            " GROUP BY PLAYER_ID1 HAVING (COUNT(PLAYER_ID1)) >= :minimumGame  ORDER BY AVG(SCORE1) DESC LIMIT :limit";

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Map<String, Number> getPlayerStatistics(String id) {
        Map<String, Number> stats = new HashMap<>();
        Query query = entityManager.createNativeQuery(GET_WINS_NUMBER_QUERY);
        query.setParameter("id", id.replace("-", ""));
        stats.put("wins", getResultAsInt(query));

        query = entityManager.createNativeQuery(GET_LOSSES_NUMBER_QUERY);
        query.setParameter("id", id.replace("-", ""));
        stats.put("losses", getResultAsInt(query));

        query = entityManager.createNativeQuery(GET_AVERAGE_SCORE_QUERY);
        query.setParameter("id", id.replace("-", ""));
        stats.put("avgScore", getResultAsDouble(query));

        return stats;
    }

    @Override
    public List<Pair<String, Double>> getLeaderBoard(int limit, int gameMinimum) {
        Query query = entityManager.createNativeQuery(GET_LEADERBOARD_QUERY);
        query.setParameter("limit", limit);
        query.setParameter("minimumGame", gameMinimum);

        List queryResult = query.getResultList();
        List<Pair<String, Double>> leaderboard = new ArrayList<>();
        for (int i = 0; i < queryResult.size(); i++) {
            Object[] manager = (Object[]) queryResult.get(i);
            String id = String.valueOf(manager[0]);
            Double o1 = (Double) manager[1];
            leaderboard.add(Pair.of(id, o1));
        }
        return leaderboard;
    }

    private int getResultAsInt(Query query) {
        return query.getSingleResult() != null ? ((BigInteger) query.getSingleResult()).intValue() : 0;
    }

    private double getResultAsDouble(Query query) {
        Number singleResult = (Number) query.getSingleResult();
        return singleResult != null ? singleResult.doubleValue() : 0.0;
    }
}
