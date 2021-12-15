package view.gui.toolbar;

import java.util.ArrayList;
import javax.swing.JButton;

import controller.AddPlayerListener;
import controller.BettingListener;
import controller.CancellingBetListener;
import controller.DealingListener;
import controller.RemovePlayerListener;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameFrame;
import view.model.GameEngineImplGUI;

public class ToolBarButton extends JButton {
	
	private ArrayList<JButton> buttonsCollection = new ArrayList<JButton>();
	
	
	/**
	 * <pre> To be called by gameToolBar class to instantiate the game buttons
	 * 
	 * Takes a string button name and instantiates as a JButton.
	 * Every button is added its own unique ActionListener, and passed the required parameters
	 * that originate from the gameFrame and passed on via gameToolBar.
	 * 
	 * @param buttonName to be assigned a new JButton
	 * @param frame
	 * @param gameEngineImplGUI
	 * @param player
	 * @param gameEngine
	 * 
	 * @return final JButton product
	 */
	public JButton toolBarButtons(String buttonName, GameFrame frame, GameEngineImplGUI gameEngineImplGUI,
			Player player, GameEngine gameEngine) {
		JButton button = new JButton(buttonName);
		buttonsCollection.add(button);
		
		switch (buttonName) {
			case "Add Player":
				button.addActionListener(new AddPlayerListener(frame, gameEngineImplGUI, player, gameEngine));
				break;
			
			case "Remove Player":
				button.addActionListener(new RemovePlayerListener(frame, gameEngineImplGUI, player, gameEngine));
				break;
			
			case "Bet":
				button.addActionListener(new BettingListener(frame, gameEngineImplGUI, player, gameEngine));
				break;
			
			case "Cancel Bet":
				button.addActionListener(new CancellingBetListener(frame, gameEngineImplGUI, player, gameEngine));
				break;
				
			case "Deal":
				button.addActionListener(new DealingListener(frame, gameEngineImplGUI, player, gameEngine));
				break;
		}
		
		return button;
	}

	
	/**
	 * Checks if the passed buttonName exists in the buttonsCollection, and if yes
	 * disables that JButton
	 * 
	 * @param buttonName of the button to be disabled
	 */
	public void disableButton(String buttonName) {
		for (int i = 0; i < buttonsCollection.size(); i++) {
			JButton tempButton = buttonsCollection.get(i);

			if (tempButton.getText() == buttonName) {
				tempButton.setEnabled(false);
				break;
			}
		}
	}

	
	/**
	 * Checks if the passed buttonName exists in the buttonsCollection, and if yes
	 * enables that JButton
	 * 
	 * @param buttonName of the button to be enabled
	 */
	public void enableButton(String buttonName) {
		for (int i = 0; i < buttonsCollection.size(); i++) {
			JButton tempButton = buttonsCollection.get(i);

			if (tempButton.getText() == buttonName) {
				tempButton.setEnabled(true);
				break;
			}
		}
	}

}
