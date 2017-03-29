package com.github.gonzo17.db;

import com.github.gonzo17.db.entities.match.MatchEntity;

public interface MatchDbFacade {
    void saveMatch(MatchEntity gameEntity);

    boolean hasMatchWithId(long gameId);
}
