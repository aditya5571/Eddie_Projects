package client;

import model.interfaces.Player;

public class SimplePlayer implements Player {

	private String id;
	private String playerName;
	private int intialPoints;
	private int bet;
	private int result;
	
	private final int zero = 0;

	
	public SimplePlayer(String id, String playerName, int initialPoints) {
		this.id = id;
		this.playerName = playerName;
		this.intialPoints = initialPoints;
	}

	
	@Override
	public String getPlayerName() {
		return playerName;
	}

	
	@Override
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	
	@Override
	public int getPoints() {
		return intialPoints;
	}

	
	@Override
	public void setPoints(int points) {
		this.intialPoints = points;
	}

	
	@Override
	public String getPlayerId() {
		return id;
	}


	@Override
	public boolean setBet(int bet) {		
		if (bet > zero && bet <= intialPoints) {
			this.bet = bet;
			return true;
		}
		else if (bet == zero) {
			resetBet();
		}
		
		return false;
	}

	
	@Override
	public int getBet() {
		return bet;
	}

	
	@Override
	public void resetBet() {
		this.bet = 0;
	}

	
	@Override
	public int getResult() {
		return result;
	}

	
	@Override
	public void setResult(int result) {
		this.result = result;
	}

	
	@Override
	public boolean equals(Player player) {
		return id.equals(player.getPlayerId());
	}
	
	
	@Override
	public boolean equals(Object player) {
		if ((player.getClass().equals(Player.class)) && player != null) {
			Player newPlayer = (Player) player;
			return (equals(newPlayer));
		}
		
		return false;
	}

	
	@Override
	public int hashCode() {
		return id.hashCode();
	}


	@Override
	public int compareTo(Player player) {
		return player.getPlayerId().compareTo(id);
	}

	
	@Override
	public String toString() {
		return String.format("Player: id=%s, name=%s, bet=%d, points=%d, RESULT .. %d",
				id, playerName, bet, intialPoints, result);
	}
}
