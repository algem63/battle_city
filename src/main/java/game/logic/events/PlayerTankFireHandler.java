/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.logic.events;

import game.gui.GameFieldPanel;
import game.logic.Constants;
import game.objects.MapObject;
import game.objects.Shell;
import game.objects.tank.AbstractTank;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Павел
 */
public class PlayerTankFireHandler extends KeyAdapter implements Runnable {

    private AbstractTank playerTank;
    private GameFieldPanel gameFieldPanel;
    private Shell shell, shell2;
    private int currentBullets;
//    TODO проверить, нужен ли этот класс и выпилить если не нужен
    public PlayerTankFireHandler(AbstractTank playerTank, GameFieldPanel gameFieldPanel) {
        this.playerTank = playerTank;
        this.gameFieldPanel = gameFieldPanel;
    }

    @Override
    public void run() {
        playerTank.addKeyListener(this);
    }

    public void keyPressed(KeyEvent e) {
        if (!playerTank.isFireKeyPressed() && playerTank.getCurrentBullets() < playerTank.getMaxBullets()) {
            gameFieldPanel.battleField.increaseShotsCountForPlayerOne();
            gameFieldPanel.battleField.increaseTotalShotsCountForPlayerOne();
            boolean closeShot = false;
            Rectangle shell1Bounds = null, shell2Bounds = null;
            Component[] components = gameFieldPanel.getComponents();
            if (playerTank.getDirection() == Constants.UP) {
				// РїСЂРѕРІРµСЂРєР° РІС‹СЃС‚СЂРµР»Р° РІ РєРёСЂРїРёС‡ РІ
                // СѓРїРѕСЂ
                for (Component component : components) {
                    if (component instanceof MapObject) {
                        MapObject mapObject = (MapObject) component;
                        if (mapObject.getImageNum() == 1
                                && mapObject.getX() == playerTank.getX()
                                + Constants.tankSize / 2
                                - Constants.brickSize / 2
                                && mapObject.getY() == playerTank.getY()
                                - Constants.brickSize) {
                            gameFieldPanel.remove(mapObject);
                            gameFieldPanel
                                    .removeHorizontalLineOfBlocks(
                                            mapObject.getX(),
                                            mapObject.getY(),
                                            Shell.CENTER);
                            gameFieldPanel.repaint(mapObject.getX());
                            gameFieldPanel.drawExpolde(playerTank.getX()
                                    + Constants.tankSize / 2
                                    - Constants.explosionSize / 2,
                                    playerTank.getY() - Constants.brickSize,
                                    Constants.SMALL_EXPLOSION_CODE,
                                    Constants.explosionSize);
                            gameFieldPanel.repaint(playerTank.getX()
                                    + Constants.tankSize / 2
                                    - Constants.explosionSize / 2,
                                    playerTank.getY() - Constants.brickSize,
                                    Constants.explosionSize,
                                    Constants.explosionSize);
                            closeShot = true;
                            break;
                        }
                    } else if (component instanceof AbstractTank) {
                        AbstractTank tank = (AbstractTank) component;
                        if (!tank.isPlayerControlled()) {
                            if (!playerTank.isDoubleShot()) {
                                shell1Bounds = new Rectangle(playerTank.getX()
                                        + Constants.tankSize / 2
                                        - Constants.shellSize / 2,
                                        playerTank.getY() - Constants.shellSize,
                                        Constants.shellSize,
                                        Constants.shellSize);
                                if (shell1Bounds.intersects(tank
                                        .getBounds())) {
                                    gameFieldPanel.battleField.increaseShotsCountForPlayerOne();
                                    gameFieldPanel.battleField.increaseTotalShotsCountForPlayerOne();
                                    gameFieldPanel.destroyEnemyTank(tank);
                                    closeShot = true;
                                }
                            } else {
                                shell1Bounds = new Rectangle(playerTank.getX()
                                        + Constants.tankSize / 2
                                        - Constants.shellSize / 2
                                        - Constants.shellSize, playerTank.getY()
                                        - Constants.shellSize,
                                        Constants.shellSize,
                                        Constants.shellSize);
                                shell2Bounds = new Rectangle(playerTank.getX()
                                        + Constants.tankSize / 2
                                        + Constants.shellSize / 2,
                                        playerTank.getY() - Constants.shellSize,
                                        Constants.shellSize,
                                        Constants.shellSize);
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
                                + Constants.tankSize / 2
                                - Constants.shellSize / 2, playerTank.getY()
                                - Constants.shellSize,
                                Constants.shellSize,
                                Constants.shellSize);
                    } else {
                        shell1Bounds = new Rectangle(playerTank.getX()
                                + Constants.tankSize / 2
                                - Constants.shellSize / 2
                                - Constants.shellSize, playerTank.getY()
                                - Constants.shellSize,
                                Constants.shellSize,
                                Constants.shellSize);
                        shell2Bounds = new Rectangle(playerTank.getX()
                                + Constants.tankSize / 2
                                + Constants.shellSize / 2, playerTank.getY()
                                - Constants.shellSize,
                                Constants.shellSize,
                                Constants.shellSize);
                    }
                }
            } else if (playerTank.getDirection() == Constants.DOWN) {
                for (Component component : components) {
                    if (component instanceof MapObject) {
                        MapObject mapObject = (MapObject) component;
                        if (mapObject.getImageNum() == 1
                                && mapObject.getX() == playerTank.getX()
                                + Constants.tankSize / 2
                                - Constants.brickSize / 2
                                && mapObject.getY() == playerTank.getY()
                                + Constants.tankSize) {
                            gameFieldPanel.remove(mapObject);
                            gameFieldPanel
                                    .removeHorizontalLineOfBlocks(
                                            mapObject.getX(),
                                            mapObject.getY(),
                                            Shell.CENTER);
                            gameFieldPanel.repaint(mapObject.getX());
                            gameFieldPanel.drawExpolde(playerTank.getX()
                                    + Constants.tankSize / 2
                                    - Constants.explosionSize / 2,
                                    playerTank.getY() + Constants.tankSize,
                                    Constants.SMALL_EXPLOSION_CODE,
                                    Constants.explosionSize);
                            gameFieldPanel.repaint(playerTank.getX()
                                    + Constants.tankSize / 2
                                    - Constants.explosionSize / 2,
                                    playerTank.getY() + Constants.tankSize,
                                    Constants.explosionSize,
                                    Constants.explosionSize);
                            closeShot = true;
                            break;
                        }
                    } else if (component instanceof AbstractTank) {
                        AbstractTank tank = (AbstractTank) component;
                        if (!tank.isPlayerControlled()) {
                            if (!playerTank.isDoubleShot()) {
                                shell1Bounds = new Rectangle(playerTank.getX()
                                        + Constants.tankSize / 2
                                        - Constants.shellSize / 2,
                                        playerTank.getY() + Constants.tankSize,
                                        Constants.shellSize,
                                        Constants.shellSize);
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
                                        + Constants.tankSize / 2
                                        - Constants.shellSize / 2
                                        - Constants.shellSize, playerTank.getY()
                                        + Constants.tankSize,
                                        Constants.shellSize,
                                        Constants.shellSize);
                                shell2Bounds = new Rectangle(playerTank.getX()
                                        + Constants.tankSize / 2
                                        + Constants.shellSize / 2,
                                        playerTank.getY() + Constants.tankSize,
                                        Constants.shellSize,
                                        Constants.shellSize);
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
                                + Constants.tankSize / 2
                                - Constants.shellSize / 2, playerTank.getY()
                                + Constants.tankSize,
                                Constants.shellSize,
                                Constants.shellSize);
                    } else {
                        shell1Bounds = new Rectangle(playerTank.getX()
                                + Constants.tankSize / 2
                                - Constants.shellSize / 2
                                - Constants.shellSize, playerTank.getY()
                                + Constants.tankSize,
                                Constants.shellSize,
                                Constants.shellSize);
                        shell2Bounds = new Rectangle(playerTank.getX()
                                + Constants.tankSize / 2
                                + Constants.shellSize / 2, playerTank.getY()
                                + Constants.tankSize,
                                Constants.shellSize,
                                Constants.shellSize);
                    }
                }
            } else if (playerTank.getDirection() == Constants.LEFT) {
                for (Component component : components) {
                    if (component instanceof MapObject) {
                        MapObject mapObject = (MapObject) component;
                        if (mapObject.getImageNum() == 1
                                && mapObject.getX() == playerTank.getX()
                                - Constants.brickSize
                                && mapObject.getY() == playerTank.getY()
                                + Constants.tankSize / 2
                                - Constants.brickSize / 2) {
                            gameFieldPanel.remove(mapObject);
                            // !! С‚СѓС‚ С‚РѕС‡РЅРѕ С†РµРЅС‚СЂ?
                            gameFieldPanel.removeVerticalLineOfBlocks(
                                    mapObject.getX(), mapObject.getY(),
                                    Shell.CENTER);
                            gameFieldPanel.repaint(mapObject
                                    .getBounds());
                            gameFieldPanel.drawExpolde(playerTank.getX()
                                    - Constants.brickSize, playerTank.getY()
                                    + Constants.tankSize / 2
                                    - Constants.explosionSize / 2,
                                    Constants.SMALL_EXPLOSION_CODE,
                                    Constants.explosionSize);
                            gameFieldPanel.repaint(playerTank.getX()
                                    - Constants.brickSize, playerTank.getY()
                                    + Constants.tankSize / 2
                                    - Constants.explosionSize / 2,
                                    Constants.explosionSize,
                                    Constants.explosionSize);
                            closeShot = true;
                            break;
                        }
                    } else if (component instanceof AbstractTank) {
                        AbstractTank tank = (AbstractTank) component;
                        if (!tank.isPlayerControlled()) {
                            if (!playerTank.isDoubleShot()) {
                                shell1Bounds = new Rectangle(playerTank.getX()
                                        - Constants.shellSize, playerTank.getY()
                                        + Constants.tankSize / 2
                                        - Constants.shellSize / 2,
                                        Constants.shellSize,
                                        Constants.shellSize);
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
                                        - Constants.shellSize, playerTank.getY()
                                        + Constants.tankSize / 2
                                        - Constants.shellSize / 2
                                        - Constants.shellSize,
                                        Constants.shellSize,
                                        Constants.shellSize);
                                shell2Bounds = new Rectangle(playerTank.getX()
                                        - Constants.shellSize, playerTank.getY()
                                        + Constants.tankSize / 2
                                        + Constants.shellSize / 2,
                                        Constants.shellSize,
                                        Constants.shellSize);
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
                                - Constants.shellSize, playerTank.getY()
                                + Constants.tankSize / 2
                                - Constants.shellSize / 2,
                                Constants.shellSize,
                                Constants.shellSize);
                    } else {
                        shell1Bounds = new Rectangle(playerTank.getX()
                                - Constants.shellSize, playerTank.getY()
                                + Constants.tankSize / 2
                                - Constants.shellSize / 2
                                - Constants.shellSize,
                                Constants.shellSize,
                                Constants.shellSize);
                        shell2Bounds = new Rectangle(playerTank.getX()
                                - Constants.shellSize, playerTank.getY()
                                + Constants.tankSize / 2
                                + Constants.shellSize / 2,
                                Constants.shellSize,
                                Constants.shellSize);
                    }
                }
            } else if (playerTank.getDirection() == Constants.RIGHT) {
                for (Component component : components) {
                    if (component instanceof MapObject) {
                        MapObject mapObject = (MapObject) component;
                        if (mapObject.getImageNum() == 1
                                && mapObject.getX() == playerTank.getX()
                                + Constants.tankSize
                                && mapObject.getY() == playerTank.getY()
                                + Constants.tankSize / 2
                                - Constants.brickSize / 2) {
                            gameFieldPanel.remove(mapObject);
                            gameFieldPanel.removeVerticalLineOfBlocks(
                                    mapObject.getX(), mapObject.getY(),
                                    Shell.CENTER);
                            gameFieldPanel.repaint(mapObject
                                    .getBounds());
                            gameFieldPanel.drawExpolde(playerTank.getX()
                                    + Constants.tankSize, playerTank.getY()
                                    + Constants.tankSize / 2
                                    - Constants.explosionSize / 2,
                                    Constants.SMALL_EXPLOSION_CODE,
                                    Constants.explosionSize);
                            gameFieldPanel.repaint(playerTank.getX()
                                    + Constants.tankSize, playerTank.getY()
                                    + Constants.tankSize / 2
                                    - Constants.explosionSize / 2,
                                    Constants.explosionSize,
                                    Constants.explosionSize);
                            closeShot = true;
                            break;
                        } // Проверяем столкновение пули с танком при
                        // выстреле в упор.
                    } else if (component instanceof AbstractTank) {
                        AbstractTank tank = (AbstractTank) component;
                        if (!tank.isPlayerControlled()) {
                            if (!playerTank.isDoubleShot()) {
                                shell1Bounds = new Rectangle(playerTank.getX()
                                        + Constants.tankSize, playerTank.getY()
                                        + Constants.tankSize / 2
                                        - Constants.shellSize / 2,
                                        Constants.shellSize,
                                        Constants.shellSize);
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
                                        + Constants.tankSize, playerTank.getY()
                                        + Constants.shellSize,
                                        Constants.shellSize,
                                        Constants.shellSize);
                                shell2Bounds = new Rectangle(playerTank.getX()
                                        + Constants.tankSize, playerTank.getY()
                                        + Constants.tankSize
                                        - Constants.shellSize * 2,
                                        Constants.shellSize,
                                        Constants.shellSize);
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
                                + Constants.tankSize, playerTank.getY()
                                + Constants.tankSize / 2
                                - Constants.shellSize / 2,
                                Constants.shellSize,
                                Constants.shellSize);
                    } else {
                        shell1Bounds = new Rectangle(playerTank.getX()
                                + Constants.tankSize, playerTank.getY()
                                + Constants.shellSize,
                                Constants.shellSize,
                                Constants.shellSize);
                        shell2Bounds = new Rectangle(playerTank.getX()
                                + Constants.tankSize, playerTank.getY()
                                + Constants.tankSize
                                - Constants.shellSize * 2,
                                Constants.shellSize,
                                Constants.shellSize);
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
				// !! РїРµСЂРµРґРµР»Р°С‚СЊ РґР»СЏ РґРІСѓС…
                // РёРіСЂРѕРєРѕРІ
                gameFieldPanel.battleField
                        .increaseShotsCountForPlayerOne();
                gameFieldPanel.battleField
                        .increaseTotalShotsCountForPlayerOne();
            }

        }
    }
}
