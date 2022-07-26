package core;

import entity.Entity;

public class CollisionChecker {

    private final GamePanel gamePanel;

    /**
     * Class to handle collision checking between various entities and the world
     *
     * @param gamePanel object
     */
    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Compares entity's next position to tiles and determines if a movement is permissible.
     * entity.setCollisionHappening is set to true if a collision is imminent and movement is blocked.
     *
     * @param entity Entity that is being compared to its surrounding tiles.
     */
    public void checkTile(Entity entity) {
        // represents the left edge of the entity collision bounds.
        int eLeftWorldX = entity.getWorldX() + entity.getCollisionBoundsX();

        // represents the right edge of the entity collision bounds.
        int eRightWorldX = entity.getWorldX() + entity.getCollisionBoundsX() + entity.getCollisionBoundsWidth();

        // represents the top edge of the entity collision bounds.
        int eTopWorldY = entity.getWorldY() + entity.getCollisionBoundsY();

        // represents the bottom edge of the entity collision bounds.
        int eBottomWorldY = entity.getWorldY() + entity.getCollisionBoundsY() + entity.getCollisionBoundsHeight();

        // represents the column of tiles which contains the left edge of the entity collision bounds
        int eLeftCol = eLeftWorldX / gamePanel.getTileSize();

        // represents the column of tiles which contains the right edge of the entity collision bounds
        int eRightCol = eRightWorldX / gamePanel.getTileSize();

        // represents the row of tiles which contains the top edge of the entity collision bounds
        int eTopRow = eTopWorldY / gamePanel.getTileSize();

        // represents the row of tiles which contains the bottom edge of the entity collision bounds
        int eBottomRow = eBottomWorldY / gamePanel.getTileSize();

        int tile1;
        int tile2;

        /*
        When moving in any direction the 2 ends of the edge facing that direction are the only
        2 points that must be checked for collisions. This switch assigns the values from the collision map of the
        2 tiles being checked to tile 1 and tile 2. if either are 1 (representing unpassable) then a collision
        is triggered and the entity is blocked from moving.
         */
        switch (entity.getDirection()) {
            case UP -> {
                eTopRow = (eTopWorldY - Entity.COLLISION_CHECK_DISTANCE) / gamePanel.getTileSize();

                if (eTopRow <= 0) {
                    entity.setCollisionHappening(true);
                    break;
                }

                tile1 = gamePanel.getMapManager().getCollisionMap()[eLeftCol][eTopRow];
                tile2 = gamePanel.getMapManager().getCollisionMap()[eRightCol][eTopRow];

                if (tile1 == 1 || tile2 == 1) {
                    entity.setCollisionHappening(true);
                }
            }
            case DOWN -> {
                eBottomRow = (eBottomWorldY + Entity.COLLISION_CHECK_DISTANCE) / gamePanel.getTileSize();

                if (eBottomRow >= gamePanel.getMapManager().getCurrentMap().getHeight()) {
                    entity.setCollisionHappening(true);
                    break;
                }

                tile1 = gamePanel.getMapManager().getCollisionMap()[eLeftCol][eBottomRow];
                tile2 = gamePanel.getMapManager().getCollisionMap()[eRightCol][eBottomRow];

                if (tile1 == 1 || tile2 == 1) {
                    entity.setCollisionHappening(true);
                }
            }
            case RIGHT -> {
                eRightCol = (eRightWorldX + Entity.COLLISION_CHECK_DISTANCE) / gamePanel.getTileSize();

                if (eRightCol >= gamePanel.getMapManager().getCurrentMap().getWidth()) {
                    entity.setCollisionHappening(true);
                    break;
                }

                tile1 = gamePanel.getMapManager().getCollisionMap()[eRightCol][eTopRow];
                tile2 = gamePanel.getMapManager().getCollisionMap()[eRightCol][eBottomRow];

                if (tile1 == 1 || tile2 == 1) {
                    entity.setCollisionHappening(true);
                }
            }
            case LEFT -> {
                eLeftCol = (eLeftWorldX - Entity.COLLISION_CHECK_DISTANCE) / gamePanel.getTileSize();

                if (eLeftCol <= 0) {
                    entity.setCollisionHappening(true);
                    break;
                }

                tile1 = gamePanel.getMapManager().getCollisionMap()[eLeftCol][eTopRow];
                tile2 = gamePanel.getMapManager().getCollisionMap()[eLeftCol][eBottomRow];

                if (tile1 == 1 || tile2 == 1) {
                    entity.setCollisionHappening(true);
                }
            }
        }
    }
}
