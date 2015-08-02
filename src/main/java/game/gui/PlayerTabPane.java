package game.gui;

import game.logic.GameConfig;
import game.logic.KeySetHandler;
import game.objects.tank.AbstractTank;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Properties;


public class PlayerTabPane extends JPanel {
    private String file;
    private String playerNum;
    private JTextField playerName;
    private JSeparator horizontalSeparator;
    private JToggleButton playerFire;
    private JToggleButton playerUp;
    private JToggleButton playerLeft;
    private JToggleButton playerDown;
    private JToggleButton playerRight;

    public PlayerTabPane(String file, String playerNum) {
        super();
        this.file = file;
        this.playerNum = playerNum;
        setLayout(null);
        GameConfig.getInstance().loadConfig();
        addComponents();
    }

    public void addComponents() {
        PlayerTankPictureLoader playerTankPicture;
        playerTankPicture = new PlayerTankPictureLoader(playerNum);
        playerTankPicture.setBounds(8, 8, 50, 50);
        add(playerTankPicture);

        playerName = new JTextField();
        playerName.setBounds(70, 20, 200, 28);
        if (playerNum.equals("one")) {
            playerName.setText(GameConfig.getInstance().getProperty("playerOneName"));
        } else if (playerNum.equals("two")) {
            playerName.setText(GameConfig.getInstance().getProperty("playerTwoName"));
        }
        playerName.setFont(new Font("Times New Roman", Font.BOLD, 16));
        add(playerName);

        horizontalSeparator = new JSeparator();
        horizontalSeparator.setBounds(10, 70, getSize().width-28, 5);
        add(horizontalSeparator);

        playerFire = new JToggleButton();
        playerFire.setBounds(10, 80, 70, 40);
        // Убирает реакцию кнопки на нажатие Space
        playerFire.getInputMap().getParent().clear();
        if (playerNum.equals("one")) {
            playerFire.setToolTipText("Player 1 fire");
            playerFire.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerOneFire"))));
        } else if (playerNum.equals("two")) {
            playerFire.setToolTipText("Player 2 fire");
            playerFire.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoFire"))));
        }
        playerFire.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (playerNum.equals("one")) {
                    playerFire.addKeyListener(new KeySetHandler(playerFire, "playerOneFire"));
                } else if (playerNum.equals("two")) {
                    playerFire.addKeyListener(new KeySetHandler(playerFire, "playerTwoFire"));
                }
            }
        });
        add(playerFire);

        playerUp = new JToggleButton();
        playerUp.setBounds(139, 80, 70, 40);
        playerUp.getInputMap().getParent().clear();
        if (playerNum.equals("one")) {
            playerUp.setToolTipText("Player 1 up");
            playerUp.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerOneUp"))));
        } else if (playerNum.equals("two")) {
            playerUp.setToolTipText("Player 2 up");
            playerUp.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoUp"))));
        }
        playerUp.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (playerNum.equals("one")) {
                    playerUp.addKeyListener(new KeySetHandler(playerUp, "playerOneUp"));
                } else if (playerNum.equals("two")) {
                    playerUp.addKeyListener(new KeySetHandler(playerUp, "playerTwoUp"));
                }
            }
        });
        add(playerUp);

        playerLeft = new JToggleButton();
        playerLeft.setBounds(59, 130, 70, 40);
        playerUp.getInputMap().getParent().clear();
        if (playerNum.equals("one")) {
            playerLeft.setToolTipText("Player 1 left");
            playerLeft.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerOneLeft"))));
        } else if (playerNum.equals("two")) {
            playerLeft.setToolTipText("Player 2 left");
            playerLeft.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoLeft"))));
        }
        playerLeft.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (playerNum.equals("one")) {
                    playerLeft.addKeyListener(new KeySetHandler(playerLeft, "playerOneLeft"));
                } else if (playerNum.equals("two")) {
                    playerLeft.addKeyListener(new KeySetHandler(playerLeft, "playerTwoLeft"));
                }
            }
        });
        add(playerLeft);

        playerDown = new JToggleButton();
        playerDown.setBounds(139, 130, 70, 40);
        if (playerNum.equals("one")) {
            playerDown.setToolTipText("Player 1 down");
            playerDown.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerOneDown"))));
        }
        else if (playerNum.equals("two")) {
            playerDown.setToolTipText("Player 2 down");
            playerDown.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoDown"))));
        }
        playerDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (playerNum.equals("one")) {
                    playerDown.addKeyListener(new KeySetHandler(playerDown, "playerOneDown"));
                } else if (playerNum.equals("two")) {
                    playerDown.addKeyListener(new KeySetHandler(playerDown, "playerTwoDown"));
                }
            }
        });
        add(playerDown);

        playerRight = new JToggleButton();
        playerRight.setBounds(219, 130, 70, 40);
        if (playerNum.equals("one")) {
            playerRight.setToolTipText("Player 1 right");
            playerRight.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerOneRight"))));
        }
        else if (playerNum.equals("two")) {
            playerRight.setToolTipText("Player 2 right");
            playerRight.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoRight"))));
        }
        playerRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (playerNum.equals("one")) {
                    playerRight.addKeyListener(new KeySetHandler(playerRight, "playerOneRight"));
                } else if (playerNum.equals("two")) {
                    playerRight.addKeyListener(new KeySetHandler(playerRight, "playerTwoRight"));
                }
            }
        });
        add(playerRight);
    }

    public void setDefaultValues(Properties defaultValues) {
       if (playerNum.equals("one")) {
           GameConfig.getInstance().setProperty("playerOneUp",
                   defaultValues.getProperty("playerOneUp"));
           playerUp.setText(KeyEvent.getKeyText(
                   Integer.parseInt(GameConfig.getInstance().getProperty("playerOneUp"))));

           GameConfig.getInstance().setProperty("playerOneLeft",
                   defaultValues.getProperty("playerOneLeft"));
           playerLeft.setText(KeyEvent.getKeyText(
                   Integer.parseInt(GameConfig.getInstance().getProperty("playerOneLeft"))));

           GameConfig.getInstance().setProperty("playerOneDown",
                   defaultValues.getProperty("playerOneDown"));
           playerDown.setText(KeyEvent.getKeyText(
                   Integer.parseInt(GameConfig.getInstance().getProperty("playerOneDown"))));

           GameConfig.getInstance().setProperty("playerOneRight",
                   defaultValues.getProperty("playerOneRight"));
           playerRight.setText(KeyEvent.getKeyText(
                   Integer.parseInt(GameConfig.getInstance().getProperty("playerOneRight"))));

           GameConfig.getInstance().setProperty("playerOneFire",
                   defaultValues.getProperty("playerOneFire"));
           playerFire.setText(KeyEvent.getKeyText(
                   Integer.parseInt(GameConfig.getInstance().getProperty("playerOneFire"))));
       } else if (playerNum.equals("two")) {
           GameConfig.getInstance().setProperty("playerTwoUp",
                   defaultValues.getProperty("playerTwoUp"));
           playerUp.setText(KeyEvent.getKeyText(
                   Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoUp"))));

           GameConfig.getInstance().setProperty("playerTwoLeft",
                   defaultValues.getProperty("playerTwoLeft"));
           playerLeft.setText(KeyEvent.getKeyText(
                   Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoLeft"))));

           GameConfig.getInstance().setProperty("playerTwoDown",
                   defaultValues.getProperty("playerTwoDown"));
           playerDown.setText(KeyEvent.getKeyText(
                   Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoDown"))));

           GameConfig.getInstance().setProperty("playerTwoRight",
                   defaultValues.getProperty("playerTwoRight"));
           playerRight.setText(KeyEvent.getKeyText(
                   Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoRight"))));

           GameConfig.getInstance().setProperty("playerTwoFire",
                   defaultValues.getProperty("playerTwoFire"));
           playerFire.setText(KeyEvent.getKeyText(
                   Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoFire"))));
       }
    }
    
    public String getPlayerName() {
        return playerName.getText();
    }

    public void unselectButtons() {
        playerFire.setSelected(false);
        playerLeft.setSelected(false);
        playerRight.setSelected(false);
        playerUp.setSelected(false);
        playerDown.setSelected(false);
    }
}


