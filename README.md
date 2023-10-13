# My Personal Project
## Java Minesweeper

I want to make a minesweeper game where:
- There is a ui window with a standard grid of 30x16 cells, a mine count, a timer, 
        a reset button, a save button, a game-dimension selector, and (maybe) a mode selection panel.
- *YOU CAN CHOOSE DIMENSIONS OF GAME* in the game-dimension selector, so there can be **an arbitrary number of cells**.
- All cells will be blank at the start until first user input that starts timer.
- The first click will be guaranteed safe by removing any bombs in the vicinity of your first click and moving them
    to the top left corner, or the closest free cell to the right of the top left corner. This clears an initial area
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

The mode selection panel will be if I get really ambitious and make an auto-solver, but that might be done
        on my own time and not for this project.

I would also like to have a theme selection panel where you can choose from a set of color themes
  for the minesweeper game. This is not necessary and is just extra, if I have time.

**NOTE**: this is not going to work very well as a console based application, and selecting which cell
to interact with will be clunky.

_________

People will use this whenever they want a fun, quick, simple game to distract themselves and/or pass time.

This project is of interest to me because I love using minesweeper as a quick distraction and I have an app for
it on my phone. I'm curious to see if I can design and implement the game myself, and with the save function
I could even play it on my laptop. I also want to see if I can design an auto solver for it, and I don't particularly
want to interact with the HTML element of the game online, so this is a good opportunity to just make it myself.

_________

## User Stories

- as a user, I want to be able to choose how many cells are in my game by picking the dimensions of my game (This arbitrary, since even prime numbers can be chosen by selecting [prime x 1] as a dimension)
- as a user, I want to click my first cell, clearing it and not dying immediately
- as a user, I want to save my game so that if I quit the application, I can resume my game when I re-open the application
- as a user, I want to see all the bomb tiles when I die (I think this counts as viewing a list of items?)
- as a user, I want to see how many bombs there are left
- as a user, I want to see how long I've spent on my current game



*NOTE: SO sorry, I thought this was due at midnight tonight. It was mostly done, I just needed to clear the specifics of how the first click will work*