package game.gui;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.HeadlessException;

//TODO возможно ли сделать абстрактным
public class BasicForm extends JFrame {

    protected Dimension screenResolution;

    public BasicForm() throws HeadlessException {
        super();
        screenResolution = Toolkit.getDefaultToolkit().getScreenSize();
        setVisible(true);
        setLayout(null);
        setResizable(false);
    }

    public void addComponents() {
    }

    /**
     *
     * @param width ширина исходной формы
     * @return начальное значение координаты верхнего левого угла формы по оси X
     */
    protected final int calculateXStartCoord(final int width) {
         return screenResolution.width / 2 - width / 2;
    }

    /**
     *
     * @param height высота исходной формы
     * @return начальное значение координаты верхнего левого угла формы по оси Y
     */
    protected final int calculateYStartCoord(final int height) {
        return screenResolution.height / 2 - height / 2;
    }
}
