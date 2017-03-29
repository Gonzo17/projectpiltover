package com.github.gonzo17.db.entities.match.timeline;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class ParticipantFrameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int currentGold;
    private int dominionScore;
    private int jungleMinionsKilled;
    private int level;
    private int minionsKilled;
    private int participantId;
    private int positionX;
    private int positionY;
    private int teamScore;
    private int totalGold;
    private int xp;
}
