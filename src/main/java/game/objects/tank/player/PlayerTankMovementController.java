/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.objects.tank.player;

/**
 *
 * @author �����
 */

import game.gui.game.GameFieldPanel;
import game.logic.Constants;
import game.objects.Bonus;
import game.objects.HeadQuarter;
import game.objects.tank.AbstractTank;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerTankMovementController implements ActionListener {    

    private PlayerTank tank;
    private GameFieldPanel gameFieldPanel;

    public PlayerTankMovementController(PlayerTank tank, GameFieldPanel gameFieldPanel) {
        this.tank = tank;
        this.gameFieldPanel = gameFieldPanel;
    }    
    
    @Override
    public void actionPerformed(ActionEvent e) {       
        Rectangle tankRect = calculateTankPosition();
        boolean tankMovementCondition = false, hit = false;
        
        for (Component mapObject : gameFieldPanel.getComponents()) {
            Rectangle mapObjectRect = mapObject.getBounds();
            Rectangle currentPos = tank.getBounds();					
            if (tankRect.intersects(mapObjectRect) && !currentPos.equals(mapObjectRect) 
                    && !(mapObject instanceof Bonus)) {
                hit = true;
                break;						
            }
            if (currentPos.intersects(mapObjectRect) && mapObject instanceof Bonus) {
                Bonus bonus = (Bonus) mapObject;
                switch (bonus.getType()) {
                    case Bonus.LIFE:
                        processLifeGetting(mapObject);
                        break;
                    case Bonus.SPADE:
                        processSpadeGetting(mapObject);
                        break;
                    case Bonus.STAR:
                        processStarGetting(mapObject);
                        break;
                    }
            }
        }
        tankMovementCondition = checkIfTankCanMoveFurther(hit);
        if (tankMovementCondition) {
            tank.setBounds(tankRect);
        }
        gameFieldPanel.repaint(tank.getBounds());
    } 
    
    private Rectangle calculateTankPosition() {
        Rectangle tankRect = null;
        int posX = tank.getX();
        int posY = tank.getY();
        //todo �������� �� distance
        int temp = 0;
        switch (tank.getDirection()) {
            case Constants.UP:
                temp = posY - Constants.STEP_VALUE;
                tankRect = new Rectangle(posX, posY - Constants.STEP_VALUE,
                                Constants.TANK_SIZE, Constants.TANK_SIZE);
                break;
            case Constants.DOWN:
                temp = posY + Constants.STEP_VALUE + Constants.TANK_SIZE;
                tankRect = new Rectangle(posX, posY + Constants.STEP_VALUE,
                                Constants.TANK_SIZE, Constants.TANK_SIZE);
                break;
            case Constants.LEFT:
                temp = posX - Constants.STEP_VALUE;
                tankRect = new Rectangle(posX - Constants.STEP_VALUE, posY,
                                Constants.TANK_SIZE, Constants.TANK_SIZE);
                break;
            case Constants.RIGHT:
                temp = posX + Constants.STEP_VALUE + Constants.TANK_SIZE;
                tankRect = new Rectangle(posX + Constants.STEP_VALUE, posY,
                                Constants.TANK_SIZE, Constants.TANK_SIZE);
                break;
        }
        return tankRect;
    }
    
    private void processLifeGetting(Component bonus) {
        int life = tank.getLife();
        life++;
        tank.setLife(life);							
        gameFieldPanel.remove(bonus);
        gameFieldPanel.repaint(bonus.getBounds());							
        gameFieldPanel.battleField.setPlayerOneLifeValue(life);			
    }
    
    private void processSpadeGetting(Component spadeBonus) {
        gameFieldPanel.remove(spadeBonus);
        gameFieldPanel.repaint(spadeBonus.getBounds());
        Component[] components = gameFieldPanel.getComponents();							
        for (Component component : components) {
            if (component instanceof HeadQuarter) {
                HeadQuarter headQuarter = (HeadQuarter) component;
                headQuarter.increaseLevel();
            }
        }		
    }
    
    private void processStarGetting(Component starBonus) {
        int starsCount = tank.getStarsCount();
        if (starsCount < 4) {                                                            
            starsCount++;  
            tank.setStarsCount(starsCount);
            switch (starsCount) {
                case 1:                                                                
                    applyOneStarBonus();
                    break;
                case 2:                                                                    
                    applyTwoStarsBonus();
                    break;
                case 3:                                                                    
                    applyThreeStarsBonus();
                    break;
                case 4:                                                                    
                    applyFourStarsBonus();
                    break;
            }
        }
        gameFieldPanel.remove(starBonus);
        gameFieldPanel.repaint(starBonus.getBounds());
    }
    
    private void applyOneStarBonus() {
        tank.setMaxBullets(2);
        if (tank.getDirection() == Constants.UP) {
            tank.setTankPicture(AbstractTank.G2_SHELL_UP);
        } else if (tank.getDirection() == Constants.DOWN) {
            tank.setTankPicture(AbstractTank.G2_SHELL_DOWN);
        } else if (tank.getDirection() == Constants.LEFT) {
            tank.setTankPicture(AbstractTank.G2_SHELL_LEFT);
        } else if (tank.getDirection() == Constants.RIGHT) {
            tank.setTankPicture(AbstractTank.G2_SHELL_RIGHT);
        }
    }
    
    private void applyTwoStarsBonus() {
        tank.setMaxBullets(1);
        tank.setDoubleShot(true);
        if (tank.getDirection() == Constants.UP) {
            tank.setTankPicture(AbstractTank.G_DOUBLE_SHELL_UP);
        } else if (tank.getDirection() == Constants.DOWN) {
            tank.setTankPicture(AbstractTank.G_DOUBLE_SHELL_DOWN);
        } else if (tank.getDirection() == Constants.LEFT) {
            tank.setTankPicture(AbstractTank.G_DOUBLE_SHELL_LEFT);
        } else if (tank.getDirection() == Constants.RIGHT) {
            tank.setTankPicture(AbstractTank.G_DOUBLE_SHELL_RIGHT);
        }
    }
    
    private void applyThreeStarsBonus() {
        if (tank.getDirection() == Constants.UP) {
            tank.setTankPicture(AbstractTank.G_QUADRO_UP);
        } else if (tank.getDirection() == Constants.DOWN) {
            tank.setTankPicture(AbstractTank.G_QUADRO_DOWN);
        } else if (tank.getDirection() == Constants.LEFT) {
            tank.setTankPicture(AbstractTank.G_QUADRO_LEFT);
        } else if (tank.getDirection() == Constants.RIGHT) {
                tank.setTankPicture(AbstractTank.G_QUADRO_RIGHT);
        }
    }
    
    private void applyFourStarsBonus() {
        tank.getMovementTimer().setDelay(25);
        if (tank.getDirection() == Constants.UP) {
            tank.setTankPicture(AbstractTank.G_QUADRO_TURBO_UP);
        } else if (tank.getDirection() == Constants.DOWN) {
            tank.setTankPicture(AbstractTank.G_QUADRO_TURBO_DOWN);
        } else if (tank.getDirection() == Constants.LEFT) {
            tank.setTankPicture(AbstractTank.G_QUADRO_TURBO_LEFT);
        } else if (tank.getDirection() == Constants.RIGHT) {
            tank.setTankPicture(AbstractTank.G_QUADRO_TURBO_RIGHT);
        }
    }

    private boolean checkIfTankCanMoveFurther(Boolean hit) {
        boolean tankMovementCondition = false;
        int borderDistance = calculateDistanceFromBorders();
        switch (tank.getDirection()) {
            case Constants.UP:
                tankMovementCondition = borderDistance >= 0 && hit == false;
                break;
            case Constants.DOWN:
                tankMovementCondition = borderDistance <= Constants.GAME_FIELD_HEIGHT && hit == false;
                break;
            case Constants.LEFT:
                tankMovementCondition = borderDistance >= 0 && hit == false;
                break;
            case Constants.RIGHT:
                tankMovementCondition = borderDistance <= Constants.GAME_FIELD_WIDTH && hit == false;
                break;
        }
        return tankMovementCondition;
    }
    
    private int calculateDistanceFromBorders() {
        int distance = 0;
        switch (tank.getDirection()) {
            case Constants.UP:
                distance = tank.getY() - Constants.STEP_VALUE;
                break;
            case Constants.DOWN:
                distance = tank.getY() + Constants.STEP_VALUE + Constants.TANK_SIZE;
                break;
            case Constants.LEFT:
                distance = tank.getX() - Constants.STEP_VALUE;
                break;
            case Constants.RIGHT:
                distance = tank.getX() + Constants.STEP_VALUE + Constants.TANK_SIZE;
                break;
        }
        return distance;
    }
}
