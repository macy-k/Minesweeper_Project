package model;

import org.json.JSONObject;
import persistence.Writable;

public class Log implements Writable {
    private Boolean incomplete;
    private Boolean won;
    private Integer score;
    private Integer time;
    private String stateString;

    // EFFECTS: creates a log with given game state determines human-readable string describing state
    public Log(boolean incomplete, boolean won, int score, int time) {
        this.incomplete = incomplete;
        this.won = won;
        this.score = score;
        this.time = time;
        if (incomplete) {
            stateString = "Incomplete";
        } else if (won) {
            stateString = "Won";
        } else {
            stateString = "Lost";
        }
    }

    public boolean getIncomplete() {
        return incomplete;
    }

    public boolean getWon() {
        return won;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getTime() {
        return time;
    }

    public String getStateString() {
        return stateString;
    }

    // EFFECTS: converts a Log into a json object and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("incomplete", incomplete);
        json.put("won", won);
        json.put("score", score);
        json.put("time", time);
        return json;
    }
}
