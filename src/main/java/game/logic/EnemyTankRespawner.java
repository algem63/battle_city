package game.logic;

import game.gui.game.GameFieldPanel;
import game.objects.tank.AbstractTank;
import game.objects.tank.enemy.EnemyTank;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: melnikovp
 * Date: 12.02.13
 * Time: 12:25
 * To change this template use File | Settings | File Templates.
 */
public class EnemyTankRespawner implements ActionListener {

    //TODO выпилить это отсюда
    private GameFieldPanel gameFieldPanel;

    private final int LEFT_POS = 0;
    private final int CENTER_POS = 1;
    private final int RIGHT_POS = 2;

    /**
     * Выбирает случайным образом позицию для добавления танка противника: слева, справа или посередине
     */
    private Random posCreator;

    /**
     * Количество танков противника на поле
     */
    private int onField;

    public EnemyTankRespawner(GameFieldPanel gameFieldPanel) {
        this.gameFieldPanel = gameFieldPanel;
        posCreator = new Random(System.currentTimeMillis());
        onField = Integer.parseInt(GameConfig.getInstance().getProperty("onField"));
    }

    public void actionPerformed(ActionEvent e) {        
        if (gameFieldPanel.getEnemyTanksOnField() < onField && gameFieldPanel.getMaxTanksCount() > 0) {
            addEnemyTank();
        }
    }

    /**
     Добавляет на игровое поле танк противника
     */
    private void addEnemyTank() {
        Rectangle position = new Rectangle();
        final int positionCode = posCreator.nextInt(3);
        final int xCoord;
        switch (positionCode) {
            case LEFT_POS:
                position.setBounds(0, 0, Constants.TANK_SIZE, Constants.TANK_SIZE);
                break;
            case CENTER_POS:
                xCoord = Constants.GAME_FIELD_WIDTH / 2 - Constants.TANK_SIZE / 2;
                position.setBounds(xCoord, 0, Constants.TANK_SIZE, Constants.TANK_SIZE);
                break;
            case RIGHT_POS:
                xCoord = Constants.GAME_FIELD_WIDTH - Constants.TANK_SIZE;
                position.setBounds(xCoord, 0, Constants.TANK_SIZE, Constants.TANK_SIZE);
                break;
        }
        if (checkFreeSpace(position)) {
            EnemyTank enemyTank = new EnemyTank(gameFieldPanel, false, 1);
            enemyTank.setDirection(Constants.DOWN);
            enemyTank.setTankPicture(AbstractTank.EN_TANK_DOWN);
            enemyTank.setBounds(position);
            //TODO зачем тут ноль?
            gameFieldPanel.add(enemyTank, new Integer(0));
            gameFieldPanel.increaseEnemyTanksOnField();
        }
    }

    /**
     * Проверяет, не занята ли позиция для добавления танка посторонними объектами
     * @param rectangle Позиция для добавления танка
     * @return
     */
    private boolean checkFreeSpace(final Rectangle rectangle) {
        boolean free = true;
        Component[] components = gameFieldPanel.getComponents();
        for (Component component: components) {
            if (component.getBounds().intersects(rectangle)) {
                free = false;
                break;
            }
        }
        return free;       
    }
}
