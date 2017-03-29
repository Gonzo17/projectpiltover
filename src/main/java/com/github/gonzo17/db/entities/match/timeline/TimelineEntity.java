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
public class TimelineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long frameInterval;

    @OneToMany(targetEntity = FrameEntity.class, cascade = CascadeType.ALL)
    private List<FrameEntity> frames;
}
