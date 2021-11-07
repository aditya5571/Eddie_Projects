package view.gui.toolbar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToolBar;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameFrame;
import view.model.GameEngineImplGUI;

public class GameToolBar extends JToolBar {
		
	public GameToolBar(ToolBarButton toolBarButton, GameFrame gameFrame,
			GameEngineImplGUI gameEngineImplGUI, Player player, GameEngine gameEngine) {
				
		// initialize buttons for supporting basic gameplay
		final String[] buttonNames = new String[] { "Add Player", "Remove Player", "Bet",
				"Cancel Bet", "Deal" };
		
		// call ToolBarButton class to create JButtons and populate the 'buttons' array
		JButton[] buttons = new JButton[buttonNames.length];
		for (int i = 0; i < buttonNames.length; i++) {
			buttons[i] = toolBarButton.toolBarButtons(buttonNames[i], gameFrame,
					gameEngineImplGUI, player, gameEngine);
		}

		// pass the buttons to addButtons method to be displayed on the toolbar
		addButtons(buttons);
		
		// also add the gameComboBox to gameToolBar
		add(gameFrame.getComboBox());
		
		// Disable certain buttons using toolBarButton instance to place conditions
		// Logically, every button except Add Player button should to be disabled as
		// total players in game = 0
		if (gameEngine.getAllPlayers().size() == 0) {
			toolBarButton.disableButton("Remove Player");
			toolBarButton.disableButton("Bet");
			toolBarButton.disableButton("Cancel Bet");
			toolBarButton.disableButton("Deal");
		}
	}


	/**
	 * Adds buttons to group for radio behaviour and then adds them to toolBar
	 * 
	 * @param buttons array containing JButtons
	 */
	private void addButtons(JButton[] buttons) {
		ButtonGroup buttonGroup = new ButtonGroup();

		for (int i = 0; i < buttons.length; i++) {
			buttonGroup.add(buttons[i]);

			add(buttons[i]);
		}
	}
	
}
