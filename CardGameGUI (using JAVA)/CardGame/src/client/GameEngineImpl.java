package client;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class GameEngineImpl implements GameEngine {
	
	private ArrayList<Player> playersInGame = new ArrayList<Player>();
	private LinkedList<GameEngineCallback> gECB = new LinkedList<GameEngineCallback>();	
	private Deque<PlayingCard> deck = getShuffledHalfDeck();
	
	private final int notExists = -1;

	
	@Override
	public void dealPlayer(Player player, int delay) throws IllegalArgumentException {
		// calls helper method to create new deck if was initially empty
		checkDeck();
		
		// Removes the top card off the deck, instantiates its suit and value,
		// and deals it to the player's hand
		PlayingCard topCard = deck.pop();
		PlayingCard card = new PlayingCardImpl(topCard.getSuit(), topCard.getValue());
		
		// stores the result of every card dealt
		int result = 0;
		result += card.getScore();
		
		// loops through gameEngineCallBack instances and calls nextCard() on each instance
		nextCardCallBack(player, card);
		
		boolean houseFlag = false;
		while (true) {
			// calls helper method
			// houseFlag helps threadDelay throw exception only when delay < 0
			// set to true in dealHouse() only
			threadDelay(delay, houseFlag);
			
			checkDeck();
			
			// deals another card
			topCard = deck.pop();
			card = new PlayingCardImpl(topCard.getSuit(), topCard.getValue());
			result += card.getScore();
			
			// checks if result exceeded bust limit
			if (result > BUST_LEVEL) {
				result -= card.getScore();
				for (GameEngineCallback callBack : gECB) {
					callBack.bustCard(player, card, this);
				}
				
				break;
			}
			else if (result == BUST_LEVEL) {
				break;
			}
			
			nextCardCallBack(player, card);
		}
		
		// sets the total result of the player
		player.setResult(result);
		
		for (GameEngineCallback callBack : gECB) {
			callBack.result(player, result, this);
		}
	}
	
	
	@Override
	public void dealHouse(int delay) throws IllegalArgumentException {
		checkDeck();

		PlayingCard topCard = deck.pop();
		PlayingCard card = new PlayingCardImpl(topCard.getSuit(), topCard.getValue());
		
		int result = 0;
		result += card.getScore();
		
		nextHouseCardCallBack(card);
		
		boolean houseFlag = true;
		while (true) {
			threadDelay(delay, houseFlag);
			
			checkDeck();
			
			topCard = deck.pop();
			card = new PlayingCardImpl(topCard.getSuit(), topCard.getValue());
			result += card.getScore();
			
			if (result > BUST_LEVEL) {
				result -= card.getScore();
				for (GameEngineCallback callBack : gECB) {
					callBack.houseBustCard(card, this);
				}

				break;
			}
			else if (result == BUST_LEVEL) {
				break;
			}
			
			nextHouseCardCallBack(card);
		}
		
		for (Player player : playersInGame) {
			applyWinLoss(player, result);
		}
		
		for (GameEngineCallback callBack : gECB) {
			callBack.houseResult(result, this);
		}
		
		for (Player player : playersInGame) {
			player.resetBet();
		}
	}

	
	@Override
	public void applyWinLoss(Player player, int houseResult) {
		int points = player.getPoints();
		
		// if player wins, player's final points = initial + bet
		// if player loses, player's final points = initial - bet
		// if draw, no change to the points
		if (player.getResult() > houseResult) {
			points += player.getBet();
		}
		else if (player.getResult() < houseResult) {
			points -= player.getBet();
		}
		
		player.setPoints(points);
	}
	
	
	@Override
	public void addPlayer(Player player) {
		// pre-condition
		if (player != null) {
			// checks if player didn't originally exist by calling playerExists() method
			// if return value not equal to -1, then there was a match, thus need to replace at the returned index
			if (playerExists(player) != notExists) {
				int i = playerExists(player);
				playersInGame.remove(i);
				playersInGame.add(player);
			}
			else {
				playersInGame.add(player);
			}
		}
	}

	
	@Override
	public Player getPlayer(String id) {
		Player p;
		
		for (int i = 0; i < playersInGame.size(); i++) {
			p = playersInGame.get(i);
			
			if (p.getPlayerId() == id) {
				return p;
			}
		}
		
		return null;
	}

	
	@Override
	public boolean removePlayer(Player player) {
		if (playerExists(player) != notExists) {
			// "i" is the index at which the player existed
			int i = playerExists(player);
			playersInGame.remove(i);
			return true;
		}
		
		return false;
	}
	
	
	@Override
	public boolean placeBet(Player player, int bet) throws IllegalArgumentException {
		try {
			// throw exception when player has a pre-existing bet,
			// meaning another player with similar ID has placed a bet
			if (player.getBet() != 0) {
				throw new IllegalArgumentException();
			}
		}
		catch (IllegalArgumentException exception) {
			exception.printStackTrace();
		};
		
		// if setBet() returns true, initial points were sufficient and bet was > 0
		if (player.setBet(bet)) {
			return true;
		}
		
		return false;
	}

	
	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		gECB.add(gameEngineCallback);
	}

	
	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		for (int i = 0; i < gECB.size(); i++) {
			if(gameEngineCallback == gECB.get(i)) {
				gECB.remove(i);
				return true;
			}
		}
		
		return false;
	}

	
	@Override
	public Collection<Player> getAllPlayers() {
		final int zero = 0;
		Player temp;
		
		// Traversing through a nested For loop for sorting playersInGame array in ascending order
		for (int i = 0; i < playersInGame.size(); i++) {
			// player extracted from collection by the first for loop
			Player iPlayer = playersInGame.get(i);
			
			for (int j = i+1; j < playersInGame.size(); j++) {
				// player extracted from collection by the second for loop
				Player jPlayer = playersInGame.get(j);
				
				int compare = iPlayer.getPlayerId().compareTo(jPlayer.getPlayerId());
				
				if (compare > zero) {
					// shifting the player with the smaller Id to the top
					temp = iPlayer;
					iPlayer = jPlayer;
					jPlayer = temp;
				}
			}
		}
		
		return playersInGame;
	}

	
	@Override
	public Deque<PlayingCard> getShuffledHalfDeck() {
		List<PlayingCard> cards = new ArrayList<PlayingCard>(PlayingCard.DECK_SIZE);
		PlayingCard uniqueCard;
		
		// creates a "half deck" containing all combinations of suits and values
		for (PlayingCard.Suit suit : PlayingCard.Suit.values()) {
            for (PlayingCard.Value value : PlayingCard.Value.values()) {
            	uniqueCard = new PlayingCardImpl(suit, value);
                cards.add(uniqueCard);
            }
        }
		
		Collections.shuffle(cards);
		
		Deque<PlayingCard> halfDeck = new ArrayDeque<PlayingCard>(cards);
		
		return halfDeck;
	}
	
	
	/**
	 * <pre>
	 * Private helper method - for generating a brand new shuffled halfDeck using getShuffledHalfDeck()
	 * To be called when dealPlayer() or dealHouse() runs out of cards mid-game
	 */
	private void checkDeck() {
		if (deck.isEmpty()) {
			this.deck = getShuffledHalfDeck();
		}
	}

	
	/**
	 * <pre>
	 * Private helper method to handle the delay parameter
	 * 
	 * @param delay
	 */
	private void threadDelay(int delay, boolean houseFlag) {
		final int lower_limit = 0;
		final int upper_limit = 1000;
		
		try {
			if (houseFlag) {
				if (delay < lower_limit) {
					throw new IllegalArgumentException();
				}

				Thread.sleep(delay);
			}
			else {
				if (delay < lower_limit || delay > upper_limit) {
					throw new IllegalArgumentException();
				}

				Thread.sleep(delay);
			}
		}
		catch (IllegalArgumentException | InterruptedException exception) {
			exception.printStackTrace();
		};
	}

	
	/**
	 * <pre>
	 * Private helper method to loop through all the call back instances and call nextCard()
	 * 
	 * @param player
	 * @param card
	 */
	private void nextCardCallBack(Player player, PlayingCard card) {
		for (GameEngineCallback callBack : gECB) {
			callBack.nextCard(player, card, this);
		}
	}
	
	
	/**
	 * <pre>
	 * Private helper method to loop through all the call back instances and call nextHouseCard()
	 * 
	 * @param card
	 */
	private void nextHouseCardCallBack(PlayingCard card) {
		for (GameEngineCallback callBack : gECB) {
			callBack.nextHouseCard(card, this);
		}
	}
	
	
	/**
	 * <pre>
	 * Private helper method to get index of the pre-existing player
	 * 
	 * @param player - to be checked for existance
	 * @return index at which the player already existed, or -1 if player never existed
	 */
	private int playerExists(Player player) {
		Player p;
		
		// check for player's existence only if there's at least 1 player
		if (playersInGame.size() > 0) {
			for (int i = 0; i < playersInGame.size(); i++) {
				p = playersInGame.get(i);
				
				if (p.getPlayerId() == player.getPlayerId()) {
					return i;
				}
			}
		}
		
		return -1;
	}

}
