/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.objects.tank.player;

import game.gui.GameFieldPanel;
import game.logic.Constants;
import game.logic.GameConfig;
import game.objects.MapObject;
import game.objects.tank.AbstractTank;
import game.objects.Shell;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Павел
 */
public class PlayerTankKeyListener implements KeyListener {

    private final AbstractTank tank;
    private boolean alive, upKeyPressed, downKeyPressed, leftKeyPressed, rightKeyPressed;    
    private GameFieldPanel gameFieldPanel;
    private Shell shell, shell2;
    private int upKeyCode, downKeyCode, leftKeyCode, rightKeyCode, fireKeyCode;
    
    public PlayerTankKeyListener(AbstractTank tank, GameFieldPanel gameFieldPanel) {
        this.tank = tank;        
        this.alive = tank.isAlive();
        this.gameFieldPanel = gameFieldPanel;
        this.shell = tank.getShell();
        upKeyCode = Integer.parseInt(GameConfig.getInstance().getProperty("playerOneUp"));
        downKeyCode = Integer.parseInt(GameConfig.getInstance().getProperty("playerOneDown"));
        leftKeyCode = Integer.parseInt(GameConfig.getInstance().getProperty("playerOneLeft"));
        rightKeyCode = Integer.parseInt(GameConfig.getInstance().getProperty("playerOneRight"));
        fireKeyCode = Integer.parseInt(GameConfig.getInstance().getProperty("playerOneFire"));
    } 
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {        		
        if (alive) {
            if (e.getKeyCode() == upKeyCode) {					
                rotateTankUp();
            } else if (e.getKeyCode() == downKeyCode) {	
                rotateTankDown();
            } else if (e.getKeyCode() == leftKeyCode) {	
                rotateTankLeft();
            } else if (e.getKeyCode() == rightKeyCode) {
                rotateTankRight();
            } else if (e.getKeyCode() == fireKeyCode) {				
                fire();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {            
        if (e.getKeyCode() == upKeyCode) {				
            upKeyPressed = false;
            if (!downKeyPressed && !leftKeyPressed && !rightKeyPressed) {
                tank.getMovementTimer().stop();		
            } 
        } else if (e.getKeyCode() == downKeyCode) {
            downKeyPressed = false;				
            if (!upKeyPressed && !leftKeyPressed && !rightKeyPressed) {
                tank.getMovementTimer().stop();	
            } 
        } else if (e.getKeyCode() == leftKeyCode) {
            leftKeyPressed = false;				
            if (!upKeyPressed && !downKeyPressed && !rightKeyPressed) {
                tank.getMovementTimer().stop();	
            } 
        } else if (e.getKeyCode() == rightKeyCode) {
            rightKeyPressed = false;				
            if (!upKeyPressed && !downKeyPressed && !leftKeyPressed) {
                tank.getMovementTimer().stop();	
            } 			
        } else if (e.getKeyCode() == fireKeyCode) {	                   
            tank.setFireKeyPressed(false);
        }       
    }    
    
    private void rotateTankUp() {
        if (!upKeyPressed) {
            upKeyPressed = true;
        }
        if (tank.getDirection() != Constants.UP) {
            tank.setDirection(Constants.UP);
            switch (tank.getStarsCount()) {
                case 0:
                    tank.setTankPicture(AbstractTank.G_TANK_UP);
                    break;
                case 1:
                    tank.setTankPicture(AbstractTank.G2_SHELL_UP);
                    break;
                case 2:
                    tank.setTankPicture(AbstractTank.G_DOUBLE_SHELL_UP);
                    break;
                case 3:
                    tank.setTankPicture(AbstractTank.G_QUADRO_UP);
                    break;
                case 4:
                    tank.setTankPicture(AbstractTank.G_QUADRO_TURBO_UP);
                    break;
                default:
                    break;
                }
        }							
        tank.getMovementTimer().start();
    }
    
    private void rotateTankDown() {
        if (!downKeyPressed) {
            downKeyPressed = true;
        }
        if (tank.getDirection() != Constants.DOWN) {
            tank.setDirection(Constants.DOWN);
            switch (tank.getStarsCount()) {
            case 0:
                tank.setTankPicture(AbstractTank.G_TANK_DOWN);
                break;
            case 1:
                tank.setTankPicture(AbstractTank.G2_SHELL_DOWN);
                break;
            case 2:
                tank.setTankPicture(AbstractTank.G_DOUBLE_SHELL_DOWN);
                break;
            case 3:
                tank.setTankPicture(AbstractTank.G_QUADRO_DOWN);
                break;
            case 4:
                tank.setTankPicture(AbstractTank.G_QUADRO_TURBO_DOWN);
                break;
            default:
                break;
            }
        }
        tank.getMovementTimer().start();
    }
    
    private void rotateTankLeft() {
        if (!leftKeyPressed) {
            leftKeyPressed = true;
        }
        if (tank.getDirection() != Constants.LEFT) {
            tank.setDirection(Constants.LEFT);
            switch (tank.getStarsCount()) {
            case 0:
                tank.setTankPicture(AbstractTank.G_TANK_LEFT);
                break;
            case 1:
                tank.setTankPicture(AbstractTank.G2_SHELL_LEFT);
                break;
            case 2:
                tank.setTankPicture(AbstractTank.G_DOUBLE_SHELL_LEFT);
                break;
            case 3:
                tank.setTankPicture(AbstractTank.G_QUADRO_LEFT);
                break;
            case 4:
                tank.setTankPicture(AbstractTank.G_QUADRO_TURBO_LEFT);
                break;
            default:
                break;
            }
        }				
        tank.getMovementTimer().start();
    }
    
    private void rotateTankRight() {
        if (!rightKeyPressed) {
            rightKeyPressed = true;
        }
        if (tank.getDirection() != Constants.RIGHT) {
            tank.setDirection(Constants.RIGHT);
            switch (tank.getStarsCount()) {
            case 0:
                tank.setTankPicture(AbstractTank.G_TANK_RIGHT);
                break;
            case 1:
                tank.setTankPicture(AbstractTank.G2_SHELL_RIGHT);
                break;
            case 2:
                tank.setTankPicture(AbstractTank.G_DOUBLE_SHELL_RIGHT);
                break;
            case 3:
                tank.setTankPicture(AbstractTank.G_QUADRO_RIGHT);
                break;
            case 4:
                tank.setTankPicture(AbstractTank.G_QUADRO_TURBO_RIGHT);
                break;
            default:
                break;
            }
        }				
        tank.getMovementTimer().start();
    }
    
    private boolean processCloseShotUp() {
        boolean isCloseShot = false;
        Component[] components = gameFieldPanel.getComponents();
        for (Component component : components) {
            if (component instanceof MapObject) {
                MapObject mapObject = (MapObject) component;
                if (mapObject.getImageNum() == 1 && mapObject.getX() == tank.getX() + Constants.tankSize / 2 
                        - Constants.brickSize / 2 && mapObject.getY() == tank.getY() - Constants.brickSize) {
                    gameFieldPanel.remove(mapObject);
                    gameFieldPanel.removeHorizontalLineOfBlocks(mapObject.getX(),mapObject.getY(), Shell.CENTER);
                    gameFieldPanel.repaint(mapObject.getX());
                    gameFieldPanel.drawExpolde(tank.getX() + Constants.tankSize / 2 - Constants.explosionSize / 2,
                                    tank.getY() - Constants.brickSize, Constants.SMALL_EXPLOSION_CODE,
                                    Constants.explosionSize);
                    gameFieldPanel.repaint(tank.getX() + Constants.tankSize / 2 - Constants.explosionSize / 2, 
                            tank.getY() - Constants.brickSize, Constants.explosionSize, Constants.explosionSize);
                    isCloseShot = true;
                    break;
                }
            } else if (component instanceof AbstractTank) {
                AbstractTank tank = (AbstractTank) component;
                if (!tank.isPlayerControlled()) {									
                    Rectangle shell1Bounds, shell2Bounds;
                    if (!this.tank.isDoubleShot()) {
                        shell1Bounds = new Rectangle(tank.getX() + Constants.tankSize / 2 - Constants.shellSize / 2,
                                        tank.getY() - Constants.shellSize, Constants.shellSize, Constants.shellSize);
                        if (shell1Bounds.intersects(tank.getBounds())) {
                            gameFieldPanel.battleField.increaseShotsCountForPlayerOne();
                            gameFieldPanel.battleField.increaseTotalShotsCountForPlayerOne();
                            gameFieldPanel.destroyEnemyTank(tank);
                            isCloseShot = true;
                        }
                    } else {
                        shell1Bounds = new Rectangle(tank.getX() + Constants.tankSize / 2 - Constants.shellSize / 2
                                        - Constants.shellSize, tank.getY() - Constants.shellSize, Constants.shellSize,
                                        Constants.shellSize);
                        shell2Bounds = new Rectangle(tank.getX() + Constants.tankSize / 2 + Constants.shellSize / 2,
                                        tank.getY() - Constants.shellSize, Constants.shellSize, Constants.shellSize);
                        if (shell1Bounds.intersects(tank.getBounds()) || shell2Bounds.intersects(tank.getBounds())) {
                            gameFieldPanel.battleField.increaseShotsCountForPlayerOne();
                            gameFieldPanel.battleField.increaseTotalShotsCountForPlayerOne();
                            gameFieldPanel.destroyEnemyTank(tank);
                            isCloseShot = true;
                        }
                    }
                }
            }
        }
        return isCloseShot;
    }
    
    private boolean processCloseShotDown() {
        boolean isCloseShot = false;
        Component[] components = gameFieldPanel.getComponents();
            for (Component component : components) {
            if (component instanceof MapObject) {
                MapObject mapObject = (MapObject) component;
                if (mapObject.getImageNum() == 1 && mapObject.getX() == tank.getX() + Constants.tankSize / 2 
                        - Constants.brickSize / 2 && mapObject.getY() == tank.getY() + Constants.tankSize) {
                    gameFieldPanel.remove(mapObject);
                    gameFieldPanel.removeHorizontalLineOfBlocks(mapObject.getX(), mapObject.getY(),Shell.CENTER);
                    gameFieldPanel.repaint(mapObject.getX());
                    gameFieldPanel.drawExpolde(tank.getX() + Constants.tankSize / 2 - Constants.explosionSize / 2,
                                    tank.getY() + Constants.tankSize, Constants.SMALL_EXPLOSION_CODE, 
                                    Constants.explosionSize);
                    gameFieldPanel.repaint(tank.getX() + Constants.tankSize / 2 - Constants.explosionSize / 2, 
                            tank.getY() + Constants.tankSize, Constants.explosionSize, Constants.explosionSize);
                    isCloseShot = true;
                    break;
                }
            } else if (component instanceof AbstractTank) {
                AbstractTank tank = (AbstractTank) component;
                if (!tank.isPlayerControlled()) {
                    Rectangle shell1Bounds, shell2Bounds;
                    if (!tank.isDoubleShot()) {
                        shell1Bounds = new Rectangle(tank.getX() + Constants.tankSize / 2 - Constants.shellSize / 2,
                                        tank.getY() + Constants.tankSize, Constants.shellSize, Constants.shellSize);
                        if (shell1Bounds.intersects(tank.getBounds())) {
                            gameFieldPanel.battleField.increaseShotsCountForPlayerOne();
                            gameFieldPanel.battleField.increaseTotalShotsCountForPlayerOne();
                            gameFieldPanel.destroyEnemyTank(tank);
                            isCloseShot = true;
                        }
                    } else {
                        shell1Bounds = new Rectangle(tank.getX() + Constants.tankSize / 2 - Constants.shellSize / 2
                                        - Constants.shellSize, tank.getY() + Constants.tankSize, Constants.shellSize,
                                        Constants.shellSize);
                        shell2Bounds = new Rectangle(tank.getX() + Constants.tankSize / 2 + Constants.shellSize / 2,
                                        tank.getY() + Constants.tankSize, Constants.shellSize, Constants.shellSize);
                        if (shell1Bounds.intersects(tank.getBounds()) || shell2Bounds.intersects(tank.getBounds())) {
                            gameFieldPanel.battleField.increaseShotsCountForPlayerOne();
                            gameFieldPanel.battleField.increaseTotalShotsCountForPlayerOne();
                            gameFieldPanel.destroyEnemyTank(tank);
                            isCloseShot = true;
                        }
                    }
                }
            }
        }
        return isCloseShot;
    }
    
    private boolean processCloseShotLeft() {
        boolean isCloseShot = false;
        Rectangle shell1Bounds, shell2Bounds;
        Component[] components = gameFieldPanel.getComponents();
        for (Component component : components) {
            if (component instanceof MapObject) {
                MapObject mapObject = (MapObject) component;
                if (mapObject.getImageNum() == 1 && mapObject.getX() == tank.getX() - Constants.brickSize 
                        && mapObject.getY() == tank.getY() + Constants.tankSize / 2 - Constants.brickSize / 2) {
                    gameFieldPanel.remove(mapObject);                                
                    gameFieldPanel.removeVerticalLineOfBlocks(mapObject.getX(), mapObject.getY(), Shell.CENTER);
                    gameFieldPanel.repaint(mapObject.getBounds());
                    gameFieldPanel.drawExpolde(tank.getX() - Constants.brickSize, tank.getY() + Constants.tankSize / 2
                                    - Constants.explosionSize / 2, Constants.SMALL_EXPLOSION_CODE, Constants.explosionSize);
                    gameFieldPanel.repaint(tank.getX() - Constants.brickSize, tank.getY() + Constants.tankSize / 2
                                    - Constants.explosionSize / 2, Constants.explosionSize, Constants.explosionSize);
                    isCloseShot = true;
                    break;
                }
            } else if (component instanceof AbstractTank) {
                AbstractTank tank = (AbstractTank) component;
                if (!tank.isPlayerControlled()) {
                    if (!tank.isDoubleShot()) {
                        shell1Bounds = new Rectangle(tank.getX() - Constants.shellSize, tank.getY() 
                                + Constants.tankSize / 2 - Constants.shellSize / 2, Constants.shellSize, 
                                Constants.shellSize);
                        if (shell1Bounds.intersects(tank.getBounds())) {
                            gameFieldPanel.battleField.increaseShotsCountForPlayerOne();
                            gameFieldPanel.battleField.increaseTotalShotsCountForPlayerOne();
                            gameFieldPanel.destroyEnemyTank(tank);
                            isCloseShot = true;
                        }
                    } else {
                        shell1Bounds = new Rectangle(tank.getX() - Constants.shellSize, tank.getY() 
                                + Constants.tankSize / 2 - Constants.shellSize / 2 - Constants.shellSize, 
                                Constants.shellSize, Constants.shellSize);
                        shell2Bounds = new Rectangle(tank.getX() - Constants.shellSize, tank.getY() 
                                + Constants.tankSize / 2 + Constants.shellSize / 2, Constants.shellSize, 
                                Constants.shellSize);
                        if (shell1Bounds.intersects(tank.getBounds()) || shell2Bounds.intersects(tank.getBounds())) {
                            gameFieldPanel.battleField.increaseShotsCountForPlayerOne();
                            gameFieldPanel.battleField.increaseTotalShotsCountForPlayerOne();
                            gameFieldPanel.destroyEnemyTank(tank);
                            isCloseShot = true;
                        }
                    }
                }
            }
        }
        return isCloseShot;
    }
    
    private boolean processCloseShotRight() {
        boolean isCloseShot = false;
        Component[] components = gameFieldPanel.getComponents();
        for (Component component : components) {
            if (component instanceof MapObject) {
                MapObject mapObject = (MapObject) component;
                if (mapObject.getImageNum() == 1 && mapObject.getX() == tank.getX() + Constants.tankSize
                                && mapObject.getY() == tank.getY() + Constants.tankSize/2 - Constants.brickSize/2) {
                    gameFieldPanel.remove(mapObject);
                    gameFieldPanel.removeVerticalLineOfBlocks(mapObject.getX(), mapObject.getY(), Shell.CENTER);
                    gameFieldPanel.repaint(mapObject.getBounds());
                    gameFieldPanel.drawExpolde(tank.getX() + Constants.tankSize, tank.getY() + Constants.tankSize / 2
                                    - Constants.explosionSize / 2, Constants.SMALL_EXPLOSION_CODE, 
                                    Constants.explosionSize);
                    gameFieldPanel.repaint(tank.getX() + Constants.tankSize, tank.getY() + Constants.tankSize / 2
                                    - Constants.explosionSize / 2, Constants.explosionSize, Constants.explosionSize);
                    isCloseShot = true;
                    break;
                } 
            } else if (component instanceof AbstractTank) {
                AbstractTank tank = (AbstractTank) component;
                if (!tank.isPlayerControlled()) {
                    Rectangle shell1Bounds, shell2Bounds;                    
                    if (!tank.isDoubleShot()) {
                        shell1Bounds = new Rectangle(tank.getX() + Constants.tankSize, tank.getY() 
                                + Constants.tankSize / 2 - Constants.shellSize / 2, Constants.shellSize,
                                        Constants.shellSize);
                        if (shell1Bounds.intersects(tank.getBounds())) {
                            gameFieldPanel.battleField.increaseShotsCountForPlayerOne();
                            gameFieldPanel.battleField.increaseTotalShotsCountForPlayerOne();
                            gameFieldPanel.destroyEnemyTank(tank);
                            isCloseShot = true;
                        }
                    } else {
                        shell1Bounds = new Rectangle(tank.getX() + Constants.tankSize, tank.getY() 
                                + Constants.shellSize, Constants.shellSize, Constants.shellSize);
                        shell2Bounds = new Rectangle(tank.getX() + Constants.tankSize, tank.getY() + Constants.tankSize
                                        - Constants.shellSize * 2, Constants.shellSize, Constants.shellSize);
                        if (shell1Bounds.intersects(tank.getBounds())
                                        || shell2Bounds.intersects(tank.getBounds())) {
                            gameFieldPanel.battleField.increaseShotsCountForPlayerOne();
                            gameFieldPanel.battleField.increaseTotalShotsCountForPlayerOne();
                            gameFieldPanel.destroyEnemyTank(tank);
                            isCloseShot = true;
                        }
                    }
                }
            }
	}
        return isCloseShot;
    }
    
    private void fire() {
        if (tank.isFireKeyPressed() == false && tank.getCurrentBullets() < tank.getMaxBullets()) {					
            gameFieldPanel.battleField.increaseShotsCountForPlayerOne();
            gameFieldPanel.battleField.increaseTotalShotsCountForPlayerOne();
            boolean closeShot = false;
            Rectangle shell1Bounds = null, shell2Bounds = null;            
            if (tank.getDirection() == Constants.UP) {						
                closeShot = processCloseShotUp();
                if (!closeShot) {
                    if (!tank.isDoubleShot()) {
                        shell1Bounds = new Rectangle(tank.getX() + Constants.tankSize / 2 - Constants.shellSize / 2, 
                                tank.getY() - Constants.shellSize, Constants.shellSize, Constants.shellSize);
                    } else {
                        shell1Bounds = new Rectangle(tank.getX() + Constants.tankSize / 2 - Constants.shellSize / 2
                                        - Constants.shellSize, tank.getY() - Constants.shellSize, Constants.shellSize,
                                        Constants.shellSize);
                        shell2Bounds = new Rectangle(tank.getX() + Constants.tankSize / 2 + Constants.shellSize / 2, 
                                tank.getY() - Constants.shellSize, Constants.shellSize, Constants.shellSize);
                    }
                }
            } else if (this.tank.getDirection() == Constants.DOWN) {
                closeShot = processCloseShotDown();
                    if (!closeShot) {
                        if (!tank.isDoubleShot()) {
                            shell1Bounds = new Rectangle(tank.getX() + Constants.tankSize / 2 
                                    - Constants.shellSize / 2, tank.getY() + Constants.tankSize, Constants.shellSize,
                                            Constants.shellSize);
                        } else {
                            shell1Bounds = new Rectangle(tank.getX() + Constants.tankSize / 2 - Constants.shellSize / 2
                                            - Constants.shellSize, tank.getY() + Constants.tankSize, 
                                            Constants.shellSize, Constants.shellSize);
                            shell2Bounds = new Rectangle(tank.getX() + Constants.tankSize / 2 
                                    + Constants.shellSize / 2, tank.getY() + Constants.tankSize, Constants.shellSize,
                                            Constants.shellSize);
                        }
                    }
            } else if (this.tank.getDirection() == Constants.LEFT) {
                closeShot = processCloseShotLeft();
                if (!closeShot) {
                    if (!tank.isDoubleShot()) {
                        shell1Bounds = new Rectangle(tank.getX() - Constants.shellSize, tank.getY() 
                                + Constants.tankSize / 2 - Constants.shellSize / 2, Constants.shellSize, 
                                Constants.shellSize);
                    } else {
                        shell1Bounds = new Rectangle(tank.getX() - Constants.shellSize, tank.getY() 
                                + Constants.tankSize / 2 - Constants.shellSize / 2 - Constants.shellSize,
                                        Constants.shellSize, Constants.shellSize);
                        shell2Bounds = new Rectangle(tank.getX() - Constants.shellSize, tank.getY() 
                                + Constants.tankSize / 2 + Constants.shellSize / 2, Constants.shellSize,
                                        Constants.shellSize);
                    }
                }
            } else if (this.tank.getDirection() == Constants.RIGHT) {
                closeShot = processCloseShotRight();
                if (!closeShot) {
                    if (!tank.isDoubleShot()) {
                        shell1Bounds = new Rectangle(tank.getX() + Constants.tankSize, tank.getY() 
                                + Constants.tankSize / 2 - Constants.shellSize / 2, Constants.shellSize, 
                                Constants.shellSize);
                    } else {
                        shell1Bounds = new Rectangle(tank.getX() + Constants.tankSize, tank.getY() 
                                + Constants.shellSize, Constants.shellSize, Constants.shellSize);
                        shell2Bounds = new Rectangle(tank.getX() + Constants.tankSize, tank.getY() + Constants.tankSize
                                        - Constants.shellSize * 2, Constants.shellSize, Constants.shellSize);
                    }
                }
            }
            if (!closeShot) {
                shell = new Shell(gameFieldPanel, this.tank.getDirection(), tank, Shell.FIRST);
                shell.setBounds(shell1Bounds);
                if (tank.isDoubleShot()) {
                    shell2 = new Shell(gameFieldPanel, this.tank.getDirection(), tank, Shell.SECOND);
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
                } else {
                    shell.setPositionCode(Shell.CENTER);
                }
                if (tank.getStarsCount() >= 3) {
                    shell.setPiercing(true);
                    if (tank.isDoubleShot()) {
                            shell2.setPiercing(true);
                    }
                }
                gameFieldPanel.add(shell, new Integer(2));
                gameFieldPanel.repaint(shell.getBounds());
                if (tank.isDoubleShot()) {
                    gameFieldPanel.add(shell2, new Integer(2));
                    gameFieldPanel.repaint(shell2.getBounds());
                }
                int currentBullets = tank.getCurrentBullets();
                currentBullets++;
                tank.setCurrentBullets(currentBullets);
                tank.setFireKeyPressed(true);   
                gameFieldPanel.battleField.increaseShotsCountForPlayerOne();
                gameFieldPanel.battleField.increaseTotalShotsCountForPlayerOne();                
                gameFieldPanel.battleField.increaseShotsCountForPlayerOne();
                gameFieldPanel.battleField.increaseTotalShotsCountForPlayerOne();
            } 
        }
    }
}
