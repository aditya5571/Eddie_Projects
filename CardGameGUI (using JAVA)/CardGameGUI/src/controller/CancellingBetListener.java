package controller;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameFrame;
import view.model.GameEngineImplGUI;

public class CancellingBetListener extends AbstractGameFrameActionListener {

	public CancellingBetListener(GameFrame frame, GameEngineImplGUI gameEngineGUI, Player player, GameEngine gameEngine) {
		super(frame, gameEngineGUI, player, gameEngine);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		player = gameEngineGUI.getCurrentPlayer();
		
		int userChoice = JOptionPane.showConfirmDialog(frame, String.format("Original bet amount = $%d\n"
				+ "Are you sure you want to cancel the current bet for %s?",
				player.getBet(), player.getPlayerName()), "Cancel Bet", JOptionPane.YES_NO_OPTION);
		
		Boolean cancelled;
		
		if (userChoice == JOptionPane.YES_OPTION) {
			// update player's bet in model.interfaces.GameEngine;
			player.resetBet();
			
			// update player's bet status in view.model.GameEngineImplGUI;
			gameEngineGUI.setCurrentPlayerSelection(player.getPlayerName());
			
			// remove bet from the summary panel
			frame.getSummaryPanel().removeBetInfo(player);
			
			cancelled = true;
			frame.getStatusBar().notifyBetCancelled(cancelled);
		}
		else {
			cancelled = false;
			frame.getStatusBar().notifyBetCancelled(cancelled);
		}

		// check what buttons need enabling or disabling after betting activity
		if (gameEngineGUI.hasBet()) {
			frame.enableBetCancellation();
			frame.enableDealing();
		} else {
			frame.disableBetCancellation();
			frame.disableDealing();
			
			frame.enableBetting();
		}
	}

}
