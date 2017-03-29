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
public class FrameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(targetEntity = EventEntity.class, cascade = CascadeType.ALL)
    private List<EventEntity> events;
    @OneToMany(targetEntity = ParticipantFrameEntity.class, cascade = CascadeType.ALL)
    private List<ParticipantFrameEntity> participantFrames;
    private long timestamp;
}
