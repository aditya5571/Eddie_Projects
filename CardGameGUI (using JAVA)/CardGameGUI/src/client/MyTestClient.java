package client;
 
import java.util.Deque;
import java.util.logging.Level;

import model.interfaces.GameEngine;
import model.interfaces.PlayingCard;
import view.GameEngineCallbackGUI;
import view.GameEngineCallbackImpl;
import view.interfaces.GameEngineCallback;
import view.model.GameEngineImplGUI;

/**
 * Simple console client for FP assignment 2, 2020 NOTE: This code will not
 * compile until you have implemented code for the supplied interfaces!
 * 
 * You must be able to compile your code WITHOUT modifying this class.
 * Additional testing should be done by copying and adding to this class while
 * ensuring this class still works.
 * 
 * The provided Validator.jar will check if your code adheres to the specified
 * interfaces!
 * 
 * @author Caspar Ryan
 * 
 */
public class MyTestClient {
	
	public static void main(String args[]) {
		final GameEngine gameEngine = new GameEngineImpl();
		final GameEngineImplGUI gameEngineImplGUI = new GameEngineImplGUI(gameEngine);

		/*
		// create two test players
		Player[] players = new Player[] { new SimplePlayer("1", "The Shark", 500),
				new SimplePlayer("2", "The Loser", 1000),
				new SimplePlayer("44", "House", 2000),
				new SimplePlayer("8", "Hulk", 10000),
				new SimplePlayer("3", "Cap", 2000),
				new SimplePlayer("4", "Spiderman", 3000),
				new SimplePlayer("5", "Ironman", 4000),
				new SimplePlayer("6", "Nat", 4000),
				new SimplePlayer("7", "Thor", 5000) };
		 */
		
		
		
		
		// add logging callback
		GameEngineCallbackImpl callBack = new GameEngineCallbackImpl();
		gameEngine.addGameEngineCallback(callBack);
		
		GameEngineCallback guiCallBack = new GameEngineCallbackGUI(gameEngine, gameEngineImplGUI);
		gameEngine.addGameEngineCallback(guiCallBack);
		
		
		
		
		
		
		/*
		// Uncomment this to DEBUG your deck of cards creation
		// Deque<PlayingCard> shuffledDeck = gameEngine.getShuffledHalfDeck();
		// printCards(shuffledDeck);

		
		// main loop to add players, place a bet and receive hand
		for (Player player : players) {
			gameEngine.addPlayer(player);
			
			gameEngine.placeBet(player, 50000);
			
			gameEngine.dealPlayer(player, 100);
		}

		
		// all players have played so now house deals
		// GameEngineCallBack.houseResult() is called to log all players (after results
		// are calculated)
		gameEngine.dealHouse(10);*/
		
	}

	@SuppressWarnings("unused")
	private static void printCards(Deque<PlayingCard> deck) {
		for (PlayingCard card : deck)
			GameEngineCallbackImpl.logger.log(Level.INFO, card.toString());
	}
}
