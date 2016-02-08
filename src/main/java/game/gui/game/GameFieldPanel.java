package game.gui.game;

import game.gui.bf.BattleField;
import game.gui.editor.MapDesigner;
import game.gui.editor.StatusBar;
import game.logic.Constants;
import game.logic.EnemyTankRespawner;
import game.logic.GameConfig;
import game.objects.Bonus;
import game.objects.Explosion;
import game.objects.HeadQuarter;
import game.objects.MapObject;
import game.objects.Shell;
import game.objects.tank.player.PlayerTankKeyListener;
import game.objects.tank.AbstractTank;
import game.objects.tank.player.PlayerTank;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JLayeredPane;
import javax.swing.Timer;

public class GameFieldPanel extends JLayeredPane implements KeyListener {

	private AbstractTank playerOneTank;
	private StatusBar statusBar;
	private Component[] panelComponents;
	private boolean button1Down = false;
	private boolean rightMouseButtonPressed = false;
	private boolean gamePaused, gameOver = false;
	private GameMessage gameMessage = null;
	// !! необходимость инкапсуляции этого
	// спорна
	private int enemyTanksOnField = 0;
	private Timer enemyRespawner;
	public BattleField battleField;
	// количество танков, которые еще не вышли
	// на поле
	private int maxTanksCount;
	// количество танков, убитое обоими
	// игроками.
	// Нужно для добавления бонуса жизни после
	// каждого 3-го убитого танка.
	private int totalTanksKilled;
	// коллекция со всеми штабами
	private ArrayList<HeadQuarter> headQuarters;

	// Конструктор добавляет игровое поле на
	// основную форму
	public GameFieldPanel(BattleField bf) {
		super();
		this.battleField = bf;
		setBounds(0, 0, 800, 600);
		setLayout(null);
		setBackground(Color.BLACK);
		setOpaque(true);
		setFocusable(true);
		addKeyListener(this);
		addEnemyTanks();
		add(new Bonus(), new Integer(2));
	}

	// Этот конструктор используется для
	// добавления игрового поля в окно
	// редактора
	public GameFieldPanel(final StatusBar statBar) {
		super();
		setOpaque(true);
		setBounds(0, 0, 800, 600);
		setLayout(null);
		setBackground(Color.BLACK);
		setFocusable(true);
		this.statusBar = statBar;
		addMouseListener(new MouseListener() {
			public void mouseClicked(final MouseEvent e) {
				switch (e.getButton()) {
				case MouseEvent.BUTTON1:
					int x;
					int y;
					x = e.getX() - e.getX() % 8;
					y = e.getY() - e.getY() % 8;
					if (MapDesigner.brickSelected) {
						addMapObject(x, y, 1, Constants.BRICK_SIZE);
					}
					// перед добавлением объектов
					// больше одной клетки, т.е.
					// всех объектов кроме кирпича,
					// нужно убедиться,
					// что они не выходят за границы
					// экрана.
					if (MapDesigner.concreteSelected
							&& (x / 8 != Constants.HORIZONTAL_CELLS_COUNT)
							&& (y / 8 != Constants.VERTICAL_CELLS_COUNT)) {
						addMapObject(x, y, 2, Constants.CONCRETE_SIZE);
					}
					if (MapDesigner.waterSelected
							&& (x / 8 != Constants.HORIZONTAL_CELLS_COUNT)
							&& (y / 8 != Constants.VERTICAL_CELLS_COUNT)) {
						addMapObject(x, y, 3, Constants.WATER_SIZE);
					}
					if (MapDesigner.headQuartersSelected
							&& (x / 8 + 4 <= Constants.HORIZONTAL_CELLS_COUNT)
							&& (y / 8 + 4 <= Constants.VERTICAL_CELLS_COUNT)) {
						addMapObject(x, y, 4, Constants.HQ_SIZE);
					}
					repaint();
					break;
				default:
					break;
				}
			}

			// это нужно, чтобы объекты
			// добавлялись только при зажатой
			// левой клавише мыши
			// TODO переделать как и в MapObject
			public void mousePressed(final MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					button1Down = true;
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					setRightMouseButtonPressed(true);
				}
			}

			// это нужно, чтобы объекты
			// добавлялись только при зажатой
			// левой клавише мыши
			public void mouseReleased(final MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					button1Down = false;
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					setRightMouseButtonPressed(false);
				}
			}

			public void mouseEntered(final MouseEvent e) {
			}

			public void mouseExited(final MouseEvent e) {
			}
		});
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(final MouseEvent e) {
				// рисование объектов с зажатой
				// левой кнопкой мыши
				int xCoord = e.getX() - e.getX() % 8, yCoord = e.getY()
						- e.getY() % 8;
				if (MapDesigner.brickSelected && button1Down) {
					addMapObject(xCoord, yCoord, Constants.BRICK_CODE,
							Constants.BRICK_SIZE);
					// !! repaint() можно вынести за if
					repaint();
				} else if (MapDesigner.concreteSelected && button1Down
						&& (xCoord / 8 != Constants.HORIZONTAL_CELLS_COUNT)
						&& (yCoord / 8 != Constants.VERTICAL_CELLS_COUNT)) {
					addMapObject(xCoord, yCoord, Constants.CONCRETE_CODE,
							Constants.CONCRETE_SIZE);
					repaint();
				} else if (MapDesigner.waterSelected && button1Down
						&& (xCoord / 8 != Constants.HORIZONTAL_CELLS_COUNT)
						&& (yCoord / 8 != Constants.VERTICAL_CELLS_COUNT)) {
					addMapObject(xCoord, yCoord, Constants.WATER_CODE,
							Constants.WATER_SIZE);
					repaint();
				}
			}

			public void mouseMoved(final MouseEvent e) {
				// выводит в панель статуса
				// координаты текущей ячейки
				statBar.setStatusBarText(e.getX() / 8 + ", " + e.getY() / 8);
			}
		});
	}

	@Override
	public final void paintComponent(final Graphics g) {
		super.paintComponent(g); // иначе фон не будет
									// черным
		/*
		 * if (statusBar != null) { g.setColor(Color.CYAN); // рисуем
		 * вертикальные линии for (int i = 7; i < 800; i += 8)
		 * { g.drawLine(i, 0, i, Constants.GAME_FIELD_HEIGHT); } // рисуем
		 * горизонтальные линии for (int i = 7; i < 600; i +=
		 * 8) { g.drawLine(0, i, Constants.GAME_FIELD_WIDTH, i); } }
		 */
	}

	// добавляет объект в режиме конструктора
	public final void addMapObject(final int x, final int y, final int imageNum, final int size) {
		// перед добавлением нового объекта
		// убеждаемся, что он не пересекается с
		// существующими на панели объектами.
		MapObject mapObject;
		if (imageNum == 4) {
			mapObject = new HeadQuarter(HeadQuarter.START_LEVEL_VALUE, x, y, this);
		} else {
			mapObject = new MapObject(x, y, size, imageNum, this);
		}
		// !! перенести область видимости
		panelComponents = getComponents();
		boolean intersection = false;
		Rectangle brickRect = mapObject.getBounds();
		for (Component comp : panelComponents) {
			Rectangle compRect = comp.getBounds();
			if (compRect.intersects(brickRect)) {
				intersection = true;
				break;
			}
		}
		if (!intersection) {
			// !! скорее всего конструктор можно
			// заменить числом.
			add(mapObject, new Integer(1));
			// !! эта строка иногда выводит
			// отрицательные координаты
		}
	}

	public final void addPlayerOneTank(int life) {
		playerOneTank = new PlayerTank(this, true, life);
		playerOneTank.setTankPicture(AbstractTank.G_TANK_UP);
		/*
		 * playerOneTank.setBounds(0, Constants.GAME_FIELD_HEIGHT -
		 * Constants.TANK_SIZE, Constants.TANK_SIZE, Constants.TANK_SIZE);
		 */
		playerOneTank.setBounds(Constants.GAME_FIELD_WIDTH - Constants.TANK_SIZE,
				Constants.GAME_FIELD_HEIGHT - Constants.TANK_SIZE,
				Constants.TANK_SIZE, Constants.TANK_SIZE);
		add(playerOneTank, new Integer(0));
                addKeyListener(new PlayerTankKeyListener(playerOneTank, this));
	}

	// удаляет все компоненты с карты, т.е.
	// очищает поле.
	public final void removeAllComponents() {
		Component[] components = getComponents();
		for (Component component : components) {
			if (component instanceof AbstractTank) {
				AbstractTank tank = (AbstractTank) component;
				// tank.setAlive(false);
				if (tank.isPlayerControlled() && tank.isAlive()) {
					continue;
				}
			}
			remove(component);
		}
		repaint();
	}

	public final void removeHorizontalLineOfBlocks(final int x, final int y, final int posCode) {
		Component[] components = getComponents();
		if (posCode == Shell.CENTER) {
			if (findAndRemoveMapObject(x + 8, y, components,
					Constants.BRICK_CODE)) {
				findAndRemoveMapObject(x + 16, y, components,
						Constants.BRICK_CODE);
			}
			if (findAndRemoveMapObject(x - 8, y, components,
					Constants.BRICK_CODE)) {
				findAndRemoveMapObject(x - 16, y, components,
						Constants.BRICK_CODE);
			}
		} else if (posCode == Shell.V_LEFT) {
			findAndRemoveMapObject(x - 8, y, components, Constants.BRICK_CODE);
			if (findAndRemoveMapObject(x + 8, y, components,
					Constants.BRICK_CODE)) {
				if (findAndRemoveMapObject(x + 16, y, components,
						Constants.BRICK_CODE)) {
					findAndRemoveMapObject(x + 24, y, components,
							Constants.BRICK_CODE);
				}
			}
		} else if (posCode == Shell.V_RIGHT) {
			findAndRemoveMapObject(x + 8, y, components, Constants.BRICK_CODE);
			if (findAndRemoveMapObject(x - 8, y, components,
					Constants.BRICK_CODE)) {
				if (findAndRemoveMapObject(x - 16, y, components,
						Constants.BRICK_CODE)) {
					findAndRemoveMapObject(x - 24, y, components,
							Constants.BRICK_CODE);
				}
			}
		}
	}

	public final void removeVerticalLineOfBlocks(final int x, final int y, final int posCode) {
		Component[] components = getComponents();
		if (posCode == Shell.CENTER) {
			if (findAndRemoveMapObject(x, y + 8, components,
					Constants.BRICK_CODE)) {
				findAndRemoveMapObject(x, y + 16, components,
						Constants.BRICK_CODE);
			}
			if (findAndRemoveMapObject(x, y - 8, components,
					Constants.BRICK_CODE)) {
				findAndRemoveMapObject(x, y - 16, components,
						Constants.BRICK_CODE);
			}
		} else if (posCode == Shell.H_UP) {
			findAndRemoveMapObject(x, y - 8, components, Constants.BRICK_CODE);
			if (findAndRemoveMapObject(x, y + 8, components,
					Constants.BRICK_CODE)) {
				if (findAndRemoveMapObject(x, y + 16, components,
						Constants.BRICK_CODE)) {
					findAndRemoveMapObject(x, y + 24, components,
							Constants.BRICK_CODE);
				}
			}
		} else if (posCode == Shell.H_DOWN) {
			findAndRemoveMapObject(x, y + 8, components, Constants.BRICK_CODE);
			if (findAndRemoveMapObject(x, y - 8, components,
					Constants.BRICK_CODE)) {
				if (findAndRemoveMapObject(x, y - 16, components,
						Constants.BRICK_CODE)) {
					findAndRemoveMapObject(x, y - 24, components,
							Constants.BRICK_CODE);
				}
			}
		}
	}

	public final void removeConcreteHorizontal(final int x, final int y, final int posCode, Rectangle intersection) {
		Component[] components = getComponents();
		if (posCode == Shell.CENTER) {
			findAndRemoveMapObject(x + 16, y, components,
					Constants.CONCRETE_CODE);
			findAndRemoveMapObject(x - 16, y, components,
					Constants.CONCRETE_CODE);
		} else if (posCode == Shell.V_LEFT) {
			if (intersection.getX() == x) {
				findAndRemoveMapObject(x + 16, y, components,
						Constants.CONCRETE_CODE);
				findAndRemoveMapObject(x - 16, y, components,
						Constants.CONCRETE_CODE);
			} else if (intersection.getX() == x + 8) {
				if (findAndRemoveMapObject(x + 16, y, components,
						Constants.CONCRETE_CODE)) {
					findAndRemoveMapObject(x + 32, y, components,
							Constants.CONCRETE_CODE);
				}
			}
		} else if (posCode == Shell.V_RIGHT) {
			if (intersection.getX() == x) {
				if (findAndRemoveMapObject(x - 16, y, components,
						Constants.CONCRETE_CODE)) {
					findAndRemoveMapObject(x - 32, y, components,
							Constants.CONCRETE_CODE);
				}
			} else if (intersection.getX() == x + 8) {
				findAndRemoveMapObject(x + 16, y, components,
						Constants.CONCRETE_CODE);
				findAndRemoveMapObject(x - 16, y, components,
						Constants.CONCRETE_CODE);
			}
		}
	}

	public final void removeConcreteVertical(final int x, final int y, int posCode, Rectangle intersection) {
		Component[] components = getComponents();
		if (posCode == Shell.CENTER) {
			findAndRemoveMapObject(x, y + 16, components, Constants.CONCRETE_CODE);
			findAndRemoveMapObject(x, y - 16, components, Constants.CONCRETE_CODE);
		} else if (posCode == Shell.H_UP) {
			if (intersection.getY() == y) {
				findAndRemoveMapObject(x, y + 16, components, Constants.CONCRETE_CODE);
				findAndRemoveMapObject(x, y - 16, components, Constants.CONCRETE_CODE);
			} else if (intersection.getY() == y + 8) {
				if (findAndRemoveMapObject(x, y + 16, components, Constants.CONCRETE_CODE)) {
					findAndRemoveMapObject(x, y + 32, components, Constants.CONCRETE_CODE);
				}
			}
		} else if (posCode == Shell.H_DOWN) {
			if (intersection.getY() == y) {
				if (findAndRemoveMapObject(x, y - 16, components, Constants.CONCRETE_CODE)) {
					findAndRemoveMapObject(x, y - 32, components, Constants.CONCRETE_CODE);
				}
			} else if (intersection.getY() == y + 8) {
				findAndRemoveMapObject(x, y + 16, components, Constants.CONCRETE_CODE);
				findAndRemoveMapObject(x, y - 16, components, Constants.CONCRETE_CODE);
			}
		}
	}

	private boolean findAndRemoveMapObject(final int x, final int y, final Component[] components, int objectCode) {
		boolean removed = false;
		for (Component component : components) {
			if (!(component instanceof AbstractTank)
					&& !(component instanceof Shell)
					&& !(component instanceof Bonus)) {
				MapObject mapObject = (MapObject) component;
				if ((mapObject.getX() == x)
						&& (mapObject.getY() == y)
						&& (mapObject.getImageNum() == objectCode)) {
					remove(mapObject);
					repaint(mapObject.getBounds());
					removed = true;
					break;
				}
			}
		}
		return removed;
	}

	// метод рисует взрыв на форме
	public final void drawExpolde(final int x,
								  final int y,
								  final int explosionCode,
								  final int explosionSize) {
		//TODO Какая необходимость передавать код взрыва в метод?
		MapObject explosion = new MapObject(x, y, explosionSize, explosionCode,	this);
		new Explosion(this, explosion).start();
	}

	// метод останавливает перемещение всех
	// танков после закрытия формы.
	public final void stopAllTimersOnExit() {
		Component[] components = getComponents();
		for (Component component : components) {
			if (component instanceof AbstractTank) {
				AbstractTank tank = (AbstractTank) component;
				tank.setAlive(false);
				tank.stopAllTimers();
			}
		}
	}

	public void keyTyped(final KeyEvent e) {
	}

	public final void keyPressed(final KeyEvent e) {
		// если гейм-овер, то пауза не должна
		// работать.
		int pauseCode;
		pauseCode = Integer.parseInt(GameConfig.getInstance().getProperty("gamePause"));
		if (e.getKeyCode() == pauseCode && !gameOver) {
			KeyListener[] listeners = getListeners(KeyListener.class);
			if (!gamePaused) {
				setPause();
			} else {
				unSetPause();
			}
			gamePaused = !gamePaused;
		}
		// рестарт после геймовера. !!! Когда
		// будут готовы уровни - переделать!
		int restartCode;
		restartCode = Integer.parseInt(GameConfig.getInstance().getProperty("gameRestart"));
		if (e.getKeyCode() == restartCode && gameOver) {
			restart();
		}
	}

	public void keyReleased(final KeyEvent e) {
	}

	private void setPause() {
		Component[] components = getComponents();
		for (Component component : components) {
			if (component instanceof AbstractTank) {
				AbstractTank tank = (AbstractTank) component;
				if (tank.isPlayerControlled()) {
//					removeKeyListener(tank);
					// !! скорее всего это тут не надо
					tank.stopAllTimers();
				} else {
					tank.stopAllTimers();
				}
			}
			if (component instanceof Shell) {
				Shell shell = (Shell) component;
				shell.timer.stop();
			}
		}
		if (enemyRespawner != null) {
			enemyRespawner.stop();
		}
		gameMessage = new GameMessage(Constants.PAUSE_MESSAGE_CODE);
		gameMessage.setBounds(270, 256, 260, 88);
		add(gameMessage, new Integer(3));
		repaint();
	}

	private void unSetPause() {
		Component[] components = getComponents();
		for (Component component : components) {
			if (component instanceof AbstractTank) {
				AbstractTank tank = (AbstractTank) component;
				if (tank.isPlayerControlled()) {
                                    addKeyListener(new PlayerTankKeyListener(playerOneTank, this));
//					addKeyListener(tank);
				} else {
					tank.startTimer();
				}
			}
			if (component instanceof Shell) {
				Shell shell = (Shell) component;
				shell.timer.start();
			}
		}
		if (enemyRespawner != null) {
			enemyRespawner.start();
		}
		remove(gameMessage);
//		getPlayerOneTank().releaseAllKeys();
		repaint();
	}

	public final void stopGame(int reasonCode) {
		Component[] components = getComponents();
		for (Component component : components) {
			if (component instanceof AbstractTank) {
				AbstractTank tank = (AbstractTank) component;
				if (tank.isPlayerControlled()) {
//					removeKeyListener(tank);
					// !! скорее всего это тут не надо
					tank.stopAllTimers();
				} else {
					tank.stopAllTimers();
				}
			}
			if (component instanceof Shell) {
				Shell shell = (Shell) component;
				shell.timer.stop();
			}
		}
		enemyRespawner.stop();
		gameOver = true;
		gameMessage = new GameMessage(reasonCode);
		gameMessage.setBounds(293, 215, 214, 170);
		add(gameMessage, new Integer(3));
		repaint();
	}

	public final void setRightMouseButtonPressed(final boolean rmbPressed) {
		this.rightMouseButtonPressed = rmbPressed;
	}

	public final void addEnemyTanks() {
		if (enemyRespawner == null) {
			//TODO сделать 1000 константой
			enemyRespawner = new Timer(1000, new EnemyTankRespawner(this));
		}
		enemyRespawner.start();
	}

	public final int getEnemyTanksOnField() {
		return enemyTanksOnField;
	}

	public final void increaseEnemyTanksOnField() {
		enemyTanksOnField++;
		maxTanksCount--;
	}

	public final void decreaseEnemyTanksOnField() {
		if (enemyTanksOnField > 0) {
			enemyTanksOnField--;
		}
	}

	public final int getMaxTanksCount() {
		return maxTanksCount;
	}

	public final void setMaxTanksCount(int x) {
		maxTanksCount = x;
	}

	public final void increaseMaxTanksCount() {
		maxTanksCount++;
	}

	public final void increaseTotalEnemyTanksKilled() {
		totalTanksKilled++;
	}

	public final void addBonusIfNeeded() {
		if (totalTanksKilled == 3) {
			add(new Bonus(), new Integer(2));
			totalTanksKilled = 0;
		}
	}

	public boolean mapAvaliable(int mapNum) {
		boolean avaliable = false;
		File mapsDir = new File("maps");
		if (mapsDir.exists() && mapsDir.isDirectory()) {
			File[] maps = mapsDir.listFiles();
			for (File mapFile : maps) {
				StringBuilder fileName = new StringBuilder();
				for (int i = 0; i < mapFile.getName().length(); i++) {
					char c = mapFile.getName().charAt(i);
					if (c != '.') {
						fileName.append(c);
					} else {
						break;
					}
				}
				if (mapNum == Integer.parseInt(fileName.toString())) {
					avaliable = true;
					break;
				}
			}
		}
		return avaliable;
	}

	public void restart() {
		// removeAllComponents();
		battleField.setStaticticsToZero();
		battleField.setTotalStatiscticsToZero();
		enemyTanksOnField = 0;
		// загружаем первую карту
		GameConfig.getInstance().setCurrentLevel(1);
		battleField.setLevelValue("1");

		// loadMap(GameConfig.getInstance().getCurrentMapFile());
		MapLoader mapLoader = new MapLoader(this, GameConfig.getInstance()
				.getCurrentMapFile());
		mapLoader.start();
		try {
			mapLoader.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// обнуляем значение жизни у танка и
		// число в статистике
		playerOneTank.setLife(1);
		battleField.setPlayerOneLifeValue(1);

		// сбрасываем счетчик танков, не
		// вышедших на поле
		String count = GameConfig.getInstance().getProperty("tanksCount");
		maxTanksCount = Integer.parseInt(count);
		/*
		 * if (getPlayerOneTank() == null) { addPlayerOneTank(1); } else { //
		 * убрать этот быдлокод!
		 * getPlayerOneTank().addKeyListener(playerOneTank); }
		 */
		if (playerOneTank != null) {
                    addKeyListener(new PlayerTankKeyListener(playerOneTank, this));
//			addKeyListener(playerOneTank);
			playerOneTank.setDirection(Constants.UP);
			playerOneTank.setTankPicture(AbstractTank.G_TANK_UP);
			// вынести это в отдельный метод

			/*
			 * switch(playerOneTank.getStarsCount()) { case 1:
			 * playerOneTank.setTankPicture(Tank.G2_SHELL_UP); break; case 2:
			 * playerOneTank.setTankPicture(Tank.G_DOUBLE_SHELL_UP); break; case
			 * 3: playerOneTank.setTankPicture(Tank.G_QUADRO_UP); break; case 4:
			 * playerOneTank.setTankPicture(Tank.G_QUADRO_TURBO_UP); break; }
			 */
			repaint(playerOneTank.getBounds());
		}
		// addEnemyTanks();
		gameOver = false;
		// add(new Bonus(), new Integer(2));
		// !!! необходимость этой строки тут не
		// ясна
                addKeyListener(new PlayerTankKeyListener(playerOneTank, this));
//		 addKeyListener(playerOneTank);
	}

	public void freeRespawningZones() {
		Component[] components = getComponents();
		// !! зоны респавна неплохо бы вынести
		// куда-то отдельно, в файле EnemyTankRespawner
		// тоже

		Rectangle enemyZone1 = new Rectangle(0, 0, Constants.TANK_SIZE,
				Constants.TANK_SIZE);
		Rectangle enemyZone2 = new Rectangle(Constants.GAME_FIELD_WIDTH / 2
				- Constants.TANK_SIZE / 2 + 4, 0, Constants.TANK_SIZE,
				Constants.TANK_SIZE);
		Rectangle enemyZone3 = new Rectangle(Constants.GAME_FIELD_WIDTH
				- Constants.TANK_SIZE, 0, Constants.TANK_SIZE, Constants.TANK_SIZE);
		Rectangle pleerZone1 = new Rectangle(0, Constants.GAME_FIELD_HEIGHT
				- Constants.TANK_SIZE, Constants.TANK_SIZE, Constants.TANK_SIZE);
		Rectangle pleerZone2 = new Rectangle(Constants.GAME_FIELD_WIDTH
				- Constants.TANK_SIZE, Constants.GAME_FIELD_HEIGHT
				- Constants.TANK_SIZE, Constants.TANK_SIZE, Constants.TANK_SIZE);
		for (Component component : components) {
			if (component.getBounds().intersects(enemyZone1)
					|| component.getBounds().intersects(enemyZone2)
					|| component.getBounds().intersects(enemyZone3)
					|| component.getBounds().intersects(pleerZone1)
					|| component.getBounds().intersects(pleerZone2)) {
				remove(component);
			}
		}
		repaint(pleerZone1.getBounds());
		repaint(pleerZone2.getBounds());
		repaint(enemyZone1.getBounds());
		repaint(enemyZone2.getBounds());
		repaint(enemyZone3.getBounds());
	}

	public AbstractTank getPlayerOneTank() {
		return playerOneTank;
	}

	public void setPlayerOneTank(AbstractTank tank) {
		playerOneTank = tank;
	}

	public void stopEnemyRespawner() {
		if (enemyRespawner != null) {
			enemyRespawner.stop();
		}
	}

	public void destroyEnemyTank(AbstractTank tank) {
		tank.stopAllTimers();
		battleField.increasePlayerOneKilledCount();
		battleField.increaseTotalKilledCountForPlayerOne();
		increaseTotalEnemyTanksKilled();
		addBonusIfNeeded();
		if (tank.getLife() > 1) {
			increaseMaxTanksCount();
		} else {
			battleField.increaseTanksKilled();
			if (battleField.isAllTanksKilled()) {
				if (tank.getShell() != null) {
					tank.getShell().stopTimer();
					remove(tank.getShell());
					repaint(tank.getShell().getBounds());
				}
				if (tank.getShell2() != null) {
					tank.getShell2().stopTimer();
					remove(tank.getShell2());
					repaint(tank.getShell2().getBounds());
				}
				GameConfig.getInstance().increaseCurrentLevelNum();
				if (mapAvaliable(GameConfig.getInstance().getCurrentLevel())) {
					getPlayerOneTank().setBounds(0,
							Constants.GAME_FIELD_HEIGHT - Constants.TANK_SIZE,
							Constants.TANK_SIZE, Constants.TANK_SIZE);
					switch (getPlayerOneTank().getStarsCount()) {
					case 0:
						getPlayerOneTank().setTankPicture(AbstractTank.G_TANK_UP);
						break;
					case 1:
						getPlayerOneTank().setTankPicture(AbstractTank.G2_SHELL_UP);
						break;
					case 2:
						getPlayerOneTank().setTankPicture(AbstractTank.G_DOUBLE_SHELL_UP);
						break;
					case 3:
						getPlayerOneTank().setTankPicture(AbstractTank.G_QUADRO_UP);
						break;
					case 4:
						getPlayerOneTank().setTankPicture(AbstractTank.G_QUADRO_TURBO_UP);
						break;
					default:
						break;
					}
					battleField.setLevelValue(String.valueOf(GameConfig.getInstance().getCurrentLevel()));
					stopEnemyRespawner();
					new MapLoader(this, GameConfig.getInstance().getCurrentMapFile()).start();
				} else {
					stopGame(Constants.YOU_WIN_MESSAGE_CODE);
				}
			}
			tank.setAlive(false);
			remove(tank);
			drawExpolde(tank.getX(), tank.getY(), Constants.BIG_EXPLOSION_CODE,
					Constants.TANK_SIZE);
			repaint(tank.getX(), tank.getY(), Constants.TANK_SIZE,
					Constants.TANK_SIZE);
			tank = null;
		}
	}
}