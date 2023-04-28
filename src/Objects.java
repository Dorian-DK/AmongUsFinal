import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @ASSESSME.INTENSITY:LOW
 * @author Dorian Drazic-Karalic
 *         The Objects class represents the objects in the game.
 *         It has properties such
 *         as image, name, collision, worldX, and worldY.
 */
public class Objects {

    public BufferedImage image;
    public String name;
    public boolean collision;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    /**
     * This method draws the object on the screen.
     *
     * @param g  the Graphics2D object to use for drawing.
     * @param gp the GamePanel object.
     */
    public void draw(Graphics2D g, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
        int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

        g.drawImage(image, screenX, screenY, gp.TILESIZE, gp.TILESIZE, null);

    }

}

/**
 * 
 * The Doors class extends the Objects class and represents the doors in the
 * game. It has properties such as image and collision.
 */
class Doors extends Objects {
    /**
     * 
     * This constructor creates a new door object with the specified direction.
     * 
     * @param down true if the door is facing down, false otherwise.
     */
    public Doors(Boolean down) {
        name = "Door";
        collision = true;
        try {
            if (down) {
                image = ImageIO.read(new File("door_down.png"));

            }

            else {
                image = ImageIO.read(new File("door_side.png"));

            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

/**
 * 
 * The Tasks class extends the Objects class and represents the tasks in the
 * game. It has properties such as image and name.
 */
class Tasks extends Objects {
    public Tasks() {
        name = "Tasks";
        try {
            image = ImageIO.read(new File("Task.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
