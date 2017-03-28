package com.github.gonzo17.db.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class MatchEntity {

    private int championId;
    private long createDate;
    @Id
    private long gameId;
    private String gameMode;
    private String gameType;
}