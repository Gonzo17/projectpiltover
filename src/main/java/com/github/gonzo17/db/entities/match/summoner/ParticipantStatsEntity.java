package com.github.gonzo17.db.entities.match.summoner;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class ParticipantStatsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int assists;
    private int champLevel;
    private int combatPlayerScore;
    private int deaths;
    private int doubleKills;
    private boolean firstBloodAssist;
    private boolean firstBloodKill;
    private boolean firstInhibitorAssist;
    private boolean firstInhibitorKill;
    private boolean firstTowerAssist;
    private boolean firstTowerKill;
    private int goldEarned;
    private int goldSpent;
    private int inhibitorKills;
    private int item0;
    private int item1;
    private int item2;
    private int item3;
    private int item4;
    private int item5;
    private int item6;
    private int killingSprees;
    private int kills;
    private int largestCriticalStrike;
    private int largestKillingSpree;
    private int largestMultiKill;
    private int magicDamageDealt;
    private int magicDamageDealtToChampions;
    private int magicDamageTaken;
    private int minionsKilled;
    private int neutralMinionsKilled;
    private int neutralMinionsKilledEnemyJungle;
    private int neutralMinionsKilledTeamJungle;
    private int nodeCapture;
    private int nodeCaptureAssist;
    private int nodeNeutralize;
    private int nodeNeutralizeAssist;
    private int objectivePlayerScore;
    private int pentaKills;
    private int physicalDamageDealt;
    private int physicalDamageDealtToChampions;
    private int physicalDamageTaken;
    private int quadraKills;
    private int sightWardsBoughtInGame;
    private int teamObjective;
    private int totalDamageDealt;
    private int totalDamageDealtToChampions;
    private int totalDamageTaken;
    private int totalHeal;
    private int totalPlayerScore;
    private int totalScoreRank;
    private int totalTimeCrowdControlDealt;
    private int totalUnitsHealed;
    private int towerKills;
    private int tripleKills;
    private int trueDamageDealt;
    private int trueDamageDealtToChampions;
    private int trueDamageTaken;
    private int unrealKills;
    private int visionWardsBoughtInGame;
    private int wardsKilled;
    private int wardsPlaced;
    private boolean winner;
}
