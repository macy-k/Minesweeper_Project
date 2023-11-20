package ui;

import model.Log;

import java.io.IOException;

// Interface for engine classes
public interface Engine {
    // Starts gui
    Log start() throws IOException, InterruptedException;
}
