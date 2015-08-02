package game.gui;

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

    public PlayerTankPictureLoader(String playerNum) {
        if (playerNum.equals("one")) {
            playerTankPicture = GameConfig.getInstance().getPlayerOneTankImage();
        } else if (playerNum.equals("two")) {
            playerTankPicture = GameConfig.getInstance().getPlayerTwoTankImage();
        }
    }
    
    public void paintComponent(Graphics g) {
        g.drawImage(playerTankPicture, 0, 0, this);
    }
}
