package com.github.gonzo17.db;

import com.github.gonzo17.db.entities.match.MatchEntity;
import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<MatchEntity, Long> {
}