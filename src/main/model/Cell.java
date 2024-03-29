package model;

import org.json.JSONObject;
import persistence.Writable;

// holds data needed for a single minesweeper cell and has cell modifier methods
public class Cell implements Writable {
    private boolean isBomb;
    private boolean isClear;
    private boolean isFlagged;

    private int inRadius;

    // EFFECTS: creates blank cell;
    public Cell() {
        isBomb = false;
        isClear = false;
        isFlagged = false;
        inRadius = 0;
    }

    // EFFECTS: creates a fully formed cell with specified fields
    public Cell(boolean isBomb, boolean isClear, boolean isFlagged, int inRadius) {
        this.isBomb = isBomb;
        this.isClear = isClear;
        this.isFlagged = isFlagged;
        this.inRadius = inRadius;
    }

    // MODIFIES: this
    // EFFECTS: makes cell a bomb of not a bomb based on its previous state
    public void setBomb() {
        isBomb = !isBomb;
    }

    // REQUIRES: will not be called on bomb cells
    // MODIFIES: this
    // EFFECTS: Changes cell state to clear. Only works if cell is unflagged.
    public void clear() {
        if (!isFlagged) {
            isClear = true;
            EventLog.getInstance().logEvent(new Event("----- Clear Cell"));
        }
    }

    // MODIFIES: this
    // EFFECTS: Switches flagged state to opposite, returns change in amount of flags
    public int toggleFlag() {
        if (isFlagged) {
            isFlagged = false;
            EventLog.getInstance().logEvent(new Event("----- Un-Flag Cell"));
            return -1;
        } else {
            isFlagged = true;
            EventLog.getInstance().logEvent(new Event("----- Flag Cell"));
            return 1;
        }
    }

    // MODIFIES: this
    // EFFECTS: increases or decreases amount of bombs in radius by one
    public void incrementInRadius(Boolean increase) {
        if (increase) {
            inRadius++;
        } else {
            inRadius--;
        }
    }

    public boolean getIsBomb() {
        return isBomb;
    }

    public boolean getIsClear() {
        return isClear;
    }

    public boolean getIsFlagged() {
        return isFlagged;
    }

    public int getInRadius() {
        return inRadius;
    }

//####################################################################
//Json Setup
//####################################################################

    // EFFECTS: converts a cell to a json object and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("isBomb", isBomb);
        json.put("isClear", isClear);
        json.put("isFlagged", isFlagged);
        json.put("inRadius", inRadius);
        return json;
    }
}
