package game.objects.tank;

import game.gui.game.GameFieldPanel;
import game.logic.ShootHandler;
import game.objects.Shell;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class AbstractTank extends JPanel {

    protected Image tankPicture;
    protected GameFieldPanel gameFieldPanel;
    protected Timer movementTimer, directionChooser, shootTimer;
    protected int direction;
    private boolean fireKeyPressed = false;
    protected Toolkit kit;
    private boolean playerControlled;
    protected boolean alive;
    protected boolean doubleShot;
    protected Shell shell, shell2 = null;
    protected int life, starsCount, speedDelay;

    protected int currentBullets, maxBullets;

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
    
    protected static final int SPEED_DELAY = 75;
    
    public AbstractTank(final GameFieldPanel gameFieldPanel,
            final boolean playerControlled, final int initLifeValue) {
        life = initLifeValue;
        starsCount = 0;
        currentBullets = 0;
        maxBullets = 1;        
        alive = true;
        setOpaque(false);
        this.gameFieldPanel = gameFieldPanel;
        this.playerControlled = playerControlled;
        kit = Toolkit.getDefaultToolkit();
        shootTimer = new Timer(2500, new ShootHandler(this, gameFieldPanel));
    }

    public Timer getMovementTimer() {
        return movementTimer;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(tankPicture, 0, 0, this);
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
        if (!playerControlled) {
            gameFieldPanel.decreaseEnemyTanksOnField();
        }
    }

    public boolean isPlayerControlled() {
        return playerControlled;
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
        if (directionChooser != null) {
            directionChooser.start();
        }
        if (shootTimer != null) {
            shootTimer.start();
        }
    }

    public void stopShootTimer() {
        shootTimer.stop();
    }

    public void stopAllTimers() {
        if (directionChooser != null) {
            directionChooser.stop();
        }
        if (shootTimer != null) {
            shootTimer.stop();
        }
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

    public boolean isFireKeyPressed() {
        return fireKeyPressed;
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

    /*private void initKeyListening() {
     int upCode = Integer.parseInt(GameConfig.getInstance().getProperty("playerOneUp"));
     String upKeyName = KeyEvent.getKeyText(upCode);
     int downCode = Integer.parseInt(GameConfig.getInstance().getProperty("playerOneDown"));
     int leftCode = Integer.parseInt(GameConfig.getInstance().getProperty("playerOneLeft"));
     int rightCode = Integer.parseInt(GameConfig.getInstance().getProperty("playerOneRight"));
     int fireCode = Integer.parseInt(GameConfig.getInstance().getProperty("playerOneFire"));
     String fireKeyName = KeyEvent.getKeyText(fireCode);

     getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(upCode, 0), "moveTankUp");
     getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(upCode, 0, true), "stopMovingTankUp");
     getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.getKeyText(downCode)), "moveTankDown");
     getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.getKeyText(leftCode)), "moveTankLeft");
     getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.getKeyText(rightCode)), "moveTankRight");
     getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(fireCode, 0, true), "playerTankFire");

     getActionMap().put("moveTankUp", new PlayerTankUpStartMovementAction(this));
     getActionMap().put("stopMovingTankUp", new PlayerTankUpStopMovementAction(this));
     getActionMap().put("playerTankFire", new PlayerTankFireAction(this, gameFieldPanel));
     }*/
    public void setFireKeyPressed(boolean fireKeyPressed) {
        this.fireKeyPressed = fireKeyPressed;
    }

    public void setMaxBullets(int maxBullets) {
        this.maxBullets = maxBullets;
    }
}
