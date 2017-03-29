package com.github.gonzo17.db.entities.match.summoner;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class SummonerIdentity {
    @Id
    private long summonerId;
    private String summonerName;

    private String matchHistoryUri;
    private int profileIcon;
}
