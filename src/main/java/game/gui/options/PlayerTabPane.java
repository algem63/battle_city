package game.gui.options;

import game.logic.GameConfig;
import game.logic.KeySetHandler;

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

    public static final int FIRST = 1;
    public static final int SECOND = 2;
    private static final int BUTTON_HEIGHT = 70;
    private static final int BUTTON_WIDTH = 40;

    private int playerNum;
    private JTextField playerName;
    private JToggleButton playerFire;
    private JToggleButton playerUp;
    private JToggleButton playerLeft;
    private JToggleButton playerDown;
    private JToggleButton playerRight;

    public PlayerTabPane(final int playerNum) {
        super();
        this.playerNum = playerNum;
        setLayout(null);
        GameConfig.getInstance().loadConfig();
        addComponents();
    }

    public void addComponents() {
        addPlayerTankPicture();
        addPlayerName();
        addHorizontalSeparator();
        addFireButton();
        addUpButton();
        addLeftButton();
        addDownButton();
        addRightButton();
    }

    /**
     * Добавляет на форму кнопку 'Right'.
     */
    private void addRightButton() {
		playerRight = new JToggleButton();
        playerRight.setBounds(219, 130, BUTTON_HEIGHT, BUTTON_WIDTH);
        if (playerNum == FIRST) {
            playerRight.setToolTipText("Player 1 right");
            playerRight.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerOneRight"))));
        }
        else if (playerNum == SECOND) {
            playerRight.setToolTipText("Player 2 right");
            playerRight.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoRight"))));
        }
        playerRight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (playerNum == FIRST) {
                    playerRight.addKeyListener(new KeySetHandler(playerRight, "playerOneRight"));
                } else if (playerNum == SECOND) {
                    playerRight.addKeyListener(new KeySetHandler(playerRight, "playerTwoRight"));
                }
            }
        });
        add(playerRight);
	}

    /**
     * Добавляет на форму кнопку 'Down'.
     */
    private void addDownButton() {
		playerDown = new JToggleButton();
        playerDown.setBounds(139, 130, BUTTON_HEIGHT, BUTTON_WIDTH);
        if (playerNum == FIRST) {
            playerDown.setToolTipText("Player 1 down");
            playerDown.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerOneDown"))));
        }
        else if (playerNum == SECOND) {
            playerDown.setToolTipText("Player 2 down");
            playerDown.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoDown"))));
        }
        playerDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (playerNum == FIRST) {
                    playerDown.addKeyListener(new KeySetHandler(playerDown, "playerOneDown"));
                } else if (playerNum == SECOND) {
                    playerDown.addKeyListener(new KeySetHandler(playerDown, "playerTwoDown"));
                }
            }
        });
        add(playerDown);
	}

    /**
     * Добавляет на форму кнопку 'Left'.
     */
    private void addLeftButton() {
		playerLeft = new JToggleButton();
        playerLeft.setBounds(59, 130, BUTTON_HEIGHT, BUTTON_WIDTH);
        playerUp.getInputMap().getParent().clear();
        if (playerNum == FIRST) {
            playerLeft.setToolTipText("Player 1 left");
            playerLeft.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerOneLeft"))));
        } else if (playerNum == SECOND) {
            playerLeft.setToolTipText("Player 2 left");
            playerLeft.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoLeft"))));
        }
        playerLeft.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (playerNum == FIRST) {
                    playerLeft.addKeyListener(new KeySetHandler(playerLeft, "playerOneLeft"));
                } else if (playerNum == SECOND) {
                    playerLeft.addKeyListener(new KeySetHandler(playerLeft, "playerTwoLeft"));
                }
            }
        });
        add(playerLeft);
	}

    /**
     * Добавляет на форму кнопку 'Up'.
     */
    private void addUpButton() {
		playerUp = new JToggleButton();
        playerUp.setBounds(139, 80, BUTTON_HEIGHT, BUTTON_WIDTH);
        playerUp.getInputMap().getParent().clear();
        if (playerNum == FIRST) {
            playerUp.setToolTipText("Player 1 up");
            playerUp.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerOneUp"))));
        } else if (playerNum == SECOND) {
            playerUp.setToolTipText("Player 2 up");
            playerUp.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoUp"))));
        }
        playerUp.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (playerNum == FIRST) {
                    playerUp.addKeyListener(new KeySetHandler(playerUp, "playerOneUp"));
                } else if (playerNum == SECOND) {
                    playerUp.addKeyListener(new KeySetHandler(playerUp, "playerTwoUp"));
                }
            }
        });
        add(playerUp);
	}

    /**
     * Добавляет на форму кнопку 'Fire'.
     */
    private void addFireButton() {
		playerFire = new JToggleButton();
        playerFire.setBounds(10, 80, BUTTON_HEIGHT, BUTTON_WIDTH);
        // Убирает реакцию кнопки на нажатие Space
        playerFire.getInputMap().getParent().clear();
        if (playerNum == FIRST) {
            playerFire.setToolTipText("Player 1 fire");
            playerFire.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerOneFire"))));
        } else if (playerNum == SECOND) {
            playerFire.setToolTipText("Player 2 fire");
            playerFire.setText(KeyEvent.getKeyText(
                    Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoFire"))));
        }
        playerFire.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (playerNum == FIRST) {
                    playerFire.addKeyListener(new KeySetHandler(playerFire, "playerOneFire"));
                } else if (playerNum == SECOND) {
                    playerFire.addKeyListener(new KeySetHandler(playerFire, "playerTwoFire"));
                }
            }
        });
        add(playerFire);
	}

	private void addHorizontalSeparator() {
        JSeparator horizontalSeparator = new JSeparator();
        horizontalSeparator.setBounds(10, 70, getSize().width - 28, 5);
        add(horizontalSeparator);
	}

    /**
     * Добавляет текстовое поле для ввода имени пользователя.
     */
    private void addPlayerName() {
		playerName = new JTextField();
        playerName.setBounds(70, 20, 200, 28);
        if (playerNum == FIRST) {
            playerName.setText(GameConfig.getInstance().getProperty("playerOneName"));
        } else if (playerNum == SECOND) {
            playerName.setText(GameConfig.getInstance().getProperty("playerTwoName"));
        }
        playerName.setFont(new Font("Times New Roman", Font.BOLD, 16));
        add(playerName);
	}

    /**
     * Добавляет на форму картинку с танком соотв. игроку цвета.
     */
    private void addPlayerTankPicture() {
		PlayerTankPictureLoader playerTankPicture = new PlayerTankPictureLoader(playerNum);
        playerTankPicture.setBounds(8, 8, 50, 50);
        add(playerTankPicture);
	}

    /**
     * Устанавливает значения по-умолчанию для настроек управления.
     * @param defaultValues значения по-умолчанию.
     */
    public void setDefaultValues(Properties defaultValues) {
        switch (playerNum) {
            case FIRST:
                readConfigDataForPlayer(playerNum, defaultValues);
                break;
            case SECOND:
                GameConfig.getInstance().setProperty("playerTwoUp", defaultValues.getProperty("playerTwoUp"));
                playerUp.setText(KeyEvent.getKeyText(Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoUp"))));

                GameConfig.getInstance().setProperty("playerTwoLeft", defaultValues.getProperty("playerTwoLeft"));
                playerLeft.setText(KeyEvent.getKeyText(Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoLeft"))));

                GameConfig.getInstance().setProperty("playerTwoDown", defaultValues.getProperty("playerTwoDown"));
                playerDown.setText(KeyEvent.getKeyText(Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoDown"))));

                GameConfig.getInstance().setProperty("playerTwoRight", defaultValues.getProperty("playerTwoRight"));
                playerRight.setText(KeyEvent.getKeyText(Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoRight"))));

                GameConfig.getInstance().setProperty("playerTwoFire", defaultValues.getProperty("playerTwoFire"));
                playerFire.setText(KeyEvent.getKeyText(Integer.parseInt(GameConfig.getInstance().getProperty("playerTwoFire"))));
                break;
        }
    }

    /**
     * Считывает сохраненные настройки для конкретного игрока.
     * @param playerNum Номер игрока.
     * @param defaultValues Сохраненные настройки.
     */
    private void readConfigDataForPlayer(final int playerNum, final Properties defaultValues) {
        String playerNumStr = "";
        switch (playerNum) {
            case FIRST:
                playerNumStr = "playerOne";
                break;
            case SECOND:
                playerNumStr = "playerTwo";
                break;
            default:
                break;
        }
        GameConfig.getInstance().setProperty(playerNumStr + "Up", defaultValues.getProperty(playerNumStr + "Up"));
        playerUp.setText(KeyEvent.getKeyText(Integer.parseInt(GameConfig.getInstance().getProperty(playerNumStr + "Up"))));

        GameConfig.getInstance().setProperty(playerNumStr + "Left", defaultValues.getProperty(playerNumStr + "Left"));
        playerLeft.setText(KeyEvent.getKeyText(Integer.parseInt(GameConfig.getInstance().getProperty(playerNumStr + "Left"))));

        GameConfig.getInstance().setProperty(playerNumStr + "Down", defaultValues.getProperty(playerNumStr + "Down"));
        playerDown.setText(KeyEvent.getKeyText(Integer.parseInt(GameConfig.getInstance().getProperty(playerNumStr + "Down"))));

        GameConfig.getInstance().setProperty(playerNumStr + "Right", defaultValues.getProperty(playerNumStr + "Right"));
        playerRight.setText(KeyEvent.getKeyText(Integer.parseInt(GameConfig.getInstance().getProperty(playerNumStr + "Right"))));

        GameConfig.getInstance().setProperty("playerOneFire", defaultValues.getProperty(playerNumStr + "Fire"));
        playerFire.setText(KeyEvent.getKeyText(Integer.parseInt(GameConfig.getInstance().getProperty(playerNumStr + "Fire"))));
    }

    public String getPlayerName() {
        return playerName.getText();
    }

    /**
     * Снимает выделение со всех кнопок.
     */
    public void unselectButtons() {
        playerFire.setSelected(false);
        playerLeft.setSelected(false);
        playerRight.setSelected(false);
        playerUp.setSelected(false);
        playerDown.setSelected(false);
    }
}