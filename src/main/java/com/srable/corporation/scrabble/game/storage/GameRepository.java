package com.srable.corporation.scrabble.game.storage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Represents standard CRUD repository.
 * Created by lubomir.viluda on 3/23/2019.
 */
@Repository
public interface GameRepository extends CrudRepository<GameDao, UUID> {

}
