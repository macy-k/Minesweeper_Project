package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// holds all Log data for previous games and has methods operating on that data. Uses Singleton Pattern
public class GameLogs implements Writable {
    private static GameLogs theLog;
    private List<Log> gameLogs;
    private Integer won;
    private Integer lost;

    // EFFECTS: internal construction for gameLogs with no logs and with no won or lost games
    private GameLogs() {
        gameLogs = new ArrayList<>();
        won = 0;
        lost = 0;
    }

    // MODIFIES: this
    // EFFECTS: Gets instance of EventLog and creates it if it doesn't already exist.
    public static GameLogs getInstance() {
        if (theLog == null) {
            theLog = new GameLogs();
        }

        return theLog;
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

    // MODIFIES: this
    // EFFECTS: removes all current logs
    public void clearLogs() {
        EventLog.getInstance().logEvent(new Event("Reset Game Logs"));
        gameLogs = new ArrayList<>();
        won = 0;
        lost = 0;
    }

    // EFFECTS: returns string of all game logs in human-readable form, ready for printing
    public String printGameLogs() {
        EventLog.getInstance().logEvent(new Event("Show Game Logs"));
        if (gameLogs.isEmpty()) {
            return "No Logs\n";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Games Finished:" + (getLost() + getWon()) + " -- Won:" + getWon()
                +  " -- Win Rate:" + getWinRate() + "%\n\n");
        for (Log log : gameLogs) {
            sb.append("Score:" + log.getScore() + " -- Time:" + log.getTime() + " -- " + log.getStateString() + "\n");
        }
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

//####################################################################
//Json Setup
//####################################################################

    // EFFECTS: converts a GameLogs into a json object and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("gameLogs", logToJson());
        return json;
    }

    // EFFECTS: converts an array of Logs into a json array and returns it
    private JSONArray logToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Log l : gameLogs) {
            jsonArray.put(l.toJson());
        }
        return jsonArray;
    }
}
