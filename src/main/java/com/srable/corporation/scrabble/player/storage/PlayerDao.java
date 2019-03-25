package com.srable.corporation.scrabble.player.storage;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents Player database entity.
 * Created by lubomir.viluda on 3/23/2019.
 */
@Entity
@Table(name = "Player")
public class PlayerDao {
    @Id
    private String id;
    private String firstName;
    private String lastName;

    public PlayerDao() {
    }

    public PlayerDao(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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
}
