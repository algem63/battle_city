package game.gui.bf;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by melnikov on 12.01.16.
 */
public class StatisticLabel extends JLabel {

    public StatisticLabel(String text) {
        super(text);
        setHorizontalAlignment(SwingConstants.CENTER);
        setFont(new Font("Times new roman", Font.BOLD, 16));
    }
}
