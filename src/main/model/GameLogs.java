package model;

import java.util.ArrayList;
import java.util.List;

public class GameLogs {
    private final List<Log> gameLogs;
    private Integer won;
    private Integer lost;

    // EFFECTS: initializes gameLogs with no logs and with no won or lost games
    public GameLogs() {
        gameLogs = new ArrayList<>();
        won = 0;
        lost = 0;
    }

    // MODIFIES: this
    // EFFECTS: adds a log to the gameLogs and changes won and lost variables according to given log
    public void addLog(Log l) {
        gameLogs.add(l);
        if (!l.getIncomplete()) {
            if (l.getWon()) {
                won++;
            } else {
                lost++;
            }
        }
    }

    // EFFECTS: returns string of all game logs in human-readable form, ready for printing
    public String printGameLogs() {
        if (gameLogs.isEmpty()) {
            return "No Logs";
        }
        StringBuilder sb = new StringBuilder();
        for (Log log : gameLogs) {
            sb.append("Score:" + log.getScore() + " -- Time:" + log.getTime() + " -- " + log.getStateString() + "\n");
        }
        sb.append("Games Finished:" + (getLost() + getWon()) + " -- Won:" + getWon()
                +  " -- Win Rate:" + getWinRate() + "%");
        return sb.toString();
    }

    // EFFECTS: returns integer representing the player's win rate percentage
    public Integer getWinRate() {
        if ((lost + won) != 0) {
            return (won * 100 / (lost + won));
        } else {
            return 0;
        }
    }

    public List<Log> getGameLogs() {
        return gameLogs;
    }

    public Integer getWon() {
        return won;
    }

    public Integer getLost() {
        return lost;
    }

}
