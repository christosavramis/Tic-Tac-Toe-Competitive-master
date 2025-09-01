package tttc.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Player extends AbstractEntity {
    private String name;

    private int games;

    private int wins;

    private int ties;

    private int loses;

    private String rank;

    private int elo;

    private boolean isBot;

//    transient fields are not saved in the database :)
    private transient Game currentGame;

    // Optional constructor for quick creation
    public Player(String name, String rank) {
        this.name = name;
        this.rank = rank;
    }
}
