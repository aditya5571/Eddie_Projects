package controller;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameFrame;
import view.model.GameEngineImplGUI;

public class DealingListener extends AbstractGameFrameActionListener {

	public DealingListener(GameFrame frame, GameEngineImplGUI gameEngineGUI, Player player, GameEngine gameEngine) {
		super(frame, gameEngineGUI, player, gameEngine);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		player = gameEngineGUI.getCurrentPlayer();
		
		String house = "House";

		frame.disableBetting();
		frame.disableBetCancellation();
		frame.disableDealing();
		
		new Thread() {
			@Override
			public void run() {
				if (frame.getPlayerFromComboBox() != house) {
					frame.getCardPanel().clearCards();
					
					gameEngine.dealPlayer(player, frame.getDelay());
					
					frame.getStatusBar().notifyDealingStatus(player.getPlayerName());
					
					if (gameEngineGUI.allPlayersDealt()) {
						JOptionPane.showMessageDialog(frame,
								"All players have dealt. Press OK to deal house", "Deal House",
								JOptionPane.OK_OPTION);

						// sets house as the current player inside view.model.GameEngineImplGUI
						// and sets House as the selected item in the comboBox;
						// House was added at index = 0
						gameEngineGUI.setCurrentPlayerSelection(house);
						frame.getComboBox().setSelectedIndex(0);
						
						gameEngine.dealHouse(frame.getDelay());

						frame.getStatusBar().notifyDealingStatus(house);
						
						gameEngineGUI.resetAllBets();
					}
				}
			}
		}.start();
	}

}
