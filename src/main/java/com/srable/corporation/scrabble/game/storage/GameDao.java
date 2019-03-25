package com.srable.corporation.scrabble.game.storage;

import com.srable.corporation.scrabble.player.storage.PlayerDao;

import javax.persistence.*;
import java.util.UUID;

/**
 * Represents game db entity, has reference to player1 and player2 (mapped as ID) in db. Reference on the other
 * side is omitted to break many to many relationship.
 * Created by lubomir.viluda on 3/23/2019.
 */
@Entity
@Table(name = "Game")
public class GameDao {
    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "player_id1")
    private PlayerDao player1;

    @OneToOne
    @JoinColumn(name="player_id2")
    private PlayerDao player2;

    private double score1;
    private double score2;

    /**
     * Creates instance.
     */
    public GameDao() {
    }

    /**
     * Creates instance.
     * @param id
     * @param player1
     * @param player2
     * @param score1
     * @param score2
     */
    public GameDao(String id, PlayerDao player1, PlayerDao player2, double score1, double score2) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.score1 = score1;
        this.score2 = score2;
    }
}
