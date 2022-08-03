package core;

import javax.swing.*;

public class Main {
    /**
     * Would you like to play a game?
     * @param args Not supported
     */
    public static void main(String[] args) {
        // Create and setup a Frame (Stage)
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
}
