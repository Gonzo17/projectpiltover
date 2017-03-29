package com.github.gonzo17.mapper;

import com.github.gonzo17.db.SummonerDbFacade;
import com.github.gonzo17.db.entities.match.MatchEntity;
import com.github.gonzo17.db.entities.match.summoner.*;
import com.github.gonzo17.db.entities.match.team.BannedChampionEntity;
import com.github.gonzo17.db.entities.match.team.TeamEntity;
import com.github.gonzo17.db.entities.match.timeline.EventEntity;
import com.github.gonzo17.db.entities.match.timeline.FrameEntity;
import com.github.gonzo17.db.entities.match.timeline.ParticipantFrameEntity;
import com.github.gonzo17.db.entities.match.timeline.TimelineEntity;
import net.rithms.riot.api.endpoints.match.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Component
public class MatchMapper {

    @Autowired
    private SummonerDbFacade summonerDbFacade;

    public MatchEntity mapMatchDetailToMatchEntity(MatchDetail matchDetail) {
        return MatchEntity.builder()
                .mapId(matchDetail.getMapId())
                .matchCreation(matchDetail.getMatchCreation())
                .matchDuration(matchDetail.getMatchDuration())
                .matchId(matchDetail.getMatchId())
                .matchMode(matchDetail.getMatchMode())
                .matchType(matchDetail.getMatchType())
                .matchVersion(matchDetail.getMatchVersion())
                .participantIdentities(matchDetail.getParticipantIdentities().stream().map(this::toEntity).collect(Collectors.toList()))
                .participants(matchDetail.getParticipants().stream().map(this::toEntity).collect(Collectors.toList()))
                .platformId(matchDetail.getPlatformId())
                .queueType(matchDetail.getQueueType())
                .region(matchDetail.getRegion())
                .season(matchDetail.getSeason())
                .teams(matchDetail.getTeams().stream().map(this::toEntity).collect(Collectors.toList()))
                .timeline(toEntity(matchDetail.getTimeline()))
                .build();
    }

    private ParticipantIdentityEntity toEntity(ParticipantIdentity participantIdentity) {
        if (participantIdentity == null) {
            return null;
        }
        return ParticipantIdentityEntity.builder()
                .participantId(participantIdentity.getParticipantId())
                .player(toEntity(participantIdentity.getPlayer()))
                .build();
    }

    private ParticipantEntity toEntity(Participant participant) {
        if (participant == null) {
            return null;
        }
        return ParticipantEntity.builder()
                .championId(participant.getChampionId())
                .highestAchievedSeasonTier(participant.getHighestAchievedSeasonTier())
                .masteries(participant.getMasteries().stream().map(this::toEntity).collect(Collectors.toList()))
                .participantId(participant.getParticipantId())
                .runes(runeList(participant))
                .spell1Id(participant.getSpell1Id())
                .spell2Id(participant.getSpell2Id())
                .stats(toEntity(participant.getStats()))
                .teamId(participant.getTeamId())
                .timeline(toEntity(participant.getTimeline()))
                .build();
    }

    private List<RuneEntity> runeList(Participant participant) {
        if (participant.getRunes() == null) {
            return emptyList();
        }
        return participant.getRunes().stream().map(this::toEntity).collect(Collectors.toList());
    }


    private TeamEntity toEntity(Team team) {
        if (team == null) {
            return null;
        }
        return TeamEntity.builder()
                .bans(toEntity(team.getBans()))
                .baronKills(team.getBaronKills())
                .dominionVictoryScore(team.getDominionVictoryScore())
                .dragonKills(team.getDragonKills())
                .firstBaron(team.isFirstBaron())
                .firstBlood(team.isFirstBlood())
                .firstDragon(team.isFirstDragon())
                .firstInhibitor(team.isFirstInhibitor())
                .firstRiftHerald(team.isFirstRiftHerald())
                .firstTower(team.isFirstTower())
                .inhibitorKills(team.getInhibitorKills())
                .riftHeraldKills(team.getRiftHeraldKills())
                .teamId(team.getTeamId())
                .towerKills(team.getTowerKills())
                .vilemawKills(team.getVilemawKills())
                .winner(team.isWinner())
                .build();
    }

    private List<BannedChampionEntity> toEntity(List<BannedChampion> bannedChampion) {
        if (bannedChampion == null) {
            return emptyList();
        }
        return bannedChampion.stream().map(this::toEntity).collect(Collectors.toList());
    }

    private BannedChampionEntity toEntity(BannedChampion bannedChampion) {
        if (bannedChampion == null) {
            return null;
        }
        return BannedChampionEntity.builder()
                .championId(bannedChampion.getChampionId())
                .pickTurn(bannedChampion.getPickTurn())
                .build();
    }

    private TimelineEntity toEntity(Timeline timeline) {
        if (timeline == null) {
            return null;
        }
        return TimelineEntity.builder()
                .frameInterval(timeline.getFrameInterval())
                .frames(timeline.getFrames().stream().map(this::toEntity).collect(Collectors.toList()))
                .build();
    }

    private FrameEntity toEntity(Frame frame) {
        if (frame == null) {
            return null;
        }
        return FrameEntity.builder()
                .events(frame.getEvents().stream().map(this::toEntity).collect(Collectors.toList()))
                .participantFrames(toEntity(frame.getParticipantFrames()))
                .timestamp(frame.getTimestamp())
                .build();
    }

    private EventEntity toEntity(Event event) {
        if (event == null) {
            return null;
        }
        return EventEntity.builder()
                .ascendedType(event.getAscendedType())
                .assistingParticipantIds(event.getAssistingParticipantIds())
                .buildingType(event.getBuildingType())
                .creatorId(event.getCreatorId())
                .eventType(event.getEventType())
                .itemAfter(event.getItemAfter())
                .itemBefore(event.getItemBefore())
                .itemId(event.getItemId())
                .killerId(event.getKillerId())
                .laneType(event.getLaneType())
                .levelUpType(event.getLevelUpType())
                .monsterSubType(event.getMonsterSubType())
                .monsterType(event.getMonsterType())
                .participantId(event.getParticipantId())
                .pointCaptured(event.getPointCaptured())
                .positionX(event.getPosition().getX())
                .positionY(event.getPosition().getY())
                .skillSlot(event.getSkillSlot())
                .teamId(event.getTeamId())
                .timestamp(event.getTimestamp())
                .towerType(event.getTowerType())
                .victimId(event.getVictimId())
                .wardType(event.getWardType())
                .build();
    }

    private List<ParticipantFrameEntity> toEntity(Map<String, ParticipantFrame> participantFrameMap) {
        return participantFrameMap.values().stream().map(this::toEntity).collect(Collectors.toList());
    }

    private ParticipantFrameEntity toEntity(ParticipantFrame participantFrame) {
        if (participantFrame == null) {
            return null;
        }
        return ParticipantFrameEntity.builder()
                .currentGold(participantFrame.getCurrentGold())
                .dominionScore(participantFrame.getDominionScore())
                .jungleMinionsKilled(participantFrame.getJungleMinionsKilled())
                .level(participantFrame.getLevel())
                .minionsKilled(participantFrame.getMinionsKilled())
                .participantId(participantFrame.getParticipantId())
                .positionX(participantFrame.getPosition().getX())
                .positionY(participantFrame.getPosition().getY())
                .teamScore(participantFrame.getTeamScore())
                .totalGold(participantFrame.getTotalGold())
                .xp(participantFrame.getXp())
                .build();
    }

    private SummonerIdentityEntity toEntity(Player player) {
        if (player == null) {
            return null;
        }

        if(summonerDbFacade.exists(player.getSummonerId())){
            return summonerDbFacade.getSummonerById(player.getSummonerId());
        }

        return SummonerIdentityEntity.builder()
                .summonerId(player.getSummonerId())
                .summonerName(player.getSummonerName())
                .matchHistoryUri(player.getMatchHistoryUri())
                .profileIcon(player.getProfileIcon())
                .build();
    }

    private MasteryEntity toEntity(Mastery mastery) {
        if (mastery == null) {
            return null;
        }
        return MasteryEntity.builder()
                .masteryId(mastery.getMasteryId())
                .rank(mastery.getRank())
                .build();
    }

    private RuneEntity toEntity(Rune rune) {
        if (rune == null) {
            return null;
        }
        return RuneEntity.builder()
                .runeId(rune.getRuneId())
                .rank(rune.getRank())
                .build();
    }

    private ParticipantStatsEntity toEntity(ParticipantStats stats) {
        if (stats == null) {
            return null;
        }
        return ParticipantStatsEntity.builder()
                .assists(stats.getAssists())
                .champLevel(stats.getChampLevel())
                .combatPlayerScore(stats.getCombatPlayerScore())
                .deaths(stats.getDeaths())
                .doubleKills(stats.getDoubleKills())
                .firstBloodAssist(stats.isFirstBloodAssist())
                .firstBloodKill(stats.isFirstBloodKill())
                .firstInhibitorAssist(stats.isFirstInhibitorAssist())
                .firstInhibitorKill(stats.isFirstInhibitorKill())
                .firstTowerAssist(stats.isFirstTowerAssist())
                .firstTowerKill(stats.isFirstTowerKill())
                .goldEarned(stats.getGoldEarned())
                .goldSpent(stats.getGoldSpent())
                .inhibitorKills(stats.getInhibitorKills())
                .item0(stats.getItem0())
                .item1(stats.getItem1())
                .item2(stats.getItem2())
                .item3(stats.getItem3())
                .item4(stats.getItem4())
                .item5(stats.getItem5())
                .item6(stats.getItem6())
                .killingSprees(stats.getKillingSprees())
                .kills(stats.getKills())
                .largestCriticalStrike(stats.getLargestCriticalStrike())
                .largestKillingSpree(stats.getLargestKillingSpree())
                .largestMultiKill(stats.getLargestMultiKill())
                .magicDamageDealt(stats.getMagicDamageDealt())
                .magicDamageDealtToChampions(stats.getMagicDamageDealtToChampions())
                .magicDamageTaken(stats.getMagicDamageTaken())
                .minionsKilled(stats.getMinionsKilled())
                .neutralMinionsKilled(stats.getNeutralMinionsKilled())
                .neutralMinionsKilledEnemyJungle(stats.getNeutralMinionsKilledEnemyJungle())
                .neutralMinionsKilledTeamJungle(stats.getNeutralMinionsKilledTeamJungle())
                .nodeCapture(stats.getNodeCapture())
                .nodeCaptureAssist(stats.getNodeCaptureAssist())
                .nodeNeutralize(stats.getNodeNeutralize())
                .nodeNeutralizeAssist(stats.getNodeNeutralizeAssist())
                .objectivePlayerScore(stats.getObjectivePlayerScore())
                .pentaKills(stats.getPentaKills())
                .physicalDamageDealt(stats.getPhysicalDamageDealt())
                .physicalDamageDealtToChampions(stats.getPhysicalDamageDealtToChampions())
                .physicalDamageTaken(stats.getPhysicalDamageTaken())
                .quadraKills(stats.getQuadraKills())
                .sightWardsBoughtInGame(stats.getSightWardsBoughtInGame())
                .teamObjective(stats.getTeamObjective())
                .totalDamageDealt(stats.getTotalDamageDealt())
                .totalDamageDealtToChampions(stats.getTotalDamageDealtToChampions())
                .totalDamageTaken(stats.getTotalDamageTaken())
                .totalHeal(stats.getTotalHeal())
                .totalPlayerScore(stats.getTotalPlayerScore())
                .totalScoreRank(stats.getTotalScoreRank())
                .totalTimeCrowdControlDealt(stats.getTotalTimeCrowdControlDealt())
                .totalUnitsHealed(stats.getTotalUnitsHealed())
                .towerKills(stats.getTowerKills())
                .tripleKills(stats.getTripleKills())
                .trueDamageDealt(stats.getTrueDamageDealt())
                .trueDamageDealtToChampions(stats.getTrueDamageDealtToChampions())
                .trueDamageTaken(stats.getTrueDamageTaken())
                .unrealKills(stats.getUnrealKills())
                .visionWardsBoughtInGame(stats.getVisionWardsBoughtInGame())
                .wardsKilled(stats.getWardsKilled())
                .wardsPlaced(stats.getWardsPlaced())
                .winner(stats.isWinner())
                .build();
    }

    private ParticipantTimelineEntity toEntity(ParticipantTimeline timeline) {
        if (timeline == null) {
            return null;
        }
        return ParticipantTimelineEntity.builder()
                .ancientGolemAssistsPerMinCounts(toEntity(timeline.getAncientGolemAssistsPerMinCounts()))
                .ancientGolemKillsPerMinCounts(toEntity(timeline.getAncientGolemKillsPerMinCounts()))
                .assistedLaneDeathsPerMinDeltas(toEntity(timeline.getAssistedLaneDeathsPerMinDeltas()))
                .assistedLaneKillsPerMinDeltas(toEntity(timeline.getAssistedLaneKillsPerMinDeltas()))
                .baronAssistsPerMinCounts(toEntity(timeline.getBaronAssistsPerMinCounts()))
                .baronKillsPerMinCounts(toEntity(timeline.getBaronKillsPerMinCounts()))
                .creepsPerMinDeltas(toEntity(timeline.getCreepsPerMinDeltas()))
                .csDiffPerMinDeltas(toEntity(timeline.getCsDiffPerMinDeltas()))
                .damageTakenDiffPerMinDeltas(toEntity(timeline.getDamageTakenDiffPerMinDeltas()))
                .damageTakenPerMinDeltas(toEntity(timeline.getDamageTakenPerMinDeltas()))
                .dragonAssistsPerMinCounts(toEntity(timeline.getDragonAssistsPerMinCounts()))
                .dragonKillsPerMinCounts(toEntity(timeline.getDragonKillsPerMinCounts()))
                .elderLizardAssistsPerMinCounts(toEntity(timeline.getElderLizardAssistsPerMinCounts()))
                .elderLizardKillsPerMinCounts(toEntity(timeline.getElderLizardKillsPerMinCounts()))
                .goldPerMinDeltas(toEntity(timeline.getGoldPerMinDeltas()))
                .inhibitorAssistsPerMinCounts(toEntity(timeline.getInhibitorAssistsPerMinCounts()))
                .inhibitorKillsPerMinCounts(toEntity(timeline.getInhibitorKillsPerMinCounts()))
                .lane(timeline.getLane())
                .role(timeline.getRole())
                .towerAssistsPerMinCounts(toEntity(timeline.getTowerAssistsPerMinCounts()))
                .towerKillsPerMinCounts(toEntity(timeline.getTowerKillsPerMinCounts()))
                .towerKillsPerMinDeltas(toEntity(timeline.getTowerKillsPerMinDeltas()))
                .vilemawAssistsPerMinCounts(toEntity(timeline.getVilemawAssistsPerMinCounts()))
                .vilemawKillsPerMinCounts(toEntity(timeline.getVilemawKillsPerMinCounts()))
                .wardsPerMinDeltas(toEntity(timeline.getWardsPerMinDeltas()))
                .xpDiffPerMinDeltas(toEntity(timeline.getXpDiffPerMinDeltas()))
                .xpPerMinDeltas(toEntity(timeline.getXpPerMinDeltas()))
                .build();
    }

    private ParticipantTimelineDataEntity toEntity(ParticipantTimelineData timelineData) {
        if (timelineData == null) {
            return null;
        }
        return ParticipantTimelineDataEntity.builder()
                .tenToTwenty(timelineData.getTenToTwenty())
                .thirtyToEnd(timelineData.getThirtyToEnd())
                .twentyToThirty(timelineData.getTwentyToThirty())
                .zeroToTen(timelineData.getZeroToTen())
                .build();
    }
}
