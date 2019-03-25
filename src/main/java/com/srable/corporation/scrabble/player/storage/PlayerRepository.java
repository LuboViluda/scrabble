package com.srable.corporation.scrabble.player.storage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Player CRUD repository with custom query defined in {@link PlayerRepositoryCustom}.
 * Created by lubomir.viluda on 3/23/2019.
 */
@Repository
public interface PlayerRepository extends CrudRepository<PlayerDao, String>, PlayerRepositoryCustom {

}
