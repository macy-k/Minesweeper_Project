package ui;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import static ui.EngineSwing.lightGrey;

public class MenuPanel extends JPanel {
    private EngineSwing top;
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

// TO DO, Dimension selector, !!view Logs!!, reset game, save game, load game,

    public MenuPanel(EngineSwing top, Game game) {
        this.game = game;
        this.top = top;
        setBackground(lightGrey);
        setPreferredSize(new Dimension(800, 60));
        setUpComponents();
        setUpLayout();
        addComponents();
        //game.start(); // remove
        viewGameLogs();
    }

    private void addComponents() {
        add(timerL);
        add(timerM);
        add(timerR);
        add(scoreL);
        add(scoreM);
        add(scoreR);
    }

    private void setUpLayout() {
        SpringLayout layout = new SpringLayout();

        layout.putConstraint(SpringLayout.EAST, timerR, -9, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.NORTH, timerR, 9, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, timerM, 0, SpringLayout.WEST, timerR);
        layout.putConstraint(SpringLayout.NORTH, timerM, 9, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, timerL, 0, SpringLayout.WEST, timerM);
        layout.putConstraint(SpringLayout.NORTH, timerL, 9, SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, scoreL, 9, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, scoreL, 9, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, scoreM, 0, SpringLayout.EAST, scoreL);
        layout.putConstraint(SpringLayout.NORTH, scoreM, 9, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, scoreR, 0, SpringLayout.EAST, scoreM);
        layout.putConstraint(SpringLayout.NORTH, scoreR, 9, SpringLayout.NORTH, this);

        setLayout(layout);
    }

    private void setUpComponents() {
        setUpTimerLabels();
        setUpScoreLabels();
    }

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

//####################################################################
// Game Log Methods
//####################################################################

    public void viewGameLogs() {
        String gameLogs = top.getGameLogs();
        new GameLogDialog(top, "Game Logs", gameLogs);
    }

//####################################################################
// Update Methods
//####################################################################

    public void update() {
        updateTimer();
        updateScore();
    }

    private void updateTimer() {
        String timeString = game.getTimeString();
        AbstractList<Character> timeStringList = toCharList(timeString);
        for (int i = 0; i < 3; i++) {
            charToImage(timeStringList.get(i), i, true);
        }
    }

    private void updateScore() {
        String timeString = game.getScoreString();
        AbstractList<Character> timeStringList = toCharList(timeString);
        for (int i = 0; i < 3; i++) {
            charToImage(timeStringList.get(i), i, false);
        }
    }

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

    private void charToImage(Character c, Integer imageNum, Boolean changeTimer) {
        ImageIcon image;
        if (c == '0') {
            image = getImageFromPath("./images/0.png");
        } else if (c == '1') {
            image = getImageFromPath("./images/1.png");
        } else if (c == '2') {
            image = getImageFromPath("./images/2.png");
        } else if (c == '3') {
            image = getImageFromPath("./images/3.png");
        } else if (c == '4') {
            image = getImageFromPath("./images/4.png");
        } else if (c == '5') {
            image = getImageFromPath("./images/5.png");
        } else if (c == '6') {
            image = getImageFromPath("./images/6.png");
        } else if (c == '7') {
            image = getImageFromPath("./images/7.png");
        } else if (c == '8') {
            image = getImageFromPath("./images/8.png");
        } else {
            image = getImageFromPath("./images/9.png");
        }
        chooseTimerOrScore(image, imageNum, changeTimer);
    }

    private void chooseTimerOrScore(ImageIcon image, Integer imageNum, Boolean changeTimer) {
        if (changeTimer) {
            assignTimerImage(image, imageNum);
        } else {
            assignScoreImage(image, imageNum);
        }
    }

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

    private ImageIcon getImageFromPath(String localPath) {
        return new ImageIcon(new ImageIcon(localPath)
                .getImage().getScaledInstance(23, 40, Image.SCALE_AREA_AVERAGING));
    }
}
