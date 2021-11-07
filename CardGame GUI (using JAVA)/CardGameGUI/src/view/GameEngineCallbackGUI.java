package view;

import client.GameEngineImpl;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;
import view.model.GameEngineImplGUI;

public class GameEngineCallbackGUI implements GameEngineCallback {
	
	private GameFrame gameFrame;
	private GameEngine gameEngine = new GameEngineImpl();
	private GameEngineImplGUI gameEngineImplGUI; 
	
	public GameEngineCallbackGUI(GameEngine gameEngine, GameEngineImplGUI gameEngineImplGUI) {
		// create new frame and establish the basic conditions for the game to start
		this.gameFrame = new GameFrame(gameEngine, gameEngineImplGUI);
		this.gameEngineImplGUI = gameEngineImplGUI;
		this.gameEngine = gameEngine;
	}


	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		// to keep track of what cards are being dealt to what player
		gameFrame.getCardPanel().addToHashMap(player, card, gameEngine);
		
		// update the CardPanel via gameFrame with the cards that were dealt and stored in view.model
		gameFrame.updateCardPanel(player);
	}

	
	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {		
		gameFrame.getCardPanel().addToHashMap(player, card, gameEngine);
		
		gameFrame.updateCardPanel(player);
	}

	
	@Override
	public void result(Player player, int result, GameEngine engine) {
		// update the final score of a player's hand on gameSummaryPanel
		gameFrame.getSummaryPanel().addResultInfo(player);
	}

	
	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		gameFrame.getCardPanel().addToHashMap(gameEngineImplGUI.getCurrentPlayer(), card, gameEngine);
		
		gameFrame.updateCardPanel(gameEngineImplGUI.getCurrentPlayer());
	}

	
	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		gameFrame.getCardPanel().addToHashMap(gameEngineImplGUI.getCurrentPlayer(), card, gameEngine);
		
		gameFrame.updateCardPanel(gameEngineImplGUI.getCurrentPlayer());
	}

	
	@Override
	public void houseResult(int result, GameEngine engine) {
		gameFrame.getSummaryPanel().resetAllInfo(result);
		
		gameFrame.removeIfZeroPoints();
	}
	
}
