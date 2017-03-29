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
public class SummonerIdentityEntity {
    @Id
    private long summonerId;
    private String summonerName;
    private int profileIcon;

    /** From @{@link net.rithms.riot.api.endpoints.match.dto.Player} object. **/
    private String matchHistoryUri;

    /** From @{@link net.rithms.riot.api.endpoints.summoner.dto.Summoner} object. **/
    private long revisionDate;
    private int summonerLevel;

    /** Our internal flag to determine if we should watch the games of this summoner. **/
    @Setter
    private boolean watch;
}
