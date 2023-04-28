
/**
 * The GamePanel class is responsible for setting up and running the game, including creating game
 * objects, handling player input, updating the game state, and drawing the game graphics.
 */

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;

import javax.swing.JPanel;

/**
 * @author Dorian Drazic-Karalic
 * @ASSESSME.INTENSITY:LOW
 */
public class GamePanel extends JPanel implements Runnable {
    /**
     * 
     * Setting the size of the panel and of the game
     *
     * mp- tag that would be used if the server worked
     */

    // Initializing constants
    final int ORIGINAL_TILESIZE = 16;
    final int SCALE = 3;
    // Defining the tile size for the game screen and world
    public final int TILESIZE = ORIGINAL_TILESIZE * SCALE;
    // Defining the maximum number of columns and rows that the screen can display
    public final int MAX_SCREEN_COL = 16;
    public final int MAX_SCREEN_ROW = 12;
    // Defining the width and height of the screen based on tile size and max screen
    // columns and rows
    public final int SCREEN_WIDTH = TILESIZE * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = TILESIZE * MAX_SCREEN_ROW;
    // Defining the frame rate for the game
    public final int FPS = 60;

    // Defining the maximum number of columns and rows that the world can have
    public final int MAX_WORLD_COLUMNS = 62;
    public final int MAX_WORLD_ROWS = 62;
    // Defining the width and height of the world based on tile size and max world
    // columns and rows
    public final int WORLD_WIDTH = TILESIZE * MAX_WORLD_COLUMNS;
    public final int WORLD_HEIGHT = TILESIZE * MAX_WORLD_ROWS;

    // Initializing boolean variables to check if the player is an impostor and if
    // the game has ended
    public boolean impostor;
    public boolean end = false;

    // Initializing file object to store configuration information
    public File xmlFile = new File("config.xml");

    // Game
    TileManager tm = new TileManager(this);
    UI ui = new UI(this);
    // MultiplayerAttributes mpAttributes = new MultiplayerAttributes(); mp
    /** MultiplayerSetup mpSetup = new MultiplayerSetup(); mp */
    // MultiplayerAttributes.User user = mpAttributes.new User(mpSetup.ID,
    // mpSetup.Impostor); mp

    KeyHandler keyHandler = new KeyHandler(this);
    ObjectSetter oSetter = new ObjectSetter(this);
    EntitiySetter eSetter = new EntitiySetter(this);
    EventHandler eHandler = new EventHandler(this);
    Light light;

    Thread gamThread;
    Config config = new Config();

    // Objects

    Player player;
    Collision col = new Collision(this);

    public Objects obj[] = new Objects[10];
    // for dynamic change for players entity would be arrayList with the number of
    // players got from the size of clients array but i dont have the server
    public Entitiy entities[] = new Entitiy[3];

    // Initializing integer variable to track the current state of the game
    public int gameState;
    public final int TITLE_STATE = 0;
    public final int PLAY_STATE = 1;
    public final int PAUSE_STATE = 2;

    // Defining the GamePanel constructor
    public GamePanel() {
        // Setting the preferred size of the panel
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        // Enabling double buffering for smooth graphics
        this.setDoubleBuffered(true);
        // Adding a key listener to handle player input
        this.addKeyListener(keyHandler);
        // Making the panel focusable
        this.setFocusable(true);

    }

    // Method to set up the game
    public void setup() {
        // Setting up game objects
        oSetter.setObject();
        // Checking for existing configuration file
        if (xmlFile.exists()) {
            config.loadConfig();
            impostor = config.getImpostor();
        } else {
            impostor = false;
        }
        // Initializing the player object
        player = new Player(this, keyHandler, 1, impostor, 1);
        // Setting up entity objects
        eSetter.SetEntity();
        // Setting game state to title screen
        gameState = TITLE_STATE;
        light = new Light(this, 350);

    }

    /**
     * Starts the game thread by creating a new thread and starting it.
     */
    public void startGameThread() {
        gamThread = new Thread(this);
        gamThread.start();
    }

    /**
     * This class is used to set the entities in the game panel.
     */
    class EntitiySetter {
        GamePanel gp;

        public EntitiySetter(GamePanel gp) {
            this.gp = gp;

        }

        /**
         * 
         * Method to set the entities in the game panel by creating new OtherPlayers
         * objects
         * and setting their worldX, worldY, and name properties.
         */
        public void SetEntity() {

            gp.entities[0] = new OtherPlayers(2, gp);
            gp.entities[0].worldX = 36 * TILESIZE;
            gp.entities[0].worldY = 36 * TILESIZE;
            gp.entities[0].name = "Bob";

            gp.entities[1] = new OtherPlayers(3, gp);
            gp.entities[1].worldX = 33 * TILESIZE;
            gp.entities[1].worldY = 36 * TILESIZE;
            gp.entities[1].name = "Rob";

            gp.entities[2] = new OtherPlayers(4, gp);
            gp.entities[2].worldX = 38 * TILESIZE;
            gp.entities[2].worldY = 36 * TILESIZE;
            gp.entities[2].name = "Cob";

        }

        /*
         * if the server worked it would display it like this
         * public void SetEntity() {
         * for (int i = 0; i < mpAttributes.players.size(); i++) {
         * MultiplayerAttributes.User playerOther = mpAttributes.players.get(i);
         * if (playerOther.ID == player.ID) {
         * // skip creating an OtherPlayers entity for the player
         * continue;
         * }
         * gp.entities[i] = new OtherPlayers(player.ID, gp);
         * gp.entities[i].worldX = playerOther.worldXOnline;
         * gp.entities[i].worldY = playerOther.worldYOnline;
         * gp.entities[i].name = player.name;
         * }}
         */

    }

    class ObjectSetter {
        GamePanel gp;

        public ObjectSetter(GamePanel gp) {
            this.gp = gp;
        }

        /**
         * 
         * Method to set the objects in the game panel by creating new objects
         * and setting their worldX, worldY
         * for the doors it also sees if its a side door or horizontal
         */
        public void setObject() {

            gp.obj[0] = new Doors(false);
            gp.obj[0].worldX = 19 * gp.TILESIZE;
            gp.obj[0].worldY = 12 * gp.TILESIZE;

            gp.obj[1] = new Doors(false);
            gp.obj[1].worldX = 20 * gp.TILESIZE;
            gp.obj[1].worldY = 12 * gp.TILESIZE;

            gp.obj[2] = new Doors(true);
            gp.obj[2].worldX = 21 * gp.TILESIZE;
            gp.obj[2].worldY = 15 * gp.TILESIZE;

            gp.obj[3] = new Doors(true);
            gp.obj[3].worldX = 21 * gp.TILESIZE;
            gp.obj[3].worldY = 16 * gp.TILESIZE;

            gp.obj[4] = new Doors(false);
            gp.obj[4].worldX = 19 * gp.TILESIZE;
            gp.obj[4].worldY = 20 * gp.TILESIZE;

            gp.obj[5] = new Doors(false);
            gp.obj[5].worldX = 20 * gp.TILESIZE;
            gp.obj[5].worldY = 20 * gp.TILESIZE;

            gp.obj[6] = new Tasks();
            gp.obj[6].worldX = 8 * gp.TILESIZE;
            gp.obj[6].worldY = 15 * gp.TILESIZE;

            gp.obj[7] = new Tasks();
            gp.obj[7].worldX = 29 * gp.TILESIZE;
            gp.obj[7].worldY = 52 * gp.TILESIZE;

            gp.obj[8] = new Tasks();
            gp.obj[8].worldX = 53 * gp.TILESIZE;
            gp.obj[8].worldY = 52 * gp.TILESIZE;

        }
    }

    /**
     * Runs the game loop
     */
    @Override
    public void run() {

        double refresh = 1000000000 / FPS;
        double nextRefresh = System.nanoTime() + refresh;

        while (gamThread != null) {

            // Update the screen for movement and map
            update();

            // Draw the screen with the updated screen
            repaint();
            try {
                double remaining = nextRefresh - System.nanoTime();
                remaining = remaining / 1000000;
                if (remaining <= 0)
                    remaining = 0;
                Thread.sleep((long) remaining);

                nextRefresh += refresh;

            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    /**
     * Updates the game
     */
    public void update() {
        // Update the player
        if (gameState == PLAY_STATE) {
            player.update();

        }
        if (gameState == PAUSE_STATE) {
            if (player.bodyFound) {

            } else
                gameState = PLAY_STATE;
        }

    }

    /**
     * Paints the game
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Using graphics as it gives more control over the drawing
        Graphics2D g2d = (Graphics2D) g;

        if (gameState == TITLE_STATE) {
            ui.draw(g2d);

        } else {
            // Draw the tiles
            tm.draw(g2d);
            // Draw the objects
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2d, this);
                }
            }
            // Draw the entities
            for (int i = 0; i < entities.length; i++) {
                if (entities[i] != null) {
                    entities[i].draw(g2d);
                }
            }
            // Draw the player
            player.draw(g2d);
            // Draw the light
            light.draw(g2d);
            // Draw the UI
            ui.draw(g2d);

            // saves memory
            g2d.dispose();

        }

    }

}
