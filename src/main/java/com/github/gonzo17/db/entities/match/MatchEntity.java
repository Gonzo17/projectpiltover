package com.github.gonzo17.db.entities.match;

import com.github.gonzo17.db.entities.match.summoner.ParticipantEntity;
import com.github.gonzo17.db.entities.match.summoner.ParticipantIdentityEntity;
import com.github.gonzo17.db.entities.match.team.TeamEntity;
import com.github.gonzo17.db.entities.match.timeline.TimelineEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class MatchEntity {

    private int mapId;
    private long matchCreation;
    private long matchDuration;
    @Id
    private long matchId;
    private String matchMode;
    private String matchType;
    private String matchVersion;
    @OneToMany(targetEntity = ParticipantIdentityEntity.class, cascade = CascadeType.ALL)
    private List<ParticipantIdentityEntity> participantIdentities;
    @OneToMany(targetEntity = ParticipantEntity.class, cascade = CascadeType.ALL)
    private List<ParticipantEntity> participants;
    private String platformId;
    private String queueType;
    private String region;
    private String season;
    @OneToMany(targetEntity = TeamEntity.class, cascade = CascadeType.ALL)
    private List<TeamEntity> teams;
    @OneToOne(targetEntity = TimelineEntity.class, cascade = CascadeType.ALL)
    private TimelineEntity timeline;
}