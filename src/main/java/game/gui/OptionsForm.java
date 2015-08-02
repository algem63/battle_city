package game.gui;

import game.gui.intro.IntroForm;
import game.logic.Constants;
import game.logic.GameConfig;
import game.objects.tank.AbstractTank;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
        setBounds(calculateXStartCoord(formWidth),
                  calculateYStartCoord(formHeight),
                  formWidth,
                  formHeight);
        addComponents();
        repaint();
    }

    @Override
    public final void addComponents() {
        // открытие стартовой формы после нажатия на крестик
        //TODO заменить на адаптер
        addWindowListener(new WindowListener() {
            public void windowOpened(final WindowEvent e) {
            }

            public void windowClosing(final WindowEvent e) {
                new IntroForm();
            }

            public void windowClosed(final WindowEvent e) {
            }

            public void windowIconified(final WindowEvent e) {
            }

            public void windowDeiconified(final WindowEvent e) {
            }

            public void windowActivated(final WindowEvent e) {
            }

            public void windowDeactivated(final WindowEvent e) {
            }
        });

        playerOneOptionsTab = new PlayerTabPane(AbstractTank.G2_SHELL_UP,
                                                "one");
        playerTwoOptionsTab = new PlayerTabPane(Constants.playerTwoTankUp,
                                                "two");
        gameOptionsTab = new GameOptionsTabPane();

        optionTabs = new JTabbedPane();
        optionTabs.addChangeListener(new ChangeListener() {
            public void stateChanged(final ChangeEvent e) {
                // Снимает выделение со всех кнопок
                // на вкладках при смене активной вкладки
                JTabbedPane pane = (JTabbedPane)e.getSource();
                JPanel panel = (JPanel)pane.getSelectedComponent();                
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

        defaultButton = new JButton("Default");
        defaultButton.setBounds(10, getSize().height - 57, 75, 25);
        defaultButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Properties defaultValues = new Properties();
                try {
                    defaultValues.load(getClass().getResourceAsStream(Constants.defaultConfig));
                } catch (IOException e1) {
                    GameConfig.getInstance().writeErrorMessageToLog(e1);
                }
                playerOneOptionsTab.setDefaultValues(defaultValues);
                playerTwoOptionsTab.setDefaultValues(defaultValues);
                gameOptionsTab.setDefaultValues(defaultValues);
            }
        });
        add(defaultButton);

        okButton = new JButton("OK");
        okButton.setBounds(130, getSize().height - 57, 75, 25);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GameConfig.getInstance().setProperty("tanksCount",
                        gameOptionsTab.getTanksCount());
                GameConfig.getInstance().setProperty("onField",
                        gameOptionsTab.getOnField());
                GameConfig.getInstance().setProperty("playerOneName",
                        playerOneOptionsTab.getPlayerName());
                GameConfig.getInstance().setProperty("playerTwoName",
                        playerTwoOptionsTab.getPlayerName());
                GameConfig.getInstance().saveConfig();
                introForm = new IntroForm();
                dispose();
            }
        });
        add(okButton);

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
}
