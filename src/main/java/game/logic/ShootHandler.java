package game.logic;

import game.gui.GameFieldPanel;
import game.objects.MapObject;
import game.objects.Shell;
import game.objects.tank.AbstractTank;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA. User: melnikovp Date: 30.11.12 Time: 16:45 To
 * change this template use File | Settings | File Templates.
 */
public class ShootHandler implements ActionListener {

    private AbstractTank tank;
    private GameFieldPanel gameFieldPanel;
//TODO очень сильно оптимизировать этот класс
    public ShootHandler(AbstractTank tank, GameFieldPanel gameFieldPanel) {
        this.tank = tank;
        this.gameFieldPanel = gameFieldPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean closeShot = false;
        Rectangle shell1Bounds = null, shell2Bounds = null;
        switch (tank.getDirection()) {
            case Constants.UP:
                closeShot = checkCloseShotUp();
                if (!closeShot) {
                    shell1Bounds = getFirstShellBounds();
                    if (!tank.isDoubleShot()) {
                        shell1Bounds = new Rectangle(tank.getX() + Constants.tankSize / 2 - Constants.shellSize / 2,
                                tank.getY() - Constants.shellSize,
                                Constants.shellSize,
                                Constants.shellSize);
                    } else {
                        shell1Bounds = new Rectangle(tank.getX() + Constants.tankSize / 2 - Constants.shellSize / 2 - Constants.shellSize,
                                tank.getY() - Constants.shellSize,
                                Constants.shellSize,
                                Constants.shellSize);
                        shell2Bounds = new Rectangle(tank.getX() + Constants.tankSize / 2 + Constants.shellSize / 2,
                                tank.getY() - Constants.shellSize,
                                Constants.shellSize,
                                Constants.shellSize);
                    }
                }
                break;
            case Constants.DOWN:
                closeShot = checkCloseShotDown();
                if (!closeShot) {
                    if (!tank.isDoubleShot()) {
                        shell1Bounds = new Rectangle(tank.getX() + Constants.tankSize / 2 - Constants.shellSize / 2,
                                tank.getY() + Constants.tankSize,
                                Constants.shellSize,
                                Constants.shellSize);
                    } else {
                        shell1Bounds = new Rectangle(tank.getX() + Constants.tankSize / 2 - Constants.shellSize / 2 - Constants.shellSize,
                                tank.getY() + Constants.tankSize,
                                Constants.shellSize,
                                Constants.shellSize);
                        shell2Bounds = new Rectangle(tank.getX() + Constants.tankSize / 2 + Constants.shellSize / 2,
                                tank.getY() + Constants.tankSize,
                                Constants.shellSize,
                                Constants.shellSize);
                    }
                }
                break;
            case Constants.LEFT:
                closeShot = checkCloseShotLeft();
                if (!closeShot) {
                    if (!tank.isDoubleShot()) {
                        shell1Bounds = new Rectangle(tank.getX() - Constants.shellSize,
                                tank.getY() + Constants.tankSize / 2 - Constants.shellSize / 2,
                                Constants.shellSize,
                                Constants.shellSize);
                    } else {
                        shell1Bounds = new Rectangle(tank.getX() - Constants.shellSize,
                                tank.getY() + Constants.tankSize / 2 - Constants.shellSize / 2 - Constants.shellSize,
                                Constants.shellSize,
                                Constants.shellSize);
                        shell2Bounds = new Rectangle(tank.getX() - Constants.shellSize,
                                tank.getY() + Constants.tankSize / 2 + Constants.shellSize / 2,
                                Constants.shellSize,
                                Constants.shellSize);
                    }
                }
                break;
            case Constants.RIGHT:
                closeShot = checkCloseShotRight();
                if (!closeShot) {
                    if (!tank.isDoubleShot()) {
                        shell1Bounds = new Rectangle(tank.getX() + Constants.tankSize,
                                tank.getY() + Constants.tankSize / 2 - Constants.shellSize / 2,
                                Constants.shellSize,
                                Constants.shellSize);
                    } else {
                        shell1Bounds = new Rectangle(tank.getX() + Constants.tankSize,
                                tank.getY() + Constants.shellSize,
                                Constants.shellSize,
                                Constants.shellSize);
                        shell2Bounds = new Rectangle(tank.getX() + Constants.tankSize,
                                tank.getY() + Constants.tankSize - Constants.shellSize * 2,
                                Constants.shellSize,
                                Constants.shellSize);
                    }
                }
                break;
            default:
                break;
        }       
        if (!closeShot) {
            shoot(shell2Bounds, shell1Bounds);
        }
    }

    private void shoot(Rectangle shell2Bounds, Rectangle shell1Bounds) {
        Shell shell = null, shell2 = null;
        shell = new Shell(gameFieldPanel, tank.getDirection(), tank, Shell.FIRST);
        tank.setShell(shell);
        if (tank.isDoubleShot()) {
            shell2 = new Shell(gameFieldPanel, tank.getDirection(), tank, Shell.SECOND);
            tank.setShell2(shell2);
            shell2.setBounds(shell2Bounds);
            switch (tank.getDirection()) {
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
            if (tank.getStarsCount() > 0) {
                shell2.setPiercing(true);
            }
        } else {
            shell.setPositionCode(Shell.CENTER);
        }
        shell.setBounds(shell1Bounds);
        if (tank.getStarsCount() > 0) {
            shell.setPiercing(true);
        }
        gameFieldPanel.add(shell);
        if (tank.isDoubleShot()) {
            gameFieldPanel.add(shell2);
        }
    }

    private boolean checkCloseShotRight() {
        boolean closeShot = false;
        Component[] components = gameFieldPanel.getComponents();
        for (Component component : components) {
            if (component instanceof MapObject) {
                MapObject mapObject = (MapObject) component;
                if (mapObject.getImageNum() == 1
                        && mapObject.getX() == tank.getX() + Constants.tankSize
                        && mapObject.getY() == tank.getY() + Constants.tankSize / 2 - Constants.brickSize / 2) {
                    gameFieldPanel.remove(mapObject);
                    gameFieldPanel.removeVerticalLineOfBlocks(mapObject.getX(),
                            mapObject.getY(), Shell.CENTER);
                    gameFieldPanel.drawExpolde(tank.getX() + Constants.tankSize,
                            tank.getY() + Constants.tankSize / 2 - Constants.explosionSize / 2,
                            Constants.SMALL_EXPLOSION_CODE,
                            Constants.explosionSize);
                    gameFieldPanel.repaint(tank.getX() + Constants.tankSize,
                            tank.getY() + Constants.tankSize / 2 - Constants.explosionSize / 2,
                            Constants.explosionSize,
                            Constants.explosionSize);
                    closeShot = true;
                    break;
                }
            }
        }
        return closeShot;
    }   
    
    private boolean checkCloseShotLeft() {
        boolean closeShot = false;
        Component[] components = gameFieldPanel.getComponents();
        for (Component component : components) {
            if (component instanceof MapObject) {
                MapObject mapObject = (MapObject) component;
                if (mapObject.getImageNum() == 1
                        && mapObject.getX() == tank.getX() - Constants.brickSize
                        && mapObject.getY() == tank.getY() + Constants.tankSize / 2 - Constants.brickSize / 2) {
                    gameFieldPanel.remove(mapObject);
                    gameFieldPanel.removeVerticalLineOfBlocks(mapObject.getX(),
                            mapObject.getY(), Shell.CENTER);
                    gameFieldPanel.drawExpolde(tank.getX() - Constants.brickSize,
                            tank.getY() + Constants.tankSize / 2 - Constants.explosionSize / 2,
                            Constants.SMALL_EXPLOSION_CODE,
                            Constants.explosionSize);
                    gameFieldPanel.repaint(tank.getX() - Constants.brickSize,
                            tank.getY() + Constants.tankSize / 2 - Constants.explosionSize / 2,
                            Constants.explosionSize,
                            Constants.explosionSize);
                    closeShot = true;
                    break;
                }
            }
        }
        return closeShot;
    }

    private boolean checkCloseShotDown() {
        boolean closeShot = false;
        Component[] components = gameFieldPanel.getComponents();
        for (Component component : components) {
            if (component instanceof MapObject) {
                MapObject mapObject = (MapObject) component;
                if (mapObject.getImageNum() == 1
                        && mapObject.getX() == tank.getX() + Constants.tankSize / 2 - Constants.brickSize / 2
                        && mapObject.getY() == tank.getY() + Constants.tankSize) {
                    gameFieldPanel.remove(mapObject);
                    gameFieldPanel.removeHorizontalLineOfBlocks(mapObject.getX(),
                            mapObject.getY(), Shell.CENTER);
                    gameFieldPanel.drawExpolde(tank.getX() + Constants.tankSize / 2 - Constants.explosionSize / 2,
                            tank.getY() + Constants.tankSize,
                            Constants.SMALL_EXPLOSION_CODE,
                            Constants.explosionSize);
                    gameFieldPanel.repaint(tank.getX() + Constants.tankSize / 2 - Constants.explosionSize / 2,
                            tank.getY() + Constants.tankSize,
                            Constants.explosionSize,
                            Constants.explosionSize);
                    closeShot = true;
                    break;
                }
            }
        }
        return closeShot;
    }

    private boolean checkCloseShotUp() {
        boolean closeShot = false;
        Component[] components = gameFieldPanel.getComponents();
        for (Component component : components) {
            if (component instanceof MapObject) {
                MapObject mapObject = (MapObject) component;
                if (mapObject.getImageNum() == 1
                        && mapObject.getX() == tank.getX() + Constants.tankSize / 2 - Constants.brickSize / 2
                        && mapObject.getY() == tank.getY() - Constants.brickSize) {
                    gameFieldPanel.remove(mapObject);
                    gameFieldPanel.removeHorizontalLineOfBlocks(mapObject.getX(),
                            mapObject.getY(), Shell.CENTER);
                    gameFieldPanel.drawExpolde(tank.getX() + Constants.tankSize / 2 - Constants.explosionSize / 2,
                            tank.getY() - Constants.brickSize,
                            Constants.SMALL_EXPLOSION_CODE,
                            Constants.explosionSize);
                    gameFieldPanel.repaint(tank.getX() + Constants.tankSize / 2 - Constants.explosionSize / 2,
                            tank.getY() - Constants.brickSize,
                            Constants.explosionSize,
                            Constants.explosionSize);
                    closeShot = true;
                    break;
                }
            }
        }
        return closeShot;
    }
    
    private Rectangle getFirstShellBounds() {
        int x = 0, y = 0;
        switch (tank.getDirection()) {
            case Constants.UP:
                x = tank.getX() + Constants.tankSize / 2 - Constants.shellSize / 2;
                if (tank.isDoubleShot()) {
                    x -= Constants.shellSize;
                }
                y = tank.getY() - Constants.shellSize;                
                break;
            case Constants.DOWN:
                x = tank.getX() + Constants.tankSize / 2 - Constants.shellSize / 2;
                if (tank.isDoubleShot()) {
                    x -= Constants.shellSize;
                }
                y = tank.getY() + Constants.tankSize;
                break;
            case Constants.LEFT:
                x = tank.getX() - Constants.shellSize;
                y = tank.getY() + Constants.tankSize / 2 - Constants.shellSize / 2;
                if (tank.isDoubleShot()) {
                    y -= Constants.shellSize;
                }
                break;
            case Constants.RIGHT:
                x = tank.getX() + Constants.tankSize;
                if (!tank.isDoubleShot()) {
                    y = tank.getY() + Constants.tankSize / 2 - Constants.shellSize / 2;
                } else {
                    y = tank.getY() + Constants.shellSize;
                }                
                break;
            default:
                break;
        }
        return new Rectangle(x, y, Constants.shellSize, Constants.shellSize);
    }
}
