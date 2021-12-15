package controller;

import java.awt.event.ActionEvent;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameFrame;
import view.model.GameEngineImplGUI;

public class GameComboBoxListener extends AbstractGameFrameActionListener {

	public GameComboBoxListener(GameFrame frame, GameEngineImplGUI gameEngineGUI, Player player, GameEngine gameEngine) {
		super(frame, gameEngineGUI, player, gameEngine);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String currentPlayerName = frame.getPlayerFromComboBox();
		
		// notify view.model.GameEngineImplGUI that this is the current player
		gameEngineGUI.setCurrentPlayerSelection(currentPlayerName);
		
		frame.getCardPanel().clearCards();
		
		player = gameEngineGUI.getCurrentPlayer();
		
		if (gameEngineGUI.hasDealt() && player != null) {
			// Repaint CardPanel if user switches to another player in the combo box
			frame.getCardPanel().drawCard(player);
		}
		
		
		
		// if House is selected, prevent user from removing/betting/dealing and
		// if one of the other players is selected, then check button enabling/disabling status
		if (currentPlayerName.equals("House")) {
			frame.disablePlayerRemoval();
			frame.disableBetting();
			frame.disableBetCancellation();
			frame.disableDealing();
		}
		else {
			// check what buttons need enabling or disabling
			if (gameEngineGUI.hasBet() && !gameEngineGUI.hasDealt()) {
				frame.enableBetCancellation();
				frame.enableDealing();
			}
			else if (gameEngineGUI.hasBet() && gameEngineGUI.hasDealt()) {
				frame.disableBetCancellation();
				frame.disableDealing();
			}
			else {
				frame.enableBetting();
				
				frame.disableBetCancellation();
				frame.disableDealing();
			}
			frame.enablePlayerRemoval();
		}
	}

}
