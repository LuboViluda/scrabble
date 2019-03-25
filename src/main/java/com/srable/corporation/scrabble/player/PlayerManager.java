package com.srable.corporation.scrabble.player;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Player manager interface, for crud and statistical operation with players.
 * Created by lubomir.viluda on 3/24/2019.
 */
public interface PlayerManager {
    List<Player> getPlayers();

    Optional<Player> getPlayer(String id);

    Map<String, Number> getPlayersStatistics(String id);

    List<Player> getLeadersBoard();
}
