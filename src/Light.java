import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * @ASSESSME.INTENSITY:LOW
 * @author Dorian Drazic-Karalic
 *         This class represents a Light object in the game.
 *         The Light object is created with a circle of a given
 *         size, with its center at
 *         the player's location, and creates a radial gradient
 *         from the center to the
 *         edges of the circle to simulate light.
 *         The Light object also creates a dark filter to
 *         overlay the screen, except for
 *         the area within the circle, to simulate darkness.
 */
public class Light {
    GamePanel gp;
    BufferedImage darknesFilter;

    /**
     * Constructs a new Light object with a given circle size.
     * The circle is centered on the player's location, and a radial gradient is
     * created from the center of the circle to the edges to simulate light.
     * A dark filter is also created to overlay the screen, except for the area
     * within the circle, to simulate darkness.
     * 
     * @param gp         The GamePanel object that the Light is associated with.
     * @param circleSize The size of the circle that will represent the light
     *                   source.
     */
    public Light(GamePanel gp, int circleSize) {
        darknesFilter = new BufferedImage(gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknesFilter.getGraphics();
        Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT));

        int centerX = gp.player.SCREEN_X + (gp.TILESIZE) / 2;
        int centerY = gp.player.SCREEN_Y + (gp.TILESIZE) / 2;

        double x = centerX - (circleSize / 2);
        double y = centerY - (circleSize / 2);

        Shape circle = new Ellipse2D.Double(x, y, circleSize, circleSize);

        Area lightArea = new Area(circle);

        screenArea.subtract(lightArea);

        Color color[] = new Color[5];
        float fraction[] = new float[5];

        color[0] = new Color(0, 0, 0, 0F);
        color[1] = new Color(0, 0, 0, 0.25F);
        color[2] = new Color(0, 0, 0, 0.5F);
        color[3] = new Color(0, 0, 0, 0.75F);
        color[4] = new Color(0, 0, 0, 1F);

        fraction[0] = 0f;
        fraction[1] = 0.25f;
        fraction[2] = 0.5f;
        fraction[3] = 0.75f;
        fraction[4] = 1f;

        RadialGradientPaint rgPaint = new RadialGradientPaint(centerX, centerY, (circleSize / 2), fraction, color);

        g2.setPaint(rgPaint);

        g2.fill(lightArea);

        g2.fill(screenArea);
        g2.dispose();
    }

    /**
     * Draws the dark filter that represents the darkness in the game.
     * 
     * @param g2 The Graphics2D object to draw the dark filter on.
     */
    public void draw(Graphics2D g2) {
        g2.drawImage(darknesFilter, 0, 0, null);
    }

}
