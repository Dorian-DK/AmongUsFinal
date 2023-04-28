
/**
 * The EventHandler class handles game events that occur when a player hits certain coordinates on the
 * game board in a Java game.
 */
import java.awt.Rectangle;

/**
 * @ASSESSME.INTENSITY:LOW
 * @author Dorian Drazic-Karalic
 *         This class handles the game events that occur when a
 *         player hits certain
 *         coordinates on the game board.
 */

public class EventHandler {
    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;

    /**
     * Constructs an EventHandler object.
     * 
     * @param gp the GamePanel object that EventHandler is associated with
     */
    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 4;
        eventRect.height = 4;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }

    /**
     * Checks if the player has hit any of the event coordinates and performs an
     * event if a hit has occurred.
     */
    public void checkEvent() {
        if (hit(8, 18, "any")) {
            gp.player.worldX = 30 * gp.TILESIZE;
            gp.player.worldY = 52 * gp.TILESIZE;

        }
        if (hit(31, 52, "any")) {
            gp.player.worldX = 9 * gp.TILESIZE;
            gp.player.worldY = 17 * gp.TILESIZE;

        }
        if (hit(53, 46, "any")) {
            gp.player.worldX = 53 * gp.TILESIZE;
            gp.player.worldY = 29 * gp.TILESIZE;

        }
        if (hit(53, 28, "any")) {
            gp.player.worldX = 53 * gp.TILESIZE;
            gp.player.worldY = 47 * gp.TILESIZE;

        }
    }

    /**
     * Checks if the player has hit a specified event coordinate in a specific
     * direction.
     * 
     * 
     * @param eventCol the column of the event coordinate
     * @param eventRow the row of the event coordinate
     * @param reqDir   the required direction of the player's movement to trigger
     *                 the event ("up", "down", "left", "right", or "any")
     * 
     * @return true if the player has hit the specified event coordinate in the
     *         required direction, false otherwise
     */
    public boolean hit(int eventCol, int eventRow, String reqDir) {
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect.x = eventCol * gp.TILESIZE + eventRect.x;
        eventRect.y = eventRow * gp.TILESIZE + eventRect.y;

        if (gp.player.solidArea.intersects(eventRect)) {

            if (gp.player.impostor) {
                hit = true;
            }

        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;
    }

}
