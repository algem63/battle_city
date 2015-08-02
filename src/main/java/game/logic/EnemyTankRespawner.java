package game.logic;

import game.gui.GameFieldPanel;
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

    private GameFieldPanel gameFieldPanel;
    private Random posCreator;
    private int onField;

    public EnemyTankRespawner(GameFieldPanel gameFieldPanel) {
        this.gameFieldPanel = gameFieldPanel;
        posCreator = new Random(System.currentTimeMillis());
        onField = Integer.parseInt(GameConfig.getInstance().getProperty("onField"));
    }

    public void actionPerformed(ActionEvent e) {        
        if (gameFieldPanel.getEnemyTanksOnField() < onField
                && gameFieldPanel.getMaxTanksCount() > 0) {
            Rectangle position = new Rectangle();            
            int pos = posCreator.nextInt(3); 
//            int pos = 0;
            switch (pos) {
                case 0:
                    position.setBounds(0,
                                       0,
                                       Constants.tankSize,
                                       Constants.tankSize);
                    break;
                case 1:
                    position.setBounds(Constants.gameFieldWidth/2 - Constants.tankSize/2,
                                       0,
                                       Constants.tankSize,
                                       Constants.tankSize);
                    break;
                case 2:
                    position.setBounds(Constants.gameFieldWidth - Constants.tankSize,
                                       0,
                                       Constants.tankSize,
                                       Constants.tankSize);
                    break;
            }                                  
            if (checkFreeSpace(position)) {
//                Tank enemyTank = new Tank(gameFieldPanel, false, 1);
                EnemyTank enemyTank = new EnemyTank(gameFieldPanel, false, 1);
                enemyTank.setDirection(Constants.DOWN);
                enemyTank.setTankPicture(AbstractTank.EN_TANK_DOWN);
                enemyTank.setBounds(position);
                gameFieldPanel.add(enemyTank, new Integer(0));
                gameFieldPanel.increaseEnemyTanksOnField();              
            }
        }
    }

    private boolean checkFreeSpace(Rectangle rectangle) {
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
