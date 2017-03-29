package com.github.gonzo17.db.entities.match.summoner;

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
public class ParticipantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int championId;
    private String highestAchievedSeasonTier;
    @OneToMany(targetEntity = MasteryEntity.class, cascade = CascadeType.ALL)
    private List<MasteryEntity> masteries;
    private int participantId;
    @OneToMany(targetEntity = RuneEntity.class, cascade = CascadeType.ALL)
    private List<RuneEntity> runes;
    private int spell1Id;
    private int spell2Id;
    @OneToOne(targetEntity = ParticipantStatsEntity.class, cascade = CascadeType.ALL)
    private ParticipantStatsEntity stats;
    private int teamId;
    @OneToOne(targetEntity = ParticipantTimelineEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineEntity timeline;
}
