package game.gui.options;

import game.gui.common.BasicForm;
import game.gui.intro.IntroForm;
import game.logic.Constants;
import game.logic.GameConfig;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Properties;

public class OptionsForm extends BasicForm {
    private JTabbedPane optionTabs;
    private JButton defaultButton;
    private JButton okButton;
    private JButton cancelButton;

    private BasicForm introForm;
    private PlayerTabPane playerOneOptionsTab;
    private PlayerTabPane playerTwoOptionsTab;
    private GameOptionsTabPane gameOptionsTab;

    private final int formWidth = 314;
    private final int formHeight = 264;

    public OptionsForm() throws HeadlessException {
        super();
        setTitle("Options");
        setLayout(null);
//        setResizable(true);
        setBounds(calculateXStartCoord(formWidth), calculateYStartCoord(formHeight), formWidth, formHeight);
        addComponents();
        repaint();
    }

    @Override
    public final void addComponents() {
        // открытие стартовой формы после нажатия на крестик
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new IntroForm();
            }
        });

        playerOneOptionsTab = new PlayerTabPane(PlayerTabPane.FIRST);
        playerTwoOptionsTab = new PlayerTabPane(PlayerTabPane.SECOND);
        gameOptionsTab = new GameOptionsTabPane();

        optionTabs = new JTabbedPane();
        optionTabs.addChangeListener(new ChangeListener() {
            public void stateChanged(final ChangeEvent e) {
                // Снимает выделение со всех кнопок
                // на вкладках при смене активной вкладки
                JTabbedPane pane = (JTabbedPane) e.getSource();
                JPanel panel = (JPanel) pane.getSelectedComponent();
                if (panel instanceof PlayerTabPane) {
                    ((PlayerTabPane) panel).unselectButtons();
                } else if (panel instanceof GameOptionsTabPane) {
                    ((GameOptionsTabPane) panel).unselectButtons();
                }
            }
        });
        optionTabs.setBounds(0, 0, getSize().width, getSize().height - 60);
        optionTabs.addTab("Player 1", playerOneOptionsTab);
        optionTabs.addTab("Player 2", playerTwoOptionsTab);
        optionTabs.addTab("Game", gameOptionsTab);
        add(optionTabs);

        addDefaultButton();
        addOkButton();
        addCancelButton();
    }

    private void addCancelButton() {
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(215, getSize().height - 57, 75, 25);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                introForm = new IntroForm();
                dispose();
            }
        });
        add(cancelButton);
    }

    private void addOkButton() {
        okButton = new JButton("OK");
        okButton.setBounds(130, getSize().height - 57, 75, 25);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameConfig.getInstance().setProperty("tanksCount", gameOptionsTab.getTanksCount());
                GameConfig.getInstance().setProperty("onField", gameOptionsTab.getOnField());
                GameConfig.getInstance().setProperty("playerOneName", playerOneOptionsTab.getPlayerName());
                GameConfig.getInstance().setProperty("playerTwoName", playerTwoOptionsTab.getPlayerName());
                GameConfig.getInstance().saveConfig();
                introForm = new IntroForm();
                dispose();
            }
        });
        add(okButton);
    }

    private void addDefaultButton() {
        defaultButton = new JButton("Default");
        defaultButton.setBounds(10, getSize().height - 57, 75, 25);
        defaultButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Properties defaultValues = new Properties();
                try {
                    defaultValues.load(getClass().getResourceAsStream(Constants.DEFAULT_CONFIG));
                } catch (IOException e1) {
                    GameConfig.getInstance().writeErrorMessageToLog(e1);
                }                playerOneOptionsTab.setDefaultValues(defaultValues);
                playerTwoOptionsTab.setDefaultValues(defaultValues);
                gameOptionsTab.setDefaultValues(defaultValues);
            }
        });
        add(defaultButton);
    }
}
