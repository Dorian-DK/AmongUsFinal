
/**
 * The Collision class contains methods for detecting collisions between entities and tiles or other
 * entities in a game world.
 */
import java.awt.Rectangle;

public class Collision {
    GamePanel gp;

    /**
     * Constructor for the Collision class
     * 
     * @author Dorian Drazic-Karalic
     * @ASSESSME.INTENSITY:LOW
     * @param gp the GamePanel instance to use for collision detection
     */
    public Collision(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Check for collision with the specified entity
     * 
     * @param entity the Entity to check for collision with
     */
    public void checkCollision(Entitiy entity) {

        // Calculate the coordinates of the Entity's collision rectangle in the world
        int entityLeftWorldX = entity.worldX + entity.collisionRectangle.x;
        int entityRightWorldX = entity.worldX + entity.collisionRectangle.x + entity.collisionRectangle.width;
        int entityTopWorldY = entity.worldY + entity.collisionRectangle.y;
        int entityBottomWorldY = entity.worldY + entity.collisionRectangle.y + entity.collisionRectangle.height;

        // Calculate the columns and rows of the Entity's collision rectangle in the
        // tile map
        int entityLeftColumn = entityLeftWorldX / gp.TILESIZE;
        int entityRightColumn = entityRightWorldX / gp.TILESIZE;
        int entityTopRow = entityTopWorldY / gp.TILESIZE;
        int entityBottomRow = entityBottomWorldY / gp.TILESIZE;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "UP":
                // Check for collision with tiles above the Entity
                entityTopRow = (entityTopWorldY - entity.speed) / gp.TILESIZE;
                tileNum1 = gp.tm.mapTitleNum[entityLeftColumn][entityTopRow];
                tileNum2 = gp.tm.mapTitleNum[entityRightColumn][entityTopRow];
                if (gp.tm.tile[tileNum1].collision || gp.tm.tile[tileNum2].collision) {
                    entity.collision = true;
                }

                break;
            case "DOWN":
                // Check for collision with tiles below the Entity
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.TILESIZE;
                tileNum1 = gp.tm.mapTitleNum[entityLeftColumn][entityBottomRow];
                tileNum2 = gp.tm.mapTitleNum[entityRightColumn][entityBottomRow];
                if (gp.tm.tile[tileNum1].collision || gp.tm.tile[tileNum2].collision) {
                    entity.collision = true;
                }
                break;
            case "LEFT":
                // Check for collision with tiles to the left of the Entity
                entityLeftColumn = (entityLeftWorldX - entity.speed) / gp.TILESIZE;
                tileNum1 = gp.tm.mapTitleNum[entityLeftColumn][entityTopRow];
                tileNum2 = gp.tm.mapTitleNum[entityLeftColumn][entityBottomRow];
                if (gp.tm.tile[tileNum1].collision || gp.tm.tile[tileNum2].collision) {
                    entity.collision = true;
                }
                break;
            case "RIGHT":
                // Check for collision with tiles to the right of the Entity
                entityRightColumn = (entityRightWorldX + entity.speed) / gp.TILESIZE;
                tileNum1 = gp.tm.mapTitleNum[entityRightColumn][entityTopRow];
                tileNum2 = gp.tm.mapTitleNum[entityRightColumn][entityBottomRow];
                if (gp.tm.tile[tileNum1].collision || gp.tm.tile[tileNum2].collision) {
                    entity.collision = true;
                }
                break;

        }
    }

    /**
     * 
     * Checks for collisions between a given Entity and other Entities in the game
     * world.
     * 
     * @param entity   the Entity to check for collisions
     * @param targets  an array of Entities to check against for collisions
     * @param impostor a boolean indicating whether the entity is an impostor or not
     * @return the index of the colliding Entity in the targets array, or -1 if no
     *         collision occurred
     */
    public int checkObjectCollision(Entitiy entitiy, boolean player) {
        int index = 9999;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                // get the position of the object
                entitiy.collisionRectangle.x = entitiy.worldX + entitiy.collisionRectangle.x;
                entitiy.collisionRectangle.y = entitiy.worldY + entitiy.collisionRectangle.y;

                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entitiy.direction) {
                    case "UP":
                        entitiy.collisionRectangle.y -= entitiy.speed;
                        if (entitiy.collisionRectangle.intersects(gp.obj[i].solidArea)) {

                            if (gp.obj[i].collision == true) {
                                entitiy.collision = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "DOWN":
                        entitiy.collisionRectangle.y += entitiy.speed;
                        entitiy.collisionRectangle.y -= entitiy.speed;
                        if (entitiy.collisionRectangle.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entitiy.collision = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "LEFT":
                        entitiy.collisionRectangle.x -= entitiy.speed;
                        entitiy.collisionRectangle.y -= entitiy.speed;
                        if (entitiy.collisionRectangle.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entitiy.collision = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                    case "RIGHT":
                        entitiy.collisionRectangle.x += entitiy.speed;
                        entitiy.collisionRectangle.y -= entitiy.speed;
                        if (entitiy.collisionRectangle.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entitiy.collision = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        break;
                }
                entitiy.collisionRectangle.x = entitiy.solidAreaDefaultX;
                entitiy.collisionRectangle.y = entitiy.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;

            }
        }

        return index;
    }

    /**
     * 
     * Checks for collisions between a given Entity and other Entities in the game
     * world.
     * 
     * @param entity   the Entity to check for collisions
     * @param targets  an array of Entities to check against for collisions
     * @param impostor a boolean indicating whether the entity is an impostor or not
     * @return the index of the colliding Entity in the targets array, or -1 if no
     *         collision occurred
     */
    public int checkEntity(Entitiy entity, Entitiy[] targets, boolean impostor) {
        int index = -1;
        try {
            for (int i = 0; i < targets.length; i++) {
                if (targets[i] != null) {
                    // Create temporary rectangle and area variables for each entity
                    Rectangle entityRect = new Rectangle(entity.collisionRectangle);
                    Rectangle targetRect = new Rectangle(targets[i].solidArea);

                    // Translate rectangles to world coordinates
                    entityRect.translate(entity.worldX, entity.worldY);
                    targetRect.translate(targets[i].worldX, targets[i].worldY);

                    switch (entity.direction) {
                        case "UP":
                            entityRect.translate(0, -entity.speed);
                            if (entityRect.intersects(targetRect)) {
                                if (impostor) {
                                    return i;
                                }
                            }
                            break;
                        case "DOWN":
                            Rectangle tempRect = new Rectangle(entityRect);
                            tempRect.translate(0, entity.speed);
                            if (tempRect.intersects(targetRect)) {
                                if (impostor) {
                                    return i;
                                }
                            }
                            break;
                        case "LEFT":
                            entityRect.translate(-entity.speed, 0);
                            if (entityRect.intersects(targetRect)) {
                                if (impostor) {
                                    return i;
                                }
                            }
                            break;
                        case "RIGHT":
                            entityRect.translate(entity.speed, 0);
                            if (entityRect.intersects(targetRect)) {
                                if (impostor) {
                                    return i;
                                }
                            }
                            break;
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return -1;
        }

        return index;
    }

}
