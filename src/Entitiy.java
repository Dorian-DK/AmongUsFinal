
/**
 * The code defines a class hierarchy for game entities, with a base class "Entity" and a subclass
 * "OtherPlayers" that inherits from it.
 */
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Entitiy {

    public int worldX, worldY;
    public int speed;
    public GamePanel gp;
    public Boolean isDead = false;

    public BufferedImage ballon;
    public String direction;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 47);

    public int spriteCounter;
    public int spriteNum;

    public Rectangle collisionRectangle;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public Boolean collision = false;
    public Boolean sabotaged = false;
    public String name;
    public BufferedImage image;
    public boolean impostor;

    /**
     * Creates a new Entity with the specified GamePanel.
     * 
     * @param gp the GamePanel the Entity is associated with
     */
    public Entitiy(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Draws the Entity onto the screen using the specified Graphics2D object.
     * 
     * @param g the Graphics2D object to use for drawing the Entity
     */
    public void draw(Graphics2D g) {
        int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
        int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

        g.drawImage(image, screenX, screenY, gp.TILESIZE, gp.TILESIZE, null);

    }

}

/**
 * 
 * Represents an OtherPlayer Entity in the game that inherits from Entitiy.
 */
class OtherPlayers extends Entitiy {
    public String imageFile = "";

    public int solidAreaDefaultX, solidAreaDefaultY;

    /**
     * 
     * Creates a new OtherPlayer with the specified color and GamePanel.
     * 
     * @ASSESSME.INTENSITY:LOW
     * @author Dorian Drazic-Karalic
     * @param color the color of the OtherPlayer
     * 
     * @param gp    the GamePanel the OtherPlayer is associated with
     */
    public OtherPlayers(int color, GamePanel gp) {
        super(gp);
        try {
            switch (color) {
                case 1:
                    imageFile = "Ballon_Blue.png";

                    break;
                case 2:
                    imageFile = "Ballon_Red.png";

                    break;
                case 3:
                    imageFile = "Ballon_Pink.png";

                    break;
                case 4:
                    imageFile = "Ballon_Yellow.png";

                    break;
            }

            image = ImageIO.read(new File(imageFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
