package com.github.gonzo17.db.entities.match.timeline;

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
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String ascendedType;
    @ElementCollection
    private List<Integer> assistingParticipantIds;
    private String buildingType;
    private int creatorId;
    private String eventType;
    private int itemAfter;
    private int itemBefore;
    private int itemId;
    private int killerId;
    private String laneType;
    private String levelUpType;
    private String monsterSubType;
    private String monsterType;
    private int participantId;
    private String pointCaptured;
    private int positionX;
    private int positionY;
    private int skillSlot;
    private int teamId;
    private long timestamp;
    private String towerType;
    private int victimId;
    private String wardType;
}
