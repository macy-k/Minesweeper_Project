package ui;

import model.Game;
import model.Log;

import java.io.IOException;

// Interface for engine classes
public interface Engine {
    // Starts gui
    void start() throws IOException, InterruptedException;

    Game getGame();

    void newGame(Game game) throws IOException, InterruptedException;
}
