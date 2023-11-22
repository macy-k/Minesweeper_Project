package ui;

import exceptions.NoSavedGameException;
import model.Game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;

import static ui.EngineSwing.*;

// responsible for the menu panel of the gui
public class MenuPanel extends JPanel {
    private EngineSwing engine;
    private Game game;

    private JLabel timerL;
    private ImageIcon imageTimerL;
    private JLabel scoreL;
    private ImageIcon imageScoreL;

    private JLabel timerM;
    private ImageIcon imageTimerM;
    private JLabel scoreM;
    private ImageIcon imageScoreM;

    private JLabel timerR;
    private ImageIcon imageTimerR;
    private JLabel scoreR;
    private ImageIcon imageScoreR;

    private JButton viewLogs;
    private JButton loadGame;
    private JButton resetGame;
    private JButton saveGame;
    private JButton dimensionPopup;


    public MenuPanel(EngineSwing engine) throws IOException, InterruptedException {
        this.engine = engine;
        this.game = engine.getGame();
        setBackground(lightGrey);
        setPreferredSize(new Dimension(800, 60));
        setUpComponents();
        setUpLayout();
        addComponents();
    }

    // MODIFIES: this
    // EFFECTS: adds components to this
    private void addComponents() {
        add(timerL);
        add(timerM);
        add(timerR);
        add(scoreL);
        add(scoreM);
        add(scoreR);
        add(viewLogs);
        add(loadGame);
        add(resetGame);
        add(saveGame);
        add(dimensionPopup);
    }

    // EFFECTS: sets up SpringLayout constraints for all components
    private void setUpLayout() {
        SpringLayout layout = new SpringLayout();

        setUpNumberLayouts(layout);

        layout.putConstraint(SpringLayout.WEST, viewLogs, 27, SpringLayout.EAST, scoreR);
        layout.putConstraint(SpringLayout.NORTH, viewLogs, 9, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, dimensionPopup, 27, SpringLayout.EAST, viewLogs);
        layout.putConstraint(SpringLayout.NORTH, dimensionPopup, 9, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, resetGame, 0,
                SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.NORTH, resetGame, 9, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.EAST, saveGame, -27, SpringLayout.WEST, loadGame);
        layout.putConstraint(SpringLayout.NORTH, saveGame, 9, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.EAST, loadGame, -27, SpringLayout.WEST, timerL);
        layout.putConstraint(SpringLayout.NORTH, loadGame, 9, SpringLayout.NORTH, this);

        setLayout(layout);
    }

    // EFFECTS: specifically sets up SpringLayout constraints for number based menu items
    private void setUpNumberLayouts(SpringLayout layout) {
        layout.putConstraint(SpringLayout.WEST, scoreL, 9, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, scoreL, 9, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, scoreM, 0, SpringLayout.EAST, scoreL);
        layout.putConstraint(SpringLayout.NORTH, scoreM, 9, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, scoreR, 0, SpringLayout.EAST, scoreM);
        layout.putConstraint(SpringLayout.NORTH, scoreR, 9, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.EAST, timerR, -9, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, timerR, 9, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, timerM, 0, SpringLayout.WEST, timerR);
        layout.putConstraint(SpringLayout.NORTH, timerM, 9, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, timerL, 0, SpringLayout.WEST, timerM);
        layout.putConstraint(SpringLayout.NORTH, timerL, 9, SpringLayout.NORTH, this);
    }

    // EFFECTS: sets up components in MenuPanel
    private void setUpComponents() {
        setUpTimerLabels();
        setUpScoreLabels();
        setUpViewLogs();
        setUpLoadGame();
        setUpResetGame();
        setUpSaveGame();
        setUpDimensionPopup();
    }

    // MODIFIES: this
    // EFFECTS: sets up timer label components and updates them
    private void setUpTimerLabels() {
        timerL = new JLabel();
        timerL.setBackground(Color.BLACK);
        timerL.setOpaque(true);

        timerM = new JLabel();
        timerM.setBackground(Color.BLACK);
        timerM.setOpaque(true);

        timerR = new JLabel();
        timerR.setBackground(Color.BLACK);
        timerR.setOpaque(true);

        updateTimer();
    }

    // MODIFIES: this
    // EFFECTS: sets up score label components updates them
    private void setUpScoreLabels() {
        scoreL = new JLabel();
        scoreL.setBackground(Color.BLACK);
        scoreL.setOpaque(true);

        scoreM = new JLabel();
        scoreM.setBackground(Color.BLACK);
        scoreM.setOpaque(true);

        scoreR = new JLabel();
        scoreR.setBackground(Color.BLACK);
        scoreR.setOpaque(true);

        updateScore();
    }

    // MODIFIES: this
    // EFFECTS: sets up viewLogs button component
    private void setUpViewLogs() {
        viewLogs = new JButton("View Logs");
        viewLogs.setBackground(lightGrey);
        viewLogs.setFocusable(false);
        viewLogs.setPreferredSize(new Dimension(90, 40));
        viewLogs.setBorder(new CellBevelBorder(highlight, shadow, shadow, highlight, 3));
        viewLogs.addActionListener(e -> pressedViewLogs());
    }

    // MODIFIES: this
    // EFFECTS: sets up loadGame button component
    private void setUpLoadGame() {
        loadGame = new JButton("Load Game");
        loadGame.setBackground(lightGrey);
        loadGame.setFocusable(false);
        loadGame.setPreferredSize(new Dimension(90, 40));
        loadGame.setBorder(new CellBevelBorder(highlight, shadow, shadow, highlight, 3));
        loadGame.addActionListener(e -> {
            try {
                pressedLoadGame();
            } catch (IOException | InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: sets up resetGame button component
    private void setUpResetGame() {
        resetGame = new JButton();
        resetGame.setIcon(new ImageIcon(new ImageIcon("./images/Smiley Face.png")
                .getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING)));
        resetGame.setBackground(lightGrey);
        resetGame.setFocusable(false);
        resetGame.setPreferredSize(new Dimension(40, 40));
        resetGame.setMinimumSize(new Dimension(40, 40));
        resetGame.setMaximumSize(new Dimension(40, 40));
        resetGame.setBorder(new CellBevelBorder(highlight, shadow, shadow, highlight, 3));
        resetGame.addActionListener(e -> {
            try {
                pressedResetGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: sets up saveGame button component
    private void setUpSaveGame() {
        saveGame = new JButton("Save Game");
        saveGame.setBackground(lightGrey);
        saveGame.setFocusable(false);
        saveGame.setPreferredSize(new Dimension(90, 40));
        saveGame.setBorder(new CellBevelBorder(highlight, shadow, shadow, highlight, 3));
        saveGame.addActionListener(e -> pressedSaveGame());
    }

    // MODIFIES: this
    // EFFECTS: sets up dimensionPopup button component
    private void setUpDimensionPopup() {
        dimensionPopup = new JButton("Dimensions");
        dimensionPopup.setBackground(lightGrey);
        dimensionPopup.setFocusable(false);
        dimensionPopup.setPreferredSize(new Dimension(90, 40));
        dimensionPopup.setBorder(new CellBevelBorder(highlight, shadow, shadow, highlight, 3));
        dimensionPopup.addActionListener(e -> pressedDimensionButton());
    }

    // EFFECTS: calls up dialog popup used to view game logs
    private void pressedViewLogs() {
        String gameLogs = engine.getGameLogs();
        new GameLogDialog(engine, "Game Logs", gameLogs);
    }

    // MODIFIES: game, engine
    // EFFECTS: loads the saved game into engine while ensuring engine will properly save
    //      current game's status to logs
    private void pressedLoadGame() throws IOException, InterruptedException {
        try {
            if (!game.isEnded()) {
                game.incomplete();
            }
            Game newGame = engine.getTop().readGame();
            newGame.start();
            engine.newGame(newGame);
        } catch (NoSavedGameException e) {
            // for future, put in WarningDialog popup with "There is no saved game"
        }
    }

    // MODIFIES: game, engine
    // EFFECTS: resets and loads a new game into engine while ensuring engine will properly save
    //      current game's status to logs
    private void pressedResetGame() throws IOException, InterruptedException {
        if (game.isStarted()) {
            if (!game.isEnded()) {
                game.incomplete();
            }
            Integer score = game.getBoard().getCorrectlyFlaggedBombs();
            game.getBoard().generateLayout();
            engine.newGame(new Game(game.getBoard()), score);
        }
    }

    // MODIFIES: engine
    // EFFECTS: saves current game
    private void pressedSaveGame() {
        engine.saveGame();
    }

    // EFFECTS: calls up dialog popup used to set new board dimensions
    private void pressedDimensionButton() {
        DimensionDialog dd = new DimensionDialog(engine, "Choose Dimensions",
                Dialog.ModalityType.APPLICATION_MODAL);
        dd.start();

    }

//####################################################################
// Update Methods
//####################################################################

    // Updates entire MenuPanel based on game state
    public void update() {
        updateTimer();
        updateScore();
        updateSmileyFace();
    }

    // EFFECTS: updates timer
    private void updateTimer() {
        String timeString = game.getTimeString();
        AbstractList<Character> timeStringList = toCharList(timeString);
        for (int i = 0; i < 3; i++) {
            charToImage(timeStringList.get(i), i, true);
        }
    }

    // EFFECTS: updates score
    private void updateScore() {
        String timeString = game.getScoreString();
        AbstractList<Character> timeStringList = toCharList(timeString);
        for (int i = 0; i < 3; i++) {
            charToImage(timeStringList.get(i), i, false);
        }
    }

    // EFFECTS: updated smiley face on reset button
    private void updateSmileyFace() {
        if (game.isEnded()) {
            if (game.isWon()) {
                resetGame.setIcon(new ImageIcon(new ImageIcon("./images/Sunglasses Face.png")
                        .getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING)));
            } else {
                resetGame.setIcon(new ImageIcon(new ImageIcon("./images/Dead Face.png")
                        .getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING)));
            }
        }
    }

    // EFFECTS: converts string into abstract list of characters
    private AbstractList<Character> toCharList(String s) {
        return new AbstractList<Character>() {
            @Override
            public Character get(int index) {
                return s.charAt(index);
            }

            @Override
            public int size() {
                return s.length();
            }
        };
    }

    // REQUIRES: charachter must be numerical
    // EFFECTS: converts a charachter into its respective image sprite
    private void charToImage(Character c, Integer imageNum, Boolean changeTimer) {
        ImageIcon image;
        if (c == '0') {
            image = getNumberImageFromPath("./images/0.png");
        } else if (c == '1') {
            image = getNumberImageFromPath("./images/1.png");
        } else if (c == '2') {
            image = getNumberImageFromPath("./images/2.png");
        } else if (c == '3') {
            image = getNumberImageFromPath("./images/3.png");
        } else if (c == '4') {
            image = getNumberImageFromPath("./images/4.png");
        } else if (c == '5') {
            image = getNumberImageFromPath("./images/5.png");
        } else if (c == '6') {
            image = getNumberImageFromPath("./images/6.png");
        } else if (c == '7') {
            image = getNumberImageFromPath("./images/7.png");
        } else if (c == '8') {
            image = getNumberImageFromPath("./images/8.png");
        } else {
            image = getNumberImageFromPath("./images/9.png");
        }
        chooseTimerOrScore(image, imageNum, changeTimer);
    }

    // EFFECTS: changes time or score image based on given boolean
    private void chooseTimerOrScore(ImageIcon image, Integer imageNum, Boolean changeTimer) {
        if (changeTimer) {
            assignTimerImage(image, imageNum);
        } else {
            assignScoreImage(image, imageNum);
        }
    }

    // MODIFIES: this
    // EFFECTS: changes timer image, whose position is given by integer imageNum
    private void assignTimerImage(ImageIcon image, Integer imageNum) {
        if (imageNum == 0) {
            imageTimerL = image;
            timerL.setIcon(imageTimerL);
            timerL.revalidate();
        } else if (imageNum == 1) {
            imageTimerM = image;
            timerM.setIcon(imageTimerM);
            timerM.revalidate();
        } else {
            imageTimerR = image;
            timerR.setIcon(imageTimerR);
            timerR.revalidate();
        }
    }

    // MODIFIES: this
    // EFFECTS: changes score image, whose position is given by integer imageNum
    private void assignScoreImage(ImageIcon image, Integer imageNum) {
        if (imageNum == 0) {
            imageScoreL = image;
            scoreL.setIcon(imageScoreL);
            scoreL.revalidate();
        } else if (imageNum == 1) {
            imageScoreM = image;
            scoreM.setIcon(imageScoreM);
            scoreM.revalidate();
        } else {
            imageScoreR = image;
            scoreR.setIcon(imageScoreR);
            scoreR.revalidate();
        }
    }

    // EFFECTS: retrieves numerical image sprite from given path and scales it appropriately
    private ImageIcon getNumberImageFromPath(String localPath) {
        return new ImageIcon(new ImageIcon(localPath)
                .getImage().getScaledInstance(23, 40, Image.SCALE_AREA_AVERAGING));
    }
}
