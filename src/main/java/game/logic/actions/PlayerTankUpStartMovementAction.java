package game.logic.actions;

import game.logic.Constants;
import game.objects.tank.AbstractTank;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
//TODO Возможно, этот класс можно удалить совсем
public class PlayerTankUpStartMovementAction extends AbstractAction {

	private AbstractTank playerTank;	
	
	public PlayerTankUpStartMovementAction(AbstractTank playerTank) {
		super();
		this.playerTank = playerTank;
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		if (!(playerTank.getDirection() == Constants.UP)) {
			playerTank.setDirection(Constants.UP);			
			switch (playerTank.getStarsCount()) {
			case 0:
				playerTank.setTankPicture(AbstractTank.G_TANK_UP);
				break;
			case 1:
				playerTank.setTankPicture(AbstractTank.G2_SHELL_UP);
				break;
			case 2:
				playerTank.setTankPicture(AbstractTank.G_DOUBLE_SHELL_UP);
				break;
			case 3:
				playerTank.setTankPicture(AbstractTank.G_QUADRO_UP);
				break;
			case 4:
				playerTank.setTankPicture(AbstractTank.G_QUADRO_TURBO_UP);
				break;
			default:
				break;
			}
		}							
		playerTank.getMovementTimer().start();		
	}
}
