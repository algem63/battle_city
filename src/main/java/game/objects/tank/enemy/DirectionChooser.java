/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.objects.tank.enemy;

import game.logic.Constants;
import game.objects.tank.AbstractTank;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 *
 * @author Павел
 */
public class DirectionChooser implements ActionListener {

    private EnemyTank enemyTank;

    public DirectionChooser(EnemyTank enemyTank) {
        this.enemyTank = enemyTank;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Random random = new Random();
        int directionCode = random.nextInt(4);
        switch (directionCode) {
            case 0:
                rotateTankUp();
                break;
            case 1:
                rotateTankDown();
                break;
            case 2:
                rotateTankLeft();
                break;
            case 3:
                rotateTankRight();
                break;
        }
        enemyTank.getMovementTimer().start();
    }

    private void rotateTankUp() {
        enemyTank.setDirection(Constants.UP);
        switch (enemyTank.getStarsCount()) {
            case 0:
                enemyTank.setTankPicture(AbstractTank.EN_TANK_UP);
                break;
            case 1:
                enemyTank.setTankPicture(AbstractTank.EN_2SHELL_UP);
                break;
            case 2:
                enemyTank.setTankPicture(AbstractTank.EN_QUADRO_UP);
                break;
            default:
                break;
        }
    }

    private void rotateTankDown() {
        enemyTank.setDirection(Constants.DOWN);
        switch (enemyTank.getStarsCount()) {
            case 0:
                enemyTank.setTankPicture(AbstractTank.EN_TANK_DOWN);
                break;
            case 1:
                enemyTank.setTankPicture(AbstractTank.EN_2SHELL_DOWN);
                break;
            case 2:
                enemyTank.setTankPicture(AbstractTank.EN_QUADRO_DOWN);
                break;
            default:
                break;
        }
    }

    private void rotateTankLeft() {
        enemyTank.setDirection(Constants.LEFT);
        switch (enemyTank.getStarsCount()) {
            case 0:
                enemyTank.setTankPicture(AbstractTank.EN_TANK_LEFT);
                break;
            case 1:
                enemyTank.setTankPicture(AbstractTank.EN_2SHELL_LEFT);
                break;
            case 2:
                enemyTank.setTankPicture(AbstractTank.EN_QUADRO_LEFT);
                break;
            default:
                break;
        }
    }

    private void rotateTankRight() {
        enemyTank.setDirection(Constants.RIGHT);
        switch (enemyTank.getStarsCount()) {
            case 0:
                enemyTank.setTankPicture(AbstractTank.EN_TANK_RIGHT);
                break;
            case 1:
                enemyTank.setTankPicture(AbstractTank.EN_2SHELL_RIGHT);
                break;
            case 2:
                enemyTank.setTankPicture(AbstractTank.EN_QUADRO_RIGHT);
                break;
            default:
                break;
        }
    }
}
