# My Personal Project
## Java Minesweeper

A simple minesweeper game that stays true to the original design but with added game customization, game-tracking functions, and saving/loading functions.
Much of the intended design has been implemented, but there are still many things about the GUI and customization functions that I would like to improve.

This project is of interest to me because I love using minesweeper as a quick distraction throughout my day--I even have an app for
it on my phone. I'm curious to see if I can design and implement the game myself, and with the save function
I could even play it on my laptop. I also want to see if I can design an auto solver for it, and I don't particularly
want to interact with the HTML element of the game online, so this is a good opportunity to just make it myself.

Currently Implemented:
- Models the data structure of a Cell, Board, Game, Log, and GameLog within their own respective classes
- Board class can automatically generate a randomized layout for an arbitrary number of bombs with any specified density
- Dimensions of the Board used in a Game can be changed while the Game hasn't been started
- GameLog and EventLog use a Singleton design pattern to reduce coupling
- GameLog records all previously played games, tracks win ratio and percentage, and can be reset completely
- All data-structures modeled can be saved using JSON (except for Events, which aren't relevant to the player experience)
- Using saved JSON files, full games can now be loaded in from their saved state
- Using Java Swing, created UI classes for Cells, the Board, the Menu, the various Dialog windows that stay true to the original design
- Uses manipulation of various Swing layouts including GridBagLayout, GridLayout, SpringLayout etc. to properly organize GUI
- Visuals make use of custom pixel art and a custom extension of AbstractBorder to stay true to the original design
- Implements a scrollable playing area using JScrollPane and implementations of the Scrollable interface
- CellLabels each contain a mouse handler that communicates the player's mouse position to the Game. Also allows the clearing of cells via left-click by starting the clearing system originating in BoardPanel
- BoardPanel contains mutually recursive methods for clearing cells on the board, ensuring that all nearby 0 cells are flood-cleared. This system interacts with both the CellLabel graphics and the corresponding Cell data-structures
- Contains two Engines: the currently used EngineSwing which includes the described Swing GUI and the EngineLanterna which was used early in development for visualization via a text-based GUI
- EngineSwing contains a key handler which allows player interactions with Cells via the space bar to flag, unflag, or clear surrounding Cells based on the selected Cell's state
- Includes user-friendly options for changing the dimensions of the board, saving the board, loading a previously saved board, viewing game logs, and resetting the board
- Includes full testing of model and persistence packages with 100% code coverage

To Be Implemented:
- More visually appealing Dialog UI for viewing Gamelogs and changing the dimensions
- Create an auto-solver using a combination of pattern recognition and statistical analysis (switching when needed--uses statistics when no more completely safe patterns are recognized)
- Create a downloadable application for the project
- Possible implement customizable color themes
- Make it possible to filter Games viewed in the GameLog by size of the board, completion statue, and other filtration methods.