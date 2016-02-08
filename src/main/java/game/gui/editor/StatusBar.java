package game.gui.editor;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Created by IntelliJ IDEA.
 * User: melnikovp
 * Date: 06.06.12
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
public class StatusBar extends JPanel {
    private JLabel coords;

    public StatusBar() {
        setBorder(BorderFactory.createEtchedBorder());
        setLayout(null);
        addComponents();
    }

    public void addComponents() {
        coords = new JLabel();
        coords.setBounds(100, 3, 150, 30);
        coords.setBorder(BorderFactory.createEtchedBorder());
        add(coords);
    }
    
    public void setStatusBarText(String text) {
        coords.setText(text);
    }
}
