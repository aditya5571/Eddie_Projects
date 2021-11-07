package view;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.interfaces.GameEngine;
import model.interfaces.Player;

import javax.swing.JPanel;

public class GameSummaryPanel extends JPanel {
	
	private ArrayList<Player> players;
	private DefaultTableModel gameTable;
	
	public GameSummaryPanel(GameEngine gameEngine) {
		// create a summary panel using JTable with columns showing player's details and win/loss states
		String[] coloumTitles = {"ID", "Name", "Points", "Bet", "Result", "WIN/LOSS"};
		Object[][] values = {};
		gameTable = new DefaultTableModel(values, coloumTitles);
		
		JTable jTable = new JTable();
		jTable.setModel(gameTable);
		jTable.setFillsViewportHeight(true);
		jTable.setPreferredScrollableViewportSize(getMinimumSize());
		jTable.setBackground(Color.CYAN);
		
		// Wrap the table inside scrollpane to bring up a scrollbar whenever table exceeds preferred height
		JScrollPane tableScrollPane = new JScrollPane(jTable);
		
		// add the whole scrollpane containing the table in the GameSummaryPanel
		add(tableScrollPane);
		
		// maintain a collection of current players in game
		players = (ArrayList<Player>) gameEngine.getAllPlayers();
	}
	

	/**
	 * Update the player's info on the table as a new row, as soon as player is added
	 * 
	 * @param player who's info is added
	 */
	public void addPlayerInfo(Player player) {
		Object[] playerInfo = {player.getPlayerId(), player.getPlayerName(), player.getPoints()};
		gameTable.addRow(playerInfo);
	}
	
	
	/**
	 * Remove the player's row as soon as player is removed from game
	 * 
	 * @param player
	 */
	public void removePlayerInfo(Player player) {
		int rowNum = matchedPlayerIndex(player);
		if (rowNum >= 0) {
			gameTable.removeRow(rowNum);
		}
	}

	
	public void addBetInfo(Player player) {
		int rowNum = matchedPlayerIndex(player);
		int colNum = gameTable.findColumn("Bet");
		
		if (rowNum >= 0) {
			gameTable.setValueAt(player.getBet(), rowNum, colNum);
		}
	}

	
	public void removeBetInfo(Player player) {
		int rowNum = matchedPlayerIndex(player);
		int colNum = gameTable.findColumn("Bet");
		
		if (rowNum >= 0) {
			gameTable.setValueAt(player.getBet(), rowNum, colNum);
		}
	}
	
	
	public void addResultInfo(Player player) {
		int rowNum = matchedPlayerIndex(player);
		int colNum = gameTable.findColumn("Result");
		
		if (rowNum >= 0) {
			gameTable.setValueAt(player.getResult(), rowNum, colNum);
		}
	}
	
	
	/**
	 * Update the bet, final points, and win/loss of every player in the collection
	 * 
	 * @param result the final score of the dealers (house) hand
	 */
	public void resetAllInfo(int result) {
		for (int i = 0; i < players.size(); i++) {
			Player tempPlayer = players.get(i);
			int rowNum = i;
			int colNum = gameTable.findColumn("Bet");
			gameTable.setValueAt(tempPlayer.getBet(), rowNum, colNum);
			
			colNum = gameTable.findColumn("Points");
			gameTable.setValueAt(tempPlayer.getPoints(), rowNum, colNum);
			
			colNum = gameTable.findColumn("WIN/LOSS");
			String winLoss = checkWinLoss(result, tempPlayer);
			gameTable.setValueAt(winLoss, rowNum, colNum);
		}
	}
	
	
	/**
	 * <pre>To be called by resetAllInfo() method to update player's win/loss
	 * 
	 * Private helper method to return a string based each player's score compared
	 * to house's score
	 * 
	 * @param houseResult final score of house's hand
	 * @param tempPlayer current player being checked from the collection
	 * 
	 * @return A string based on win/loss state
	 */
	private String checkWinLoss(int houseResult, Player tempPlayer) {
		if (tempPlayer.getResult() > houseResult) {
			return "WIN";
		} else if (tempPlayer.getResult() < houseResult) {
			return "LOSS";
		}

		return "DRAW";
	}
	
	
	/**
	 * Checks what row number the passed player exists at
	 * 
	 * @param player being checked from collection
	 * @return row number at which the player being checked exists
	 */
	private int matchedPlayerIndex(Player player) {
		int rowNum = -1;
		for (int i = 0; i < players.size(); i++) {
			Player tempPlayer = players.get(i);
			if (tempPlayer.equals(player)) {
				rowNum = i;
				return rowNum;
			}
		}
		
		return rowNum;
	}
	
}
