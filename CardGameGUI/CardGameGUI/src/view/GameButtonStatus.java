package view;

import java.util.ArrayList;

import client.GameEngineImpl;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.gui.toolbar.ToolBarButton;

public class GameButtonStatus {
	
	private GameEngine gameEngine = new GameEngineImpl();
	private ToolBarButton toolBarButton = new ToolBarButton();
	
	public GameButtonStatus(ToolBarButton toolBarButton, GameEngine gameEngine) {
		this.gameEngine = gameEngine;
		this.toolBarButton = toolBarButton;
	}

	
	public void enableCancelBet() {
		toolBarButton.enableButton("Cancel Bet");
	}
	
	
	public void disableCancelBet() {
		toolBarButton.disableButton("Cancel Bet");
	}
	
	
	/**
	 * check if every player in the game has placed a bet, if true then only enable
	 * the "Deal" button
	 */
	public void enableDeal() {
		ArrayList<Player> allPlayers = (ArrayList<Player>) gameEngine.getAllPlayers();
		Boolean readyToDeal = true;
		for (int i = 0; i < allPlayers.size(); i++) {
			if (allPlayers.get(i).getBet() <= 0) {
				readyToDeal = false;
			}
		}

		if (readyToDeal) {
			toolBarButton.enableButton("Deal");
		}
	}


	public void disableDeal() {
		toolBarButton.disableButton("Deal");
	}


	public void enableRemovePlayer() {
		toolBarButton.enableButton("Remove Player");
	}


	public void disableRemovePlayer() {
		toolBarButton.disableButton("Remove Player");
	}

	public void enableBet() {
		toolBarButton.enableButton("Bet");
	}


	public void disableBet() {
		toolBarButton.disableButton("Bet");
	}
	
	
	

}
