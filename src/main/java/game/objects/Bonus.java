package game.objects;

import game.logic.Constants;
import game.logic.GameConfig;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JPanel;

/**
 * Created by IntelliJ IDEA. User: melnikovp Date: 14.03.13 Time: 17:15 To
 * change this template use File | Settings | File Templates.
 */
public class Bonus extends JPanel {

    public static final int LIFE = 0;
    public static final int SPADE = 1;
    public static final int STAR = 2;   

    private BufferedImage bonusImage;
    private int x, y, type;

    public Bonus() {
        setOpaque(false);
        createBonusType();        
        createBonusPosition();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        makeBonusTransparent();
        g.drawImage(bonusImage, 0, 0, this);
    }
    
    private void createBonusPosition() {
        Random posGenerator = new Random(System.currentTimeMillis());  
        x = posGenerator.nextInt(Constants.gameFieldWidth - Constants.bonusSize);
        y = posGenerator.nextInt(Constants.gameFieldHeight - Constants.bonusSize);
        setBounds(x, y, Constants.bonusSize, Constants.bonusSize);
    }

    private void createBonusType() {
        int type = new Random(System.currentTimeMillis()).nextInt(3);
        switch (type) {
            case Bonus.LIFE:
                this.bonusImage = GameConfig.getInstance().getLifeBonusImage();
                this.type = Bonus.LIFE;
                break;
            case Bonus.SPADE:
                this.bonusImage = GameConfig.getInstance().getSpadeBonusImage();
                this.type = Bonus.SPADE;
                break;
            case Bonus.STAR:
                this.bonusImage = GameConfig.getInstance().getStarBonusImage();
                this.type = Bonus.STAR;
                break;
            default:
                break;
        }
    }    

    private void makeBonusTransparent() {
        for (int y = 0; y < bonusImage.getHeight(); ++y) {
            for (int x = 0; x < bonusImage.getWidth(); ++x) {                
                int argb = bonusImage.getRGB(x, y);
                if (argb == Constants.BLACK_BACKGROUND) {
                    bonusImage.setRGB(x, y, 0);
                }
            }
        }
    }

    public int getType() {
        return type;
    }
}
