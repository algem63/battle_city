package game.gui.game;

import game.gui.game.GameFieldPanel;
import game.logic.Constants;
import game.logic.GameConfig;
import game.objects.Bonus;
import game.objects.MapObject;
import game.objects.tank.AbstractTank;

import java.awt.Cursor;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JProgressBar;

/**
 * Created by IntelliJ IDEA. User: melnikovp Date: 29.05.13 Time: 16:25 To
 * change this template use File | Settings | File Templates.
 */
public class MapLoader extends Thread {

    GameFieldPanel panel;
    File mapFile;

    public MapLoader(GameFieldPanel panel, File map) {
        this.panel = panel;
        this.mapFile = map;
    }

    public void run() {
        if (mapFile != null) {
            loadMap();
        }
    }

    private void loadMap() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // TODO это лучше убрать отсюда
        panel.battleField.setStaticticsToZero();
        panel.setMaxTanksCount(Integer.parseInt(GameConfig.getInstance().getProperty("tanksCount")));

        JProgressBar bar = null;
        FileInputStream loadMapStream = null;
        ObjectInputStream loadMapStream2 = null;

        panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        // очередная порция хардкода. Надо прочитать про URL и URI потом :)
        try {
            loadMapStream = new FileInputStream(mapFile);
            loadMapStream2 = new ObjectInputStream(loadMapStream);
            panel.removeAllComponents();

            // это чтобы плеертанк не мог ездить во время загрузки большой
            // карты
            if (panel.getPlayerOneTank() != null) {
                // одновременно поворачиваем его мордой вверх
                panel.getPlayerOneTank().setDirection(Constants.UP);
                panel.repaint(panel.getPlayerOneTank().getBounds());
                panel.getPlayerOneTank().setAlive(false);
            }

            Integer count = (Integer) loadMapStream2.readObject();
            int done = 0;
            Object obj = null;
            bar = addProgressBarToGameField(count.intValue());
            while ((obj = loadMapStream2.readObject()) != null) {
                MapObject mapObject = (MapObject) obj;
                panel.add(mapObject);
                done++;
                bar.setValue(done);
            }
        } catch (IOException | ClassNotFoundException e) {
            GameConfig.getInstance().writeErrorMessageToLog(e);
        } catch (NullPointerException e) {
            // GameConfig.getInstance().writeErrorMessageToLog(e);
            e.printStackTrace();
        } finally {
            try {
                assert loadMapStream2 != null;
                loadMapStream2.close();
                loadMapStream.close();
                assert bar != null;
                panel.remove(bar);
                panel.repaint(bar.getBounds());
            } catch (IOException e) {
                GameConfig.getInstance().writeErrorMessageToLog(e);
            }
        }
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        if (panel.getPlayerOneTank() == null) {
            panel.addPlayerOneTank(1);
        } else {
            panel.getPlayerOneTank().setBounds(0,
                    Constants.GAME_FIELD_HEIGHT - Constants.TANK_SIZE,
                    Constants.TANK_SIZE, Constants.TANK_SIZE);
            panel.getPlayerOneTank().setAlive(true);
            switch (panel.getPlayerOneTank().getStarsCount()) {
                case 0:
                    panel.getPlayerOneTank().setTankPicture(AbstractTank.G_TANK_UP);
                    break;
                case 1:
                    panel.getPlayerOneTank().setTankPicture(AbstractTank.G_DOUBLE_SHELL_UP);
                    break;
                case 2:
                    panel.getPlayerOneTank().setTankPicture(AbstractTank.G_QUADRO_UP);
                    break;
                case 3:
                    panel.getPlayerOneTank().setTankPicture(AbstractTank.G_QUADRO_TURBO_UP);
                    break;
            }
            panel.repaint(panel.getPlayerOneTank().getBounds());
        }
        panel.add(new Bonus(), new Integer(2));
    }

    public JProgressBar addProgressBarToGameField(int maxValue) {
        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setMaximum(maxValue);
        progressBar.setBounds(panel.getWidth() / 2 - 100, panel.getHeight() / 2 - 15, 200, 30);
        panel.add(progressBar);
        return progressBar;
    }
}
