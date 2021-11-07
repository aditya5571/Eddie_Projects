package controller;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.GameFrame;
import view.model.GameEngineImplGUI;

public class BettingListener extends AbstractGameFrameActionListener {
		
	public BettingListener(GameFrame frame, GameEngineImplGUI gameEngineGUI, Player player, GameEngine gameEngine) {
		super(frame, gameEngineGUI, player, gameEngine);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		player = gameEngineGUI.getCurrentPlayer();
		String playerName = player.getPlayerName();
		
		// prompt user to enter bet amount
		String bet = JOptionPane.showInputDialog(frame, String.format("Enter bet for %s", playerName), "Place Bet",
				JOptionPane.OK_CANCEL_OPTION);

		// pass bet amount to the setBet() method in model.interfaces.Player to place player's bet
		Boolean betPlaced = player.setBet(Integer.parseInt(bet));

		// notify the user bet was placed succesfully if setBet returns true
		// else notify that bet was unsuccessful
		if (betPlaced) {
			JOptionPane.showMessageDialog(frame, String.format("Bet amount confirmed successfully."),
					"Bet confirmation", JOptionPane.OK_OPTION);
			
			// update the summary panel with bet amount for the specific player
			frame.getSummaryPanel().addBetInfo(player);
			
			frame.disableBetting();
		}
		else {
			JOptionPane.showMessageDialog(frame, String.format("Bet unsuccessful!"), "Bet error",
					JOptionPane.OK_OPTION);
			
			frame.enableBetting();
		}

		// check what other buttons need enabling or disabling after betting activity
		if (gameEngineGUI.hasBet()) {
			frame.enableBetCancellation();
			frame.enableDealing();
		}
		else {
			frame.disableBetCancellation();
			frame.disableDealing();
		}
	}

}
