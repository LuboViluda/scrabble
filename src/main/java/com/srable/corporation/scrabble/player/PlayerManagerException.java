package com.srable.corporation.scrabble.player;

/**
 * Custom exception, signaling problems during operations in {@link PlayerManager}.
 * Created by lubomir.viluda on 3/24/2019.
 */
public class PlayerManagerException extends RuntimeException {

    /**
     * Create instance.
     * @param message
     */
    public PlayerManagerException(String message) {
        super(message);
    }
}
