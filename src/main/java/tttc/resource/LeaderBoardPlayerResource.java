package tttc.resource;

import lombok.Data;

@Data
public class LeaderBoardPlayerResource {
    private String name;
    private String rank;
    private String games;
    private String wins;
    private String ties;
    private String loses;
}
