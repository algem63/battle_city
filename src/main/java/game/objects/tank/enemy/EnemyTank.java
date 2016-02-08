/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.objects.tank.enemy;

import game.gui.game.GameFieldPanel;
import game.objects.tank.AbstractTank;
import javax.swing.Timer;

/**
 *
 * @author �����
 */
public class EnemyTank extends AbstractTank {
    public static final String EN_TANK_UP = "/images/EnTankT.gif";
    public static final String EN_TANK_DOWN = "/images/EnTankB.gif";
    public static final String EN_TANK_LEFT = "/images/EnTankL.gif";
    public static final String EN_TANK_RIGHT = "/images/EnTankR.gif";

    public static final String EN_2SHELL_UP = "/images/En2ShellT.gif";
    public static final String EN_2SHELL_DOWN = "/images/En2ShellB.gif";
    public static final String EN_2SHELL_LEFT = "/images/En2ShellL.gif";
    public static final String EN_2SHELL_RIGHT = "/images/En2ShellR.gif";

    public static final String EN_QUADRO_UP = "/images/EnQuadroT.gif";
    public static final String EN_QUADRO_DOWN = "/images/EnQuadroB.gif";
    public static final String EN_QUADRO_LEFT = "/images/EnQuadroL.gif";
    public static final String EN_QUADRO_RIGHT = "/images/EnQuadroR.gif";
           
    
    public EnemyTank(final GameFieldPanel gameFieldPanel, final boolean playerControlled, final int initLifeValue) {
        super(gameFieldPanel, playerControlled, initLifeValue);
        directionChooser = new Timer(500, new DirectionChooser(this));
        movementTimer = new Timer(SPEED_DELAY, new EnemyTankMovementController(this, gameFieldPanel));
        directionChooser.start();
        shootTimer.start();
    }
    
    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(int starsCount) {
        this.starsCount = starsCount;
    }

    public void setDoubleShot(boolean doubleShot) {
        this.doubleShot = doubleShot;
    } 
    
    public boolean isDoubleShot() {
        return this.doubleShot;
    }
    
    public int getDirection() {
        return this.direction;
    }
    
    public void setDirection(int direction) {
        this.direction = direction;        
    }
}