package game.gui;

import game.logic.Constants;
import game.logic.GameConfig;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Created by IntelliJ IDEA.
 * User: melnikovp
 * Date: 03.12.12
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
public class GameMessage extends JPanel {

    private BufferedImage messagePicture = null;

    public GameMessage(final int pictureCode) {
        /*
        Цвета, используемые в картинке pause.png

        ff000000 черный фон
        ff7fffff голубые буквы
        ff3f3f3f горизонтальные линии внутри букв
        ff7f7f7f вертикальные линии внутри букв
        */
        switch (pictureCode) {
            case Constants.pauseMessageCode:
                messagePicture = GameConfig.getInstance().getPauseImage();
                break;
            case Constants.gameOverMessageCode:
                messagePicture = GameConfig.getInstance().getGameOverImage();
                break;
            case Constants.youWinMessageCode:
                messagePicture = GameConfig.getInstance().getYouWinImage();
                break;
            default:
                break;
        }
        // это чтобы фон картинки был прозрачным.
        setOpaque(false);
    }

    @Override
    protected final void paintComponent(final Graphics g) {
        for (int y = 0; y < messagePicture.getHeight(); ++y) {
            for (int x = 0; x < messagePicture.getWidth(); ++x) {
                // делаем черный цвет прозрачным
                int argb = messagePicture.getRGB(x, y);
                if (argb == Constants.BLACK_BACKGROUND) {
                    messagePicture.setRGB(x, y, 0);
                }
            }
        }
        g.drawImage(messagePicture, 0, 0, this);
    }
}
