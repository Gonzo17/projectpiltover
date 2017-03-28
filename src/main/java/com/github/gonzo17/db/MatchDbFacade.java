package com.github.gonzo17.db;

import com.github.gonzo17.db.entities.MatchEntity;

public interface MatchDbFacade {
    void saveMatch(MatchEntity gameEntity);
}
