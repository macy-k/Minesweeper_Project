package model;

public class Cell {
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

    // MODIFIES: this
    // EFFECTS: makes cell a bomb of not a bomb based on its previous state
    public void setBomb() {
        isBomb = !isBomb;
    }

    // REQUIRES: will not be called on bomb cells
    // MODIFIES: this

    // EFFECTS: Changes cell state to clear. Only works if cell is unflagged.s
    public void clear() {
        if (!isFlagged) {
            isClear = true;
        }
    }

    // MODIFIES: this
    // EFFECTS: Switches flagged state to opposite, returns change in amount of flags
    public int toggleFlag() {
        if (isFlagged) {
            isFlagged = false;
            return -1;
        } else {
            isFlagged = true;
            return 1;
        }
    }

    // MODIFIES: this
    // EFFECTS: increases amount of bombs in radius by one
    public void increaseInRadius() {
        inRadius++;
    }

    // MODIFIES: this
    // EFFECTS: decreases amount of bombs in radius by one
    public void decreaseInRadius() {
        inRadius--;
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
}
