package de.gonzo;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GameEntity {

    private int championId;
    private long createDate;
    @Id
    private long gameId;
    private String gameMode;
    private String gameType;
}