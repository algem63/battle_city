package game.objects;

import game.gui.GameFieldPanel;
import game.logic.Constants;
import game.logic.GameConfig;
import javax.swing.ImageIcon;

/**
 * Created by IntelliJ IDEA.
 * User: melnikovp
 * Date: 12.04.13
 * Time: 12:14
 * To change this template use File | Settings | File Templates.
 */
public class HeadQuarter extends MapObject {

    private static final long serialVersionUID = 1L;
    public static int START_LEVEL_VALUE = 1;
    private int level;

    public HeadQuarter(int level, int x, int y, GameFieldPanel panel) {
        super(x, y, Constants.hqSize, 4, panel);
        this.level = level;
        setObjectImage(START_LEVEL_VALUE);
    }

    /*@Override
    public void paint(Graphics g) {
        g.drawImage(new ImageIcon(GameConfig.getInstance().getHeadquartersImageL1()).getImage(), 0, 0, this);
    }*/

    public void increaseLevel() {
        if (level < 7) {
            level++;
            setObjectImage(level);
            repaint();
        }
    }

    public void decreaseLevel() {
        if (level > 1) {
            level--;
            setObjectImage(level);
            repaint();
        }
    }

    public int getLevel() {
        return level;
    }
    
    public void drawExplode() {
        panel.drawExpolde(getX(), getY(), Constants.BIG_EXPLOSION_CODE, Constants.hqSize);
    }

    @Override
    public void setObjectImage(int levelNum) {
        switch (level) {
            case 1:
                objectImage = new ImageIcon(GameConfig.getInstance().getHeadquartersImageL1());
                break;
            case 2:
                objectImage = new ImageIcon(GameConfig.getInstance().getHeadquartersImageL2());
                break;
            case 3:
                objectImage = new ImageIcon(GameConfig.getInstance().getHeadquartersImageL3());
                break;
            case 4:
                objectImage = new ImageIcon(GameConfig.getInstance().getHeadquartersImageL4());
                break;
            case 5:
                objectImage = new ImageIcon(GameConfig.getInstance().getHeadquartersImageL5());
                break;
            case 6:
                objectImage = new ImageIcon(GameConfig.getInstance().getHeadquartersImageL6());
                break;
            case 7:
                objectImage = new ImageIcon(GameConfig.getInstance().getHeadquartersImageL7());
                break;
        }
    }
}
