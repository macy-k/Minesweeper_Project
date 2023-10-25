package model;

public class Log {
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
}
