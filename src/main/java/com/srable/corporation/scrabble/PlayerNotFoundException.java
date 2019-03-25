package com.srable.corporation.scrabble;

/**
 * Player wasn't found exception.
 * Created by lubomir.viluda on 3/23/2019.
 */
public class PlayerNotFoundException extends RuntimeException {

    /**
     * Create instance.
     * @param message
     */
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
