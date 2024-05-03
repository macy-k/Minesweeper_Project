package ui;

import model.GameLogs;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import static ui.EngineSwing.*;

// Creates and operates the dialog box that shows GameLogs
public class GameLogDialog extends JDialog {
    private final Container contentPane;
    private JScrollPane scrollPane;
    private TextPanel textPanel;
    private JPanel buttonPanel;
    private JButton buttonFilter;
    private Boolean filter;

    public GameLogDialog(EngineSwing owner, String title) {
        super(owner, title);
        this.filter = false;
        contentPane = getContentPane();
        start();
    }

    // EFFECTS: Starts Dialog box
    public void start() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(440, 450);
        setMinimumSize(new Dimension(320, 200));
        contentPane.setBackground(lightGrey);

        createScrollPane();
        createButtonPanel();

        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates scroll pane
    private void createScrollPane() {
        textPanel = new TextPanel();
        scrollPane = new JScrollPane(textPanel);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(lightGrey, 0, false));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    // MODIFIES: this
    // EFFECTS: creates button panel
    private void createButtonPanel() {
        JButton buttonReset = new JButton("Reset Logs");
        buttonReset.setBackground(lightGrey);
        buttonReset.setBorder(new CellBevelBorder(highlight, shadow, shadow, highlight, 3));
        buttonReset.setFocusable(false);
        buttonReset.setPreferredSize(new Dimension(120, 27));
        buttonReset.addActionListener(e -> pressedButtonReset());
        buttonFilter = new JButton("Filter Incomplete Logs");
        buttonFilter.setBackground(lightGrey);
        buttonFilter.setBorder(new CellBevelBorder(highlight, shadow, shadow, highlight, 3));
        buttonFilter.setFocusable(false);
        buttonFilter.setPreferredSize(new Dimension(170, 27));
        buttonFilter.addActionListener(e -> pressedButtonFilter());

        buttonPanel = new JPanel();
        buttonPanel.add(buttonReset, BorderLayout.EAST);
        buttonPanel.add(buttonFilter, BorderLayout.WEST);
    }

    // MODIFIES: this, File Directory
    // EFFECTS: deletes GameLog json file, clears GameLogs and disposes of dialog box
    private void pressedButtonReset() {
        File file = new File("./data/Logs.json");
        if (file.exists()) {
            file.delete();
        }
        GameLogs.getInstance().clearLogs();
        dispose();
    }

    // MODIFIES: this
    // EFFECTS: changes the message displayed by the dialog box to either filter or unfilter incomplete games
    private void pressedButtonFilter() {
        filter = !filter;
        if (filter) {
            buttonFilter.setText("Unfilter Incomplete Logs");
        } else {
            buttonFilter.setText("Filter Incomplete Logs");
        }
        textPanel.changeTextAreaFilter();
    }

    //Private class used to create scrollable text panel
    private class TextPanel extends JPanel implements Scrollable {
        private final JTextArea text;

        public TextPanel() {
            text = new JTextArea();
            text.setBackground(Color.WHITE);
            text.setOpaque(false);
            text.setEditable(false);
            text.setText(GameLogs.getInstance().printGameLogs());
            text.setFont(new Font("Dialog", Font.BOLD, 15));
            add(text);
        }

        // MODIFIES: this
        // EFFECTS: changes the text in the text area according to the filter
        public void changeTextAreaFilter() {
            text.setText(GameLogs.getInstance().printGameLogs(filter));
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return null;
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 10;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 30;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return false;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }
}
