package game.gui.options;

import game.gui.options.PlayerTabPane;
import game.logic.GameConfig;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Created by IntelliJ IDEA.
 * User: melnikovp
 * Date: 07.06.12
 * Time: 19:17
 * To change this template use File | Settings | File Templates.
 */
public class PlayerTankPictureLoader extends JPanel {

    private BufferedImage playerTankPicture;

    public PlayerTankPictureLoader(int playerNum) {
        switch (playerNum) {
            case PlayerTabPane.FIRST:
                playerTankPicture = GameConfig.getInstance().getPlayerOneTankImage();
                break;
            case PlayerTabPane.SECOND:
                playerTankPicture = GameConfig.getInstance().getPlayerTwoTankImage();
                break;
        }
    }
    
    public void paintComponent(Graphics g) {
        g.drawImage(playerTankPicture, 0, 0, this);
    }
}
