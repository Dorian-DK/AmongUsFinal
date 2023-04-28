import java.awt.Graphics2D;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

/**
 * @ASSESSME.INTENSITY:LOW
 * @author Dorian Drazic-Karalic
 *         This class manages the tiles and loads the map to be
 *         displayed in the
 *         GamePanel.
 */

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int mapTitleNum[][];

    /**
     * Constructor for TileManager class that takes in a GamePanel object.
     * Initializes tile and mapTitleNum arrays.
     */
    public TileManager(GamePanel gp) {

        this.gp = gp;
        tile = new Tile[10];
        mapTitleNum = new int[gp.MAX_WORLD_COLUMNS][gp.MAX_WORLD_ROWS];

        getTileImage();
        loadMap("map.txt");

    }

    /**
     * This method loads tile images from file.
     */
    private void getTileImage() {

        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(new File("floor.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(new File("Space.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(new File("Vent.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(new File("Task.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(new File("wall_down.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(new File("wall_side.png"));
            tile[5].collision = true;

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(new File("window.png"));
            tile[6].collision = true;

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(new File("table.png"));
            tile[7].collision = true;

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(new File("chair.png"));
            tile[8].collision = true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * This method loads the map from file.
     * It reads the file line by line and stores the numbers in a 2D array
     * mapTitleNum.
     */
    public void loadMap(String mapPath) {
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(new File(mapPath)));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            int col = 0;
            int row = 0;

            while (col < gp.MAX_WORLD_COLUMNS && row < gp.MAX_WORLD_ROWS) {
                String line = br.readLine();
                while (col < gp.MAX_WORLD_COLUMNS) {
                    String num[] = line.split(" ");

                    int numbers = Integer.parseInt(num[col]);

                    mapTitleNum[col][row] = numbers;
                    col++;

                }
                if (col == gp.MAX_WORLD_COLUMNS) {
                    col = 0;
                    row++;
                }

            }
            br.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * This method draws the tiles on the GamePanel using Graphics2D object.
     */
    public void draw(Graphics2D g2d) {

        int col = 0;
        int row = 0;

        while (col < gp.MAX_WORLD_COLUMNS && row < gp.MAX_WORLD_ROWS) {

            int tileNumber = mapTitleNum[col][row];
            int x = col * gp.TILESIZE;
            int y = row * gp.TILESIZE;
            int screenX = x - gp.player.worldX + gp.player.SCREEN_X;
            int screenY = y - gp.player.worldY + gp.player.SCREEN_Y;

            g2d.drawImage(tile[tileNumber].image, screenX, screenY, gp.TILESIZE, gp.TILESIZE, null);
            col++;

            if (col == gp.MAX_WORLD_COLUMNS) {
                col = 0;
                row++;
            }
        }

    }

}
