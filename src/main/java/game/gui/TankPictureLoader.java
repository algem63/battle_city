package game.gui;

import game.logic.GameConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: melnikovp
 * Date: 27.04.12
 * Time: 17:10
 * To change this template use File | Settings | File Templates.
 */
public class TankPictureLoader extends JPanel {
    private BufferedImage tankPicture;

    public TankPictureLoader() {
        tankPicture = GameConfig.getInstance().getTankImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(tankPicture, 0, 0, this);
    }
}
