package game.listeners;

import game.gui.GameFieldPanel;
import game.logic.Constants;
import game.logic.GameConfig;
import game.objects.MapObject;
import game.objects.Shell;
import game.objects.tank.AbstractTank;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerTankFireHandler extends KeyAdapter {

	private AbstractTank tank;
	private GameFieldPanel gameFieldPanel;	
	private Shell shell, shell2;
	
	public PlayerTankFireHandler(AbstractTank tank, GameFieldPanel gameFieldPanel) {
		super();
		this.tank = tank;
		this.gameFieldPanel = gameFieldPanel;
	}
    
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == Integer.parseInt(GameConfig
				.getInstance().getProperty("playerOneFire"))) {
			if (tank.getCurrentBullets() < tank.getMaxBullets()) {				
				gameFieldPanel.battleField.increaseShotsCountForPlayerOne();
				gameFieldPanel.battleField.increaseTotalShotsCountForPlayerOne();
				boolean closeShot = false;
				Rectangle shell1Bounds = null, shell2Bounds = null;
				Component[] components = gameFieldPanel.getComponents();
				if (tank.getDirection() == Constants.UP) {
					// РїСЂРѕРІРµСЂРєР° РІС‹СЃС‚СЂРµР»Р° РІ РєРёСЂРїРёС‡ РІ
					// СѓРїРѕСЂ
					for (Component component : components) {
						if (component instanceof MapObject) {
							MapObject mapObject = (MapObject) component;
							if (mapObject.getImageNum() == 1
									&& mapObject.getX() == tank.getX()
											+ Constants.tankSize / 2
											- Constants.brickSize / 2
									&& mapObject.getY() == tank.getY()
											- Constants.brickSize) {
								gameFieldPanel.remove(mapObject);
								gameFieldPanel
										.removeHorizontalLineOfBlocks(
												mapObject.getX(),
												mapObject.getY(),
												Shell.CENTER);
								gameFieldPanel.repaint(mapObject.getX());
								gameFieldPanel.drawExpolde(tank.getX()
										+ Constants.tankSize / 2
										- Constants.explosionSize / 2,
										tank.getY() - Constants.brickSize,
										Constants.SMALL_EXPLOSION_CODE,
										Constants.explosionSize);
								gameFieldPanel.repaint(tank.getX()
										+ Constants.tankSize / 2
										- Constants.explosionSize / 2,
										tank.getY() - Constants.brickSize,
										Constants.explosionSize,
										Constants.explosionSize);
								closeShot = true;
								break;
							}
						} else if (component instanceof AbstractTank) {
							AbstractTank tank = (AbstractTank) component;
							if (!tank.isPlayerControlled()) {									
								if (!tank.isDoubleShot()) {
									shell1Bounds = new Rectangle(tank.getX()
											+ Constants.tankSize / 2
											- Constants.shellSize / 2,
											tank.getY() - Constants.shellSize,
											Constants.shellSize,
											Constants.shellSize);
									if (shell1Bounds.intersects(tank
											.getBounds())) {
										/*gameFieldPanel.battleField.increaseShotsCountForPlayerOne();
										gameFieldPanel.battleField.increaseTotalShotsCountForPlayerOne();*/
										gameFieldPanel.destroyEnemyTank(tank);
										closeShot = true;
									}
								} else {
									shell1Bounds = new Rectangle(tank.getX()
											+ Constants.tankSize / 2
											- Constants.shellSize / 2
											- Constants.shellSize, tank.getY()
											- Constants.shellSize,
											Constants.shellSize,
											Constants.shellSize);
									shell2Bounds = new Rectangle(tank.getX()
											+ Constants.tankSize / 2
											+ Constants.shellSize / 2,
											tank.getY() - Constants.shellSize,
											Constants.shellSize,
											Constants.shellSize);
									if (shell1Bounds.intersects(tank
											.getBounds())
											|| shell2Bounds.intersects(tank
													.getBounds())) {
										/*gameFieldPanel.battleField
												.increaseShotsCountForPlayerOne();
										gameFieldPanel.battleField
												.increaseTotalShotsCountForPlayerOne();*/
										gameFieldPanel
												.destroyEnemyTank(tank);
										closeShot = true;
									}
								}
							}
						}
					}
					if (!closeShot) {
						if (!tank.isDoubleShot()) {
							shell1Bounds = new Rectangle(tank.getX()
									+ Constants.tankSize / 2
									- Constants.shellSize / 2, tank.getY()
									- Constants.shellSize,
									Constants.shellSize,
									Constants.shellSize);
						} else {
							shell1Bounds = new Rectangle(tank.getX()
									+ Constants.tankSize / 2
									- Constants.shellSize / 2
									- Constants.shellSize, tank.getY()
									- Constants.shellSize,
									Constants.shellSize,
									Constants.shellSize);
							shell2Bounds = new Rectangle(tank.getX()
									+ Constants.tankSize / 2
									+ Constants.shellSize / 2, tank.getY()
									- Constants.shellSize,
									Constants.shellSize,
									Constants.shellSize);
						}
					}
				} else if (tank.getDirection() == Constants.DOWN) {
					for (Component component : components) {
						if (component instanceof MapObject) {
							MapObject mapObject = (MapObject) component;
							if (mapObject.getImageNum() == 1
									&& mapObject.getX() == tank.getX()
											+ Constants.tankSize / 2
											- Constants.brickSize / 2
									&& mapObject.getY() == tank.getY()
											+ Constants.tankSize) {
								gameFieldPanel.remove(mapObject);
								gameFieldPanel
										.removeHorizontalLineOfBlocks(
												mapObject.getX(),
												mapObject.getY(),
												Shell.CENTER);
								gameFieldPanel.repaint(mapObject.getX());
								gameFieldPanel.drawExpolde(tank.getX()
										+ Constants.tankSize / 2
										- Constants.explosionSize / 2,
										tank.getY() + Constants.tankSize,
										Constants.SMALL_EXPLOSION_CODE,
										Constants.explosionSize);
								gameFieldPanel.repaint(tank.getX()
										+ Constants.tankSize / 2
										- Constants.explosionSize / 2,
										tank.getY() + Constants.tankSize,
										Constants.explosionSize,
										Constants.explosionSize);
								closeShot = true;
								break;
							}
						} else if (component instanceof AbstractTank) {
							AbstractTank tank = (AbstractTank) component;
							if (!tank.isPlayerControlled()) {
								if (!tank.isDoubleShot()) {
									shell1Bounds = new Rectangle(tank.getX()
											+ Constants.tankSize / 2
											- Constants.shellSize / 2,
											tank.getY() + Constants.tankSize,
											Constants.shellSize,
											Constants.shellSize);
									if (shell1Bounds.intersects(tank
											.getBounds())) {
										/*gameFieldPanel.battleField
												.increaseShotsCountForPlayerOne();
										gameFieldPanel.battleField
												.increaseTotalShotsCountForPlayerOne();*/
										gameFieldPanel
												.destroyEnemyTank(tank);
										closeShot = true;
									}
								} else {
									shell1Bounds = new Rectangle(tank.getX()
											+ Constants.tankSize / 2
											- Constants.shellSize / 2
											- Constants.shellSize, tank.getY()
											+ Constants.tankSize,
											Constants.shellSize,
											Constants.shellSize);
									shell2Bounds = new Rectangle(tank.getX()
											+ Constants.tankSize / 2
											+ Constants.shellSize / 2,
											tank.getY() + Constants.tankSize,
											Constants.shellSize,
											Constants.shellSize);
									if (shell1Bounds.intersects(tank
											.getBounds())
											|| shell2Bounds.intersects(tank
													.getBounds())) {
										/*gameFieldPanel.battleField
												.increaseShotsCountForPlayerOne();
										gameFieldPanel.battleField
												.increaseTotalShotsCountForPlayerOne();*/
										gameFieldPanel
												.destroyEnemyTank(tank);
										closeShot = true;
									}
								}
							}
						}
					}
					if (!closeShot) {
						if (!tank.isDoubleShot()) {
							shell1Bounds = new Rectangle(tank.getX()
									+ Constants.tankSize / 2
									- Constants.shellSize / 2, tank.getY()
									+ Constants.tankSize,
									Constants.shellSize,
									Constants.shellSize);
						} else {
							shell1Bounds = new Rectangle(tank.getX()
									+ Constants.tankSize / 2
									- Constants.shellSize / 2
									- Constants.shellSize, tank.getY()
									+ Constants.tankSize,
									Constants.shellSize,
									Constants.shellSize);
							shell2Bounds = new Rectangle(tank.getX()
									+ Constants.tankSize / 2
									+ Constants.shellSize / 2, tank.getY()
									+ Constants.tankSize,
									Constants.shellSize,
									Constants.shellSize);
						}
					}
				} else if (tank.getDirection() == Constants.LEFT) {
					for (Component component : components) {
						if (component instanceof MapObject) {
							MapObject mapObject = (MapObject) component;
							if (mapObject.getImageNum() == 1
									&& mapObject.getX() == tank.getX()
											- Constants.brickSize
									&& mapObject.getY() == tank.getY()
											+ Constants.tankSize / 2
											- Constants.brickSize / 2) {
								gameFieldPanel.remove(mapObject);
								// !! С‚СѓС‚ С‚РѕС‡РЅРѕ С†РµРЅС‚СЂ?
								gameFieldPanel.removeVerticalLineOfBlocks(
										mapObject.getX(), mapObject.getY(),
										Shell.CENTER);
								gameFieldPanel.repaint(mapObject
										.getBounds());
								gameFieldPanel.drawExpolde(tank.getX()
										- Constants.brickSize, tank.getY()
										+ Constants.tankSize / 2
										- Constants.explosionSize / 2,
										Constants.SMALL_EXPLOSION_CODE,
										Constants.explosionSize);
								gameFieldPanel.repaint(tank.getX()
										- Constants.brickSize, tank.getY()
										+ Constants.tankSize / 2
										- Constants.explosionSize / 2,
										Constants.explosionSize,
										Constants.explosionSize);
								closeShot = true;
								break;
							}
						} else if (component instanceof AbstractTank) {
							AbstractTank tank = (AbstractTank) component;
							if (!tank.isPlayerControlled()) {
								if (!tank.isDoubleShot()) {
									shell1Bounds = new Rectangle(tank.getX()
											- Constants.shellSize, tank.getY()
											+ Constants.tankSize / 2
											- Constants.shellSize / 2,
											Constants.shellSize,
											Constants.shellSize);
									if (shell1Bounds.intersects(tank
											.getBounds())) {
										/*gameFieldPanel.battleField
												.increaseShotsCountForPlayerOne();
										gameFieldPanel.battleField
												.increaseTotalShotsCountForPlayerOne();*/
										gameFieldPanel
												.destroyEnemyTank(tank);
										closeShot = true;
									}
								} else {
									shell1Bounds = new Rectangle(tank.getX()
											- Constants.shellSize, tank.getY()
											+ Constants.tankSize / 2
											- Constants.shellSize / 2
											- Constants.shellSize,
											Constants.shellSize,
											Constants.shellSize);
									shell2Bounds = new Rectangle(tank.getX()
											- Constants.shellSize, tank.getY()
											+ Constants.tankSize / 2
											+ Constants.shellSize / 2,
											Constants.shellSize,
											Constants.shellSize);
									if (shell1Bounds.intersects(tank
											.getBounds())
											|| shell2Bounds.intersects(tank
													.getBounds())) {										
										gameFieldPanel
												.destroyEnemyTank(tank);
										closeShot = true;
									}
								}
							}
						}
					}
					if (!closeShot) {
						if (!tank.isDoubleShot()) {
							shell1Bounds = new Rectangle(tank.getX()
									- Constants.shellSize, tank.getY()
									+ Constants.tankSize / 2
									- Constants.shellSize / 2,
									Constants.shellSize,
									Constants.shellSize);
						} else {
							shell1Bounds = new Rectangle(tank.getX()
									- Constants.shellSize, tank.getY()
									+ Constants.tankSize / 2
									- Constants.shellSize / 2
									- Constants.shellSize,
									Constants.shellSize,
									Constants.shellSize);
							shell2Bounds = new Rectangle(tank.getX()
									- Constants.shellSize, tank.getY()
									+ Constants.tankSize / 2
									+ Constants.shellSize / 2,
									Constants.shellSize,
									Constants.shellSize);
						}
					}
				} else if (tank.getDirection() == Constants.RIGHT) {
					for (Component component : components) {
						if (component instanceof MapObject) {
							MapObject mapObject = (MapObject) component;
							if (mapObject.getImageNum() == 1
									&& mapObject.getX() == tank.getX()
											+ Constants.tankSize
									&& mapObject.getY() == tank.getY()
											+ Constants.tankSize / 2
											- Constants.brickSize / 2) {
								gameFieldPanel.remove(mapObject);
								gameFieldPanel.removeVerticalLineOfBlocks(
										mapObject.getX(), mapObject.getY(),
										Shell.CENTER);
								gameFieldPanel.repaint(mapObject
										.getBounds());
								gameFieldPanel.drawExpolde(tank.getX()
										+ Constants.tankSize, tank.getY()
										+ Constants.tankSize / 2
										- Constants.explosionSize / 2,
										Constants.SMALL_EXPLOSION_CODE,
										Constants.explosionSize);
								gameFieldPanel.repaint(tank.getX()
										+ Constants.tankSize, tank.getY()
										+ Constants.tankSize / 2
										- Constants.explosionSize / 2,
										Constants.explosionSize,
										Constants.explosionSize);
								closeShot = true;
								break;
							} // Проверяем столкновение пули с танком при
								// выстреле в упор.
						} else if (component instanceof AbstractTank) {
							AbstractTank tank = (AbstractTank) component;
							if (!tank.isPlayerControlled()) {
								if (!tank.isDoubleShot()) {
									shell1Bounds = new Rectangle(tank.getX()
											+ Constants.tankSize, tank.getY()
											+ Constants.tankSize / 2
											- Constants.shellSize / 2,
											Constants.shellSize,
											Constants.shellSize);
									if (shell1Bounds.intersects(tank.getBounds())) {
										
										gameFieldPanel
												.destroyEnemyTank(tank);
										closeShot = true;
									}
								} else {
									shell1Bounds = new Rectangle(tank.getX()
											+ Constants.tankSize, tank.getY()
											+ Constants.shellSize,
											Constants.shellSize,
											Constants.shellSize);
									shell2Bounds = new Rectangle(tank.getX()
											+ Constants.tankSize, tank.getY()
											+ Constants.tankSize
											- Constants.shellSize * 2,
											Constants.shellSize,
											Constants.shellSize);
									if (shell1Bounds.intersects(tank.getBounds()) 
											|| shell2Bounds.intersects(tank.getBounds())) {										
										gameFieldPanel.destroyEnemyTank(tank);
										closeShot = true;
									}
								}
							}
						}
					}
					if (!closeShot) {
						if (!tank.isDoubleShot()) {
							shell1Bounds = new Rectangle(tank.getX()
									+ Constants.tankSize, tank.getY()
									+ Constants.tankSize / 2
									- Constants.shellSize / 2,
									Constants.shellSize,
									Constants.shellSize);
						} else {
							shell1Bounds = new Rectangle(tank.getX()
									+ Constants.tankSize, tank.getY()
									+ Constants.shellSize,
									Constants.shellSize,
									Constants.shellSize);
							shell2Bounds = new Rectangle(tank.getX()
									+ Constants.tankSize, tank.getY()
									+ Constants.tankSize
									- Constants.shellSize * 2,
									Constants.shellSize,
									Constants.shellSize);
						}
					}
				}
				if (!closeShot) {
					shell = new Shell(gameFieldPanel, tank.getDirection(), tank, Shell.FIRST);
					shell.setBounds(shell1Bounds);
					if (tank.isDisplayable()) {
						shell2 = new Shell(gameFieldPanel, tank.getDirection(), tank,
								Shell.SECOND);
						shell2.setBounds(shell2Bounds);
						switch (tank.getDirection()) {
						case Constants.UP:
							shell.setPositionCode(Shell.V_LEFT);
							shell2.setPositionCode(Shell.V_RIGHT);
							break;
						case Constants.DOWN:
							shell.setPositionCode(Shell.V_LEFT);
							shell2.setPositionCode(Shell.V_RIGHT);
							break;
						case Constants.LEFT:
							shell.setPositionCode(Shell.H_UP);
							shell2.setPositionCode(Shell.H_DOWN);
							break;
						case Constants.RIGHT:
							shell.setPositionCode(Shell.H_UP);
							shell2.setPositionCode(Shell.H_DOWN);
							break;
						default:
							break;
						}
					} else {
						shell.setPositionCode(Shell.CENTER);
					}
					if (tank.getStarsCount() >= 3) {
						shell.setPiercing(true);
						if (tank.isDoubleShot()) {
							shell2.setPiercing(true);
						}
					}
					gameFieldPanel.add(shell, new Integer(2));
					gameFieldPanel.repaint(shell.getBounds());
					if (tank.isDoubleShot()) {
						gameFieldPanel.add(shell2, new Integer(2));
						gameFieldPanel.repaint(shell2.getBounds());
					}
					int currentBullets = tank.getCurrentBullets();
					currentBullets++;
					tank.setCurrentBullets(currentBullets); 								
				}				
			}
		}
	}	
}
