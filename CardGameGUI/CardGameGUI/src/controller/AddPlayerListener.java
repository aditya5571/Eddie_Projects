package controller;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameFrame;
import view.model.GameEngineImplGUI;

public class AddPlayerListener extends AbstractGameFrameActionListener {
	
	public AddPlayerListener(GameFrame frame, GameEngineImplGUI gameEngineGUI, Player player, GameEngine gameEngine) {
		super(frame, gameEngineGUI, player, gameEngine);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// get the panel that will prompt user for entering details of the new player
		JPanel playerDetailsPanel = frame.getNewPlayerPanel().getPlayerDetailsPanel();
	    
		int userChoice = JOptionPane.showConfirmDialog(frame, playerDetailsPanel, "Add Players",
				JOptionPane.OK_CANCEL_OPTION);
		
		JTextField playerID = frame.getNewPlayerPanel().getPlayerID();
		JTextField playerName = frame.getNewPlayerPanel().getPlayerName();
		JTextField playerPoints = frame.getNewPlayerPanel().getPlayerPoints();
		
		Boolean added;
		
		if (userChoice == JOptionPane.OK_OPTION) {
			// if either of three 3 fields are left blank by user, show error message
			if (playerID.getText().isBlank() || playerName.getText().isBlank() || playerPoints.getText().isBlank()) {
				JOptionPane.showMessageDialog(frame, "Your player entry was unsuccessful! Please try again.");
			}
			else {
				// initialize simplePlayer with this player's player ID, name and initialPoints
				player = new SimplePlayer(playerID.getText(), playerName.getText(), Integer.parseInt(playerPoints.getText()));
				
				// update model.interfaces.GameEngine by passing this player to the
				// addPlayer method, so player is officially added to the game/model
				gameEngine.addPlayer(player);
				
				// reference to the view, for updating the combo box
				// listener passes true because player needs to be 'added' to view
				Boolean add = true;
				frame.updateComboBox(playerName.getText(), add);
				
				// update player details on the summary panel
				frame.getSummaryPanel().addPlayerInfo(player);
				
				frame.getCardPanel().addToHashMap(player, null, gameEngine);
				
				// notify gameStatusBar that this player was added to game
				added = true;
				frame.getStatusBar().notifyPlayerAdded(playerName.getText(), added);
			}
		}
		else {
			// notify status bar player wasn't added to game if user presses cancel
			added = false;
			frame.getStatusBar().notifyPlayerAdded(playerName.getText(), added);
		}

		// check if "Remove Player" and "Bet" buttons need enabling or disabling based on
		// the number of players in the game
		if (gameEngine.getAllPlayers().size() > 0) {
			frame.enablePlayerRemoval();
			frame.enableBetting();
		} else {
			frame.disablePlayerRemoval();
			frame.disableBetting();
		}
		
		// lastly clear the JTextFields for new entries
		frame.getNewPlayerPanel().clearTextFields();
	}
	
}