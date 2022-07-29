package core;

import entity.Entity;

public class CollisionChecker {

    private GamePanel gamepanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamepanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int eLeftWorldX = entity.getWorldX() + entity.getCollisionBoundsX();
        int eRightWorldX = entity.getWorldX() + entity.getCollisionBoundsX() + entity.getCollisionBoundsWidth();
        int eTopWorldY = entity.getWorldY() + entity.getCollisionBoundsY();
        int eBottomWorldY = entity.getWorldY() + entity.getCollisionBoundsY() + entity.getCollisionBoundsHeight();

        int eLeftCol = eLeftWorldX / ScreenVar.TILE_SIZE.getValue();
        int eRightCol = eRightWorldX / ScreenVar.TILE_SIZE.getValue();
        int eTopRow = eTopWorldY / ScreenVar.TILE_SIZE.getValue();
        int eBottomRow = eBottomWorldY / ScreenVar.TILE_SIZE.getValue();

        int tile1, tile2;

        switch (entity.getDirection()) {
            case UP: {
                eTopRow = (eTopWorldY - Entity.CollisionCheckDistance) / ScreenVar.TILE_SIZE.getValue();
                tile1 = gamepanel.getMapManager().getCollisionMap()[eLeftCol][eTopRow];
                tile2 = gamepanel.getMapManager().getCollisionMap()[eRightCol][eTopRow];

                if (tile1 == 1 || tile2 == 1) {
                    entity.setCollisionHappening(true);
                }
                break;
            }
            case DOWN: {
                eBottomRow = (eBottomWorldY + Entity.CollisionCheckDistance) / ScreenVar.TILE_SIZE.getValue();
                tile1 = gamepanel.getMapManager().getCollisionMap()[eLeftCol][eBottomRow];
                tile2 = gamepanel.getMapManager().getCollisionMap()[eRightCol][eBottomRow];

                if (tile1 == 1 || tile2 == 1) {
                    entity.setCollisionHappening(true);
                }
                break;
            }
            case RIGHT: {
                eRightCol = (eRightWorldX + Entity.CollisionCheckDistance) / ScreenVar.TILE_SIZE.getValue();
                tile1 = gamepanel.getMapManager().getCollisionMap()[eRightCol][eTopRow];
                tile2 = gamepanel.getMapManager().getCollisionMap()[eRightCol][eBottomRow];

                if (tile1 == 1 || tile2 == 1) {
                    entity.setCollisionHappening(true);
                }
                break;
            }
            case LEFT: {
                eLeftCol = (eLeftWorldX - Entity.CollisionCheckDistance) / ScreenVar.TILE_SIZE.getValue();
                tile1 = gamepanel.getMapManager().getCollisionMap()[eLeftCol][eTopRow];
                tile2 = gamepanel.getMapManager().getCollisionMap()[eLeftCol][eBottomRow];

                if (tile1 == 1 || tile2 == 1) {
                    entity.setCollisionHappening(true);
                }
                break;
            }
        }
    }
}
