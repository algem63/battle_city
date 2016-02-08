package game.gui.options;

import game.logic.GameConfig;
import game.logic.KeySetHandler;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.Properties;

public class GameOptionsTabPane extends JPanel {

    //TODO баг: нажать две кнопки пользовательских клавиш подряд
    private JCheckBox randomTanks;
    private JLabel tanksCountLabel;
    private JLabel onFieldLabel;
    private JSeparator line;
    private JTextField tanksCountField;
    private JTextField onFieldField;
    private JToggleButton pauseButton;
    private JToggleButton restartButton;
    //TODO по-прежнему не поддерживается
    private JButton but3;

    public GameOptionsTabPane() {
        super();
        setLayout(null);
        addComponents();
    }

    public final void addComponents() {
        randomTanks = new JCheckBox("Random Tanks");
        randomTanks.setBounds(10, 5, 200, 30);
        randomTanks.setFont(new Font("Times New Roman", Font.BOLD, 16));
        if (Boolean.parseBoolean(
                GameConfig.getInstance().getProperty("randomTanks"))) {
            randomTanks.setSelected(true);
        } else {
            randomTanks.setSelected(false);
        }
        randomTanks.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                GameConfig.getInstance().setProperty("randomTanks",
                        String.valueOf(randomTanks.isSelected()));
            }
        });
        add(randomTanks);

        tanksCountLabel = new JLabel("Tanks Count:");
        tanksCountLabel.setBounds(10, 35, 100, 30);
        tanksCountLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        add(tanksCountLabel);

        tanksCountField = new JTextField();
        tanksCountField.setBounds(110, 35, 100, 30);
        tanksCountField.setFont(new Font("Times New Roman", Font.BOLD, 16));
        tanksCountField.setText(
                GameConfig.getInstance().getProperty("tanksCount"));
        add(tanksCountField);

        onFieldLabel = new JLabel("On Field:");
        onFieldLabel.setBounds(10, 65, 200, 30);
        onFieldLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        add(onFieldLabel);

        onFieldField = new JTextField();
        onFieldField.setBounds(110, 68, 100, 30);
        onFieldField.setFont(new Font("Times New Roman", Font.BOLD, 16));
        onFieldField.setText(GameConfig.getInstance().getProperty("onField"));
        add(onFieldField);

        line = new JSeparator();
        line.setBounds(10, 110, 285, 10);
        add(line);

        pauseButton = new JToggleButton();
        pauseButton.setBounds(38, 120, 70, 40);
        pauseButton.setToolTipText("PAUSE");
        pauseButton.setText(
                KeyEvent.getKeyText(
                        Integer.parseInt(GameConfig.getInstance().getProperty("gamePause"))));
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pauseButton.addKeyListener(
                        new KeySetHandler(pauseButton, "gamePause"));
            }
        });
        add(pauseButton);

        restartButton = new JToggleButton();
        restartButton.setBounds(118, 120, 70, 40);
        restartButton.setToolTipText("RESTART");
        restartButton.setText(
                KeyEvent.getKeyText(
                        Integer.parseInt(GameConfig.getInstance().getProperty("gameRestart"))));
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                restartButton.addKeyListener(
                        new KeySetHandler(restartButton, "gameRestart"));
            }
        });
        add(restartButton);

        but3 = new JButton();
        but3.setBounds(198, 120, 70, 40);
        but3.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                JOptionPane.showMessageDialog(new JFrame(),
                                            "Not supported yet.",
                                            "Warning",
                                            JOptionPane.WARNING_MESSAGE);
            }
        });
        add(but3);
    }

    public final void setDefaultValues(final Properties defaultValues) {
        GameConfig.getInstance().setProperty("randomTanks", defaultValues.getProperty("randomTanks"));
        randomTanks.setSelected(Boolean.parseBoolean(GameConfig.getInstance().getProperty("randomTanks")));

        GameConfig.getInstance().setProperty("tanksCount", defaultValues.getProperty("tanksCount"));
        tanksCountField.setText(GameConfig.getInstance().getProperty("tanksCount"));

        GameConfig.getInstance().setProperty("onField", defaultValues.getProperty("onField"));
        onFieldField.setText(GameConfig.getInstance().getProperty("onField"));

        GameConfig.getInstance().setProperty("gamePause", defaultValues.getProperty("gamePause"));
        pauseButton.setText(KeyEvent.getKeyText(Integer.parseInt(GameConfig.getInstance().getProperty("gamePause"))));

        GameConfig.getInstance().setProperty("gameRestart", defaultValues.getProperty("gameRestart"));
        restartButton.setText(KeyEvent.getKeyText
                (Integer.parseInt(GameConfig.getInstance().getProperty("gameRestart"))));
    }

    public final String getTanksCount() {
        return tanksCountField.getText();
    }

    public final String getOnField() {
        return onFieldField.getText();
    }

    public final void unselectButtons() {
        pauseButton.setSelected(false);
        restartButton.setSelected(false);
    }
}


