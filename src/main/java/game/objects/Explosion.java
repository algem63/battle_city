package game.objects;

import game.gui.GameFieldPanel;

/**
 * Created with IntelliJ IDEA.
 * User: Павел
 * Date: 20.04.12
 * Time: 13:46
 * To change this template use File | Settings | File Templates.
 */
public class Explosion extends Thread {
    GameFieldPanel gameFieldPanel;
    MapObject mapObject;

    public Explosion(GameFieldPanel panel, MapObject mapObject) {
        this.gameFieldPanel = panel;
        this.mapObject = mapObject;
    }

    public void run() {
        // TODO Exception in thread "Thread-3" java.lang.IllegalArgumentException: illegal component position
        gameFieldPanel.add(mapObject, new Integer(2));
        gameFieldPanel.repaint(mapObject.getBounds());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {            
        }
        gameFieldPanel.remove(mapObject);
        gameFieldPanel.repaint(mapObject.getBounds());
    }
}
