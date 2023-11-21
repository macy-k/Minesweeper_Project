package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static ui.EngineSwing.lightGrey;

// Creates and operates the dialog box that shows GameLogs
public class GameLogDialog extends JDialog {
    private String message;
    private Container contentPane;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;

    public GameLogDialog(EngineSwing owner, String title, String message) {
        super(owner, title);
        this.message = message;
        contentPane = getContentPane();
        start();
    }

    // EFFECTS: Starts Dialog box
    public void start() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 450);
        setMinimumSize(new Dimension(320, 100));
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
        scrollPane = new JScrollPane(new TextPanel());
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(lightGrey, 0, false));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    // MODIFIES: this
    // EFFECTS: creates button panel
    private void createButtonPanel() {
        buttonPanel = new JPanel();
        JButton button = new JButton("Press to Reset Game Logs");
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(190, 20));
        button.addActionListener(e -> pressedButton());
        buttonPanel.add(button, BorderLayout.CENTER);
    }

    // MODIFIES: this, File Directory
    // EFFECTS: deletes GameLog json file and disposes of dialog box
    private void pressedButton() {
        File file = new File("./data/Logs.json");
        if (file.exists()) {
            file.delete();
        }
        dispose();
    }

    //Private class used to create scrollable text panel
    private class TextPanel extends JPanel implements Scrollable {

        public TextPanel() {
            add(createTextBox());
        }

        // EFFECTS: creates text area/box with game logs
        public JTextArea createTextBox() {
            JTextArea text = new JTextArea();
            text.setBackground(Color.WHITE);
            text.setOpaque(false);
            text.setText(message);
            text.setFont(new Font("Dialog", Font.BOLD, 15));
            return text;
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
