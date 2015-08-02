package game.objects;

import game.objects.tank.AbstractTank;
import game.gui.GameFieldPanel;
import game.logic.Constants;
import game.logic.GameConfig;
import game.logic.ShellMovementHandler;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Shell extends JPanel {

    private int direction;
    private BufferedImage bulletPicture;
    //TODO зачем нужно это поле?
    private GameFieldPanel gameFieldPanel;
    public Timer timer;
    private AbstractTank tank;
    private boolean piercing;

    private int positionCode;

    public static int CENTER = 1;
    public static int H_UP = 2;
    public static int H_DOWN = 3;
    public static int V_LEFT = 4;
    public static int V_RIGHT = 5;

    public static final int FIRST = 1;
    public static final int SECOND = 2;
    private int num;

    public Shell(GameFieldPanel gameFieldPanel, int direction, AbstractTank tank, int num) {
        this.num = num;
        this.tank = tank;
        this.gameFieldPanel = gameFieldPanel;
        this.direction = direction;
        setShellPicture(direction);        
        setOpaque(false);
        timer = new Timer(20, new ShellMovementHandler(this, direction, gameFieldPanel, tank));
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        makeTransparent();
        g.drawImage(bulletPicture, 0, 0, this);
    }

    private void makeTransparent() {
        for (int y = 0; y < bulletPicture.getHeight(); ++y) {
            for (int x = 0; x < bulletPicture.getWidth(); ++x) {                
                int argb = bulletPicture.getRGB(x, y);
                if (argb == Constants.BLACK_BACKGROUND) {
                    bulletPicture.setRGB(x, y, 0);
                }
            }
        }
    }

    private void setShellPicture(int direction) {
        try {
            if (direction == Constants.UP) {
                bulletPicture = ImageIO.read(getClass().getResource(Constants.shellUp));
            } else if (direction == Constants.DOWN) {
                bulletPicture = ImageIO.read(getClass().getResource(Constants.shellDown));
            } else if (direction == Constants.LEFT) {
                bulletPicture = ImageIO.read(getClass().getResource(Constants.shellLeft));
            } else if (direction == Constants.RIGHT) {
                bulletPicture = ImageIO.read(getClass().getResource(Constants.shellRight));
            }
        } catch (IOException e) {
            GameConfig.getInstance().writeErrorMessageToLog(e);
        }
    }

    public void removeShellFromTank() {
        switch (getNum()) {
            case Shell.FIRST:
                if (tank.getShell() != null) {
                    tank.setShell(null);
                }
                break;
            case Shell.SECOND:
                if (tank.getShell2() != null) {
                    tank.setShell2(null);
                }
                break;
        }
    }

    public int getDirection() {
        return direction;
    }

    public void setPiercing(boolean value) {
        piercing = value;
    }

    public boolean isPiercing() {
        return piercing;
    }

    public void setPositionCode(int code) {
        positionCode = code;
    }

    public int getPositionCode() {
        return positionCode;
    }

    public AbstractTank getTank() {
        return tank;
    }

    public void stopTimer() {
        timer.stop();
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
