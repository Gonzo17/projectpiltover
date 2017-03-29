package com.github.gonzo17.db.entities.match.summoner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class ParticipantIdentityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int participantId;
    @OneToOne(targetEntity = SummonerIdentityEntity.class, cascade = CascadeType.ALL)
    private SummonerIdentityEntity player;
}
