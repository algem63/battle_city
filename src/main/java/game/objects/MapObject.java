package game.objects;

import game.gui.game.GameFieldPanel;
import game.logic.Constants;
import game.logic.GameConfig;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * Created by IntelliJ IDEA.
 * User: melnikovp
 * Date: 19.07.12
 * Time: 16:54
 * To change this template use File | Settings | File Templates.
 */

public class MapObject extends Component implements Serializable {
    private static final long serialVersionUID = 1L;

    protected ImageIcon objectImage;
    protected ImageIcon explosionImage;

    protected GameFieldPanel panel;
    //TODO ��������, ����� ����� ��� ����
    private int xCoord;    
    private int yCoord;
    private int imageNum;
    private int objectSize;

    protected MapObject() {
    }

    public MapObject(final int xCoord, final int yCoord, int size, int imageNum, final GameFieldPanel panel) {        
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.objectSize = size;
        this.imageNum = imageNum;
        this.panel = panel;
        setBounds(xCoord, yCoord, size, size);
        setObjectImage(imageNum);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (e.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK) {
                    removeMapObject();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                 if (e.getButton() == MouseEvent.BUTTON3) {
                    removeMapObject();
                }
            }            
        });        
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage dest = null;
        Graphics2D g2;
        if (imageNum == Constants.EXPLOSION_CODE || imageNum == Constants.TANK_CODE) {
            switch (imageNum) {
                case Constants.EXPLOSION_CODE:
                    dest = new BufferedImage(Constants.EXPLOSION_SIZE, Constants.EXPLOSION_SIZE,
                            BufferedImage.TYPE_INT_ARGB);                
                    break;
                case Constants.TANK_CODE:
                    dest = new BufferedImage(Constants.TANK_SIZE, Constants.TANK_SIZE, BufferedImage.TYPE_INT_ARGB);
                    break;            
            }
            if (dest != null) {
                g2 = dest.createGraphics();
                g2.drawImage(explosionImage.getImage(), 0, 0, this);
                makeImageTransparent(dest);
                g.drawImage(dest, 0, 0, this);
            }            
        } else {
            g.drawImage(objectImage.getImage(), 0, 0, this);
        }         
    }

    //TODO ���� ����� ����������� � ���������� �������
    private void makeImageTransparent(BufferedImage dest) {
        for (int y = 0; y < dest.getHeight(); ++y) {
            for (int x = 0; x < dest.getWidth(); ++x) {
                int argb = dest.getRGB(x, y);
                if (argb == Constants.BLACK_BACKGROUND) {
                    dest.setRGB(x, y, 0);
                }
            }
        }
    }

    public void setObjectImage(int imageNum) {        
        switch (imageNum) {
            case 1:
                this.objectImage = new ImageIcon(GameConfig.getInstance().getBrickImage());
                break;
            case 2:
                this.objectImage = new ImageIcon(GameConfig.getInstance().getConcreteImage());
                break;
            case 3:
                this.objectImage = new ImageIcon(GameConfig.getInstance().getWaterImage());
                break;
            case 4:
                this.objectImage = new ImageIcon(GameConfig.getInstance().getHeadquartersImageL1());
                break;
            case 5:
//                this.objectImage = new ImageIcon(GameConfig.getInstance().getExplosionImage());
                this.explosionImage = new ImageIcon(GameConfig.getInstance().getExplosionImage());
                break;
            case 6:
                this.explosionImage = new ImageIcon(GameConfig.getInstance().getTankExplosionImage());
//                this.explosionImage = (BufferedImage) GameConfig.getInstance().getTankExplosionImage();
                break;
        }
    }

    public void removeMapObject() {
        panel.remove(this);
        panel.repaint(getBounds());
    }

    public int getObjectSize() {
        return objectSize;
    }

    public int getImageNum() {
        return imageNum;
    }    
}
