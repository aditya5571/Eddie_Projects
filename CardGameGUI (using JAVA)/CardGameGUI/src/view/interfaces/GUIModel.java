package view.interfaces;

import model.interfaces.Player;

/**
 * Interface for the new viewModel that supports the storage of further player information
 * and provides additional functionality required for maintaining the game's state 
 * 
 * @author Aditya Vadgama
 *
 */
public interface GUIModel {
	
	/**
	 * Iterates through this model's player collection. If the passed player name
	 * matches one in the ArrayList, sets this player as the current player.
	 * 
	 * @param currentPlayerName
	 */
	public abstract void setCurrentPlayerSelection(String currentPlayerName);
	
	
	/**
	 * To be called by listeners to obtain the currently selected player.
	 * 
	 * @return currentPlayer of type Player in the comboBox selection
	 */
	public abstract Player getCurrentPlayer();
	
	
	/**
	 * Checks if the current player has a valid bet
	 * 
	 * @return true if current player has a successful bet amount > 0
	 */
	public abstract Boolean hasBet();
	
	
	/**
	 * Checks if the current player has finished dealing
	 * 
	 * @return true if current player's result is not 0
	 */
	public abstract boolean hasDealt();
	
	
	/**
	 * Checks that if every player's check for hasDealt() returns true, this
	 * method should also return true
	 * 
	 * @return true if all players in the collection have finished dealing
	 */
	public abstract boolean allPlayersDealt();
	
	
	/**
	 * Resets the bets to 0 for all players in the collection
	 */
	public abstract void resetAllBets();

}
