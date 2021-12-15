package controller;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameFrame;
import view.model.GameEngineImplGUI;

public class RemovePlayerListener extends AbstractGameFrameActionListener {

	public RemovePlayerListener(GameFrame frame, GameEngineImplGUI gameEngineGUI, Player player, GameEngine gameEngine) {
		super(frame, gameEngineGUI, player, gameEngine);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		player = gameEngineGUI.getCurrentPlayer();
		
		int userChoice = JOptionPane.showConfirmDialog(frame,
				String.format("Are you sure you want to remove %s?", player.getPlayerName()),
				"Remove Player", JOptionPane.YES_NO_OPTION);
		
		Boolean removed;
		
		if (userChoice == JOptionPane.YES_OPTION) {
			// reference to the view, for updating the combo box
			// listener passes false because player needs to be removed from the view
			Boolean add = false;
			frame.updateComboBox(player.getPlayerName(), add);
			
			// update the summary table
			frame.getSummaryPanel().removePlayerInfo(player);

			// update model.interfaces.GameEngine
			// removed from model after view is fully updated, because the summary panel uses
			// model's getAllPlayers collection to get the player to be removed from table
			gameEngine.removePlayer(player);

			// update status bar that player is no longer in game
			removed = true;
			frame.getStatusBar().notifyPlayerRemoved(player.getPlayerName(), removed);
		}
		else {
			removed = false;
			frame.getStatusBar().notifyPlayerRemoved(player.getPlayerName(), removed);
		}

		// check if "Remove Player" and "Bet" buttons need enabling or disabling based on
		// number of players in the game
		if ((gameEngine.getAllPlayers().size() > 0) && (frame.getPlayerFromComboBox() != "House")) {
			frame.enablePlayerRemoval();
			frame.enableBetting();
		} else {
			frame.disablePlayerRemoval();
			frame.disableBetting();
		}
	}

}
