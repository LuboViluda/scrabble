package com.srable.corporation.scrabble.player.storage;

import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

/**
 * Custom queries for {@link PlayerRepository}.
 * Created by lubomir.viluda on 3/24/2019.
 */
public interface PlayerRepositoryCustom {
    /**
     * Players statistics in format stats name - value in map.
     * @param id
     * @return
     */
    Map<String, Number> getPlayerStatistics(String id);

    /**
     * LeadersBoard list, better avarage score on lower index. Returned as list of pairs id - double.
     * @param limit
     * @param gameMinimum
     * @return
     */
    List<Pair<String, Double>> getLeaderBoard(int limit, int gameMinimum);
}
