package core;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Would you like to play a game?
     *
     * @param args commands to adjust program operation.
     *             -ClearLog will clear old log file data
     */
    public static void main(String[] args) {
        // Read command line arguments (if any)
        processArgs(args);

        // Create and set up a Frame (Stage)
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Tile Game Practice");

        // Create and add a panel
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // Size the Frame to hold the panel correctly.
        window.pack();

        // Set to middle of the screen
        window.setLocationRelativeTo(null);

        // Set Visible
        window.setVisible(true);

        // Lets get this game thread started.
        //TODO: Start paused and show menu (Not yet implemented)
        gamePanel.startGameThread();
    }

    private static void processArgs(String[] args) {
        for (String s : args) {
            if (s.equalsIgnoreCase("-ClearLog")) {
                LogTest.setDoAppend(false);
            } else {
                String msg = String.format("%s : %s", "Unknown argument", s);
                logger.log(Level.INFO, msg);
            }
        }
    }
}
