import javax.swing.JFrame;

/**
 * @author Dorian Drazic-Karalic
 * @ASSESSME.INTENSITY:LOW
 */
public class App {
    /**
     * This function creates a window and starts the game.
     */
    public static void main(String[] args) {
        // creates the window and starts the game
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Among Us");

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);

        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gamePanel.setup();

        gamePanel.startGameThread();
    }

}
