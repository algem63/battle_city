package game.logic;

import javax.swing.JToggleButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by IntelliJ IDEA.
 * User: melnikovp
 * Date: 01.06.12
 * Time: 14:40
 * To change this template use File | Settings | File Templates.
 */
public class KeySetHandler extends KeyAdapter {

    private JToggleButton but;
    private boolean keyPressed;
    private String propertyToSet;

    public KeySetHandler(JToggleButton but, String propertyToSet) {
        this.but = but;
        this.propertyToSet = propertyToSet;
    }

    public void keyPressed(KeyEvent e) {
        if (!keyPressed) {
            if (GameConfig.getInstance().containsValue(e.getKeyCode())
                    && !GameConfig.getInstance().checkTheFunctionKeyAssignedTo(e.getKeyCode()).equals(propertyToSet)) {
                GameConfig.getInstance().checkKeyBusy(e.getKeyCode());
            } else {
                but.setText(KeyEvent.getKeyText(e.getKeyCode()));
                GameConfig.getInstance().setProperty(propertyToSet, String.valueOf(e.getKeyCode()));
                keyPressed = true;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        keyPressed = false;
        but.removeKeyListener(this);
        but.setSelected(false);
    }
}
