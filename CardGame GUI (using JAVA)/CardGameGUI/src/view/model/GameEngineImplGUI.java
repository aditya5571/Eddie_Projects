package view.model;

import java.util.ArrayList;
import client.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.interfaces.GUIModel;

public class GameEngineImplGUI implements GUIModel {
	
	private ArrayList<Player> playersInGame;
	private Player currentPlayer;	
	
	public GameEngineImplGUI(GameEngine gameEngine) {
		// Obtain all players currently in game using gameEngine instance
		playersInGame = (ArrayList<Player>) gameEngine.getAllPlayers();
	}
	
	
	public void setCurrentPlayerSelection(String currentPlayerName) {
		for (int i = 0; i < playersInGame.size(); i++) {
			Player player = playersInGame.get(i);
			if (player.getPlayerName().equals(currentPlayerName)) {
				currentPlayer = player;
			}
			else if (currentPlayerName.equals("House")) {
				currentPlayer = new SimplePlayer("0", "House", 0);
			}
		}
	}

	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	
	public Boolean hasBet() {
		if (currentPlayer.getBet() > 0) {
			return true;
		}
		
		return false;
	}

	
	public boolean hasDealt() {
		if (currentPlayer != null && currentPlayer.getResult() != 0) {
			return true;
		}
		
		return false;
	}
	
	
	public boolean allPlayersDealt() {
		for (int i = 0; i < playersInGame.size(); i++) {
			currentPlayer = playersInGame.get(i);
			if (!hasDealt()) {
				return false;
			}
		}
		
		return true;
	}


	public void resetAllBets() {
		for (int i = 0; i < playersInGame.size(); i++) {
			Player player = playersInGame.get(i);
			player.resetBet();
		}
	}
	
}
