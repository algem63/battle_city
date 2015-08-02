package game.logic;

import game.gui.GameFieldPanel;
import game.gui.GameMessage;
import game.objects.Bonus;
import game.objects.HeadQuarter;
import game.objects.MapObject;
import game.objects.Shell;
import game.objects.tank.AbstractTank;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: melnikovp
 * Date: 07.11.12
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
 */
public class ShellMovementHandler implements ActionListener {
    private Shell shell;
    private int directionCode;
    private GameFieldPanel gameFieldPanel;
    private AbstractTank tank;
    
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    public ShellMovementHandler(Shell shell, int directionCode, GameFieldPanel gameFieldPanel, AbstractTank tank) {
        this.shell = shell;
        this.directionCode = directionCode;
        this.gameFieldPanel = gameFieldPanel;
        this.tank = tank;
    }

    public void actionPerformed(ActionEvent e) {        
        int posX = shell.getX();
        int posY = shell.getY();
        int temp = 0;
        Rectangle shellRect = null;
        boolean hit = false,
                shellMovementCondition = false;
        hit = checkCloseShot();
        Component component = null;
        switch (directionCode) {
            case ShellMovementHandler.UP :
                temp = posY - Constants.stepValue;
                shellRect = new Rectangle(posX,
                                          posY - Constants.stepValue,
                                          Constants.shellSize,
                                          Constants.shellSize);
                component = gameFieldPanel.getComponentAt(shell.getX(), shell.getY() - Constants.shellSize);
                break;
            case ShellMovementHandler.DOWN :
                temp = posY + Constants.stepValue + Constants.shellSize;
                shellRect = new Rectangle(posX,
                                          posY + Constants.stepValue,
                                          Constants.shellSize,
                                          Constants.shellSize);
                component = gameFieldPanel.getComponentAt(shell.getX(), shell.getY());
                break;
            case ShellMovementHandler.LEFT :
                temp = posX - Constants.stepValue;
                shellRect = new Rectangle(posX - Constants.stepValue,
                                          posY,
                                          Constants.shellSize,
                                          Constants.shellSize);
                component = gameFieldPanel.getComponentAt(shell.getX() + Constants.shellSize, shell.getY());
                break;
            case ShellMovementHandler.RIGHT :
                temp = posX + Constants.stepValue + Constants.shellSize;
                shellRect = new Rectangle(posX + Constants.stepValue,
                                          posY,
                                          Constants.shellSize,
                                          Constants.shellSize);
                component = gameFieldPanel.getComponentAt(shell.getX() - Constants.shellSize, shell.getY());
                break;
        }        
        for (Component mapObject: gameFieldPanel.getComponents()) {
            if (mapObject instanceof MapObject) {            	
                MapObject obj = (MapObject) mapObject;
                if (shellRect.intersects(obj.getBounds())
                        && obj.getImageNum() == Constants.CONCRETE_CODE) {                	
                    hit = shellHitConcrete(shellRect, obj);
                    // TODO тут надо добавить break походу!
                }
                if (shellRect.intersects(obj.getBounds())
                        && obj.getImageNum() == Constants.BRICK_CODE) {                	
                    hit = true;
                    shellHitBrick(obj);
                }
                if (shellRect.intersects(obj.getBounds())
                        && obj.getImageNum() == Constants.HQ_CODE) {                	
                    hit = true;
                    shellHitHQ(obj);
                }
            }
            if (mapObject instanceof AbstractTank) {            	
                AbstractTank tank = (AbstractTank) mapObject;
                if (shellRect.intersects(tank.getBounds())) {                	
                    hit = true;
                    if (this.tank.isPlayerControlled()
                            != tank.isPlayerControlled()) {                       
                        boolean superTank = false;
                        if (!tank.isPlayerControlled()) {
                            shellHitPlayerTank(tank);
                        } else {
                            shellHitEnemyTank(tank, superTank);
                        }
                    }
                }
            }
            if (mapObject instanceof Shell) {            	
                hit = shellHitShell(mapObject, shellRect, hit);
            }
        }
        switch (directionCode) {
            case ShellMovementHandler.UP :            	
                shellMovementCondition = temp>=0 && !hit;
                break;
            case ShellMovementHandler.DOWN :
                shellMovementCondition = temp <= Constants.gameFieldHeight && !hit;
                break;
            case ShellMovementHandler.LEFT :
                shellMovementCondition = temp>=0 && !hit;
                break;
            case ShellMovementHandler.RIGHT :
                shellMovementCondition = temp<= Constants.gameFieldWidth && !hit;
                break;
        }        
        if (shellMovementCondition) {
            shell.setBounds(shellRect);
        } 
        else {        	
            shell.timer.stop();
            shell.removeShellFromTank();            
            tank.decreaseCurrentBulletsNum();
            gameFieldPanel.remove(shell);
        }
        gameFieldPanel.repaint(shell.getBounds());                
    }    

    private boolean shellHitShell(Component mapObject, Rectangle shellRect, boolean hit) {
        Shell shell2 = (Shell) mapObject;
        if (shellRect.intersects(shell2.getBounds())) {
            hit = true;
            shell.timer.stop();
            shell.removeShellFromTank();
            shell.getTank().decreaseCurrentBulletsNum();
            shell2.timer.stop();
            shell2.removeShellFromTank();
            shell2.getTank().decreaseCurrentBulletsNum();
            gameFieldPanel.remove(shell);
            gameFieldPanel.remove(shell2);
            gameFieldPanel.repaint(shell.getBounds());
            gameFieldPanel.repaint(shell2.getBounds());
        }
        return hit;
    }

    private void shellHitEnemyTank(AbstractTank tank1, boolean superTank) {
        if (tank1.getStarsCount() == 4) {
            gameFieldPanel.drawExpolde(tank1.getX(), tank1.getY(), Constants.BIG_EXPLOSION_CODE, Constants.tankSize);
            gameFieldPanel.repaint(tank1.getX(), tank1.getY(), Constants.tankSize, Constants.tankSize);
            tank1.setStarsCountToZero();
            tank1.setDoubleShot(false);
            tank1.getMovementTimer().setDelay(75);
            switch (tank1.getDirection()) {
                case Constants.UP:
                    tank1.setTankPicture(AbstractTank.G_TANK_UP);
                    break;
                case Constants.DOWN:
                    tank1.setTankPicture(AbstractTank.G_TANK_DOWN);
                    break;
                case Constants.LEFT:
                    tank1.setTankPicture(AbstractTank.G_TANK_LEFT);
                    break;
                case Constants.RIGHT:
                    tank1.setTankPicture(AbstractTank.G_TANK_RIGHT);
                    break;
                default:
                    break;
            }
            superTank = true;
        } else {
            if (tank1.getLife() > 1) {
                int life = tank1.getLife();
                life--;
                Bonus bonus = new Bonus();
                gameFieldPanel.add(bonus, new Integer(2));
                gameFieldPanel.repaint(bonus.getBounds());
                gameFieldPanel.battleField.decreasePlayerOneLifeValue();
                gameFieldPanel.addPlayerOneTank(life);
            } else {
                gameFieldPanel.stopGame(Constants.gameOverMessageCode);
            }
        }
        if (!superTank) {
            tank1.setAlive(false);
            gameFieldPanel.remove(tank1);
            gameFieldPanel.drawExpolde(tank1.getX(), tank1.getY(), Constants.BIG_EXPLOSION_CODE, Constants.tankSize);
            gameFieldPanel.repaint();
            gameFieldPanel.setPlayerOneTank(null);
        }
    }

    private void shellHitPlayerTank(AbstractTank tank1) {
        int count = tank1.getStarsCount();
        if (count > 0) {
            count--;
            tank1.setStarsCount(count);
            gameFieldPanel.drawExpolde(tank1.getX(), tank1.getY(), Constants.BIG_EXPLOSION_CODE, Constants.tankSize);
            gameFieldPanel.repaint(tank1.getX(), tank1.getY(), Constants.BIG_EXPLOSION_CODE, Constants.tankSize);
            switch (tank1.getStarsCount()) {
                case 0:
                    tank1.setDoubleShot(false);
                    if (tank1.getDirection() == Constants.UP) {
                        tank1.setTankPicture(AbstractTank.EN_TANK_UP);
                    } else if (tank1.getDirection() == Constants.DOWN) {
                        tank1.setTankPicture(AbstractTank.EN_TANK_DOWN);
                    } else if (tank1.getDirection() == Constants.LEFT) {
                        tank1.setTankPicture(AbstractTank.EN_TANK_LEFT);
                    } else if (tank1.getDirection() == Constants.RIGHT) {
                        tank1.setTankPicture(AbstractTank.EN_TANK_RIGHT);
                    }
                    break;
                case 1:
                    tank1.setDoubleShot(false);
                    if (tank1.getDirection() == Constants.UP) {
                        tank1.setTankPicture(AbstractTank.EN_2SHELL_UP);
                    } else if (tank1.getDirection() == Constants.DOWN) {
                        tank1.setTankPicture(AbstractTank.EN_2SHELL_DOWN);
                    } else if (tank1.getDirection() == Constants.LEFT) {
                        tank1.setTankPicture(AbstractTank.EN_2SHELL_LEFT);
                    } else if (tank1.getDirection() == Constants.RIGHT) {
                        tank1.setTankPicture(AbstractTank.EN_2SHELL_RIGHT);
                    }
                    break;
                case 2:
                    tank1.setDoubleShot(true);
                    if (tank1.getDirection() == Constants.UP) {
                        tank1.setTankPicture(AbstractTank.EN_QUADRO_UP);
                    } else if (tank1.getDirection() == Constants.DOWN) {
                        tank1.setTankPicture(AbstractTank.EN_QUADRO_DOWN);
                    } else if (tank1.getDirection() == Constants.LEFT) {
                        tank1.setTankPicture(AbstractTank.EN_QUADRO_LEFT);
                    } else if (tank1.getDirection() == Constants.RIGHT) {
                        tank1.setTankPicture(AbstractTank.EN_QUADRO_RIGHT);
                    }
                    break;
                default:
                    break;
            }
        } else {
            tank1.stopAllTimers();
            gameFieldPanel.battleField.increasePlayerOneKilledCount();
            gameFieldPanel.battleField.increaseTotalKilledCountForPlayerOne();
            gameFieldPanel.increaseTotalEnemyTanksKilled();
            gameFieldPanel.addBonusIfNeeded();
            if (tank1.getLife() > 1) {
                gameFieldPanel.increaseMaxTanksCount();
            } else {
                gameFieldPanel.battleField.increaseTanksKilled();
                if (gameFieldPanel.battleField.isAllTanksKilled()) {
                    if (tank1.getShell() != null) {
                        tank1.getShell().stopTimer();
                        gameFieldPanel.remove(tank1.getShell());
                        gameFieldPanel.repaint(tank1.getShell().getBounds());
                    }
                    if (tank1.getShell2() != null) {
                        tank1.getShell2().stopTimer();
                        gameFieldPanel.remove(tank1.getShell2());
                        gameFieldPanel.repaint(tank1.getShell2().getBounds());
                    }
                    GameConfig.getInstance().increaseCurrentLevelNum();
                    if (gameFieldPanel.mapAvaliable(GameConfig.getInstance().getCurrentLevel())) {
                        AbstractTank playerTank = gameFieldPanel.getPlayerOneTank();
                        gameFieldPanel.battleField.setLevelValue(
                                String.valueOf(
                                        GameConfig.getInstance().getCurrentLevel()));
                        gameFieldPanel.stopEnemyRespawner();
                        new MapLoader(gameFieldPanel, GameConfig.getInstance().getCurrentMapFile()).start();
                    } else {
                        gameFieldPanel.stopGame(Constants.youWinMessageCode);
                    }
                }
            }
            tank1.setAlive(false);
            gameFieldPanel.remove(tank1);
            gameFieldPanel.drawExpolde(tank1.getX(), tank1.getY(), Constants.BIG_EXPLOSION_CODE, Constants.tankSize);
            gameFieldPanel.repaint(tank1.getX(), tank1.getY(), Constants.tankSize, Constants.tankSize);
            tank1 = null;
        }
    }

    private void shellHitHQ(MapObject obj) {
        HeadQuarter headQuarter = (HeadQuarter) obj;
        if (headQuarter.getLevel() > 1) {
            headQuarter.drawExplode();
            headQuarter.decreaseLevel();
        } else {
            gameFieldPanel.remove(headQuarter);
            gameFieldPanel.drawExpolde(headQuarter.getX(),
                    headQuarter.getY(),
                    Constants.BIG_EXPLOSION_CODE,
                    Constants.hqSize);
            gameFieldPanel.repaint(headQuarter.getX(),
                    headQuarter.getY(),
                    Constants.BIG_EXPLOSION_CODE,
                    Constants.hqSize);
            gameFieldPanel.stopGame(Constants.gameOverMessageCode);
        }
    }

    private void shellHitBrick(MapObject obj) {
        gameFieldPanel.remove(obj);
        gameFieldPanel.repaint(obj.getBounds());
        if (directionCode == Constants.UP
                || directionCode == Constants.DOWN) {
            gameFieldPanel.removeHorizontalLineOfBlocks(obj.getX(), obj.getY(), shell.getPositionCode());
        } else if (directionCode == Constants.LEFT
                || directionCode == Constants.RIGHT) {
            gameFieldPanel.removeVerticalLineOfBlocks(obj.getX(), obj.getY(), shell.getPositionCode());
        }
        gameFieldPanel.drawExpolde(shell.getX(),
                shell.getY(),
                Constants.SMALL_EXPLOSION_CODE,
                Constants.explosionSize);
        gameFieldPanel.repaint(shell.getX(),
                shell.getY(),
                Constants.explosionSize,
                Constants.explosionSize);
    }

    private boolean shellHitConcrete(Rectangle shellRect, MapObject obj) {
        boolean hit;
        Rectangle intersection = shellRect.intersection(obj.getBounds());
        hit = true;
        gameFieldPanel.drawExpolde(shell.getX(),
                shell.getY(),
                Constants.SMALL_EXPLOSION_CODE,
                Constants.explosionSize);
        gameFieldPanel.repaint(shell.getX(),
                shell.getY(),
                Constants.explosionSize,
                Constants.explosionSize);
        if (shell.isPiercing()) {
            gameFieldPanel.remove(obj);
            gameFieldPanel.repaint(obj.getBounds());
            if (directionCode == Constants.UP || directionCode == Constants.DOWN) {
                gameFieldPanel.removeConcreteHorizontal(obj.getX(), obj.getY(), shell.getPositionCode(), intersection);
            } else if (directionCode == Constants.LEFT || directionCode == Constants.RIGHT) {
                gameFieldPanel.removeConcreteVertical(obj.getX(), obj.getY(), shell.getPositionCode(), intersection);
            }
        }
        return hit;
    }

    private boolean checkCloseShot() {
        boolean hit = false;
        Component[] components = gameFieldPanel.getComponents();
        for (Component component: components) {
            if (shell.getBounds().intersects(component.getBounds())
                    && !(component instanceof Shell)
                    && !(component instanceof Bonus)
                    && !(component instanceof AbstractTank)
                    && !(component instanceof GameMessage)) {
                MapObject mapObject = (MapObject) component;
                if (mapObject.getImageNum() == Constants.BRICK_CODE) {
                    gameFieldPanel.remove(mapObject);
                    if (shell.getDirection() == Constants.UP
                            || shell.getDirection() == Constants.DOWN) {
                        gameFieldPanel.removeHorizontalLineOfBlocks(mapObject.getX(),
                                mapObject.getY(), Shell.CENTER);
                    } else if (shell.getDirection() == Constants.LEFT
                            || shell.getDirection() == Constants.RIGHT) {
                        gameFieldPanel.removeVerticalLineOfBlocks(mapObject.getX(),
                                mapObject.getY(), Shell.CENTER);
                    }
                    hit = true;
                }
            }
        }
        return hit;
    }
}
