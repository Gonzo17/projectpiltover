package com.github.gonzo17.db.entities.match.summoner;

import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class ParticipantTimelineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity ancientGolemAssistsPerMinCounts;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity ancientGolemKillsPerMinCounts;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity assistedLaneDeathsPerMinDeltas;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity assistedLaneKillsPerMinDeltas;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity baronAssistsPerMinCounts;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity baronKillsPerMinCounts;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity creepsPerMinDeltas;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity csDiffPerMinDeltas;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity damageTakenDiffPerMinDeltas;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity damageTakenPerMinDeltas;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity dragonAssistsPerMinCounts;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity dragonKillsPerMinCounts;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity elderLizardAssistsPerMinCounts;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity elderLizardKillsPerMinCounts;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity goldPerMinDeltas;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity inhibitorAssistsPerMinCounts;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity inhibitorKillsPerMinCounts;
    private String lane;
    private String role;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity towerAssistsPerMinCounts;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity towerKillsPerMinCounts;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity towerKillsPerMinDeltas;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity vilemawAssistsPerMinCounts;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity vilemawKillsPerMinCounts;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity wardsPerMinDeltas;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity xpDiffPerMinDeltas;
    @OneToOne(targetEntity = ParticipantTimelineDataEntity.class, cascade = CascadeType.ALL)
    private ParticipantTimelineDataEntity xpPerMinDeltas;
}
