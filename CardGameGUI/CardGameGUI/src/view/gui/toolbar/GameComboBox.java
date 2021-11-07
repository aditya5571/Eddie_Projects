package view.gui.toolbar;

import javax.swing.JComboBox;

import client.SimplePlayer;
import controller.GameComboBoxListener;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameFrame;
import view.model.GameEngineImplGUI;

public class GameComboBox {
	
	// combo box to choose players from
	private JComboBox<String> playerSelector;	
	
	public GameComboBox(GameFrame frame, GameEngineImplGUI gameEngineGUI, Player player, GameEngine gameEngine) {
		playerSelector = new JComboBox<String>();
		
		// directly add House as the first player in the combo box
		player = new SimplePlayer("0", "House", 0);
		playerSelector.addItem(player.getPlayerName());
		
		// add House as a player in the hashMap (which will contain cards from house's hands)
		frame.getCardPanel().addToHashMap(player, null, gameEngine);
		
		// ActionListener to constantly return what the user's current selection is
		playerSelector.addActionListener(new GameComboBoxListener(frame, gameEngineGUI, player, gameEngine));
	}

	
	/**
	 * Adds given string to the comboBox
	 * 
	 * @param playerName to be added to the comboBox
	 * @param add the Boolean value deciding whether to add or remove the playerName
	 */
	public void playerComboBox(String playerName, Boolean add) {
		if (add) {
			playerSelector.addItem(playerName);
			playerSelector.setSelectedItem(playerName);
		}
		else {
			playerSelector.removeItem((Object) playerName);
		}
	}

	
	/**
	 * @return the current playerName that is selected on the comboBox
	 */
	public String getPlayerName() {
		int i = playerSelector.getSelectedIndex();
		String s = playerSelector.getItemAt(i);
		
		return s;
	}

	
	/**
	 * @return the gameComboBox, to be added to gameToolBar
	 */
	public JComboBox<String> getPlayerSelector() {
		return playerSelector;
	}

}
