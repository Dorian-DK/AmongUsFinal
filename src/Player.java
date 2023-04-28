
/**
 * The Player class represents a player entity in a game and handles movement, collisions, tasks, and
 * interactions with other objects and players.
 */
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.imageio.ImageIO;

/**
 * @ASSESSME.INTENSITY:LOW
 * @author Dorian Drazic-Karalic
 */
public class Player extends Entitiy {
    /*
     * mp- tag that would be used if the server worked
     */
    KeyHandler keyHandler;
    String image = "";
    String image2 = "";
    BufferedImage ballonMove1;
    BufferedImage ballonMove;
    int tasks;
    ArrayList<Integer> lastTask = new ArrayList<Integer>();
    Boolean sabotaged = false;
    int counterR = 0;
    int counterSpace = 0;
    int counterK = 0;
    Boolean bodyFound = false;
    Boolean didTask1 = false;
    Boolean didTask2 = false;
    Boolean didTask3 = false;
    public final int SCREEN_X;
    public final int SCREEN_Y;
    public String name;
    public int ID;
    // int alive = gp.mpAttributes.user.playersAlive; mp
    int alive = 4;
    String task2 = "";

    /**
     * Constructor for the Player class. Initializes a new player entity.
     *
     * @param gp         The GamePanel object representing the game.
     * @param keyHandler The KeyHandler object used for handling key presses.
     * @param color      The color of the player.
     * @param isImpostor A Boolean indicating whether the player is an impostor or
     *                   not.
     * @param ID         The unique ID of the player.
     */
    public Player(GamePanel gp, KeyHandler keyHandler, int color, Boolean isImpostor, int ID) {
        super(gp);
        this.keyHandler = keyHandler;
        impostor = isImpostor;
        this.ID = ID;

        SCREEN_X = gp.SCREEN_WIDTH / 2 - (gp.TILESIZE / 2);
        SCREEN_Y = gp.SCREEN_HEIGHT / 2 - (gp.TILESIZE / 2);

        collisionRectangle = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = collisionRectangle.x;
        solidAreaDefaultY = collisionRectangle.y;

        setDeafultValues();
        getPlayerImage(color);
    }

    /**
     * Sets the default values for a new player entity.
     */
    public void setDeafultValues() {
        worldX = gp.TILESIZE * 36;
        worldY = gp.TILESIZE * 33;
        speed = 2;
        direction = "DOWN";
        tasks = 3;
    }

    /**
     * Loads the player image based on the player's color.
     *
     * @param color The color of the player.
     */
    public void getPlayerImage(int color) {
        try {
            switch (color) {
                case 1:
                    image = "Ballon_Blue.png";
                    image2 = "Ballon_Blue_Move.png";
                    break;
                case 2:
                    image = "Ballon_Red.png";
                    image2 = "Ballon_Red_Move.png";
                    break;
                case 3:
                    image = "Ballon_Pink.png";
                    image2 = "Ballon_Pink_Move.png";
                    break;
                case 4:
                    image = "Ballon_Red.png";
                    image2 = "Ballon_Pink_Move.png";
                    break;
            }

            ballonMove1 = ImageIO.read(new File(image));
            ballonMove = ImageIO.read(new File(image2));
            ballon = ImageIO.read(new File(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * Updates the state of the player and checks for collisions and interactions
     * with other objects and players.
     * 
     * Also updates the direction of movement and handles sabotaging and task
     * completion.
     */
    public void update() {

        if (keyHandler.upPressed) {
            direction = "UP";

        }
        if (keyHandler.downPressed) {

            direction = "DOWN";
        }
        if (keyHandler.leftPressed) {

            direction = "LEFT";
        }
        if (keyHandler.rightPressed) {

            direction = "RIGHT";
        }
        if (keyHandler.rPressed) {
            sabotaged = true;

        }
        if (keyHandler.spacePressed && impostor) {
            speed = 1;
        }
        counterR++;
        if (counterR > 800) {
            sabotaged = false;
            speed = 2;
            counterR = 0;
        }

        gp.ui.setMessage("Doors sabotaged!", sabotaged, impostor);

        // check for collisions
        collision = false;
        gp.col.checkCollision(this);

        // check for collisions with other objects
        int objIndex = gp.col.checkObjectCollision(this, true);
        openDoor(sabotaged, objIndex);
        doTask(sabotaged, objIndex);

        // check for collisions with other players
        int npcIndex = gp.col.checkEntity(this, gp.entities, impostor);
        killCrewmate(npcIndex);
        report(npcIndex);

        gp.eHandler.checkEvent();

        if (collision == false) {
            if (direction == "UP" && keyHandler.upPressed)
                worldY -= speed;
            if (direction == "DOWN" && keyHandler.downPressed)
                worldY += speed;
            if (direction == "LEFT" && keyHandler.leftPressed)
                worldX -= speed;
            if (direction == "RIGHT" && keyHandler.rightPressed)
                worldX += speed;

        }
        if (tasks == 0) {
            gp.ui.gameOver = true;
            gp.ui.crewmateWin = true;
        }
        if (alive <= 1) {
            gp.ui.gameOver = true;
            gp.ui.impostorWin = true;
        }
    }

    /**
     * 
     * Indicates if the player has been killed or not.
     */
    public boolean isKilled = false;
    private Timer timer = new Timer(5000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            isKilled = false;
        }
    });

    /**
     * 
     * Kills a crewmate if the impostor presses the 'k' key and if there isn't a
     * cooldown for killing.
     * Sends death info to server and updates the game state accordingly.
     * 
     * @param i the index of the crewmate being killed
     */
    private void killCrewmate(int i) {
        if (i != -1 && impostor && keyHandler.kPressed && !isKilled) {
            // send death info to server
            try {
                gp.entities[i].image = ImageIO.read(new File("dead.png"));

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            gp.entities[i].isDead = true;
            isKilled = true;
            alive--;

            timer.restart(); // start or restart the timer
        }
    }

    /**
     * 
     * Checks if a dead body has been found and updates the game state accordingly.
     * 
     * @param i the index of the entity being checked for being dead
     */
    public void report(int i) {
        if (i != -1 && gp.entities[i].isDead) {
            bodyFound = true;
        } else
            bodyFound = false;
    }

    /**
     * 
     * Sets the collision status of the door object with the given index based on
     * the isSabotaged parameter.
     * 
     * @param isSabotaged the boolean value indicating if the door is sabotaged
     * @param index       the index of the door object to update
     */
    public void openDoor(Boolean isSabotaged, int index) {
        if (index != 9999) {
            if (gp.obj[index].name == "Door")
                gp.obj[index].collision = isSabotaged;

        }
    }

    /**
     * 
     * Performs the task with the given index based on the isSabotaged parameter, if
     * it has not been completed before and the player is not an impostor.
     * 
     * @param isSabotaged the boolean value indicating if the task is sabotaged
     * @param index       the index of the task object to perform
     */
    public void doTask(Boolean isSabotaged, int index) {
        if (index != 9999) {

            if (tasks > 0)

                if (!lastTask.contains(index) && gp.obj[index].name == "Tasks" && !isSabotaged && !impostor) {
                    if (index == 6) {
                        gp.ui.taskNum = 1;

                    } else if (index == 7) {
                        gp.ui.taskNum = 2;

                    } else if (index == 8) {
                        gp.ui.taskNum = 3;

                    }
                    tasks--;
                    // gp.mpAttributes.user.totalTasks--; mp
                    lastTask.add(index);
                }

        }
    }

    /**
     * 
     * Draws the player's image on the game screen using the given graphics object.
     * 
     * @param g2d the graphics object to use for drawing
     */
    public void draw(Graphics2D g2d) {

        g2d.drawImage(ballon, SCREEN_X, SCREEN_Y, gp.TILESIZE, gp.TILESIZE, null);
    }
}