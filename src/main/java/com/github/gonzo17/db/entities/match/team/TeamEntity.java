package com.github.gonzo17.db.entities.match.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(targetEntity = BannedChampionEntity.class, cascade = CascadeType.ALL)
    private List<BannedChampionEntity> bans;
    private int baronKills;
    private int dominionVictoryScore;
    private int dragonKills;
    private boolean firstBaron;
    private boolean firstBlood;
    private boolean firstDragon;
    private boolean firstInhibitor;
    private boolean firstRiftHerald;
    private boolean firstTower;
    private int inhibitorKills;
    private int riftHeraldKills;
    private int teamId;
    private int towerKills;
    private int vilemawKills;
    private boolean winner;
}
