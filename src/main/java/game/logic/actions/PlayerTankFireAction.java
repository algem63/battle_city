package game.logic.actions;

import game.gui.game.GameFieldPanel;
import game.logic.Constants;
import game.objects.MapObject;
import game.objects.Shell;
import game.objects.tank.AbstractTank;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
//TODO Возможно, этот класс можно удалить совсем
public class PlayerTankFireAction extends AbstractAction {

    private AbstractTank playerTank;
    private GameFieldPanel gameFieldPanel;
    private Shell shell, shell2;
    private int currentBullets, maxBullets;

    public PlayerTankFireAction(AbstractTank playerTank, GameFieldPanel gameFieldPanel) {
        super();
        this.playerTank = playerTank;
        this.gameFieldPanel = gameFieldPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!playerTank.isFireKeyPressed() && playerTank.getCurrentBullets() < playerTank.getMaxBullets()) {
            gameFieldPanel.battleField.increaseShotsCountForPlayerOne();
            gameFieldPanel.battleField.increaseTotalShotsCountForPlayerOne();
            boolean closeShot = false;
            Rectangle shell1Bounds = null, shell2Bounds = null;
            Component[] components = gameFieldPanel.getComponents();
            if (playerTank.getDirection() == Constants.UP) {	
                for (Component component : components) {
                    if (component instanceof MapObject) {
                        MapObject mapObject = (MapObject) component;
                        if (mapObject.getImageNum() == 1
                                && mapObject.getX() == playerTank.getX()
                                + Constants.TANK_SIZE / 2
                                - Constants.BRICK_SIZE / 2
                                && mapObject.getY() == playerTank.getY()
                                - Constants.BRICK_SIZE) {
                            gameFieldPanel.remove(mapObject);
                            gameFieldPanel
                                    .removeHorizontalLineOfBlocks(
                                            mapObject.getX(),
                                            mapObject.getY(),
                                            Shell.CENTER);
                            gameFieldPanel.repaint(mapObject.getX());
                            gameFieldPanel.drawExpolde(playerTank.getX()
                                    + Constants.TANK_SIZE / 2
                                    - Constants.EXPLOSION_SIZE / 2,
                                    playerTank.getY() - Constants.BRICK_SIZE,
                                    Constants.SMALL_EXPLOSION_CODE,
                                    Constants.EXPLOSION_SIZE);
                            gameFieldPanel.repaint(playerTank.getX()
                                    + Constants.TANK_SIZE / 2
                                    - Constants.EXPLOSION_SIZE / 2,
                                    playerTank.getY() - Constants.BRICK_SIZE,
                                    Constants.EXPLOSION_SIZE,
                                    Constants.EXPLOSION_SIZE);
                            closeShot = true;
                            break;
                        }
                    } else if (component instanceof AbstractTank) {
                        AbstractTank tank = (AbstractTank) component;
                        if (!tank.isPlayerControlled()) {
                            if (!playerTank.isDoubleShot()) {
                                shell1Bounds = new Rectangle(playerTank.getX()
                                        + Constants.TANK_SIZE / 2
                                        - Constants.SHELL_SIZE / 2,
                                        playerTank.getY() - Constants.SHELL_SIZE,
                                        Constants.SHELL_SIZE,
                                        Constants.SHELL_SIZE);
                                if (shell1Bounds.intersects(tank
                                        .getBounds())) {
                                    gameFieldPanel.battleField.increaseShotsCountForPlayerOne();
                                    gameFieldPanel.battleField.increaseTotalShotsCountForPlayerOne();
                                    gameFieldPanel.destroyEnemyTank(tank);
                                    closeShot = true;
                                }
                            } else {
                                shell1Bounds = new Rectangle(playerTank.getX()
                                        + Constants.TANK_SIZE / 2
                                        - Constants.SHELL_SIZE / 2
                                        - Constants.SHELL_SIZE, playerTank.getY()
                                        - Constants.SHELL_SIZE,
                                        Constants.SHELL_SIZE,
                                        Constants.SHELL_SIZE);
                                shell2Bounds = new Rectangle(playerTank.getX()
                                        + Constants.TANK_SIZE / 2
                                        + Constants.SHELL_SIZE / 2,
                                        playerTank.getY() - Constants.SHELL_SIZE,
                                        Constants.SHELL_SIZE,
                                        Constants.SHELL_SIZE);
                                if (shell1Bounds.intersects(tank
                                        .getBounds())
                                        || shell2Bounds.intersects(tank
                                                .getBounds())) {
                                    gameFieldPanel.battleField
                                            .increaseShotsCountForPlayerOne();
                                    gameFieldPanel.battleField
                                            .increaseTotalShotsCountForPlayerOne();
                                    gameFieldPanel
                                            .destroyEnemyTank(tank);
                                    closeShot = true;
                                }
                            }
                        }
                    }
                }
                if (!closeShot) {
                    if (!playerTank.isDoubleShot()) {
                        shell1Bounds = new Rectangle(playerTank.getX()
                                + Constants.TANK_SIZE / 2
                                - Constants.SHELL_SIZE / 2, playerTank.getY()
                                - Constants.SHELL_SIZE,
                                Constants.SHELL_SIZE,
                                Constants.SHELL_SIZE);
                    } else {
                        shell1Bounds = new Rectangle(playerTank.getX()
                                + Constants.TANK_SIZE / 2
                                - Constants.SHELL_SIZE / 2
                                - Constants.SHELL_SIZE, playerTank.getY()
                                - Constants.SHELL_SIZE,
                                Constants.SHELL_SIZE,
                                Constants.SHELL_SIZE);
                        shell2Bounds = new Rectangle(playerTank.getX()
                                + Constants.TANK_SIZE / 2
                                + Constants.SHELL_SIZE / 2, playerTank.getY()
                                - Constants.SHELL_SIZE,
                                Constants.SHELL_SIZE,
                                Constants.SHELL_SIZE);
                    }
                }
            } else if (playerTank.getDirection() == Constants.DOWN) {
                for (Component component : components) {
                    if (component instanceof MapObject) {
                        MapObject mapObject = (MapObject) component;
                        if (mapObject.getImageNum() == 1
                                && mapObject.getX() == playerTank.getX()
                                + Constants.TANK_SIZE / 2
                                - Constants.BRICK_SIZE / 2
                                && mapObject.getY() == playerTank.getY()
                                + Constants.TANK_SIZE) {
                            gameFieldPanel.remove(mapObject);
                            gameFieldPanel
                                    .removeHorizontalLineOfBlocks(
                                            mapObject.getX(),
                                            mapObject.getY(),
                                            Shell.CENTER);
                            gameFieldPanel.repaint(mapObject.getX());
                            gameFieldPanel.drawExpolde(playerTank.getX()
                                    + Constants.TANK_SIZE / 2
                                    - Constants.EXPLOSION_SIZE / 2,
                                    playerTank.getY() + Constants.TANK_SIZE,
                                    Constants.SMALL_EXPLOSION_CODE,
                                    Constants.EXPLOSION_SIZE);
                            gameFieldPanel.repaint(playerTank.getX()
                                    + Constants.TANK_SIZE / 2
                                    - Constants.EXPLOSION_SIZE / 2,
                                    playerTank.getY() + Constants.TANK_SIZE,
                                    Constants.EXPLOSION_SIZE,
                                    Constants.EXPLOSION_SIZE);
                            closeShot = true;
                            break;
                        }
                    } else if (component instanceof AbstractTank) {
                        AbstractTank tank = (AbstractTank) component;
                        if (!tank.isPlayerControlled()) {
                            if (!playerTank.isDoubleShot()) {
                                shell1Bounds = new Rectangle(playerTank.getX()
                                        + Constants.TANK_SIZE / 2
                                        - Constants.SHELL_SIZE / 2,
                                        playerTank.getY() + Constants.TANK_SIZE,
                                        Constants.SHELL_SIZE,
                                        Constants.SHELL_SIZE);
                                if (shell1Bounds.intersects(tank
                                        .getBounds())) {
                                    gameFieldPanel.battleField
                                            .increaseShotsCountForPlayerOne();
                                    gameFieldPanel.battleField
                                            .increaseTotalShotsCountForPlayerOne();
                                    gameFieldPanel
                                            .destroyEnemyTank(tank);
                                    closeShot = true;
                                }
                            } else {
                                shell1Bounds = new Rectangle(playerTank.getX()
                                        + Constants.TANK_SIZE / 2
                                        - Constants.SHELL_SIZE / 2
                                        - Constants.SHELL_SIZE, playerTank.getY()
                                        + Constants.TANK_SIZE,
                                        Constants.SHELL_SIZE,
                                        Constants.SHELL_SIZE);
                                shell2Bounds = new Rectangle(playerTank.getX()
                                        + Constants.TANK_SIZE / 2
                                        + Constants.SHELL_SIZE / 2,
                                        playerTank.getY() + Constants.TANK_SIZE,
                                        Constants.SHELL_SIZE,
                                        Constants.SHELL_SIZE);
                                if (shell1Bounds.intersects(tank
                                        .getBounds())
                                        || shell2Bounds.intersects(tank
                                                .getBounds())) {
                                    gameFieldPanel.battleField
                                            .increaseShotsCountForPlayerOne();
                                    gameFieldPanel.battleField
                                            .increaseTotalShotsCountForPlayerOne();
                                    gameFieldPanel
                                            .destroyEnemyTank(tank);
                                    closeShot = true;
                                }
                            }
                        }
                    }
                }
                if (!closeShot) {
                    if (!playerTank.isDoubleShot()) {
                        shell1Bounds = new Rectangle(playerTank.getX()
                                + Constants.TANK_SIZE / 2
                                - Constants.SHELL_SIZE / 2, playerTank.getY()
                                + Constants.TANK_SIZE,
                                Constants.SHELL_SIZE,
                                Constants.SHELL_SIZE);
                    } else {
                        shell1Bounds = new Rectangle(playerTank.getX()
                                + Constants.TANK_SIZE / 2
                                - Constants.SHELL_SIZE / 2
                                - Constants.SHELL_SIZE, playerTank.getY()
                                + Constants.TANK_SIZE,
                                Constants.SHELL_SIZE,
                                Constants.SHELL_SIZE);
                        shell2Bounds = new Rectangle(playerTank.getX()
                                + Constants.TANK_SIZE / 2
                                + Constants.SHELL_SIZE / 2, playerTank.getY()
                                + Constants.TANK_SIZE,
                                Constants.SHELL_SIZE,
                                Constants.SHELL_SIZE);
                    }
                }
            } else if (playerTank.getDirection() == Constants.LEFT) {
                for (Component component : components) {
                    if (component instanceof MapObject) {
                        MapObject mapObject = (MapObject) component;
                        if (mapObject.getImageNum() == 1
                                && mapObject.getX() == playerTank.getX()
                                - Constants.BRICK_SIZE
                                && mapObject.getY() == playerTank.getY()
                                + Constants.TANK_SIZE / 2
                                - Constants.BRICK_SIZE / 2) {
                            gameFieldPanel.remove(mapObject);
                            // !! тут точно центр?
                            gameFieldPanel.removeVerticalLineOfBlocks(
                                    mapObject.getX(), mapObject.getY(),
                                    Shell.CENTER);
                            gameFieldPanel.repaint(mapObject
                                    .getBounds());
                            gameFieldPanel.drawExpolde(playerTank.getX()
                                    - Constants.BRICK_SIZE, playerTank.getY()
                                    + Constants.TANK_SIZE / 2
                                    - Constants.EXPLOSION_SIZE / 2,
                                    Constants.SMALL_EXPLOSION_CODE,
                                    Constants.EXPLOSION_SIZE);
                            gameFieldPanel.repaint(playerTank.getX()
                                    - Constants.BRICK_SIZE, playerTank.getY()
                                    + Constants.TANK_SIZE / 2
                                    - Constants.EXPLOSION_SIZE / 2,
                                    Constants.EXPLOSION_SIZE,
                                    Constants.EXPLOSION_SIZE);
                            closeShot = true;
                            break;
                        }
                    } else if (component instanceof AbstractTank) {
                        AbstractTank tank = (AbstractTank) component;
                        if (!tank.isPlayerControlled()) {
                            if (!playerTank.isDoubleShot()) {
                                shell1Bounds = new Rectangle(playerTank.getX()
                                        - Constants.SHELL_SIZE, playerTank.getY()
                                        + Constants.TANK_SIZE / 2
                                        - Constants.SHELL_SIZE / 2,
                                        Constants.SHELL_SIZE,
                                        Constants.SHELL_SIZE);
                                if (shell1Bounds.intersects(tank
                                        .getBounds())) {
                                    gameFieldPanel.battleField
                                            .increaseShotsCountForPlayerOne();
                                    gameFieldPanel.battleField
                                            .increaseTotalShotsCountForPlayerOne();
                                    gameFieldPanel
                                            .destroyEnemyTank(tank);
                                    closeShot = true;
                                }
                            } else {
                                shell1Bounds = new Rectangle(playerTank.getX()
                                        - Constants.SHELL_SIZE, playerTank.getY()
                                        + Constants.TANK_SIZE / 2
                                        - Constants.SHELL_SIZE / 2
                                        - Constants.SHELL_SIZE,
                                        Constants.SHELL_SIZE,
                                        Constants.SHELL_SIZE);
                                shell2Bounds = new Rectangle(playerTank.getX()
                                        - Constants.SHELL_SIZE, playerTank.getY()
                                        + Constants.TANK_SIZE / 2
                                        + Constants.SHELL_SIZE / 2,
                                        Constants.SHELL_SIZE,
                                        Constants.SHELL_SIZE);
                                if (shell1Bounds.intersects(tank
                                        .getBounds())
                                        || shell2Bounds.intersects(tank
                                                .getBounds())) {
                                    gameFieldPanel.battleField
                                            .increaseShotsCountForPlayerOne();
                                    gameFieldPanel.battleField
                                            .increaseTotalShotsCountForPlayerOne();
                                    gameFieldPanel
                                            .destroyEnemyTank(tank);
                                    closeShot = true;
                                }
                            }
                        }
                    }
                }
                if (!closeShot) {
                    if (!playerTank.isDoubleShot()) {
                        shell1Bounds = new Rectangle(playerTank.getX()
                                - Constants.SHELL_SIZE, playerTank.getY()
                                + Constants.TANK_SIZE / 2
                                - Constants.SHELL_SIZE / 2,
                                Constants.SHELL_SIZE,
                                Constants.SHELL_SIZE);
                    } else {
                        shell1Bounds = new Rectangle(playerTank.getX()
                                - Constants.SHELL_SIZE, playerTank.getY()
                                + Constants.TANK_SIZE / 2
                                - Constants.SHELL_SIZE / 2
                                - Constants.SHELL_SIZE,
                                Constants.SHELL_SIZE,
                                Constants.SHELL_SIZE);
                        shell2Bounds = new Rectangle(playerTank.getX()
                                - Constants.SHELL_SIZE, playerTank.getY()
                                + Constants.TANK_SIZE / 2
                                + Constants.SHELL_SIZE / 2,
                                Constants.SHELL_SIZE,
                                Constants.SHELL_SIZE);
                    }
                }
            } else if (playerTank.getDirection() == Constants.RIGHT) {
                for (Component component : components) {
                    if (component instanceof MapObject) {
                        MapObject mapObject = (MapObject) component;
                        if (mapObject.getImageNum() == 1
                                && mapObject.getX() == playerTank.getX()
                                + Constants.TANK_SIZE
                                && mapObject.getY() == playerTank.getY()
                                + Constants.TANK_SIZE / 2
                                - Constants.BRICK_SIZE / 2) {
                            gameFieldPanel.remove(mapObject);
                            gameFieldPanel.removeVerticalLineOfBlocks(
                                    mapObject.getX(), mapObject.getY(),
                                    Shell.CENTER);
                            gameFieldPanel.repaint(mapObject
                                    .getBounds());
                            gameFieldPanel.drawExpolde(playerTank.getX()
                                    + Constants.TANK_SIZE, playerTank.getY()
                                    + Constants.TANK_SIZE / 2
                                    - Constants.EXPLOSION_SIZE / 2,
                                    Constants.SMALL_EXPLOSION_CODE,
                                    Constants.EXPLOSION_SIZE);
                            gameFieldPanel.repaint(playerTank.getX()
                                    + Constants.TANK_SIZE, playerTank.getY()
                                    + Constants.TANK_SIZE / 2
                                    - Constants.EXPLOSION_SIZE / 2,
                                    Constants.EXPLOSION_SIZE,
                                    Constants.EXPLOSION_SIZE);
                            closeShot = true;
                            break;
                        } // ��������� ������������ ���� � ������ ���
                        // �������� � ����.
                    } else if (component instanceof AbstractTank) {
                        AbstractTank tank = (AbstractTank) component;
                        if (!tank.isPlayerControlled()) {
                            if (!playerTank.isDoubleShot()) {
                                shell1Bounds = new Rectangle(playerTank.getX()
                                        + Constants.TANK_SIZE, playerTank.getY()
                                        + Constants.TANK_SIZE / 2
                                        - Constants.SHELL_SIZE / 2,
                                        Constants.SHELL_SIZE,
                                        Constants.SHELL_SIZE);
                                if (shell1Bounds.intersects(tank
                                        .getBounds())) {
                                    gameFieldPanel.battleField
                                            .increaseShotsCountForPlayerOne();
                                    gameFieldPanel.battleField
                                            .increaseTotalShotsCountForPlayerOne();
                                    gameFieldPanel
                                            .destroyEnemyTank(tank);
                                    closeShot = true;
                                }
                            } else {
                                shell1Bounds = new Rectangle(playerTank.getX()
                                        + Constants.TANK_SIZE, playerTank.getY()
                                        + Constants.SHELL_SIZE,
                                        Constants.SHELL_SIZE,
                                        Constants.SHELL_SIZE);
                                shell2Bounds = new Rectangle(playerTank.getX()
                                        + Constants.TANK_SIZE, playerTank.getY()
                                        + Constants.TANK_SIZE
                                        - Constants.SHELL_SIZE * 2,
                                        Constants.SHELL_SIZE,
                                        Constants.SHELL_SIZE);
                                if (shell1Bounds.intersects(tank
                                        .getBounds())
                                        || shell2Bounds.intersects(tank
                                                .getBounds())) {
                                    gameFieldPanel.battleField
                                            .increaseShotsCountForPlayerOne();
                                    gameFieldPanel.battleField
                                            .increaseTotalShotsCountForPlayerOne();
                                    gameFieldPanel
                                            .destroyEnemyTank(tank);
                                    closeShot = true;
                                }
                            }
                        }
                    }
                }
                if (!closeShot) {
                    if (!playerTank.isDoubleShot()) {
                        shell1Bounds = new Rectangle(playerTank.getX()
                                + Constants.TANK_SIZE, playerTank.getY()
                                + Constants.TANK_SIZE / 2
                                - Constants.SHELL_SIZE / 2,
                                Constants.SHELL_SIZE,
                                Constants.SHELL_SIZE);
                    } else {
                        shell1Bounds = new Rectangle(playerTank.getX()
                                + Constants.TANK_SIZE, playerTank.getY()
                                + Constants.SHELL_SIZE,
                                Constants.SHELL_SIZE,
                                Constants.SHELL_SIZE);
                        shell2Bounds = new Rectangle(playerTank.getX()
                                + Constants.TANK_SIZE, playerTank.getY()
                                + Constants.TANK_SIZE
                                - Constants.SHELL_SIZE * 2,
                                Constants.SHELL_SIZE,
                                Constants.SHELL_SIZE);
                    }
                }
            }
            if (!closeShot) {
                shell = new Shell(gameFieldPanel, playerTank.getDirection(), playerTank, Shell.FIRST);
                shell.setBounds(shell1Bounds);
                if (playerTank.isDoubleShot()) {
                    shell2 = new Shell(gameFieldPanel, playerTank.getDirection(), playerTank, Shell.SECOND);
                    shell2.setBounds(shell2Bounds);
                    switch (playerTank.getDirection()) {
                        case Constants.UP:
                            shell.setPositionCode(Shell.V_LEFT);
                            shell2.setPositionCode(Shell.V_RIGHT);
                            break;
                        case Constants.DOWN:
                            shell.setPositionCode(Shell.V_LEFT);
                            shell2.setPositionCode(Shell.V_RIGHT);
                            break;
                        case Constants.LEFT:
                            shell.setPositionCode(Shell.H_UP);
                            shell2.setPositionCode(Shell.H_DOWN);
                            break;
                        case Constants.RIGHT:
                            shell.setPositionCode(Shell.H_UP);
                            shell2.setPositionCode(Shell.H_DOWN);
                            break;
                        default:
                            break;
                    }
                } else {
                    shell.setPositionCode(Shell.CENTER);
                }
                if (playerTank.getStarsCount() >= 3) {
                    shell.setPiercing(true);
                    if (playerTank.isDoubleShot()) {
                        shell2.setPiercing(true);
                    }
                }
                gameFieldPanel.add(shell, new Integer(2));
                gameFieldPanel.repaint(shell.getBounds());
                if (playerTank.isDoubleShot()) {
                    gameFieldPanel.add(shell2, new Integer(2));
                    gameFieldPanel.repaint(shell2.getBounds());
                }
                currentBullets++;
                playerTank.setFireKeyPressed(true);

                gameFieldPanel.battleField
                        .increaseShotsCountForPlayerOne();
                gameFieldPanel.battleField
                        .increaseTotalShotsCountForPlayerOne();
				// !! переделать для двух
                // игроков
                gameFieldPanel.battleField
                        .increaseShotsCountForPlayerOne();
                gameFieldPanel.battleField
                        .increaseTotalShotsCountForPlayerOne();
            }

        }

    }
}
