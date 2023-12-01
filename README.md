# My Personal Project
## Java Minesweeper

I want to make a minesweeper game where:
- There is a ui window with a standard grid of 30x16 cells, a mine count, a timer, 
        a reset button, a save button, a load button, and game-dimension selector.
- *YOU CAN CHOOSE DIMENSIONS OF GAME* in the game-dimension selector, so there can be **an arbitrary number of cells**.
- All cells will be blank at the start until first user input that starts timer.
- The first click will be guaranteed safe by removing any bombs in the vicinity of your first click and moving them
    to a random free spot on the board (that isn't also in the initial vicinity). This clears an initial area
- From there, aside from initial area, bombs will be randomly distributed throughout remaining cells.
        Then the remaining non-bomb cells will be designated a number based on how many bombs are nearby.
- Timer will start to tick up. Save button will now be able so save game state, so you can exit the application 
        and come back. Reset button can reset game to initial state.
- Hovering the mouse over an uncleared cell and pressing `space` will flag that cell as a bomb on the ui.
- Hovering the mouse over an uncleared cell and `left clicking` will attempt to clear that cell
- Hovering the mouse over a cleared cell and pressing `space` will attempt to clear all uncleared cells 
      around it *IF* there is the correct number of flagged bombs in the vicinity.
- If you attempt to clear a cell that has no bomb in it, it works. Otherwise, if there is a bomb the game ends,
        the ui displays all the bombs and stops the timer.

I get really ambitious I want to try making an auto-solver, but that will be done on my own time and not for this project.

I would also like to have a theme selection panel where you can choose from a set of color themes
  for the minesweeper game. Again, this will probably be on my own time.

**NOTE**: this is not going to work very well as a console based application, and selecting which cell
to interact with will be clunky. Same with selecting dimensions.

_________

People will use this whenever they want a fun, quick, simple game to distract themselves and/or pass time.

This project is of interest to me because I love using minesweeper as a quick distraction and I have an app for
it on my phone. I'm curious to see if I can design and implement the game myself, and with the save function
I could even play it on my laptop. I also want to see if I can design an auto solver for it, and I don't particularly
want to interact with the HTML element of the game online, so this is a good opportunity to just make it myself.

_________

## User Stories

### Phase 1:
- as a user, I want to be able to choose how many cells are in my game by picking the dimensions of my game 
(This arbitrary, since even prime numbers can be chosen by selecting [prime x 1] as a dimension)
- as a user, I want to click my first cell, clearing it and not dying immediately
- as a user, I want to see all the bomb tiles when I die (I think this counts as viewing a list of items?)
- as a user, I want to see how many bombs there are left
- as a user, I want to see how long I've spent on my current game
- as a user, I want cleared cells to be color coded

### Phase 2:
- as a user, I want to be able to save my current, incomplete game (game has to be started)
- as a user, I want to be able to load in and play my saved game from the console menu. The timer should resume automatically
- as a user, I want the game logs to automatically persist forever and continue to update with each game (autosave)
EXTRA
- as a user I want the board to automatically clear all 0 cells that are connected to clear cells.
- as a user I want all my complete and incomplete games to be stored in a game log which I can view
- as a user I want to see the number of games I've finished, the number of games I've won, and my win rate when I view my game logs.
- as a user I want the game panel to show me my score and indicate whether a game is incomplete, won, or lost after the game is finished.

### Phase 3:
**Instructions for Grader**
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by changing the dimensions of the board
    with the "dimensions" selector
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by using the reset button to reset the board
    with the given dimensions and start a new game
- You can locate my visual component by looking in the images folder of the project. There I have the pixel art that I made myself 
    and use for the timer, the flags, the bombs, and the smiley face on the reset button.
- You can save the state of my application by clicking the save button. There are also automatically saving game logs that can be
    viewed and reset
- You can reload the state of my application by clicking the load game button
- Note that previous version of my game that used Lantern can still be accessed by changing the field 'engineUsesSwing'
    in the 'MineSweeper' class to false.

### Phase 4:
_task 2_: \
Fri Dec 01 15:22:23 PST 2023    Generate New Board Layout: 30 x 16 \
Fri Dec 01 15:22:25 PST 2023    Starts Game \
Fri Dec 01 15:22:25 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:25 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:25 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:25 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:25 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:25 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:25 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:25 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:25 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:35 PST 2023    Ends Game Incomplete \
Fri Dec 01 15:22:35 PST 2023    Generate New Board Layout: 5 x 5 \
Fri Dec 01 15:22:38 PST 2023    Starts Game \
Fri Dec 01 15:22:38 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:38 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:38 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:38 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:38 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:38 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:38 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:38 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:38 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:38 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:38 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:38 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:38 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:38 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:43 PST 2023    ----- Flag Cell \
Fri Dec 01 15:22:45 PST 2023    ----- Flag Cell \
Fri Dec 01 15:22:47 PST 2023    ----- Un-Flag Cell \
Fri Dec 01 15:22:48 PST 2023    ----- Flag Cell \
Fri Dec 01 15:22:50 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:54 PST 2023    ----- Flag Cell \
Fri Dec 01 15:22:56 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:56 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:56 PST 2023    ----- Clear Cell \
Fri Dec 01 15:22:58 PST 2023    ----- Flag Cell \
Fri Dec 01 15:22:59 PST 2023    ----- Un-Flag Cell \
Fri Dec 01 15:22:59 PST 2023    ----- Flag Cell \
Fri Dec 01 15:23:00 PST 2023    ----- Un-Flag Cell \
Fri Dec 01 15:23:01 PST 2023    ----- Flag Cell \
Fri Dec 01 15:23:02 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:03 PST 2023    ----- Flag Cell \
Fri Dec 01 15:23:04 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:04 PST 2023    Ends Game Won \
Fri Dec 01 15:23:11 PST 2023    Generate New Board Layout: 5 x 5 \
Fri Dec 01 15:23:15 PST 2023    Starts Game \
Fri Dec 01 15:23:15 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:15 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:15 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:15 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:15 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:15 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:15 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:15 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:15 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:15 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:15 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:15 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:15 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:15 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:15 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:15 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:15 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:15 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:17 PST 2023    ----- Flag Cell \
Fri Dec 01 15:23:18 PST 2023    ----- Flag Cell \
Fri Dec 01 15:23:20 PST 2023    Saves Game \
Fri Dec 01 15:23:26 PST 2023    Ends Game Incomplete \
Fri Dec 01 15:23:26 PST 2023    Generate New Board Layout: 5 x 5 \
Fri Dec 01 15:23:29 PST 2023    Starts Game \
Fri Dec 01 15:23:29 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:29 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:29 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:29 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:29 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:29 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:29 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:29 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:29 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:29 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:29 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:29 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:32 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:33 PST 2023    ----- Flag Cell \
Fri Dec 01 15:23:33 PST 2023    ----- Flag Cell \
Fri Dec 01 15:23:34 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:36 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:40 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:41 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:41 PST 2023    Ends Game Lost \
Fri Dec 01 15:23:45 PST 2023    Loads Game \
Fri Dec 01 15:23:45 PST 2023    Starts Game \
Fri Dec 01 15:23:55 PST 2023    ----- Clear Cell \
Fri Dec 01 15:23:56 PST 2023    ----- Flag Cell \
Fri Dec 01 15:23:57 PST 2023    ----- Flag Cell \
Fri Dec 01 15:23:59 PST 2023    ----- Clear Cell \
Fri Dec 01 15:24:02 PST 2023    Show Game Logs \
Fri Dec 01 15:24:08 PST 2023    Reset Game Logs \
Fri Dec 01 15:24:09 PST 2023    Show Game Logs \
Fri Dec 01 15:24:14 PST 2023    ----- Flag Cell \
Fri Dec 01 15:24:14 PST 2023    Ends Game Won \
Fri Dec 01 15:24:20 PST 2023    Generate New Board Layout: 5 x 5 \

