/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.objects.tank.player;

import game.gui.game.GameFieldPanel;
import game.logic.Constants;
import game.objects.Shell;
import game.objects.tank.AbstractTank;
import java.awt.Graphics;
import javax.swing.Timer;

/**
 *
 * @author �����
 */
public class PlayerTank extends AbstractTank {
    
    public static final String G_TANK_UP = "/images/GTankT.gif";
    public static final String G_TANK_DOWN = "/images/GTankB.gif";
    public static final String G_TANK_LEFT = "/images/GTankL.gif";
    public static final String G_TANK_RIGHT = "/images/GTankR.gif";

    public static final String G2_SHELL_UP = "/images/G2ShellT.gif";
    public static final String G2_SHELL_DOWN = "/images/G2ShellB.gif";
    public static final String G2_SHELL_LEFT = "/images/G2ShellL.gif";
    public static final String G2_SHELL_RIGHT = "/images/G2ShellR.gif";

    public static final String G_DOUBLE_SHELL_UP = "/images/GDoubleShellT.gif";
    public static final String G_DOUBLE_SHELL_DOWN = "/images/GDoubleShellB.gif";
    public static final String G_DOUBLE_SHELL_LEFT = "/images/GDoubleShellL.gif";
    public static final String G_DOUBLE_SHELL_RIGHT = "/images/GDoubleShellR.gif";

    public static final String G_QUADRO_UP = "/images/GQuadroT.gif";
    public static final String G_QUADRO_DOWN = "/images/GQuadroB.gif";
    public static final String G_QUADRO_LEFT = "/images/GQuadroL.gif";
    public static final String G_QUADRO_RIGHT = "/images/GQuadroR.gif";

    public static final String G_QUADRO_TURBO_UP = "/images/GQuadroTurboT.gif";
    public static final String G_QUADRO_TURBO_DOWN = "/images/GQuadroTurboB.gif";
    public static final String G_QUADRO_TURBO_LEFT = "/images/GQuadroTurboL.gif";
    public static final String G_QUADRO_TURBO_RIGHT = "/images/GQuadroTurboR.gif";
    
    /**
     *
     * @param gameFieldPanel
     * @param playerControlled
     * @param initLifeValue
     */
    public PlayerTank(final GameFieldPanel gameFieldPanel, final boolean playerControlled, final int initLifeValue) {
        super(gameFieldPanel, playerControlled, initLifeValue);           
        direction = Constants.UP;      
        movementTimer = new Timer(SPEED_DELAY, new PlayerTankMovementController(this, gameFieldPanel));
        addKeyListener(new PlayerTankKeyListener((this), gameFieldPanel));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(tankPicture, 0, 0, this);
    }
    
    public Timer getMovementTimer() {
        return movementTimer;
    }   

    public void setTankPicture(String file) {
        tankPicture = kit.getImage(getClass().getResource(file));
    }
        
    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;        
        gameFieldPanel.decreaseEnemyTanksOnField();        
    }    

    public int getDirection() {
        return direction;
    }

    public void setShell(Shell shell) {
        this.shell = shell;
    }

    public void setShell2(Shell shell) {
        this.shell2 = shell;
    }

    public Shell getShell() {
        return shell;
    }

    public Shell getShell2() {
        return shell2;
    }
    
    public void startTimer() {
        if (movementTimer != null) {
                movementTimer.start();
        }        
    }   

    public void stopAllTimers() {        
        if (movementTimer != null) {
                movementTimer.stop();
        }
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void increaseStarsCount() {
        if (starsCount < 5) {
            starsCount++;
        }
    }
    
    public void setStarsCountToZero() {
        starsCount = 0;
    }

    public void decreaseCurrentBulletsNum() {
        if (currentBullets > 0) {
            currentBullets--;
        }
    }    

    public void setMaxBullets(int maxBullets) {
        this.maxBullets = maxBullets;
    }
    
    public int getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(int value) {
        starsCount = value;
    }

    public void setSpeedDelay(int value) {
        speedDelay = value;
    }

    public void setDoubleShot(boolean doubleShot) {
        this.doubleShot = doubleShot;
    }

    public boolean isDoubleShot() {
        return doubleShot;
    }        
	
    public int getMaxBullets() {
        return maxBullets;
    }

    public int getCurrentBullets() {
        return currentBullets;
    }
	
    public void setCurrentBullets(int currentBullets) {
        this.currentBullets = currentBullets;
    }
}
