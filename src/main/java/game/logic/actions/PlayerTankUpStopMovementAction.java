/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.logic.actions;

import game.objects.tank.AbstractTank;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 *
 * @author Павел
 */
public class PlayerTankUpStopMovementAction extends AbstractAction {

    private AbstractTank playerTank;
    
    public PlayerTankUpStopMovementAction(AbstractTank tank) {
        this.playerTank = tank;
    }

    
    
    @Override
    public void actionPerformed(ActionEvent e) {
//        playerTank.setUpKeyPressed(false);
        playerTank.getMovementTimer().stop();        
    }    
}
