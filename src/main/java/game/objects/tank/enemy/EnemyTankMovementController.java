/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.objects.tank.enemy;

import game.gui.game.GameFieldPanel;
import game.logic.Constants;
import game.objects.Bonus;
import game.objects.HeadQuarter;
import game.objects.tank.AbstractTank;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author �����
 */
public class EnemyTankMovementController implements ActionListener {

    private EnemyTank tank;
    private GameFieldPanel gameFieldPanel;

    public EnemyTankMovementController(EnemyTank tank, GameFieldPanel gameFieldPanel) {
        this.tank = tank;
        this.gameFieldPanel = gameFieldPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {                     
        boolean hit = false;
        Rectangle nextPos = calculateNextPosition();
        int distance = calculateDistanceFromBorders();        
        for (Component mapObject : gameFieldPanel.getComponents()) {
            Rectangle mapObjectRect = mapObject.getBounds();
            Rectangle currentPos = tank.getBounds();
            if (nextPos.intersects(mapObjectRect) && !currentPos.equals(mapObjectRect) 
                    && !(mapObject instanceof Bonus)) {
                hit = true;
                break;
            }
            if (currentPos.intersects(mapObjectRect) && mapObject instanceof Bonus) {
                getBonus(mapObject);
                break;
            }
        }
        boolean tankMovementCondition = calculateTankMovementCondition(distance, hit);
        if (tankMovementCondition) {
            tank.setBounds(nextPos);
        }
        gameFieldPanel.repaint(tank.getBounds());
    }
    
    private Rectangle calculateNextPosition() {
        Rectangle tankRect = null;
        switch (tank.getDirection()) {
            case Constants.UP:                
                tankRect = new Rectangle(tank.getX(), tank.getY() - Constants.STEP_VALUE,
                        Constants.TANK_SIZE, Constants.TANK_SIZE);
                break;
            case Constants.DOWN:                
                tankRect = new Rectangle(tank.getX(), tank.getY() + Constants.STEP_VALUE,
                        Constants.TANK_SIZE, Constants.TANK_SIZE);
                break;
            case Constants.LEFT:                
                tankRect = new Rectangle(tank.getX() - Constants.STEP_VALUE, tank.getY(),
                        Constants.TANK_SIZE, Constants.TANK_SIZE);
                break;
            case Constants.RIGHT:                
                tankRect = new Rectangle(tank.getX() + Constants.STEP_VALUE, tank.getY(),
                        Constants.TANK_SIZE, Constants.TANK_SIZE);
                break;
        }
        return tankRect;
    }
    
    private int calculateDistanceFromBorders() {
        int temp = 0;
        switch (tank.getDirection()) {
            case Constants.UP:
                temp = tank.getY() - Constants.STEP_VALUE;
                break;
            case Constants.DOWN:
                temp = tank.getY() + Constants.STEP_VALUE + Constants.TANK_SIZE;
                break;
            case Constants.LEFT:
                temp = tank.getX() - Constants.STEP_VALUE;
                break;
            case Constants.RIGHT:
                temp = tank.getX() + Constants.STEP_VALUE + Constants.TANK_SIZE;
                break;
        }
        return temp;
    }
    
    private void processLifeBonusTaking(Component lifeBonus) {
        int life = tank.getLife();
        life++;
        tank.setLife(life);
        gameFieldPanel.remove(lifeBonus);
        gameFieldPanel.repaint(lifeBonus.getBounds());
    }
    
    private void processSpadeBonusTaking(Component spadeBonus) {
        gameFieldPanel.remove(spadeBonus);
        gameFieldPanel.repaint(spadeBonus.getBounds());
        Component[] components = gameFieldPanel.getComponents();
        for (Component component : components) {
            if (component instanceof HeadQuarter) {
                HeadQuarter headQuarter = (HeadQuarter) component;
                if (headQuarter.getLevel() > 1) {
                    headQuarter.decreaseLevel();
                }
            }
        }
    }
    
    private void processStarBonusTaking() {
        int starsCount = tank.getStarsCount();
        if (starsCount < 2) {
            starsCount++;
            tank.setStarsCount(starsCount);
            switch (starsCount) {
                case 0:
                    if (tank.getDirection() == Constants.UP) {
                        tank.setTankPicture(AbstractTank.EN_TANK_UP);
                    } else if (tank.getDirection() == Constants.DOWN) {
                        tank.setTankPicture(AbstractTank.EN_TANK_DOWN);
                    } else if (tank.getDirection() == Constants.LEFT) {
                        tank.setTankPicture(AbstractTank.EN_TANK_LEFT);
                    } else if (tank.getDirection() == Constants.RIGHT) {
                        tank.setTankPicture(AbstractTank.EN_TANK_RIGHT);
                    }
                    break;
                case 1:
                    if (tank.getDirection() == Constants.UP) {
                        tank.setTankPicture(AbstractTank.EN_2SHELL_UP);
                    } else if (tank.getDirection() == Constants.DOWN) {
                        tank.setTankPicture(AbstractTank.EN_2SHELL_DOWN);
                    } else if (tank.getDirection() == Constants.LEFT) {
                        tank.setTankPicture(AbstractTank.EN_2SHELL_LEFT);
                    } else if (tank.getDirection() == Constants.RIGHT) {
                        tank.setTankPicture(AbstractTank.EN_2SHELL_RIGHT);
                    }
                    break;
                case 2:
                    tank.setDoubleShot(true);
                    if (tank.getDirection() == Constants.UP) {
                        tank.setTankPicture(AbstractTank.EN_QUADRO_UP);
                    } else if (tank.getDirection() == Constants.DOWN) {
                        tank.setTankPicture(AbstractTank.EN_QUADRO_DOWN);
                    } else if (tank.getDirection() == Constants.LEFT) {
                        tank.setTankPicture(AbstractTank.EN_QUADRO_LEFT);
                    } else if (tank.getDirection() == Constants.RIGHT) {
                        tank.setTankPicture(AbstractTank.EN_QUADRO_RIGHT);
                    }
                    break;
                default:
                    break;
            }
        }
    }
    
    private boolean calculateTankMovementCondition(int distance, boolean hit) {
        boolean tankMovementCondition = false;
        switch (tank.getDirection()) {
            case Constants.UP:
                tankMovementCondition = distance >= 0 && hit == false;
                break;
            case Constants.DOWN:
                tankMovementCondition = distance <= Constants.GAME_FIELD_HEIGHT
                        && hit == false;
                break;
            case Constants.LEFT:
                tankMovementCondition = distance >= 0 && hit == false;
                break;
            case Constants.RIGHT:
                tankMovementCondition = distance <= Constants.GAME_FIELD_WIDTH
                        && hit == false;
                break;
        }
        return tankMovementCondition;
    }
        
    private void getBonus(Component mapObject) {
        Bonus bonus = (Bonus) mapObject;
        switch (bonus.getType()) {
            case Bonus.LIFE:
                processLifeBonusTaking(mapObject);
                break;
            case Bonus.SPADE:
                processSpadeBonusTaking(mapObject);
                break;
            case Bonus.STAR:
                processStarBonusTaking();
                break;
        }
        gameFieldPanel.remove(mapObject);
        gameFieldPanel.repaint(mapObject.getBounds());
    }
}
