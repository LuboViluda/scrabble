package com.srable.corporation.scrabble.player;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Represents internal player representation.
 * Created by lubomir.viluda on 3/24/2019.
 */
public final class Player {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final Map<String, Number> playerStatistics;

    private Player(Builder builder) {
        id = builder.id;
        firstName = builder.firstName;
        lastName = builder.lastName;
        playerStatistics = builder.playerStatistics;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Map<String, Number> getPlayerStatistics() {
        return playerStatistics;
    }

    public static final class Builder {
        private String id;
        private String firstName;
        private String lastName;
        private Map<String, Number> playerStatistics;

        public Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withPlayerStatistics(Map<String, Number> playerStatistics) {
            this.playerStatistics = ImmutableMap.copyOf(playerStatistics);
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }
}
