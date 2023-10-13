## Domain Analysis


State 1 (Game not started):
Starts with default dimensions (16x30), default mine number (99), and fixed 000 time. 

- Can change dimensions, after which the board will be altered along with the number of bombs, with a floored ratio of
0.2063 mines per cell.
- Therefore, only data that can be changed in this state is the dimensions
- The rest of the changing data will automatically follow the change in dimensions
- Each change in dimension will automatically resize board, calculate new number of bombs, and place the bombs randomly on baord

State 2 (Game Started):
Starts game when player clears a cell on board. Cell that is attempting to be cleared will either be:
- Not a bomb and successfully cleared, with no bombs in radius (hence cell value is 0). Game is started, and any nearby clear cells unveil. Time starts.
- There is one or more bombs either in selected cell or its radius. Moves all bombs in cell/radius to random viable cells. Now cell is successfully cleared. Game is started, and any nearby clear cells unveil. Time starts.


Now fixed data includes:

- Dimensions
- Board Layout

Changing data includes:

- Time
- Cleared cells
- Flagged cells
- Number of bombs remaining (aggording to flagged number)


Button Layout:
*time* **SAVE** **RESET** **DIMENSIONS** *unflagged bombs*